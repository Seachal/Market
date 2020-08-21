package com.dajukeji.hslz.util;

/**
 * Created by Administrator on 2017/9/14.
 */

public interface ResultCallback {
    public abstract void onError(String msg);

    public abstract void onResponse(String result);
}
