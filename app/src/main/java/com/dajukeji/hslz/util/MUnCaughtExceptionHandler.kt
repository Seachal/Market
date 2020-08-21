package com.dajukeji.hslz.util

import android.content.pm.PackageManager
import android.os.Build
import com.dajukeji.hslz.global.AliSdkApplication


/**
 * 作者: Li_ke
 * 日期: 2019/2/26 14:45
 * 作用:
 */
object MUnCaughtExceptionHandler : Thread.UncaughtExceptionHandler {
	private val context get() = AliSdkApplication.getContext()
	private lateinit var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler

	fun init() {
		defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
		Thread.setDefaultUncaughtExceptionHandler(this)
	}

	override fun uncaughtException(t: Thread?, e: Throwable?) {

		try {
			//1、拿到应用的版本号和版本名
			val packageInfo = context.packageManager.getPackageInfo(
				context.packageName,
				PackageManager.GET_ACTIVITIES//表示取的是activity信息
			)
			val versionName = packageInfo.versionName//版本名
			val versionCode = packageInfo.versionCode//版本号

			val deviceId = Build.DEVICE//设备的编号
			val apiVersion = Build.VERSION.SDK_INT//手机的api版本

			val deviceManufacturer = Build.MANUFACTURER//手机厂商
			val deviceBrand = Build.BRAND//手机品牌
			val deviceModel = Build.MODEL//手机型号  小米  三星  …
			val deviceCpuAbi = Build.CPU_ABI//处理器信息

			//2、错误信息
			val errorName = e?.message
			val errorDetails = e?.stackTrace?.map { it.toString() }


			//线程
			val thread = "${t?.id}-${t?.name}"

			//保存日志
			val log = mapOf(
				Pair("thread", thread),
				Pair("errorName", errorName),
				Pair("errorDetails", errorDetails),
				Pair("versionName", versionName),
				Pair("versionCode", versionCode),
				Pair("apiVersion", apiVersion),
				Pair("deviceManufacturer", deviceManufacturer),
				Pair("deviceBrand", deviceBrand),
				Pair("deviceModel", deviceModel)
			).toString()
			MLog.e("全局异常捕捉", log)
			MLog.saveFileSpecial("全局异常捕获", log)

			//已成功捕获bug，若不做处理页面会卡死，返回按钮无效
			defaultUncaughtExceptionHandler.uncaughtException(t, e)
		} catch (e: Exception) {
			defaultUncaughtExceptionHandler.uncaughtException(t, e)
		}
	}
}