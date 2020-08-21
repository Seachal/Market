package com.dajukeji.hslz.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.shop_tab_adapter;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.fragment.mine.MyAwardGoodsFragment;
import com.dajukeji.hslz.fragment.mine.MyCouponFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.AwardGoodsPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;
import com.dajukeji.hslz.view.tablayout.SlidingTabLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyCouponGoodsActivity extends HttpBaseActivity {
    private AwardGoodsPresenter awardGoodsPresenter;
    private MessagePresenter messagePresenter;
    private SlidingTabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> mTitleList = new ArrayList<>();                                  //tab名称列表
    private List<Fragment> mFragmentList = new ArrayList<>();                             //定义要装fragment的列表
    private FragmentPagerAdapter mAdapter;
    private MyAwardGoodsFragment myAwardGoodsFragment = new MyAwardGoodsFragment();

    public long id; // 当前选择的产品ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        awardGoodsPresenter = new AwardGoodsPresenter(this);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_my_coupon_goods);
        setTitleBar(R.string.my_coupon,true,0,0);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.tab_coupon_title);
        mViewPager = (ViewPager) findViewById(R.id.vp_coupon_pager);

        initViewPager();
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.award.createAwardOrder.toString())){
            hideDialogLoading();
            showToast("兑换成功");
            myAwardGoodsFragment.recyclerAdapter.clear();
            myAwardGoodsFragment.loadList(1);
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }else if(contentType.equals(DataType.award.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this,userAddressBean.getContent(),id,"","award");
            shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long id, String sku, long addressId,String type) {
                    awardGoodsPresenter.createAwardOrder(getContext(),id,addressId,SPUtil.getPrefString("token",""),DataType.award.createAwardOrder.toString());
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectAddressEvent event) {
        UserAddressBean.ContentBean useraddressbean = event.userAddressBean;
        ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this,useraddressbean,this.id,"","award");
        shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
        shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
        shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
            @Override
            public void createFree(long id, String sku, long addressId,String type) {
                if(type.equals("award")){
                    awardGoodsPresenter.createAwardOrder(getContext(),id,addressId, SPUtil.getPrefString("token",""), DataType.award.createAwardOrder.toString());
                }
            }

        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        awardGoodsPresenter.getDefaultAddress(getContext(),SPUtil.getPrefString("token",""),DataType.award.getDefaultAddress.toString());
    }

    private void initViewPager(){
        //动态设置TabLayout布局属性
        mTabLayout.setTabSpaceEqual(true);
        mTitleList.add("省券详情");
        mTitleList.add("领奖中心");
        mFragmentList.add(new MyCouponFragment());
        mFragmentList.add(myAwardGoodsFragment);
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
