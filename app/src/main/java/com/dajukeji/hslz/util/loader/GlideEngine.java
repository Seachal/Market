package com.dajukeji.hslz.util.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Administrator on 2017/12/6.
 */

public class GlideEngine {

    public static void loadThumbnail(Context context, int resize, int placeHolder, ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .override(resize, resize)
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    public static void loadThumbnail(Context context,int width , int height, int placeHolder, ImageView imageView, int drawable) {

        RequestOptions options = new RequestOptions()
                .override(width, height)
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(drawable)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    public static void loadThumbnail(Context context,int placeHolder, ImageView imageView, int drawable) {

        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(drawable)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    public static void loadThumbnail(Context context, int width,int height, int placeHolder, ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .override(width, height)
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    public static void loadImage(Context context, int placeHolder, ImageView imageView, String url){
        RequestOptions options = new RequestOptions()
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .apply(options)
                .into(imageView);
    }

    public static void loadThumbnails(Context context,int width , int height, int placeHolder, ImageView imageView, int drawable) {

        RequestOptions options = new RequestOptions()
                .override(width, height)
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(drawable)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }

    public static void loadThumbnails(Context context,int width , int height, int placeHolder, ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .override(width, height)
                .placeholder(placeHolder)
                .priority(Priority.HIGH);

        Glide.with(context)
                .load(url)
                .thumbnail(0.1f)
                .apply(options)
                .into(imageView);
    }
}
