package com.dajukeji.hslz.activity.taocoupon;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.coupon.CatCoupon;
import com.dajukeji.hslz.domain.coupon.CatCouponLink;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.NetworkUtils;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.dialog.LoadingDialog;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;

/**
 * Created by Administrator on 2017/9/12.
 */

public class GoodsDetailActivity extends HttpBaseActivity {

    private BridgeWebView mWebView;
    private CouponPresenter couponPresenter;

    private CatCoupon.ContentBean.GoodsListBean coupon;//优惠券
    private Gson gson;
    private TextView tv_http_error;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_detail);
        setTitleBar("粉丝福利购", true);
        mWebView =(BridgeWebView) findViewById(R.id.webView);
        tv_http_error = (TextView) findViewById(R.id.tv_http_error);
        if(!NetworkUtils.isNetworkAvailable(GoodsDetailActivity.this)){
            mWebView.setVisibility(View.GONE);
            tv_http_error.setVisibility(View.VISIBLE);
        }
        gson = new Gson();
        coupon=gson.fromJson(getIntent().getStringExtra("content"), CatCoupon.ContentBean.GoodsListBean.class);
        couponPresenter = new CouponPresenter(this);
        couponPresenter.getCatLink(getContext(),coupon.getNUM_IID(),coupon.getCOUPON_ID(), SPUtil.getPrefString("agencyId",""), DataType.coupon.getCatLink.toString());
        initWebView();
    }

    /**
     * 初始化webView
     */
    private void initWebView(){
        mWebView.setDefaultHandler(new DefaultHandler());
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress!=100){
                    LoadingDialog.showLoadingDialog(GoodsDetailActivity.this, null);
                }else{
                    LoadingDialog.hideLoadingDialog();
                    mWebView.callHandler("functionInJs", getIntent().getStringExtra("content"), new CallBackFunction() {
                        @Override
                        public void onCallBack(String data) {
                        }
                    });
                }
            }

        });
        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("shareUrl")) {
//                        因阿里百川错误而注释
//                        AlibcTrade.show(GoodsDetailActivity.this, new AlibcPage(coupon.getM_coupon_click_url()), new AlibcShowParams(OpenType.Native, false), null, null , new MyTradeCallback());
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        mWebView.getSettings().setJavaScriptEnabled(true);


    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.coupon.getCatLink.toString())){
            CatCouponLink catCouponLink = (CatCouponLink) object;
            coupon.setM_coupon_click_url(catCouponLink.getContent().getLink().getCoupon_click_url());
            coupon.setM_coupon_end_time(catCouponLink.getContent().getLink().getCoupon_end_time());
            coupon.setM_coupon_info(catCouponLink.getContent().getLink().getCoupon_info());
            coupon.setM_coupon_start_time(catCouponLink.getContent().getLink().getCoupon_start_time());
            //WebView加载web资源
            mWebView.loadUrl("file:///android_asset/newCatGoodsDetail.html");
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
