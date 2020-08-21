package com.dajukeji.hslz.activity.address;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.CommonAdapter;
import com.dajukeji.hslz.util.ViewHolder;
import com.dajukeji.hslz.view.dialog.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 地址列表
 */
public class AddressListActivity extends AppCompatActivity {

    private ListView address_list;
    private BaseAdapter myAdapter;
    private List<UserAddressBean.ContentBean> Address = new ArrayList<>();
    private TextView manage_area; // 管理地址
    private Map map = new HashMap(); // 存放选中地址信息
    private MyReceiver receiver; //广播接收者
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_address_list);

        receiver = new MyReceiver(new Handler()); // Create the receiver //创建广播
        this.registerReceiver(receiver, new IntentFilter("addressChange")); // 注册广播，并过滤Intent

        initDate();
        initView();
    }

    private void initDate() {
        getUserAddressList(); // 获取用户地址
    }

    private void initView() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.back); // 返回
        address_list = (ListView) findViewById(R.id.address_list);
        manage_area = (TextView) findViewById(R.id.manage_area);


        myAdapter=new CommonAdapter<UserAddressBean.ContentBean>(this,Address,R.layout.item_address_list_list){

            @Override
            public void convert(ViewHolder helper, UserAddressBean.ContentBean item) {
                TextView consignee_username =  helper.getView(R.id.consignee_name);
                TextView consignee_phone = helper.getView(R.id.consignee_phone);
                TextView consignee_address =  helper.getView(R.id.consignee_address);

                consignee_username.setText(item.getTrueName());
                consignee_phone.setText(item.getMobile());
                consignee_address.setText(item.getAddress()+item.getArea_info()+"");
            }
        };

        address_list.setAdapter(myAdapter);
        manage_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressListActivity.this, AddressActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        address_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView consignee_username =  (TextView) view.findViewById(R.id.consignee_name);
                TextView consignee_phone = (TextView)  view.findViewById(R.id.consignee_phone);
                TextView consignee_address =  (TextView) view.findViewById(R.id.consignee_address);


                Intent intent = new Intent();
                map.put("consignee_username" , consignee_username.getText().toString());
                map.put("consignee_phone" , consignee_phone.getText().toString());
                map.put("consignee_address" , consignee_address.getText().toString());
                map.put("addressId",Address.get(position).getId());
                intent.putExtra("consigneeInfo", (Serializable) map);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void getUserAddressList()
    {
        //http://192.168.0.110:8003/wemall/app/
        String url = HttpAddress.mBaseUrl2+HttpAddress.getAddressList;
        OkHttpUtils
                .post()
                .url(url)
                .build()
                .execute(new MyStringCallback());
    }


    /**
     * 网络请求回调
     */
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            LoadingDialog.showLoadingDialog(AddressListActivity.this, null);
        }

        @Override
        public void onAfter(int id) {
            LoadingDialog.hideLoadingDialog();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(AddressListActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            UserAddressListBean userAddress=(UserAddressListBean) gson.fromJson(response, UserAddressListBean.class);
            if (userAddress.getStatus().equals("0")){
                Address.addAll(userAddress.getContent().getAddressList());
                myAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(AddressListActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            //MLog.INSTANCE.e(TAG, "inProgress:" + progress);
            //mProgressBar.setProgress((int) (100 * progress));
        }
    }

    private  class MyReceiver extends BroadcastReceiver {  // 广播接接收者，更新用户收货地址

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
                    Address.clear();  //清除原先地址
                    getUserAddressList();
                    address_list.setAdapter(myAdapter);
                }
            });
        }
    }

}
