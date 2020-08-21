package com.dajukeji.hslz.adapter.recycleradapter.interfaces;


import com.dajukeji.hslz.adapter.recycleradapter.ViewHolder;

/**
 * Created by cdr on 2017/11/28.
 */
public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
