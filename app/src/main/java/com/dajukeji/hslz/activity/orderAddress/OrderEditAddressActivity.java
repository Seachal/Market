package com.dajukeji.hslz.activity.orderAddress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.address.AreaSelectActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.util.PhoneFormatCheckUtils;
import com.dajukeji.hslz.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by ${wangjiasheng} on 2017/12/14 0014.
 * 添加默认地址或者编辑现有的一条地址， 根据editExistAddress判断
 */

public class OrderEditAddressActivity extends HttpBaseActivity {

    private LinearLayout select_area; // 选择省市区地址
    private TextView area; // 省市区选中地址
    private EditText trueNametv, mobiletv, area_infotv; // 收货人，电话，详细地址
    private String area_Id; //地区Id
    private AddressPresenter addressPresenter;
    private boolean editExistAddress;
    private long id;
    private String trueName;
    private String address;
    private String area_info;
    private String mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if ((editExistAddress = intent.getBooleanExtra("editExistAddress", false))) {
            id = intent.getLongExtra("id", -1);
            area_Id = intent.getStringExtra("area_id");
            trueName = intent.getStringExtra("true_name");
            address = intent.getStringExtra("address");
            area_info = intent.getStringExtra("area_info");
            mobile = intent.getStringExtra("mobile");
            trueNametv.setText(trueName);
            mobiletv.setText(mobile);
            area_infotv.setText(area_info);
            area.setText(address);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_add_address);
        setTitleBar(getResources().getString(R.string.add_address), true, getResources().getString(R.string.save));
        trueNametv = (EditText) findViewById(R.id.trueName);
        mobiletv = (EditText) findViewById(R.id.mobile);
        area_infotv = (EditText) findViewById(R.id.area_info);
        area = (TextView) findViewById(R.id.area);
        select_area = (LinearLayout) findViewById(R.id.select_area);
        select_area = (LinearLayout) findViewById(R.id.select_area);
        select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //选择区域
                Intent intent = new Intent(OrderEditAddressActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        addressPresenter = new AddressPresenter(this);
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
            area_Id = map.get("area_Id") + "";// 地区ID
        }
    }

    @Override
    protected void clickRightTitle() {
        super.clickRightTitle();
        if (area_infotv.getText().toString().equals("") || mobiletv.getText().toString().equals("") || trueNametv.getText().toString().equals("") || area_Id == null || area_Id.equals("")) {
            showToast("请填写完整地址与收货人信息");
        } else {
            if(PhoneFormatCheckUtils.isPhoneLegal(mobiletv.getText().toString())){
                if (editExistAddress) {
                    addressPresenter.updateAddress(getContext(), id, area_infotv.getText().toString(), mobiletv.getText().toString(), trueNametv.getText().toString(), area_Id, DataType.address.updateAddress.toString());
                } else {
                    addressPresenter.addAddress(getContext(), SPUtil.getPrefString("token",""),area_infotv.getText().toString(), mobiletv.getText().toString(), trueNametv.getText().toString(), area_Id, DataType.address.addAddress.toString());
                }
            }else {
                showToast("请填写正确手机号码");
            }
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        showToast(getResources().getString(R.string.save_success));
        if (contentType.equals(DataType.address.addAddress.toString())) {
            EventBus.getDefault().post(new AddressChangeEvent(false));
        } else if (contentType.equals(DataType.address.updateAddress.toString())) {
            EventBus.getDefault().post(new AddressChangeEvent(false));
        }
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(OrderEditAddressActivity.this);
    }
}
