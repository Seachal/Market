package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.order.ExchangeSecuritiesBean;
import com.dajukeji.hslz.domain.order.ExchangeVoucherInfoBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * 作者: Li_ke
 * 日期: 2019/1/21 19:47
 * 作用: 我的产品券
 */
public class ExchangeVoucherPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public ExchangeVoucherPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得某个产品券信息
     */
    public void getExchangeVoucherInfo(Object tag, int securities_id, String token, final String contentType) {
//        token = "oPJ8m5-irx5X1fUS3YnXQT1QA0o8";
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.securitiesDetail;
        HttpParams httpParams = new HttpParams();
        httpParams.put("securities_id", securities_id);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ExchangeVoucherInfoBean bean = gson.fromJson(json, ExchangeVoucherInfoBean.class);
                    if (bean.getStatus().equals("0")) {
                        iView.setResultData(bean, contentType);
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

    /**
     * 产品券兑换
     */
    public void exchangeSecurities(Object tag, String token, int securities_id, long addressId, String password, final String contentType) {
//        token = "oPJ8m5-irx5X1fUS3YnXQT1QA0o8";
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.exchangeSecurities;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", token);
        httpParams.put("securities_id", securities_id);
        httpParams.put("addr_id", addressId);
        httpParams.put("payPass", password);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ExchangeSecuritiesBean bean = gson.fromJson(json, ExchangeSecuritiesBean.class);
                    if (bean.getStatus().equals("0")) {
                        iView.setResultData(bean, contentType);
                    } else {
                        onfailed(bean.getMessage());
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
