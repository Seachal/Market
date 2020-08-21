package com.dajukeji.hslz.util;



import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitnapUtils {

	NetCacheUtitls mNetCacheUtitls; //网络缓存
	LocalCacheUtils mLocalCacheUtils; // 本地缓存
	MemoyCacheUtils mMemoyCacheUtils; // 内存缓存

	public MyBitnapUtils() {
		mMemoyCacheUtils = new MemoyCacheUtils();
		mLocalCacheUtils = new LocalCacheUtils();
		mNetCacheUtitls = new NetCacheUtitls(mLocalCacheUtils ,mMemoyCacheUtils);
	}

	public void display(ImageView ivPic, String url) {


		Bitmap bitmap = null;
		
		
		bitmap = mMemoyCacheUtils.getBitmapFromMemory(url);
		
		if(bitmap != null){
			ivPic.setImageBitmap(bitmap);
			return;
		}
		
		bitmap=mLocalCacheUtils.getBitmapFromLocal(url);

		if (bitmap != null) {
			ivPic.setImageBitmap(bitmap);
			return;
		}

		// 从网络读取图片
		mNetCacheUtitls.GetBitmapFromNet(ivPic, url);
	}

}
