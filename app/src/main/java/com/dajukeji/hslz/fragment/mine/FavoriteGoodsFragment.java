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
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.FavouriteGoodsBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FavoritePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.EndLessOnScrollListener;
import com.dajukeji.hslz.view.SideslipFavoriteItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品收藏列表
 */
@SuppressLint("ValidFragment")
public  class FavoriteGoodsFragment extends HttpBaseFragment {
    private FavoritePresenter favoritePresenter;
    private SideslipFavoriteItem rv_favorite_goods; //产品收藏列表
    private SwipeRefreshLayout refresh; // 下拉刷新
    private LinearLayoutManager linearLayoutManager; // RecyclerView 布局管理器
    private MyReAdapter mMyAdapter;
    private LinearLayout ll_empty_favorite; //空收藏
    private int currentPage = 1;
    private boolean fresh = false;
    private List<FavouriteGoodsBean.ContentBean.FavoriteListBean> favoriteList = new ArrayList<>(); // 购物车产品数据
    private int pageSize;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.srecycler_layout, container, false);
        }
        favoritePresenter = new FavoritePresenter(this);
        initView(rootView);
        return rootView;
    }


    private void initView(View v) {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        ll_empty_favorite = (LinearLayout) v.findViewById(R.id.ll_empty_favorite);
        rv_favorite_goods = (SideslipFavoriteItem) v.findViewById(R.id.rv_favorite_goods);
        refresh = (SwipeRefreshLayout) v.findViewById(R.id.refresh);
        rv_favorite_goods.setLayoutManager(linearLayoutManager);
        mMyAdapter = new MyReAdapter(favoriteList);
        rv_favorite_goods.setAdapter(mMyAdapter);
        rv_favorite_goods.setOnItemClickListener(new SideslipFavoriteItem.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(favoriteList.get(position).getZone_type().equals(Constants.compareprice)){
                    Intent intent = new Intent(getContext().getApplicationContext(), ComparePriceGoodDetailActivity.class);
                    intent.putExtra("goods_id",(int)favoriteList.get(position).getGoods_id());
                    startActivity(intent);
                }else if(favoriteList.get(position).getZone_type().equals(Constants.cutprice)){
                    Intent intent = new Intent(getContext().getApplicationContext(), SubsidyGoodDetailActivity.class);
                    intent.putExtra("goods_id",(int)favoriteList.get(position).getGoods_id());
                    startActivity(intent);
                }else if(favoriteList.get(position).getZone_type().equals("")||favoriteList.get(position).getZone_type().equals(Constants.creative)||favoriteList.get(position).getZone_type().equals(Constants.ninepointnine)||favoriteList.get(position).getZone_type().equals("brand")){
                    Intent intent = new Intent(getContext().getApplicationContext(), NormalGoodDetailActivity.class);
                    intent.putExtra("goods_id",(int)favoriteList.get(position).getGoods_id());
                    startActivity(intent);
                }else {
                    showToast("此产品无产品详情");
                }
            }

            @Override
            public void onDeleteClick(int position) {
                favoritePresenter.deleteFavorite(getContext(), SPUtil.getPrefString("token",""),"0",favoriteList.get(position).getGoods_id(),DataType.favorite.delete.toString());
            }
        });

        refreshView(); // 刷新布局
        recyclerLoadMore(rv_favorite_goods); // 加载更多
    }

    private void recyclerLoadMore(RecyclerView recycler_bargain) { // 上拉

        recycler_bargain.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                if(currentPage>=pageSize){
                    showToast("没有更多数据了");
                }else {
                    showDialogLoading();
                    favoritePresenter.selectFavoriteGoods(getContext(), currentPage, SPUtil.getPrefString("token",""),DataType.favorite.selectFavoriteGoods.toString());
                }
            }
        });
    }

    private void refreshView(){  //刷新布局
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {  //下拉刷新
            public void onRefresh() {
                fresh = true;
                refresh.setRefreshing(true);
                favoritePresenter.selectFavoriteGoods(getContext(), currentPage,SPUtil.getPrefString("token","") ,DataType.favorite.selectFavoriteGoods.toString());
            }
        });
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.favorite.selectFavoriteGoods.toString())){
            hideDialogLoading();
            refresh.setRefreshing(false);
            FavouriteGoodsBean favouriteGoodsBean = (FavouriteGoodsBean) object;
            if(favouriteGoodsBean.getContent().getFavoriteList().isEmpty()){
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
            favoriteList.addAll(favouriteGoodsBean.getContent().getFavoriteList());
            mMyAdapter.notifyDataSetChanged();
            pageSize = favouriteGoodsBean.getContent().getPages();
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
        favoritePresenter.selectFavoriteGoods(getContext(), page,SPUtil.getPrefString("token","") ,DataType.favorite.selectFavoriteGoods.toString());
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

        private List<FavouriteGoodsBean.ContentBean.FavoriteListBean> favoriteList; // 购物车产品数据

        public static final int VALID_GOODS = 1; // 未失效购物车产品标示

        public MyReAdapter(List<FavouriteGoodsBean.ContentBean.FavoriteListBean> favoriteList) {
            this.favoriteList = favoriteList;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(getActivity());
            RecyclerView.ViewHolder holder = null;
            if(VALID_GOODS == viewType){   // 未失效购物车产品
                View v = mInflater.inflate(R.layout.item_favorite_goods,parent,false);
                holder = new ValidViewHolder(v);
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if(holder instanceof ValidViewHolder){ // 未失效购物车产品
                ImageView pict_url =  ((ValidViewHolder) holder).pict_url;
                int size =  getResources().getDimensionPixelSize(R.dimen.x300);
                GlideEngine.loadThumbnail(getActivity(),size, R.drawable.goods, pict_url,favoriteList.get(position).getIcon());

                TextView item_title =  ((ValidViewHolder) holder).item_title;
                item_title.setText(favoriteList.get(position).getGoods_name());

                TextView tv_size =  ((ValidViewHolder) holder).tv_size;
                tv_size.setText(favoriteList.get(position).getSize()+"人已收藏");

                TextView tv_goods_price =  ((ValidViewHolder) holder).tv_goods_price;
                tv_goods_price.setText(getResources().getString(R.string.rmb_symbol)+" "+favoriteList.get(position).getStore_price());

                TextView tv_goods_invalid = ((ValidViewHolder) holder).tv_goods_invalid;
                if(Integer.toString(favoriteList.get(position).getStatus()).equals("0")){
                    tv_goods_invalid.setVisibility(View.GONE);
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
        ImageView pict_url;
        TextView item_title;
        TextView tv_size;
        TextView tv_goods_price;
        TextView tv_goods_invalid;
        public ValidViewHolder(View itemView) {
            super(itemView);
            ll_favorite_item =(LinearLayout) itemView.findViewById(R.id.ll_favorite_item);
            pict_url = (ImageView) itemView.findViewById(R.id.pict_url);
            item_title = (TextView) itemView.findViewById(R.id.item_title);
            tv_size = (TextView) itemView.findViewById(R.id.tv_size);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_goods_price);
            tv_goods_invalid = (TextView) itemView.findViewById(R.id.tv_goods_invalid);
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
