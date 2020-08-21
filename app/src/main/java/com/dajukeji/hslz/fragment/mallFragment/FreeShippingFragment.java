package com.dajukeji.hslz.fragment.mallFragment;

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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.NewGoodsLoadMoreBean;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.luck.picture.lib.tools.ScreenUtils;

/**
 * 新品区底部
 * 9.9包邮产品
 * <p/>
 * interface.
 */
@SuppressLint("ValidFragment")
public class FreeShippingFragment extends HttpBaseFragment {

    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    private int gc_id;
    private boolean isSCJ_Show;//是否显示市场价
    private GoodPresenter goodPresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;
    private String zone_type = "ninepointnine";
    private String keyword = "";
    private BaseRecyclerAdapter<NewGoodsLoadMoreBean.ContentBean.StoreGoodsListBean> recyclerAdapter;
    private int mScreenWidth = 0;
    private int mPaddingRight, mPaddingBottom;
    private View headerView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.gc_id =  getArguments().getInt("gc_id");
        this.isSCJ_Show = getArguments().getBoolean("isSCJ_Show");
        goodPresenter = new GoodPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.xrecycler_order_layout, null);
        }
        headerView = inflater.inflate(R.layout.item_header,null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }

    private void initView(View view) {
//        ll_empty_order = (LinearLayout) view.findViewById(R.id.ll_empty_order);
//        tv_order_empty = (TextView) view.findViewById(R.id.tv_order_empty);
//        im_empty = (ImageView) view.findViewById(R.id.im_empty);
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);

        initHeaderView(); // 添加头部图片
//        xRecyclerView.getItemAnimator().setChangeDuration(0);
        xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadList(currentPage);
                isFirstPage = true;
            }

            @Override
            public void onLoadMore() {
                if (currentPage > pageSize) {
                    showToast("到底了");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });
        mPaddingRight = mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.x12);
        mScreenWidth = ScreenUtils.getScreenWidth(getActivity());
        recyclerAdapter = new BaseRecyclerAdapter<NewGoodsLoadMoreBean.ContentBean.StoreGoodsListBean>(getContext(), null, R.layout.item_goods_display_sold) {
            @Override
            public void convert(BaseRecyclerHolder holder, NewGoodsLoadMoreBean.ContentBean.StoreGoodsListBean data, int position, boolean isScrolling) {
                //动态设置图片的宽高
                int  mScreenWidth = ScreenUtils.getScreenWidth(getContext());
                int height = getResources().getDimensionPixelSize(R.dimen.y534);
                int  mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
                int  mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
                //动态设置item 之间的间距
                if(position % 2 == 0 ){
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                }else{
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }
                ImageView good_img =  holder.getView(R.id.item_free_shopping_url);
                RelativeLayout.LayoutParams mImageParams =(RelativeLayout.LayoutParams) good_img.getLayoutParams();
                mImageParams.width = mScreenWidth / 2;
                mImageParams.height =height;
                good_img.setLayoutParams(mImageParams);
                holder.setThumbWithGlide(getContext(),mScreenWidth/2, height ,R.drawable.goods,good_img, data.getGoods_main_photo());
                holder.setText(R.id.item_free_shopping_title, data.getGoods_name());
                holder.setText(R.id.item_free_shopping_final_price,getResources().getString(R.string.rmb_symbol) +String.format("%.2f",data.getGoods_price()*1.0));
                if (isSCJ_Show){//如果需要显示市场价，就传个参数来
                    TextView shichangjia = holder.getView(R.id.shichangjia);
                    shichangjia.setVisibility(View.VISIBLE);
                    TextView mTvOriginalPrice = holder.getView(R.id.item_free_shopping_original_price);
                    mTvOriginalPrice.setVisibility(View.VISIBLE);
                    mTvOriginalPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
//                    mTvOriginalPrice.setText(getResources().getString(R.string.rmb_symbol) + data.getGoods_price());
                    mTvOriginalPrice.setText("￥" + data.getGoods_price());
                }
                holder.setText(R.id.item_free_shopping_sold, data.getGoods_salenum() + "件已售");
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<NewGoodsLoadMoreBean.ContentBean.StoreGoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, NewGoodsLoadMoreBean.ContentBean.StoreGoodsListBean data, int position) {
                Intent intent = new Intent(getContext().getApplicationContext(), NormalGoodDetailActivity.class);
                intent.putExtra("goods_id", data.getId());
                getActivity().startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }
    private void initHeaderView() {
        xRecyclerView.addHeaderView(headerView);
    }
    private void loadList(int page) {
        goodPresenter.getNewGoodsLoadmore(this,page,"新品区上拉");
//        goodPresenter.getGoodsList(this, page, gc_id, keyword, zone_type, DataType.good.getGoodList.toString());//之前的数据逻辑
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals("新品区上拉")) {
            NewGoodsLoadMoreBean goodListBean = (NewGoodsLoadMoreBean) object;
            if (isFirstPage) {
                recyclerAdapter.clear();
            }
            complete();
            if(goodListBean.getContent().getStoreGoodsList().isEmpty()){
//                ll_empty_order.setVisibility(View.VISIBLE);
//                tv_order_empty.setText("暂无产品");
//                im_empty.setVisibility(View.GONE);
//                xRecyclerView.setVisibility(View.GONE);
            }else {
                currentPage ++;
                recyclerAdapter.setData(goodListBean.getContent().getStoreGoodsList());
                pageSize = goodListBean.getContent().getPages();
            }
        }
    }

    @Override
    public boolean supportX() {
        return true;
    }

    @Override
    public void showHttpError(String error, String contenttype) {
        super.showHttpError(error, contenttype);
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
    }
}
