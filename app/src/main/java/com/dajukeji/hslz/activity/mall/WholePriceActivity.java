package com.dajukeji.hslz.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.adapter.shop_tab_adapter;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.comparePrice.PlanList;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.fragment.mallFragment.WholePriceFragment;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.ComparePricePresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 全网比价
 */
public class WholePriceActivity extends HttpBaseActivity {

    private ComparePricePresenter comparePricePresenter;
    private MessagePresenter messagePresenter;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private TextView tv_empty_goods;

    private List<String> mTitleList = new ArrayList<>();                                  //tab名称列表
    private List<Fragment> mFragmentList = new ArrayList<>();                             //定义要装fragment的列表
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AliSdkApplication)getApplication()).addActivity(this);
        comparePricePresenter = new ComparePricePresenter(this);
        messagePresenter = new MessagePresenter(this);
        comparePricePresenter.getPlanList(getContext(), DataType.mall.compareprice.toString());
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_whole_price);
        setTitleBar(R.string.text_whole_price,true,0,0);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_free_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_free_pager);
        tv_empty_goods = (TextView) findViewById(R.id.tv_empty_goods);
        $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(WholePriceActivity.this, UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(WholePriceActivity.this, MobailePhoneLoginActivity.class));
                    }

                }else {
                    startActivity(new Intent(WholePriceActivity.this, WeChatLoginActivity.class));
                }
            }
        });
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.mall.compareprice.toString())){
            PlanList planList = (PlanList) object;
            if(!planList.getContent().getPlan_list().isEmpty()){
                mTabLayout.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                initViewPager(planList);
            }else{
                tv_empty_goods.setVisibility(View.VISIBLE);
                mTabLayout.setVisibility(View.GONE);
                mViewPager.setVisibility(View.GONE);
            }
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            mTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        tv_empty_goods.setVisibility(View.VISIBLE);
        tv_empty_goods.setText("网络出问题,请稍后重试");
        mTabLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if(event.message.equals("message")){
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        }
    }

    private void initViewPager(PlanList planList){

        //动态设置TabLayout布局属性
        if(planList.getContent().getPlan_list().size()==5){
            mTabLayout.setTabSpaceEqual(false);
        }else{
            mTabLayout.setTabSpaceEqual(true);
        }

        int index = 0;
        for(PlanList.ContentBean.PlanListBean temp : planList.getContent().getPlan_list()){
            if(index == 0){
                mTitleList.add(temp.getBegin_time()+"\n"+"抢购中");
            }else{
                mTitleList.add(temp.getBegin_time()+"\n"+"即将开始");
            }
            WholePriceFragment wholePriceFragment =  new WholePriceFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("plan_id",temp.getPlan_id());
            bundle.putInt("left_second",temp.getLeft_second());
            bundle.putInt("index",index);
            wholePriceFragment.setArguments(bundle);
            mFragmentList.add(wholePriceFragment);
            index++;
        }
        mAdapter = new shop_tab_adapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

}
