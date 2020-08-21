package com.dajukeji.hslz.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.dajukeji.hslz.activity.MyCouponGoodsActivity;
import com.dajukeji.hslz.activity.mine.order.OrderActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.AwardGoodsBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AwardGoodsPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;

/**
 * 我的抽奖货品列表
 */
@SuppressLint("ValidFragment")
public  class MyAwardGoodsFragment extends HttpBaseFragment {

    private AwardGoodsPresenter awardGoodsPresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    public BaseRecyclerAdapter<AwardGoodsBean.ContentBean.AwardListBean> recyclerAdapter;

    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    public long id; // 当前选择的产品ID

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.xrecycler_order_layout, container, false);
        }
        awardGoodsPresenter = new AwardGoodsPresenter(this);
        initView(rootView);
        return rootView;
    }


    protected void initView(View v) {
        ll_empty_order = (LinearLayout) v.findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) v.findViewById(R.id.tv_order_empty);
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        recyclerAdapter = new BaseRecyclerAdapter<AwardGoodsBean.ContentBean.AwardListBean>(getContext(), null, R.layout.item_award_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, final AwardGoodsBean.ContentBean.AwardListBean data, int position, boolean isScrolling) {
                ImageView pict_url = holder.getView(R.id.pict_url);
                int size =  getResources().getDimensionPixelSize(R.dimen.x400);
                GlideEngine.loadThumbnail(getContext().getApplicationContext(),size, R.drawable.goods, pict_url, data.getIcon());
                holder.setText(R.id.tv_award_goods_name,data.getGoods_name());
                holder.setText(R.id.tv_award_price,getResources().getString(R.string.rmb_symbol)+"0.00");
                holder.setText(R.id.tv_award_goods_price,getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getPrice()));
                TextView tv_award_record =  holder.getView(R.id.tv_award_record); // 领奖时间
                TextView tv_award_already = holder.getView(R.id.tv_award_already);
                TextView tv_award_receive = holder.getView(R.id.tv_award_receive);
                if(data.getStatus().equals("0")){ // 未领奖
                    tv_award_record.setVisibility(View.INVISIBLE);
                    tv_award_already.setVisibility(View.GONE);
                    tv_award_receive.setVisibility(View.VISIBLE);
                }else if(data.getStatus().equals("1")){ //已领奖
                    tv_award_record.setVisibility(View.VISIBLE);
                    if(data.getTime()!=null && !data.equals("")){
                        tv_award_record.setText(data.getTime()+" 交易成功");
                    }
                    tv_award_already.setVisibility(View.VISIBLE);
                    tv_award_receive.setVisibility(View.GONE);
                }

                tv_award_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyCouponGoodsActivity context =(MyCouponGoodsActivity) getActivity();
                        context.id = data.getId();
                        MyAwardGoodsFragment.this.id = data.getId();
                        awardGoodsPresenter.getDefaultAddress(getContext(), SPUtil.getPrefString("token",""),DataType.award.getDefaultAddress.toString());
                    }
                });

                tv_award_already.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToast("请在我的订单查看");
                    }
                });
            }
        };
        xRecyclerView.setAdapter(recyclerAdapter);
    }


    public void loadList(int page) {
        awardGoodsPresenter.getAwardGoodsList(getContext(), SPUtil.getPrefString("token",""),page,DataType.award.awardGoods.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.award.awardGoods.toString())){
            AwardGoodsBean awardGoodsBean = (AwardGoodsBean) object;
            complete();
            if(awardGoodsBean.getContent().getAwardList().isEmpty()){
                tv_order_empty.setText("暂无中奖记录");
                ll_empty_order.setVisibility(View.VISIBLE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(awardGoodsBean.getContent().getAwardList());
                pageSize = awardGoodsBean.getContent().getPages();
                currentPage++;
            }
        }else if(dataType.equals(DataType.award.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(getActivity(),userAddressBean.getContent(),id,"","award");
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long id, String sku, long addressId,String type) {
                    awardGoodsPresenter.createAwardOrder(getContext(),id,addressId,SPUtil.getPrefString("token",""),DataType.award.createAwardOrder.toString());
                }
            });
        }else if(dataType.equals(DataType.award.createAwardOrder.toString())){
            hideDialogLoading();
            showToast("兑换成功");
            recyclerAdapter.clear();
            loadList(1);
            startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("status", 2));
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }
}
