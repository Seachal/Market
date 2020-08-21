package com.dajukeji.hslz.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/9/18.
 */

public class ImageTask extends AsyncTask<String, Object,BitmapDrawable> {
    private static LruCache<String, BitmapDrawable> mImageCache;//图片缓存
    static {
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache / 8;
        mImageCache = new LruCache<String, BitmapDrawable>(cacheSize) {
        @Override
        protected int sizeOf(String key, BitmapDrawable value) {
            return value.getBitmap().getByteCount();
        }
    };
    }
    private String imageUrl;
    private Resources resources;
    private ListView listView;

    public ImageTask(Resources resources,ListView listView) {
        this.resources = resources;
        this.listView = listView;
    }

    @Override
    protected void onProgressUpdate(Object[] values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected BitmapDrawable doInBackground(String... params) {
        imageUrl = params[0];
        Bitmap bitmap = downloadImage();
        BitmapDrawable db = new BitmapDrawable(resources,
                bitmap);
        // 如果本地还没缓存该图片，就缓存
        if (mImageCache.get(imageUrl) == null) {
            mImageCache.put(imageUrl, db);
        }
        return db;
    }

    @Override
    protected void onPostExecute(BitmapDrawable result) {
        // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
        ImageView iv = (ImageView) listView.findViewWithTag(imageUrl);
        if (iv != null && result != null) {
            iv.setImageDrawable(result);
        }
    }

    /**
     * 根据url从网络上下载图片
     *
     * @return
     */
    private Bitmap downloadImage() {
        HttpURLConnection con = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5 * 1000);
            con.setReadTimeout(10 * 1000);
            bitmap = BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }

        return bitmap;
    }
}
