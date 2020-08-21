package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.comparePrice.PlanGoodsListBean;
import com.dajukeji.hslz.domain.comparePrice.PlanList;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

/**
 * Created by Administrator on 2018/1/2.
 * 全网比价
 */

public class ComparePricePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public ComparePricePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获取全网比价计划
     * */
    public void getPlanList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getPlanList;
        okGoEngine.post(tag, url, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PlanList planList = gson.fromJson(json, PlanList.class);
                    if(planList.getStatus().equals("0")){
                        iView.setResultData(planList, contentType);
                    }else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
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
     * 全网比价产品列表
     *
     * @param currentPage
     * @param contentType
     */
    public void getGoodsList(Object tag, int currentPage,long plan_id ,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getPlanGoodsList;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage);
        map.put("plan_id",plan_id);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "getGoodsList" + currentPage+plan_id, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PlanGoodsListBean planGoodsListBean = gson.fromJson(json, PlanGoodsListBean.class);
                    iView.setResultData(planGoodsListBean, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }
}
