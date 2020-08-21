package com.dajukeji.hslz.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;

public class AboutActivity extends HttpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitleBar("关于",true);
    }
}
