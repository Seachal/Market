package com.dajukeji.hslz.fragment.catcoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.JDShopFindActivity;
import com.dajukeji.hslz.adapter.shop_tab_adapter;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.coupon.JDCouponType;
import com.dajukeji.hslz.event.ActionBarEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.view.tablayout.SelfAdaptionTabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/3.
 */

public class JDGoodFragment extends HttpBaseFragment {
    private CouponPresenter couponPresenter;
    private SelfAdaptionTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private LinearLayout ll_find_jd_list; // 京券查找
    private List<String> mTitleList = new ArrayList<>();                                  //tab名称列表
    private List<Fragment> mFragmentList = new ArrayList<>();                             //定义要装fragment的列表
    private FragmentPagerAdapter mAdapter;
    private LinearLayout ll_jd_find;
    private RelativeLayout reload_rl;
    private Button reload_button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        couponPresenter = new CouponPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_jd_coupon,null);
        }
        initView(rootView);
        return rootView;
    }

    private void initView(View v){
        mSlidingTabLayout = (SelfAdaptionTabLayout) v.findViewById(R.id.jd_sliding_tabLayout);
        mViewPager = (ViewPager) v.findViewById(R.id.jd_viewPager);
        ll_jd_find = (LinearLayout) v.findViewById(R.id.ll_jd_find);
        reload_rl = (RelativeLayout) v.findViewById(R.id.reload_rl);
        ll_find_jd_list = (LinearLayout) v.findViewById(R.id.ll_find_jd_list);
        reload_button = (Button) v.findViewById(R.id.reload_button);
        reload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getJdTypeList();
            }
        });
        ll_find_jd_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), JDShopFindActivity.class));
            }
        });
    }

    private void getJdTypeList(){
        showDialogLoading();
        couponPresenter.getJDTypeList(getContext(), DataType.coupon.getTypeList.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.coupon.getTypeList.toString())){
            hideDialogLoading();
            JDCouponType  jdCouponType = (JDCouponType) object;
            if(!jdCouponType.getContent().getGoodsType().isEmpty()){
                reload_rl.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                ll_jd_find.setVisibility(View.VISIBLE);
                initViewPager(jdCouponType);
            }else{
                showToast("暂无产品");
            }
        }
    }
    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
        if (dataType.equals(DataType.coupon.getTypeList.toString())) {
            reload_rl.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.GONE);
            ll_jd_find.setVisibility(View.GONE);
        } else {
            showToast(getResources().getString(R.string.no_network_tips));
        }
    }

    private void initViewPager(JDCouponType jdCouponType){
        for(int i = 0 ; i < jdCouponType.getContent().getGoodsType().size(); i++){
            mTitleList.add(jdCouponType.getContent().getGoodsType().get(i).getNAME());
            JDGoodsListFragment jdGoodsListFragment = new JDGoodsListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type",Integer.parseInt(jdCouponType.getContent().getGoodsType().get(i).getCATID_ID()));
            jdGoodsListFragment.setArguments(bundle);
            mFragmentList.add(jdGoodsListFragment);
        }
        mAdapter = new shop_tab_adapter(getActivity().getSupportFragmentManager(), mFragmentList, mTitleList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        getJdTypeList();
        ((MainActivity)getActivity()).currentPage=2;
        EventBus.getDefault().post(new ActionBarEvent("action"));
    }
}
