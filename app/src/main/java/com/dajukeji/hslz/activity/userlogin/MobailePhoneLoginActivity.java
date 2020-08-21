package com.dajukeji.hslz.activity.userlogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.usersetting.agreement.PrivacyPolicyActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.UserLoginPresenter;
import com.dajukeji.hslz.util.PhoneFormatCheckUtils;

/**
 * 绑定手机号
 */
public class MobailePhoneLoginActivity extends HttpBaseActivity {

    private UserLoginPresenter userLoginPresenter;

    private EditText et_invitationCode, et_login_phone, et_login_code; // 手机号 验证码
    private TextView tv_get_code;  // 获取验证码
    private Button bt_phone_login; // 绑定
    private CheckBox agree_checkBox; // 同意服务协议
    private LinearLayout ll_syb_agree, ll_privacy; // 服务协议 隐私协议
    private LinearLayout ll_agree;
    private final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000, 1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLoginPresenter = new UserLoginPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_mobaile_phone_login);
        et_invitationCode = (EditText) findViewById(R.id.et_invitationCode);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_code = (EditText) findViewById(R.id.et_login_code);
        tv_get_code = (TextView) findViewById(R.id.tv_get_code);
        bt_phone_login = (Button) findViewById(R.id.bt_phone_login);
        agree_checkBox = (CheckBox) findViewById(R.id.agree_checkBox);
        ll_syb_agree = (LinearLayout) findViewById(R.id.ll_syb_agree);
        ll_privacy = (LinearLayout) findViewById(R.id.ll_privacy);
        ll_agree = (LinearLayout) findViewById(R.id.ll_agree);

        findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_get_code.setOnClickListener(this);
        bt_phone_login.setOnClickListener(this);
        ll_syb_agree.setOnClickListener(this);
        ll_privacy.setOnClickListener(this);
        ll_agree.setOnClickListener(this);

        //大刘：在修改手机号时启用验证码按钮
        et_login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (PhoneFormatCheckUtils.isPhoneLegal(s.toString())) {
                    myCountDownTimer.cancel();
                    myCountDownTimer.onFinish();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_get_code: // 获取验证码
                if (!et_login_phone.getText().toString().equals("")) {
                    if (PhoneFormatCheckUtils.isPhoneLegal(et_login_phone.getText().toString())) {
                        sendBandPhoneNumberCode();  // 获取验证码
                        myCountDownTimer.start();
                    } else {
                        showToast("请输入正确手机号码");
                    }
                } else {
                    showToast("请填写手机号");
                }
                break;
            case R.id.bt_phone_login: // 点击登陆
                if (!et_login_phone.getText().toString().equals("") && !et_login_code.getText().toString().equals("")) {
                    if (agree_checkBox.isChecked()) {
                        checkCode(); // 绑定手机号
                    } else {
                        showToast("请同意协议与隐私");
                    }
                } else {
                    showToast("请填写手机号和验证码");
                }
                break;
            case R.id.ll_syb_agree: // 服务协议
                Intent serviceIntent = new Intent(MobailePhoneLoginActivity.this, PrivacyPolicyActivity.class);
                serviceIntent.putExtra("page", "service");
                startActivity(serviceIntent);
                break;
            case R.id.ll_privacy: // 隐私协议
                Intent policyIntent = new Intent(MobailePhoneLoginActivity.this, PrivacyPolicyActivity.class);
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

    //复写倒计时
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            tv_get_code.setClickable(false);
            tv_get_code.setText("重新发送(" + l / 1000 + ")");
            tv_get_code.setTextColor(Color.parseColor("#999999"));
        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            tv_get_code.setText("获取验证码");
            tv_get_code.setTextColor(Color.parseColor("#006cff"));
            //设置可点击
            tv_get_code.setClickable(true);
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.userLogin.sendBand.toString())) {
            showToast("发送成功");
//            bt_phone_login.setBackgroundResource(R.drawable.btn_phone_sure);
        } else if (contentType.equals(DataType.userLogin.checkCode.toString())) {
            showToast("验证成功");
            Intent intent = new Intent(MobailePhoneLoginActivity.this, LoginPasswordActivity.class);
            intent.putExtra("agree", agree_checkBox.isChecked());
            intent.putExtra("phoneNumber", et_login_phone.getText().toString().trim());
            intent.putExtra("code", et_login_code.getText().toString().trim());
            intent.putExtra("invitationCode", et_invitationCode.getText().toString().trim());
            startActivity(intent);
            finish();
        }
    }

    private void sendBandPhoneNumberCode() {
        userLoginPresenter.sendBandPhoneNumberCodeNew(getContext(), et_login_phone.getText().toString(), DataType.userLogin.sendBand.toString());
    }

    private void checkCode() {
        userLoginPresenter.checkCode(getContext(), et_login_phone.getText().toString().trim(), et_login_code.getText().toString().trim(), DataType.userLogin.checkCode.toString());
    }

}
