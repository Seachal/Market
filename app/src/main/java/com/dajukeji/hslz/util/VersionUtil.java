package com.dajukeji.hslz.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2017/11/30.
 */

public class VersionUtil {

    /**
     * 获取当前程序的版本号
     * @param context
     * @return
     * @throws Exception
     */
    public static int getVersionCode(Context context) {
        try {
            //获取packagemanager的实例
            PackageManager packageManager =context.getPackageManager();
            //getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  1;
    }

    /**
     * 获取当前应用的版本号
     * @param context
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context) throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        String version = packInfo.versionName;
        return version;
    }
}
