package com.dajukeji.hslz.network.presenter;

import android.text.TextUtils;

import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:03
 * 作用:
 */
public class MsgCodePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public MsgCodePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 发送短信验证码(新接口)
     */
    public void sendMsgCode(Object tag, String mobile, final String contenttype) {
        iView.showLoading();
        String token = SPUtil.getPrefString("token", "");
        String url;
        if (TextUtils.isEmpty(token)) {
            url = HttpAddress.mBaseUrl2 + HttpAddress.sendMsgCodeNoToken;
        } else
            url = HttpAddress.mBaseUrl2 + HttpAddress.sendMsgCodeNew;

        HttpParams params = new HttpParams();
        if (!TextUtils.isEmpty(token))
            params.put("token", token);
        params.put("phoneNumber", mobile);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                iView.setResultData(bean, contenttype);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contenttype);
            }
        });
    }

    /**
     * 校验验证码
     */
    public void checkMsgCode(Object tag, String mobile, String code, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.checkMsgCodeNew;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token", ""));
        params.put("phoneNumber", mobile);
        params.put("code", code);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                iView.setResultData(bean, contenttype);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contenttype);
            }
        });
    }
}
