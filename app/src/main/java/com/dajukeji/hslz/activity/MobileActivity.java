package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.freeOrder.PhoneloginBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.PhoneLoginPersenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;

/**
 * 手机号登录页
 */
public class MobileActivity extends HttpBaseActivity {
    private String token;
    private PhoneLoginPersenter loginPersenter;
    private EditText et_login_phone, et_login_code;
    private Button bt_phone_login, bt_phone_register; // 绑定

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginPersenter = new PhoneLoginPersenter(this);
    }

//    @Override
//    protected void loadLayout(Bundle savedInstanceState) {
//        super.loadLayout(savedInstanceState);
//    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_mobile);
        setTitleBar("登录", true);
        et_login_phone = (EditText) findViewById(R.id.et_login_phone);
        et_login_code = (EditText) findViewById(R.id.et_login_code);
        bt_phone_login = (Button) findViewById(R.id.bt_phone_login);
        bt_phone_register = (Button) findViewById(R.id.bt_phone_register);

        //注册
        bt_phone_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MobileActivity.this, RegisterActivity.class));

                //微信注册代码块 //产品：手机注册功能不可用,使用微信注册
//                if (WeChatLoginActivity.isWeixinAvilible(getContext())) {
//                    SendAuth.Req req = new SendAuth.Req();
//                    req.scope = "snsapi_userinfo";
//                    req.state = "haoshen_wx_text";
//
//                    IWXAPI mApi = WXAPIFactory.createWXAPI(MobileActivity.this, GlobalContants.wxAppID, true); //初始化微信api
//                    mApi.registerApp(GlobalContants.wxAppID);
//                    mApi.sendReq(req);
//                } else {
//                    showToast("请先安装微信");
//                }
                finish();

            }
        });

        bt_phone_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBandPhoneNumberCode();
//                MLog.INSTANCE.i("ssss",token);
            }
        });

        findViewById(R.id.login_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //忘记密码
        findViewById(R.id.tv_dontPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileActivity.this, ForgetPwdCheckActivity.class));
            }
        });
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        PhoneloginBean phoneloginBean = (PhoneloginBean) object;
        showToast(phoneloginBean.getMessage());
        token = phoneloginBean.getContent().getToken();
        SPUtil.setPrefString("sex", phoneloginBean.getContent().getSex());
        SPUtil.setPrefString("nickName", phoneloginBean.getContent().getNickName());
        SPUtil.setPrefString("headimgurl", phoneloginBean.getContent().getHeadimgurl());
        SPUtil.setPrefString("phoneNumber", phoneloginBean.getContent().getPhoneNumber());
        SPUtil.setPrefString("agencyId", phoneloginBean.getContent().getAgencyId()); // 上级ID
        SPUtil.setPrefString("token", phoneloginBean.getContent().getToken());
        SPUtil.setPrefString("chatId", phoneloginBean.getContent().getChatId());
        SPUtil.setPrefString("chatSig", phoneloginBean.getContent().getChatSig());
        startActivity(new Intent(MobileActivity.this, MainActivity.class));
        finish();
    }

    protected void showToast(String string) {
        ToastUtils.getInstance().showToast(getContext(), string);
    }

    private void sendBandPhoneNumberCode() {
        loginPersenter.loginPhoneNumber(getContext(), et_login_phone.getText().toString().trim(), et_login_code.getText().toString().trim(), DataType.userLogin.loginphone.toString());
    }
}
