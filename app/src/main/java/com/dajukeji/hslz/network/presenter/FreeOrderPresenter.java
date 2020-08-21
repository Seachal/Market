package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.freeOrder.FreeOrderGoods;
import com.dajukeji.hslz.domain.freeOrder.InviteeFreeOrderBean;
import com.dajukeji.hslz.domain.freeOrder.MeFreeOrderBean;
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
 * Created by Administrator on 2017/12/29.
 * 免单
 */

public class FreeOrderPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public FreeOrderPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得免单产品列表
     * */
    public void getFreeGoodsList(Object tag, int currentPage,String zone_type, final String contentType) {
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

    /**
     * 获得我的免单产品列表
     * */
    public void getMeFreeList(Object tag, int currentPage,String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.freeOrderList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token", token);
        if (currentPage == 1) {
            cacheMode = cacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getMeFreeList" + currentPage + token, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MeFreeOrderBean meFreeOrderBean = gson.fromJson(json, MeFreeOrderBean.class);
                    if(meFreeOrderBean.getStatus().equals("0")){
                        iView.setResultData(meFreeOrderBean, contentType);
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
                try {
                    UserAddressBean userAddressBean = gson.fromJson(json, UserAddressBean.class);
                    iView.setResultData(userAddressBean, contentType);
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
     * 继续分享免单
     *  @param token
     *  @param freeId
     *  @param contentType
     */
    public void shareFreeOrder(Object tag, String token , String freeId , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.shareFreeOrder;
        HttpParams map = new HttpParams();
        map.put("token",token);
        map.put("id",freeId);
        okGoEngine.postMap(tag, url,map ,new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    FreeOrderGoods freeOrderGoods = gson.fromJson(json, FreeOrderGoods.class);
                    if(freeOrderGoods.getStatus().equals("0")){
                        iView.setResultData(freeOrderGoods, contentType);
                    }else{
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
     * 创建免单
     *  @param goods_id 产品ID
     *  @param sku 产品规格
     *  @param id 地址
     *  @param token
     *  @param contentType
     */

    public void createFreeOrder(Object tag, long goods_id,  String sku, long id , String token , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createFreeOrder;
        HttpParams map = new HttpParams();
        map.put("token",token);
        map.put("id",Long.toString(goods_id)+"");
        map.put("gsp",sku);
        map.put("addr_id",Long.toString(id)+"");
        okGoEngine.postMap(tag, url,map ,new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    FreeOrderGoods freeOrderGoods = gson.fromJson(json, FreeOrderGoods.class);
                    if(freeOrderGoods.getStatus().equals("0")){
                        iView.setResultData(freeOrderGoods, contentType);
                    }else {
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
     * 创建受邀者免单
     *  @param token
     *  @param freeId
     *  @param addr_id
     *  @param contentType
     */
    public void createInviteeFreeOrder(Object tag, long freeId , long addr_id , String token , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createInviteeFreeOrder;
        HttpParams map = new HttpParams();

        map.put("token",token);
        map.put("id",freeId+"");
        map.put("addr_id",addr_id+"");
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    InviteeFreeOrderBean inviteeFreeOrderBean = gson.fromJson(json, InviteeFreeOrderBean.class);
                    if(inviteeFreeOrderBean.getStatus().equals("0")){
                        iView.setResultData(inviteeFreeOrderBean, contentType);
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
     * 删除免单
     *  @param token
     *  @param order_id
     *  @param contentType
     */
    public void deleteFreeOrder(Object tag, String token , String order_id , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.deleteFreeOrder;
        HttpParams map = new HttpParams();
        map.put("token",token);
        map.put("order_id",order_id);
        okGoEngine.postMap(tag, url,map ,new RequestCallBack() {
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
