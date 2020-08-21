package com.dajukeji.hslz.activity.mall;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.fragment.mallFragment.MySubsidyFragment;
import com.dajukeji.hslz.fragment.mallFragment.TodaySubsidyFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 补贴专区
 */
public class SubsidyActivity extends HttpBaseActivity implements View.OnClickListener{

    private MessagePresenter messagePresenter;
    private LinearLayout ll_today_subsidy ,ll_my_subsidy; // 今日补贴，我的补贴
    private TextView tv_today_subsidy , tv_my_subsidy; // 文本
    private ImageView iv_today_subsidy , iv_my_subsidy; // 图片

    private NoScrollViewPager noscroll_subsidy;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private int setOffscreenPage = 0; // 当前页面偏移量
    private String choseFree = "today"; // 当前选择界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();
        initView();
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
    }

    private void initDate() {
        fragments.add(new TodaySubsidyFragment());
        fragments.add(new MySubsidyFragment());
    }

    @Override
    protected void initView() {
        initDate();
        setContentView(R.layout.activity_subsidy);
        setTitleBar(R.string.text_subsidy,true,0,0);
        noscroll_subsidy = (NoScrollViewPager)findViewById(R.id.noscroll_subsidy);
        ll_today_subsidy = (LinearLayout) findViewById(R.id.ll_today_subsidy);  // 今日补贴
        ll_my_subsidy = (LinearLayout) findViewById(R.id.ll_my_subsidy);  //我的补贴
        tv_today_subsidy = (TextView) findViewById(R.id.tv_today_subsidy);
        tv_my_subsidy = (TextView) findViewById(R.id.tv_my_subsidy);
        iv_today_subsidy = (ImageView) findViewById(R.id.iv_today_subsidy);
        iv_my_subsidy = (ImageView) findViewById(R.id.iv_my_subsidy);

        noscroll_subsidy.setAdapter(adapter);
        noscroll_subsidy.setOffscreenPageLimit(setOffscreenPage);
        ll_today_subsidy.setOnClickListener(this);
        ll_my_subsidy.setOnClickListener(this);
        String type = getIntent().getStringExtra("type");
        if(type!=null&&!type.equals("")){
            choseFree = type;
            tv_my_subsidy.setTextColor(Color.rgb(15, 15, 15));
            tv_today_subsidy.setTextColor(Color.rgb(87, 87, 87));
            iv_my_subsidy.setImageResource(R.drawable.subsidy_btn_sel);
            iv_today_subsidy.setImageResource(R.drawable.freetab_btn_nor);
            noscroll_subsidy.setCurrentItem(1, false);
        }
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_today_subsidy: // 今日补贴
                if(choseFree.equals("me")){  // 如果当前页为我的补贴，切换为今日补贴
                    tv_today_subsidy.setTextColor(Color.rgb(15, 15, 15));
                    tv_my_subsidy.setTextColor(Color.rgb(87, 87, 87));
                    iv_today_subsidy.setImageResource(R.drawable.freetab_btn_sel);
                    iv_my_subsidy.setImageResource(R.drawable.subsidy_btn_nor);
                }
                choseFree = "today";
                noscroll_subsidy.setCurrentItem(0, false);
                break;
            case R.id.ll_my_subsidy: // 我的补贴
                if(choseFree.equals("today")){ // 如果当前页为今日补贴，切换为我的补贴
                    tv_my_subsidy.setTextColor(Color.rgb(15, 15, 15));
                    tv_today_subsidy.setTextColor(Color.rgb(87, 87, 87));
                    iv_my_subsidy.setImageResource(R.drawable.subsidy_btn_sel);
                    iv_today_subsidy.setImageResource(R.drawable.freetab_btn_nor);
                }
                choseFree = "me";
                noscroll_subsidy.setCurrentItem(1, false);
                break;
        }
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
