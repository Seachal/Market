package com.dajukeji.hslz.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;

import butterknife.ButterKnife;

/**
 * Created by cdr on 2017/11/28.
 *
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLayout(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        loadData();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected abstract void loadLayout(Bundle savedInstanceState);

    protected void initView(){};

    protected void loadData(){};

    /**
     * 标题栏设置
     * @param titleResId 标题
     * @param isReturn 是否下显示返回按钮
     */
    public void setTitleBar(int titleResId, boolean isReturn){
        if(isReturn){
            $(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            $(R.id.title_bar_return).setVisibility(View.GONE);
        }
        ((TextView)$(R.id.title_bar_title)).setText(getString(titleResId));
    }

    public void setTitleBar(String title, boolean isReturn){
        if(isReturn){
            $(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            $(R.id.title_bar_return).setVisibility(View.GONE);
        }
        ((TextView)$(R.id.title_bar_title)).setText(title);
    }

    /**
     * 标题栏设置
     * @param titleResId 标题id
     * @param isReturn 返回功能
     * @param noticeNum 通知条目，-1不显示
     * @param titleBarBgColor 标题栏背景颜色，有默认颜色
     */
    public void setTitleBar(int titleResId, boolean isReturn, int noticeNum, int titleBarBgColor){
        ((TextView) $(R.id.title_bar_title)).setText(getString(titleResId));

        if(isReturn){
            $(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            $(R.id.title_bar_return).setVisibility(View.GONE);
        }

        if(noticeNum != -1){
            $(R.id.title_bar_notice).setVisibility(View.VISIBLE);
            $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent messageIntent=new Intent(BaseActivity.this, UserMessageActivity.class);
                    startActivity(messageIntent);
                }
            });
            if(noticeNum == 0){
                $(R.id.title_bar_notice_num).setVisibility(View.GONE);
            }else{
                ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(noticeNum));
            }
        }else{
            $(R.id.title_bar_notice).setVisibility(View.GONE);
        }

        if(titleBarBgColor != 0){
            $(R.id.title_bar_bg).setBackgroundColor(getResources().getColor(titleBarBgColor));
        }
    }

    /**
     * 获取布局控件
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id)
    {
        return (T) super.findViewById(id);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
