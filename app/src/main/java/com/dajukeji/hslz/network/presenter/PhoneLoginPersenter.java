package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.domain.freeOrder.PhoneloginBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

public class PhoneLoginPersenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public PhoneLoginPersenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }
    public void loginPhoneNumber(Object tag,String phoneNumber ,String password , final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl3 + HttpAddress.login_byphone;
        HttpParams map = new HttpParams();
        map.put("phoneNumber",phoneNumber);
        map.put("password",password);
        okGoEngine.postMap(tag, url,map, new RequestCallBack() {

            @Override
            public void onsuccess(String json) {
                try {
                    PhoneloginBean phoneloginBean = gson.fromJson(json, PhoneloginBean.class);
                    if(phoneloginBean.getStatus().equals("0")){
                        iView.setResultData(phoneloginBean, contentType);
                    }else {
                        onfailed(phoneloginBean.getMessage());
                        iView.hideLoading();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }
}
