package com.dajukeji.hslz.network.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.OrderLogisticsMessageBean;
import com.dajukeji.hslz.domain.order.MyOrderBean;
import com.dajukeji.hslz.domain.order.OrderDetailBean;
import com.dajukeji.hslz.domain.order.OrderEvaluateBean;
import com.dajukeji.hslz.domain.order.OrderPayBean;
import com.dajukeji.hslz.domain.order.OrderStatusCountBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/2.
 * 订单
 * 通过本类来获取具体的商品数据
 */

public class MyOrderPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public MyOrderPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得我的订单列表
     * @param order_status 订单状态
     * */
    public void getOrderList(Object tag, int currentPage,String token,String order_status , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.normalOrderList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token",token);
        httpParams.put("order_status", order_status);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getOrderList" + currentPage+order_status, cacheMode,  new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MyOrderBean myOrderBean = gson.fromJson(json, MyOrderBean.class);
                    if(myOrderBean.getStatus().equals("0")){
                        iView.setResultData(myOrderBean, contentType);
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
     * 获得订单详情
     * @param id 订单编号
     * */
    public void getOrderDetail(Object tag,String token ,String id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.orderDetail;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("id", id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderDetailBean orderDetailBean = gson.fromJson(json, OrderDetailBean.class);
                    if(orderDetailBean.getStatus().equals("0")){
                        iView.setResultData(orderDetailBean, contentType);
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
     * 确认订单已收货
     * @param id 订单编号
     * */
    public void orderConfirmReceive(Object tag,String token ,String id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.orderConfirmReceive;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token",token);
        httpParams.put("id", id);
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
                    onfailed("确认失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }
    /**
     * 订单评价界面
     * @param tag
     * @param token
     * @param id
     * @param contentType
     */
    public void toEvaluate(Context tag, String token, long id, final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("id", id);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.toEvaluate;
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderEvaluateBean orderEvaluateBean = gson.fromJson(json, OrderEvaluateBean.class);
                    if(orderEvaluateBean.getStatus().equals("0")){
                        iView.setResultData(orderEvaluateBean, contentType);
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
     * 订单评价界面
     * @param tag
     * @param token
     * @param id
     * @param evaluate_info_x
     * @param is_nickname_x
     * @param is_satisfied_x
     * @param contentType
     */
    public void orderEvaluate(Context tag, String token, long id, List<String> evaluateId, Map<String,String> evaluate_info_x , Map<String,String> is_satisfied_x, Map<String,String> is_nickname_x , final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("id", id);

        for(String Id:evaluateId){
            if(evaluate_info_x.get(Id)!=null){
                map.put("evaluate_info_"+Id,evaluate_info_x.get(Id));
            };
            if(is_satisfied_x.get(Id)!=null&&!is_satisfied_x.get(Id).equals("")){
                map.put("is_satisfied_"+Id,is_satisfied_x.get(Id));
            };
            if(is_nickname_x.get(Id)!=null&&!is_nickname_x.get(Id).equals("")){
                map.put("is_nickname_"+Id,is_nickname_x.get(Id));
            };
        }
        String url = HttpAddress.mBaseUrl2 + HttpAddress.orderEvaluate;
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
                    onfailed("评价失败");
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

    /**
     * 取消订单
     * @param tag
     * @param token
     * @param order_id
     * @param contentType
     */
    public void cancelOrder(Context tag, String token, long order_id, final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("order_id", order_id);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.cancelOrder;
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
                    onfailed("取消失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 产品物流信息接口
     * @param tag
     * @param token
     * @param order_id
     * @param contentType
     */
    public void orderLogistics(Context tag, String token, String order_id, final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("order_id", order_id);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.orderLogistics;
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderLogisticsMessageBean logisticsMessageBean = gson.fromJson(json, OrderLogisticsMessageBean.class);
                    if(logisticsMessageBean.getStatus().equals("0")){
                        iView.setResultData(logisticsMessageBean, contentType);
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
     * 通过订单编号获取待付款费用
     * @param tag
     * @param token
     * @param of_id
     * @param addr_id
     * @param contentType
     */
    public void getFeeByOrderId(Context tag, String token, String of_id, String addr_id,final String contentType) {
        iView.showLoading();
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("of_id", of_id);
        map.put("addr_id", addr_id);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getFeeByOrderId;
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderPayBean orderPayBean = gson.fromJson(json, OrderPayBean.class);
                    if(orderPayBean.getStatus().equals("0")){
                        iView.setResultData(orderPayBean, contentType);
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
     * 订单数量统计
     * @param tag
     * @param token
     * @param contentType
     */
    public void orderStatusCount(Context tag, String token,final String contentType) {
        HttpParams map = new HttpParams();
        map.put("token", token);
        String url = HttpAddress.mBaseUrl2 + HttpAddress.orderStatusCount;
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderStatusCountBean statusCountBean = gson.fromJson(json, OrderStatusCountBean.class);
                    if(statusCountBean.getStatus().equals("0")){
                        iView.setResultData(statusCountBean, contentType);
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
