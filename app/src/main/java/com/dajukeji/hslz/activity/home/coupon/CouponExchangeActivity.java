package com.dajukeji.hslz.activity.home.coupon;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.order.OrderActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponExchangePresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;
import com.dajukeji.hslz.view.dialog.SkuDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 省券换购
 */

public class CouponExchangeActivity extends HttpBaseActivity {

    private CouponExchangePresenter couponExchangePresenter;
    private MessagePresenter messagePresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;

    private BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity> recyclerAdapter;

    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    public long goods_id; // 当前选择的产品ID
    public String sku; // 当前选择的产品规格
    private int count; // 数量

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_exchange);
        setTitleBar(R.string.text_home_coupon_exchange,true,0,0);
        couponExchangePresenter = new CouponExchangePresenter(this);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        ll_empty_order = (LinearLayout) findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) findViewById(R.id.tv_order_empty);
        im_empty = (ImageView) findViewById(R.id.im_empty);
        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        recyclerAdapter = new BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity>(getContext(), null, R.layout.item_coupon_exchange) {
            @Override
            public void convert(BaseRecyclerHolder holder, GoodListBean.ContentEntity.GoodsListEntity data, int position, boolean isScrolling) {
                ImageView pict_url = holder.getView(R.id.pict_url);
                int size =  getResources().getDimensionPixelSize(R.dimen.x441);
                GlideEngine.loadThumbnail(getContext().getApplicationContext(),size, R.drawable.goods, pict_url, data.getGoods_main_photo());
                holder.setText(R.id.item_coupon_exchange_goodName,data.getGoods_name());
                holder.setText(R.id.item_coupon_exchange_price,data.getIntegration()+"");
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodListBean.ContentEntity.GoodsListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, GoodListBean.ContentEntity.GoodsListEntity data, int position) {
                couponExchangePresenter.getGoodDetail(getContext(),data.getId(),DataType.freeOrder.getGoodDetail.toString()); // 获取产品详情
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList(currentPage);
    }


    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        couponExchangePresenter.getGoodsList(getContext(),page,"integral", DataType.mall.integral.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.mall.integral.toString())){
            hideDialogLoading();
            GoodListBean goodListBean = (GoodListBean) object;
            complete();
            if(goodListBean.getContent().getGoodsList().isEmpty()){
                ll_empty_order.setVisibility(View.VISIBLE);
                tv_order_empty.setText("暂无省券换购产品");
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
            SkuDialog dialog = new SkuDialog(this,goodDetailsBean.getContent()).builder();
            dialog.show();
            dialog.setOnSelectListener(new SkuDialog.onSelectListener() {
                @Override
                public void onComplete(long goodId, int count, String sku, String goodPrice) {
                    CouponExchangeActivity.this.goods_id = goodId;
                    CouponExchangeActivity.this.sku =sku;
                    CouponExchangeActivity.this.count = count;
                    showDialogLoading();
                    couponExchangePresenter.getDefaultAddress(getContext(), SPUtil.getPrefString("token",""),DataType.freeOrder.getDefaultAddress.toString());
                }
            });
        }else if(dataType.equals(DataType.freeOrder.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this, userAddressBean.getContent(),this.goods_id,CouponExchangeActivity.this.sku,"exchange");
            shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long id, String sku, long addressId,String type) {
                    if(type.equals("exchange")){
                        couponExchangePresenter.createIntegralOrder(getContext(),SPUtil.getPrefString("token",""),id,sku,addressId,CouponExchangeActivity.this.count,DataType.order.createIntegralOrder.toString());
                    }
                }

            });
        }else if(dataType.equals(DataType.order.createIntegralOrder.toString())){
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")){
                showToast("兑换成功");
                startActivity(new Intent(CouponExchangeActivity.this, OrderActivity.class).putExtra("status", 2));
            }
        }else if(dataType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
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
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectAddressEvent event) {
        UserAddressBean.ContentBean useraddressbean = event.userAddressBean;
        ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this,useraddressbean,this.goods_id,CouponExchangeActivity.this.sku,"exchange");
        shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
        shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
        shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
            @Override
            public void createFree(long id, String sku, long addressId,String type) {
                if(type.equals("exchange")){
                    couponExchangePresenter.createIntegralOrder(getContext(),SPUtil.getPrefString("token",""),id,sku,addressId,CouponExchangeActivity.this.count,DataType.order.createIntegralOrder.toString());
                }
            }

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        couponExchangePresenter.getDefaultAddress(getContext(),SPUtil.getPrefString("token",""),DataType.freeOrder.getDefaultAddress.toString());
    }
}
