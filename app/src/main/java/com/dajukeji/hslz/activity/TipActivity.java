package com.dajukeji.hslz.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dajukeji.hslz.R;

import static com.dajukeji.hslz.network.HttpAddress.mBaseUrl;


/**
 * Created by Administrator on 2017/9/19.
 */

public class TipActivity extends Activity implements View.OnClickListener {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        initToolbar();
    }
    private void initToolbar() {
        ImageView back=(ImageView)findViewById(R.id.back);
        TextView title=(TextView)findViewById(R.id.title);
        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        back.setOnClickListener(this);
        webView= (WebView) findViewById(R.id.webView);
        if("省钱指南".equals(intent.getStringExtra("title"))){
            webView.loadUrl(mBaseUrl+"/app/hs/tip.do");
        }else if("大V一族APP".equals(intent.getStringExtra("title"))){
            webView.loadUrl(mBaseUrl+"/app/hs/introduction.do");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}
