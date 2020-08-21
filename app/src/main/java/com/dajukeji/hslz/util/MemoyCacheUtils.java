package com.dajukeji.hslz.util;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MemoyCacheUtils {

//	private HashMap<String, SoftReference<Bitmap>> mMemorCache = new HashMap<String, SoftReference<Bitmap>>();

	LruCache<String, Bitmap> mMemorCache;
	public MemoyCacheUtils(){
		long maxMemory = Runtime.getRuntime().maxMemory(); //获得APP运行内存大小
		
		mMemorCache  = new LruCache<String, Bitmap>((int)(maxMemory/8)){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				int byteCount = value.getRowBytes() * value.getHeight(); // value.getByteCount() 不兼容低版本
				return byteCount;
			}
		};
	}
	
	public Bitmap getBitmapFromMemory(String url) {
//		SoftReference<Bitmap> softReference = mMemorCache.get(url);
//		if (softReference != null) {
//			Bitmap bitmap = softReference.get();
//			return bitmap;
//		}
		
		Bitmap bitmap = mMemorCache.get(url);
		return bitmap;
	}

	public void setBitmapTomemory(String url, Bitmap bit) {
//		SoftReference<Bitmap> bitmap = new SoftReference<Bitmap>(bit);
//
//		mMemorCache.put(url, bitmap);
		
		mMemorCache.put(url, bit);
	}
}
