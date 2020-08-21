package com.dajukeji.hslz.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.dajukeji.hslz.domain.javaBean.StoreGoodsBean;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.util.MyAdGallery;
import com.dajukeji.hslz.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/6 0006.
 */

public class StoreGoodListFragment extends HttpBaseFragment {

    private BrandPresenter brandPresenter;
    private int gc_id;
    private XRecyclerView xRecyclerView;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private int store_id;
    private View headerView;
    private int pagesize = 0;
    private MyAdGallery store_good_list_header;
    private boolean isAddedHeader = false;
    private BaseRecyclerAdapter<StoreGoodsBean.ContentEntity.GoodsListEntity> madapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public boolean supportX() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_brand_list, null);
        }
        headerView = inflater.inflate(R.layout.header_store_goodlist, null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        store_id = getArguments().getInt(Constants.store_id);
        gc_id = getArguments().getInt(Constants.gc_id);
        brandPresenter = new BrandPresenter(this);
        loadList(currentPage);
    }

    private void loadList(int page) {
        if (gc_id == -1) {
            brandPresenter.getStoreGoodsList(StoreGoodListFragment.this, page, store_id, DataType.brand.storeGoodsList.toString());
        } else {
            brandPresenter.getStoreGoodsList(StoreGoodListFragment.this, page, gc_id, store_id, DataType.brand.storeGoodsList.toString());
        }
    }

    private void initView(View view) {
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        madapter = new BaseRecyclerAdapter<StoreGoodsBean.ContentEntity.GoodsListEntity>(getContext(), null, R.layout.item_store_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, StoreGoodsBean.ContentEntity.GoodsListEntity data, int position, boolean isScrolling) {
                int mScreenWidth = ScreenUtils.getScreenWidth(getContext());
                int height = getResources().getDimensionPixelSize(R.dimen.y534);
                int mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
                int mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
                if (position % 2 == 0) {
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                } else {
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }
                ImageView good_img =  holder.getView(R.id.good_img);
                holder.setThumbWithGlide(getContext(),mScreenWidth/2, height ,R.drawable.goods,good_img,data.getGoods_main_photo());
                holder.setText(R.id.good_name_tt, data.getGoods_name());
                TextView good_dis_left = holder.getView(R.id.brand_item_good_dis_left);
                TextView good_pre_price = holder.getView(R.id.good_pre_price);
                if (data.getPrice_des() == null || data.getPrice_des().equals("")) {
                    good_dis_left.setVisibility(View.GONE);
                    good_pre_price.setText(getResources().getString(R.string.rmb_symbol) + data.getGoods_price());
                    good_pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    good_pre_price.setVisibility(View.GONE);
                } else {
                    good_pre_price.setVisibility(View.GONE);
                    good_dis_left.setText(data.getPrice_des());
                    good_dis_left.setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.good_current_price, data.getGoods_current_price() + "");
                ImageView good_zonetype_img = holder.getView(R.id.good_zonetype_img);
                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.compare_price_card);
                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.subside_card);
                } else if (data.getZone_type().equals(DataType.zone_type.ninepointnine.toString())) {
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.nine_card);
                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                    good_zonetype_img.setVisibility(View.VISIBLE);
                    good_zonetype_img.setImageResource(R.drawable.brand_sale_card);
                } else {
                    good_zonetype_img.setVisibility(View.GONE);
                }
            }
        };
        madapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<StoreGoodsBean.ContentEntity.GoodsListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, StoreGoodsBean.ContentEntity.GoodsListEntity data, int position) {
//                showToast(data.getGoods_name());
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
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                isFirstPage = true;
                loadList(currentPage);

            }

            @Override
            public void onLoadMore() {
                if (currentPage > pagesize) {
                    ToastUtils.getInstance().showToast(getContext(), "最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                isFirstPage = false;
                loadList(currentPage);
            }
        });
        Manager manager = new Manager(getContext(), 2);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(manager);
        xRecyclerView.setAdapter(madapter);
        store_good_list_header = (MyAdGallery) headerView.findViewById(R.id.store_good_list_header);
    }

    class Manager extends GridLayoutManager {

        public Manager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public Manager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public Manager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }

        @Override
        public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
            try {
                super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void setResultData(Object object, final String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.brand.storeGoodsList.toString())) {
            final StoreGoodsBean data = (StoreGoodsBean) object;
            if (isFirstPage) {
                madapter.setNewData(data.getContent().getGoodsList());
            } else {
                madapter.setData(data.getContent().getGoodsList());
            }
            complete();
            currentPage++;
            pagesize = data.getContent().getPages();
            if (gc_id == -1) {
                if (null != data.getContent().getBanner_list() && data.getContent().getBanner_list().size() > 0) {
                    List<String> urlList = new ArrayList<>();
                    for (int i = 0; i < data.getContent().getBanner_list().size(); i ++) {
                        urlList.add(data.getContent().getBanner_list().get(i).getBanner_logo());
                    }
                    store_good_list_header.start(AliSdkApplication.getContext(), urlList, R.drawable.goods, 3000, null, 0, 0);
                    store_good_list_header.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {
                        @Override
                        public void onItemClick(int curIndex) {
                            int goodid = data.getContent().getBanner_list().get(curIndex).getGoods_id();
                            String zone_type = data.getContent().getBanner_list().get(curIndex).getZone_type();
                            if (zone_type.equals(DataType.zone_type.compareprice.toString())) {
                                Intent intent1 = new Intent(getActivity(), ComparePriceGoodDetailActivity.class);
                                intent1.putExtra("goods_id", goodid);
                                startActivity(intent1);
                            } else if (zone_type.equals(DataType.zone_type.ninepointnine.toString())) {
                                Intent intent2 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                intent2.putExtra("goods_id", goodid);
                                startActivity(intent2);
                            } else if (zone_type.equals(DataType.zone_type.creative.toString())) {
                                Intent intent3 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                intent3.putExtra("goods_id", goodid);
                                startActivity(intent3);
                            } else if (zone_type.equals(DataType.zone_type.cutprice.toString())) {
                                Intent intent4 = new Intent(getActivity(), SubsidyGoodDetailActivity.class);
                                intent4.putExtra("goods_id", goodid);
                                startActivity(intent4);
                            } else if (zone_type.equals(DataType.zone_type.brand.toString())) {
                                Intent intent5 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                intent5.putExtra("goods_id", goodid);
                                intent5.putExtra(Constants.is_brand_good, true);
                                startActivity(intent5);
                            } else if (zone_type.equals("")) {
                                Intent intent6 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                intent6.putExtra("goods_id", goodid);
                                startActivity(intent6);
                            }
                        }
                    });
                    if (!isAddedHeader) {
                        xRecyclerView.addHeaderView(headerView);
                        isAddedHeader = true;
                    } else {
                    }
                }
            }
        }
    }
}
