package com.dajukeji.hslz.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dajukeji.hslz.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cdr on 2017/11/29.
 * 去应用市场吐槽 or 赞
 */

public class ComplaintAppraiseDialog {

    private Context context;
    private Dialog dialog;
    private Display display;

    public ComplaintAppraiseDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public ComplaintAppraiseDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_complaint_appraise, null);
        ButterKnife.bind(this, view);
        view.setMinimumWidth(display.getWidth());

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.CommonDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(display.getWidth());
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    @OnClick({R.id.btn_to_appraise, R.id.btn_to_complaint, R.id.btn_to_close})
    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.btn_to_appraise:
                onBackListener.toAppraise();
                dimiss();
                break;

            case R.id.btn_to_complaint:
                onBackListener.toComplaint();
                dimiss();
                break;

            case R.id.btn_to_close:
                dimiss();
                break;
        }
    }

    private onBackListener onBackListener;

    public interface onBackListener{
        void toAppraise();
        void toComplaint();
    }

    public void setOnBackListener(onBackListener onBackListener){
        this.onBackListener = onBackListener;
    }

    public void show() {
        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dimiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }

}
