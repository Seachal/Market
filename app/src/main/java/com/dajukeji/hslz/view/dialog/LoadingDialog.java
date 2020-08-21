package com.dajukeji.hslz.view.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.widget.LinearLayout;

import com.dajukeji.hslz.R;


/**
 * Created by admin on 2016/6/20.
 */
public class LoadingDialog extends Dialog {

    private String mTitle;
    private static Context mContext;
    private static LoadingDialog mLoadingDialog;

    public LoadingDialog(Context context, String title) {
        super(context, R.style.loadingDialogStyle);
        mTitle = title;
        mContext =context;
    }

    public static LoadingDialog getInstance(Context context, String title){
        if(mLoadingDialog == null && context != null){
            mLoadingDialog = new LoadingDialog(context, title);
        }
        return mLoadingDialog;
    }

    public static void showLoadingDialog(Context context, String title){
        if(context == null && mContext != null){
            return;
        }

        if(mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(context, title);
            mLoadingDialog.show();
        }else{
            if(!mLoadingDialog.isShowing()){
                mLoadingDialog.show();
            }
        }
    }

    public static void hideLoadingDialog(){
        if(mContext != null && mLoadingDialog != null && mLoadingDialog.isShowing()){
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(0);

        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                hideLoadingDialog();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
