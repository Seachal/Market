package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

/**
 * 积分抽奖
 * */
public class InitAwardActivity extends HttpBaseActivity {

    private MessagePresenter messagePresenter;
    public WebView wb_sign_receive;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_sign_receive);
        setTitleBar(R.string.prize_draw, true, 0, 0);
        String url = HttpAddress.BASE_URL+HttpAddress.init_award+SPUtil.getPrefString("token","");
        wb_sign_receive = (WebView) findViewById(R.id.wb_sign_receive);
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
                        LoadingDialog.showLoadingDialog(InitAwardActivity.this, "");
                    }
                }
            }
        });
        wb_sign_receive.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
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
            wb_sign_receive.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            wb_sign_receive.clearHistory();
            ((ViewGroup) wb_sign_receive.getParent()).removeView(wb_sign_receive);
            wb_sign_receive.destroy();
            wb_sign_receive = null;
            hideDialogLoading();
        }
        super.onDestroy();
    }

}
