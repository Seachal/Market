package com.dajukeji.hslz.activity.mine.order;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.MyPagerAdapter;
import com.dajukeji.hslz.base.BaseActivity;
import com.dajukeji.hslz.fragment.mine.order.OrderFragment;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * 我的订单页
 */

public class OrderActivity extends BaseActivity {

    @BindView(R.id.order_sliding_tabLayout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.order_viewPager)
    ViewPager mViewPager;

    @BindArray(R.array.my_order_array)
    String[] mNormalTitles;

    @BindArray(R.array.my_order_status_array)
    String[] mNormalOrderStatus;


    private int mStatus = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ((AliSdkApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_order);
        mStatus = getIntent().getIntExtra("status" , 0);
        setTitleBar( R.string.text_my_order , true);
    }

    @Override
    protected void initView() {
        initViewPager();
    }

    private void initViewPager(){
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for(int i = 0 ; i < mNormalTitles.length; i++){
            OrderFragment orderFragment=  new OrderFragment();
            Bundle bundle = new Bundle();
            bundle.putString("mStatus",mNormalOrderStatus[i]);
            orderFragment.setArguments(bundle);
            mAdapter.addFragment(orderFragment, mNormalTitles[i]);//往适配器中加添加数据
        }
        mViewPager.setOffscreenPageLimit(mNormalTitles.length);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);

        mViewPager.setCurrentItem(mStatus);
    }

}