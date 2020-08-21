package com.dajukeji.hslz.util;

/**
 * Created by ${wangjiasheng} on 2017/12/27 0027.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 显示Toast提示的工具类
 * Created by Zengxiaoping on 2016/4/27.
 */
public class ToastUtils {

    protected volatile static ToastUtils toastUtils = null;

    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (toastUtils == null) {
            synchronized (ToastUtils.class) {
                if (toastUtils == null) {
                    toastUtils = new ToastUtils();
                }
            }
        }
        return toastUtils;
    }

    protected Handler handler = new Handler(Looper.getMainLooper());
    protected Toast toast = null;

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param context
     * @param content
     */
    public void showToast(final Context context, final String content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }

    public void showToast(final Context context, final int content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }

    /**
     * @param context  调用者context
     * @param content  要显示的内容
     * @param duration 持续时间
     */
    public void showToast(final Context context, final String content, final int duration) {
        if (TextUtils.isEmpty(content)) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, content, duration);
                    toast.show();
                } else {
                    toast.setText(content);
                    toast.setDuration(duration);
                    toast.show();
                }
            }
        });
    }

    public void showToast(final Context context, final int content, final int duration) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, content, duration);
                    toast.show();
                } else {
                    toast.setText(content);
                    toast.setDuration(duration);
                    toast.show();
                }
            }
        });
    }
}