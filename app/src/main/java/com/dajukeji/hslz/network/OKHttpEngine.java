package com.dajukeji.hslz.network;

import android.content.Context;
import android.os.Looper;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.util.LogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by ${wong} on 2017/9/11.
 * 四种请求方法
 */

public class OKHttpEngine<T> {
    private OkHttpClient okHttpClient;
    private Gson gson;
    private Context context = AliSdkApplication.getContext();
    private static OKHttpEngine instance;

    private OKHttpEngine() {
        gson = new Gson();
        okHttpClient = new OkHttpClient();
    }

    public static OKHttpEngine getInstance() {
        if (instance == null) {
            synchronized (OKHttpEngine.class) {
                if (instance == null) {
                    instance = new OKHttpEngine();
                }
            }
        }
        return instance;
    }

    /**
     * 使用okhttputil进行请求表单提交
     *
     * @param url
     * @param params
     * @param requestCallBack
     */
    public void httpUtilPostForm(final String url, final Map<String, String> params, Context tag, final RequestCallBack requestCallBack) {
        OkHttpUtils.post().params(params).url(url).tag(tag).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                requestCallBack.onfailed(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                requestCallBack.onsuccess(response);
                LogUtil.info("url" + url);
                LogUtil.info("response" + response);
                if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                }
            }
        });
    }

    /**
     * get请求    please take token in url
     *
     * @param url
     * @param params
     * @param tag
     * @param requestCallBack
     */
    public void httpUtilGet(final String url, final Map<String, String> params, Context tag, final RequestCallBack requestCallBack) {
        OkHttpUtils.get().url(url).params(params).tag(tag).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                requestCallBack.onfailed(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                requestCallBack.onsuccess(response);
            }
        });
    }

    /**
     * 上传文件
     *
     * @param url
     * @param file
     * @param tag
     * @param requestCallBack
     */
    public void httpUtilFile(final String url, final File file, Context tag, final RequestCallBack requestCallBack) {
        OkHttpUtils.postFile().url(url).file(file).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                requestCallBack.onfailed(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                requestCallBack.onsuccess(response);
            }
        });
    }

    /**
     * 文件下载
     *
     * @param url
     * @param context
     * @param destFileDir
     * @param destFileName
     * @param requestCallBack
     */
    public void downLoadFile(final String url, Context context, String destFileDir, String destFileName, final RequestCallBack requestCallBack) {
        OkHttpUtils.get().url(url).tag(context).build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {

            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
//                mProgressBar.setProgress((int)(100*progress));
            }
        });
    }

//    OkGo.get(Urls.URL_METHOD)     // 请求方式和请求url
//            .tag(this)                       // 请求的 tag, 主要用于取消对应的请求
//    .cacheKey("cacheKey")            // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
//    .cacheMode(CacheMode.DEFAULT)    // 缓存模式，详细请看缓存介绍
//    .execute(new StringCallback() {
//        @Override
//        public void onSuccess(String s, Call call, Response response) {
//            // s 即为所需要的结果
//        }
//    });

    /**
     * 基本的网络请求
     *
     * @param tag
     * @param url
     * @param cacheKey
     * @param cacheMode
     * @param requestCallBack
     */
    public void get(Object tag, String url, String cacheKey, CacheMode cacheMode, RequestCallBack requestCallBack) throws IOException {
        OkGo.<com.lzy.okgo.callback.StringCallback>get(url).cacheKey(cacheKey).cacheMode(cacheMode).tag(tag).
                execute(new com.lzy.okgo.callback.Callback<com.lzy.okgo.callback.StringCallback>() {

                    @Override
                    public com.lzy.okgo.callback.StringCallback convertResponse(Response response) throws Throwable {
                        return null;
                    }

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<com.lzy.okgo.callback.StringCallback, ? extends com.lzy.okgo.request.base.Request> request) {

                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

                    }

                    @Override
                    public void onCacheSuccess(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

                    }

                    @Override
                    public void onFinish() {

                    }

                    @Override
                    public void uploadProgress(Progress progress) {

                    }

                    @Override
                    public void downloadProgress(Progress progress) {

                    }
                });
    }

    /**
     * 普通post，直接上传string类型文本
     *
     * @param tag
     * @param url
     * @param content
     * @param requestcallback
     */
    public void postString(Object tag, String url, String content, final RequestCallBack requestcallback) {
        OkGo.<com.lzy.okgo.callback.StringCallback>post(url).tag(tag).upString(content).execute(new com.lzy.okgo.callback.Callback<com.lzy.okgo.callback.StringCallback>() {

            @Override
            public com.lzy.okgo.callback.StringCallback convertResponse(Response response) throws Throwable {
                return null;
            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<com.lzy.okgo.callback.StringCallback, ? extends com.lzy.okgo.request.base.Request> request) {

            }

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

            }

            @Override
            public void onCacheSuccess(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

            }

            @Override
            public void onError(com.lzy.okgo.model.Response<com.lzy.okgo.callback.StringCallback> response) {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void uploadProgress(Progress progress) {

            }

            @Override
            public void downloadProgress(Progress progress) {

            }
        });
    }

    /**
     * 普通post，直接上传json类型
     *
     * @param tag
     * @param url
     * @param requestCallback
     */
    public void postMap(Object tag, String url, HttpParams httpParams, String cacheKey, final RequestCallBack requestCallback) {
        OkGo.<String>post(url).params(httpParams).tag(tag).cacheKey(cacheKey).cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new com.lzy.okgo.callback.StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        requestCallback.onsuccess(response.body());
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        requestCallback.onfailed(response.body());
                    }

                    @Override
                    public void onStart(com.lzy.okgo.request.base.Request<String, ? extends com.lzy.okgo.request.base.Request> request) {
                        super.onStart(request);
                        LogUtil.info("httpparamsrequest", "-------------onstart:请求开始了");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        LogUtil.info("httpparamsrequest", "-------------onfinish:请求");
                    }
                });
    }
}
