package com.dajukeji.hslz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cdr on 2017/11/28.
 * 保存了一个 订单页页面的商品集合 ，集合中有每个商品的具体布局和标题
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragment = new ArrayList<Fragment>();
    private final List<String> mFragmentTitle = new ArrayList<String>();

    public List<Fragment> getFragmentList(){
        return mFragment;
    }

    public void addFragment(Fragment fragment, String title){
        mFragment.add(fragment);
        mFragmentTitle.add(title);
    }

    public void clearFragment(){
        mFragment.clear();
        mFragmentTitle.clear();
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitle.get(position);
    }
}
