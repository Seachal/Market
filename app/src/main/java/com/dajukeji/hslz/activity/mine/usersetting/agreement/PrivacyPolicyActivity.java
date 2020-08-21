package com.dajukeji.hslz.activity.mine.usersetting.agreement;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.BaseActivity;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.NetworkUtils;

import butterknife.BindView;

/**
 * 用户协议中的服务协议与隐私协议
 */

public class PrivacyPolicyActivity extends BaseActivity {

    @BindView(R.id.webView_progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.webView)
    WebView mWebView;

    @BindView(R.id.tv_http_error)
    TextView tv_http_error;


    private String mPage;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_privacy_policy);
        mPage = getIntent().getStringExtra("page");
        setTitleBar(TextUtils.equals(mPage, "service") ? R.string.text_service_agreement : R.string.text_privacy_policy, true);
    }

    @Override
    protected void initView() {
        init();
        if(!NetworkUtils.isNetworkAvailable(PrivacyPolicyActivity.this)){
            mWebView.setVisibility(View.GONE);
            tv_http_error.setVisibility(View.VISIBLE);
        }
        mWebView.loadUrl(TextUtils.equals(mPage, "service") ? HttpAddress.serviceAgreement : HttpAddress.privacyPolicy);
    }

    private void init() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            //覆写shouldOverrideUrlLoading实现内部显示网页
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行
                //网络未连接
//                showErrorPage();
            }

            //处理网页加载失败时
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行
//                showErrorPage();//显示错误页面
            }

        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if(newProgress == 100){
                    mProgressBar.setVisibility(View.GONE);
                }
                else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }

            }
        });

    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(mWebView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                mWebView.goBack();
                return true;
            }
            else {
                PrivacyPolicyActivity.this.finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
