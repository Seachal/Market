package com.dajukeji.hslz.util.loader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/12/6.
 */

public interface ImageEngine {

    /**
     * 加载缩略图
     * @param context
     * @param resize
     * @param placeHolder
     * @param imageView
     * @param url
     */
    void loadThumbnail(Context context, int resize, Drawable placeHolder, ImageView imageView, String url);
}
