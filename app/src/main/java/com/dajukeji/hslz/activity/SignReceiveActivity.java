package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.home.coupon.CouponExchangeActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.NetworkUtils;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

/**
 * 签到积分
 * */

public class SignReceiveActivity extends HttpBaseActivity {

    private MessagePresenter messagePresenter;
    public WebView wb_sign_receive;
    private TextView tv_http_error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
    }


    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_sign_receive);
        setTitleBar(R.string.text_sign_receive, true, 0, 0);
        final String url = HttpAddress.BASE_URL+HttpAddress.home_sign+"?token="+ SPUtil.getPrefString("token","");
        wb_sign_receive = (WebView) findViewById(R.id.wb_sign_receive);
        tv_http_error = (TextView) findViewById(R.id.tv_http_error);
        if(!NetworkUtils.isNetworkAvailable(SignReceiveActivity.this)){
            wb_sign_receive.setVisibility(View.GONE);
            tv_http_error.setVisibility(View.VISIBLE);
        }
        wb_sign_receive.loadUrl(url);
        wb_sign_receive.getSettings().setAppCacheEnabled(true);// 设置启动缓存
        wb_sign_receive.getSettings().setJavaScriptEnabled(true);
        wb_sign_receive.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        wb_sign_receive.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        wb_sign_receive.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//适应屏幕，内容将自动缩放
        wb_sign_receive.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress==100){
                    hideDialogLoading();
                }else {
                    if(wb_sign_receive!=null){
                        LoadingDialog.showLoadingDialog(SignReceiveActivity.this, "");
                    }
                }
            }
        });
        wb_sign_receive.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                hideDialogLoading();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                hideDialogLoading();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 步骤2：根据协议的参数，判断是否是所需要的url
                // 一般根据scheme（协议格式） & authority（协议名）判断（前两个参数）
                //假定传入进来的 url = "js://webview?arg1=111&arg2=222"（同时也是约定好的需要拦截的）
                Uri uri = Uri.parse(url);
                // 如果url的协议 = 预先约定的 js 协议
                // 就解析往下解析参数
                if ( uri.getScheme().equals("js")) {
                    // 如果 authority  = 预先约定协议里的 webview，即代表都符合约定的协议
                    // 所以拦截url,下面JS开始调用Android需要的方法
                    if (uri.getAuthority().equals("webview")) {
                        //  步骤3：
                        // 执行JS所需要调用的逻辑
                        // 可以在协议上带有参数并传递到Android上
                        /*HashMap<String, String> params = new HashMap<>();
                        Set<String> collection = uri.getQueryParameterNames();*/
                        Intent intent = new Intent(SignReceiveActivity.this, CouponExchangeActivity.class);
                        SignReceiveActivity.this.startActivity(intent);
                    }
                    return true;
                }

                if(url.equals(HttpAddress.BASE_URL+HttpAddress.init_award+SPUtil.getPrefString("token",""))){
                    Intent intent = new Intent(SignReceiveActivity.this, InitAwardActivity.class);
                    SignReceiveActivity.this.startActivity(intent);
                    return  true;
                }

                if(url.equals(HttpAddress.BASE_URL+HttpAddress.oneMonthRecord+"?token="+SPUtil.getPrefString("token",""))){
                    Intent intent = new Intent(SignReceiveActivity.this, MyCouponGoodsActivity.class);
                    SignReceiveActivity.this.startActivity(intent);
                    return  true;
                }

                view.loadUrl(url);
                return false;

            }
        });
    }
    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(wb_sign_receive.canGoBack())
            {
                wb_sign_receive.goBack();//返回上一页面
                return true;
            }
            else
            {
                finish();//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //销毁WebView 防止泄露
    @Override
    protected void onDestroy() {
        OkGo.getInstance().cancelTag(this);
        if (wb_sign_receive != null) {
            hideDialogLoading();
            wb_sign_receive.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wb_sign_receive.clearHistory();
            ((ViewGroup) wb_sign_receive.getParent()).removeView(wb_sign_receive);
            wb_sign_receive.destroy();
            wb_sign_receive = null;
        }
        super.onDestroy();
    }
}
