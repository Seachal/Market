package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/2.
 * 省券兑换
 */

public class CouponExchangePresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public CouponExchangePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }


    /**
     * 省券兑换产品列表
     *
     * @param currentPage
     * @param contentType
     */
    public void getGoodsList(Object tag, int currentPage,String zone_type,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.GoodList;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage);
        map.put("zone_type",zone_type);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "getGoodsList" + currentPage+zone_type, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                    if(goodListBean.getStatus().equals("0")){
                        iView.setResultData(goodListBean, contentType);
                    }else{
                        onfailed("");
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
     * 获取产品详情
     * @param goods_id
     * @param contentType
     */
    public void getGoodDetail(Object tag, long goods_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsDetail;
        HttpParams map = new HttpParams();
        map.put("goods_id", goods_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodDetailsBean goodDetailsBean = gson.fromJson(json, GoodDetailsBean.class);
                    if(goodDetailsBean.getStatus().equals("0")){
                        iView.setResultData(goodDetailsBean, contentType);
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
     * 获取默认地址
     *
     * @param contentType
     */
    public void getDefaultAddress(Object tag, String token , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getDefaultAddress;
        HttpParams map = new HttpParams();
        map.put("token",token);
        okGoEngine.postMap(tag, url, map,new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                UserAddressBean userAddressBean = gson.fromJson(json, UserAddressBean.class);
                if(userAddressBean.getStatus().equals("0")){
                    iView.setResultData(userAddressBean, contentType);
                }else{
                    onfailed("");
                    iView.hideLoading();
                }

            }
            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     *  创建积分订单
     *  @param token
     *  @param goods_id
     *  @param gsp
     *  @param addr_id
     *  @param count
     *  @param contentType
     */
    public void createIntegralOrder(Object tag,String token ,long goods_id,  String gsp, long addr_id ,int count, final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createIntegralOrder;
        HttpParams map = new HttpParams();
        map.put("token",token);
        map.put("id",goods_id);
        map.put("gsp",gsp);
        map.put("addr_id",addr_id);
        map.put("count",count);
        okGoEngine.postMap(tag, url, map,new RequestCallBack() {
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
                    onfailed("兑换失败");
                }

            }
            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

}
