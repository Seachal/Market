package com.dajukeji.hslz.activity.taocoupon;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.coupon.JDCoupon;
import com.dajukeji.hslz.domain.coupon.JDGoodLink;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.NetworkUtils;
import com.dajukeji.hslz.util.SPUtil;

/**
 * Created by Administrator on 2017/9/12.
 */

public class JDGoodsDetailActivity extends HttpBaseActivity {

    private CouponPresenter couponPresenter;
    private BridgeWebView mWebView;
    private JDCoupon.ContentBean.DataBean coupon;//优惠券
    private Gson gson;
    private TextView tv_http_error;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_jd_goods_detail);
        setTitleBar("粉丝福利购", true);
        couponPresenter = new CouponPresenter(this);
        initJDcoupon();
    }



    @Override
    protected void initView() {
        mWebView = (BridgeWebView) findViewById(R.id.webView);
        tv_http_error = (TextView) findViewById(R.id.tv_http_error);
        if(!NetworkUtils.isNetworkAvailable(JDGoodsDetailActivity.this)){
            mWebView.setVisibility(View.GONE);
            tv_http_error.setVisibility(View.VISIBLE);
        }
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress!=100){
                   showDialogLoading();
                }else{
                    hideDialogLoading();
                }
            }
        });
    }

    private void initJDcoupon(){
        gson = new Gson();
        coupon=gson.fromJson(getIntent().getStringExtra("content"), JDCoupon.ContentBean.DataBean.class);
        couponPresenter.getGoodsLink(getContext(),coupon.getGoods_id(),coupon.getDiscount_link(), SPUtil.getPrefString("agencyId",""), DataType.coupon.getJDGoodLink.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.coupon.getJDGoodLink.toString())){
            JDGoodLink jdGoodLink = (JDGoodLink) object;
            coupon.setLink(jdGoodLink.getContent().getLink());
            mWebView.loadUrl("file:///android_asset/jdGoodsDetail.html");
            mWebView.callHandler("functionInJs", gson.toJson(coupon), new CallBackFunction() {
                @Override
                public void onCallBack(String data) {

                }
            });
        }
    }
    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(mWebView.canGoBack())
            {
                mWebView.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
