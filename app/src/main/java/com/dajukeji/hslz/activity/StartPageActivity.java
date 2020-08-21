package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * Created by Administrator on 2017/9/21.
 */

public class StartPageActivity extends HttpBaseActivity {

    private ImageView start_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_start_page);
        start_iv = (ImageView) findViewById(R.id.start_iv);
        GlideEngine.loadThumbnail(this,R.drawable.start_page,start_iv,R.drawable.start_page);
        handler.sendEmptyMessageDelayed(0,2000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            init();
            super.handleMessage(msg);
        }
    };

    private void init() {
       if (SPUtil.getPrefBoolean("FirstRun", true)) {
            startActivity(new Intent(StartPageActivity.this, GuideActivity.class));
            finish();
        } else {
            startActivity(new Intent(StartPageActivity.this, MainActivity.class));
            finish();
        }
    }
}
