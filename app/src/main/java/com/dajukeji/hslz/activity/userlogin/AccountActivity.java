package com.dajukeji.hslz.activity.userlogin;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.SetupLoginPwdCheckActivity;
import com.dajukeji.hslz.activity.SetupPayPwdCheckActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.domain.javaBean.UserInfoBean;
import com.dajukeji.hslz.global.GlobalContants;
import com.dajukeji.hslz.network.presenter.UserInfoPersenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/9/11.
 * 个人资料页
 */

public class AccountActivity extends HttpBaseActivity implements View.OnClickListener {

    @BindView(R.id.user_image)
    ImageView mImgFace;

    @BindView(R.id.user_nick)
    TextView mTvNickName;

    @BindView(R.id.user_phone)
    TextView mTvPhone;
    @BindView(R.id.rela_pay)
    RelativeLayout rela_pay;

    TextView user_sex;
    private IWXAPI mApi; // 微信API 获取用户信息
    private EditText user_nam, user_address;
    private UserInfoPersenter userInfoPersenter;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_account);
        setTitleBar(R.string.text_personal_data, true);
        userInfoPersenter = new UserInfoPersenter(this);
    }

    @Override
    protected void initView() {
        mApi = WXAPIFactory.createWXAPI(this, GlobalContants.wxAppID, true); //初始化微信api
        mApi.registerApp(GlobalContants.wxAppID);
        mImgFace = (ImageView) findViewById(R.id.user_image);
        mTvNickName = (TextView) findViewById(R.id.user_nick);
        mTvPhone = (TextView) findViewById(R.id.user_phone);
        user_sex = (TextView) findViewById(R.id.user_sex);
        rela_pay = findViewById(R.id.rela_pay);
        user_nam = findViewById(R.id.user_nam);
        user_nam.setCursorVisible(false);
        user_address = findViewById(R.id.user_address);
        user_address.setCursorVisible(false);
        user_nam.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    user_nam.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });
        user_address.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (MotionEvent.ACTION_DOWN == motionEvent.getAction()) {
                    user_address.setCursorVisible(true);// 再次点New击显示光标
                }
                return false;
            }
        });

        findViewById(R.id.group_loginPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, SetupLoginPwdCheckActivity.class));
            }
        });
        findViewById(R.id.rela_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, SetupPayPwdCheckActivity.class));
            }
        });
        init();

        userInfoPersenter.getUserInfo(this, "个人资料信息");
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init() {
        if (!TextUtils.isEmpty(SPUtil.getPrefString("nickName", ""))) {
            mTvNickName.setText(SPUtil.getPrefString("nickName", ""));
        }

        if (!TextUtils.isEmpty(SPUtil.getPrefString("headimgurl", ""))) {
            Glide.with(this).load(SPUtil.getPrefString("headimgurl", "")).into(mImgFace);
        }

        if (!TextUtils.isEmpty(SPUtil.getPrefString("sex", ""))) {
            user_sex.setText(SPUtil.getPrefString("sex", ""));
        }

        if (!TextUtils.isEmpty(SPUtil.getPrefString("phoneNumber", ""))) {
            String phone = SPUtil.getPrefString("phoneNumber", "");
            phone = phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            mTvPhone.setText(phone);
        }

        //获取用户信息
//        String url = HttpAddress.mBaseUrl2 + "wallet/app/dealers/user/index.action";
//        OkHttpUtils.post()
//                .url(url)
//                .addParams("userPhone", SPUtil.getPrefString("phoneNumber", ""))
//                .build()//
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        final ZichanBean zichanBean = new Gson().fromJson(response, ZichanBean.class);
//                        if (zichanBean.getCode() == 0) {
//                            if (zichanBean.getData().getTrueName() == null) {
//
//                            } else {
//                                user_nam.setText(zichanBean.getData().getTrueName() + "");
//                            }
//                            if (zichanBean.getData().getAddress() == null) {
//
//                            } else {
//                                user_address.setText(zichanBean.getData().getAddress() + "");
//                            }
//
///*                          原有逻辑
//                            if (zichanBean.getData().getCashCouponPassword() == null) {
//                                rela_pay.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent2 = new Intent(AccountActivity.this, PaypassActivity.class);
//                                        intent2.putExtra("oldpass", "");
//                                        startActivity(intent2);
//                                    }
//                                });
//                            } else {
//                                rela_pay.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View view) {
//                                        Intent intent = new Intent(AccountActivity.this, RePaypassActivity.class);
////                                        intent.putExtra("cashCouponPassword",zichanBean.getData().getCashCouponPassword());
//                                        startActivity(intent);
//                                    }
//                                });
//                            }
//*/
//                        }
//                    }
//                });
    }

    @OnClick({R.id.user_phone, R.id.user_sync_weChat, R.id.baocun})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_sync_weChat:
                evokingWX();
                break;
            case R.id.baocun://保存修改
                userInfoPersenter.setupAddressRealName(this, user_address.getText().toString(), user_nam.getText().toString(), "修改真名地址");
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "修改真名地址":
                BaseStatusMessageBean bean = (BaseStatusMessageBean) object;
                ToastUtils.getInstance().showToast(this, bean.getMessage());
                break;
            case "个人资料信息":
                UserInfoBean infoBean = (UserInfoBean) object;
                user_address.setText(infoBean.getContent().getGradeRegion());
                user_nam.setText(infoBean.getContent().getTrueName());
                //0=未设置
                ((TextView) findViewById(R.id.user_loginpass)).setText(
                        (infoBean.getContent().getPasswordIsEmpty() == 0) ? "设置密码" : "修改密码");
                ((TextView) findViewById(R.id.user_paypass)).setText(
                        (infoBean.getContent().getPayPassEmpty() == 0) ? "设置密码" : "修改密码");
                break;
        }
    }

    /**
     * 唤起微信登陆
     */
    private void evokingWX() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "haoshen_wx_text";
        mApi.sendReq(req);
    }

}
