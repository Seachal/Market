package com.dajukeji.hslz.network.presenter;

import android.content.Context;

import com.dajukeji.hslz.activity.mine.ProductsBean;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.MySubsidyBean;
import com.dajukeji.hslz.domain.javaBean.SubsidyShareBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/2.
 * 补贴专区
 */

public class SubsidyPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public SubsidyPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得补贴产品列表
     * */
    public void getSubsidyGoodsList(Object tag, int currentPage,String zone_type, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.GoodList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("zone_type", zone_type);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                    if(goodListBean.getStatus().equals("0")){
                        iView.setResultData(goodListBean, contentType);
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

    public void getProductGoodsList(Object tag,String token, int currentPage,int type, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + "app/hslz/mall/new_products_drop_down.htm";
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("currentPage", currentPage);
        httpParams.put("type", type);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                ProductsBean goodListBean = gson.fromJson(json, ProductsBean.class);
                iView.setResultData(goodListBean, contentType);

            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 我的补贴列表
     * */
    public void myCutPriceOrderList(Object tag, int currentPage,String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.myCutPriceOrderList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MySubsidyBean goodListBean = gson.fromJson(json, MySubsidyBean.class);
                    if(goodListBean.getStatus().equals("0")){
                        iView.setResultData(goodListBean, contentType);
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
     * 创建砍单
     * @param token
     * @param id 产品ID
     * @param gsp 产品规格
     * */
    public void createCutPriceOrder(Object tag,String token,long id , String gsp ,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createCutPriceOrder;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", token);
        httpParams.put("id", id);
        httpParams.put("gsp", gsp);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    SubsidyShareBean subsidyShareBean = gson.fromJson(json, SubsidyShareBean.class);
                    if(subsidyShareBean.getStatus().equals("0")){
                        iView.setResultData(subsidyShareBean, contentType);
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
     * 继续分享链接
     * */
    public void shareCutPriceOrder(Object tag, String order_id,String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.shareCutPriceOrder;
        HttpParams httpParams = new HttpParams();
        httpParams.put("order_id", order_id);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    SubsidyShareBean subsidyShareBean = gson.fromJson(json, SubsidyShareBean.class);
                    if(subsidyShareBean.getStatus().equals("0")){
                        iView.setResultData(subsidyShareBean, contentType);
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
     * 删除订单
     * @param tag
     * @param token
     * @param order_id
     * @param contentType
     */
    public void deleteOrder(Context tag, String token, long order_id, final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("order_id", order_id);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.deleteOrder;
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonO = new JSONObject(json);
                    if(jsonO.get("status").toString().equals("0")){
                        iView.setResultData("sucess",contentType);
                    }else{
                        onfailed(jsonO.get("message").toString());
                    }
                } catch (JSONException e) {
                    onfailed("删除失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

}
