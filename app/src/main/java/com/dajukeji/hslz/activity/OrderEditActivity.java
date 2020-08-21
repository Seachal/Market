package com.dajukeji.hslz.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.dajukeji.hslz.activity.mine.order.OrderActivity;
import com.dajukeji.hslz.activity.orderAddress.OrderEditAddressActivity;
import com.dajukeji.hslz.activity.orderAddress.OrderSelectAddressActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.order.OrderPayBean;
import com.dajukeji.hslz.domain.order.OrderWaitPriceBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.network.presenter.MyOrderPresenter;
import com.dajukeji.hslz.network.presenter.OrderPresenter;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

/**
 * Created by ${wangjiasheng} on 2017/12/12 0012.
 */

public class OrderEditActivity extends HttpBaseActivity {

    private TextView consignee_address, order_goods_info, order_info_count, good_price_tt, user_name_tt, user_tel_tt;
    private LinearLayout click_wechat, click_alipay;
    private ImageView is_alipay, is_wechat, order_goods_img;
    private Bundle bundle;
    private AddressPresenter addressPresenter;
    private UserAddressBean addressBean;
    private TextView real_price; // 实付金额
    /*    private TextView tv_transfee_total; // 邮费*/
    private LinearLayout pay_order;
    private LinearLayout default_address_ll, add_address_ll;
    private TextView tv_goods_amount_total;
    private TextView tv_deduct_amount_total;
    private TextView tv_cheap_amount_total;
    private TextView tv_transfee_totals;
    private TextView good_count;
    private String payType = Constants.cash; // 默认支付方式为微信支付
    private String orderType = ""; // 订单支付类型
    private String goods_id;   //产品id
    private String imgUrl; //产品图片
    private int count;   //购买数量
    private String gsp = "";     //规格编号集
    private String addr_id; //收货地址编号
    private String transport;//运输方式
    private String good_name;//产品名称
    private double goods_price;//支付价格

    private String share; // 全网比价是否分享
    private int maxcount; // 全网比价最大限购数
    private long cut_price_id; // 补贴专区支付订单ID
    private PayPwdEditText ppet;
    private String order_id;
    private Dialog walletDialog;
    private UserAddressBean.ContentBean userAddressBean;
    private final int addAddressCode = 100;
    private final int changeAddressCode = 200;
    private OrderPresenter orderPresenter;
    private MyOrderPresenter myOrderPresenter;
    private boolean isVoucher = false;
    private boolean isOrderAdded = false;//订单是否已经创建
    private String goods_id_webactivity = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        goods_id_webactivity = getIntent().getStringExtra("goods_id_webactivity");//配合兑换专区的更新
        if (goods_id_webactivity==null){
            goods_id_webactivity = "";
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectAddressEvent event) {
        this.userAddressBean = event.userAddressBean;
        this.addr_id = event.userAddressBean.getId() + "";
        default_address_ll.setVisibility(View.VISIBLE);
        add_address_ll.setVisibility(View.GONE);
        user_name_tt.setText(event.userAddressBean.getTrueName());
        user_tel_tt.setText(event.userAddressBean.getMobile());
        consignee_address.setText(event.userAddressBean.getAddress() + event.userAddressBean.getArea_info());

        getWaitPrice(orderType); // 获取要付款费用
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        if (event.removed) {
            default_address_ll.setVisibility(View.GONE);
            add_address_ll.setVisibility(View.VISIBLE);
        }
        addressPresenter.getDefaultAddress(getContext(), SPUtil.getPrefString("token", ""), DataType.address.getDefaultAddress.toString());
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        orderPresenter = new OrderPresenter(this);
        myOrderPresenter = new MyOrderPresenter(this);
        bundle = getIntent().getExtras();
        orderType = bundle.getString("orderType");
        getPayOrderIno();
    }

    /**
     * 获得不同的支付订单信息经行支付
     */
    private void getPayOrderIno() {
        good_name = bundle.getString("good_name");
        imgUrl = bundle.getString("imgurl");
        isVoucher = bundle.getBoolean("isVoucher");
        if (orderType.equals("nolPay") || orderType.equals("ninepointnine") || orderType.equals("creative") || orderType.equals("brandgood")) {
            goods_id = bundle.getString("goods_id");
            count = bundle.getInt("count");
            gsp = bundle.getString("gsp");
            transport = bundle.getString("transport");
            goods_price = bundle.getDouble("goods_price");
        } else if (orderType.equals("subsidyPay")) {
            cut_price_id = bundle.getLong("cut_price_id");
            goods_price = bundle.getDouble("goods_price");
        } else if (orderType.equals("comparePay")) {
            gsp = bundle.getString("gsp");
            goods_id = bundle.getString("goods_id");
            goods_price = bundle.getDouble("goods_price");
            share = bundle.getString("share");
            count = bundle.getInt("count");
            goods_price = bundle.getDouble("goods_price");
            maxcount = bundle.getInt("maxcount");
        }

    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_order_edit);
        setTitleBar(getResources().getString(R.string.text_order_pay), true);



        default_address_ll = (LinearLayout) findViewById(R.id.default_address_ll);
        add_address_ll = (LinearLayout) findViewById(R.id.add_address_ll);
        user_name_tt = (TextView) findViewById(R.id.user_name_tt);
        user_tel_tt = (TextView) findViewById(R.id.user_tel_tt);
        consignee_address = (TextView) findViewById(R.id.consignee_address);
        order_goods_info = (TextView) findViewById(R.id.order_goods_info);
        order_info_count = (TextView) findViewById(R.id.order_info_count);
        good_price_tt = (TextView) findViewById(R.id.good_price_tt);
        click_wechat = (LinearLayout) findViewById(R.id.click_wechat);
        click_alipay = (LinearLayout) findViewById(R.id.click_alipay);
        is_alipay = (ImageView) findViewById(R.id.is_alipay);
        is_wechat = (ImageView) findViewById(R.id.is_wechat);
        good_count = ((TextView) findViewById(R.id.good_count));
        order_goods_img = (ImageView) findViewById(R.id.order_goods_img);
        real_price = (TextView) findViewById(R.id.real_price);
        /*tv_transfee_total = (TextView) findViewById(R.id.tv_transfee_total);*/
        pay_order = (LinearLayout) findViewById(R.id.pay_order);
        tv_deduct_amount_total = (TextView) findViewById(R.id.tv_deduct_amount_total);
        tv_cheap_amount_total = (TextView) findViewById(R.id.tv_cheap_amount_total);
        tv_goods_amount_total = (TextView) findViewById(R.id.tv_goods_amount_total);
        tv_transfee_totals = (TextView) findViewById(R.id.tv_transfee_totals);

        //配合兑换专区的更新
        if (!goods_id_webactivity.equals("")){
            ((LinearLayout)findViewById(R.id.yunfei_linear)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.jine_linear)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tv_zffs)).setText("兑换额度");
            ((TextView)findViewById(R.id.tv_sfk)).setText("支付额度：");
        }


        int size = getResources().getDimensionPixelSize(R.dimen.x260);
        GlideEngine.loadThumbnail(getContext(), size, R.drawable.goods, order_goods_img, imgUrl);
        order_goods_info.setText(good_name);//名字
        good_price_tt.setText(goods_price + "");//金额

        //配合兑换专区的更新
        if (!goods_id_webactivity.equals("")){
            real_price.setText(goods_price+"");
        }else {
            real_price.setText(getResources().getString(R.string.rmb_symbol) + goods_price);//实付款
        }


        good_count.setText("" + count);//数量
        pay_order.setOnClickListener(this);
        click_wechat.setOnClickListener(this);
        click_alipay.setOnClickListener(this);
        add_address_ll.setOnClickListener(this);
        default_address_ll.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getDefaultAddress(getContext(), SPUtil.getPrefString("token", ""), DataType.address.getDefaultAddress.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);

        Log.d("dingdan",contentType+" wocaooc");

        if (contentType.equals(DataType.address.getDefaultAddress.toString())) {
            addressBean = (UserAddressBean) object;
            if (!addressBean.getMessage().equals(getResources().getString(R.string.no_default_address))) {
                default_address_ll.setVisibility(View.VISIBLE);
                /*     tv_transfee_total.setVisibility(View.VISIBLE);*/
                add_address_ll.setVisibility(View.GONE);
                user_name_tt.setText(addressBean.getContent().getTrueName());
                user_tel_tt.setText(addressBean.getContent().getMobile());
                consignee_address.setText(addressBean.getContent().getAddress() + addressBean.getContent().getArea_info());
                order_goods_info.setText(bundle.getString("good_name"));
                good_price_tt.setText(String.format("%.2f", bundle.getDouble("goods_price")));

                if(!goods_id_webactivity.equals("")){//兑换专区的更新
                    real_price.setText("额度 " + goods_price);
                }else {
                    real_price.setText(getResources().getString(R.string.rmb_symbol) + goods_price);
                }

                addr_id = addressBean.getContent().getId() + "";
                // 获取要付款费用
                getWaitPrice(orderType);
            } else {
                addr_id = ""; // 地址ID置为空
                default_address_ll.setVisibility(View.GONE);
                add_address_ll.setVisibility(View.VISIBLE);
//                real_price.setText("0.00");//仍然显示价格
                /*  tv_transfee_total.setVisibility(View.GONE);*/
            }

        } else if (contentType.equals("添加订单")) {
            OrderBean callback = (OrderBean) object;
            showToast(callback.getMessage());
            if (callback.getContent().getStatus() == 0) {//下单成功
                isOrderAdded = true;
                this.order_id = String.valueOf(callback.getContent().getOrder_id());
                //开始支付
                showEditPayPwdDialog();
                Log.d("dingdan","?? 订单已添加成功");
            }

//            String appId, String partnerId, String prepayId,
//                    String nonceStr, String timeStamp, String sign
//            if(callback.getContent().getPaytype().equals("wxpay")){
//                callback.getContent().setSign(callback.getContent().getSign().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
//            }else{
//                callback.getContent().setAlipaymessge(callback.getContent().getAlipaymessge().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
//            }
//            if (payType.equals(Constants.cash)) {
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                intent.putExtra("id", order_id);
//                startActivity(intent);
//                finish();
//            }
//                wxPay(callback.getContent().getAppid(), callback.getContent().getPartnerid(), callback.getContent().getPrepayid(), callback.getContent().getNoncestr(),
//                        callback.getContent().getTimestamp(), callback.getContent().getSign());
//            } else if (payType.equals(Constants.alipay)) {
//                aliPay(callback.getContent().getAlipaymessge());
//            }

        } else if (contentType.equals(DataType.order.getFeeByGoodsId.toString())) {
            hideDialogLoading();
            OrderWaitPriceBean waitPriceBean = (OrderWaitPriceBean) object;
            real_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", waitPriceBean.getContent().getReal_pay_total()));
            tv_deduct_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", waitPriceBean.getContent().getDeduct_amount_total()));
            tv_cheap_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", waitPriceBean.getContent().getCheap_amount_total()));
            tv_goods_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", waitPriceBean.getContent().getGoods_amount_total()));
            tv_transfee_totals.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", waitPriceBean.getContent().getTransfee_total()));
            order_info_count.setText((isVoucher ? "产品券 " : "产品 ") + waitPriceBean.getContent().getSpec_info());
        } else if (contentType.equals(DataType.myOrder.getFeeByOrderId.toString())) {
            hideDialogLoading();
            OrderPayBean orderPayBean = (OrderPayBean) object;
            tv_deduct_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getDeduct_amount_total()));
            real_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getReal_pay_total()));
            tv_deduct_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getDeduct_amount_total()));
            tv_cheap_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getCheap_amount_total()));
            tv_goods_amount_total.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getGoods_amount_total()));
            tv_transfee_totals.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", orderPayBean.getContent().getTransfee_total()));
        } else if ("支付订单".equals(contentType)) {
            OrderBean bean = (OrderBean) object;
            walletDialog.dismiss();

            showToast(bean.getMessage());
            Log.d("dingdan",bean.getMessage()+"?? "+bean.getStatus());
            if (bean.getStatus().equals("0")) {
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            } else if ("3".equals(bean.getStatus())) {//没设密码
                Intent intent = new Intent(getContext(), SetupPayPwdCheckActivity.class);
                startActivity(intent);
            }else if ("2".equals(bean.getStatus())){
                //余额不足就提示用户去充值
                final AlertDialog.Builder normalDialog =
                        new AlertDialog.Builder(this);
                normalDialog.setIcon(R.drawable.app_icon);
                normalDialog.setTitle("余额不足");
                normalDialog.setMessage("是否前往充值");
                normalDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                startActivity(new Intent(OrderEditActivity.this,TopActivity.class));
                            }
                        });
                normalDialog.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //...To-do
                                Intent intent = new Intent(OrderEditActivity.this, OrderActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                // 显示
                normalDialog.show();
            }
        }else if ("兑换区订单".equals(contentType)){
            OrderBean bean = (OrderBean) object;
            showToast(bean.getMessage());
            this.order_id = String.valueOf(bean.getContent().getOrder_id());
            //开始支付
            showEditPayPwdDialog();

        }else if ("支付兑换专区订单".equals(contentType)){
            OrderBean bean = (OrderBean) object;
            walletDialog.dismiss();
            showToast(bean.getMessage());

            if (bean.getStatus().equals("0")) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                startActivity(intent);
                finish();
            } else if ("3".equals(bean.getStatus())) {//没设密码
                Intent intent = new Intent(getContext(), SetupPayPwdCheckActivity.class);
                startActivity(intent);
            }else if ("2".equals(bean.getStatus())) {
                //兑换专区余额不足就提示用户联系客服
                showToast("余额不足，请联系客服");

            }
        }

        //配合兑换专区的更新
        if (!goods_id_webactivity.equals("")){
            ((LinearLayout)findViewById(R.id.yunfei_linear)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.jine_linear)).setVisibility(View.GONE);
            ((TextView)findViewById(R.id.tv_zffs)).setText("兑换额度");
            ((TextView)findViewById(R.id.tv_sfk)).setText("支付额度：");
        }
        //配合兑换专区的更新
        if (!goods_id_webactivity.equals("")){
            real_price.setText(goods_price+"");
            ((TextView)findViewById(R.id.quan)).setText("额度 ");
        }else {
            real_price.setText(getResources().getString(R.string.rmb_symbol) + goods_price);//实付款
            ((TextView)findViewById(R.id.quan)).setText("券 ");
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.click_wechat:
                is_wechat.setVisibility(View.VISIBLE);
                is_alipay.setVisibility(View.GONE);
                payType = Constants.cash; // 默认支付方式为微信支付
                break;
            case R.id.click_alipay:
                is_wechat.setVisibility(View.GONE);
                is_alipay.setVisibility(View.VISIBLE);
                payType = Constants.alipay;
                break;
            case R.id.pay_order:
                if (addr_id != null && !addr_id.equals("") && !addr_id.equals("0")) {

//                    if(orderType.equals("comparePay")){
//                        orderPresenter.createComparePriceOrder(getContext(), payType, SPUtil.getPrefString("token",""),goods_id, count,gsp,addr_id,share, DataType.order.createComparePriceOrder.toString());
//                    }else if(orderType.equals("nolPay")){
                    if (order_id == null || !isOrderAdded) {//订单未创建
                        //得到 addr_id 后再创建订单
                        //在这里创建订单
                        int buyType = bundle.getInt("buyType", 0);

                        //配合兑换专区的更新,
                        if(!goods_id_webactivity.equals("")){
                            orderPresenter.createDuiOrder(getContext(),payType,SPUtil.getPrefString("token", ""),gsp,addr_id,goods_id,""+buyType,count+"","兑换区订单");
                        }else {
                            orderPresenter.createNolOrder(getContext(), payType, SPUtil.getPrefString("token", ""), gsp, addr_id, goods_id, "" + buyType, count + "", "添加订单");
                        }

                    } else {
                        showEditPayPwdDialog();
                    }
//                    }else if(orderType.equals("subsidyPay")){
//                        orderPresenter.payForCutPriceOrder(getContext(),payType, SPUtil.getPrefString("token",""),cut_price_id,addr_id,DataType.subsidy.payForCutPriceOrder.toString());
//                    } else if (orderType.equals("brandgood")){
//                        orderPresenter.createBrandOrder(OrderEditActivity.this, goods_id, payType, count, gsp, addr_id, DataType.brand.createBrandOrder.toString());
//                    }else if(orderType.equals("ninepointnine")){
//                        orderPresenter.createNPNOrder(getContext(), payType, SPUtil.getPrefString("token",""),gsp ,addr_id, goods_id, count + "", DataType.order.createNPNOrder.toString());
//                    }else if(orderType.equals("creative")){
//                        orderPresenter.createCreativeOrder(getContext(), payType, SPUtil.getPrefString("token",""),gsp ,addr_id, goods_id, count + "", DataType.order.createCreativeOrder.toString());
//                    }
                } else {
                    showToast("请选择地址");
                }

                break;
            case R.id.default_address_ll:
                Intent changeAddress = new Intent(OrderEditActivity.this, OrderSelectAddressActivity.class);
                startActivityForResult(changeAddress, RESULT_OK);
                break;
            case R.id.add_address_ll:
                Intent addAddress = new Intent(OrderEditActivity.this, OrderEditAddressActivity.class);
                startActivityForResult(addAddress, RESULT_OK);
                break;
        }
    }

    /**
     * 获得代付款金额
     */
    private void getWaitPrice(String type) {
        if (type.equals("subsidyPay")) { // 补贴待支付金额
            myOrderPresenter.getFeeByOrderId(getContext(), SPUtil.getPrefString("token", ""), cut_price_id + "", addr_id, DataType.myOrder.getFeeByOrderId.toString());
        } else {
            orderPresenter.getFeeByGoodsId(getContext(), SPUtil.getPrefString("token", ""), goods_id, gsp, count, addr_id, share, DataType.order.getFeeByGoodsId.toString());
        }
    }

//    private void wxPay(String appId, String partnerId, String prepayId,
//                       String nonceStr, String timeStamp, String sign) {
//        JPay.getIntance(this).toWxPay(appId, partnerId, prepayId,
//                nonceStr, timeStamp, sign, new JPay.JPayListener() {
//                    @Override
//                    public void onPaySuccess() {
//                        showToast("支付成功");
//                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                        intent.putExtra("id",order_id);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void onPayError(int error_code, String message) {
//                        showToast("支付失败");
//                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                        intent.putExtra("id",order_id);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    @Override
//                    public void onPayCancel() {
//                        showToast("取消支付");
//                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                        intent.putExtra("id",order_id);
//                        startActivity(intent);
//                        finish();
//                    }
//                });
//    }

    //    private void aliPay(String orderInfo) { // 支付宝支付
//     JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
//            @Override
//            public void onPaySuccess() {
//                showToast("支付成功");
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                intent.putExtra("id",order_id);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onPayError(int error_code, String message) {
//                showToast("支付失败");
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                intent.putExtra("id",order_id);
//                startActivity(intent);
//                finish();
//            }
//
//            @Override
//            public void onPayCancel() {
//                showToast("取消支付");
//                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                intent.putExtra("id",order_id);
//                startActivity(intent);
//                finish();
//            }
//        });
//    }


    /**
     * 在父类中，会用Toast提示error信息
     * @param error
     * @param dataType
     */
    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
//        if (walletDialog != null)
//            walletDialog.dismiss();
        //密码输入错误的话，不要关闭输入界面，继续让用户输入密码
        if (dataType.equals("支付兑换专区订单")||dataType.equals("支付订单")){
            //手动收起软键盘
//            InputMethodManager imm = (InputMethodManager)
//                    getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
            ((PayPwdEditText)walletDialog.findViewById(R.id.dialog_ppet)).clearText();
        }

    }

    /**
     * 开始支付操作
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

        //如果忘记密码就从这里跳转
        view.findViewById(R.id.tv_forgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderEditActivity.this, SetupPayPwdCheckActivity.class));
            }
        });
        ppet = view.findViewById(R.id.dialog_ppet);
        ImageView img_cancle = view.findViewById(R.id.img_cancle);
        //调用initStyle方法创建你需要设置的样式
        ppet.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletDialog.dismiss();
            }
        });
        ppet.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调

                //手动收起软键盘
//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
                //可在此执行下一步操作
                String paypass = ppet.getPwdText();
//            showToast(paypass);
                //TODO 购买类型
                //配合兑换专区的更新
                if (!goods_id_webactivity.equals("")){
                    orderPresenter.PayDuiOrder(this,order_id,paypass,"支付兑换专区订单");
                }else {
                    orderPresenter.newPayOrder(this, order_id, paypass, "支付订单");
                }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
