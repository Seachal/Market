package com.dajukeji.hslz.fragment.catcoupon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.kepler.jd.Listener.LoginListener;
import com.kepler.jd.login.KeplerApiManager;
import com.luck.picture.lib.tools.ScreenUtils;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.JDGoodsDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.coupon.JDCoupon;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * Created by Administrator on 2018/1/3.
 * 京券列表
 */
@SuppressLint("ValidFragment")
public class JDGoodsListFragment extends HttpBaseFragment {

    private XRecyclerView xRecyclerView;
    private CouponPresenter couponPresenter;
    private BaseRecyclerAdapter<JDCoupon.ContentBean.DataBean> recyclerAdapter;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private boolean isLast = false; // 是否最后一页
    private int mScreenWidth = 0;
    private int mPaddingRight, mPaddingBottom;

    private int type;
    public JDGoodsListFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.type = getArguments().getInt("type");
        if(rootView==null){
            rootView = inflater.inflate(R.layout.xrecycler_layout,null);
        }
        couponPresenter = new CouponPresenter(this);
        initView(rootView);
        return rootView;
    }

    private void initView(View v){
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mScreenWidth = ScreenUtils.getScreenWidth(getActivity());
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
    }

    @Override
    public boolean supportX() {
        return true;
    }


    private void loadList(int page){
        showDialogLoading();
        couponPresenter.getJDGoodsList(getContext(),page,20,type+"","", DataType.coupon.getJDList.toString());
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals( DataType.coupon.getJDList.toString())){
            hideDialogLoading();
            JDCoupon jdCoupon = (JDCoupon)object;
            complete();
            recyclerAdapter.setData(jdCoupon.getContent().getData());
            if(jdCoupon.getContent().getData().size()<20){
                isLast = true;
            }
            currentPage++;
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

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
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
        KeplerApiManager.getWebViewService().login(getActivity(), mLoginListener);
    }
}
