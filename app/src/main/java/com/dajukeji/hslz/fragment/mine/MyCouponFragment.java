package com.dajukeji.hslz.fragment.mine;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.NetworkUtils;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

/**
 * 我的省券列表
 */
@SuppressLint("ValidFragment")
public  class MyCouponFragment extends HttpBaseFragment {
    public WebView mWebView;
    private TextView tv_http_error;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.my_coupon_web, container, false);
        }
        initView(rootView);
        return rootView;
    }

    private void initView(View v){
        String url = HttpAddress.BASE_URL+HttpAddress.oneMonthRecord+"?token="+ SPUtil.getPrefString("token","");
        mWebView = (WebView) v.findViewById(R.id.mWebView);
        tv_http_error = (TextView) v.findViewById(R.id.tv_http_error);
        if(!NetworkUtils.isNetworkAvailable(getActivity())){
            mWebView.setVisibility(View.GONE);
            tv_http_error.setVisibility(View.VISIBLE);
        }
        mWebView.loadUrl(url);
        mWebView.getSettings().setAppCacheEnabled(true);// 设置启动缓存
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        mWebView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放
        mWebView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
               hideDialogLoading();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(mWebView!=null){
                    LoadingDialog.showLoadingDialog(getContext(), "");
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
            }
        });
    }


    //销毁WebView 防止泄露
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
