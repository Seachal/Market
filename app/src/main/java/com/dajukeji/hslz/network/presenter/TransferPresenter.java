package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.domain.javaBean.TransferTargetInfoBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * 作者: Li_ke
 * 日期: 2019/1/19 13:30
 * 作用: 转账
 */
public class TransferPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public TransferPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }

    /**
     * 查询转账人信息
     *
     * @param phone
     */
    public void findTransferTarget(Object tag, String phone, final String dataType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.transferTargetInfo;
        HttpParams httpParams = new HttpParams();
//        httpParams.put("phone", phone);
        httpParams.put("token", SPUtil.getPrefString("token", ""));
        httpParams.put("mobile", phone);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    TransferTargetInfoBean info = gson.fromJson(json, TransferTargetInfoBean.class);
                    iView.setResultData(info, dataType);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, dataType);
            }
        });
    }

    public void doTransfer(Object tag, String targetId, String money, String password, final String dataType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.transfer;
        HttpParams httpParams = new HttpParams();
        httpParams.put("token", SPUtil.getPrefString("token", ""));
        httpParams.put("password", password);
        httpParams.put("user_id", targetId);
        httpParams.put("price", money);
//        旧参数
//        String myPhone = SPUtil.getPrefString("phoneNumber", "");
//        httpParams.put("sourcePhone", myPhone);
//        httpParams.put("targetPhone", phone);
//        httpParams.put("Amount", money);
//        httpParams.put("payPass", password);
        okGoEngine.postMap(tag, url, httpParams, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    BaseStatusMessageBean bean = gson.fromJson(json, BaseStatusMessageBean.class);
                    iView.setResultData(bean, dataType);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, dataType);
            }
        });
    }
}
