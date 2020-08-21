package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.fragment.GoodListFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.domain.javaBean.GoodsClassBean;
import com.dajukeji.hslz.network.presenter.GoodPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class GroupBuyListActivity extends HttpBaseActivity {

    private TabLayout tab_free_title;                            //定义TabLayout
    private ViewPager vp_free_pager;                             //定义viewPager
    private GoodPresenter goodPresenter;
    private int currentPage;
    private List<String> list_Title;
    private List<GoodsClassBean.ContentEntity.GoodsClassEntity> goodsClass;
    private GoodsPageAdapter goodsPageAdapter;
    private List<GoodListFragment> fragmentList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_group_buy);
        setTitleBar("拼团专区", true);
        tab_free_title = (TabLayout) findViewById(R.id.tab_free_title);
        vp_free_pager = (ViewPager) findViewById(R.id.vp_free_pager);
        tab_free_title.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void initData() {
        super.initData();
        goodPresenter = new GoodPresenter(this);
        goodPresenter.getGoodClassList(getContext(), DataType.good.GetGoodsClassList.toString());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.good.GetGoodsClassList.toString())) {
            GoodsClassBean goodsClassBean = (GoodsClassBean) object;
            list_Title = new ArrayList<>();
            fragmentList = new ArrayList<>();
            List<GoodsClassBean.ContentEntity.GoodsClassEntity> goodsClass = goodsClassBean.getContent().getGoodsClass();
            goodsClass = goodsClassBean.getContent().getGoodsClass();
            for (GoodsClassBean.ContentEntity.GoodsClassEntity goodsClassEntity : goodsClass) {
                GoodListFragment goodListFragment = new GoodListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("gc_id", goodsClassEntity.getId());
                goodListFragment.setArguments(bundle);
                fragmentList.add(goodListFragment);
                list_Title.add(goodsClassEntity.getClassName());
            }
            goodsPageAdapter = new GoodsPageAdapter(getSupportFragmentManager(), fragmentList, list_Title);
            vp_free_pager.setAdapter(goodsPageAdapter);
            tab_free_title.setupWithViewPager(vp_free_pager);
        } else if (contentType.equals(contentType.equals(DataType.myPresenterType.getGoodsList))) {

        }
    }

    class GoodsPageAdapter extends FragmentPagerAdapter{
        List<GoodListFragment> fragmentList;
        List<String> titleList;

        public GoodsPageAdapter(FragmentManager fm, List<GoodListFragment> fragmentList, List<String> titleList) {
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

}
