package com.dajukeji.hslz.activity.orderAddress;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.OrderDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27 0027.
 */

public class AddressManageActivity extends HttpBaseActivity {

    private XRecyclerView xRecyclerView;
    private BaseRecyclerAdapter<UserAddressBean.ContentBean> madapter;
    private List<UserAddressBean.ContentBean> addressList;
    private Button add_area;
    private AddressPresenter addressPresenter;
    private int defaultAddressPosition;
    private int deletePosition;
    //是选择地址模式，点击会返回地址信息
    private boolean isSelectMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isSelectMode = getIntent().getBooleanExtra("isSelectMode", false);
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelTag(AddressManageActivity.this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_address_manage);
        setTitleBar(getResources().getString(R.string.address_manage), true);
        xRecyclerView = (XRecyclerView) findViewById(R.id.address_list);
        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        madapter = new BaseRecyclerAdapter<UserAddressBean.ContentBean>(getContext(), null, R.layout.item_address_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, final UserAddressBean.ContentBean data, final int position, boolean isScrolling) {
                holder.setText(R.id.consignee_name, data.getTrueName());
                holder.setText(R.id.consignee_phone, data.getMobile());
                holder.setText(R.id.consignee_address, data.getAddress() + data.getArea_info());

                final ImageView default_address = holder.getView(R.id.default_address);
                final TextView tv_default = holder.getView(R.id.tv_default);
                if (data.getDefault_address().equals("1")) {
                    default_address.setImageResource(R.drawable.checkbox_true);
                    tv_default.setTextColor(Color.parseColor("#ff4f00"));
                    tv_default.setText(getResources().getString(R.string.default_address));
                } else {
                    default_address.setImageResource(R.drawable.checkbox_false);
                    tv_default.setTextColor(Color.parseColor("#757575"));
                    tv_default.setText(getResources().getString(R.string.set_default_address));
                }
                holder.getView(R.id.ll_set_default).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addressPresenter.setDefaultAddress(AddressManageActivity.this, data.getId(), DataType.address.setDefaultAddress.toString());
                        defaultAddressPosition = position;
                    }
                });
                holder.getView(R.id.image_edit_area).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AddressManageActivity.this, OrderEditAddressActivity.class);
                        intent.putExtra("editExistAddress", true);
                        intent.putExtra("id", data.getId());
                        intent.putExtra("default_address", data.getDefault_address());
                        intent.putExtra("area_id", data.getArea_id() + "");
                        intent.putExtra("true_name", data.getTrueName());
                        intent.putExtra("address", data.getAddress());
                        intent.putExtra("area_info", data.getArea_info());
                        intent.putExtra("mobile", data.getMobile());
                        startActivity(intent);
                    }
                });
                holder.getView(R.id.image_delete_area).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderDialog orderDialog = new OrderDialog(getContext(), "确认删除地址?", "", "确认");
                        orderDialog.show();
                        orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                            @Override
                            public void sureClick() {
                                deletePosition = position;
                                addressPresenter.deleteAddress(AddressManageActivity.this, data.getId(), DataType.address.deleteAddress.toString());
                            }
                        });

                    }
                });
            }
        };
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });
        xRecyclerView.setAdapter(madapter);
        add_area = (Button) findViewById(R.id.add_area);
        add_area.setOnClickListener(this);

        //选择地址模式,点击地址返回
        if (isSelectMode) {
            madapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<UserAddressBean.ContentBean>() {
                @Override
                public void onItemClick(BaseRecyclerHolder viewHolder, UserAddressBean.ContentBean data, int position) {
                    Intent intent = new Intent();
                    intent.putExtra("addressId", data.getId());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_area:
                Intent intent = new Intent(AddressManageActivity.this, OrderEditAddressActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getAddressList(AddressManageActivity.this, SPUtil.getPrefString("token", ""), DataType.address.getAddressList.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.address.getAddressList.toString())) {
            UserAddressListBean bean = (UserAddressListBean) object;
            addressList = bean.getContent().getAddressList();
            madapter.setNewData(addressList);
            xRecyclerView.refreshComplete();
        } else if (contentType.equals(DataType.address.setDefaultAddress.toString())) {
            for (int i = 0; i < addressList.size(); i++) {
                addressList.get(i).setDefault_address("0");
                if (i == defaultAddressPosition) {
                    addressList.get(i).setDefault_address("1");
                }
            }
            madapter.setNewData(addressList);
        } else if (contentType.equals(DataType.address.deleteAddress.toString())) {
            UserAddressListBean bean = (UserAddressListBean) object;
            addressList = bean.getContent().getAddressList();
            madapter.setNewData(addressList);
            if (addressList != null && addressList.size() == 0) {
                EventBus.getDefault().post(new AddressChangeEvent(true));
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        if (!event.removed) {
            initData();
        }
    }
}
