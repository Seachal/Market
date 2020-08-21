package com.dajukeji.hslz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.PayCallbackInfoBean;
import com.dajukeji.hslz.domain.javaBean.PayInfoBean;
import com.dajukeji.hslz.event.OrderChangeEvent;
import com.dajukeji.hslz.jpay.JPay;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.OrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.NumberFormat;

/**
 * 充值页
 */
public class TopActivity extends HttpBaseActivity implements View.OnClickListener {

    private LinearLayout line_o, line_t, line_s, line_f, line_five, line_six;
    private TextView d1, d2, d3, d4, d5, m1, m2, m3, m4, m5, d6, m6;
    private TextView money;
    private EditText ed_num;
    private RelativeLayout click_wechat, click_alipay;
    private ImageView is_wechat, is_alipay;
    private String payType = Constants.wxpay;
    private Button pay_btn;
    private String cash;
    private String pash;
    private String order_id;
    private OrderPresenter orderPresenter;
    private PayInfoBean payInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        setTitleBar("充值", true);
        orderPresenter = new OrderPresenter(this);
        line_o = findViewById(R.id.line_o);
        line_t = findViewById(R.id.line_t);
        line_s = findViewById(R.id.line_s);
        line_f = findViewById(R.id.line_f);
        line_five = findViewById(R.id.line_five);
        line_six = findViewById(R.id.line_six);
        click_wechat = findViewById(R.id.click_wechat);
        click_alipay = findViewById(R.id.click_alipay);
        click_alipay.setOnClickListener(this);
        click_wechat.setOnClickListener(this);
        is_wechat = (ImageView) findViewById(R.id.is_wechat);
        is_alipay = (ImageView) findViewById(R.id.is_alipay);
        d1 = findViewById(R.id.d1);
        d2 = findViewById(R.id.d2);
        d3 = findViewById(R.id.d3);
        d4 = findViewById(R.id.d4);
        d5 = findViewById(R.id.d5);
        m1 = findViewById(R.id.m1);
        m2 = findViewById(R.id.m2);
        m3 = findViewById(R.id.m3);
        m4 = findViewById(R.id.m4);
        m5 = findViewById(R.id.m5);
        d6 = findViewById(R.id.d6);
        m6 = findViewById(R.id.m6);
        ed_num = findViewById(R.id.ed_num);
        line_six.setOnClickListener(this);
        line_five.setOnClickListener(this);
        line_f.setOnClickListener(this);
        line_s.setOnClickListener(this);
        line_t.setOnClickListener(this);
        line_o.setOnClickListener(this);
        money = findViewById(R.id.money);
        pay_btn = findViewById(R.id.pay_btn);
        pay_btn.setOnClickListener(this);

        money.setText("¥0.0");
        //数值改变后立即更新实付金额
        ed_num.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //还未初始化
                if (payInfoBean == null) {
                    ToastUtils.getInstance().showToast(getContext(), "还未初始化");
                    return;
                }

                if (!editable.toString().isEmpty()) {
                    double moneyRate = payInfoBean.getContent().getMoney_cash_rate();
                    if (moneyRate == 0.0) {//尚未初始化
                        ed_num.setText("");
                        return;
                    }
                    double w = Double.valueOf(editable.toString()).doubleValue();
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    money.setText("¥" + nf.format(w * moneyRate) + "");
                    pash = nf.format(w * moneyRate) + "";
                    cash = ed_num.getText().toString();
                    //限制保留两位小数
//                    if (w % 0.01 != 0) {
//                        ed_num.setText(""+);
//                    }
                }
            }
        });

        orderPresenter.getPayInfo(this, SPUtil.getPrefString("token", ""), "充值页信息");
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "充值页信息":
                payInfoBean = (PayInfoBean) object;
                ((TextView) findViewById(R.id.tv_moneyRate)).setText("" + payInfoBean.getContent().getMoney_cash_rate());
                m1.setText(payInfoBean.getContent().getMoneyList().get(0).getMoney());
                m2.setText(payInfoBean.getContent().getMoneyList().get(1).getMoney());
                m3.setText(payInfoBean.getContent().getMoneyList().get(2).getMoney());
                m4.setText(payInfoBean.getContent().getMoneyList().get(3).getMoney());
                m5.setText(payInfoBean.getContent().getMoneyList().get(4).getMoney());
                m6.setText(payInfoBean.getContent().getMoneyList().get(5).getMoney());

                line_o.callOnClick();

//        String en = ed_num.getText().toString();
                ed_num.setOnClickListener(this);
                break;
            default:
                PayCallbackInfoBean callback = (PayCallbackInfoBean) object;
                this.order_id = callback.getContent().getOrder_id();
//            String appId, String partnerId, String prepayId,
//                    String nonceStr, String timeStamp, String sign
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
                break;
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        //还没初始化
        if (payInfoBean == null) {
            ToastUtils.getInstance().showToast(this, "还未初始化");
            return;
        }

        if (v.getId() != R.id.click_wechat && v.getId() != R.id.click_alipay && v.getId() != R.id.pay_btn) {
            m6.setTextColor(getResources().getColor(R.color.near_gray));
            d6.setTextColor(getResources().getColor(R.color.far_gray));
            m5.setTextColor(getResources().getColor(R.color.near_gray));
            d5.setTextColor(getResources().getColor(R.color.far_gray));
            m2.setTextColor(getResources().getColor(R.color.near_gray));
            d2.setTextColor(getResources().getColor(R.color.far_gray));
            m3.setTextColor(getResources().getColor(R.color.near_gray));
            d3.setTextColor(getResources().getColor(R.color.far_gray));
            m4.setTextColor(getResources().getColor(R.color.near_gray));
            d4.setTextColor(getResources().getColor(R.color.far_gray));
            m1.setTextColor(getResources().getColor(R.color.near_gray));
            d1.setTextColor(getResources().getColor(R.color.far_gray));

            line_o.setBackgroundResource(R.drawable.line_bg_befor);
            line_t.setBackgroundResource(R.drawable.line_bg_befor);
            line_f.setBackgroundResource(R.drawable.line_bg_befor);
            line_s.setBackgroundResource(R.drawable.line_bg_befor);
            line_five.setBackgroundResource(R.drawable.line_bg_befor);
            line_six.setBackgroundResource(R.drawable.line_bg_befor);
        }

        //汇率
        double moneyRate = payInfoBean.getContent().getMoney_cash_rate();
        switch (v.getId()) {
            case R.id.line_o:
                String moneyStr = payInfoBean.getContent().getMoneyList().get(0).getMoney();
                cash = moneyStr;
                this.money.setText("¥" + Double.valueOf(moneyStr) * moneyRate);
                pash = Double.valueOf(moneyStr) * moneyRate + "";
                line_o.setBackgroundResource(R.drawable.line_bg);
                m1.setTextColor(Color.WHITE);
                d1.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.line_t:
                String moneyStr2 = payInfoBean.getContent().getMoneyList().get(1).getMoney();
                cash = moneyStr2;
                this.money.setText("¥" + Double.valueOf(moneyStr2) * moneyRate);
                pash = Double.valueOf(moneyStr2) * moneyRate + "";
                line_t.setBackgroundResource(R.drawable.line_bg);
                m2.setTextColor(Color.WHITE);
                d2.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.line_s:
                String moneyStr3 = payInfoBean.getContent().getMoneyList().get(2).getMoney();
                cash = moneyStr3;
                this.money.setText("¥" + Double.valueOf(moneyStr3) * moneyRate);
                pash = Double.valueOf(moneyStr3) * moneyRate + "";
                line_s.setBackgroundResource(R.drawable.line_bg);
                m3.setTextColor(Color.WHITE);
                d3.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.line_f:
                String moneyStr4 = payInfoBean.getContent().getMoneyList().get(3).getMoney();
                cash = moneyStr4;
                this.money.setText("¥" + Double.valueOf(moneyStr4) * moneyRate);
                pash = Double.valueOf(moneyStr4) * moneyRate + "";
                line_f.setBackgroundResource(R.drawable.line_bg);
                m4.setTextColor(Color.WHITE);
                d4.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.line_five:
                String moneyStr5 = payInfoBean.getContent().getMoneyList().get(4).getMoney();
                cash = moneyStr5;
                this.money.setText("¥" + Double.valueOf(moneyStr5) * moneyRate);
                pash = Double.valueOf(moneyStr5) * moneyRate + "";
                line_five.setBackgroundResource(R.drawable.line_bg);
                m5.setTextColor(Color.WHITE);
                d5.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.line_six:
                String moneyStr6 = payInfoBean.getContent().getMoneyList().get(5).getMoney();
                cash = moneyStr6;
                this.money.setText("¥" + Double.valueOf(moneyStr6) * moneyRate);
                pash = Double.valueOf(moneyStr6) * moneyRate + "";
                line_six.setBackgroundResource(R.drawable.line_bg);
                m6.setTextColor(Color.WHITE);
                d6.setTextColor(Color.WHITE);
                ed_num.setText("");
                break;
            case R.id.ed_num:
                line_o.setBackgroundResource(R.drawable.line_bg_befor);
                line_t.setBackgroundResource(R.drawable.line_bg_befor);
                line_f.setBackgroundResource(R.drawable.line_bg_befor);
                line_s.setBackgroundResource(R.drawable.line_bg_befor);
                line_five.setBackgroundResource(R.drawable.line_bg_befor);
                m5.setTextColor(getResources().getColor(R.color.near_gray));
                d5.setTextColor(getResources().getColor(R.color.far_gray));
                m2.setTextColor(getResources().getColor(R.color.near_gray));
                d2.setTextColor(getResources().getColor(R.color.far_gray));
                m3.setTextColor(getResources().getColor(R.color.near_gray));
                d3.setTextColor(getResources().getColor(R.color.far_gray));
                m4.setTextColor(getResources().getColor(R.color.near_gray));
                d4.setTextColor(getResources().getColor(R.color.far_gray));
                m1.setTextColor(getResources().getColor(R.color.near_gray));
                d1.setTextColor(getResources().getColor(R.color.far_gray));
                String en = ed_num.getText().toString();
                if (en.equals("")) {
                    this.money.setText("¥" + 0 + "");
                } else {
                    double w;
                    w = Double.valueOf(ed_num.getText().toString()).doubleValue();
                    NumberFormat nf = NumberFormat.getNumberInstance();
                    nf.setMaximumFractionDigits(2);
                    this.money.setText("¥" + nf.format(w * moneyRate) + "");
                    pash = nf.format(w * moneyRate) + "";
                    cash = ed_num.getText().toString();
                }
                break;
            case R.id.click_wechat:
                is_wechat.setImageResource(R.mipmap.select_yes);
                is_alipay.setImageResource(R.mipmap.select_no);
                payType = Constants.wxpay; // 默认支付方式为微信支付
                break;
            case R.id.click_alipay:
                is_wechat.setImageResource(R.mipmap.select_no);
                is_alipay.setImageResource(R.mipmap.select_yes);
                payType = Constants.alipay;
                break;
            case R.id.pay_btn:
                try {
                    //若充值的金额过大,不允许充值  percentage的算法是分润的，具体问后台
                    if (Double.valueOf(pash) > Double.valueOf(payInfoBean.getContent().getAmount()) * (1 + payInfoBean.getContent().getPercentage())) {
                        ToastUtils.getInstance().showToast(this, "充值金额太大了");
                        break;
                    }
                } catch (Exception e) {
                }
                orderPresenter.payForOder(TopActivity.this, SPUtil.getPrefString("token", ""), payType, pash, cash, "" + payInfoBean.getContent().getPercentage(), DataType.order.payForOder.toString());
//                String url = HttpAddress.mBaseUrl2+"app/mall/order_new/buyCash.htm";
//                OkHttpUtils.post()//
//                        .url(url)
//                        .addParams("token", SPUtil.getPrefString("token",""))
//                        .addParams("money",pash)
//                        .addParams("cash",cash)
//                        .addParams("pay_type",payType)
//                        .build()//
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onError(Call call, Exception e, int id) {
//
//                            }
//
//                            @Override
//                            public void onResponse(String response, int id) {
//                                if (payType==Constants.alipay){
//                                    final PayalipayBean payalipayBean = new Gson().fromJson(response, PayalipayBean.class);
//                                    payalipayBean.getContent().setAlipaymessge(payalipayBean.getContent().getAlipaymessge().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
//                                    aliPay(payalipayBean.getContent().getAlipaymessge());
//                                }else if (payType==Constants.wxpay){
//                                    final PaywxBean paywxBean = new Gson().fromJson(response, PaywxBean.class);
//                                    paywxBean.getContent().setSign(paywxBean.getContent().getSign().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
////                                    wxPay(paywxBean.getContent().getAppid(), paywxBean.getContent().getPartnerid(), paywxBean.getContent().getPrepayid(), paywxBean.getContent().getNoncestr(),
////                                            paywxBean.getContent().getTimestamp(), paywxBean.getContent().getSign());
//                                }
//                            }
//                        });

                break;
        }
    }

    private void wxPay(String appId, String partnerId, String prepayId, String nonceStr, String timeStamp, String sign) {

        JPay.getIntance(this).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new JPay.JPayListener() {
                    @Override
                    public void onPaySuccess() {
                        showToast("支付成功");
//                        EventBus.getDefault().post(new OrderChangeEvent("change"));
//                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
////                        intent.putExtra("id",order_id);
//                        startActivity(intent);
//                        finish();
                    }

                    @Override
                    public void onPayError(int error_code, String message) {
                        if (TextUtils.isEmpty(message)) {
                            showToast("支付失败");
                        }else{
                            showToast(message);
                        }
                        Log.d("weixinchongzhi",error_code+"  "+message);
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
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
////                intent.putExtra("id",order_id);
//                startActivity(intent);
//                finish();
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
}
