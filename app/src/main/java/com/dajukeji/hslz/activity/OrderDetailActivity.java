package com.dajukeji.hslz.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.refund.ApplyAfterSaleActivity;
import com.dajukeji.hslz.activity.mine.refund.GoodsAfterSaleActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.domain.javaBean.OrderLogisticsMessageBean;
import com.dajukeji.hslz.domain.order.OrderDetailBean;
import com.dajukeji.hslz.event.OrderChangeEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.im.activity.ChatActivity;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.network.presenter.MyOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.OrderDialog;
import com.dajukeji.hslz.view.OrderLogisticsPopWindow;
import com.tencent.TIMConversationType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 订单详情页
 */
//PS: 微信@EVALA 负责订单详情接口
public class OrderDetailActivity extends HttpBaseActivity {
    private MyOrderPresenter myOrderPresenter;//从服务端获取商品数据的工具类
    private MessagePresenter messagePresenter;//
    private LinearLayout ll_order_detail;//
    private ScrollView sv_order;
    private TextView tv_order_status, tv_auto_des; //订单状态
    private LinearLayout ll_accept; // 最新状态
    private View vw_accept;
    private TextView tv_accept_message, tv_accept_time; // 最新信息，最新时间
    private TextView tv_receiver, tv_mobile, tv_address; //收货人 ，电话 ，时间
    private RecyclerView rv_order_detail_goods; // 订单详情产品列表
    private TextView tv_ship_price, tv_cheap_amount, tv_total_price; // 邮费 ，省券优惠 ， 总价
    private TextView tv_real_pay; //实付款
    private TextView tv_back_integration; // 返省券
    private TextView tv_order_sn, tv_create_time; //订单号, 创建时间
    private LinearLayout ll_pay_time; // 支付时间
    private TextView tv_pay_time;// 支付时间
    private LinearLayout ll_ship_time; // 发货时间
    private TextView tv_ship_time;// 发货时间
    private LinearLayout ll_receive_time; // 确认收货时间
    private TextView tv_receive_time;// 确认收货时间
    private TextView tv_copy_order; // 复制订单

    private LinearLayout ll_order_no_pay, ll_order_already_pay; //未付款， 已付款
    private LinearLayout ll_cancel_order, ll_no_contact_service, ll_immediate_payment;// 取消订单 联系客服 立即支付
    private LinearLayout ll_contact_service, ll_look_logistics, ll_sure_recipient;// 联系客服 查看物流 确认收货
    private LinearLayout ll_order_evaluation; //订单评价
    private String storename;
    private ClipboardManager mClipboardManager; //复制粘贴
    //剪切板Data对象
    private ClipData mClipData;

    private String goods_id_webActivity = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myOrderPresenter = new MyOrderPresenter(this);
        messagePresenter = new MessagePresenter(this);
        showDialogLoading();
        goods_id_webActivity = getIntent().getStringExtra("goods_id_webActivity");
        if (goods_id_webActivity == null){
            goods_id_webActivity = "";//兑换专区的更新
        }

        Log.d("nihao",goods_id_webActivity+" wocaonidaye");

        myOrderPresenter.getOrderDetail(getContext(), SPUtil.getPrefString("token", ""), getIntent().getStringExtra("id"), DataType.myOrder.orderDetail.toString());
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token", ""), DataType.message.notWriteNo.toString());
        mClipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE); //初始化复制粘贴
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_order_detail);
        setTitleBar(R.string.order_detail, true, 0, 0);
        ll_order_detail = (LinearLayout) findViewById(R.id.ll_order_detail);
        sv_order = (ScrollView) findViewById(R.id.sv_order);
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_auto_des = (TextView) findViewById(R.id.tv_auto_des);
        ll_accept = (LinearLayout) findViewById(R.id.ll_accept);
        vw_accept = findViewById(R.id.vw_accept);
        tv_accept_message = (TextView) findViewById(R.id.tv_accept_message);
        tv_accept_time = (TextView) findViewById(R.id.tv_accept_time);
        tv_receiver = (TextView) findViewById(R.id.tv_receiver);
        tv_mobile = (TextView) findViewById(R.id.tv_mobile);
        tv_address = (TextView) findViewById(R.id.tv_address);
        rv_order_detail_goods = (RecyclerView) findViewById(R.id.rv_order_detail_goods);
        tv_ship_price = (TextView) findViewById(R.id.tv_ship_price);
        tv_cheap_amount = (TextView) findViewById(R.id.tv_cheap_amount);
        tv_total_price = (TextView) findViewById(R.id.tv_total_price);
        tv_real_pay = (TextView) findViewById(R.id.tv_real_pay);
        tv_back_integration = (TextView) findViewById(R.id.tv_back_integration);
        tv_order_sn = (TextView) findViewById(R.id.tv_order_sn);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        ll_pay_time = (LinearLayout) findViewById(R.id.ll_pay_time);
        tv_pay_time = (TextView) findViewById(R.id.tv_pay_time);
        ll_ship_time = (LinearLayout) findViewById(R.id.ll_ship_time);
        tv_ship_time = (TextView) findViewById(R.id.tv_ship_time);
        ll_receive_time = (LinearLayout) findViewById(R.id.ll_receive_time);
        tv_receive_time = (TextView) findViewById(R.id.tv_receive_time);
        tv_copy_order = (TextView) findViewById(R.id.tv_copy_order);
        ll_order_no_pay = (LinearLayout) findViewById(R.id.ll_order_no_pay);
        ll_order_already_pay = (LinearLayout) findViewById(R.id.ll_order_already_pay);
        ll_cancel_order = (LinearLayout) findViewById(R.id.ll_cancel_order);
        ll_no_contact_service = (LinearLayout) findViewById(R.id.ll_no_contact_service);

        ll_immediate_payment = (LinearLayout) findViewById(R.id.ll_immediate_payment);
        ll_contact_service = (LinearLayout) findViewById(R.id.ll_contact_service);
        ll_look_logistics = (LinearLayout) findViewById(R.id.ll_look_logistics);
        ll_sure_recipient = (LinearLayout) findViewById(R.id.ll_sure_recipient);
        ll_order_evaluation = (LinearLayout) findViewById(R.id.ll_order_evaluation);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.myOrder.orderDetail.toString())) {
            hideDialogLoading();
            final OrderDetailBean order = (OrderDetailBean) object;
            tv_order_status.setText(order.getContent().getOrder_status_des());
            storename = order.getContent().getStore_name();
            if (order.getContent().getAuto_des() != null && !order.getContent().getAuto_des().equals("")) {
                tv_auto_des.setText(order.getContent().getAuto_des());
            } else {
                tv_auto_des.setVisibility(View.GONE);
            }
            if (order.getContent().getOrder_status() == 30) { // 当订单状态为30才有最新信息
                tv_accept_message.setText(order.getContent().getAccept_station());
                tv_accept_time.setText(order.getContent().getAccept_time());
            } else {
                ll_accept.setVisibility(View.GONE);
                vw_accept.setVisibility(View.GONE);
            }
            tv_receiver.setText(order.getContent().getReceiver());
            tv_mobile.setText(order.getContent().getMobile());
            tv_address.setText("收货地址: " + order.getContent().getAddress());
            //兑换专区的更新
            if (!goods_id_webActivity.equals("")){
                tv_ship_price.setText("额度" + String.format("%.2f", order.getContent().getShip_price()));
                tv_cheap_amount.setText("额度" + String.format("%.2f", order.getContent().getCheap_amount()));
                tv_total_price.setText("额度" + String.format("%.2f", order.getContent().getTotal_price()));
                tv_real_pay.setText("额度" + String.format("%.2f", order.getContent().getReal_pay()));
            }else {
                tv_ship_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", order.getContent().getShip_price()));
                tv_cheap_amount.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", order.getContent().getCheap_amount()));
                tv_total_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", order.getContent().getTotal_price()));
                tv_real_pay.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", order.getContent().getReal_pay()));
            }

            tv_back_integration.setText(order.getContent().getBack_integration() + "");
            tv_order_sn.setText(order.getContent().getOrder_sn());
            tv_create_time.setText(order.getContent().getCreate_time());

            tv_copy_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClipData = ClipData.newPlainText("order", order.getContent().getOrder_sn());
                    //把clip对象放在剪贴板中
                    mClipboardManager.setPrimaryClip(mClipData);
                    showToast("复制成功");
                }
            });

            if (order.getContent().getPay_time() != null && !order.getContent().getPay_time().equals("")) { // 支付时间
                tv_pay_time.setText(order.getContent().getPay_time());
            } else {
                ll_pay_time.setVisibility(View.GONE);
            }

            if (order.getContent().getShip_time() != null && !order.getContent().getShip_time().equals("")) { // 发货时间
                tv_ship_time.setText(order.getContent().getShip_time());
            } else {
                ll_ship_time.setVisibility(View.GONE);
            }
            if (order.getContent().getReceive_time() != null && !order.getContent().getReceive_time().equals("")) { // 收货时间
                tv_receive_time.setText(order.getContent().getReceive_time());
            } else {
                ll_receive_time.setVisibility(View.GONE);
            }
            ll_no_contact_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatActivity.navToChat(getContext(), order.getContent().getChat_id(), TIMConversationType.C2C, storename);
                }
            });

            ll_contact_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChatActivity.navToChat(getContext(), order.getContent().getChat_id(), TIMConversationType.C2C, storename);
                }
            });

            initOrderGoods(rv_order_detail_goods, order.getContent()); // 订单产品列表
            if (order.getContent().getOrder_status() == 10 && order.getContent().getPayable() == 1) { // 能否进行待支付操作
                ll_order_no_pay.setVisibility(View.VISIBLE);
                ll_order_detail.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x160));
            } else {
                ll_order_no_pay.setVisibility(View.GONE);
            }
            if (order.getContent().getOrder_status() == 20 || order.getContent().getOrder_status() == 30 || order.getContent().getOrder_status() == 40) { // 进行支付完成操作
                ll_order_already_pay.setVisibility(View.VISIBLE);
                ll_order_detail.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.x160));
            } else {
                ll_order_already_pay.setVisibility(View.GONE);
            }

            ll_cancel_order.setOnClickListener(new View.OnClickListener() { // 取消订单
                @Override
                public void onClick(View v) { // 取消订单
                    OrderDialog orderDialog = new OrderDialog(getContext(), "确认取消订单?", "取消订单后不可恢复", "确认");
                    orderDialog.show();
                    orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                        @Override
                        public void sureClick() {
                            myOrderPresenter.cancelOrder(getContext(), SPUtil.getPrefString("token", ""), Long.parseLong(getIntent().getStringExtra("id")), DataType.myOrder.cancelOrder.toString());
                        }
                    });
                }
            });

            ll_sure_recipient.setOnClickListener(new View.OnClickListener() { // 确认收货
                @Override
                public void onClick(View v) { // 确认收货
                    OrderDialog orderDialog = new OrderDialog(getContext(), "", "是否确认收货", "确认");
                    orderDialog.show();
                    orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                        @Override
                        public void sureClick() {
                            showDialogLoading();
                            myOrderPresenter.orderConfirmReceive(getContext(), SPUtil.getPrefString("token", ""), getIntent().getStringExtra("id"), DataType.myOrder.orderConfirmReceive.toString());
                        }
                    });
                }
            });

            if (order.getContent().getOrder_status() == 40) {
                ll_order_evaluation.setVisibility(View.VISIBLE);
                ll_sure_recipient.setVisibility(View.GONE);
            } else {
                ll_sure_recipient.setVisibility(View.VISIBLE);
                ll_order_evaluation.setVisibility(View.GONE);
            }

            ll_look_logistics.setOnClickListener(new View.OnClickListener() { // 查看物流
                @Override
                public void onClick(View v) { // 查看物流
                    showDialogLoading();
                    myOrderPresenter.orderLogistics(getContext(), SPUtil.getPrefString("token", ""), getIntent().getStringExtra("id"), DataType.myOrder.logistics.toString());
                }
            });
            ll_order_evaluation.setOnClickListener(new View.OnClickListener() { // 去评价
                @Override
                public void onClick(View v) {
                    Intent evaIntent = new Intent(getContext(), OrderEvaluateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("id", Long.parseLong(getIntent().getStringExtra("id")));
                    evaIntent.putExtras(bundle);
                    startActivity(evaIntent);
                    finish();
                }
            });
            ll_immediate_payment.setOnClickListener(new View.OnClickListener() { // 立即付款
                @Override
                public void onClick(View v) {
                    Intent evaIntent = new Intent(getContext(), WaitOrderPayActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putLong("order_id", Long.parseLong(getIntent().getStringExtra("id")));
                    evaIntent.putExtras(bundle);
                    startActivity(evaIntent);
                    finish();
                }
            });
//            sv_order.setVisibility(View.VISIBLE);
        } else if (contentType.equals(DataType.myOrder.orderConfirmReceive.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            EventBus.getDefault().post(new OrderChangeEvent("change"));
            if (sucess.equals("sucess")) {
                showToast("确认成功");
                finish();
            }
        } else if (contentType.equals(DataType.myOrder.logistics.toString())) {
            hideDialogLoading();
            OrderLogisticsMessageBean logisticsMessageBean = (OrderLogisticsMessageBean) object;
            OrderLogisticsPopWindow logisticsPopWindow = new OrderLogisticsPopWindow(OrderDetailActivity.this, logisticsMessageBean);
            logisticsPopWindow.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
        } else if (contentType.equals(DataType.myOrder.cancelOrder.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            EventBus.getDefault().post(new OrderChangeEvent("change"));
            if (sucess.equals("sucess")) {
                showToast("取消成功");
                finish();
            }
        } else if (contentType.equals(DataType.message.notWriteNo.toString())) {
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.GONE);
//            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    //初始化订单产品列表
    private void initOrderGoods(RecyclerView rv_order_detail_goods, final OrderDetailBean.ContentBean datas) {
        //若购买的所有商品都是产品券，那么不显示地址
        try {
            boolean isAllVoucher = true;//购买的商品是否都是产品券
            for (OrderDetailBean.ContentBean.GoodsListBean goodsListBean : datas.getGoodsList()) {
                if (!goodsListBean.isSecurities())
                    isAllVoucher = false;
            }
            if (isAllVoucher)
                findViewById(R.id.v_address).setVisibility(View.GONE);
        } catch (Exception e) {
            //不行就不行吧╮(╯_╰)╭
        }

        rv_order_detail_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        final BaseRecyclerAdapter<OrderDetailBean.ContentBean.GoodsListBean> list = new BaseRecyclerAdapter<OrderDetailBean.ContentBean.GoodsListBean>(getContext(), null, R.layout.item_order_detail_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, final OrderDetailBean.ContentBean.GoodsListBean data, int position, boolean isScrolling) {
                ImageView iv_goods = holder.getView(R.id.item_order_list_img);
                int width = getResources().getDimensionPixelSize(R.dimen.x260);
                int height = getResources().getDimensionPixelSize(R.dimen.y260);
                GlideEngine.loadThumbnails(getContext().getApplicationContext(), width, height, R.drawable.goods, iv_goods, data.getPhoto()); //产品图片
                holder.setText(R.id.item_order_list_name, data.getGoods_name()); // 产品名称
                holder.setText(R.id.item_order_list_good_sku, (data.isSecurities() ? "产品券 " : "产品 ") + data.getSpec_info()); //产品规格
                //配合兑换专区的更新
                if(!goods_id_webActivity.equals("")){
                    holder.setText(R.id.item_order_list_present_price, "额度" + String.format("%.2f", data.getPrice())); // 现价
                }else {
                    holder.setText(R.id.item_order_list_present_price, getResources().getString(R.string.rmb_symbol) + String.format("%.2f", data.getPrice())); // 现价
                }


                holder.setText(R.id.tv_goods_count, "x " + data.getCount());
                TextView item_order_list_original_price = holder.getView(R.id.item_order_list_original_price);
                if (!goods_id_webActivity.equals("")){
                    item_order_list_original_price.setText("额度" + String.format("%.2f", data.getOriginal_price()));// 原价
                }else {
                    item_order_list_original_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", data.getOriginal_price()));// 原价
                }

                item_order_list_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                //申请售后按钮出现还是不出现，蛀牙取决于data中的数据，需要查看一下接口
                //refund_type : 2 退款类型，0为不能退款退货，1为能退款，2为能退货...
                //可以根据tv_order_status来判断是否显示退货按钮
                //售后
                TextView tv_can_refundable = holder.getView(R.id.tv_can_refundable);//申请退货按钮
//                if(!tv_order_status.getText().equals("待付款")){
//                    tv_can_refundable.setVisibility(View.VISIBLE);
//                }
                if (data.getRefund_type() == 1) {
                    tv_can_refundable.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) { //退款
                            Intent intent = new Intent(getContext(), ApplyAfterSaleActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putLong("gc_id", data.getGc_id());
                            if (!goods_id_webActivity.equals("")){//兑换专区的更新
                                intent.putExtra("goods_id_webActivity",goods_id_webActivity);
                            }
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else if (data.getRefund_type() == 2) { //退货
                    tv_can_refundable.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), GoodsAfterSaleActivity.class);
                            Bundle bundle = new Bundle();
                            if (!goods_id_webActivity.equals("")){//兑换专区的更新
                                intent.putExtra("goods_id_webActivity",goods_id_webActivity);
                            }
                            bundle.putLong("gc_id", data.getGc_id());
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else if (data.getRefund_type() == 0) {
                    tv_can_refundable.setVisibility(View.GONE);
                    //如果需要在评价里面也加上申请售后按钮，那么“代付款”页面的的申请售后按钮也会出现
                }
            }
        };
        list.setData(datas.getGoodsList());
        rv_order_detail_goods.setAdapter(list);
        list.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<OrderDetailBean.ContentBean.GoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, OrderDetailBean.ContentBean.GoodsListBean data, int position) {
                Intent intent = new Intent();
                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                    intent.setClass(getContext(), ComparePriceGoodDetailActivity.class);

                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                    intent.setClass(getContext(), SubsidyGoodDetailActivity.class);

                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
                    intent.putExtra(Constants.is_brand_good, true);

                } else {
                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
                }
                intent.putExtra(Constants.goods_id, (int) data.getGoods_id());

                if (!goods_id_webActivity.equals("")){
                    intent.putExtra("goods_id_webActivity",data.getGoods_id()+"");
                }

                startActivity(intent);
            }
        });
        list.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if (event.message.equals("message")) {
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token", ""), DataType.message.notWriteNo.toString());
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
