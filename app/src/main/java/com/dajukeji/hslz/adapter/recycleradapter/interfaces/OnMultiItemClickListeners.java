package com.dajukeji.hslz.adapter.recycleradapter.interfaces;


import com.dajukeji.hslz.adapter.recycleradapter.ViewHolder;

/**
 * Created by cdr on 2017/11/28.
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
