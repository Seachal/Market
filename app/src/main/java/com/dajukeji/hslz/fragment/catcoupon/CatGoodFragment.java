package com.dajukeji.hslz.fragment.catcoupon;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.CatGoodTypeActivity;
import com.dajukeji.hslz.activity.taocoupon.GoodsDetailActivity;
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.coupon.CatCoupon;
import com.dajukeji.hslz.event.ActionBarEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.luck.picture.lib.tools.ScreenUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by cdr on 2017/12/1.
 * 猫券-产品表格
 */

public class CatGoodFragment extends HttpBaseFragment {

    private CouponPresenter couponPresenter;
    private XRecyclerView xRecyclerView;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<CatCoupon.ContentBean.GoodsListBean> recyclerAdapter;

    private int mScreenWidth = 0;
    private int mPaddingRight, mPaddingBottom;

    private RelativeLayout ll_cat_find;
    private RelativeLayout reload_rl;
    private Button reload_button;

    private boolean isLast = false; // 是否最后一页

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        couponPresenter = new CouponPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_cat_good_grid, null);
        }
        initView(rootView);
        return rootView;
    }
    /**
     * 初始化头部布局与点击事件
     */

    private void initView(View v) {
        //初始化头部布局与点击事件
        v.findViewById(R.id.search_to_catType).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CatGoodTypeActivity.class));
            }
        });
        v.findViewById(R.id.search_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ShopFindActivity.class));
            }
        });
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        ll_cat_find = (RelativeLayout) v.findViewById(R.id.ll_cat_find);
        reload_rl = (RelativeLayout) v.findViewById(R.id.reload_rl);
        reload_button = (Button) v.findViewById(R.id.reload_button);
        reload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadList(1);
            }
        });
        xRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mScreenWidth = ScreenUtils.getScreenWidth(getActivity());
        mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x20);
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
        recyclerAdapter = new BaseRecyclerAdapter<CatCoupon.ContentBean.GoodsListBean>(getContext(), null, R.layout.item_cat_good_grid) {
            @Override
            public void convert(BaseRecyclerHolder holder, CatCoupon.ContentBean.GoodsListBean data, int position, boolean isScrolling) {
                if(position % 2 == 0 ){
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                }else{
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }

                ImageView mImage = holder.getView(R.id.item_catGood_img);
                LinearLayout.LayoutParams mImageParams =(LinearLayout.LayoutParams) mImage.getLayoutParams();
                mImageParams.width = mScreenWidth / 2;
                mImage.setLayoutParams(mImageParams);
                GlideEngine.loadThumbnail(getActivity(),mImageParams.width, R.drawable.goods, mImage, data.getPICT_URL());
                ImageView iv_coupon_type = holder.getView(R.id.iv_coupon_type);
                holder.setText(R.id.item_catGood_title,"     "+data.getTITLE());
                int size =  getResources().getDimensionPixelSize(R.dimen.x40);
                if(data.getUSER_TYPE().equals("0")||data.getUSER_TYPE().equals("c")){
                    GlideEngine.loadThumbnail(getContext(),size,size,R.drawable.taobao1,iv_coupon_type,R.drawable.taobao1);
                }else{
                    GlideEngine.loadThumbnail(getContext(),size,size,R.drawable.tmall,iv_coupon_type,R.drawable.tmall);
                }

                TextView mTvOriginalPrice = holder.getView(R.id.item_catGood_original_price);
                mTvOriginalPrice.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", (Double.parseDouble(data.getCOUPON_INFO())+Double.parseDouble(data.getZK_FINAL_PRICE()))));
                mTvOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

                holder.setText(R.id.item_catGood_cut_price, getResources().getString(R.string.rmb_symbol) + data.getCOUPON_INFO());
                holder.setText(R.id.item_catGood_present_price, getResources().getString(R.string.rmb_symbol) + String.format("%.2f", (Double.parseDouble(data.getZK_FINAL_PRICE()))));
                holder.setText(R.id.tv_volume,"已售"+data.getVOLUME()+"件");
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<CatCoupon.ContentBean.GoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder,CatCoupon.ContentBean.GoodsListBean data, int position) {
                if(!SPUtil.getPrefString("taoLogin","").equals("")){
                    Intent mIntent = new Intent(getContext(), GoodsDetailActivity.class);
                    mIntent.putExtra("content",new Gson().toJson(data));
                    startActivity(mIntent);
                }else{
                    taoLogin();
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
        couponPresenter.getCatGoodsList(getContext(), page,"" ,"", DataType.coupon.getCatList.toString());
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
        if (dataType.equals(DataType.coupon.getCatList.toString())) {
            reload_rl.setVisibility(View.VISIBLE);
            xRecyclerView.setVisibility(View.GONE);
            ll_cat_find.setVisibility(View.GONE);
        } else {
            showToast(getResources().getString(R.string.no_network_tips));
        }
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.coupon.getCatList.toString())) {
            hideDialogLoading();
            CatCoupon catCoupon = (CatCoupon) object;
            complete();
            if(!catCoupon.getContent().getGoodsList().isEmpty()){
                reload_rl.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.VISIBLE);
                ll_cat_find.setVisibility(View.VISIBLE);
                recyclerAdapter.setData(catCoupon.getContent().getGoodsList());
                currentPage++;
                // 条目数小于20为最后一页
                if(catCoupon.getContent().getGoodsList().size()<20){
                    isLast = true;
                }
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
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
        ((MainActivity)getActivity()).currentPage=1;
        EventBus.getDefault().post(new ActionBarEvent("action"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getActivity());
//        AlibcTradeSDK.destory();因阿里百川错误而注释
    }

    /**
     *  //淘宝登陆授权
     * */
    private void taoLogin() {
//        因阿里百川错误而注释
//        final AlibcLogin alibcLogin = AlibcLogin.getInstance();
//
//        alibcLogin.showLogin(new AlibcLoginCallback(){
//
//            @Override
//            public void onSuccess(int i) {
//                showToast("授权成功");
//                SPUtil.setPrefString("taoLogin","login");
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                showToast("登陆失败");
//            }
//        });
    }
}