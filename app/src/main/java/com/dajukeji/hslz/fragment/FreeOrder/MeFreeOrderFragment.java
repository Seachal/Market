package com.dajukeji.hslz.fragment.FreeOrder;

import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import com.dajukeji.hslz.activity.mall.FreeOrderActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.freeOrder.FreeOrderGoods;
import com.dajukeji.hslz.domain.freeOrder.InviteeFreeOrderBean;
import com.dajukeji.hslz.domain.freeOrder.MeFreeOrderBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FreeOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.OrderDialog;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;
import com.dajukeji.hslz.view.ShareFreeOrderGoodsPopWindow;

@SuppressLint("ValidFragment")
public  class MeFreeOrderFragment extends HttpBaseFragment {

    private FreeOrderPresenter freeOrderPresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<MeFreeOrderBean.ContentBean.OrderListBean> recyclerAdapter;
    private int pageSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        freeOrderPresenter = new FreeOrderPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.xrecycler_order_layout, container, false);
        }
        initView(rootView);
        return rootView;
    }


    private void initView(View v) {
        ll_empty_order = (LinearLayout) v.findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) v.findViewById(R.id.tv_order_empty);
        im_empty = (ImageView) v.findViewById(R.id.im_empty);
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
        recyclerAdapter = new BaseRecyclerAdapter<MeFreeOrderBean.ContentBean.OrderListBean>(getContext(), null, R.layout.item_me_free_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, final MeFreeOrderBean.ContentBean.OrderListBean data, int position, boolean isScrolling) {
                TextView  tv_end_time = holder.getView(R.id.tv_end_time);
                ImageView pict_url =  holder.getView(R.id.pict_url);
                TextView  item_title =  holder.getView(R.id.item_title);
                TextView lack_number = holder.getView(R.id.lack_number);
                TextView do_free_order = holder.getView(R.id.do_free_order);
                TextView invite_count = holder.getView(R.id.invite_count);

                int size =  getResources().getDimensionPixelSize(R.dimen.x400);
                GlideEngine.loadThumbnail(getActivity(),size, R.drawable.goods, pict_url, data.getGoods_main_photo());
                item_title.setText(data.getGoods_name());
                tv_end_time.setText(data.getInvalid_time());
                lack_number.setText(data.getStatus_description());
                invite_count.setText("需要"+data.getNeed_number()+"助力");
                if(data.getStatus().equals("fail")){
                    if(data.getDeletable()==1){
                        do_free_order.setText("删除");
                    }else {
                        do_free_order.setText("助力失败");
                    }
                }else if(data.getStatus().equals("can_exchange")){
                    do_free_order.setText("能兑换");
                }else if(data.getStatus().equals("finish")){
                    if(data.getDeletable()==1){
                        do_free_order.setText("删除");
                    }else {
                        do_free_order.setText("抢单成功");
                    }
                }else {
                    do_free_order.setText("继续分享");
                }
                if(data.getDeletable()==1){
                    do_free_order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OrderDialog orderDialog = new OrderDialog(getContext(),"确认删除?","删除后不可恢复","确认");
                            orderDialog.show();
                            orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                                @Override
                                public void sureClick() {
                                    freeOrderPresenter.deleteFreeOrder(getContext(), SPUtil.getPrefString("token",""),data.getFree_order_id()+"",DataType.freeOrder.deleteFreeOrder.toString());
                                }
                            });
                        }
                    });
                }
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<MeFreeOrderBean.ContentBean.OrderListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, MeFreeOrderBean.ContentBean.OrderListBean data, int position) {
                if(data.getStatus().equals("being")){ // 继续分享
                    showDialogLoading();
                    freeOrderPresenter.shareFreeOrder(getContext(), SPUtil.getPrefString("token",""),Long.toString(data.getFree_order_id()),DataType.freeOrder.shareFreeOrder.toString());
                }else if(data.getStatus().equals("can_exchange")){ // 能兑换
                    showDialogLoading();
                    FreeOrderActivity context =(FreeOrderActivity) getActivity();
                    context.type = "invitee";
                    context.goods_id = data.getFree_order_id();
                    freeOrderPresenter.getDefaultAddress(getContext(),SPUtil.getPrefString("token",""),DataType.freeOrder.getDefaultAddress.toString());
                }
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        freeOrderPresenter.getMeFreeList(getContext(), page,SPUtil.getPrefString("token","") ,DataType.freeOrder.meFree.toString());
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.freeOrder.meFree.toString())) {
            hideDialogLoading();
            MeFreeOrderBean meFreeOrderBean = (MeFreeOrderBean) object;
            complete();
            if(meFreeOrderBean.getContent().getOrderList().isEmpty()){
                ll_empty_order.setVisibility(View.VISIBLE);
                tv_order_empty.setText("您暂无免单订单");
                im_empty.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(meFreeOrderBean.getContent().getOrderList());
                pageSize = meFreeOrderBean.getContent().getPages();
                currentPage++;
            }
        }else if(dataType.equals(DataType.freeOrder.shareFreeOrder.toString())){
            hideDialogLoading();
            FreeOrderGoods freeOrderGoods = (FreeOrderGoods) object;
            ShareFreeOrderGoodsPopWindow shareFreeOrderGoodsPopWindow = new ShareFreeOrderGoodsPopWindow(getActivity(),freeOrderGoods);
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
        }else if(dataType.equals(DataType.freeOrder.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            FreeOrderActivity context =(FreeOrderActivity) getActivity();
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(getActivity(),userAddressBean.getContent(),context.goods_id,context.sku,"invitee");
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long id, String sku, long addressId,String type) {
                    if(type.equals("invitee")){
                        freeOrderPresenter.createInviteeFreeOrder(getContext(),id,addressId,SPUtil.getPrefString("token",""),DataType.freeOrder.createInvitee.toString());
                    }
                }

            });
        }else if(dataType.equals(DataType.freeOrder.createInvitee.toString())){
            hideDialogLoading();
            InviteeFreeOrderBean inviteeFreeOrderBean = (InviteeFreeOrderBean) object;
            if (inviteeFreeOrderBean.getStatus().equals("0")){
                showToast("兑换成功");
                recyclerAdapter.clear();
                loadList(1);
            }
        }else if(dataType.equals(DataType.freeOrder.deleteFreeOrder.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")){
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
