package com.dajukeji.hslz.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dajukeji.hslz.global.AliSdkApplication;

/**
 * Created by ${wangjiasheng} on 2017/12/12 0012.
 */

public class SPUtil {

    /*
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "ShardPref_HS";

    public static String getPrefString(String key, final String defaultValue) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    public static void setPrefString(final String key, final String value) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().putString(key, value).commit();
    }

    public static boolean getPrefBoolean(final String key, final boolean defaultValue) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static void setPrefBoolean(final String key, final boolean value) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().putBoolean(key, value).commit();
    }

    public static int getPrefInt(final String key, final int defaultValue) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    public static void setPrefInt(final String key, final int value) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().putInt(key, value).commit();
    }

    public static float getPrefFloat(final String key, final float defaultValue) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    public static void setPrefFloat(final String key, final float value) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().putFloat(key, value).commit();
    }

    public static long getPrefLong(Context context, final String key,
                                   final long defaultValue) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    public static void setPrefLong(Context context, final String key,
                                   final long value) {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        settings.edit().putLong(key, value).commit();
    }

    public static boolean isKeyContains(final String key) {
        return AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).contains(key);
    }



    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void removeKey(String key)
    {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key).commit();
    }

    /**
     * 清除所有数据
     */
    public static void clearAll()
    {
        final SharedPreferences settings = AliSdkApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear().commit();
    }
}
