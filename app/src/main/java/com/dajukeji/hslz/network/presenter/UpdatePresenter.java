package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.dajukeji.hslz.domain.javaBean.CheckVersionBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;

/**
 * Created by ${wangjiasheng} on 2018/1/9 0009.
 */

public class UpdatePresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;

    public UpdatePresenter(IView iView) {
        this.okGoEngine = OkGoEngine.getInstance();
        this.iView = iView;
        gson = new Gson();
    }

    public void checkUpdate(Object tag, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.checkUpdate;
        okGoEngine.post(tag, url, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    CheckVersionBean checkVersionBean = gson.fromJson(json, CheckVersionBean.class);
                    if (checkVersionBean.getMessage().equals("成功") && checkVersionBean.getStatus().equals("0")) {
                        iView.setResultData(checkVersionBean, DataType.update.checkVersion.toString());
                    }
                }catch (Exception e){
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    public void update() {

    }
}
