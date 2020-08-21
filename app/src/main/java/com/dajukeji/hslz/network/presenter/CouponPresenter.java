package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.coupon.CatCoupon;
import com.dajukeji.hslz.domain.coupon.CatCouponLink;
import com.dajukeji.hslz.domain.coupon.CatCouponType;
import com.dajukeji.hslz.domain.coupon.JDCoupon;
import com.dajukeji.hslz.domain.coupon.JDCouponType;
import com.dajukeji.hslz.domain.coupon.JDGoodLink;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

/**
 * 猫券，京券
 */

public class CouponPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public CouponPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 获得猫券类目
     * @param contentType
     * */
    public void getCatTypeList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl + HttpAddress.couponTypeList;
        okGoEngine.post(tag, url, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    CatCouponType catCouponType = gson.fromJson(json, CatCouponType.class);
                    iView.setResultData(catCouponType, contentType);
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
     * 获得猫券列表
     * @param currentPage 当前页数
     * @param cat 类目
     * @param q 查询关键词
     * @param contentType
     * */
    public void getCatGoodsList(Object tag, int currentPage ,String cat , String q ,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl + HttpAddress.couponList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageNo", currentPage);
        httpParams.put("cat", cat);
        httpParams.put("q",q);
        if (currentPage == 1) {
            cacheMode = cacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getGoodList" + currentPage + cat + q, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    CatCoupon catCoupon = gson.fromJson(json, CatCoupon.class);
                    iView.setResultData(catCoupon, contentType);
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
     * 获得猫券二合一转链接口
     * @param item_id 产品ID
     * @param coupon_id 优惠券链接Id
     * @param positionid 推广位ID
     * @param contentType
     * */
    public void  getCatLink(Object tag,String item_id, String coupon_id ,String positionid , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl + HttpAddress.getCatLink;
        HttpParams map = new HttpParams();
        map.put("item_id",item_id);
        map.put("coupon_id",coupon_id);
        map.put("USER_ID",positionid);
        okGoEngine.postMap(tag, url,map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    CatCouponLink catCouponLink = gson.fromJson(json, CatCouponLink.class);
                    if(catCouponLink.getStatus().equals("0")){
                        iView.setResultData(catCouponLink, contentType);
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
     * 获得京券类目
     * @param contentType
     * */
    public void getJDTypeList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl + HttpAddress.getJDTypeList;
        okGoEngine.post(tag, url, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JDCouponType jdCouponType = gson.fromJson(json, JDCouponType.class);
                    iView.setResultData(jdCouponType, contentType);
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
     * 获得京券优惠券列表
     * @param page 页数
     * @param num 条目数
     * @param type 产品类型
     * @param so 关键词
     * @param contentType
     * */
    public void getJDGoodsList(Object tag,int page , int num , String type ,String so, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl+ HttpAddress.getJdGoodsList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("page", page);
        httpParams.put("num", num);
        httpParams.put("type",type);
        httpParams.put("so",so);
        if (page == 1) {
            cacheMode = cacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams,"getJDGoodsList" + page + num + type+so, cacheMode,new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JDCoupon jdCoupon = gson.fromJson(json, JDCoupon.class);
                    iView.setResultData(jdCoupon, contentType);
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
     * 获得京券二合一转链接口
     * @param gid 产品ID
     * @param coupon_url 优惠券链接
     * @param positionid 推广位ID
     * @param contentType
     * */
    public void getGoodsLink(Object tag,String gid , String coupon_url ,String positionid , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl + HttpAddress.getGoodsLink;
        HttpParams map = new HttpParams();
        map.put("gid",gid);
        map.put("coupon_url",coupon_url);
        map.put("USER_ID",positionid);
        okGoEngine.postMap(tag, url,map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JDGoodLink jdGoodLink = gson.fromJson(json, JDGoodLink.class);
                    if(jdGoodLink.getStatus().equals("0")){
                        iView.setResultData(jdGoodLink, contentType);
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

}
