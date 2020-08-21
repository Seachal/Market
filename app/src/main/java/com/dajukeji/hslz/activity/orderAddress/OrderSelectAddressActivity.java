package com.dajukeji.hslz.activity.orderAddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.adapter.recycleradapter.SpacesItemDecoration;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by ${wangjiasheng} on 2017/12/15 0015.
 */

public class OrderSelectAddressActivity extends HttpBaseActivity {

    private AddressPresenter addressPresenter;
    private BaseRecyclerAdapter<UserAddressBean.ContentBean> recyclerAdapter;
    private XRecyclerView xRecyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void clickRightTitle() {
        super.clickRightTitle();
        Intent intent = new Intent(OrderSelectAddressActivity.this, AddressManageActivity.class);
        startActivity(intent);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_order_select_address);
        setTitleBar(getResources().getString(R.string.select_address), true, getResources().getString(R.string.manage));
        xRecyclerView = (XRecyclerView) findViewById(R.id.address_list);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        xRecyclerView.addItemDecoration(new SpacesItemDecoration(1, 0));
        recyclerAdapter = new BaseRecyclerAdapter<UserAddressBean.ContentBean>(getContext(), null, R.layout.item_select_address) {
            @Override
            public void convert(BaseRecyclerHolder holder, final UserAddressBean.ContentBean data, int position, boolean isScrolling) {
                holder.setText(R.id.consignee_name, data.getTrueName());
                holder.setText(R.id.consignee_phone, data.getMobile());
                holder.setText(R.id.consignee_address, data.getAddress()+data.getArea_info());
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<UserAddressBean.ContentBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, UserAddressBean.ContentBean data, int position) {
                SelectAddressEvent event = new SelectAddressEvent(data);
                EventBus.getDefault().post(event);
                finish();
            }
        });
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setAdapter(recyclerAdapter);

    }
    @Override
    protected void initData() {
        super.initData();
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getAddressList(OrderSelectAddressActivity.this,  SPUtil.getPrefString("token",""), DataType.address.getAddressList.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(OrderSelectAddressActivity.this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event){
        initData();
    }
    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.address.getAddressList.toString())) {
            xRecyclerView.refreshComplete();
            UserAddressListBean userAddressListBean = (UserAddressListBean) object;
            recyclerAdapter.setNewData(userAddressListBean.getContent().getAddressList());
        }
    }
}
