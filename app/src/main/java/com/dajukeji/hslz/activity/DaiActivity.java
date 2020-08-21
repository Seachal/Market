package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.CouponBean;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.event.JumpIndexEvent;
import com.dajukeji.hslz.fragment.IndextwFragment;
import com.dajukeji.hslz.fragment.IndextwoFragment;
import com.dajukeji.hslz.fragment.mine.IndextFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的代金券页
 */
public class DaiActivity extends HttpBaseActivity {
    private GoodPresenter goodPresenter;
    private LinearLayout topup;
    private TextView text_mon;
    private TextView free;
    private LinearLayout money;
    private RelativeLayout title_bar_return;
    private RelativeLayout title_bar_notice;
    private TabLayout tab_free_title;
    private ViewPager discoverfragment_page;
    private List<String> list_title;
    private List<Fragment> fragmentList;
    private int userId;
    private int pageNum = 1;
    private int pageSize = 10;
    private int flowType;
    private CouponBean couponBean;
    private List<CouponBean.DataBean.TransactionlistBean.ItemsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dai);
        goodPresenter = new GoodPresenter(this);
        Intent intent = getIntent();
        title_bar_return = findViewById(R.id.title_bar_return);
        title_bar_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_bar_notice = findViewById(R.id.title_bar_notice);
        title_bar_notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DaiActivity.this,HelpActivity.class));
            }
        });
        userId = intent.getIntExtra("userId", 0);
        text_mon = findViewById(R.id.text_mon);
        topup = findViewById(R.id.topuip);
        money = findViewById(R.id.money);
        free = findViewById(R.id.free);
        discoverfragment_page = (ViewPager) findViewById(R.id.discoverfragment_page);
        tab_free_title = (TabLayout) findViewById(R.id.tab_free_title);
        tab_free_title.setTabMode(TabLayout.MODE_FIXED);

        initDat();
//        text_mon.setText(mon+"");
        topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DaiActivity.this, TopActivity.class));
            }
        });
        money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DaiActivity.this, MoneyActivity.class);
                intent1.putExtra("money",text_mon.getText().toString());
                startActivity(intent1);
            }
        });
        goodPresenter.getCashCoupon(DaiActivity.this,  pageNum + "", pageSize + "", flowType, DataType.good.getCoupon.toString());
    }

    //重进页面时刷新数据
    @Override
    protected void onRestart() {
        super.onRestart();
        goodPresenter.getCashCoupon(DaiActivity.this, pageNum + "", pageSize + "", flowType, DataType.good.getCoupon.toString());
    }

    private void initDat() {

    }

    class MyAdapter extends FragmentPagerAdapter {

        List<Fragment> fragmentList;
        List<String> titleList;

        public MyAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return list_title.size();
        }
    }

    /**
     * 不知道这个方法会不会被mainActivity里的post触发
     * @param jumpIndexEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JumpIndexEvent jumpIndexEvent) {
        if (jumpIndexEvent.jumpTop.equals("jumpIndex")) {
            tab_free_title.scrollTo(0, 0);
            tab_free_title.getTabAt(0).select();
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        //if(DataType.good.getCoupon.toString())
        couponBean = (CouponBean) object;
        list = new ArrayList<>();
        list = couponBean.getData().getTransactionlist().getItems();
        if (couponBean.getCode() == 0) {

            text_mon.setText(String.format("%.5f", couponBean.getData().getAccountInfo().getAmount()));
//            text_mon.setText("123456789123456789");
            free.setText(String.format("%.5f", couponBean.getData().getAccountInfo().getFreeze()));
//            free.setText("200.00");
            fragmentList = new ArrayList<>();
            list_title = new ArrayList<>();
            IndextwoFragment fragment05 = new IndextwoFragment();
            IndextwFragment fragment04 = new IndextwFragment();
            IndextFragment fragment02 = new IndextFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("userId", userId);
            fragment05.setArguments(bundle);
            fragment04.setArguments(bundle);
            fragment02.setArguments(bundle);
            fragmentList.add(fragment05);
            fragmentList.add(fragment04);
            fragmentList.add(fragment02);
            list_title.add("全部");
            list_title.add("收入");
            list_title.add("支出");
            discoverfragment_page.setAdapter(new MyAdapter(getSupportFragmentManager(), fragmentList, list_title));
            tab_free_title.setupWithViewPager(discoverfragment_page);
        } else {
            Toast.makeText(getContext(), couponBean.getMsg() + "", Toast.LENGTH_SHORT).show();
        }
    }
}
