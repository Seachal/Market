package com.dajukeji.hslz.global;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;

import com.dajukeji.hslz.im.utils.Foreground;
import com.dajukeji.hslz.util.MLog;
import com.dajukeji.hslz.util.MUnCaughtExceptionHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.login.KeplerApiManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.mob.MobSDK;
import com.tencent.TIMLogLevel;
import com.tencent.TIMManager;
import com.tencent.bugly.imsdk.crashreport.CrashReport;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * 功能说明：全局application
 */
public class AliSdkApplication extends Application {
    public static AliSdkApplication application = null;
    private List<Activity> mActivityList;
    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //分包，解决 Didn't find class "com.google.firebase.provider.FirebaseInitProvider"
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mActivityList = new ArrayList<>();
        // 应用异常跳转到启动页
//        UncaughtExceptionHandlerImpl.getInstance().init(this, BuildConfig.DEBUG, true, 0, StartPageActivity.class);
        //记录崩溃信息
        MUnCaughtExceptionHandler.INSTANCE.init();
        application = this;

        // 初始化bugly，错误日志上传
        CrashReport.initCrashReport(getApplicationContext(), "a050c8fcdd", false);
        // 初始化分享sdk
        MobSDK.init(getApplicationContext(), GlobalContants.shareAppkey, GlobalContants.shareAppSecret);

        // 初始化JPush
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
        //初始化阿里百川
//        因阿里百川错误而注释
//        AlibcTradeSDK.asyncInit(this, new AlibcTradeInitCallback() {
//            @Override
//            public void onSuccess() {
//              /*Toast.makeText(AliSdkApplication.this, "初始化成功", Toast.LENGTH_SHORT).show();*/
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//              /*Toast.makeText(AliSdkApplication.this, "初始化失败,错误码="+code+" / 错误消息="+msg, Toast.LENGTH_SHORT).show();*/
//            }
//        });
        MLog.INSTANCE.init(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //全局的读取超时时间
        builder.readTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        HttpParams httpParams = new HttpParams();
//        httpParams.put(HttpAddress.tokenKey, HttpAddress.token);
        OkGo.getInstance().init(this)
                .setCacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                .addCommonParams(httpParams)
                .setRetryCount(1)
                .setOkHttpClient(builder.build());
        //初始化京东开普勒
        KeplerApiManager.asyncInitSdk(this, "1b4b46affa8e485dafd22eae17a4a4d2", "ad8139456e304de7b4c1610ca551a957",
                new AsyncInitListener() {
                    @Override
                    public void onSuccess() {
                        MLog.INSTANCE.e("Kepler", "Kepler asyncInitSdk onSuccess ");
                    }

                    @Override
                    public void onFailure() {
                        MLog.INSTANCE.e("Kepler",
                                "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
                    }
                });

        Foreground.init(this);
        context = getApplicationContext();
        init();
    }

    public static Context getContext() {
        return application;
    }

    private void init() {
        TIMManager.getInstance().init(context);
        //开启本地储存
        TIMManager.getInstance().enableFriendshipStorage(true);
        //启用群资料存储
        TIMManager.getInstance().enableFriendshipStorage(true);
        //设置日志级别
        TIMManager.getInstance().setLogLevel(TIMLogLevel.INFO);
        //开启已读回执
        TIMManager.getInstance().enableReadReceipt();
        //禁用自动上报
        TIMManager.getInstance().disableAutoReport();


        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);//设置日志等级
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        //设置刷新监听
        RefreshEvent.getInstance();
    }


    /**
     * 添加单个Activity
     */
    public void addActivity(Activity activity) {
        // 为了避免重复添加，需要判断当前集合是否满足不存在该Activity
        if (!mActivityList.contains(activity)) {
            mActivityList.add(activity); // 把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity(Activity activity) {
        // 判断当前集合是否存在该Activity
        if (mActivityList.contains(activity)) {
            mActivityList.remove(activity); // 从集合中移除
            if (activity != null) {
                activity.finish(); // 销毁当前Activity
            }
        }
    }

    /**
     * 销毁所有的Activity
     */
    public void removeAllActivity() {
        // 通过循环，把集合中的所有Activity销毁
        for (Activity activity : mActivityList) {
            if (activity != null) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}