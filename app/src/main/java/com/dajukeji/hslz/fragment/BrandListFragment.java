package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.BrandStoreBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.util.ToastUtils;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandListFragment extends HttpBaseFragment {

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private int store_class_id;
    private XRecyclerView xRecyclerView;
    private BrandPresenter brandPresenter;
    private int pageSize = 0;
    private BaseRecyclerAdapter<BrandStoreBean.ContentBean.StoreListBean> madapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public boolean supportX() {
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.fragment_brand_list, null);
        }
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        xRecyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        madapter = new BaseRecyclerAdapter<BrandStoreBean.ContentBean.StoreListBean>(getContext(), null, R.layout.item_brand_store_list) {
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
                                Intent leftllintent = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                leftllintent.putExtra("goods_id", data.getGoods_list().get(0).getGoods_id());
                                leftllintent.putExtra(Constants.is_brand_good, true);
                                startActivity(leftllintent);
                                break;
                            case R.id.brand_store_right_ll:
                                Intent rightllintent = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                rightllintent.putExtra("goods_id", data.getGoods_list().get(1).getGoods_id());
                                rightllintent.putExtra(Constants.is_brand_good, true);
                                startActivity(rightllintent);
                                break;
                        }
                    }
                };
                holder.setText(R.id.store_type, data.getStore_type());
                holder.setText(R.id.store_level, data.getStore_level());
                holder.setText(R.id.store_speed , data.getStore_speed());
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
                loadList(currentPage);
            }
        });
//        xRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 10, R.color.enter_store_button));
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xRecyclerView.setAdapter(madapter);
    }

//    @Override
//    protected void onFragmentVisibleChange(boolean isVisible) {
//        super.onFragmentVisibleChange(isVisible);
//        if (isVisible) {
//            loadList(currentPage);
//        }
//    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        store_class_id = getArguments().getInt("store_class_id");
        brandPresenter = new BrandPresenter(this);
        loadList(currentPage);
    }

    private void loadList(int page) {
        if (store_class_id == -1) {
            brandPresenter.getBrandStoreList(BrandListFragment.this, page, DataType.brand.brandStoreList.toString());
        } else {
            brandPresenter.getBrandStoreList(BrandListFragment.this, page, store_class_id, DataType.brand.brandStoreList.toString());
        }
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.brand.brandStoreList.toString())) {
            BrandStoreBean brandStoreBean = (BrandStoreBean) object;
            if (isFirstPage) {
                madapter.clear();
            }
            currentPage++;
            madapter.setData(brandStoreBean.getContent().getStore_list());
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
