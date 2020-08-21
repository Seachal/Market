package com.dajukeji.hslz.activity.mine.refund;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.MyPagerAdapter;
import com.dajukeji.hslz.base.BaseActivity;
import com.dajukeji.hslz.fragment.mine.refund.RefundFragment;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.view.NoScrollViewPager;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import butterknife.BindArray;
import butterknife.BindView;

/**
 * 我的-退款售后
 */

public class RefundActivity extends BaseActivity {

    @BindView(R.id.refund_sliding_tabLayout)
    SlidingTabLayout mSlidingTabLayout;

    @BindView(R.id.refund_viewPager)
    NoScrollViewPager mViewPager;

    @BindArray(R.array.refund_array)
    String[] mTitles;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ((AliSdkApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_refund);
        setTitleBar(R.string.text_refund, true);
    }

    @Override
    protected void initView() {
        initViewPager();
    }

    private void initViewPager(){
        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager());
        for(int i = 0 ; i < mTitles.length; i++){
            RefundFragment refundFragment = new RefundFragment();
            Bundle bundle = new Bundle();
            if(i==0){
                bundle.putString("refundType","wait");
            }else {
                bundle.putString("refundType","all");
            }
            refundFragment.setArguments(bundle);
            mAdapter.addFragment(refundFragment, mTitles[i]);
        }
        mViewPager.setOffscreenPageLimit(mTitles.length);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

}
