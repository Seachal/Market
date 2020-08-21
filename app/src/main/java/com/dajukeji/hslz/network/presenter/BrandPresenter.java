package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.javaBean.BigBrandBean;
import com.dajukeji.hslz.domain.javaBean.BrandStoreBean;
import com.dajukeji.hslz.domain.javaBean.BrandClassBean;
import com.dajukeji.hslz.domain.javaBean.StoreBannerBean;
import com.dajukeji.hslz.domain.javaBean.StoreGoodClassBean;
import com.dajukeji.hslz.domain.javaBean.StoreGoodsBean;
import com.dajukeji.hslz.domain.javaBean.StoreIndexBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandPresenter {

    private IView iView;
    private Gson gson;
    private OkGoEngine okGoEngine;

    public BrandPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    /**
     * 大促品牌列表
     *
     * @param tag
     * @param max_count
     */
    public void getBigBrandList(Object tag, int max_count, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.bigBrandList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("max_count", max_count);
        okGoEngine.postMap(tag, url, httpParams, "getBigBrandList", CacheMode.REQUEST_FAILED_READ_CACHE, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BigBrandBean bigBrandBean = gson.fromJson(json, BigBrandBean.class);
                    iView.setResultData(bigBrandBean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 店铺分类列表
     *
     * @param tag
     * @param contentType
     */
    public void getStoreClassList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeClassList;
        okGoEngine.post(tag, url, "getStoreClassList", CacheMode.REQUEST_FAILED_READ_CACHE, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BrandClassBean bean = gson.fromJson(json, BrandClassBean.class);
                    iView.setResultData(bean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 品牌大促商家列表
     *
     * @param currentPage
     * @param store_class_id
     * @param contentType
     */
    public void getBrandStoreList(Object tag, int currentPage, long store_class_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.brandStoreList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("store_class_id", store_class_id);
        CacheMode cacheMode;
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getBrandStoreList", cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BrandStoreBean brandStoreBean = gson.fromJson(json, BrandStoreBean.class);
                    iView.setResultData(brandStoreBean, contentType);
                } catch(Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, "");
            }
        });
    }

    public void getBrandStoreList(Object tag, int currentPage, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.brandStoreList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        CacheMode cacheMode;
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getBrandStoreList", cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BrandStoreBean brandStoreBean = gson.fromJson(json, BrandStoreBean.class);
                    iView.setResultData(brandStoreBean, contentType);
                } catch(Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 获取品牌下的门店
     * @param brand_id   品牌id
     * @param currentPage  当前页数
     */
    public void getStoreByBrand(Object tag, int currentPage, int brand_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.brandStoreList;
        HttpParams httpParams = new HttpParams();
        httpParams.put("currentPage", currentPage);
        httpParams.put("brand_id", brand_id);
        CacheMode cacheMode;
        if (currentPage == 1) {
            cacheMode = CacheMode.REQUEST_FAILED_READ_CACHE;
        } else {
            cacheMode = CacheMode.NO_CACHE;
        }
        okGoEngine.postMap(tag, url, httpParams, "getBrandStoreList", cacheMode, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BrandStoreBean brandStoreBean = gson.fromJson(json, BrandStoreBean.class);
                    iView.setResultData(brandStoreBean, contentType);
                } catch(Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }
    /**
     * 店铺banner列表
     * @param tag
     * @param store_id
     * @param contentType
     */
    public void getStoreBannerList(Object tag, long store_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeBannerList;
        HttpParams params = new HttpParams();
        params.put("store_id", store_id);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    StoreBannerBean storeBannerBean = gson.fromJson(json, StoreBannerBean.class);
                    iView.setResultData(storeBannerBean, contentType);
                } catch(Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 店铺产品分类
     * @param tag
     * @param store_id
     * @param contentType
     */
    public void getStoreGoodsClassList(Object tag, long store_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeGoodsClassList;
        HttpParams params = new HttpParams();
        params.put("store_id", store_id);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    StoreGoodClassBean storeGoodClassBean = gson.fromJson(json, StoreGoodClassBean.class);
                    iView.setResultData(storeGoodClassBean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 店铺产品列表
     *
     * @param tag
     * @param currentPage
     * @param gc_id
     * @param store_id
     */
    public void getStoreGoodsList(Object tag, int currentPage, int gc_id, long store_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeGoodsList;
        HttpParams params = new HttpParams();
        params.put("currentPage", currentPage);
        params.put("gc_id", gc_id);
        params.put("store_id", store_id);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    StoreGoodsBean storeGoodsBean = gson.fromJson(json, StoreGoodsBean.class);
                    iView.setResultData(storeGoodsBean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    public void getStoreGoodsList(Object tag, int currentPage, long store_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeGoodsList;
        HttpParams params = new HttpParams();
        params.put("currentPage", currentPage);
        params.put("store_id", store_id);
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    StoreGoodsBean storeGoodsBean = gson.fromJson(json, StoreGoodsBean.class);
                    iView.setResultData(storeGoodsBean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }

    /**
     * 店铺主页
     * @param tag
     * @param store_id
     * @param contentType
     */
    public void getStoreIndex(Object tag, long store_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.storeIndex;
        HttpParams params = new HttpParams();
        params.put("store_id", store_id);
        params.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    StoreIndexBean storeIndexBean = gson.fromJson(json, StoreIndexBean.class);
                    iView.setResultData(storeIndexBean, contentType);
                } catch (Exception e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
            }
        });
    }
}
