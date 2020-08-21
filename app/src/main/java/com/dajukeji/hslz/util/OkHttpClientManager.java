package com.dajukeji.hslz.util;

import android.app.Activity;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/9/14.
 */

public class OkHttpClientManager {
    private static OkHttpClientManager instance;
    private OkHttpClient mOkHttpClient;
    private static final String TAG = "OkHttpClientManager";

    public OkHttpClientManager() {
        //mOkHttpClient = new OkHttpClient();
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1000l, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //.cache(cache)
                .build();

        //cookie enabled
        //cookie相关的东西还有很多需要研究，以后慢慢补充
//        mOkHttpClient.cookieJar(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
    }

    public static OkHttpClientManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientManager.class) {
                if (instance == null) {
                    instance = new OkHttpClientManager();
                }
            }
        }
        return instance;
    }


    public void cancelCallsWithTag(Object tag) {
        if (tag == null || mOkHttpClient == null) {
            return;
        }
        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
    }
    public void getAsyn(final Activity activity, String url, Map<String, String> requestmap, final ResultCallback callback) {

        final Request request = new Request.Builder()
                .get()
                .tag(activity.getClass().getSimpleName())
                .url(onBuildGetParams(url, requestmap))//get请求，参数拼接
                .build();//Request是OkHttp中访问的请求，Builder是辅助类，Response即OkHttp中的响应
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(String.valueOf(e));
            }

            /**
             *
             * @param call
             * @param response
             * @throws IOException
             * onResponse回调的参数是response，一般情况下，比如
             * 我们希望获得返回的字符串，可以通过response.body().string()获取；
             * 如果希望获得返回的二进制字节数组，则调用response.body().bytes()；
             * 如果你想拿到返回的inputStream，则调用response.body().byteStream()
             */
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                }
            }
        });
    }

    public void postAsyn(final Activity activity, String url, Map<String, String> requestmap, final         ResultCallback callback) {

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonObject = new JSONObject(requestmap);
        RequestBody requestBody = RequestBody.create(JSON, String.valueOf(jsonObject));
        //LogUti.i("info", "requestmapJson" + String.valueOf(jsonObject));
        Request request = new Request.Builder()
                .url(url)
                .tag(activity.getClass().getSimpleName())
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(String.valueOf(e));
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onResponse(response.body().string());
                }
            }
        });
    }

    /**
     * get请求参数的封装，构造成与POST同样的调用方式
     */
    private String onBuildGetParams(String url, Map<String, String> map) {
        StringBuilder sb = new StringBuilder(url);
        boolean isFirst = true;
        if (map == null) {//没有参数时 直接返回url
            return sb.toString();
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isFirst) {
                isFirst = false;
                sb.append("?" + entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            }
        }
        return sb.toString();
    }
}
