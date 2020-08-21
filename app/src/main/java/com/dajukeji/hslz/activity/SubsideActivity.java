package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.fragment.mallFragment.TodaySubsidyFragment;
import com.dajukeji.hslz.network.presenter.ShopFindPresenter;
import com.dajukeji.hslz.util.SPUtil;

/**
 * 评选区页面
 */
public class SubsideActivity extends HttpBaseActivity {

    private LinearLayout linear, linear_two;
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subside);
        linear = (LinearLayout) findViewById(R.id.linear);

        back = (LinearLayout) findViewById(R.id.back);
        linear_two = (LinearLayout) findViewById(R.id.linear_two);
        linear_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
//                startActivity(messageIntent);
                if (!SPUtil.getPrefString("token", "").equals("")) {

                    Intent messageIntent = new Intent(SubsideActivity.this, UserMessageActivity.class);
                    startActivity(messageIntent);
                } else {
                    startActivity(new Intent(SubsideActivity.this, MobileActivity.class));
                }

            }
        });
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShopFindActivity.getStartIntent(SubsideActivity.this, ShopFindPresenter.FindType.EVALUATE_LOCAL));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TodaySubsidyFragment indexFragment = new TodaySubsidyFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, indexFragment).commit();
    }

}
