package com.dajukeji.hslz.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.dajukeji.hslz.R;


/**
 * Created by cdr on 2017/11/28.
 */

public abstract class LazyFragment extends Fragment {
    protected boolean isVisible;

    public LazyFragment() {

    }

    /**
     * 在这里实现Fragment数据的缓加载.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}

    /**
     * 初始化标题
     */
    public void setTitle(int titleResId, View view){
        view.findViewById(R.id.title_bar_return).setVisibility(View.GONE);
        TextView mTvTitle = (TextView) view.findViewById(R.id.title_bar_title);
        mTvTitle.setText(getString(titleResId));
    }

    /**
     * 初始化
     */
    protected abstract void initView();

    protected void loadData(){};
}
