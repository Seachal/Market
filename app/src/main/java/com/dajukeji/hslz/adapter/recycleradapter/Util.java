package com.dajukeji.hslz.adapter.recycleradapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by cdr on 2017/11/28.
 */
public class Util {

    /**
     * StaggeredGridLayoutManager时，查找position最小的列
     *
     * @param firstVisiblePositions
     * @return
     */
    public static int findMin(int[] firstVisiblePositions) {
        int min = firstVisiblePositions[0];
        for (int value : firstVisiblePositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    /**
     * StaggeredGridLayoutManager时，查找position最大的列
     *
     * @param lastVisiblePositions
     * @return
     */
    public static int findMax(int[] lastVisiblePositions) {
        int max = lastVisiblePositions[0];
        for (int value : lastVisiblePositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static View inflate(Context context, int layoutId) {
        if (layoutId <= 0) {
            return null;
        }
        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    public static View inflate(Context context, int layoutId, ViewGroup root, boolean attachToRoot){
        if (layoutId <= 0) {
            return null;
        }
        return LayoutInflater.from(context).inflate(layoutId, root, attachToRoot);
    }

}
