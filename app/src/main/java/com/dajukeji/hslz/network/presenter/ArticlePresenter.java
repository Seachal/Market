package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.HealthArticleBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

/**
 * Created by ${wangjiasheng} on 2017/12/26 0026.
 */

public class ArticlePresenter {

    private IView iView;
    private Gson gson;
    private OkGoEngine okGoEngine;

    public ArticlePresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    /**
     * 获取养生百科保健文章列表
     *
     * @param currentPage
     */
    public void getHealthArticleList(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.healthArticleList;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage+"");
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                HealthArticleBean healthArticleBean = gson.fromJson(json, HealthArticleBean.class);
                iView.setResultData(healthArticleBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 获取养生百科保健文章列表
     *
     * @param currentPage
     */
    public void lifeArticleList(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.lifeArticleList;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage+"");
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                HealthArticleBean healthArticleBean = gson.fromJson(json, HealthArticleBean.class);
                iView.setResultData(healthArticleBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }
}
