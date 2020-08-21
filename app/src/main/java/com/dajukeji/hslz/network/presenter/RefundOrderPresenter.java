package com.dajukeji.hslz.network.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.order.RefundGoodsBean;
import com.dajukeji.hslz.domain.order.RefundOrderBean;
import com.dajukeji.hslz.domain.order.ReturnDetailBean;
import com.dajukeji.hslz.domain.order.ReturnGoodsBean;
import com.dajukeji.hslz.domain.order.ReturnLogisticsBean;
import com.dajukeji.hslz.domain.order.ReturnPromptDetailBean;
import com.dajukeji.hslz.domain.order.ReturnSaveBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/2.
 * 订单
 */

public class RefundOrderPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public RefundOrderPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得我的全部售后订单列表
     * */
    public void getOrderList(Object tag, int currentPage,String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.refundAll;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage); // 退货页数
        httpParams.put("token",token);

        if (currentPage == 1 ) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getOrderList" + currentPage, cacheMode,  new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    RefundOrderBean refundOrderBean = gson.fromJson(json, RefundOrderBean.class);
                    if(refundOrderBean.getStatus().equals("0")){
                        iView.setResultData(refundOrderBean, contentType);
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
     * 待处理订单列表
     * */
    public void getWaitList(Object tag,int currentPage,String token , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.wait_confirmed;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("return_currentPage",currentPage);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    RefundOrderBean refundOrderBean = gson.fromJson(json, RefundOrderBean.class);
                    if(refundOrderBean.getStatus().equals("0")){
                        iView.setResultData(refundOrderBean, contentType);
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
     * 申请退货退款页面
     * */
    public void to_apply(Object tag,String token ,String gc_id ,String gr_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.to_apply;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gc_id",gc_id);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnGoodsBean returnGoodsBean = gson.fromJson(json, ReturnGoodsBean.class);
                    if(returnGoodsBean.getStatus().equals("0")){
                        iView.setResultData(returnGoodsBean, contentType);
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
     * 申请退货页面
     * */
    public void refundInit(Object tag,String token ,String gc_id ,String gr_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.refundInit;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gc_id",gc_id);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    RefundGoodsBean refundGoodsBean = gson.fromJson(json, RefundGoodsBean.class);
                    if(refundGoodsBean.getStatus().equals("0")){
                        iView.setResultData(refundGoodsBean, contentType);
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
     * 保存退货或者退款申请
     * */
    public void saveRefund(Object tag,String token ,String info ,String gc_id ,String count ,String reason ,String service , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.saveRefund;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gc_id",gc_id);
        httpParams.put("info",info);
        httpParams.put("count",count);
        httpParams.put("reason",reason);
        httpParams.put("service",service);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnSaveBean returnSaveBean = gson.fromJson(json, ReturnSaveBean.class);
                    if(returnSaveBean.getStatus().equals("0")){
                        iView.setResultData(returnSaveBean, contentType);
                    }else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("申请提交失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 保存退货物流信息
     * */
    public void saveWaybill(Object tag,String token ,String gr_id ,String ec_id ,String return_shipCode ,String return_logistics_reason , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.save_logistics;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gr_id",gr_id);
        httpParams.put("ec_id",ec_id);
        httpParams.put("return_shipCode",return_shipCode);
        httpParams.put("return_logistics_reason",return_logistics_reason);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnSaveBean returnSaveBean = gson.fromJson(json, ReturnSaveBean.class);
                    if(returnSaveBean.getStatus().equals("0")){
                        iView.setResultData(returnSaveBean, contentType);
                    }else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("申请提交失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 退货退款提示详情（退款详情）
     * */
    public void returnDetail(Object tag,String token ,String gr_id , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.returnDetail;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnPromptDetailBean returnPromptDetailBean = gson.fromJson(json, ReturnPromptDetailBean.class);
                    if(returnPromptDetailBean.getStatus().equals("0")){
                        iView.setResultData(returnPromptDetailBean, contentType);
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
     * 退货退款申请状态详情
     * */
    public void returnSee(Object tag,String token ,String gr_id , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.return_see;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnDetailBean returnDetailBean = gson.fromJson(json, ReturnDetailBean.class);
                    if(returnDetailBean.getStatus().equals("0")){
                        iView.setResultData(returnDetailBean, contentType);
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
     * 寄回产品填写物流
     * */
    public void returnLogistics(Object tag,String token ,String gr_id , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.return_logistics;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ReturnLogisticsBean returnLogisticsBean = gson.fromJson(json, ReturnLogisticsBean.class);
                    if(returnLogisticsBean.getStatus().equals("0")){
                        iView.setResultData(returnLogisticsBean, contentType);
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
     * 删除退货或者退款售后
     * */
    public void returnDelete(Object tag,String token ,String gr_id , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.logic_delete;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("gr_id",gr_id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
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
