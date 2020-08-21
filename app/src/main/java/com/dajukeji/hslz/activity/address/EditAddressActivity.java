package com.dajukeji.hslz.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.SmoothCheckBox;
import com.dajukeji.hslz.view.dialog.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.dajukeji.hslz.network.HttpAddress.mBaseUrl2;


/**
 * 地址编辑
 */
public class EditAddressActivity extends AppCompatActivity {

    private LinearLayout select_area,ll_set_default; // 选择省市区地址 、设为默认地址
    private TextView area; // 省市区选中地址
    private TextView save_area; // 保存地址
    private LinearLayout show_delete; //是否显示删除地址选项
    private TextView trueName ,mobile,area_info; // 收货人，电话，详细地址
    private SmoothCheckBox default_address; // 选择默认地址
    private String area_Id; //地区Id
    private String addressId; // 用户地址ID
    private UserAddressBean addressBean; // 用户地址
    private String isDefault = "0"; // 是否默认地址 0不是 1是

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        addressId = getIntent().getStringExtra("addressId");
        initDate();
        initView();
    }

    private void initDate() {
        if(addressId!=null&&!addressId.equals("")){ // 修改存在地址
            getUserAddress(addressId); // 获取用户地址
        }
    }

    private void initView() {
        RelativeLayout back = (RelativeLayout) findViewById(R.id.title_bar_return); // 返回
        select_area = (LinearLayout) findViewById(R.id.select_area);
        ll_set_default = (LinearLayout) findViewById(R.id.ll_set_default);
        area = (TextView) findViewById(R.id.area);
        save_area = (TextView) findViewById(R.id.save_area);
        trueName = (TextView) findViewById(R.id.trueName);
        mobile = (TextView) findViewById(R.id.mobile);
        area_info = (TextView) findViewById(R.id.area_info);
        default_address = (SmoothCheckBox) findViewById(R.id.default_address);

        select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //选择区域
                Intent intent = new Intent(EditAddressActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent, 100);
            }
        });

        save_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addressId!=null&&!addressId.equals("")){ // 修改地址
                    editAddress();
                }else{ // 保存新地址
                    addAddress();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() { // 返回
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private Pattern intPattern = Pattern.compile("^[-\\+]?[\\d]*\\.0*$");

    public String getString(Map map, String key, String defaultValue) {
        Object obj = map.get(key);
        return obj == null ? defaultValue : (obj instanceof Number && intPattern.matcher(obj.toString()).matches() ? String.valueOf(Long.valueOf(((Number) obj).longValue())) : obj.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择地区返回h
         */
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            final Map map = (Map) data.getSerializableExtra("addressInfo");
            String areaName = String.format("%s %s %s", getString(map, "provName", ""),
                    getString(map, "cityName", ""), getString(map, "districtName", ""));
            area.setText(areaName);
            area_Id = map.get("area_Id")+"";// 地区ID
        }
    }

    private void addAddress(){ // 新增地址
        if(default_address.isChecked()){ // 是否选择为默认地址
            isDefault = "1";
        }
        if(area_info.getText().toString().equals("")||mobile.getText().toString().equals("")||trueName.getText().toString().equals("")||area_Id==null||area_Id.equals("")){
            Toast.makeText(this,"请填写完整地址与收货人信息",Toast.LENGTH_SHORT).show();
        }else{
            String url = mBaseUrl2+ HttpAddress.addAddress;
            OkHttpUtils.post().url(url)
                    .addParams("token", SPUtil.getPrefString("token",""))
                    .addParams("area_info",area_info.getText().toString()) // 详细地址
                    .addParams("mobile",mobile.getText().toString()) // 电话
                    .addParams("trueName",trueName.getText().toString()) // 姓名
                    .addParams("area_id",area_Id) // 地区ID
                    .addParams("default_address",isDefault)  //是否是默认地址
                    .build()//
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            sendBroadcast(); // 发送广播跟新地址
                            finish();
                        }
                    });
        }

    }

    private void editAddress(){ // 修改地址
        if(addressBean.getContent().getDefault_address().equals("1")||default_address.isChecked()){ // 是否选择为默认地址
            isDefault = "1";
        }
        if(area_Id==null||area_Id.equals("")){ // 是否重新选择地区
            area_Id= "";
        }
        if(area_info.getText().toString().equals("")||mobile.getText().toString().equals("")||trueName.getText().toString().equals("")){
            Toast.makeText(this,"请填写完整地址与收货人信息",Toast.LENGTH_SHORT).show();
        }else{
            String url = mBaseUrl2+HttpAddress.updateAddress;
            OkHttpUtils.post().url(url)
                    .addParams("token",SPUtil.getPrefString("token",""))
                    .addParams("id",addressId) // 当前地址ID
                    .addParams("area_info",area_info.getText().toString())  //详细地址
                    .addParams("mobile",mobile.getText().toString()) // 电话
                    .addParams("trueName",trueName.getText().toString()) // 姓名
                    .addParams("area_id",area_Id) // 地区ID
                    .addParams("default_address",isDefault) // 是否是默认地址
                    .build()//
                    .execute(new Callback() {
                        @Override
                        public Object parseNetworkResponse(Response response, int id) throws Exception {
                            return null;
                        }

                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(Object response, int id) {
                            sendBroadcast();// 发送广播跟新地址
                            finish();
                        }
                    });
        }
    }

    private void getUserAddress(String key) // 获得地址
    {
        //http://192.168.0.110:8003/wemall/app/
        String url = mBaseUrl2+HttpAddress.getAddressDetail;
        OkHttpUtils
                .post()
                .url(url)
                .addParams("addressId",key)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 网络请求回调
     */
    public class MyStringCallback extends StringCallback {  //获得用户详细地址
        public void onBefore(Request request, int id)
        {
            LoadingDialog.showLoadingDialog(EditAddressActivity.this, null);
        }

        @Override
        public void onAfter(int id)
        {
            LoadingDialog.hideLoadingDialog();
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Toast.makeText(EditAddressActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            addressBean =  (UserAddressBean)gson.fromJson(response,UserAddressBean.class);
            if(addressBean.getStatus().equals("0")) {
                trueName.setText(addressBean.getContent().getTrueName());
                mobile.setText(addressBean.getContent().getMobile());
                area_info.setText(addressBean.getContent().getArea_info());
                area.setText(addressBean.getContent().getAddress());
                if(addressBean.getContent().getDefault_address().equals("1")){
                    ll_set_default.setVisibility(View.INVISIBLE);
                }
            }else{
                Toast.makeText(EditAddressActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendBroadcast(){ // 发送广播
        Intent intent = new Intent();  //发送修改地址广播
        intent.setAction("addressChange");
        EditAddressActivity.this.sendBroadcast(intent);
    }

}
