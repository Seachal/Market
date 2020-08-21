package com.dajukeji.hslz.fragment.mine.refund;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.RefundOrderDetailActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.activity.mine.refund.GoodsAfterSaleWaybillActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.order.RefundOrderBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.TokenUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.OrderDialog;

/**
 * Created by cdr on 2017/11/29.
 * 售后申请、售后查询
 * 退款售后页面下的商品Fragment
 */
@SuppressLint("ValidFragment")
public class RefundFragment extends HttpBaseFragment {

    private XRecyclerView xRecyclerView;
    private RefundOrderPresenter refundOrderPresenter;
    private BaseRecyclerAdapter<RefundOrderBean.ContentBean.CustomerServiceListBean> recyclerAdapter;
    private LinearLayout ll_empty_refund;
    private TextView tv_refund_empty;

    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    private String refundType = ""; //all为全部，wait为待处理

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.refundType = getArguments().getString("refundType");
        if(rootView == null){
            rootView = inflater.inflate(R.layout.srecycler_after_sale_layout, null);
        }
        refundOrderPresenter = new RefundOrderPresenter(this);
        initView(rootView);
        return rootView;
    }

    private void initView(View v){
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        ll_empty_refund = (LinearLayout) v.findViewById(R.id.ll_empty_refund);
        tv_refund_empty = (TextView) v.findViewById(R.id.tv_refund_empty);
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
        recyclerAdapter = new BaseRecyclerAdapter<RefundOrderBean.ContentBean.CustomerServiceListBean>(getContext(), null, R.layout.item_refund_order_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, final RefundOrderBean.ContentBean.CustomerServiceListBean data, int position, boolean isScrolling) {
                holder.getView(R.id.ll_into_store).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), BrandStoreDetailActivity.class);
                        intent.putExtra("brand_id", data.getStore_id());
                        startActivity(intent);
                    }
                });
                holder.setText(R.id.tv_store_name,data.getStore_name()); // 店铺名称
                holder.setText(R.id.tv_goods_count,"共"+data.getNumber()+"件"); // 产品件数
                initOrderGoods(holder,data);


                TextView item_order_list_waybill =  holder.getView(R.id.item_order_list_waybill); // 填写运单号
                TextView item_order_list_customer =  holder.getView(R.id.item_order_list_customer); //联系客服
                TextView orderDetails = holder.getView(R.id.item_order_list_toDetails); //  查看详情
                TextView item_order_list_delete =  holder.getView(R.id.item_order_list_delete); // 删除订单
                if(data.getService()==0){ //退货
                    switch (data.getReturn_status()){
                        case 0: //申请退货
                            item_order_list_delete.setVisibility(View.GONE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"申请退货中"); // 订单状态
                            break;
                        case 1://同意退货
                            item_order_list_delete.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.GONE);
                            if(data.getLogistics()==0){
                                item_order_list_waybill.setVisibility(View.VISIBLE);
                                holder.setText(R.id.tv_order_status,"待寄回产品"); // 订单状态
                            }else {
                                item_order_list_waybill.setVisibility(View.GONE);
                                holder.setText(R.id.tv_order_status,"寄回产品中"); // 订单状态
                            }
                            orderDetails.setVisibility(View.VISIBLE);

                            break;
                        case 2://确认收货
                            item_order_list_delete.setVisibility(View.VISIBLE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"退货成功"); // 订单状态
                            break;
                        case 3://拒绝退货
                            item_order_list_delete.setVisibility(View.VISIBLE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.VISIBLE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"退货申请失败"); // 订单状态
                            break;
                    }
                }else if(data.getService()==1){ //退款
                    switch (data.getReturn_status()){
                        case 0: //申请退款
                            item_order_list_delete.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.GONE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"申请退款中"); // 订单状态
                            break;
                        case 1://同意退款（成功）
                            item_order_list_delete.setVisibility(View.VISIBLE);
                            item_order_list_customer.setVisibility(View.GONE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"退款成功"); // 订单状态
                            break;
                        case 2://同意退款（成功）
                            item_order_list_delete.setVisibility(View.VISIBLE);
                            item_order_list_customer.setVisibility(View.GONE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"退款成功"); // 订单状态
                            break;
                        case 3://拒绝退款
                            item_order_list_delete.setVisibility(View.VISIBLE);
                            item_order_list_waybill.setVisibility(View.GONE);
                            item_order_list_customer.setVisibility(View.VISIBLE);
                            orderDetails.setVisibility(View.VISIBLE);
                            holder.setText(R.id.tv_order_status,"退款申请失败"); // 订单状态
                            break;
                    }
                }
                item_order_list_waybill.setOnClickListener(new View.OnClickListener() { // 填写运单
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), GoodsAfterSaleWaybillActivity.class);
                        Bundle bundle =new Bundle();
                        bundle.putString("gr_id",data.getGr_id()+"");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                orderDetails.setOnClickListener(new View.OnClickListener() { // 查看订单
                    @Override
                    public void onClick(View v) {//查看订单详情
                        Intent intent = new Intent(getContext(), RefundOrderDetailActivity.class);
                        Bundle bundle =new Bundle();
                        bundle.putString("gr_id",data.getGr_id()+"");
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                item_order_list_customer.setOnClickListener(new View.OnClickListener() { // 联系客服
                    @Override
                    public void onClick(View v) {
//                        ChatActivity.navToChat(getContext(), data.getChat_id(), TIMConversationType.C2C);
                        TokenUtil.openChat(getContext(), data.getChat_id(), data.getStore_name());
                    }
                });
                item_order_list_delete.setOnClickListener(new View.OnClickListener() { // 删除订单
                    @Override
                    public void onClick(View v) {
                        OrderDialog orderDialog = new OrderDialog(getContext(),"确认删除?","删除后不可恢复","确认");
                        orderDialog.show();
                        orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                            @Override
                            public void sureClick() {
                                refundOrderPresenter.returnDelete(getContext(), SPUtil.getPrefString("token",""),data.getGr_id()+"",DataType.myRefund.returnDelete.toString());
                            }
                        });
                    }
                });
            }
        };
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int currentPage) {
        showDialogLoading();
        if(refundType.equals("all")){
            refundOrderPresenter.getOrderList(getContext(),currentPage,SPUtil.getPrefString("token",""),DataType.myRefund.orderList.toString());
        }else if(refundType.equals("wait")){
            refundOrderPresenter.getWaitList(getContext(),currentPage,SPUtil.getPrefString("token",""),DataType.myRefund.waitList.toString());
        }
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.myRefund.orderList.toString())) {
            hideDialogLoading();//隐藏加载框
            RefundOrderBean refundOrderBean = (RefundOrderBean) object;
            if(refundOrderBean.getContent().getCustomerServiceList()!=null&&refundOrderBean.getContent().getCustomerServiceList().isEmpty()){
                ll_empty_refund.setVisibility(View.VISIBLE);
                if(refundType.equals("all")){
                    tv_refund_empty.setText("暂无退款售后订单");
                }else if(refundType.equals("wait")){
                    tv_refund_empty.setText("暂无待处理退款售后订单");
                }
                xRecyclerView.setVisibility(View.GONE);
                return;
            }else {
                ll_empty_refund.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.VISIBLE);
            }

            complete();
            if(refundOrderBean.getContent().getCustomerServiceList()!=null){
                recyclerAdapter.setData(refundOrderBean.getContent().getCustomerServiceList());
            }
            pageSize = refundOrderBean.getContent().getPages();
            currentPage++;
        }else if(dataType.equals(DataType.myRefund.waitList.toString())){
            hideDialogLoading();
            RefundOrderBean refundOrderBean = (RefundOrderBean) object;
            complete();
            recyclerAdapter.setData(refundOrderBean.getContent().getCustomerServiceList());
            pageSize = refundOrderBean.getContent().getPages();
            currentPage++;
        }else if(dataType.equals(DataType.myRefund.returnDelete.toString())){
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")){
                showToast("确认成功");
                recyclerAdapter.clear();
                loadList(1);
            }
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    //初始化订单产品列表
    private void initOrderGoods(BaseRecyclerHolder holder,RefundOrderBean.ContentBean.CustomerServiceListBean data){
        final RefundOrderBean.ContentBean.CustomerServiceListBean datas = data;
        RecyclerView rv_order_goods = holder.getView(R.id.rv_order_goods);
        rv_order_goods.setLayoutManager(new LinearLayoutManager(getContext()));
        final BaseRecyclerAdapter<RefundOrderBean.ContentBean.CustomerServiceListBean.GListBean> list = new BaseRecyclerAdapter<RefundOrderBean.ContentBean.CustomerServiceListBean.GListBean>(getContext(), null, R.layout.item_refund_order_content) {
            @Override
            public void convert(BaseRecyclerHolder holder, RefundOrderBean.ContentBean.CustomerServiceListBean.GListBean data, int position, boolean isScrolling) {
                ImageView iv_goods = holder.getView(R.id.item_order_list_img);
                int width =  getResources().getDimensionPixelSize(R.dimen.x260);
                int height =  getResources().getDimensionPixelSize(R.dimen.y260);
                GlideEngine.loadThumbnails(getActivity(),width,height, R.drawable.goods, iv_goods, data.getIcon()); //产品图片
                holder.setText(R.id.item_order_list_name,data.getGoods_name()); // 产品名称
                holder.setText(R.id.item_order_list_good_sku,data.getSpec_info()); //产品规格
                holder.setText(R.id.item_order_list_present_price,getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getNow_price())); // 现价
                holder.setText(R.id.item_order_list_num,"*"+data.getCount());
//                不显示七天退货
//                if(data.getReturn_days()>0){
//                    holder.setText(R.id.tv_order_label,data.getReturn_days()+"天退换");
//                }else{
//                    holder.getView(R.id.tv_order_label).setVisibility(View.INVISIBLE);
//                }
            }
        };
        list.setData(data.getGList());
        rv_order_goods.setAdapter(list);
        list.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<RefundOrderBean.ContentBean.CustomerServiceListBean.GListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, RefundOrderBean.ContentBean.CustomerServiceListBean.GListBean data, int position) {
                Intent intent = new Intent(getContext(), RefundOrderDetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("gr_id",datas.getGr_id()+"");
                intent.putExtras(bundle);
                startActivity(intent);
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
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }


}
