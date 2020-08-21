package com.dajukeji.hslz.network.presenter;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.dajukeji.hslz.domain.freeOrder.BandPhoneBean;
import com.dajukeji.hslz.event.EquipmentEvent;
import com.dajukeji.hslz.event.ShopCartChangeEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.event.WXLoginEvent;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.LogUtil;
import com.dajukeji.hslz.util.MLog;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/1/8.
 * 用户登陆注册
 */

public class UserLoginPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public UserLoginPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获取绑定手机号验证码
     *
     * @param phoneNumber 用户手机号
     * @param contentType
     */
    public void sendBandPhoneNumberCode(Object tag, String phoneNumber, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl3 + HttpAddress.sendBandPhoneNumberCode;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.get("status").equals("0")) {
                        iView.setResultData(jsonObject.get("message").toString(), contentType);
                    } else {
                        onfailed(jsonObject.get("message").toString());
                    }
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void sendBandPhoneNumberCodeNew(Object tag, String phoneNumber, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl3 + HttpAddress.sendBandPhoneNumberCodeNew;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.get("status").equals("0")) {
                        iView.setResultData(jsonObject.get("message").toString(), contentType);
                    } else {
                        onfailed(jsonObject.get("message").toString());
                    }
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 检验验证码
     *
     * @param phoneNumber 用户手机号
     * @param code        验证码
     * @param contentType
     */
    public void checkCode(Object tag, String phoneNumber, String code, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.checkCode;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
//        map.put("token", token);
        map.put("code", code);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.get("status").equals("0")) {
                        iView.setResultData(jsonObject.get("message").toString(), contentType);
                    } else {
                        onfailed(jsonObject.get("message").toString());
                    }
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 绑定手机号
     *
     * @param phoneNumber    用户手机号
     * @param password       密码
     * @param token
     * @param code           验证码
     * @param contentType
     * @param invitationCode 邀请码
     */
    public void bandPhoneNumber(Object tag, String phoneNumber, String code, String password, String token, String invitationCode, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.bandPhoneNumber;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
        map.put("password", password);
        map.put("token", token);
        map.put("code", code);
        if (!TextUtils.isEmpty(invitationCode))
            map.put("invite_code", invitationCode);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BandPhoneBean bandPhoneBean = gson.fromJson(json, BandPhoneBean.class);
                    if (bandPhoneBean.getStatus().equals("0")) {
                        iView.setResultData(bandPhoneBean, contentType);
                    } else {
                        onfailed(bandPhoneBean.getMessage());
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void refreshUserInfo(final Object tag, String phoneNumber, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.refreshUserInfo;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    //拷贝自 WXEntryActivity.MyStringCallback
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.get("status").equals("0")) {
                        if (jsonObject.get("status").toString().equals("0")) {
                            String content = jsonObject.getJSONObject("content").toString();
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

                            EventBus.getDefault().post(new WXLoginEvent(SPUtil.getPrefString("chatId",""), SPUtil.getPrefString("chatSig","")));
                            EventBus.getDefault().post(new ShopCartChangeEvent(""));
                            JPushInterface.setAlias(AliSdkApplication.getContext(), 0, contentJson.get("userId").toString());//推送别名刷新
                            EventBus.getDefault().post(new UserMessageEvent("message"));
                            LogUtil.info("微信登录", "通知 MeFragment 刷新页面,得到数据为：" + json);
                            EventBus.getDefault().post(new EquipmentEvent("login"));
//                            if (SPUtil.getPrefString("phoneNumber", "").equals("")) { // 此接口是在绑定后才调用，无需再次绑定
//                                startActivity(new Intent(AliSdkApplication.getContext(), MobailePhoneLoginActivity.class));
//                            }
                        } else {
//                    Toast.makeText(WXEntryActivity.this, "获取用户信息失败", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AliSdkApplication.getContext(), jsonObject.get("message").toString(), Toast.LENGTH_SHORT).show();
                        }
                        iView.hideLoading();
                        iView.setResultData(null,contentType);
                    } else {
                        onfailed(jsonObject.get("message").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void phoneRegister(Object tag, String phoneNumber, String code, String password, String invitationCode, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl3 + HttpAddress.phoneRegister;
        HttpParams map = new HttpParams();
        map.put("phoneNumber", phoneNumber);
        map.put("password", password);
        map.put("code", code);
        map.put("invite_code", invitationCode);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    if (jsonObject.get("status").equals("0")) {
                        iView.setResultData(jsonObject.get("message").toString(), contentType);
                    } else {
                        onfailed(jsonObject.get("message").toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

}
