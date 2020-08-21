package com.dajukeji.hslz.util;

import android.view.View;

/**
 * Created by ${wangjiasheng} on 2017/12/6 0006.
 */

public class MeasureWeightHeight {

    /**
     * 获取控件高度
     *
     * @param view
     * @return
     */
    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    public static int getStatusBarHeight() {

        return 0;
    }
}
