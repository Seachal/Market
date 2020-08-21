package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.domain.javaBean.SystemMessageBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class MessagePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public MessagePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    public void  getSystemMessage(Object tag, int currentPage, String token, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.userAppMessage;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token",""));
        params.put("currentPage", currentPage);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    SystemMessageBean bean = gson.fromJson(json, SystemMessageBean.class);
                    iView.setResultData(bean, contenttype);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contenttype);
            }
        });
    }
    /**
     * 未读消息数量
     * */
    public void notWriteNo(Object tag, String token, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.notWriteNo;
        HttpParams params = new HttpParams();
        params.put("token", token);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MessageCountBean bean = gson.fromJson(json, MessageCountBean.class);
                    if(bean.getStatus().equals("0")){
                        iView.setResultData(bean, contenttype);
                    }else {
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contenttype);
            }
        });
    }

    public void markRead(Object tag, String token, String id, final String contenttype) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.updateWrite;
        HttpParams params = new HttpParams();
        params.put("token", token);
        params.put("id", id);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                iView.setResultData(null, contenttype);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception+"", contenttype);
            }
        });
    }

}
