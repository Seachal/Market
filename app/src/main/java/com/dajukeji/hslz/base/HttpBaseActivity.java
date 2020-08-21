package com.dajukeji.hslz.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.util.ToastUtils;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/11/28 0028.
 * pullRefresh表示使用swiperefresh功能，列表推荐使用XRecyclerView
 * supportX表示使用XRecyclerView，包含refresh、loadmore等方法
 */

public class HttpBaseActivity extends FragmentActivity implements IView, View.OnClickListener {

    private Context mContext;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        loadLayout(savedInstanceState);
        ButterKnife.bind(this);
        initView();
        initData();
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 加载布局的方法
     * @param savedInstanceState 通过Bundle信息去加载
     */
    protected void loadLayout(Bundle savedInstanceState) {

    }

    /**
     * 在HttpBaseActivity的onCreate中会自动调用本方法
     */
    protected void initView() {
    }

    /**
     * 在HttpBaseActivity的onCreate中会自动调用本方法
     */
    protected void initData() {
    }

    protected boolean refreshNoList() {
        return false;
    }

    public boolean supportX() {
        return false;
    }

    /**
     * 标题栏设置
     *
     * @param titleResId 标题
     * @param isReturn   是否显示返回按钮
     */
    public void setTitleBar(int titleResId, boolean isReturn) {
        if (isReturn) {
            findViewById(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            findViewById(R.id.title_bar_return).setVisibility(View.GONE);
        }

        TextView mTvTitle = (TextView) findViewById(R.id.title_bar_title);
        mTvTitle.setText(getString(titleResId));
    }

    /**
     * @param title 标题
     * @param isReturn 是否显示返回按钮
     */
    public void setTitleBar(String title, boolean isReturn) {
        if (isReturn) {
            findViewById(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            findViewById(R.id.title_bar_return).setVisibility(View.GONE);
        }

        TextView mTvTitle = (TextView) findViewById(R.id.title_bar_title);
        mTvTitle.setText(title);
    }

    /**
     * 标题栏设置
     *
     * @param titleResId      标题的resource id
     * @param isReturn        是否显示返回按钮
     * @param noticeNum       通知条目，-1不显示
     * @param titleBarBgColor 标题栏背景颜色，0就是默认颜色
     */
    public void setTitleBar(int titleResId, boolean isReturn, int noticeNum, int titleBarBgColor) {
        ((TextView) $(R.id.title_bar_title)).setText(getString(titleResId));

        if (isReturn) {
            $(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            $(R.id.title_bar_return).setVisibility(View.GONE);
        }

        if (noticeNum != -1) {
            $(R.id.title_bar_notice).setVisibility(View.VISIBLE);
            $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent messageIntent = new Intent(HttpBaseActivity.this, UserMessageActivity.class);
                    startActivity(messageIntent);
                }
            });
            if (noticeNum == 0) {
                $(R.id.title_bar_notice_num).setVisibility(View.GONE);
            } else {
                ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(noticeNum));
            }
        } else {
            $(R.id.title_bar_notice).setVisibility(View.GONE);
        }

        if (titleBarBgColor != 0) {
            $(R.id.title_bar_bg).setBackgroundColor(getResources().getColor(titleBarBgColor));
        }

    }

    /**
     * 右边带自定义图片的titlebar
     *
     * @param title      标题
     * @param isReturn   点击左边返回
     * @param rightImgId 图片资源id
     */
    public void setTitleBar(String title, boolean isReturn, int rightImgId) {
        if (isReturn) {
            findViewById(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            findViewById(R.id.title_bar_return).setVisibility(View.GONE);
        }

        TextView mTvTitle = (TextView) findViewById(R.id.title_bar_title);
        mTvTitle.setText(title);
        ImageView title_bar_right_iv = (ImageView) findViewById(R.id.title_bar_right_iv);
        title_bar_right_iv.setBackgroundResource(rightImgId);
        RelativeLayout title_bar_right_rl = (RelativeLayout) findViewById(R.id.title_bar_right_rl);
        title_bar_right_rl.setVisibility(View.VISIBLE);
        title_bar_right_rl.setOnClickListener(this);
    }

    /**
     * 右边带自定义文字的titlebar
     *
     * @param title 标题
     * @param isReturn 是否显示返回按钮
     * @param titleRight 标题右边的文字
     */
    public void setTitleBar(String title, boolean isReturn, String titleRight) {
        if (isReturn) {
            findViewById(R.id.title_bar_return).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            findViewById(R.id.title_bar_return).setVisibility(View.GONE);
        }
        TextView mTvTitle = (TextView) findViewById(R.id.title_bar_title);
        mTvTitle.setText(title);
        RelativeLayout title_bar_right_rl_tt = (RelativeLayout) findViewById(R.id.title_bar_right_rl_tt);
        title_bar_right_rl_tt.setVisibility(View.VISIBLE);
        title_bar_right_rl_tt.setOnClickListener(this);
        TextView title_bar_right_tv = (TextView) findViewById(R.id.title_bar_right_tv);
        title_bar_right_tv.setText(titleRight);
    }

    /**
     * 单击右边文字时调用
     */
    protected void clickRightTitle() {

    }

    /**
     * 单击右边图标时调用
     */
    protected void clickRightImg() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_bar_right_rl_tt:
                clickRightTitle();
                break;
            case R.id.title_bar_right_rl:
                clickRightImg();
                break;
        }
    }

    /**
     * 发送一个短时的Toast提示用户
     * @param string
     */
    protected void shortToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 发送一个长时的Toast提示用户
     * @param string
     */
    protected void longToast(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_LONG).show();
    }


    @Override
    public void setResultData(Object object, String contentType) {
        hideLoading();
    }

    @Override
    public void showHttpError(String error, String dataType) {
        if(error!=null&&!error.equals("")){
            showToast(error);//小样，藏得真深，我就说怎么找不到提示的Toast
        }
        hideLoading();
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void showLoading() {
        try {
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == mContext) {
            return;
        }
        if (supportX()) {

        } else if (refreshNoList()) {
            if (null != swipeRefreshLayout) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(true);
                }
            }
        } else {
            showDialogLoading();
        }

    }

    protected void showDialogLoading() {
        LoadingDialog.showLoadingDialog(this, "");
    }

    @Override
    public void hideLoading() {
        if (null == mContext) {
            return;
        }
        if (supportX()) {

        } else if (refreshNoList()) {
            if (null != swipeRefreshLayout) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        } else {
            hideDialogLoading();
        }
    }

    protected void hideDialogLoading() {
        LoadingDialog.hideLoadingDialog();
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 获取布局控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T $(int id) {
        return (T) super.findViewById(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showToast(String string) {
        ToastUtils.getInstance().showToast(getContext(), string);
    }
}
