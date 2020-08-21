package com.dajukeji.hslz.activity.brandmarcket;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.BigBrandBean;
import com.dajukeji.hslz.domain.javaBean.BrandClassBean;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.fragment.BrandListFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

//import com.dajukeji.hs.adapter.recycleradapter.GridDivider;
//import com.dajukeji.hs.adapter.recycleradapter.GridItemDecoration;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandIndexActivity extends HttpBaseActivity {

    private List<String> list_title;
    private List<BrandListFragment> fragments;
    private RecyclerView big_brand_list;
    private TabLayout store_class_tab;
    private ViewPager viewPager;
    private BrandPresenter brandPresenter;
    private BaseRecyclerAdapter<BigBrandBean.ContentBean.BigBrandListBean> madapter;
    private MessagePresenter messagePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_brand_index);
        setTitleBar(R.string.text_brand_promotion, true,0,0);
        big_brand_list = (RecyclerView) findViewById(R.id.big_brand_list);
        store_class_tab = (TabLayout) findViewById(R.id.store_class_tab);
        store_class_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        big_brand_list.setLayoutManager(manager);
        madapter = new BaseRecyclerAdapter<BigBrandBean.ContentBean.BigBrandListBean>(getContext(), null, R.layout.item_big_brand_img) {
            @Override
            public void convert(BaseRecyclerHolder holder, final BigBrandBean.ContentBean.BigBrandListBean data, int position, boolean isScrolling) {
                if (position != madapter.getItemCount() - 1) {
                    holder.setImageByUrl(R.id.brand_store_name_iv,  data.getBrand_logo());
                } else {
                    holder.setImageResource(R.id.brand_store_name_iv, R.drawable.more_brand_img);
                }
            }
        };
        madapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<BigBrandBean.ContentBean.BigBrandListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, BigBrandBean.ContentBean.BigBrandListBean data, int position) {
                if (position == madapter.getItemCount() - 1) {
                    Intent intent = new Intent(BrandIndexActivity.this, BrandListActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(BrandIndexActivity.this, BrandStoreListActivity.class);
                    intent.putExtra(Constants.brand_id, data.getBrand_id());
                    intent.putExtra(Constants.brand_name, data.getBrand_name());
                    startActivity(intent);
                }
            }
        });
        big_brand_list.setAdapter(madapter);
        big_brand_list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(BrandIndexActivity.this, UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(BrandIndexActivity.this, MobailePhoneLoginActivity.class));
                    }

                }else {
                    startActivity(new Intent(BrandIndexActivity.this, WeChatLoginActivity.class));
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        brandPresenter = new BrandPresenter(this);
        fragments = new ArrayList<>();
        list_title = new ArrayList<>();
        brandPresenter.getBigBrandList(BrandIndexActivity.this, -1, DataType.brand.bigBrandList.toString());
        brandPresenter.getStoreClassList(BrandIndexActivity.this, DataType.brand.storeClassList.toString());
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if(event.message.equals("message")){
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.brand.bigBrandList.toString())) {
            BigBrandBean bigBrandBean = (BigBrandBean) object;
            List<BigBrandBean.ContentBean.BigBrandListBean> data = bigBrandBean.getContent().getBig_brand_list();
            BigBrandBean.ContentBean.BigBrandListBean bigBrandListBean = new BigBrandBean.ContentBean.BigBrandListBean();
//            bigBrandListBean.setBrand_logo(getResources().getDrawable(R.drawable.more_brand_img));
            bigBrandListBean.setFirst_letter("#");
            bigBrandListBean.setBrand_id(-1);
            bigBrandListBean.setBrand_name("更多品牌");
            data.add(bigBrandListBean);
            madapter.setData(bigBrandBean.getContent().getBig_brand_list());
        } else if (contentType.equals(DataType.brand.storeClassList.toString())) {
            BrandClassBean brandClassBean = (BrandClassBean) object;
//            BrandListFragment brandlistfragment = new BrandListFragment();
            BrandListFragment brandListFragmentIndex = new BrandListFragment();
            list_title.add("精选品牌");
            Bundle b = new Bundle();
            b.putInt("store_class_id", -1);
            brandListFragmentIndex.setArguments(b);
            fragments.add(brandListFragmentIndex);
            for (BrandClassBean.ContentBean.StoreClassListBean s : brandClassBean.getContent().getStore_class_list()) {
                list_title.add(s.getName());
                BrandListFragment brandListFragment = new BrandListFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("store_class_id", s.getId());
//                bundle.putBoolean("indexClassPage", false);
                brandListFragment.setArguments(bundle);
                fragments.add(brandListFragment);
            }
            viewPager.setOffscreenPageLimit(brandClassBean.getContent().getStore_class_list().size());
            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragments, list_title));
            store_class_tab.setupWithViewPager(viewPager);
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
    }

    class PagerAdapter extends FragmentPagerAdapter {
        List<BrandListFragment> fragmentList;
        List<String> titleList;

        public PagerAdapter(FragmentManager fm, List<BrandListFragment> fragmentList, List<String> titleList) {
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
            return list_title.get(position % list_title.size());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
