package com.dajukeji.hslz.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.OrderBean;
import com.dajukeji.hslz.activity.mine.order.OrderActivity;
import com.dajukeji.hslz.activity.orderAddress.OrderEditAddressActivity;
import com.dajukeji.hslz.activity.orderAddress.OrderSelectAddressActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.CreateOrderInfoBean;
import com.dajukeji.hslz.domain.javaBean.LookShopcartBean;
import com.dajukeji.hslz.domain.javaBean.MonetaryBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.jpay.JPay;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.network.presenter.CartPresenter;
import com.dajukeji.hslz.network.presenter.OrderPresenter;
import com.dajukeji.hslz.util.MLog;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.StringUtil;
import com.dajukeji.hslz.util.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${wangjiasheng} on 2017/12/29 0029.
 * 购物车支付
 */
/*
下单逻辑：
1、接口请求应付金额，并通过购物车页面传来的选中的订单数据展示订单信息
2、点击购买时调用创建订单接口，若订单不是同一个店家、或订单不是同一个产品类型（产品、产品券），则需要多次调用创建订单接口。
3、当所有订单创建完毕后，再让用户进行全部付款，传入所有订单ID一起付款。

付款逻辑：
1、根据订单编号依次走多次支付接口，第一次支付时额外传入所有订单编号来判断余额是否足够。
2、全部成功后提示支付成功，一旦有一次失败就不在继续调用支付。
 */
public class ShopCartPayActivity extends HttpBaseActivity {
    private Dialog walletDialog;
    private PayPwdEditText ppet;
    private XRecyclerView order_good_listview;
    private Button pay_order;
    private LinearLayout default_address_ll, add_address_ll;
    private TextView user_name_tt, user_tel_tt, consignee_address;
    private LinearLayout click_wechat, click_alipay;
    private ImageView is_wechat, is_alipay;
    private String payType = Constants.wxpay;
    private AddressPresenter addressPresenter;
    private CartPresenter cartPresenter;
    private UserAddressBean addressBean;
    private OrderPresenter orderPresenter;
    private final int addAddressCode = 100;
    private final int changeAddressCode = 200;
    private long address_id;
    private TextView real_price;
    private String gc_ids = "";
    private String previousStoreName = "";
    //选中的列表订单
    private List<LookShopcartBean.ContentEntity.Store_cart_listEntity> selected;
    private BaseRecyclerAdapter<LookShopcartBean.ContentEntity.Store_cart_listEntity> madapter;
    private TextView aggregate_money, transport_cost, coupon_exchange, activity_discounts;
    //下单逻辑
    private String order_id = "";
    private boolean isOrderAdded = false;//订单是否已创建,标记订单只创建一遍
    private Map<String, List<LookShopcartBean.ContentEntity.Store_cart_listEntity>> shouldCreateOrderMap;//应该创建的订单
    //支付逻辑
    private List<String> shouldPayOrder;//需要支付的订单
    private String paypass;//支付密码

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressPresenter = new AddressPresenter(this);
        orderPresenter = new OrderPresenter(this);
        cartPresenter = new CartPresenter(this);
        addressPresenter.getDefaultAddress(ShopCartPayActivity.this, SPUtil.getPrefString("token", ""), DataType.address.getDefaultAddress.toString());
        EventBus.getDefault().register(this);
        gc_ids = getIntent().getStringExtra("gc_ids");

        //默认价格
        double defaultMoney = 0.0;
        if (selected != null)
            for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : selected) {
                defaultMoney += entity.getGoods_price() * entity.getCount();
            }
        real_price.setText(String.format("%.2f", defaultMoney));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectAddressEvent event) {
        UserAddressBean.ContentBean useraddressbean = event.userAddressBean;
        this.address_id = useraddressbean.getId();
        default_address_ll.setVisibility(View.VISIBLE);
        add_address_ll.setVisibility(View.GONE);
        user_name_tt.setText(useraddressbean.getTrueName());
        user_tel_tt.setText(useraddressbean.getMobile());
        consignee_address.setText(useraddressbean.getAddress() + useraddressbean.getArea_info());
        cartPresenter.getFeeByGCID(ShopCartPayActivity.this, gc_ids, useraddressbean.getId(), DataType.cart.getFeeByGCID.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        if (event.removed) {
            default_address_ll.setVisibility(View.GONE);
            add_address_ll.setVisibility(View.VISIBLE);
            pay_order.setVisibility(View.GONE);
        }
        addressPresenter.getDefaultAddress(ShopCartPayActivity.this, SPUtil.getPrefString("token", ""), DataType.address.getDefaultAddress.toString());
    }

    @Override
    protected void initView() {
        super.initView();
        selected = (List<LookShopcartBean.ContentEntity.Store_cart_listEntity>) getIntent().getSerializableExtra("selected");
        setContentView(R.layout.activity_shopcart_pay);
        setTitleBar(R.string.text_order_pay, true);
        pay_order = (Button) findViewById(R.id.pay_order);
        pay_order.setOnClickListener(this);
        default_address_ll = (LinearLayout) findViewById(R.id.default_address_ll);
        default_address_ll.setOnClickListener(this);
        add_address_ll = (LinearLayout) findViewById(R.id.add_address_ll);
        add_address_ll.setOnClickListener(this);
        user_name_tt = (TextView) findViewById(R.id.user_name_tt);
        user_tel_tt = (TextView) findViewById(R.id.user_tel_tt);
        consignee_address = (TextView) findViewById(R.id.consignee_address);
        click_wechat = (LinearLayout) findViewById(R.id.click_wechat);
        click_wechat.setOnClickListener(this);
        click_alipay = (LinearLayout) findViewById(R.id.click_alipay);
        click_alipay.setOnClickListener(this);
        is_wechat = (ImageView) findViewById(R.id.is_wechat);
        is_alipay = (ImageView) findViewById(R.id.is_alipay);
        order_good_listview = (XRecyclerView) findViewById(R.id.order_good_listview);
        order_good_listview.setLayoutManager(new LinearLayoutManager(this));
        order_good_listview.setNestedScrollingEnabled(false);
        order_good_listview.setLoadingMoreEnabled(false);
        order_good_listview.setPullRefreshEnabled(false);
        real_price = (TextView) findViewById(R.id.real_price);
        aggregate_money = (TextView) findViewById(R.id.aggregate_money);
        transport_cost = (TextView) findViewById(R.id.transport_cost);
        coupon_exchange = (TextView) findViewById(R.id.coupon_exchange);
        activity_discounts = (TextView) findViewById(R.id.activity_discounts);
        order_good_listview.setAdapter(new BaseRecyclerAdapter<LookShopcartBean.ContentEntity.Store_cart_listEntity>(getContext(), selected, R.layout.item_pay_cart_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, LookShopcartBean.ContentEntity.Store_cart_listEntity item, int position, boolean isScrolling) {
                RelativeLayout store_name_layout = holder.getView(R.id.store_name_layout);
                if (previousStoreName.equals(item.getStore_name())) {
                    store_name_layout.setVisibility(View.GONE);
                } else {
                    store_name_layout.setVisibility(View.VISIBLE);
                }
                previousStoreName = item.getStore_name();
                holder.setImageByUrl(R.id.goods_image, item.getGoods_image());
                holder.setText(R.id.goods_name, item.getGoods_name());
                holder.setText(R.id.good_sku, (item.isSecurities() ? "产品券 " : "产品 ") + item.getSpec_info());
                holder.setText(R.id.good_count, item.getCount() + "");
                holder.setText(R.id.good_price, StringUtil.decimalString(item.getGoods_price()));
                holder.setText(R.id.store_name_tt, item.getStore_name());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_address_ll:
                Intent addAddress = new Intent(ShopCartPayActivity.this, OrderEditAddressActivity.class);
                startActivityForResult(addAddress, addAddressCode);
                break;
            case R.id.default_address_ll:
                Intent changeAddress = new Intent(ShopCartPayActivity.this, OrderSelectAddressActivity.class);
                startActivityForResult(changeAddress, changeAddressCode);
                break;
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
            case R.id.pay_order:
                //TODO 购买类型
                //没有地址
                if (address_id == 0L) {
                    ToastUtils.getInstance().showToast(this, "请选择收货地址");
                } else if (isOrderAdded) {//已在此页创建订单
                    showEditPayPwdDialog();
                } else {

                    //gcIdMap：  key=商店id+产品是否产品券, value=此商店+此产品类型的购物车产品列表
                    Map<String, List<LookShopcartBean.ContentEntity.Store_cart_listEntity>> gcIdMap = new HashMap<>();
                    for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : selected) {
                        List<LookShopcartBean.ContentEntity.Store_cart_listEntity> gcIdValue;
                        if (gcIdMap.containsKey("" + entity.getStore_id() + entity.isSecurities())) {
                            gcIdValue = gcIdMap.get("" + entity.getStore_id() + entity.isSecurities());
                            gcIdValue.add(entity);
                        } else {
                            gcIdValue = new ArrayList<>();
                            gcIdValue.add(entity);
                        }
                        gcIdMap.put("" + entity.getStore_id() + entity.isSecurities(), gcIdValue);
                    }

                    shouldCreateOrderMap = gcIdMap;//一单成功后下后一单
                    //请求下单
                    cartPresenter.createOrderByGc(ShopCartPayActivity.this, getOnesGcIds(), address_id + "", "生成订单");
                }
//                showEditPayPwdDialog();
                break;
        }
    }

    /**
     * 随意从 shouldCreateOrderMap 中拼接一串购物车id
     */
    @NotNull
    private String getOnesGcIds() {
        StringBuilder gcIds = new StringBuilder();
        String thisKey = null;
        //随意拿一个key
        for (String key : shouldCreateOrderMap.keySet()) {
            thisKey = key;
            break;
        }
        if (thisKey != null) {
            //准备一批订单
            List<LookShopcartBean.ContentEntity.Store_cart_listEntity> values = shouldCreateOrderMap.remove(thisKey);
            //拼接购物车ids
            for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : values) {
                gcIds.append(",").append(entity.getGc_id());
            }
            gcIds.deleteCharAt(0);
        }
        return gcIds.toString();
    }

    /**
     * 是否购买 产品券 + 产品
     */
    private boolean isMergedBuy(List<LookShopcartBean.ContentEntity.Store_cart_listEntity> selected) {
        boolean isMerged = false;
        boolean firstIsSecurities = selected.get(0).isSecurities();
        for (int i = 0; i < selected.size(); i++) {
            if (firstIsSecurities != selected.get(i).isSecurities())
                isMerged = true;
        }
        return isMerged;
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.address.getDefaultAddress.toString())) {
            addressBean = (UserAddressBean) object;
            if (addressBean.getStatus().equals("0") && addressBean.getMessage().equals("地址信息")) {
                default_address_ll.setVisibility(View.VISIBLE);
                add_address_ll.setVisibility(View.GONE);
                user_name_tt.setText(addressBean.getContent().getTrueName());
                user_tel_tt.setText(addressBean.getContent().getMobile());
                consignee_address.setText(addressBean.getContent().getAddress() + addressBean.getContent().getArea_info());
                cartPresenter.getFeeByGCID(ShopCartPayActivity.this, gc_ids, addressBean.getContent().getId(), DataType.cart.getFeeByGCID.toString());
//                pay_order.setVisibility(View.VISIBLE);
            } else {
                default_address_ll.setVisibility(View.GONE);
                add_address_ll.setVisibility(View.VISIBLE);
//                pay_order.setVisibility(View.GONE);
            }
            address_id = addressBean.getContent().getId();
        } else if (contentType.equals(DataType.cart.getFeeByGCID.toString())) {
            MonetaryBean monetaryBean = (MonetaryBean) object;
//            aggregate_money.setText(StringUtil.decimalString(monetaryBean.getContent().getGoods_amount()));
            aggregate_money.setText(StringUtil.decimalString(monetaryBean.getContent().getReal_pay()));
            transport_cost.setText(StringUtil.decimalString(monetaryBean.getContent().getTransfee_amount()));
            coupon_exchange.setText(StringUtil.decimalString(monetaryBean.getContent().getDeduct_amount()));
            activity_discounts.setText(StringUtil.decimalString(Math.abs(monetaryBean.getContent().getCheap_amount())));
            real_price.setText(StringUtil.decimalString(monetaryBean.getContent().getReal_pay()));
//            pay_order.setClickable(true);
        } else if (contentType.equals("支付订单")) {
            OrderBean bean = (OrderBean) object;
            walletDialog.dismiss();

            if (bean.getContent().getStatus() == 0) { // 支付成功
                shouldPayOrder.remove(0);
                //未全部支付完成
                if (!shouldPayOrder.isEmpty()) {
                    orderPresenter.newPayOrder(this, shouldPayOrder.get(0), paypass, "支付订单");
                } else {
                    //全部支付完成
                    if (!order_id.contains(",")) {//是以前的创建了单个订单
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("id", order_id);
                        startActivity(intent);
                        finish();
                    } else { // 是新的,创建了多个订单,跳到我的订单页
                        finish();
                        startActivity(new Intent(this, OrderActivity.class));
                    }
                }
            } else if ("3".equals(bean.getStatus()) || bean.getContent().getStatus() == 3) { // 没设密码
                Intent intent = new Intent(getContext(), SetupPayPwdCheckActivity.class);
                startActivity(intent);
            } else {//其他支付错误
                ToastUtils.getInstance().showToast(getContext(), bean.getMessage());
            }
//            NowPayCallbackInfoBean callback = (NowPayCallbackInfoBean) object;
//            walletDialog.dismiss();
//            showToast(callback.getMessage() + "");
//
//            //请求成功
//            if (callback.getStatus().equals("0")) {
//
//                this.order_id = callback.getContent().getOrder_id() + "";
//                EventBus.getDefault().post(new ShopCartChangeEvent(""));
//
//                if (callback.getContent().getStatus() == 1) {
//                    //支付完成,直接跳转
//                    Intent intent = new Intent(getContext(), OrderDetailActivity.class);
//                    intent.putExtra("id", order_id);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    //支付失败
//                }


//                原有支付逻辑
//                this.order_id = callback.getContent().getOrder_id();
//                EventBus.getDefault().post(new ShopCartChangeEvent(""));
//                if(callback.getContent().getPaytype().equals("wxpay")){
//                    callback.getContent().setSign(callback.getContent().getSign().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
//                }else{
//                    callback.getContent().setAlipaymessge(callback.getContent().getAlipaymessge().replaceAll("\u003d", "=").replaceAll("\u0026", "&"));
//                }
//                if (callback.getContent().getPaytype().equals(Constants.wxpay)) {
//                    wxPay(callback.getContent().getAppid(), callback.getContent().getPartnerid(), callback.getContent().getPrepayid(), callback.getContent().getNoncestr(),
//                            "1523151921", callback.getContent().getSign());
//                } else if (callback.getContent().getPaytype().equals(Constants.alipay)) {
//                    aliPay(callback.getContent().getAlipaymessge());
//                }

        } else if ("生成订单".equals(contentType)) {

            CreateOrderInfoBean object1 = (CreateOrderInfoBean) object;
            //下单成功
            if ("0".equals(object1.getStatus())) {
                isOrderAdded = true;
                //拼接订单ID
                if (!order_id.isEmpty() && !order_id.endsWith(","))
                    order_id += ",";
                order_id += object1.getContent().getOrder_id();
                //去下下一个订单
                if (!shouldCreateOrderMap.isEmpty()) {
                    cartPresenter.createOrderByGc(ShopCartPayActivity.this, getOnesGcIds(), address_id + "", "生成订单");
                }
                //全部下单完毕
                else {
                    ToastUtils.getInstance().showToast(this, object1.getMessage());
                    MLog.INSTANCE.d("订单id:" + order_id);
                    showEditPayPwdDialog();
                }
            } else { // 下单失败
                ToastUtils.getInstance().showToast(this, object1.getMessage());
                finish();
            }
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        if (walletDialog != null)
            walletDialog.dismiss();
        //下单失败
        if ("生成订单".equals(dataType)) {
            finish();
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

        view.findViewById(R.id.tv_forgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopCartPayActivity.this, SetupPayPwdCheckActivity.class));
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
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
                //可在此执行下一步操作
                paypass = ppet.getPwdText();
//            showToast(paypass);
                //TODO 支付
                shouldPayOrder = new ArrayList();
                Collections.addAll(shouldPayOrder, order_id.split(","));
                orderPresenter.newPayOrder(this, shouldPayOrder.get(0), paypass, order_id, "支付订单");
//                cartPresenter.createOrderByGc(ShopCartPayActivity.this, gc_ids, address_id + "", "支付订单");
//                orderPresenter.createNolOrder(getContext(), payType, SPUtil.getPrefString("token",""),gsp ,addr_id, goods_id, count + "",paypass,buyType,DataType.myPresenterType.orderPay.toString());

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

//    private void showEditPayPwdDialog() {
//        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
//        walletDialog = new Dialog(this, R.style.walletFrameWindowStyle);
//        walletDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//
//        final Window window = walletDialog.getWindow();
//        WindowManager.LayoutParams wl = window.getAttributes();
//        //紧贴软键盘弹出
//        wl.gravity = Gravity.BOTTOM;
//        // 以下这两句是为了保证按钮可以水平满屏
//        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        walletDialog.onWindowAttributesChanged(wl);
//        walletDialog.setCanceledOnTouchOutside(false);
//        walletDialog.show();
//
//        ppet = view.findViewById(R.id.dialog_ppet);
//        ImageView img_cancle = view.findViewById(R.id.img_cancle);
//        //调用initStyle方法创建你需要设置的样式
//        ppet.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
//        img_cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                walletDialog.dismiss();
//            }
//        });
//        ppet.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
//            @Override
//            public void onFinish(String str) {//密码输入完后的回调
//
//                //手动收起软键盘
//                InputMethodManager imm = (InputMethodManager)
//                        getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
//                //可在此执行下一步操作
//                String paypass = ppet.getPwdText();
//                showToast(paypass);
//                cartPresenter.createOrderByGc(ShopCartPayActivity.this, gc_ids, payType, address_id + "", DataType.cart.createOrderByGc.toString());
//            }
//        });
//        //延迟弹起软键盘，使PayPwdEditText获取焦点
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ppet.setFocus();
//            }
//        }, 100);
//    }

    private void wxPay(String appId, String partnerId, String prepayId,
                       String nonceStr, String timeStamp, String sign) {
        JPay.getIntance(this).toWxPay(appId, partnerId, prepayId,
                nonceStr, timeStamp, sign, new JPay.JPayListener() {
                    @Override
                    public void onPaySuccess() {
                        showToast("支付成功");
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("id", order_id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPayError(int error_code, String message) {
                        showToast("支付失败");
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("id", order_id);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onPayCancel() {
                        showToast("取消支付");
                        Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                        intent.putExtra("id", order_id);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void aliPay(String orderInfo) { // 支付宝支付
        JPay.getIntance(this).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                showToast("支付成功");
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onPayError(int error_code, String message) {
                showToast("支付失败");
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            }

            @Override
            public void onPayCancel() {
                showToast("取消支付");
                Intent intent = new Intent(getContext(), OrderDetailActivity.class);
                intent.putExtra("id", order_id);
                startActivity(intent);
                finish();
            }
        });
    }
}
