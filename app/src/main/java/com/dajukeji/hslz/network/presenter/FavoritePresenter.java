package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.FavouriteGoodsBean;
import com.dajukeji.hslz.domain.javaBean.FavouriteShopBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/2.
 * 全网比价
 */

public class FavoritePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public FavoritePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 收藏夹产品列表
     * @param currentPage
     * @param contentType
     */
    public void selectFavoriteGoods(Object tag, int currentPage,String token ,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.selectFavoriteGoods;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage);
        map.put("token",token);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "selectFavoriteGoods" + currentPage+token, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    FavouriteGoodsBean favouriteGoodsBean = gson.fromJson(json, FavouriteGoodsBean.class);
                    if(favouriteGoodsBean.getStatus().equals("0")){
                        iView.setResultData(favouriteGoodsBean, contentType);
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
     * 收藏夹店铺列表
     * @param currentPage
     * @param contentType
     */
    public void selectFavoriteStore(Object tag, int currentPage,String token ,final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.selectFavoriteStore;
        HttpParams map = new HttpParams();
        map.put("currentPage", currentPage);
        map.put("token",token);
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, map, "selectFavoriteStore" + currentPage+token, cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    FavouriteShopBean favouriteShopBean = gson.fromJson(json, FavouriteShopBean.class);
                    if(favouriteShopBean.getStatus().equals("0")){
                        iView.setResultData(favouriteShopBean, contentType);
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
     * 添加产品或店铺到收藏夹
     * @param tag
     * @param id    产品或者店铺id
     */
    public void addCollect(Object tag,String token ,long id, String collectType, final String contentType) {
//        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.addCollect;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", token);
        httpParams.put("id", id);
        httpParams.put("type", collectType);
        httpParams.put("token", token);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonO =new JSONObject(json);
                    if(jsonO.get("status").toString().equals("0")){
                        iView.setResultData("sucess",contentType);
                    }else{
                        onfailed(jsonO.get("message").toString());
                    }
                } catch (JSONException e) {
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
     * 删除收藏产品或店铺信息
     * @param tag
     * @param type   当前页
     * @param id  删除类型（0产品1店铺）
     * @param contentType
     */
    public void deleteFavorite(Object tag,String token ,String type ,long id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.deleteFavorite;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", token);
        httpParams.put("type", type);
        httpParams.put("id", id);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonO =new JSONObject(json);
                    if(jsonO.get("status").toString().equals("0")){
                        iView.setResultData("sucess",contentType);
                    }else{
                        onfailed("删除失败");
                    }
                } catch (JSONException e) {
                   onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {

            }
        });
    }

}
