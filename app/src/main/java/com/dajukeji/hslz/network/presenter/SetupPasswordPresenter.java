package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:03
 * 作用:
 */
public class SetupPasswordPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public SetupPasswordPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 未登录时更新登录密码
     */
    public void setupLoginPasswordBeforeLogin(Object tag, String mobile, String newPassword, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.setupLoginPasswordBeforeLogin;
        HttpParams params = new HttpParams();
//        params.put("token", SPUtil.getPrefString("token",""));
        params.put("password", newPassword);
//        params.put("newPassword", newPassword);
        params.put("phoneNumber", mobile);
//        params.put("isCheck", 0);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                    iView.setResultData(bean, contentType);
                } catch (JsonSyntaxException e) {
                    iView.hideLoading();
                    onfailed("操作失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contentType);
            }
        });
    }


    /**
     * 更新登录密码
     */
    public void setupLoginPasswrod(Object tag, String mobile, String newPassword, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.setupLoginPassword;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token", ""));
        params.put("password", newPassword);
        params.put("newPassword", newPassword);
        params.put("mobile", mobile);
        params.put("isCheck", 0);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                    iView.setResultData(bean, contentType);
                } catch (JsonSyntaxException e) {
                    iView.hideLoading();
                    onfailed("操作失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contentType);
            }
        });
    }

    /**
     * 更新支付密码
     */
    public void setupPayPassword(Object tag, String mobile, String newPassword, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.setupPayPassword;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token", ""));
        params.put("password", newPassword);
        params.put("newPassword", newPassword);
        params.put("mobile", mobile);
        params.put("isCheck", 0);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                    iView.setResultData(bean, contentType);
                } catch (JsonSyntaxException e) {
                    iView.hideLoading();
                    onfailed("操作失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contentType);
            }
        });
    }
}
