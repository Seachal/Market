package com.dajukeji.hslz.adapter.recycleradapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dajukeji.hslz.R;

/**
 * Created by cdr on 2017/12/2.
 * 设置表格布局Item间距
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int mVerticalSpacing, mHorizontalSpacing;

    public SpacesItemDecoration(int verticalSpacing, int horizontalSpacing){
        mHorizontalSpacing = horizontalSpacing;
        mVerticalSpacing = verticalSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = mHorizontalSpacing;
        outRect.right = mHorizontalSpacing;
        outRect.bottom = mVerticalSpacing;
        outRect.top = mVerticalSpacing;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}
