package com.dajukeji.hslz.fragment.mine.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.OrderDetailActivity;
import com.dajukeji.hslz.activity.OrderEvaluateActivity;
import com.dajukeji.hslz.activity.WaitOrderPayActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.activity.mine.refund.RefundActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.OrderLogisticsMessageBean;
import com.dajukeji.hslz.domain.order.MyOrderBean;
import com.dajukeji.hslz.event.OrderChangeEvent;
import com.dajukeji.hslz.im.activity.ChatActivity;
import com.dajukeji.hslz.network.DataType;
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
 * Created by cdr on 2017/11/28.
 * 我的订单页(内)
 *
 * 我的订单页 面内的 商品标签
 */

@SuppressLint("ValidFragment")
public class OrderFragment extends HttpBaseFragment {

    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private MyOrderPresenter myOrderPresenter;
    private MyOrderBean myOrderBean;
    private BaseRecyclerAdapter<MyOrderBean.ContentBean.OrderListBean> recyclerAdapter;

    private boolean isFirstPage = true;
    private int currentPage = 1;
    private int pageSize = 1;
    private String mStatus = ""; // 订单状态
    private String chat_id = "";


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.xrecycler_order_layout, null);
        }
        this.mStatus = getArguments().getString("mStatus");
        myOrderPresenter = new MyOrderPresenter(this);//创建一个访问服务端数据的类，通过本类来获取具体的订单数据
        EventBus.getDefault().register(this);//注册一个线程广播
        initView(rootView);
        return rootView;
    }


    private void initView(View v){
        ll_empty_order = (LinearLayout) v.findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) v.findViewById(R.id.tv_order_empty);
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                recyclerAdapter.clear();
                loadList(currentPage);
                isFirstPage = true;
            }

            @Override
            public void onLoadMore() {
                if (currentPage > pageSize) {
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });
        recyclerAdapter = new BaseRecyclerAdapter<MyOrderBean.ContentBean.OrderListBean>(getContext(), null, R.layout.item_order_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, final MyOrderBean.ContentBean.OrderListBean data, int position, boolean isScrolling) {
                holder.getView(R.id.ll_into_store).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BrandStoreDetailActivity.class);
                        intent.putExtra("brand_id", data.getStore_id());
                        startActivity(intent);
                    }
                });

                holder.setText(R.id.tv_store_name,data.getStore_name()); // 店铺名称
                holder.setText(R.id.tv_order_status,data.getOrder_status_des()); // 订单状态
                holder.setText(R.id.tv_order_status_second_des,data.getOrder_status_second_des()); //订单二级状态
                holder.setText(R.id.tv_goods_count,"共"+data.getTotal_count()+"件产品"); // 产品件数
                holder.setText(R.id.item_order_list_totalPrice , "实付: "+data.getTotalPrice()); // 产品总价
                if(data.getShip_price()>0){ // 邮费
                    holder.setText(R.id.tv_ship_price,"(邮费:"+data.getShip_price()+")");
                }else {
                    holder.setText(R.id.tv_ship_price,"(免邮费)");
                }
                initOrderGoods(holder,data); //订单产品列表

                LinearLayout llReceived =  holder.getView(R.id.ll_order_list_received); // 待收货
                TextView orderLogistics =  holder.getView(R.id.item_order_list_checkLogistics); //查看物流
                TextView orderConfirmReceive =  holder.getView(R.id.item_order_list_confirmReceive); //确认收货

                TextView item_order_list_waybill =  holder.getView(R.id.item_order_list_waybill); //退款填写运单号

                TextView orderPay =  holder.getView(R.id.item_order_list_toPay); // 去支付
                TextView orderDetails = holder.getView(R.id.item_order_list_toDetails); //  查看详情

                TextView orderAppraise = holder.getView(R.id.item_order_list_toAppraise); // 去评价
                TextView item_order_list_delete =  holder.getView(R.id.item_order_list_delete); // 删除订单
                TextView item_order_list_customer =  holder.getView(R.id.item_order_list_customer); // 联系客服

                switch (data.getOrder_status()){
                    case 0: //已取消
                        if(data.getDeletable()==1){
                            item_order_list_delete.setVisibility(View.VISIBLE);
                        }else{
                            item_order_list_delete.setVisibility(View.GONE);
                        }
                        orderPay.setVisibility(View.GONE);
                        orderDetails.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 10: //待付款
                        if(data.getPayable()==1){
                            orderPay.setVisibility(View.VISIBLE);
                            orderDetails.setVisibility(View.GONE);
                        }else {
                            orderPay.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                        }
                        if(data.getDeletable()==1){
                            item_order_list_delete.setVisibility(View.VISIBLE);
                        }else{
                            item_order_list_delete.setVisibility(View.GONE);
                        }
                        llReceived.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 20: //待发货
                        orderDetails.setVisibility(View.VISIBLE);
                        orderPay.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        item_order_list_delete.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 30: //待收货
                        llReceived.setVisibility(View.VISIBLE);
                        orderPay.setVisibility(View.GONE);
                        orderDetails.setVisibility(View.GONE);
                        item_order_list_delete.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 40: //待评价
                        orderAppraise.setVisibility(View.VISIBLE);
                        orderPay.setVisibility(View.GONE);
                        orderDetails.setVisibility(View.GONE);
                        item_order_list_delete.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 45: //买家退款申请中 只能看退款订单详情
                        orderDetails.setVisibility(View.VISIBLE);
                        llReceived.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        item_order_list_delete.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 46://卖家已同意退款 能填写运单号 能看订单详情
                        if(data.getWrite_logistics_able()==1){
                            item_order_list_waybill.setVisibility(View.VISIBLE);
                        }else {
                            item_order_list_waybill.setVisibility(View.GONE);
                        }
                        orderDetails.setVisibility(View.VISIBLE);
                        llReceived.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        item_order_list_delete.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        break;
                    case 47://买家已完成退款
                        item_order_list_delete.setVisibility(View.VISIBLE);
                        orderDetails.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 48://卖家已拒绝退款
                        item_order_list_customer.setVisibility(View.VISIBLE);
                        if(data.getDeletable()==1){
                            item_order_list_delete.setVisibility(View.VISIBLE);
                        }
                        orderAppraise.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        orderDetails.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 50://已评价
                        if(data.getDeletable()==1){
                            item_order_list_delete.setVisibility(View.VISIBLE);
                        }
                        orderDetails.setVisibility(View.GONE);
                        llReceived.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                    case 60: //已完成
                        if(data.getDeletable()==1){
                            item_order_list_delete.setVisibility(View.VISIBLE);
                        }
                        llReceived.setVisibility(View.GONE);
                        orderDetails.setVisibility(View.GONE);
                        orderPay.setVisibility(View.GONE);
                        orderAppraise.setVisibility(View.GONE);
                        item_order_list_customer.setVisibility(View.GONE);
                        item_order_list_waybill.setVisibility(View.GONE);
                        break;
                }
                //订单详情
                orderDetails.setOnClickListener(new View.OnClickListener() {//查看详情
                    @Override
                    public void onClick(View v) {
                        Intent detailIn =new Intent(getActivity(), OrderDetailActivity.class);
                        detailIn.putExtra("id",data.getOrder_id()+"");
                        if (data.getOrder_type().equals("exchange")){
                            detailIn.putExtra("goods_id_webActivity",data.getOrder_type());//兑换专区的更新
                        }
                        getActivity().startActivity(detailIn);
                    }
                });
                //物流信息
                orderLogistics.setOnClickListener(new View.OnClickListener() { // 查看物流
                    @Override
                    public void onClick(View v) {
                        showDialogLoading();
                        myOrderPresenter.orderLogistics(getContext(), SPUtil.getPrefString("token",""),data.getOrder_id()+"",DataType.myOrder.logistics.toString());
                    }
                });
                //退货
                item_order_list_waybill.setOnClickListener(new View.OnClickListener() {  // 填写运单号
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), RefundActivity.class));
                    }
                });

                orderConfirmReceive.setOnClickListener(new View.OnClickListener() { // 确认收货
                    @Override
                    public void onClick(View v) {
                        OrderDialog orderDialog = new OrderDialog(getContext(),"","是否确认收货","确认");
                        orderDialog.show();
                        orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                            @Override
                            public void sureClick() {
                                showDialogLoading();
                                myOrderPresenter.orderConfirmReceive(getContext(),SPUtil.getPrefString("token",""),data.getOrder_id()+"",DataType.myOrder.orderConfirmReceive.toString());
                            }
                        });

                    }
                });
                orderAppraise.setOnClickListener(new View.OnClickListener() {// 订单评价界面
                    @Override
                    public void onClick(View v) {
                        Intent evaIntent = new Intent(getActivity(), OrderEvaluateActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("id",data.getOrder_id());
                        evaIntent.putExtras(bundle);
                        startActivity(evaIntent);
                    }
                });

                orderPay.setOnClickListener(new View.OnClickListener() { // 去支付
                    @Override
                    public void onClick(View v) {
                        Intent evaIntent = new Intent(getActivity(), WaitOrderPayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("order_id",data.getOrder_id());
                        evaIntent.putExtras(bundle);
                        startActivity(evaIntent);
                    }
                });
                /**
                 * 删除订单
                 * */
                item_order_list_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDialog orderDialog = new OrderDialog(getContext(),"确认删除订单?","删除订单后不可恢复","确认");
                        orderDialog.show();
                        orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                            @Override
                            public void sureClick() {
                                myOrderPresenter.deleteOrder(getContext(),SPUtil.getPrefString("token",""),data.getOrder_id(),DataType.myOrder.delete.toString());
                            }
                        });
                    }
                });
                item_order_list_customer.setOnClickListener(new View.OnClickListener() {  // 联系客服
                    @Override
                    public void onClick(View v) {
                        ChatActivity.navToChat(getContext(), data.getChat_id(), TIMConversationType.C2C, data.getStore_name());
                    }
                });
            }
        };
        /**
         * 从这里进入订单详情页面（订单列表中的每一个商品，中间的预览图和文字简介其实是一个recyclerView）
         */
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<MyOrderBean.ContentBean.OrderListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, MyOrderBean.ContentBean.OrderListBean data, int position) {
                Intent detailIn =new Intent(getActivity(), OrderDetailActivity.class);
                detailIn.putExtra("id",data.getOrder_id()+"");
                if (data.getOrder_type().equals("exchange")){
                    detailIn.putExtra("goods_id_webActivity",data.getOrder_type());//兑换专区的更新
                }
                getActivity().startActivity(detailIn);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(OrderChangeEvent event) {
        if(event.message.equals("change")){
            recyclerAdapter.clear();
            loadList(1);
        }
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        myOrderPresenter.getOrderList(getContext(),page,SPUtil.getPrefString("token",""),mStatus,DataType.myOrder.orderList.toString());
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.myOrder.orderList.toString())) {
            hideDialogLoading();
            myOrderBean = (MyOrderBean) object;
            complete();
            if(myOrderBean.getContent().getOrderList()!=null && myOrderBean.getContent().getOrderList().isEmpty()){
                if(mStatus.equals("wait_for_pay")){
                    tv_order_empty.setText("暂无待付款订单");
                }else if(mStatus.equals("wait_for_send")){
                    tv_order_empty.setText("暂无待发货订单");
                }else if(mStatus.equals("wait_for_receive")){
                    tv_order_empty.setText("暂无待收货订单");
                }else if(mStatus.equals("wait_for_comment")){
                    tv_order_empty.setText("暂无待评价订单");
                }
                ll_empty_order.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(myOrderBean.getContent().getOrderList());
                pageSize = myOrderBean.getContent().getPages();
                currentPage++;
            }
        }else if(dataType.equals(DataType.myOrder.delete.toString())){
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")){
                showToast("删除成功");
                recyclerAdapter.clear();
                loadList(1);
            }
        }else if(dataType.equals(DataType.myOrder.orderConfirmReceive.toString())){
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")){
                showToast("确认成功");
                recyclerAdapter.clear();
                loadList(1);
            }
        }else if(dataType.equals(DataType.myOrder.logistics.toString())){
            hideDialogLoading();
            OrderLogisticsMessageBean logisticsMessageBean = (OrderLogisticsMessageBean) object;
            OrderLogisticsPopWindow logisticsPopWindow = new OrderLogisticsPopWindow(getActivity(),logisticsMessageBean);
            logisticsPopWindow.showAtLocation(getActivity().getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM,0,0);
        }
    }


    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    //初始化订单产品列表
    private void initOrderGoods(BaseRecyclerHolder holder,MyOrderBean.ContentBean.OrderListBean data){
        final MyOrderBean.ContentBean.OrderListBean orderData = data;
        if (orderData.getOrder_type() == null){//修复闪退，当服务器没有传入这个数据的时候，判断为空，会闪退
            orderData.setOrder_type("");
        }

        RecyclerView rv_order_goods = holder.getView(R.id.rv_order_goods);
        rv_order_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        final BaseRecyclerAdapter<MyOrderBean.ContentBean.OrderListBean.GoodsListBean> list = new BaseRecyclerAdapter<MyOrderBean.ContentBean.OrderListBean.GoodsListBean>(getContext(), null, R.layout.item_order_content) {
            @Override
            public void convert(BaseRecyclerHolder holder, MyOrderBean.ContentBean.OrderListBean.GoodsListBean data, int position, boolean isScrolling) {
                ImageView iv_goods = holder.getView(R.id.item_order_list_img);
                int size =  getResources().getDimensionPixelSize(R.dimen.x260);
                GlideEngine.loadThumbnail(getActivity(),size, R.drawable.goods, iv_goods, data.getPhoto()); //产品图片
                holder.setText(R.id.item_order_list_name,data.getGoods_name()); // 产品名称
                holder.setText(R.id.item_order_list_good_sku,data.getSpec_info()); //产品规格
                if (orderData.getOrder_type().equals("exchange")){
                    holder.setText(R.id.item_order_list_present_price,"额度"+String.format("%.2f",data.getPrice())); // 现价
                }else {
                    holder.setText(R.id.item_order_list_present_price,getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getPrice())); // 现价
                }
                holder.setText(R.id.item_order_list_num,"x "+data.getCount());
                TextView item_order_list_original_price = holder.getView(R.id.item_order_list_original_price);
                item_order_list_original_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getOriginal_price()));// 原价
                item_order_list_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                不显示退货
//                if(data.getReturn_days()>0){
//                    holder.setText(R.id.tv_order_label,data.getReturn_days()+"天退换");
//                }else{
//                    holder.getView(R.id.tv_order_label).setVisibility(View.INVISIBLE);
//                }
                holder.setText(R.id.tv_return_status ,data.getReturn_status()); // 	退款状态
            }
        };
        list.setData(data.getGoodsList());
        rv_order_goods.setAdapter(list);
        list.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<MyOrderBean.ContentBean.OrderListBean.GoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, MyOrderBean.ContentBean.OrderListBean.GoodsListBean data, int position) {
                Intent detailIn =new Intent(getActivity(), OrderDetailActivity.class);
                detailIn.putExtra("id",orderData.getOrder_id()+"");
                if (orderData.getOrder_type().equals("exchange")){
                    detailIn.putExtra("goods_id_webActivity",orderData.getOrder_type());//兑换专区的更新
                }
                getActivity().startActivity(detailIn);
            }
        });
        list.notifyDataSetChanged();
    }

    private void complete() {
        hideDialogLoading();
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getActivity());
        EventBus.getDefault().unregister(getActivity());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }
}
