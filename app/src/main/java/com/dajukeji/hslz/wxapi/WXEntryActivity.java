package com.dajukeji.hslz.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.event.EquipmentEvent;
import com.dajukeji.hslz.event.ShopCartChangeEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.event.WXLoginEvent;
import com.dajukeji.hslz.global.GlobalContants;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.util.LogUtil;
import com.dajukeji.hslz.util.MLog;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.wechat.utils.WXAppExtendObject;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WechatHandlerActivity;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 微信客户端回调activity示例
 */
/*
微信登录逻辑：1、app发起微信登录等待响应（立即关闭微信登录页）。2、等待微信响应成功授权后，请求接口去拿微信信息（头像,token,电话）。3、接口返回后将信息存储SP，并发送UserMessageEvent通知页面刷新
 */
public class WXEntryActivity extends WechatHandlerActivity implements IWXAPIEventHandler {
    private IWXAPI mApi; // 唤起微信登陆

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApi = WXAPIFactory.createWXAPI(this, GlobalContants.wxAppID, true);
        mApi.handleIntent(this.getIntent(), this);
    }

    /**
     * 处理微信发出的向第三方应用请求app message
     * <p>
     * 在微信客户端中的聊天页面有“添加工具”，可以将本应用的图标添加到其中
     * 此后点击图标，下面的代码会被执行。Demo仅仅只是打开自己而已，但你可
     * 做点其他的事情，包括根本不打开任何页面
     */
    public void onGetMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null) {
            Intent iLaunchMyself = getPackageManager().getLaunchIntentForPackage(getPackageName());
            startActivity(iLaunchMyself);
        }
    }

    /**
     * 处理微信向第三方应用发起的消息
     * <p>
     * 此处用来接收从微信发送过来的消息，比方说本demo在wechatpage里面分享
     * 应用时可以不分享应用文件，而分享一段应用的自定义信息。接受方的微信
     * 客户端会通过这个方法，将这个信息发送回接收方手机上的本demo中，当作
     * 回调。
     * <p>
     * 本Demo只是将信息展示出来，但你可做点其他的事情，而不仅仅只是Toast
     */
    public void onShowMessageFromWXReq(WXMediaMessage msg) {
        if (msg != null && msg.mediaObject != null
                && (msg.mediaObject instanceof WXAppExtendObject)) {
            WXAppExtendObject obj = (WXAppExtendObject) msg.mediaObject;
            Toast.makeText(this, obj.extInfo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    //发送到微信请求的响应结果
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (SPUtil.getPrefString("token", "") == null || SPUtil.getPrefString("token", "").equals("")) {
                    String code = ((SendAuth.Resp) resp).code; // 用户同意后，获得微信code凭证
                    getwxInfo(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //发送取消
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //发送被拒绝
                break;
            default:
                //发送返回
                break;
        }
        finish();

    }

    /**
     * 获取微信用户信息
     */
    public void getwxInfo(String code) {
        MLog.INSTANCE.d("httpparamsrequest", "微信登录信息 - getwxInfo()\n code = " + code);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.loginByWX;
//        String url = "http://tsjaini.51vip.biz/wemall/" + HttpAddress.loginByWX;
        OkHttpUtils
                .get()
                .url(url)
                .addParams("code", code)
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 网络请求回调
     */
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.getInstance().showToast(WXEntryActivity.this, OkGoEngine.NO_NETWORK_TOAST);
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            MLog.INSTANCE.d("httpparamsrequest", "微信登录信息 - \n" + response);
            try {
                JSONObject json = new JSONObject(response);
                if (json.get("status").toString().equals("0")) {
                    String content = json.getJSONObject("content").toString();
                    JSONObject contentJson = new JSONObject(content);
                    String token = contentJson.get("token").toString();
                    MLog.INSTANCE.i("ssssy", token);
                    SPUtil.setPrefString("sex", contentJson.get("sex").toString());
                    SPUtil.setPrefString("nickName", contentJson.get("nickName").toString());
                    SPUtil.setPrefString("headimgurl", contentJson.get("headimgurl").toString());
                    SPUtil.setPrefString("phoneNumber", contentJson.optString("phoneNumber"));
                    SPUtil.setPrefString("agencyId", contentJson.optString("agencyId")); // 上级ID
                    SPUtil.setPrefString("token", contentJson.get("token").toString());
                    try {
                        SPUtil.setPrefString("chatId", contentJson.get("chatId").toString());
                    } catch (JSONException e) {
                        MLog.INSTANCE.w("微信登录信息没有chatId");
                    }
                    try {
                        SPUtil.setPrefString("chatSig", contentJson.get("chatSig").toString());
                    } catch (JSONException e) {
                        MLog.INSTANCE.w("微信登录信息没有chatId");
                    }
                    SPUtil.setPrefInt("userId", contentJson.getInt("userId"));

                    EventBus.getDefault().post(new WXLoginEvent(SPUtil.getPrefString("chatId", ""), SPUtil.getPrefString("chatSig", "")));
                    EventBus.getDefault().post(new ShopCartChangeEvent(""));
                    JPushInterface.setAlias(WXEntryActivity.this, 0, contentJson.get("userId").toString());
                    EventBus.getDefault().post(new UserMessageEvent("message"));
                    LogUtil.info("微信登录", "通知 MeFragment 刷新页面,得到数据为：" + response);
                    EventBus.getDefault().post(new EquipmentEvent("login"));
                    if (SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(WXEntryActivity.this, MobailePhoneLoginActivity.class));
                    }
                } else {
//                    Toast.makeText(WXEntryActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
                    Toast.makeText(WXEntryActivity.this, json.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
//                Toast.makeText(WXEntryActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
//                if(SPUtil.getPrefString("phoneNumber","").equals("")){
//                    startActivity(new Intent(WXEntryActivity.this, MobailePhoneLoginActivity.class));
//                }
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
