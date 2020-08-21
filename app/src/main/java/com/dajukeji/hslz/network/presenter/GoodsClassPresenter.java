package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.GoodsClassSubBean;
import com.dajukeji.hslz.domain.javaBean.GoodsClassTabBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 */

public class GoodsClassPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;

    public GoodsClassPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 二级类型
     *
     * @param tag
     * @param currentPage
     * @param token
     * @param contenttype
     */
    public void getGoodsSubClassList(Object tag, int currentPage, String token, long classId, final String contenttype) {
        iView.showLoading();

        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsSubClass;
        HttpParams params = new HttpParams();
        params.put("token", token);
        params.put("currentPage", currentPage);
        params.put("class_id", classId);
        okGoEngine.postMap(tag, url, params, url + classId, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodsClassSubBean bean = gson.fromJson(json, GoodsClassSubBean.class);
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
     * 左侧大类型
     */
    public void getGoodsClassTabList(Object tag, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsTab;
        HttpParams params = new HttpParams();
        okGoEngine.postMap(tag, url, params, url, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodsClassTabBean bean = gson.fromJson(json, GoodsClassTabBean.class);
                    if (bean.getStatus().equals("0")) {
                        iView.setResultData(bean, contenttype);
                    } else {
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
                iView.showHttpError(exception + "", contenttype);
            }
        });
    }

}
