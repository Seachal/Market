package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.activity.mine.CouponBean;
import com.dajukeji.hslz.activity.mine.ResponseCouponBean;
import com.dajukeji.hslz.domain.javaBean.EvaluateAllBean;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.GoodLisBean;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.GoodsClassBean;
import com.dajukeji.hslz.domain.javaBean.IndexBean;
import com.dajukeji.hslz.domain.javaBean.JsonModel;
import com.dajukeji.hslz.domain.javaBean.NewGoodsLoadMoreBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by ${wangjiasheng} on 2017/12/21 0021.
 */

public class GoodPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public GoodPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    /**
     * 新品区上拉
     *
     * @param contentType
     */
    public void getNewGoodsLoadmore(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.newGoodLoadmore;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        map.put("currentPage", currentPage);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "getNewGoodsLoadmore" + currentPage, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
//                GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                NewGoodsLoadMoreBean goodListBean = gson.fromJson(json, NewGoodsLoadMoreBean.class);
                iView.setResultData(goodListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 获取产品类目列表
     *
     * @param contentType
     */
    public void getGoodClassList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodClassList;
        okGoEngine.post(tag, url, "getGoodClassList", CacheMode.REQUEST_FAILED_READ_CACHE, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    GoodsClassBean goodsClassBean = gson.fromJson(json, GoodsClassBean.class);
                    iView.setResultData(goodsClassBean, contentType);
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

    public void getIndex(Object tag, final String contentType) {

        String url = HttpAddress.mBaseUrl2 + HttpAddress.index;
        HttpParams params = new HttpParams();
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, "getIndex", cacheMode.REQUEST_FAILED_READ_CACHE, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    IndexBean indexBean = gson.fromJson(json, IndexBean.class);
                    iView.setResultData(indexBean, contentType);
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
     * 代金券信息
     */
    public void getCashCoupon(Object tag, String pageNum, String pageSize, int flowType, final String contentType) {

        String url = HttpAddress.mBaseUrl2 + "app/mall/myCash/index.htm";//新的
//        String url = HttpAddress.mBaseUrl2 + "wallet/app/personal/myCashCoupon/queryMyCashCoupon.action";//旧的
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
//        map.put("userId", userId);
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        if (flowType == 1 || flowType == -1)
            map.put("flowType", flowType);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    ResponseCouponBean responseBean = gson.fromJson(json, ResponseCouponBean.class);
                    CouponBean indexBean = CouponBean.newForResponse(responseBean);
                    if (indexBean.getCode() == 0) {
                        iView.setResultData(indexBean, contentType);
                    } else
                        onfailed(indexBean.getMsg());
                } catch (JsonSyntaxException e) {
                    onfailed("操作失败");
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
     *
     * @param goods_id
     * @param contentType
     */
    public void getGoodDetail(Object tag, long goods_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsDetail;
        HttpParams map = new HttpParams();
        map.put("goods_id", goods_id);
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodDetailsBean goodDetailsBean = gson.fromJson(json, GoodDetailsBean.class);
                iView.setResultData(goodDetailsBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 兑换专区的产品详情获取
     * @param tag
     * @param goods_id
     * @param contentType
     */
    public void getExchangeGoodDetail(Object tag, long goods_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.exchangeGoodsDetail;
        HttpParams map = new HttpParams();
        map.put("goods_id", goods_id);
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodDetailsBean goodDetailsBean = gson.fromJson(json, GoodDetailsBean.class);
                iView.setResultData(goodDetailsBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 首页产品列表
     *
     * @param currentPage
     * @param contentType
     */
    public void getGoodsList(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.GoodList;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        map.put("currentPage", currentPage);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "getGoodListPage" + currentPage, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                iView.setResultData(goodListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void getGoodsLisd(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodClassLis;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "getGoodListIncexPage" + currentPage, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodLisBean goodListBean = null;
                try {
                    goodListBean = gson.fromJson(json, GoodLisBean.class);
                } catch (JsonSyntaxException e) {
                    onfailed("请求失败");
                }
                if (goodListBean != null)
                    iView.setResultData(goodListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void getGoodsList(Object tag, int currentPage, String zone_type, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.GoodList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("zone_type", zone_type);
        if (currentPage == 1) {
            cacheMode = cacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getGoodList" + currentPage + zone_type, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                iView.setResultData(goodListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void getGoodsList(Object tag, int currentPage, int gc_id, String keyword, String zone_type, final String contentType) {
        iView.showLoading();
//        String url = HttpAddress.mBaseUrl2 + HttpAddress.GoodList;
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodClassLis;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("token", SPUtil.getPrefString("token", ""));
//        httpParams.put("gc_id", gc_id);
//        httpParams.put("keyword", keyword);
//        httpParams.put("zone_type", zone_type);
        if (currentPage == 1) {
            cacheMode = cacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getGoodList" + currentPage + gc_id + keyword + zone_type, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                GoodListBean goodListBean = gson.fromJson(json, GoodListBean.class);
                iView.setResultData(goodListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }


    /**
     * @param tag
     * @param id
     * @param count
     * @param gsp
     * @param buyType     购买类型，0 产品，1 产品券
     * @param contentType
     */
    public void addToCart(Object tag, String id, String count, String gsp, int buyType, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.addToCart;
        HttpParams httpParams = new HttpParams();
        httpParams.put("id", id);
        httpParams.put("count", count);
        httpParams.put("gsp", gsp);
        httpParams.put("type", buyType);
        httpParams.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JsonModel<JsonModel> obj = gson.fromJson(json, JsonModel.class);
                    iView.setResultData(obj, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed("操作失败");
                    iView.hideLoading();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void goodsEvaluateList(Object tag, int currentPage, String token, String goods_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.goodsEvaluateList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("goods_id", goods_id);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    EvaluateAllBean evaluateAllBean = gson.fromJson(json, EvaluateAllBean.class);
                    if (evaluateAllBean.getStatus().equals("0")) {
                        iView.setResultData(evaluateAllBean, contentType);
                    } else {
                        onfailed(evaluateAllBean.getMessage());
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
