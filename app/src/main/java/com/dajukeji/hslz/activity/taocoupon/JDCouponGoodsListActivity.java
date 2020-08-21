package com.dajukeji.hslz.activity.taocoupon;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kepler.jd.Listener.LoginListener;
import com.kepler.jd.login.KeplerApiManager;
import com.luck.picture.lib.tools.ScreenUtils;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.coupon.JDCoupon;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * 京券产品列表
 * */
public class JDCouponGoodsListActivity extends HttpBaseActivity {

    private XRecyclerView xRecyclerView;
    private CouponPresenter couponPresenter;
    private BaseRecyclerAdapter<JDCoupon.ContentBean.DataBean> recyclerAdapter;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private boolean isLast = false; // 是否最后一页
    private int mScreenWidth = 0;
    private int mPaddingRight, mPaddingBottom;

    private String so; // 关键
    private RelativeLayout reload_rl;
    private Button reload_button;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_coupon_goods_list);
        couponPresenter = new CouponPresenter(this);
        so = getIntent().getStringExtra("so");
        if(so.equals("")){
            setTitleBar("京券",true);
        }else {
            setTitleBar("京券"+" > "+so,true);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        reload_rl = (RelativeLayout) findViewById(R.id.reload_rl);
        reload_button = (Button) findViewById(R.id.reload_button);
        reload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadList(1);
            }
        });
        xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mScreenWidth = ScreenUtils.getScreenWidth(getContext());
        mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
        mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
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
                if(isLast){
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });
        recyclerAdapter = new BaseRecyclerAdapter<JDCoupon.ContentBean.DataBean>(getContext(), null, R.layout.item_jd_good_grid) {
            @Override
            public void convert(BaseRecyclerHolder holder,JDCoupon.ContentBean.DataBean  data, int position, boolean isScrolling) {
                ImageView mImage = holder.getView(R.id.item_catGood_img);

                LinearLayout.LayoutParams mImageParams =(LinearLayout.LayoutParams) mImage.getLayoutParams();
                mImageParams.width = mScreenWidth / 2;
                int height =  getResources().getDimensionPixelSize(R.dimen.y534);
                GlideEngine.loadThumbnail(getContext(),mImageParams.width,height, R.drawable.goods, mImage, data.getGoods_img());

                holder.setText(R.id.item_catGood_title, data.getGoods_name());
                if(position % 2 == 0 ){
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                }else{
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }

                TextView mTvOriginalPrice = holder.getView(R.id.item_catGood_original_price);
                mTvOriginalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
                mTvOriginalPrice.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f",data.getGoods_price()));
                holder.setText(R.id.item_catGood_cut_price, getResources().getString(R.string.rmb_symbol) + String.format("%.2f",data.getDiscount_price()));
                holder.setText(R.id.item_catGood_present_price,getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getCoupon_price()));
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<JDCoupon.ContentBean.DataBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder,JDCoupon.ContentBean.DataBean data, int position) {
                if(SPUtil.getPrefString("jdLogin","").equals("login")) {
                    Intent mIntent = new Intent(getContext(), JDGoodsDetailActivity.class);
                    mIntent.putExtra("content", new Gson().toJson(data));
                    startActivity(mIntent);
                }else{
                    jdLogin();
                }
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList(currentPage);
    }


    @Override
    public boolean supportX() {
        return true;
    }


    private void loadList(int page){
        showDialogLoading();
        couponPresenter.getJDGoodsList(getContext(),page,20,"",so, DataType.coupon.getJDList.toString());
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
        if (dataType.equals(DataType.coupon.getJDList.toString())) {
            reload_rl.setVisibility(View.VISIBLE);
            xRecyclerView.setVisibility(View.GONE);
        } else {
            showToast(getResources().getString(R.string.no_network_tips));
        }
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals( DataType.coupon.getJDList.toString())){
            hideDialogLoading();
            JDCoupon jdCoupon = (JDCoupon)object;
            if(!jdCoupon.getContent().getData().isEmpty()){
                complete();
                reload_rl.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.VISIBLE);
                recyclerAdapter.setData(jdCoupon.getContent().getData());
                if(jdCoupon.getContent().getData().size()<20){
                    isLast = true;
                }
                currentPage++;
            }else {
                showToast("暂无此类型优惠券");
            }
        }
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

    /**
     * 京东登陆授权
     * */
    private void jdLogin(){
        final LoginListener mLoginListener = new LoginListener() {
            @Override
            public void authSuccess(Object token) {
                showToast("授权成功");
                SPUtil.setPrefString("jdLogin","login"); // 保存京东登陆授权
            }
            @Override
            public void authFailed(int errorCode) {
                showToast("授权失败");
            }
        };
        KeplerApiManager.getWebViewService().login(this, mLoginListener);
    }

}
