package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.util.SPUtil;

/**
 * 主界面中的 首页 包括了搜索框 和一个indexFragment
 */
public class DiscovertwoFragment extends Fragment {
//    private FrameLayout frameLayout;
    private LinearLayout linear,linear_two;
//    private IndexFragment indexFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.discoverwo_frag,container,false);//

//        frameLayout = (FrameLayout) view.findViewById(R.id.framelayout);//好像没什么用 王星(3-1)
        linear = (LinearLayout) view.findViewById(R.id.linear);//搜索框的布局
        linear_two = (LinearLayout) view.findViewById(R.id.linear_two);//搜索bar的右边，消息按钮
        //消息按钮的监听事件，如果已经有了登录的token，那就跳转至“消息”页面，否则提示用户使用手机号登录
        linear_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
//                startActivity(messageIntent);
                if(!SPUtil.getPrefString("token","").equals("")){

                        Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
                        startActivity(messageIntent);
                }else {
                    startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                }

            }
        });

        //单击到搜索框的布局就跳转到商品搜索页面 ShopFindActivity
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ShopFindActivity.class));
            }
        });

        //还不知道下面这个布局是干嘛用的 王星（3-1）
        //现在知道这是干嘛的了，首页的fragment是从这里创建，并添加到指定的布局中的（2019-3-19）
        IndexFragment indexFragment= new IndexFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout, indexFragment).commit();
        return view;
    }
}
