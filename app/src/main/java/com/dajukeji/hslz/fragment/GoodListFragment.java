package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.luck.picture.lib.tools.ScreenUtils;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ComparePriceGoodDetailActivity;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.SubsidyGoodDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by ${wangjiasheng} on 2017/12/21 0021.
 */

public class GoodListFragment extends HttpBaseFragment {

    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    private int gc_id;
    private GoodPresenter goodPresenter;
    private XRecyclerView xRecyclerView;
    private String zone_type = "";
    private String keyword = "";
    private BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity> recyclerAdapter;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private int scrollDistance = 0;
    private ImageView go_top_iv;
    private RelativeLayout root_layout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gc_id = getArguments().getInt("gc_id");
        goodPresenter = new GoodPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.xrecycler_layout, null);
        }
        initView(rootView);
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }

    private void initView(View view) {
        root_layout = (RelativeLayout) view.findViewById(R.id.root_layout);
        go_top_iv = (ImageView) view.findViewById(R.id.go_top_iv);
        go_top_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xRecyclerView.scrollToPosition(0);
                go_top_iv.setVisibility(View.GONE);
                scrollDistance = 0;
            }
        });
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        xRecyclerView.getItemAnimator().setChangeDuration(0);
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
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });
        recyclerAdapter = new BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity>(getContext(), null, R.layout.item_store_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, GoodListBean.ContentEntity.GoodsListEntity data, int position, boolean isScrolling) {
//                holder.setText(R.id.item_free_shopping_title, data.getGoods_name());
//                holder.setText(R.id.item_free_shopping_original_price, "￥" + data.getGoods_price());
//                TextViewUtils.setPresentPrice((TextView) holder.getView(R.id.item_free_shopping_final_price), (float) data.getGoods_current_price());
//                holder.setImageByUrl(R.id.item_free_shopping_url, HttpAddress.mBaseUrl2 + data.getGoods_main_photo());
//                holder.setText(R.id.item_free_shopping_sold, "已售" + data.getGoods_salenum() + "件");


//                if (position % 2 == 0) {
//                    holder.itemView.setPadding(0, 0, getResources().getDimensionPixelSize(R.dimen.x10), getResources().getDimensionPixelSize(R.dimen.y10));
//                } else {
//                    holder.itemView.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.y10));
//                }


                int  mScreenWidth = ScreenUtils.getScreenWidth(getContext());
                int height = getResources().getDimensionPixelSize(R.dimen.y534);
                int  mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
                int  mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
                if(position % 2 == 0 ){
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                }else{
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }
                ImageView good_img =  holder.getView(R.id.good_img);
                holder.setThumbWithGlide(getContext(),mScreenWidth/2, height ,R.drawable.goods,good_img,data.getGoods_main_photo());
                holder.setText(R.id.good_name_tt, data.getGoods_name());
                TextView good_dis_left = holder.getView(R.id.brand_item_good_dis_left);
                TextView good_pre_price = holder.getView(R.id.good_pre_price);
                if (data.getPrice_des()==null || data.getPrice_des().equals("")) {
                    good_dis_left.setVisibility(View.GONE);
                    good_pre_price.setText(getResources().getString(R.string.rmb_symbol)+data.getGoods_price());
                    good_pre_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                    good_pre_price.setVisibility(View.GONE);
                } else {
                    good_pre_price.setVisibility(View.GONE);
                    good_dis_left.setText(data.getPrice_des());
                    good_dis_left.setVisibility(View.VISIBLE);
                }
//                holder.setText(R.id.brand_item_good_dis_left, data.getPrice_des());
                holder.setText(R.id.good_current_price, data.getGoods_current_price()+"");
                ImageView good_zonetype_img = holder.getView(R.id.good_zonetype_img);
                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
//                    holder.setImageResource(R.id.good_zonetype_img, R.drawable.compare_price_card);
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.compare_price_card);
                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
//                    holder.setImageResource(R.id.good_zonetype_img, R.drawable.subside_card);
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.subside_card);
                } else if (data.getZone_type().equals(DataType.zone_type.ninepointnine.toString())) {
//                    holder.setImageResource(R.id.good_zonetype_img, R.drawable.nine_card);
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.nine_card);
                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
//                    holder.setImageResource(R.id.good_zonetype_img, R.drawable.brand_sale_card);
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.brand_sale_card);
                } else {
                    good_zonetype_img.setVisibility(View.GONE);
//                    good_zonetype_img.setImageResource(R.drawable.nine_card);
                }
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodListBean.ContentEntity.GoodsListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, GoodListBean.ContentEntity.GoodsListEntity data, int position) {
                Intent intent = new Intent();
                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                    intent.setClass(getContext(), ComparePriceGoodDetailActivity.class);
                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                    intent.setClass(getContext(), SubsidyGoodDetailActivity.class);
                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
                    intent.putExtra(Constants.is_brand_good, true);
                } else {
                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
                }
                intent.putExtra(Constants.goods_id, data.getId());
                startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
        reload_rl = (RelativeLayout) view.findViewById(R.id.reload_rl);
        reload_button = (Button) view.findViewById(R.id.reload_button);
        xRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollDistance = scrollDistance + dy;
                if (scrollDistance >= getResources().getDimensionPixelOffset(R.dimen.y1920)) {
                    go_top_iv.setVisibility(View.VISIBLE);
                } else {
                    go_top_iv.setVisibility(View.GONE);
                }
            }
        });
//        TextView textView = new TextView(getContext());
//        xRecyclerView.addHeaderView(textView);
    }

    private void loadList(int page) {
        goodPresenter.getGoodsList(GoodListFragment.this, page, gc_id, keyword, zone_type, DataType.good.getGoodList.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.good.getGoodList.toString())) {
            GoodListBean goodListBean = (GoodListBean) object;
            if (isFirstPage) {
                recyclerAdapter.clear();
            }
            currentPage ++;
            complete();
            recyclerAdapter.setData(goodListBean.getContent().getGoodsList());
            pageSize = goodListBean.getContent().getPages();
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
        OkHttpUtils.getInstance().cancelTag(getContext());
        super.onDestroy();
    }
}
