package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.AwardGoodsBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/12/29.
 * 中奖产品领取
 */

public class AwardGoodsPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public AwardGoodsPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 领取奖品和中奖纪录
     * */
    public void getAwardGoodsList(Object tag,String token ,int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.award_goods;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    AwardGoodsBean awardGoodsBean = gson.fromJson(json, AwardGoodsBean.class);
                    if(awardGoodsBean.getStatus().equals("0")){
                        iView.setResultData(awardGoodsBean, contentType);
                    }else {
                        onfailed("暂无中奖记录");
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
     * 创建抽奖订单
     *  @param token
     *  @param award_record_id
     *  @param addr_id
     *  @param contentType
     */
    public void createAwardOrder(Object tag, long award_record_id , long addr_id , String token , final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createAwardOrder;
        HttpParams map = new HttpParams();

        map.put("token",token);
        map.put("award_record_id",award_record_id);
        map.put("addr_id",addr_id+"");
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
                    e.printStackTrace();
                }

            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

}
