package com.dajukeji.hslz.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.util.ToastUtils;
import com.dajukeji.hslz.view.dialog.LoadingDialog;

/**
 * Created by Administrator on 2017/11/29 0029.
 * 子类中swipeRefreshLayout名字要一致
 */

public class HttpBaseFragment extends Fragment implements IView, View.OnClickListener {

    private SwipeRefreshLayout swipeRefreshLayout;
//    protected Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            HttpBaseFragment.this.handleMessage(msg);
//        }
//    };

    public HttpBaseFragment() {
        super();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

//    protected void handleMessage(Message msg) {
//        switch (msg.what) {
//            case Constants.RESPONSE:
//                String contentType = msg.getData().getString("contentType");
//                setResultData(msg.obj, contentType);
//                break;
//            case Constants.NORESPONSE:
//                showHttpError();
//                break;
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setResultData(Object object, String dataType) {
        hideLoading();
    }

    @Override
    public void showHttpError(String error, String dataType) {
        if(error!=null&&error.equals("")){
            showToast("获取数据失败");
        }else{
            showToast(error);
        }
        hideLoading();
    }

    @Override
    public void showLoading() {
        try {
            if (support_swipe_refresh())
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == getContext()) {
            return;
        }
        if (supportX()) {

        } else if (null != swipeRefreshLayout) {
            if (! swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        }else {
            showDialogLoading();
        }

    }

    public boolean supportX() {
        return false;
    }

    protected boolean support_swipe_refresh() {
        return false;
    }

    protected void showDialogLoading() {
        LoadingDialog.showLoadingDialog(getContext(), "");
    }
    @Override
    public void hideLoading() {

        if (null == getContext()) {
            return;
        }
        if (supportX()) {

        } else if (support_swipe_refresh()) {
            if (swipeRefreshLayout != null) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }else {
            hideDialogLoading();
        }
    }

    protected void hideDialogLoading() {
        LoadingDialog.hideLoadingDialog();
    }

    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;

    /**
     * onCreateView()里返回的view，修饰为protected,所以子类继承该类时，在onCreateView里必须对该变量进行初始化
     */
    protected View rootView;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    protected void showToast(String string) {
        ToastUtils.getInstance().showToast(getContext(), string);
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            if (!inited) {
                lazyLoad();
            }
            inited = true;
        }
    }

    private boolean inited = false;
    protected void lazyLoad() {

    }
}

