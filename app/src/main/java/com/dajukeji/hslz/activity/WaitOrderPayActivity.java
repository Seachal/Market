package com.dajukeji.hslz.activity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.OrderBean;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.PayCallbackInfoBean;
import com.dajukeji.hslz.domain.order.OrderPayBean;
import com.dajukeji.hslz.event.OrderChangeEvent;
import com.dajukeji.hslz.jpay.JPay;
import com.dajukeji.hslz.network.presenter.MyOrderPresenter;
import com.dajukeji.hslz.network.presenter.OrderPresenter;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.SelectedButton;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

/**
 * 待支付 订单 页面
 */
public class WaitOrderPayActivity extends HttpBaseActivity {
    private OrderPresenter orderPresenter;
    private MyOrderPresenter myOrderPresenter;
    private LinearLayout ll_order_pay;
    private SelectedButton order_pay;  // 立即支付
    private TextView tv_order_price, tv_ship_price; //价格 、运费
    private TextView tv_order_des; // 描述
    private TextView tv_back_integration; //省券
    private TextView tv_order_sn; //订单号
    private TextView tv_create_time; // 创建时间
    private TextView tv_copy_order; // 订单复制
    private LinearLayout click_wechat, click_alipay;
    private ImageView is_wechat, is_alipay;
    private TextView tv_deduct_amount_total;
    private ClipboardManager mClipboardManager; //复制粘贴
    //剪切板Data对象
    private ClipData mClipData;
    private String payType = Constants.cash; // 默认支付方式为代金券支付
    private String order_id;//待支付订单
    private Dialog walletDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_wait_order_pay);
        setTitleBar(R.string.text_order_pay, true);
        Bundle bundle = getIntent().getExtras();
        order_id = "" + bundle.getLong("order_id");
        orderPresenter = new OrderPresenter(this);
        myOrderPresenter = new MyOrderPresenter(this);
        myOrderPresenter.getFeeByOrderId(getContext(), SPUtil.getPrefString("token", ""), order_id + "", "", "未支付订单信息");
        mClipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE); //初始化复制粘贴
    }

    @Override
    protected void initView() {
        super.initView();
        ll_order_pay = (LinearLayout) findViewById(R.id.ll_order_pay);
        order_pay = (SelectedButton) findViewById(R.id.order_pay);
        tv_order_price = (TextView) findViewById(R.id.tv_order_price);
        tv_ship_price = (TextView) findViewById(R.id.tv_ship_price);
        tv_order_des = (TextView) findViewById(R.id.tv_order_des);
        tv_back_integration = (TextView) findViewById(R.id.tv_back_integration);
        tv_order_sn = (TextView) findViewById(R.id.tv_order_sn);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        tv_copy_order = (TextView) findViewById(R.id.tv_copy_order);
        click_wechat = (LinearLayout) findViewById(R.id.click_wechat);
        click_alipay = (LinearLayout) findViewById(R.id.click_alipay);
        is_wechat = (ImageView) findViewById(R.id.is_wechat);
        is_alipay = (ImageView) findViewById(R.id.is_alipay);
        tv_deduct_amount_total = (TextView) findViewById(R.id.tv_deduct_amount_total);
        click_wechat.setOnClickListener(this);
        click_alipay.setOnClickListener(this);
        order_pay.setOnClickListener(this);
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals("未支付订单信息")) {
            final OrderPayBean orderPayBean = (OrderPayBean) object;
            tv_order_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getReal_pay_total()));
            if (orderPayBean.getContent().getTransfee_total() == 0.0) {
                tv_ship_price.setText("运费: " + getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getTransfee_total()));
            } else {
                tv_ship_price.setText("免运费");
            }
            tv_deduct_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getDeduct_amount_total()));
            tv_order_des.setText("订单为您保留" + orderPayBean.getContent().getKeep_hours() + "小时，超时后将自动关闭");
            tv_back_integration.setText("" + orderPayBean.getContent().getBack_integration());
            tv_order_sn.setText(orderPayBean.getContent().getOrder_sn());
            tv_create_time.setText(orderPayBean.getContent().getCreate_time());
            tv_copy_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClipData = ClipData.newPlainText("order", orderPayBean.getContent().getOrder_sn());
                    //把clip对象放在剪贴板中
                    mClipboardManager.setPrimaryClip(mClipData);
                    showToast("复制成功");
                }
            });
            ll_order_pay.setVisibility(View.VISIBLE);

        } else if (contentType.equals("支付订单")) {
//            String appId, String partnerId, String prepayId,
//                    String nonceStr, String timeStamp, String sign
            //代金券支付
            if (true) {
                walletDialog.dismiss();
                OrderBean orderBean = (OrderBean) object;
                showToast(orderBean.getMessage());
                if (orderBean.getContent().getStatus() == 0) {//支付成功
                    EventBus.getDefault().post(new OrderChangeEvent("change"));
                    Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                    intent.putExtra("id", order_id);
                    startActivity(intent);
                    finish();
                } else if ("3".equals(orderBean.getStatus()) || orderBean.getContent().getStatus() == 3) {//没设密码
                    Intent intent = new Intent(getContext(), SetupPayPwdCheckActivity.class);
                    startActivity(intent);
                }
            } else {
                PayCallbackInfoBean callback = (PayCallbackInfoBean) object;
                if (callback.getContent().getPaytype().equals("wxpay")) {
                    callback.getContent().setSign(callback.getContent().getSign().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
                } else {
                    callback.getContent().setAlipaymessge(callback.getContent().getAlipaymessge().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
                }

                if (callback.getContent().getPaytype().equals(Constants.wxpay)) {
                    wxPay(callback.getContent().getAppid(), callback.getContent().getPartnerid(), callback.getContent().getPrepayid(), callback.getContent().getNoncestr(),
                            callback.getContent().getTimestamp(), callback.getContent().getSign());
                } else if (callback.getContent().getPaytype().equals(Constants.alipay)) {
                    aliPay(callback.getContent().getAlipaymessge());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.click_wechat:
                is_wechat.setVisibility(View.VISIBLE);
                is_alipay.setVisibility(View.GONE);
                payType = Constants.wxpay; // 默认支付方式为微信支付
                break;
            case R.id.click_alipay:
                is_wechat.setVisibility(View.GONE);
                is_alipay.setVisibility(View.VISIBLE);
                payType = Constants.alipay;
                break;
            case R.id.click_nowPay://代金券支付
                //只能代金券支付
                break;
            case R.id.order_pay:
                //以前
                showEditPayPwdDialog();
//                orderPresenter.payForOrder(getContext(),SPUtil.getPrefString("token",""),payType,of_id+"",DataType.order.payForOrder.toString());
                break;
        }
    }

    /**
     * 输入密码Dialog
     */
    private void showEditPayPwdDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        walletDialog = new Dialog(this, R.style.walletFrameWindowStyle);
        walletDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Window window = walletDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        //紧贴软键盘弹出
        wl.gravity = Gravity.BOTTOM;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        walletDialog.onWindowAttributesChanged(wl);
        walletDialog.setCanceledOnTouchOutside(false);
        walletDialog.show();

        //忘记密码 按钮
        view.findViewById(R.id.tv_forgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WaitOrderPayActivity.this, SetupPayPwdCheckActivity.class));
            }
        });

        final PayPwdEditText ppet = view.findViewById(R.id.dialog_ppet);
        ImageView img_cancle = view.findViewById(R.id.img_cancle);
        //调用initStyle方法创建你需要设置的样式
        ppet.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletDialog.dismiss();
                //手动收起软键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
                imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
                //跳转至订单详情页面
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            }
        });
        ppet.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调

                //手动收起软键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
                //可在此执行下一步操作
                String paypass = ppet.getPwdText();
//            showToast(paypass);
                //TODO 支付 支付啊
                orderPresenter.newPayOrder(this, order_id, paypass, "支付订单");
//                orderPresenter.payForOrder(getContext(), SPUtil.getPrefString("token", ""), payType, of_id + "", paypass, DataType.order.payForOrder.toString());
            }
        });
        //延迟弹起软键盘，使PayPwdEditText获取焦点
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ppet.setFocus();
            }
        }, 100);
    }

    private void wxPay(String appId, String partnerId, String prepayId,
                       String nonceStr, String timeStamp, String sign) {
        JPay.getIntance(this).toWxPay(appId, partnerId, prepayId,
                nonceStr, timeStamp, sign, new JPay.JPayListener() {
                    @Override
                    public void onPaySuccess() {
                        showToast("支付成功");
                        EventBus.getDefault().post(new OrderChangeEvent("change"));
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("id", order_id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPayError(int error_code, String message) {
                        showToast("支付失败");
                    }

                    @Override
                    public void onPayCancel() {
                        showToast("取消了支付");
                    }
                });
    }


    private void aliPay(String orderInfo) { // 支付宝支付
        JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                showToast("支付成功");
                EventBus.getDefault().post(new OrderChangeEvent("change"));
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onPayError(int error_code, String message) {
                showToast("支付失败");
                finish();
            }

            @Override
            public void onPayCancel() {
                showToast("取消了支付");
                finish();
            }
        });
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
        if (walletDialog != null)
            walletDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
