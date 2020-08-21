package com.dajukeji.hslz.util;

import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.widget.ImageView;

public class NetCacheUtitls {

	LocalCacheUtils mlocalCacheUtils;
	MemoyCacheUtils mMemoyCacheUtils;

	public NetCacheUtitls(LocalCacheUtils mLocalCacheUtils, MemoyCacheUtils mMemoyCacheUtils) {
		this.mlocalCacheUtils = mLocalCacheUtils;
		this.mMemoyCacheUtils = mMemoyCacheUtils;
	}

	public void GetBitmapFromNet(ImageView ivPic, String url) {
		MyAsyncTask tast = new MyAsyncTask();
		tast.execute(ivPic, url);
	}

	class MyAsyncTask extends AsyncTask<Object, Void, Bitmap> {

		private ImageView ivPic;
		private String url;

		@Override
		protected Bitmap doInBackground(Object... params) {
			ivPic = (ImageView) params[0];
			url = (String) params[1];

			ivPic.setTag(url); // 把图片和URL绑定

			return GetBitNET(url);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				String bingUrl = (String) ivPic.getTag();
				if (bingUrl.equals(url)) { // 确保设置正确
					ivPic.setImageBitmap(result);
					mlocalCacheUtils.setBitmapToLocal(bingUrl, result); //把从网络获取到的图片保存到本地
					mMemoyCacheUtils.setBitmapTomemory(bingUrl, result); //把从网络获取到的图片保存到缓存

				}
			}
		}

	}

	public Bitmap GetBitNET(String url) {
		HttpURLConnection mConnection = null;
		try {
			URL mUrl = new URL(url);
			mConnection = (HttpURLConnection) mUrl.openConnection();
			mConnection.setConnectTimeout(5000);
			mConnection.setReadTimeout(3000);
			mConnection.setRequestMethod("GET");
			mConnection.connect();

			int responseCode = mConnection.getResponseCode();

			if (responseCode == 200) {
				Options options = new Options();
				
				options.inSampleSize = 2; // 宽高都为原来的二分之一
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				
				Bitmap bitmap = BitmapFactory.decodeStream(mConnection.getInputStream() ,null ,options);

				return bitmap;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mConnection.disconnect();
		}
		return null;
	}
}
