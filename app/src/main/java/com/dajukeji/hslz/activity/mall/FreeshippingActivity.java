package com.dajukeji.hslz.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
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
import com.dajukeji.hslz.domain.javaBean.GoodsClassBean;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.fragment.mallFragment.FreeShippingFragment;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * 9.9 包邮
 */
public class FreeshippingActivity extends HttpBaseActivity {

    private GoodPresenter goodPresenter;
    private MessagePresenter messagePresenter;
    private SlidingTabLayout mTabLayout;

    private ViewPager mViewPager;
    private List<String> mTitleList = new ArrayList<>();                                  //tab名称列表
    private List<Fragment> mFragmentList = new ArrayList<>();                             //定义要装fragment的列表
    private FragmentPagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ((AliSdkApplication)getApplication()).addActivity(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_freeshipping);
        setTitleBar(R.string.text_free_goods, true, 0, 0);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_free_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_free_pager);
        goodPresenter = new GoodPresenter(this);
        goodPresenter.getGoodClassList(getContext(), DataType.good.getGoodClassList.toString());
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
        $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(FreeshippingActivity.this, UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(FreeshippingActivity.this, MobailePhoneLoginActivity.class));
                    }
                }else {
                    startActivity(new Intent(FreeshippingActivity.this, WeChatLoginActivity.class));
                }
            }
        });
    }


    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.good.getGoodClassList.toString())){
            GoodsClassBean goodsClassBean = (GoodsClassBean) object;
            for(GoodsClassBean.ContentEntity.GoodsClassEntity temp : goodsClassBean.getContent().getGoodsClass()){
                mTitleList.add(temp.getClassName());
                FreeShippingFragment freeShippingFragment = new FreeShippingFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("gc_id",temp.getId());
                freeShippingFragment.setArguments(bundle);
                mFragmentList.add(freeShippingFragment);
            }
            mAdapter = new shop_tab_adapter(getSupportFragmentManager(), mFragmentList, mTitleList);
            mViewPager.setOffscreenPageLimit(mFragmentList.size());
            mViewPager.setAdapter(mAdapter);
            mTabLayout.setViewPager(mViewPager);
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
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
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

}
