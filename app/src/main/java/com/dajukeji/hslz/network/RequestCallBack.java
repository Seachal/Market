package com.dajukeji.hslz.network;

/**
 * Created by ${wong} on 2017/9/13.
 */

public interface RequestCallBack {
    void onsuccess(String json);
    void onfailed(String exception);
}
