package com.dajukeji.hslz.network.presenter;

import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.FileUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 * 意见反馈
 */

public class FeedBackPresenter {
    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;
//    private Map<String, String> map;

    public FeedBackPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }
    /**
     * 意见反馈
     *
     * */

    public void feedback(Object tag, String phone, String content, List<File> photo, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 +HttpAddress.feedback;
        HttpParams map = new HttpParams();
        map.put("phone", phone);
        map.put("content", content);
        for(int i=0;i<photo.size();i++){
            map.put("imgStr"+i, FileUtils.fileToBase64(photo.get(i)));
        }
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    JSONObject jsonO = new JSONObject(json);
                    if(jsonO.get("status").toString().equals("0")){
                        iView.setResultData("sucess",contentType);
                    }else{
                        onfailed(jsonO.get("message").toString());
                        iView.hideLoading();
                    }
                } catch (JSONException e) {
                    onfailed("反馈失败");
                    iView.hideLoading();
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }


}
