package com.dajukeji.hslz.activity.address;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.util.CommonAdapter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ViewHolder;
import com.dajukeji.hslz.view.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 */
public class AddressActivity extends HttpBaseActivity implements View.OnClickListener {

    private ListView address_list;
    private BaseAdapter myAdapter;
    private List<UserAddressBean.ContentBean> Address = new ArrayList<>();
    private TextView add_area; // 点击进行新增地址
    private MyReceiver receiver; //广播接收者
    private AddressPresenter addressPresenter;
    private Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    myAdapter.notifyDataSetChanged(); // 修改地址列表
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receiver = new MyReceiver(new Handler()); // Create the receiver //创建广播
        this.registerReceiver(receiver, new IntentFilter("addressChange")); // 注册广播，并过滤Intent
        initView();
    }

    private void initDate() {
        getUserAddressList(); // 获取用户地址
    }

    protected void initView() {

        setContentView(R.layout.activity_address);
        setTitleBar(getResources().getString(R.string.address_manage), true);
        initDate(); // 初始化数据
        address_list = (ListView) findViewById(R.id.address_list);
        add_area = (TextView) findViewById(R.id.add_area);

        myAdapter = new CommonAdapter<UserAddressBean.ContentBean>(this, Address, R.layout.item_address_list) {

            @Override
            public void convert(ViewHolder helper, final UserAddressBean.ContentBean item) {
                TextView consignee_username = helper.getView(R.id.consignee_name);
                TextView consignee_phone = helper.getView(R.id.consignee_phone);
                TextView consignee_address = helper.getView(R.id.consignee_address);
                LinearLayout ll_set_default = helper.getView(R.id.ll_set_default);
                LinearLayout image_edit_area = helper.getView(R.id.image_edit_area); // 点中地址项进行修改
                LinearLayout image_delete_area = helper.getView(R.id.image_delete_area); // 点中地址项进行删除

                final SmoothCheckBox default_address = helper.getView(R.id.default_address);
                TextView tv_default = helper.getView(R.id.tv_default);
                default_address.setEnabled(false);
                if (item.getDefault_address().equals("1")) {  // 是否选择为默认地址
                    default_address.setChecked(true);
                    tv_default.setTextColor(Color.parseColor("#ff4f00"));
                    tv_default.setText("默认地址");
                } else {
                    default_address.setChecked(false);
                    tv_default.setTextColor(Color.parseColor("#757575"));
                    tv_default.setText("设为默认地址");
                }
                consignee_username.setText(item.getTrueName());
                consignee_phone.setText(item.getMobile());
                consignee_address.setText(item.getAddress() + item.getArea_info() + "");

                image_edit_area.setOnClickListener(new View.OnClickListener() { // 修改地址
                    @Override
                    public void onClick(View v) {
                        Intent eIIntent = new Intent(AddressActivity.this, EditAddressActivity.class);
                        eIIntent.putExtra("addressId", item.getId() + "");
                        startActivity(eIIntent);
                    }
                });
                image_delete_area.setOnClickListener(new View.OnClickListener() { // 删除地址
                    @Override
                    public void onClick(View v) {
                        deleteUserAddress(item.getId());
                    }
                });
                ll_set_default.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateUserAddressList(item.getId());
                    }
                });

            }
        };
        address_list.setAdapter(myAdapter);
        add_area.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_area:
                Intent adIntent = new Intent(AddressActivity.this, EditAddressActivity.class);
                startActivity(adIntent);
                break;
        }
    }

    /**
     * 获得用户地址列表
     */
    public void getUserAddressList() {
//        String url = HttpAddress.mBaseUrl2 + HttpAddress.getAddressList;
//        OkHttpUtils
//                .post()
//                .url(url)
//                .addParams(HttpAddress.tokenKey, HttpAddress.token)
//                .build()
//                .execute(new MyStringCallback());
        addressPresenter = new AddressPresenter(this);
        addressPresenter.getAddressList(AddressActivity.this, SPUtil.getPrefString("token",""), DataType.address.getAddressList.toString());
    }

    /**
     * 网络请求回调 获得用户地址列表
     */
//    private class MyStringCallback extends StringCallback {
//        private void updateList() {
//            Message msg = Message.obtain();
//            msg.what = 0;
//            myhandler.sendEmptyMessage(0);
//        }
//
//        @Override
//        public void onBefore(Request request, int id) {
//            LoadingDialog.showLoadingDialog(AddressActivity.this, null);
//        }
//
//        @Override
//        public void onAfter(int id) {
//            LoadingDialog.hideLoadingDialog();
//        }
//
//        @Override
//        public void onError(Call call, Exception e, int id) {
//            Toast.makeText(AddressActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onResponse(String response, int id) {
//            Gson gson = new Gson();
//            UserAddressListBean userAddress = (UserAddressListBean) gson.fromJson(response, UserAddressListBean.class);
//            if (userAddress.getStatus().equals("0")) {
//                Address.clear();
//                Address.addAll(userAddress.getContent().getAddressList());
//                updateList();
//            } else {
//                Toast.makeText(AddressActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    /**
     * 设为默认地址
     */
    public void updateUserAddressList(long key) {
//        String url = HttpAddress.mBaseUrl2 + HttpAddress.setDefaultAddress;
//        OkHttpUtils
//                .post()
//                .url(url)
//                .addParams("addressId", key)
//                .addParams("token", "32792")
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        return null;
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(AddressActivity.this, "设置默认地址失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(Object response, int id) { // 更新用户地址列表
//                        getUserAddressList(); // 获取用户地址
//                    }
//                });
        addressPresenter.setDefaultAddress(AddressActivity.this, key, DataType.address.setDefaultAddress.toString());
    }

    /**
     * 删除用户地址
     */
    public void deleteUserAddress(long key) {
//        String url = HttpAddress.mBaseUrl2 + HttpAddress.deleteAddress;
//        OkHttpUtils
//                .post()
//                .addParams("addressId", key)
//                .addParams("token", "32792")
//                .url(url)
//                .build()
//                .execute(new Callback() {
//                    @Override
//                    public Object parseNetworkResponse(Response response, int id) throws Exception {
//                        return null;
//                    }
//
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Toast.makeText(AddressActivity.this, "删除地址失败", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onResponse(Object response, int id) { // 更新用户地址列表
//                        getUserAddressList(); // 获取用户地址
//                        Intent intent = new Intent();  //发送修改地址广播
//                        intent.setAction("addressChange");
//                        AddressActivity.this.sendBroadcast(intent);
//                    }
//                });
        addressPresenter.deleteAddress(AddressActivity.this, key, DataType.address.deleteAddress.toString());

    }

    /**
     * 广播接接收者，更新用户收货地址
     */
    private class MyReceiver extends BroadcastReceiver {

        private final Handler handler;

        public MyReceiver(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            // Post the UI updating code to our Handler
            handler.post(new Runnable() {
                @Override
                public void run() {  // 更新用户地址
                    getUserAddressList(); // 获取用户地址
                }
            });
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.address.deleteAddress.toString())) {
            getUserAddressList(); // 获取用户地址
            Intent intent = new Intent();  //发送修改地址广播
            intent.setAction("addressChange");
            AddressActivity.this.sendBroadcast(intent);
        } else if (contentType.equals(DataType.address.getAddressList.toString())) {
//            Gson gson = new Gson();
//            UserAddressListBean userAddress = (UserAddressListBean) gson.fromJson(response, UserAddressListBean.class);
//            if (userAddress.getStatus().equals("0")) {
//                Address.clear();
//                Address.addAll(userAddress.getContent().getAddressList());
//                updateList();
//            } else {
//                Toast.makeText(AddressActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
//            }
            UserAddressListBean userAddressListBean = (UserAddressListBean) object;
            if (userAddressListBean.getStatus().equals("0")) {
                Address.clear();
                Address.addAll(userAddressListBean.getContent().getAddressList());
                Message msg = Message.obtain();
                msg.what = 0;
                myhandler.sendEmptyMessage(0);
            }
        } else if (contentType.equals(DataType.address.setDefaultAddress.toString())) {
                getUserAddressList();
        }
    }
}
