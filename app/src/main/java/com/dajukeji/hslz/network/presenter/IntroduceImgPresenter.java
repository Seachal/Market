package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.IntroduceImgBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.google.gson.Gson;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:03
 * 作用:
 */
public class IntroduceImgPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
    private CacheMode cacheMode;

    public IntroduceImgPresenter(IView iView) {
        this.iView = iView;
        this.gson = new Gson();
        this.okGoEngine = new OkGoEngine();
    }


    /**
     * 引导图
     */
    public void getIntroduceImg(Object tag, final String contentType) {
        String url = HttpAddress.mBaseUrl2 + HttpAddress.introduceImg;
        HttpParams params = new HttpParams();
        params.put("system","android");
        okGoEngine.postMap(tag, url, params, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                IntroduceImgBean bean = gson.fromJson(json, IntroduceImgBean.class);
                iView.setResultData(bean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception + "", contentType);
            }
        });
    }
}
