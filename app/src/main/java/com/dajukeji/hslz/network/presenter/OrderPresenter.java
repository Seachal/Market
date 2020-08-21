package com.dajukeji.hslz.network.presenter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.dajukeji.hslz.activity.mine.OrderBean;
import com.dajukeji.hslz.domain.javaBean.PayCallbackInfoBean;
import com.dajukeji.hslz.domain.javaBean.PayInfoBean;
import com.dajukeji.hslz.domain.order.OrderWaitPriceBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by ${wangjiasheng} on 2017/12/21 0021.
 */

public class OrderPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
//    private Map<String, String> map;

    public OrderPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    public void orderPay(Object tag, String pay_type, String goodsId, String goodsCount, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.pay;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("goodsId", goodsId);
        map.put("goodsCount", goodsCount);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                iView.setResultData(obj, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void newPayOrder(Object tag, String order_id, String password, final String contentType) {
        newPayOrder(tag, order_id, password, null, contentType);
    }

    /**
     * 支付订单
     *
     * @param tag
     * @param order_id
     * @param password
     * @param contentType
     */
    public void newPayOrder(Object tag, String order_id, String password, @Nullable String allOrderId, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.payOrderMoney;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        map.put("order_id", order_id);
        map.put("password", password);

        if (allOrderId != null)
            map.put("orders", allOrderId);
//        map.put("type", isMerged ? 1 : 0);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderBean obj = gson.fromJson(json, OrderBean.class);
                    if (obj.getStatus().equals("0") || obj.getStatus().equals("3")||obj.getStatus().equals("2")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 兑换专区的订单付款接口
     * @param tag
     * @param order_id
     * @param password
     * @param contentType
     */
    public void PayDuiOrder(Object tag, String order_id, String password, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.payDuiOrder;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        map.put("order_id", order_id);
        map.put("password", password);

//        map.put("type", isMerged ? 1 : 0);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderBean obj = gson.fromJson(json, OrderBean.class);
                    if (obj.getStatus().equals("0") || obj.getStatus().equals("3")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 直接购买-创建订单
     *
     * @param addr_id    地址ID
     * @param goodsCount 数量
     * @param goodsId    产品ID
     * @param gsp        产品规格
     * @param pay_type   支付方式
     * @param token
     */
    public void createNolOrder(Object tag, String pay_type, String token, String gsp, String addr_id, String goodsId, String buyType, String goodsCount, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createNolOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("id", goodsId);
        map.put("count", goodsCount);
        map.put("token", token);
        map.put("gsp", gsp);
        map.put("addr_id", addr_id);
        map.put("type", buyType);
//        map.put("password", password);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                iView.hideLoading();
                try {
                    OrderBean obj = gson.fromJson(json, OrderBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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

    public void createDuiOrder(Object tag, String pay_type, String token, String gsp, String addr_id, String goodsId, String buyType, String goodsCount, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createDuiOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("id", goodsId);
        map.put("count", goodsCount);
        map.put("token", token);
        map.put("gsp", gsp);
        map.put("addr_id", addr_id);
        map.put("type", buyType);
//        map.put("password", password);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                iView.hideLoading();
                try {
                    OrderBean obj = gson.fromJson(json, OrderBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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

//    /**
//     * 通过子购物车编号集提交订单
//     *
//     * @param tag
//     * @param pay_type
//     * @param gc_ids
//     * @param addr_id
//     * @param transport
//     * @param contentType
//     */
//    public void orderPayShopCart(Context tag, String pay_type, String gc_ids, String addr_id, String transport, int buyType, final String contentType) {
//        iView.showLoading();
//        HttpParams map = new HttpParams();
//        map.put("pay_type", pay_type);
//        map.put("gc_ids", gc_ids);
//        map.put("addr_id", addr_id);
//        map.put("type", buyType);
//        map.put("transport", transport);
//        String url = HttpAddress.mBaseUrl2 + HttpAddress.createOrderByGc;
//        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
//            @Override
//            public void onsuccess(String json) {
//                try {
//                    PayCallbackInfoBean payCallbackInfoBean = gson.fromJson(json, PayCallbackInfoBean.class);
//                    if (payCallbackInfoBean.getStatus().equals("0")) {
//                        iView.setResultData(payCallbackInfoBean, contentType);
//                    } else {
//                        onfailed(payCallbackInfoBean.getMessage());
//                    }
//                } catch (JsonSyntaxException e) {
//                    onfailed("");
//                }
//            }
//
//            @Override
//            public void onfailed(String exception) {
//                iView.showHttpError(exception, contentType);
//            }
//        });
//    }

    /**
     * 创建全网比价支付订单
     *
     * @param tag
     * @param pay_type
     * @param gsp
     * @param addr_id
     * @param contentType
     */
    public void createComparePriceOrder(Object tag, String pay_type, String token, String id, int count, String gsp, String addr_id, String share, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createComparePriceOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("token", token);
        map.put("id", id);
        map.put("gsp", gsp);
        map.put("count", count);
        map.put("addr_id", addr_id);
        map.put("share", share);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 砍单支付
     *
     * @param tag
     * @param pay_type
     * @param addr_id
     * @param cut_price_id
     * @param contentType
     */
    public void payForCutPriceOrder(Object tag, String pay_type, String token, long cut_price_id, String addr_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.payForCutPriceOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("token", token);
        map.put("cut_price_id", cut_price_id);
        map.put("addr_id", addr_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 创建品牌大促订单
     *
     * @param tag
     * @param id
     * @param pay_type
     * @param count
     * @param gsp
     * @param addr_id
     * @param contenttype
     */
    public void createBrandOrder(Object tag, String id, String pay_type, int count, String gsp, String addr_id, final String contenttype) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createBrandOrder;
        HttpParams params = new HttpParams();
        params.put("id", id);
        params.put("pay_type", pay_type);
        params.put("count", count);
        params.put("gsp", gsp);
        params.put("addr_id", addr_id);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    iView.setResultData(obj, contenttype);
                } catch (JsonSyntaxException e) {
                    onfailed(e + "");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contenttype);
            }
        });
    }

    /**
     * 提交9.9包邮订单
     *
     * @param addr_id    地址ID
     * @param goodsCount 数量
     * @param goodsId    产品ID
     * @param gsp        产品规格
     * @param pay_type   支付方式
     * @param token
     */
    public void createNPNOrder(Object tag, String pay_type, String token, String gsp, String addr_id, String goodsId, String goodsCount, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createNPNOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("goods_id", goodsId);
        map.put("count", goodsCount);
        map.put("token", token);
        map.put("gsp", gsp);
        map.put("addr_id", addr_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 创建创意新品订单
     *
     * @param addr_id    地址ID
     * @param goodsCount 数量
     * @param goodsId    产品ID
     * @param gsp        产品规格
     * @param pay_type   支付方式
     * @param token
     */
    public void createCreativeOrder(Object tag, String pay_type, String token, String gsp, String addr_id, String goodsId, String goodsCount, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.createCreativeOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("id", goodsId);
        map.put("count", goodsCount);
        map.put("token", token);
        map.put("gsp", gsp);
        map.put("addr_id", addr_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 待付款订单支付
     *
     * @param tag
     * @param pay_type
     * @param contentType
     */
    public void payForOrder(Object tag, String token, String pay_type, String of_id, String password, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.payForOrder;
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("token", token);
        map.put("of_id", of_id);
        map.put("password", password);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
//                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);//去除了支付宝/微信支付未完成订单
                    OrderBean obj = gson.fromJson(json, OrderBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * get充值参数
     */
    public void getPayInfo(Object tag, String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.inputMoney;
        HttpParams map = new HttpParams();
        map.put("token", token);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayInfoBean obj = gson.fromJson(json, PayInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 充值
     */
    public void payForOder(Object tag, String token, String pay_type, String money, String cash, String percentage, final String contentType) {
        iView.showLoading();

        String url = HttpAddress.mBaseUrl2 + "app/mall/order_new/buyCash.htm";
//        String url = "http://tsjaini.51vip.biz/wemall/" + "app/mall/order_new/buyCash.htm";
        HttpParams map = new HttpParams();
        map.put("pay_type", pay_type);
        map.put("token", token);
        map.put("money", money);
        map.put("cash", cash);
        map.put("percentage", percentage);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    PayCallbackInfoBean obj = gson.fromJson(json, PayCallbackInfoBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
     * 通过产品编号获取待付款费用
     *
     * @param tag
     * @param token
     * @param goods_id
     * @param gsp
     * @param count
     * @param addr_id
     * @param share
     */
    public void getFeeByGoodsId(Object tag, String token, String goods_id, String gsp, int count, String addr_id, String share, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getFeeByGoodsId;
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("goods_id", goods_id);
        map.put("gsp", gsp);
        map.put("count", count);
        map.put("addr_id", addr_id);
        map.put("share", share);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    OrderWaitPriceBean obj = gson.fromJson(json, OrderWaitPriceBean.class);
                    if (obj.getStatus().equals("0")) {
                        iView.setResultData(obj, contentType);
                    } else {
                        onfailed(obj.getMessage());
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
