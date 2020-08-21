package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.order.ResaleListBean;
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
 * 日期: 2019/1/22 20:44
 * 作用:
 */
public class ResaleListPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public ResaleListPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得我的全部售后订单列表
     */
    public void getResaleList(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.resaleList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SPUtil.getPrefString("token",""));
        httpParams.put("currentPage", currentPage);
        //TODO 以下先使用假数据，等要加转卖场首页顶部tab时在使用真数据
//        httpParams.put("goods_store_id", 32815);
//        httpParams.put("goods_class_id", 66019);
//        httpParams.put("goods_id", 99091);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getResaleList" + currentPage, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ResaleListBean refundOrderBean = gson.fromJson(json, ResaleListBean.class);
                    if (refundOrderBean.getStatus().equals("0")) {
                        iView.setResultData(refundOrderBean, contentType);
                    } else {
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
}
