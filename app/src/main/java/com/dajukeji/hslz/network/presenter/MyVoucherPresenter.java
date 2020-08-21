package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.order.MyVoucherBean;
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
public class MyVoucherPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public MyVoucherPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得我的产品券信息
     */
    public void getMyVoucherList(Object tag, int currentPage, String token, final String contentType) {
//        token = "oPJ8m5-irx5X1fUS3YnXQT1QA0o8";
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.myVoucher;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token", token);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getMyVoucherList" + currentPage, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MyVoucherBean bean = gson.fromJson(json, MyVoucherBean.class);
                    if (bean.getStatus().equals("0")||bean.getStatus().equals("1")) {
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
}
