package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.ShopCartPayActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.JsonModel;
import com.dajukeji.hslz.domain.javaBean.LookShopcartBean;
import com.dajukeji.hslz.event.ActionBarEvent;
import com.dajukeji.hslz.event.ShopCartChangeEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CartPresenter;
import com.dajukeji.hslz.view.AmountView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 购物车页面
 */

public class ShopCartFragment extends HttpBaseFragment {

    private CartPresenter cartPresenter;
    private double all_store_total;
    private CheckBox shop_cart_bottom_selection;
    private TextView shop_cart_money;
    private TextView shop_cart_pay;
    private LinearLayout select_all_ll;
    private TextView manage_tt;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private RelativeLayout shop_cart_bottom;
    private LinearLayout no_good_ll;
    private boolean selectedAll = false;
    private LinearLayout pay_ll;
    private TextView add_collect_tt, delete_tt;
    private LinearLayout manage_ll;
    private LookShopcartBean resultdata;
    private List<LookShopcartBean.ContentEntity.Store_cart_listEntity> contentdata;
    private XRecyclerView shop_cart_list;
    private BaseRecyclerAdapter<LookShopcartBean.ContentEntity.Store_cart_listEntity> mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartPresenter = new CartPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShopCartChangeEvent shopCartChangeEvent) {
        if (null != contentdata)
            contentdata.clear();
        mAdapter.clear();
        getShopCart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(UserMessageEvent userMessageEvent) {
        if (userMessageEvent.message.equals("user_out")) {
            contentdata.clear();
            mAdapter.clear();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_shopcart, null);
        }
        initView(rootView);
        return rootView;
    }

    private void initView(View view) {
        shop_cart_pay = (TextView) view.findViewById(R.id.shop_cart_pay);
        shop_cart_pay.setOnClickListener(this);
        reload_button = (Button) view.findViewById(R.id.reload_button);
        reload_button.setOnClickListener(this);
        shop_cart_money = (TextView) view.findViewById(R.id.shop_cart_money);
        shop_cart_bottom_selection = (CheckBox) view.findViewById(R.id.shop_cart_bottom_selection);
        no_good_ll = (LinearLayout) view.findViewById(R.id.no_good_ll);
        reload_rl = (RelativeLayout) view.findViewById(R.id.reload_rl);
        manage_tt = (TextView) view.findViewById(R.id.manage_tt);
        manage_tt.setOnClickListener(this);
        manage_ll = (LinearLayout) view.findViewById(R.id.manage_ll);
        add_collect_tt = (TextView) view.findViewById(R.id.add_collect_tt);
        add_collect_tt.setOnClickListener(this);
        delete_tt = (TextView) view.findViewById(R.id.delete_tt);
        delete_tt.setOnClickListener(this);
        select_all_ll = (LinearLayout) view.findViewById(R.id.select_all_ll);
        select_all_ll.setOnClickListener(this);
        pay_ll = (LinearLayout) view.findViewById(R.id.pay_ll);
        shop_cart_bottom = (RelativeLayout) view.findViewById(R.id.shop_cart_bottom);
        shop_cart_list = (XRecyclerView) view.findViewById(R.id.shop_cart_list);
        shop_cart_list.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new BaseRecyclerAdapter<LookShopcartBean.ContentEntity.Store_cart_listEntity>(getContext(), contentdata, R.layout.item_shopcart_goodname) {

            private SparseArray<String> titleIndices = new SparseArray<>();//标题索引,用于展示商品标题

            @Override
            public void setNewData(List<LookShopcartBean.ContentEntity.Store_cart_listEntity> data) {
                super.setNewData(data);
                //计算所有标题索引位置
                titleIndices.clear();
                String title = "";
                for (int i = 0; i < data.size(); i++) {
                    String store_name = data.get(i).getStore_name();
                    if (!title.equals(store_name)) {
                        title = store_name;
                        titleIndices.put(i, title);
                    }
                }
            }

            @Override
            public void convert(BaseRecyclerHolder holder, final LookShopcartBean.ContentEntity
                    .Store_cart_listEntity good, final int position, boolean isScrolling) {
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.good_img:
                                if (good.getStatus() == -1) {
                                    showToast("产品已失效");
                                    return;
                                } else {
                                    Intent goodimgintent = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                    goodimgintent.putExtra("goods_id", good.getGoods_id());
                                    startActivity(goodimgintent);
                                }
                                break;
                            case R.id.item_goodrl:
                                if (good.getStatus() == -1) {
                                    showToast("产品已失效");
                                    return;
                                } else {
                                    Intent itemgoodrlintent = new Intent(getActivity(), NormalGoodDetailActivity.class);
                                    itemgoodrlintent.putExtra("goods_id", good.getGoods_id());
                                    startActivity(itemgoodrlintent);
                                }
                                break;
                            case R.id.store_name_layout:
                                Intent storeintent = new Intent(getActivity(), BrandStoreDetailActivity.class);
                                storeintent.putExtra("brand_id", good.getStore_id());
                                startActivity(storeintent);
                                break;
                        }
                    }
                };

                RelativeLayout storeitem = holder.getView(R.id.store_name_layout);
                ImageView good_img = holder.getView(R.id.good_img);
                good_img.setOnClickListener(listener);
                RelativeLayout item_goodrl = holder.getView(R.id.item_goodrl);
                item_goodrl.setOnClickListener(listener);
                final ImageView is_good_lose = holder.getView(R.id.is_good_lose);
                final CheckBox good_selection = holder.getView(R.id.good_selection);
                if (good.isSelected()) {
                    good_selection.setChecked(true);
                } else {
                    good_selection.setChecked(false);
                }
                if (good.getStatus() == 0) {
                    holder.getView(R.id.goods_invaled_iv).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.goods_invaled_iv).setVisibility(View.VISIBLE);
                }

                //选中某一个商品，将价格调整一下
                holder.getView(R.id.check_good).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (contentdata.get(position).isSelected()) {
                            contentdata.get(position).setSelected(false);
                            good_selection.setChecked(false);
                            selectedAll = false;
                            shop_cart_bottom_selection.setChecked(false);
                        } else {
                            contentdata.get(position).setSelected(true);
                            good_selection.setChecked(true);
                            for (int i = 0; i < contentdata.size(); i++) {
                                if (!contentdata.get(i).isSelected()) {
                                    break;
                                }
                                if (i == contentdata.size() - 1) {
                                    selectedAll = true;
                                    shop_cart_bottom_selection.setChecked(true);
                                }
                            }
                        }
                        changeMoney();
                    }
                });
                AmountView amount_view = holder.getView(R.id.amount_view);
                amount_view.setAmount(contentdata.get(position).getCount());
                amount_view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                    @Override
                    public void onAmountChange(View view, int amount) {
//                        if (contentdata.get(position).isSelected()) {
//                            showToast("选中产品不能更改数量");
//                        }
                        contentdata.get(position).setCount(amount);
                        cartPresenter.adjustGoodCount(ShopCartFragment.this, good.getGc_id(), amount, DataType.cart.goodsCountAdjust.toString());
                    }
                });
                //标题 - 店家
                if (titleIndices.get(position) == null) {
                    storeitem.setVisibility(View.GONE);
                } else {
                    storeitem.setVisibility(View.VISIBLE);
                    if (position == 0) {
                        holder.getView(R.id.decoration_view).setVisibility(View.GONE);
                    }
                    storeitem.setOnClickListener(listener);
                    holder.setText(R.id.store_name_tt, titleIndices.get(position));
                }

                holder.setText(R.id.good_name, good.getGoods_name());
                String specInfo = good.getSpec_info();
                if (good.isSecurities()) {
                    specInfo = "产品券 " + specInfo;
                } else {
                    specInfo = "产品 " + specInfo;
                }
                holder.setText(R.id.good_sku, specInfo);
                holder.setText(R.id.good_price, good.getGoods_price() + "");
                holder.setImageByUrl(R.id.good_img, good.getGoods_image());
            }
        };
        getShopCart();
        shop_cart_list.setLoadingMoreEnabled(false);
        shop_cart_list.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //在刷新监听里清空数据，包括了adapter中的数据
                if (null != contentdata)
                    contentdata.clear();
                mAdapter.clear();
                getShopCart();//重新获得数据
                selectedAll = false;//关闭全选按钮记录
            }

            @Override
            public void onLoadMore() {
//                shop_cart_list.loadMoreComplete();
            }
        });
        shop_cart_list.setAdapter(mAdapter);
        shop_cart_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedAll = false;
                manage_tt.setText(getResources().getString(R.string.manage));
                manage_ll.setVisibility(View.GONE);
                pay_ll.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void getShopCart() {
        cartPresenter.lookgoodcart(ShopCartFragment.this, DataType.cart.look_goods_cart.toString());
    }

    /**
     * 但选中购物车中某个商品时，显示价格
     * 通过遍历contentdata中的数据来实现
     */
    private void changeMoney() {
        double aggregate_money = 0;
        if (contentdata != null) {
            for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : new ArrayList<>(contentdata)) {
                if (entity.isSelected())
                    aggregate_money = aggregate_money + (entity.getGoods_price() * entity.getCount());
            }
            shop_cart_money.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", aggregate_money));
        }
    }

    private String getSelectedGc_id() {
        String selected = "";
        for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : contentdata) {
            if (entity.isSelected()) {
                selected = selected + entity.getGc_id() + ",";
            }
        }
        if (selected != null && !selected.equals(""))
            selected = selected.substring(0, selected.length() - 1);
        return selected;
    }

    private boolean hasInvalidGood() {
        for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : contentdata) {
            if (entity.isSelected() && entity.getStatus() == -1) {
                return true;
            }
        }
        return false;
    }

    private String getSelectedGoodID() {
        String selected = "";
        for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : contentdata) {
            if (entity.isSelected()) {
                selected = selected + entity.getGoods_id() + ",";
            }
        }
        if (selected != null && !selected.equals(""))
            selected = selected.substring(0, selected.length() - 1);
        return selected;
    }

    private ArrayList<LookShopcartBean.ContentEntity.Store_cart_listEntity> getSelectedList() {
        ArrayList<LookShopcartBean.ContentEntity.Store_cart_listEntity> arrayList = new ArrayList<>();
        for (LookShopcartBean.ContentEntity.Store_cart_listEntity entity : contentdata) {
            if (entity.isSelected()) {
                arrayList.add(entity);
            }
        }
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_cart_pay:
                if (getSelectedList().size() == 0 || getSelectedGc_id().equals("")) {
                    showToast("请选择产品");
                    return;
                }
                if (hasInvalidGood()) {
                    showToast("失效产品无法结算");
                    return;
                }
                Intent intent = new Intent(getContext(), ShopCartPayActivity.class);
                intent.putExtra("selected", getSelectedList());
                intent.putExtra("gc_ids", getSelectedGc_id());

                startActivity(intent);
                break;
            case R.id.manage_tt:
                if (manage_tt.getText().equals(getResources().getString(R.string.manage))) {
                    manage_tt.setText(getResources().getString(R.string.done));
                    pay_ll.setVisibility(View.GONE);
                    manage_ll.setVisibility(View.VISIBLE);
//                    shop_cart_bottom.setVisibility(View.VISIBLE);
                } else {
                    manage_tt.setText(getResources().getString(R.string.manage));
//                    shop_cart_bottom.setVisibility(View.GONE);
                    pay_ll.setVisibility(View.VISIBLE);
                    manage_ll.setVisibility(View.GONE);
                }
                changeMoney();
                break;
            case R.id.select_all_ll:    //全选按钮,遍历adapter的数据改变选择状态同时改变总金额
                if (selectedAll) {
                    for (LookShopcartBean.ContentEntity.Store_cart_listEntity gooditem : contentdata) {
                        gooditem.setSelected(false);
                    }
                    selectedAll = false;
                    shop_cart_bottom_selection.setChecked(false);
                } else {
                    for (LookShopcartBean.ContentEntity.Store_cart_listEntity gooditem : contentdata) {
                        gooditem.setSelected(true);
                    }
                    selectedAll = true;
                    shop_cart_bottom_selection.setChecked(true);
                }
                mAdapter.clear();
                mAdapter.setNewData(contentdata);
                changeMoney();
                break;
            case R.id.reload_button:
                reload_rl.setVisibility(View.GONE);
                getShopCart();
                break;
            case R.id.delete_tt:
                if (getSelectedList().size() == 0) {
                    showToast("请选择产品");
                    return;
                }
                cartPresenter.removeGoodsCart(ShopCartFragment.this, getSelectedGc_id(), DataType.cart.removeGoodsCart.toString());
                break;
            case R.id.add_collect_tt:
                if (getSelectedList().size() == 0) {
                    showToast("请选择产品");
                    return;
                }
                if (hasInvalidGood()) {
                    showToast("失效产品无法收藏");
                    return;
                }
                cartPresenter.collectCartGood(ShopCartFragment.this, getSelectedGoodID(), DataType.cart.collectGood.toString());
                break;
        }
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        shop_cart_bottom_selection.setChecked(false);
        if (dataType.equals(DataType.cart.look_goods_cart.toString())) {
            shop_cart_list.refreshComplete();
            LookShopcartBean lookShopcartBean = (LookShopcartBean) object;
            contentdata = lookShopcartBean.getContent().getStore_cart_list();
            if (contentdata != null && contentdata.size() > 0) {
                mAdapter.setNewData(contentdata);
                no_good_ll.setVisibility(View.GONE);
                manage_tt.setVisibility(View.VISIBLE);
                pay_ll.setVisibility(View.VISIBLE);
                manage_ll.setVisibility(View.GONE);
                manage_tt.setText(getResources().getString(R.string.manage));
                shop_cart_bottom.setVisibility(View.VISIBLE);
                changeMoney();//为了刷新购物车。。。
            } else {
                mAdapter.clear();
                no_good_ll.setVisibility(View.VISIBLE);
                manage_tt.setVisibility(View.GONE);
                shop_cart_bottom.setVisibility(View.GONE);
            }
            shop_cart_list.setVisibility(View.VISIBLE);
            reload_rl.setVisibility(View.GONE);
        } else if (dataType.equals(DataType.cart.removeGoodsCart.toString())) {
            LookShopcartBean lookShopcartBean = (LookShopcartBean) object;
            showToast(lookShopcartBean.getMessage());
            contentdata = lookShopcartBean.getContent().getStore_cart_list();
            if (contentdata != null && contentdata.size() > 0) {
                mAdapter.setNewData(contentdata);
                no_good_ll.setVisibility(View.GONE);
                manage_tt.setVisibility(View.VISIBLE);
                shop_cart_bottom.setVisibility(View.VISIBLE);
            } else {
                mAdapter.clear();
                no_good_ll.setVisibility(View.VISIBLE);
                manage_tt.setVisibility(View.GONE);
                shop_cart_bottom.setVisibility(View.GONE);
            }
        } else if (dataType.equals(DataType.cart.collectGood.toString())) {
            JsonModel jsonModel = (JsonModel) object;
            try {
                if (jsonModel.getStatus().equals("0")) {
                    showToast(getResources().getString(R.string.add_collect_success));
                    selectedAll = false;
                    manage_ll.setVisibility(View.GONE);
                    pay_ll.setVisibility(View.VISIBLE);
                }
            } catch (Resources.NotFoundException e) {
            }
        } else if (dataType.equals(DataType.cart.goodsCountAdjust.toString())) {
            JsonModel jsonModel = (JsonModel) object;
            try {
                if (jsonModel.getStatus().equals("0")) {
                    changeMoney();
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        if (dataType.equals(DataType.cart.look_goods_cart.toString())) {
            reload_rl.setVisibility(View.VISIBLE);
            shop_cart_list.setVisibility(View.GONE);
            no_good_ll.setVisibility(View.GONE);
            shop_cart_bottom.setVisibility(View.GONE);
        } else {
            showToast(getResources().getString(R.string.no_network_tips));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        manage_tt.setText(getResources().getString(R.string.manage));
        pay_ll.setVisibility(View.VISIBLE);
        manage_ll.setVisibility(View.GONE);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        manage_tt.setText(getResources().getString(R.string.manage));
        pay_ll.setVisibility(View.VISIBLE);
        manage_ll.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        getShopCart();//刷新购物车列表
        if (contentdata!=null){
            Log.d("gouwuche",contentdata.size()+" onStart");
        }

        changeMoney();//放置支付后应付金额未清零
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        ((MainActivity) getActivity()).currentPage = 3;
        EventBus.getDefault().post(new ActionBarEvent("action"));
    }
}