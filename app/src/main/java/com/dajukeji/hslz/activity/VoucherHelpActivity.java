package com.dajukeji.hslz.activity;

import android.os.Bundle;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;

public class VoucherHelpActivity extends HttpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_voucher_help);
        super.onCreate(savedInstanceState);
        setTitleBar("帮助", true);
    }
}
