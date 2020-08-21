package com.dajukeji.hslz.activity.userlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.usersetting.agreement.PrivacyPolicyActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.freeOrder.BandPhoneBean;
import com.dajukeji.hslz.event.HavaFreeOrderEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.UserLoginPresenter;
import com.dajukeji.hslz.util.SPUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 绑定手机号 - 设置密码
 */
public class LoginPasswordActivity extends HttpBaseActivity {

    private UserLoginPresenter userLoginPresenter;

    private EditText et_password, et_sure_password; // 密码 确认密码
    private Button bt_phone_login; // 绑定
    private CheckBox agree_checkBox; // 同意服务协议
    private LinearLayout ll_syb_agree, ll_privacy; // 服务协议 隐私协议
    private LinearLayout ll_agree;
    private boolean agree = false; // 是否勾选协议
    private String code = ""; // 验证码
    private String phoneNumber = ""; // 手机号
    private String invitationCode = null;//邀请码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginPresenter = new UserLoginPresenter(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        agree = getIntent().getBooleanExtra("agree", false);
        code = getIntent().getStringExtra("code");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        invitationCode = getIntent().getStringExtra("invitationCode");
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_login_password);
        et_password = (EditText) findViewById(R.id.et_password);
        et_sure_password = (EditText) findViewById(R.id.et_sure_password);
        agree_checkBox = (CheckBox) findViewById(R.id.agree_checkBox);
        ll_syb_agree = (LinearLayout) findViewById(R.id.ll_syb_agree);
        ll_privacy = (LinearLayout) findViewById(R.id.ll_privacy);
        ll_agree = (LinearLayout) findViewById(R.id.ll_agree);
        bt_phone_login = (Button) findViewById(R.id.bt_phone_login);
        agree_checkBox.setChecked(agree);

        findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_phone_login.setOnClickListener(this);
        ll_syb_agree.setOnClickListener(this);
        ll_privacy.setOnClickListener(this);
        ll_agree.setOnClickListener(this);
        et_sure_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    bt_phone_login.setBackgroundResource(R.drawable.btn_phone_sure);
                } else {
                    bt_phone_login.setBackgroundResource(R.drawable.btn_phone_login);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.bt_phone_login: // 点击绑定
                if (et_password.getText().toString().equals(et_sure_password.getText().toString())) {
                    if (agree_checkBox.isChecked()) {
                        bandPhoneNumber();
                    } else {
                        showToast("请同意协议与隐私");
                    }
                } else {
                    showToast("两次密码不一致");
                }
                break;
            case R.id.ll_syb_agree: // 服务协议
                Intent serviceIntent = new Intent(LoginPasswordActivity.this, PrivacyPolicyActivity.class);
                serviceIntent.putExtra("page", "service");
                startActivity(serviceIntent);
                break;
            case R.id.ll_privacy: // 隐私协议
                Intent policyIntent = new Intent(LoginPasswordActivity.this, PrivacyPolicyActivity.class);
                policyIntent.putExtra("page", "service");
                startActivity(policyIntent);
                break;
            case R.id.ll_agree:
                if (agree_checkBox.isChecked()) {
                    agree_checkBox.setChecked(false);
                } else {
                    agree_checkBox.setChecked(true);
                }
                break;
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.userLogin.bandPhone.toString())) {
            BandPhoneBean bandPhoneBean = (BandPhoneBean) object;
            if (bandPhoneBean.getStatus().equals("0")) {
                showToast("绑定成功");
                SPUtil.setPrefString("phoneNumber", phoneNumber);
                //以前的逻辑
                if (bandPhoneBean.getContent().getHave_free_order() == 1) {
                    EventBus.getDefault().post(new HavaFreeOrderEvent("have"));
//                    finish();//大刘：现在绑定成功后直接关闭页面即可
                }
                //需要刷新用户信息
                if (bandPhoneBean.getContent().getIsFor() == 0) {
                    userLoginPresenter.refreshUserInfo(this, phoneNumber, "刷新用户信息");
                } else {
                    finish();
                }
            }
        } else if (contentType.equals("刷新用户信息")) {
            finish();
        }
    }

    /**
     * 绑定手机号
     */
    private void bandPhoneNumber() {
        userLoginPresenter.bandPhoneNumber(getContext(), phoneNumber, code, et_password.getText().toString(), SPUtil.getPrefString("token", ""), invitationCode, DataType.userLogin.bandPhone.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
