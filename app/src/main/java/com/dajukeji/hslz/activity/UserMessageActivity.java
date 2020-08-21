package com.dajukeji.hslz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.fragment.SessionListFragment;
import com.dajukeji.hslz.fragment.SystemMessageFragment;
import com.dajukeji.hslz.view.NoScrollViewPager;

import java.util.ArrayList;

public class UserMessageActivity extends HttpBaseActivity {

    private NoScrollViewPager noscroll_user_message;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    private LinearLayout transaction;
//    private TextView transaction_ind;
    private LinearLayout system_notic;
    private TextView system_notic_ind;
    private TextView tv_system_message;
    private TextView tv_chart_message;

    private LinearLayout session_ll;
    private TextView session_id;
    private int setOffscreenPage = 0; // 当前页面偏移量

    private String messageType = "orderMessage"; // 消息类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDate() {
        fragments.add(new SystemMessageFragment());
        fragments.add(new SessionListFragment());
    }
    @Override
    protected void initView() {
        initDate();
        setContentView(R.layout.activity_user_message);
        setTitleBar(R.string.text_user_message,true);
        noscroll_user_message = (NoScrollViewPager)findViewById(R.id.noscroll_user_message);
        tv_system_message = (TextView) findViewById(R.id.tv_system_message);
        tv_chart_message = (TextView) findViewById(R.id.tv_chart_message);
        system_notic = (LinearLayout) findViewById(R.id.system_notice);
        system_notic_ind = (TextView) findViewById(R.id.system_notice_ind);
        session_ll = (LinearLayout) findViewById(R.id.session_ll);
        session_id = (TextView) findViewById(R.id.session_id);
        session_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noscroll_user_message.setCurrentItem(2, false);
                session_id.setVisibility(View.VISIBLE);
                system_notic_ind.setVisibility(View.INVISIBLE);
                tv_chart_message.setTextColor(Color.parseColor("#ff4f00"));
                tv_system_message.setTextColor(Color.parseColor("#878787"));
            }
        });
        system_notic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageType = "systemMessage";
                session_id.setVisibility(View.INVISIBLE);
                system_notic_ind.setVisibility(View.VISIBLE);
                tv_system_message.setTextColor(Color.parseColor("#ff4f00"));
                tv_chart_message.setTextColor(Color.parseColor("#878787"));
                noscroll_user_message.setCurrentItem(0, false);
            }
        });
        noscroll_user_message.setAdapter(adapter);
        noscroll_user_message.setOffscreenPageLimit(setOffscreenPage);
    }

    /**
     * viewpager适配器
     */
    private PagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    };
}
