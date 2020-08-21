package com.dajukeji.hslz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class LocalCacheUtils {

	private static final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/haoshen";

	public Bitmap getBitmapFromLocal(String url) {
		String fileName;
		try {
			fileName = MD5Encoder.encode(url);
			File file = new File(CACHE_PATH, fileName);
			if (file.exists()) {
				Bitmap bit = BitmapFactory.decodeStream(new FileInputStream(file));
				return bit;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void setBitmapToLocal(String url, Bitmap bit) {

		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(CACHE_PATH, fileName);
			File parentFile = file.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdir();
			}

			bit.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
