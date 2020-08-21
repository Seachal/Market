package com.dajukeji.hslz.network.presenter;

import android.util.Log;

import com.dajukeji.hslz.domain.RedbagBean;
import com.dajukeji.hslz.domain.javaBean.RedPackageMoneyBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/1/2.
 * 全网比价
 */

public class RedPackagePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public RedPackagePresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 抢到红包,确认金额
     */
    public void grabRedPackage(Object tag, int red_id, String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.grabRedPackage;
        HttpParams map = new HttpParams();
        map.put("red_id", red_id);
        map.put("token", token);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    RedPackageMoneyBean bean = gson.fromJson(json, RedPackageMoneyBean.class);
                    if (bean.getStatus().equals("0")) {
                        iView.setResultData(bean, contentType);
                    } else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 获得红包列表
     */
    public void getRedPackageList(Object tag, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getRedPackageList;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    RedbagBean bean = gson.fromJson(json, RedbagBean.class);
                    if (bean.getStatus().equals("0")) {
                        iView.setResultData(bean, contentType);
                    } else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void getRedPackaged(Object tag, String record_id ,final String contentType){
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getRedPackaged;
        HttpParams map = new HttpParams();
//        map.put("token", SPUtil.getPrefString("token", ""));
        map.put("record_id", record_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {

                    Log.d("hongbao fanhui",json);
                    JsonObject bean = new JsonParser().parse(json).getAsJsonObject();
                    if (bean.get("status").equals("0")) {
                        iView.setResultData(bean,contentType);
                    } else {
                        onfailed("");
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, "上传红包id失败");
            }
        });
    }
}
