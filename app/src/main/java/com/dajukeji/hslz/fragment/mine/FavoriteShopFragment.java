package com.dajukeji.hslz.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ComparePriceGoodDetailActivity;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.SubsidyGoodDetailActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.FavouriteShopBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FavoritePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.EndLessOnScrollListener;
import com.dajukeji.hslz.view.SideslipFavoriteShopItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 店铺收藏列表
 */
@SuppressLint("ValidFragment")
public  class FavoriteShopFragment extends HttpBaseFragment {

    private FavoritePresenter favoritePresenter;
    private SideslipFavoriteShopItem rv_favorite_shops; //产品收藏列表
    private SwipeRefreshLayout refresh; // 下拉刷新
    private LinearLayoutManager linearLayoutManager; // RecyclerView 布局管理器
    private MyReAdapter myReAdapter;
    private LinearLayout ll_empty_favorite; //空收藏

    private int currentPage = 1;
    private boolean fresh = false;
    private List<FavouriteShopBean.ContentBean.FavoriteListBean> favoriteList = new ArrayList<>(); // 购物车产品数据
    private int pageSize;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.srecycler_shop_layout, container, false);
        }
        favoritePresenter = new FavoritePresenter(this);
        initView(rootView);
        return rootView;
    }


    private void initView(View v) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        ll_empty_favorite = (LinearLayout) v.findViewById(R.id.ll_empty_favorite);
        rv_favorite_shops = (SideslipFavoriteShopItem) v.findViewById(R.id.rv_favorite_shops);
        refresh = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        rv_favorite_shops.setLayoutManager(linearLayoutManager);
        myReAdapter= new MyReAdapter(favoriteList);
        rv_favorite_shops.setAdapter(myReAdapter);
        rv_favorite_shops.setOnItemClickListener(new SideslipFavoriteShopItem.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BrandStoreDetailActivity.class);
                intent.putExtra("brand_id", favoriteList.get(position).getStore_id());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                favoritePresenter.deleteFavorite(getContext(), SPUtil.getPrefString("token",""),"1",favoriteList.get(position).getStore_id(),DataType.favorite.delete.toString());
            }
        });
        refreshView(); // 刷新布局
        recyclerLoadMore(rv_favorite_shops); // 加载更多
    }

    private void recyclerLoadMore(RecyclerView recycler_bargain) { // 上拉加载更多

        recycler_bargain.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(currentPage>=pageSize){
                    showToast("没有更多数据了");
                }else{
                    showDialogLoading();
                    favoritePresenter.selectFavoriteStore(getContext(), currentPage, SPUtil.getPrefString("token","") ,DataType.favorite.selectFavoriteStore.toString());
                }
            }
        });
    }

    private void refreshView(){  //刷新布局
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  //下拉刷新
            public void onRefresh() {
                fresh = true;
                refresh.setRefreshing(false);
                favoritePresenter.selectFavoriteStore(getContext(), currentPage,SPUtil.getPrefString("token","") ,DataType.favorite.selectFavoriteStore.toString());
            }
        });
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.favorite.selectFavoriteStore.toString())){
            hideDialogLoading();
            FavouriteShopBean favouriteShopBean = (FavouriteShopBean) object;
            if(favouriteShopBean.getContent().getFavoriteList().isEmpty()){
                ll_empty_favorite.setVisibility(View.VISIBLE);
                refresh.setVisibility(View.GONE);
                return;
            }else {
                ll_empty_favorite.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);
            }
            complete();
            if(fresh){
                favoriteList.clear();
                fresh = false;
            }
            favoriteList.addAll(favouriteShopBean.getContent().getFavoriteList());
            myReAdapter.notifyDataSetChanged();
            pageSize = favouriteShopBean.getContent().getPages();
            currentPage++;
        }else if(dataType.equals(DataType.favorite.delete.toString())){
            String result = (String) object;
            if(result.equals("sucess")){
                favoriteList.clear();
                loadList(1);
            }
        }
    }

    private void loadList(int page) {
        showDialogLoading();
        favoritePresenter.selectFavoriteStore(getContext(), page,SPUtil.getPrefString("token",""),DataType.favorite.selectFavoriteStore.toString());
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    private void complete() {
        hideDialogLoading();
    }

    public class MyReAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{  //购物车数据布局

        private List<FavouriteShopBean.ContentBean.FavoriteListBean> favoriteList; // 购物车产品数据

        public static final int VALID_GOODS = 1; // 未失效购物车产品标示

        public MyReAdapter(List<FavouriteShopBean.ContentBean.FavoriteListBean> favoriteList) {
            this.favoriteList = favoriteList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder holder = null;
            if(VALID_GOODS == viewType){   // 未失效购物车产品
                View v = mInflater.inflate(R.layout.item_favorite_shop,parent,false);
                holder = new ValidViewHolder(v);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof ValidViewHolder){ // 未失效购物车产品
                ImageView store_img =  ((ValidViewHolder) holder).store_img;
                int size =  getResources().getDimensionPixelSize(R.dimen.x110);
                GlideEngine.loadThumbnail(getActivity(),size, R.drawable.goods, store_img, favoriteList.get(position).getIcon());

                TextView store_name_tt =  ((ValidViewHolder) holder).store_name_tt;
                store_name_tt.setText(favoriteList.get(position).getStore_name());

                TextView store_status = ((ValidViewHolder) holder).store_status;
                store_status.setText(favoriteList.get(position).getStore_status());

                TextView store_speed = ((ValidViewHolder) holder).store_speed;
                store_speed.setText(favoriteList.get(position).getStore_speed());

                TextView store_level = ((ValidViewHolder) holder).store_level;
                store_level.setText(favoriteList.get(position).getStore_level());

                TextView store_dis_tt = ((ValidViewHolder) holder).store_dis_tt;
                if(favoriteList.get(position).getStore_info()!=null&&!favoriteList.get(position).getStore_info().equals("")){
                    store_dis_tt.setText(favoriteList.get(position).getStore_info());
                }else {
                    store_dis_tt.setText("店铺暂无描述");
                }

                LinearLayout ll_shop_goods =  ((ValidViewHolder) holder).ll_shop_goods;

                int sizes =  getResources().getDimensionPixelSize(R.dimen.x250);
                int height =  getResources().getDimensionPixelSize(R.dimen.y250);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(sizes,height);
                int max = 0;
                if(favoriteList.get(position).getGoodsList().size()>4){
                    max = 4;
                }else {
                    max = favoriteList.get(position).getGoodsList().size();
                }
                ll_shop_goods.removeAllViews();
                    for (int i=0;i<max;i++){
                        final int index =i;
                        ImageView goodsIV = new ImageView(getContext());
                        goodsIV.setLayoutParams(layoutParams);
                        goodsIV.setPadding(0,getResources().getDimensionPixelSize(R.dimen.y25),getResources().getDimensionPixelSize(R.dimen.x50),getResources().getDimensionPixelSize(R.dimen.y25));
                        GlideEngine.loadThumbnail(getActivity(),sizes,height, R.drawable.goods, goodsIV, favoriteList.get(position).getGoodsList().get(i).getGoods_icon());
                        ll_shop_goods.addView(goodsIV);
                        goodsIV.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(favoriteList.get(position).getGoodsList().get(index).getZone_type().equals(Constants.compareprice)){
                                    Intent intent = new Intent(getContext().getApplicationContext(), ComparePriceGoodDetailActivity.class);
                                    intent.putExtra("goods_id",favoriteList.get(position).getGoodsList().get(index).getGoods_id());
                                    startActivity(intent);
                                }else if(favoriteList.get(position).getGoodsList().get(index).getZone_type().equals(Constants.cutprice)){
                                    Intent intent = new Intent(getContext().getApplicationContext(), SubsidyGoodDetailActivity.class);
                                    intent.putExtra("goods_id",favoriteList.get(position).getGoodsList().get(index).getGoods_id());
                                    startActivity(intent);
                                }else if(favoriteList.get(position).getGoodsList().get(index).getZone_type().equals("")||favoriteList.get(position).getGoodsList().get(index).getZone_type().equals(Constants.creative)||favoriteList.get(position).getGoodsList().get(index).getZone_type().equals(Constants.ninepointnine)||favoriteList.get(position).getGoodsList().get(index).getZone_type().equals("brand")){
                                    Intent intent = new Intent(getContext().getApplicationContext(), NormalGoodDetailActivity.class);
                                    intent.putExtra("goods_id",favoriteList.get(position).getGoodsList().get(index).getGoods_id());
                                    startActivity(intent);
                                }else {
                                    showToast("此产品无产品详情");
                                }
                            }
                        });
                    }
            }
        }

        @Override
        public int getItemCount() {
            return favoriteList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return VALID_GOODS;
        }

    }

    public class ValidViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout ll_favorite_item;
        RecyclerView rv_favorite_shop_goods;
        ImageView store_img;
        TextView store_name_tt;
        TextView store_status;
        TextView store_speed;
        TextView store_level;
        TextView store_dis_tt;
        LinearLayout ll_shop_goods;
        public ValidViewHolder(View itemView) {
            super(itemView);
            ll_favorite_item =(LinearLayout) itemView.findViewById(R.id.ll_favorite_item);
            rv_favorite_shop_goods = (RecyclerView) itemView.findViewById(R.id.rv_favorite_shop_goods);
            store_img = (ImageView) itemView.findViewById(R.id.store_img);
            store_name_tt = (TextView) itemView.findViewById(R.id.store_name_tt);
            store_status = (TextView) itemView.findViewById(R.id.store_status);
            store_speed = (TextView) itemView.findViewById(R.id.store_speed);
            store_level = (TextView) itemView.findViewById(R.id.store_level);
            store_dis_tt = (TextView) itemView.findViewById(R.id.store_dis_tt);
            ll_shop_goods = (LinearLayout) itemView.findViewById(R.id.ll_shop_goods);
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
