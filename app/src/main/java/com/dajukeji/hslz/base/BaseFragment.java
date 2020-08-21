package com.dajukeji.hslz.base;

import android.content.Context;
import android.view.View;

import com.dajukeji.hslz.network.IView;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class BaseFragment implements IView, View.OnClickListener {
    @Override
    public void onClick(View v) {

    }



//    protected Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            BaseFragment.this.handleMessage(msg);
//        }
//    };

//    protected void handleMessage(Message msg) {
//        switch (msg.what) {
//            case Constants.RESPONSE:
//                break;
//            case Constants.NORESPONSE:
//                showHttpError();
//                break;
//        }
//    }

    @Override
    public void setResultData(Object object, String dataType) {

    }

    @Override
    public void showHttpError(String error, String dataType) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
