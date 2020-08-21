package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.WindowManager;

import com.dajukeji.hslz.R;

/**
 * Created by Administrator on 2017/9/12.
 */

public class SelectPicturePopWindow extends PopupWindow {
    private View conentView;

    public SelectPicturePopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuwindow_list, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth((w / 3)*2 + 50);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        changeTransparent(context);
        // 刷新状态
        this.update();
        this.setOnDismissListener(new PopupWindow.OnDismissListener() { // 在dismiss中恢复透明度
         public void onDismiss() {
             changeNotTransparent(context);
         }
        });

        // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        //this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimationPreview);
        TextView addTaskLayout = (TextView) conentView
                .findViewById(R.id.from_album);
        TextView teamMemberLayout = (TextView) conentView
                .findViewById(R.id.from_camera);
        addTaskLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                //SelectPicturePopWindow.this.dismiss();
            }
        });

        teamMemberLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //SelectPicturePopWindow.this.dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }
    public void showPopupWindowInParentCenter(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER,0,0);
        } else {
            this.dismiss();
        }
    }

    /**
     * 使Activity变半透明
     * @param context
     */
    private void changeTransparent(Activity context){
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = context.getWindow() .getAttributes();
        lp.alpha = 0.4f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 使Activity恢复正常
     * @param context
     */
    private void changeNotTransparent(Activity context){
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = context.getWindow() .getAttributes();
        lp.alpha = 1f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);

    }
}
