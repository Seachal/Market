package com.dajukeji.hslz.network;

import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.util.LogUtil;
import com.dajukeji.hslz.util.MLog;
import com.dajukeji.hslz.util.NetworkUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Created by ${wangjiasheng} on 2017/12/25 0025.
 */

public class OkGoEngine {

    public static final String NO_NETWORK_TOAST = "请确保手机网络正常";
    private static OkGoEngine instance;

    public static OkGoEngine getInstance() {
        if (null == instance) {
            synchronized (OkGoEngine.class) {
                if (null == instance) {
                    instance = new OkGoEngine();
                }
            }
        }
        return instance;
    }

    /**
     * get请求
     *
     * @param tag
     * @param url
     * @param cacheKey
     * @param requestCallBack
     */
    public void get(Object tag, String url, String cacheKey, CacheMode cacheMode, final RequestCallBack requestCallBack) {
        OkGo.<String>get(url).tag(tag).cacheKey(cacheKey).cacheMode(cacheMode)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        requestCallBack.onsuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //无网络
                        if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                            requestCallBack.onfailed(NO_NETWORK_TOAST);
                        } else
                            requestCallBack.onfailed(response.body());
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    public void get(Object tag, String url, final RequestCallBack requestCallBack) {
        OkGo.<String>get(url).tag(tag).cacheMode(CacheMode.NO_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        requestCallBack.onsuccess(response.body());
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        //无网络
                        if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                            requestCallBack.onfailed(NO_NETWORK_TOAST);
                        } else
                            requestCallBack.onfailed(response.body());
                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });
    }

    /**
     * post请求
     */
    public void post(Object tag, final String url, String cacheKey, CacheMode cacheMode, final RequestCallBack requestCallBack) {
        MLog.INSTANCE.i("httpparamsrequest", "请求" + url);
        MLog.INSTANCE.saveFileSpecial("httpLog", "请求" + url);
        OkGo.<String>post(url).tag(tag).cacheKey(cacheKey).cacheMode(cacheMode).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MLog.INSTANCE.i("httpparamsrequest", url + "成功\n" + response.body());
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "成功\n" + response.body());
                requestCallBack.onsuccess(response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                MLog.INSTANCE.i("httpparamsrequest", url + "请求" + response.body());
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "请求" + response.body());
                //无网络
                if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                    requestCallBack.onfailed(NO_NETWORK_TOAST);
                } else
                    requestCallBack.onfailed(response.body());
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                MLog.INSTANCE.i("httpparamsrequest", url + "开始");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "开始");
                super.onStart(request);
            }

            @Override
            public void onFinish() {
                MLog.INSTANCE.i("httpparamsrequest", url + "结束");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "结束");
                super.onFinish();
            }

            @Override
            public void onCacheSuccess(Response<String> response) {
                super.onCacheSuccess(response);
                requestCallBack.onsuccess(response.body());
            }
        });
    }

    public void post(Object tag, final String url, final RequestCallBack requestCallBack) {
        MLog.INSTANCE.i("httpparamsrequest", "请求" + url);
        MLog.INSTANCE.saveFileSpecial("httpLog", "请求" + url);
        OkGo.<String>post(url).tag(tag).cacheMode(CacheMode.NO_CACHE).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MLog.INSTANCE.i("httpparamsrequest", url + "成功\n" + response.body());
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "成功\n" + response.body());
                requestCallBack.onsuccess(response.body());
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                //无网络
                if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                    requestCallBack.onfailed(NO_NETWORK_TOAST);
                } else
                    requestCallBack.onfailed(response.body());
                MLog.INSTANCE.i("httpparamsrequest", url + "失败" + response.body());
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "失败" + response.body());
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                MLog.INSTANCE.i("httpparamsrequest", url + "开始");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "开始");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                MLog.INSTANCE.i("httpparamsrequest", url + "结束");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "结束");
            }
        });
    }

    /**
     * 上传string
     *
     * @param tag
     * @param string
     * @param url
     * @param cacheKey
     * @param requestCallBack
     */
    public void postString(Object tag, String string, String url, String cacheKey, CacheMode cacheMode, final RequestCallBack requestCallBack) {
        LogUtil.info("httpparamsrequest", "请求接口" + url);
        OkGo.<String>post(url).cacheKey(cacheKey).cacheMode(cacheMode)
                .upString(string).tag(tag).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                requestCallBack.onsuccess(response.body());
                LogUtil.info("httpparamsrequest", "-------------onsuccess:请求开始了");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                //无网络
                if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                    requestCallBack.onfailed(NO_NETWORK_TOAST);
                } else
                    requestCallBack.onfailed(response.body());
                LogUtil.info("httpparamsrequest", "-------------onfailed:请求开始了");
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                LogUtil.info("httpparamsrequest", "-------------onStart:请求开始了");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LogUtil.info("httpparamsrequest", "-------------onFinish:请求开始了");
            }
        });
    }

    public void postString(Object tag, String string, String url, RequestCallBack requestCallBack) {
        LogUtil.info("httpparamsrequest", "请求接口" + url);
        OkGo.<String>post(url).upString(string).tag(tag).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                LogUtil.info("httpparamsrequest", "-------------onSuccess:请求开始了");
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                LogUtil.info("httpparamsrequest", "-------------onError:请求开始了");
            }

            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                LogUtil.info("httpparamsrequest", "-------------onStart:请求开始了");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                LogUtil.info("httpparamsrequest", "-------------onFinish:请求开始了");
            }
        });
    }


    /**
     * post提交键值对组
     *
     * @param tag
     * @param url
     * @param httpParams
     * @param cacheKey
     * @param requestCallback
     */
    public void postMap(Object tag, final String url, HttpParams httpParams, String cacheKey, CacheMode cacheMode, final RequestCallBack requestCallback) {
        MLog.INSTANCE.i("httpparamsrequest", "请求" + url + "?" + httpParams);
        MLog.INSTANCE.saveFileSpecial("httpLog", "请求" + url + "?" + httpParams);
        OkGo.<String>post(url).params(httpParams).tag(tag).cacheKey(cacheKey).cacheMode(cacheMode)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        MLog.INSTANCE.i("httpparamsrequest", url + "成功\n" + response.body());
                        MLog.INSTANCE.saveFileSpecial("httpLog", url + "成功\n" + response.body());
                        requestCallback.onsuccess(response.body());
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        MLog.INSTANCE.i("httpparamsrequest", url + "失败" + response.body());
                        MLog.INSTANCE.saveFileSpecial("httpLog", url + "失败" + response.body());
                        //无网络
                        if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                            requestCallback.onfailed(NO_NETWORK_TOAST);
                        } else
                            requestCallback.onfailed(response.body());
                    }

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                        super.onStart(request);
                        MLog.INSTANCE.i("httpparamsrequest", url + "开始");
                        MLog.INSTANCE.saveFileSpecial("httpLog", url + "开始");
                    }

                    @Override
                    public void onFinish() {
                        MLog.INSTANCE.i("httpparamsrequest", url + "结束");
                        MLog.INSTANCE.saveFileSpecial("httpLog", url + "结束");
                        super.onFinish();
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        super.onCacheSuccess(response);
                        requestCallback.onsuccess(response.body());
                    }
                });
    }

    public void postMap(Object tag, final String url, HttpParams httpParams, final RequestCallBack requestCallback) {
        MLog.INSTANCE.i("httpparamsrequest", "请求" + url + "?" + httpParams);
        MLog.INSTANCE.saveFileSpecial("httpLog", "请求" + url + "?" + httpParams);
        OkGo.<String>post(url).params(httpParams).cacheMode(CacheMode.NO_CACHE).tag(tag).execute(new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                MLog.INSTANCE.i("httpparamsrequest", url + "成功\n" + response.body());
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "成功\n" + response.body());
                requestCallback.onsuccess(response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                MLog.INSTANCE.i("httpparamsrequest", url + "失败");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "失败");
                //无网络
                if (!NetworkUtils.isNetworkAvailable(AliSdkApplication.getContext())) {
                    requestCallback.onfailed(NO_NETWORK_TOAST);
                } else
                    requestCallback.onfailed(response.body());
            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                MLog.INSTANCE.i("httpparamsrequest", url + "开始");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "开始");
                super.onStart(request);
            }

            @Override
            public void onFinish() {
                MLog.INSTANCE.i("httpparamsrequest", url + "结束");
                MLog.INSTANCE.saveFileSpecial("httpLog", url + "结束");
                super.onFinish();
            }
        });
    }
}
