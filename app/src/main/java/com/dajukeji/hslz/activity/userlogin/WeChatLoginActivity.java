package com.dajukeji.hslz.activity.userlogin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.MobileActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.global.GlobalContants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.List;

public class WeChatLoginActivity extends HttpBaseActivity {

    private ImageView iv_wechat_login;
    private ImageView iv_account_login;
    private IWXAPI mApi; // 微信API 获取用户信息

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_we_chat_login);
//        new StatusBarUtils(this).transparentStatusBar(Color.WHITE);
//        new StatusBarUtils(this).blackTextStatusBar(true);

        mApi = WXAPIFactory.createWXAPI(this, GlobalContants.wxAppID, true); //初始化微信api
        mApi.registerApp(GlobalContants.wxAppID);
        iv_account_login = (ImageView) findViewById(R.id.iv_account_login);
        iv_wechat_login = (ImageView) findViewById(R.id.iv_wechat_login);
        iv_account_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WeChatLoginActivity.this, MobileActivity.class));
                finish();
            }
        });
        iv_wechat_login.setOnClickListener(new View.OnClickListener() { // 唤起微信登陆
            @Override
            public void onClick(View v) {
                if (isWeixinAvilible(getContext())) {
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "haoshen_wx_text";
                    mApi.sendReq(req);
                } else {
                    showToast("请先安装微信");
                }
                finish();
            }
        });
    }

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
