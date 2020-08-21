package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.network.presenter.ShopFindPresenter;
import com.dajukeji.hslz.util.SPUtil;

public class BoomActivity extends HttpBaseActivity {
    private LinearLayout linear,linear_two;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boom);
        linear = (LinearLayout) findViewById(R.id.linear);
        back = (ImageView) findViewById(R.id.back);
        linear_two = (LinearLayout) findViewById(R.id.linear_two);
        linear_two.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
//                startActivity(messageIntent);
                if(!SPUtil.getPrefString("token","").equals("")){

                    Intent messageIntent = new Intent(BoomActivity.this, UserMessageActivity.class);
                    startActivity(messageIntent);
                }else {
                    startActivity(new Intent(BoomActivity.this, MobileActivity.class));
                }

            }
        });
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShopFindActivity.getStartIntent(BoomActivity.this, ShopFindPresenter.FindType.BOOM_HOT_LOCAL));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
