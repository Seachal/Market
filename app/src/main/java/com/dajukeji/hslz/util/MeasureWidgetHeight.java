package com.dajukeji.hslz.util;

import android.content.Context;
import android.view.View;

/**
 * Created by ${wangjiasheng} on 2017/12/7 0007.
 */

public class MeasureWidgetHeight {

    /**
     * 获取控件高度
     *
     * @param view
     * @return
     */
    public int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }


    /**
     * 获取状态栏高度
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getTopDis(View view) {
        int[] position = new int[2];
        view.getLocationOnScreen(position);
        int screenx = position[0];
        int screeny = position[1];
        return screeny;
    }
}
