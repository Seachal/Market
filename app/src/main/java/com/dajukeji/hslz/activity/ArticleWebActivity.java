package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.network.HttpAddress;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class ArticleWebActivity extends HttpBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_web);
        findViewById(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView title_bar_title = (TextView) findViewById(R.id.title_bar_title);
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String weblocation = bundle.getString("weblocation");
        title_bar_title.setText(title);
//        title_bar_title.setText(title);
        WebView webView = (WebView) findViewById(R.id.mWebView);
        webView.loadUrl(HttpAddress.mBaseUrl2 + weblocation);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
}
