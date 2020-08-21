package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.GoodsClassBean;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.JumpIndexEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2017/12/18 0018.
 * 暂时未启用
 */

public class DiscoverFragment extends HttpBaseFragment {
    private ViewPager discoverfragment_page;
    private GoodPresenter goodPresenter;
    private MessagePresenter messagePresenter;
    private TabLayout tab_free_title;
    private List<Fragment> fragmentList;
    private LinearLayout content_layout;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private List<String> list_title;
    private RelativeLayout title_bar_notice;
    private TextView title_bar_notice_num;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodPresenter = new GoodPresenter(this);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""), DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_discover, null);
        }
        initData();
        initView(rootView);
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JumpIndexEvent jumpIndexEvent) {
        if (jumpIndexEvent.jumpTop.equals("jumpIndex")) {
            tab_free_title.scrollTo(0, 0);
            tab_free_title.getTabAt(0).select();
        }
    }

//    @Override
//    protected void onFragmentVisibleChange(boolean isVisible) {
//        if (isVisible) {
//            initData();
//        }
//    }

    private void initView(View v) {
        title_bar_notice = (RelativeLayout) v.findViewById(R.id.title_bar_notice);
        title_bar_notice_num = (TextView) v.findViewById(R.id.title_bar_notice_num);
        discoverfragment_page = (ViewPager) v.findViewById(R.id.discoverfragment_page);
        tab_free_title = (TabLayout) v.findViewById(R.id.tab_free_title);
        tab_free_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        title_bar_notice.setOnClickListener(this);
        reload_rl = (RelativeLayout) v.findViewById(R.id.reload_rl);
        reload_button = (Button) v.findViewById(R.id.reload_button);
        reload_button.setOnClickListener(this);
    }

    private void initData() {
        goodPresenter.getGoodClassList(getContext(), DataType.good.GetGoodsClassList.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        reload_rl.setVisibility(View.GONE);
        if (dataType.equals(DataType.good.GetGoodsClassList.toString())) {
            GoodsClassBean goodsClassBean = (GoodsClassBean) object;
            IndexFragment indexFragment = new IndexFragment();
            fragmentList = new ArrayList<>();
            fragmentList.add(indexFragment);
            list_title = new ArrayList<>();
            list_title.add(getActivity().getResources().getString(R.string.index_page));
            for (GoodsClassBean.ContentEntity.GoodsClassEntity goodsClassstring : goodsClassBean.getContent().getGoodsClass()) {
                list_title.add(goodsClassstring.getClassName());
                Bundle bundle = new Bundle();
                bundle.putInt("gc_id", goodsClassstring.getId());
                GoodListFragment itemFragment = new GoodListFragment();
                itemFragment.setArguments(bundle);
                fragmentList.add(itemFragment);
            }
            discoverfragment_page.setOffscreenPageLimit(goodsClassBean.getContent().getGoodsClass().size());
            discoverfragment_page.setAdapter(new PagerAdapter(getChildFragmentManager(), fragmentList, list_title));
            tab_free_title.setupWithViewPager(discoverfragment_page);
        }else if(dataType.equals(DataType.message.notWriteNo.toString())){
            MessageCountBean countBean = (MessageCountBean) object;
            if(countBean.getContent().getNumber()!=0){
                title_bar_notice_num.setVisibility(View.VISIBLE);
                title_bar_notice_num.setText(String.valueOf(countBean.getContent().getNumber()));
            }else {
                title_bar_notice_num.setVisibility(View.GONE);
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if(event.message.equals("message")){
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        }else if(event.message.equals("user_out")){
            title_bar_notice_num.setVisibility(View.GONE);
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;
        List<String> titleList;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.title_bar_notice:
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                }else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;
            case R.id.reload_button:
                initData();
                reload_rl.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showHttpError(String error, String contenttype) {
        super.showHttpError(error, contenttype);
        if (contenttype.equals(DataType.good.GetGoodsClassList.toString()))
        reload_rl.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadingDialog.hideLoadingDialog();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
