package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.CreateOrderInfoBean;
import com.dajukeji.hslz.domain.javaBean.JsonModel;
import com.dajukeji.hslz.domain.javaBean.LookShopcartBean;
import com.dajukeji.hslz.domain.javaBean.MonetaryBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.model.HttpParams;


/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class CartPresenter {

    private IView iView;
    private Gson gson;
    private OkGoEngine okGoEngine;

    public CartPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    /**
     * 添加产品到购物车
     *
     * @param tag
     * @param id
     * @param count
     * @param gsp
     * @param contentType
     */
    public void addToCart(Object tag, String id, String count, String gsp, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.addToCart;
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("count", count);
        params.put("gsp", gsp);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {

            }

            @Override
            public void onfailed(String exception) {

            }
        });
    }

    /**
     * 从购物车删除产品
     *
     * @param tag
     * @param gc_ids
     * @param contentType
     */
    public void removeGoodsCart(Object tag, String gc_ids, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.removeGoodsCart;
        HttpParams params = new HttpParams();
        params.put("gc_ids", gc_ids);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    LookShopcartBean data = gson.fromJson(json, LookShopcartBean.class);
                    iView.setResultData(data, contentType);
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
     * 查看购物车
     *
     * @param tag
     * @param contentType
     */
    public void lookgoodcart(Object tag, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.look_goodscart;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token", ""));
//        params.put("token", "oGXTO1IQlu62sV1rlxaBpzt3DtnA");
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    LookShopcartBean lookShopcartBean = gson.fromJson(json, LookShopcartBean.class);
                    iView.setResultData(lookShopcartBean, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed(e + "");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 购物车选中产品提交订单
     *
     * @param tag
     * @param gc_ids      订单编号集
     * @param addr_id     地址id
     * @param contentType
     */
    public void createOrderByGc(Object tag, String gc_ids, String addr_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createOrderByGc;
        HttpParams params = new HttpParams();
        params.put("gc_ids", gc_ids);
        params.put("addr_id", addr_id);
//        params.put("type", buyType);
//        params.put("password", password);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    CreateOrderInfoBean payCallbackInfoBean = gson.fromJson(json, CreateOrderInfoBean.class);
                    iView.setResultData(payCallbackInfoBean, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed("网络请求失败");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 从购物车收藏产品
     *
     * @param tag
     * @param gcids
     * @param contentType
     */
    public void collectCartGood(Object tag, String gcids, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.addCollect;
        HttpParams params = new HttpParams();
        params.put("type", 0);  //收藏的类型，产品0店铺1
        params.put("id", gcids);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JsonModel jsonModel = gson.fromJson(json, JsonModel.class);
                    iView.setResultData(jsonModel, contentType);
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
     * 调整购物车产品数量
     *
     * @param tag
     * @param gc_id
     * @param count
     * @param contenttype
     */
    public void adjustGoodCount(Object tag, int gc_id, int count, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsCountAdjust;
        HttpParams params = new HttpParams();
        params.put("gc_id", gc_id);
        params.put("count", count);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JsonModel jsonModel = gson.fromJson(json, JsonModel.class);
                    iView.setResultData(jsonModel, contenttype);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contenttype);
            }
        });
    }

    /**
     * 订单费用计算
     *
     * @param tag
     * @param gc_ids
     * @param addr_id
     * @param contenttype
     */
    public void getFeeByGCID(Object tag, String gc_ids, long addr_id, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getFeeByGCID;
        HttpParams params = new HttpParams();
        params.put("gc_ids", gc_ids);
        params.put("addr_id", addr_id);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    MonetaryBean monetaryBean = gson.fromJson(json, MonetaryBean.class);
                    iView.setResultData(monetaryBean, contenttype);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contenttype);
            }
        });
    }
}
