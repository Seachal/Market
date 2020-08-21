package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.fragment.HealthArticleListFragment;
import com.dajukeji.hslz.fragment.LifeArticleListFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class HealthArticleListActivity extends HttpBaseActivity {

    private MessagePresenter messagePresenter;
    private ViewPager health_article_page;
    private List<Fragment> fragmentList;
    private SlidingTabLayout tabLayout;
    private List<String> list_Title;
    private HealthArticleListFragment healthArticleListFragment;
    private LifeArticleListFragment lifeArticleListFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Override
    public void showHttpError(String error, String contenttype) {
        super.showHttpError(error, contenttype);
    }

    @Override
    public void setResultData(Object object, String contentType) {
        if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if(event.message.equals("message")){
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_health_article_list);
        setTitleBar(R.string.text_china_taoism, true, 0, 0);
        tabLayout = (SlidingTabLayout) findViewById(R.id.timeline_layout);
        fragmentList = new ArrayList<>();
        healthArticleListFragment = new HealthArticleListFragment();
        lifeArticleListFragment = new LifeArticleListFragment();
        fragmentList.add(healthArticleListFragment);
        fragmentList.add(lifeArticleListFragment);
        health_article_page = (ViewPager) findViewById(R.id.health_article_page);
        list_Title = new ArrayList<>();
        list_Title.add("享健康");
        list_Title.add("惠生活");
        HealthArticlePageAdapter healthArticlePageAdapter = new HealthArticlePageAdapter(getSupportFragmentManager(), fragmentList, list_Title);
        health_article_page.setAdapter(healthArticlePageAdapter);
        tabLayout.setViewPager(health_article_page);
        $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(HealthArticleListActivity.this, UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(HealthArticleListActivity.this, MobailePhoneLoginActivity.class));
                    }

                }else {
                    startActivity(new Intent(HealthArticleListActivity.this, WeChatLoginActivity.class));
                }
            }
        });
    }

    class HealthArticlePageAdapter extends FragmentPagerAdapter{
        List<Fragment> fragmentList;
        List<String> titleList;

        public HealthArticlePageAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_Title.get(position % list_Title.size());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
