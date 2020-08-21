package com.dajukeji.hslz.fragment.FreeOrder;

import android.annotation.SuppressLint;
import android.graphics.Paint;
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
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FreeOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;
import com.dajukeji.hslz.view.ShareFreeOrderGoodsPopWindow;
import com.dajukeji.hslz.view.dialog.SkuDialog;

@SuppressLint("ValidFragment")
public  class TodayFreeOrderFragment extends HttpBaseFragment {

    private FreeOrderPresenter freeOrderPresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity> recyclerAdapter;
    private int pageSize;

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        freeOrderPresenter = new FreeOrderPresenter(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView =  inflater.inflate(R.layout.xrecycler_order_layout, container, false);
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
        recyclerAdapter = new BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity>(getContext(), null, R.layout.item_today_free_order) {
            @Override
            public void convert(BaseRecyclerHolder holder, GoodListBean.ContentEntity.GoodsListEntity data, int position, boolean isScrolling) {
                TextView invite_count =  holder.getView(R.id.invite_count);
                TextView  item_title = holder.getView(R.id.item_title);
                TextView tv_current_price = holder.getView(R.id.tv_current_price);
                ImageView pict_url = holder.getView(R.id.pict_url);
                int size =  getResources().getDimensionPixelSize(R.dimen.x400);
                GlideEngine.loadThumbnail(getActivity(),size, R.drawable.goods, pict_url, data.getGoods_main_photo());
                item_title.setText(data.getGoods_name());
                invite_count.setText("需要"+data.getInvite_count()+"助力");
                tv_current_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getGoods_current_price()));
                tv_current_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodListBean.ContentEntity.GoodsListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder,GoodListBean.ContentEntity.GoodsListEntity data, int position) {
                showDialogLoading();
                freeOrderPresenter.getGoodDetail(getContext(),data.getId(),DataType.freeOrder.getGoodDetail.toString()); // 获取产品详情
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
        freeOrderPresenter.getFreeGoodsList(getContext(), page,"free" , DataType.freeOrder.todayFree.toString());
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.freeOrder.todayFree.toString())) {
            hideDialogLoading();
            GoodListBean goodListBean = (GoodListBean) object;
            complete();
            if(goodListBean.getContent().getGoodsList().isEmpty()){
                ll_empty_order.setVisibility(View.VISIBLE);
                tv_order_empty.setText("暂无免单产品");
                im_empty.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(goodListBean.getContent().getGoodsList());
                pageSize = goodListBean.getContent().getPages();
                currentPage++;
            }
        }else if(dataType.equals(DataType.freeOrder.getGoodDetail.toString())){
            hideDialogLoading();
            GoodDetailsBean goodDetailsBean = (GoodDetailsBean) object;
            SkuDialog dialog = new SkuDialog(getActivity(),goodDetailsBean.getContent()).builder();
            dialog.show();
            dialog.setOnSelectListener(new SkuDialog.onSelectListener() {
                @Override
                public void onComplete(long goodId, int count, String sku, String goodPrice) {
                    FreeOrderActivity context =(FreeOrderActivity) getActivity();
                    context.goods_id = goodId;
                    context.sku =sku;
                    context.type = "freeOrder";
                    showDialogLoading();
                    freeOrderPresenter.getDefaultAddress(getContext(), SPUtil.getPrefString("token",""),DataType.freeOrder.getDefaultAddress.toString());
                }
            });
        }else if(dataType.equals(DataType.freeOrder.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            FreeOrderActivity context =(FreeOrderActivity) getActivity();
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(getActivity(),userAddressBean.getContent(),context.goods_id,context.sku,"freeOrder");
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long goodId, String sku, long addressId,String type) {
                    showDialogLoading();
                    freeOrderPresenter.createFreeOrder(getContext(),goodId,sku,addressId,SPUtil.getPrefString("token",""),DataType.freeOrder.createFree.toString());
                }
            });
        }else if(dataType.equals(DataType.freeOrder.createFree.toString())){
            hideDialogLoading();
            FreeOrderGoods freeOrderGoods = (FreeOrderGoods) object;
            ShareFreeOrderGoodsPopWindow shareFreeOrderGoodsPopWindow = new ShareFreeOrderGoodsPopWindow(getActivity(),freeOrderGoods);
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM,0,0);
        }
    }


    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
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
        OkGo.getInstance().cancelTag(getActivity());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }
}
