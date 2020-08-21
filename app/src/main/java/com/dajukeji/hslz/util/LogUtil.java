package com.dajukeji.hslz.util;

import com.dajukeji.hslz.domain.Constants;


public class LogUtil {
	public static final String TAG = LogUtil.class.getSimpleName();

	public LogUtil() {}

//	public static final void analytics(String log) {
//		if (DEBUG)
//			MLog.INSTANCE.d(LOG_TAG, log);
//	}

	public static final void info(String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.i(TAG, log);
		}

	}

	public static final void info(String TAG, String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.i(TAG, log);
		}

	}

	public static final void verbose(String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.v(TAG, log);
		}

	}

	public static final void verbose(String TAG, String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.v(TAG, log);
		}

	}

	public static final void debug(String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.d(TAG, log);
		}

	}

	public static final void debug(String TAG, String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.d(TAG, log);
		}

	}

	public static final void warn(String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.w(TAG, log);
		}

	}

	public static final void warn(String TAG, String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.w(TAG, log);
		}

	}

	public static final void error(String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.e(TAG, log);
		}

	}

	public static final void error(String TAG, String log){

		if(Constants.DEBUG){
			MLog.INSTANCE.e(TAG, log);
		}

	}

}
