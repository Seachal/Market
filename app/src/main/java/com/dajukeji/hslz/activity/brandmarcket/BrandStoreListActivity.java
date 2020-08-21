package com.dajukeji.hslz.activity.brandmarcket;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.BrandStoreBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.util.ToastUtils;

/**
 * Created by Administrator on 2018/2/5 0005.
 */

public class BrandStoreListActivity extends HttpBaseActivity {

    private BrandPresenter brandPresenter;
    private XRecyclerView xRecyclerView;
    private BaseRecyclerAdapter<BrandStoreBean.ContentBean.StoreListBean> adapter;
    private int brand_id;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private int pageSize = 0;
    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_brand_store_list);
        setTitleBar(getIntent().getStringExtra(Constants.brand_name), true);
        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        adapter = new BaseRecyclerAdapter<BrandStoreBean.ContentBean.StoreListBean>(getContext(), null, R.layout.item_brand_store_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, final BrandStoreBean.ContentBean.StoreListBean data, int position, boolean isScrolling) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.brand_store_head:
                                Intent intent = new Intent(getContext(), BrandStoreDetailActivity.class);
                                intent.putExtra("brand_id", data.getStore_id());
                                getContext().startActivity(intent);
                                break;
                            case R.id.brand_store_left_ll:
                                Intent leftllintent = new Intent(getContext(), NormalGoodDetailActivity.class);
                                leftllintent.putExtra("goods_id", data.getGoods_list().get(0).getGoods_id());
                                leftllintent.putExtra(Constants.is_brand_good, true);
                                startActivity(leftllintent);
                                break;
                            case R.id.brand_store_right_ll:
                                Intent rightllintent = new Intent(getContext(), NormalGoodDetailActivity.class);
                                rightllintent.putExtra("goods_id", data.getGoods_list().get(1).getGoods_id());
                                rightllintent.putExtra(Constants.is_brand_good, true);
                                startActivity(rightllintent);
                                break;
                        }
                    }
                };
                holder.setText(R.id.store_type, data.getStore_type());
                holder.setText(R.id.store_level, data.getStore_level());
                holder.setText(R.id.store_speed, data.getStore_speed());
                holder.setImageByUrl(R.id.brand_store_icon, data.getStore_logo());
                holder.setText(R.id.brand_item_store_name, data.getStore_name());
                if (data.getGoods_list().size() >= 1 && null != data.getGoods_list().get(0)) {
                    BrandStoreBean.ContentBean.StoreListBean.GoodsListBean goodLeft = data.getGoods_list().get(0);
                    double priceLeft = goodLeft.getGoods_current_price()-goodLeft.getBrand_price();
                    holder.setImageByUrl(R.id.brand_item_good_img_left, goodLeft.getGoods_main_photo());
                    holder.getView(R.id.brand_store_left_ll).setOnClickListener(listener);
                    holder.setText(R.id.brand_item_good_dis_left, "拍下立减"+priceLeft+"元");
                    holder.setText(R.id.brand_item_good_name_left, goodLeft.getGoods_name());
                    holder.setText(R.id.good_current_price, goodLeft.getGoods_current_price()+"");
                } else {
                    holder.getView(R.id.brand_store_left_ll).setVisibility(View.GONE);
                }
                if (data.getGoods_list().size() == 2 && null != data.getGoods_list().get(1)) {
                    BrandStoreBean.ContentBean.StoreListBean.GoodsListBean goodRight = data.getGoods_list().get(1);
                    double priceRight = goodRight.getGoods_current_price()-goodRight.getBrand_price();
                    holder.setText(R.id.brand_item_good_dis_right, "拍下立减"+priceRight+"元");
                    holder.setText(R.id.brand_item_good_name_right, goodRight.getGoods_name());
                    holder.setText(R.id.brand_item_good_price_right, goodRight.getGoods_current_price() + "");
                    holder.setImageByUrl(R.id.brand_item_good_img_right, goodRight.getGoods_main_photo());
                    holder.getView(R.id.brand_store_right_ll).setOnClickListener(listener);
                } else {
                    holder.getView(R.id.brand_store_right_ll).setVisibility(View.GONE);
                }
                holder.getView(R.id.brand_store_head).setOnClickListener(listener);
            }
        };
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                isFirstPage = true;
                loadList(currentPage);
            }

            @Override
            public void onLoadMore() {
                if (currentPage > pageSize) {
                    ToastUtils.getInstance().showToast(getContext(), "最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                isFirstPage = false;
                loadList(currentPage + 1);
            }
        });
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        brandPresenter = new BrandPresenter(this);
        brand_id = getIntent().getIntExtra(Constants.brand_id, -1);
        loadList(currentPage);
    }

    private void loadList(int page) {
        brandPresenter.getStoreByBrand(BrandStoreListActivity.this, page, brand_id, DataType.brand.brandStoreList.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.brand.brandStoreList.toString())) {
            BrandStoreBean brandStoreBean = (BrandStoreBean) object;
            if (isFirstPage) {
                adapter.clear();
            }
            currentPage++;
            adapter.setData(brandStoreBean.getContent().getStore_list());
            complete();
            pageSize = brandStoreBean.getContent().getPages();
        }
    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }
}
