package com.dajukeji.hslz.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.IntRange
import android.support.v4.app.Fragment
import android.view.View
import android.view.Window
import android.view.WindowManager

/**
状态栏设置工具类
使用示例：
StatusBarUtils(window).transparentStatusBar() //状态栏背景透明
StatusBarUtils(window).run {
transparentStatusBar() //状态栏背景透明
invisibleStatusBar() //状态栏不排版
}

软键盘 BUG:
与软键盘遮挡冲突: 若设置了 状态栏不占位 + 窗口调整adjustResize 那么弹出软键盘时，页面失去了调整功能，窗口底部会被软键盘遮挡：
简单解决 给根布局指定fitsSystemWindows=true属性,根布局会自动设定paddingTop,页面就根据软键盘可以正常调整了
若根布局是DrawerLayout,那么需要将 fitsSystemWindows=true 设到主内容区,侧滑内容区只能自己设定paddingTop

fitsSystemWindows=true ：19以上时,系统设置view的paddingTop为状态栏高度。
 */
class StatusBarUtils(private val window: Window) {
	constructor(activity: Activity) : this(activity.window)
	constructor(fragment: Fragment) : this(fragment.activity!!.window)
	constructor(dialog: Dialog) : this(dialog.window!!)

	/** 透明状态栏,或指定颜色 */
	fun transparentStatusBar(@ColorInt color: Int = Color.TRANSPARENT) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.statusBarColor = color
		}
	}

	/** 不占位状态栏,[isInitialized]是否不占位 */
	fun invisibleStatusBar(isInvisible: Boolean = true) {

		//不占位 = 全屏 + 稳定
		val invisibleFlag =
			View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

		when {
			isInvisible &&
					Build.VERSION.SDK_INT in Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.M -> {
				window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
			}
			isInvisible &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {

				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility addFlag invisibleFlag
			}

			!isInvisible &&
					Build.VERSION.SDK_INT in Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.M -> {
				window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
			}
			!isInvisible &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility clearFlag invisibleFlag
			}
		}
	}

	/** 黑字状态栏[isBlackText]黑字or白字 */
	fun blackTextStatusBar(isBlackText: Boolean = true) {
		when {
			isBlackText &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				val blackTextFlag =
					WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility addFlag blackTextFlag
			}

			!isBlackText &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				val blackTextFlag =
					WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility clearFlag blackTextFlag
			}
		}
		blackTextStatusBarPrivateApi(isBlackText)
	}

	/** 调用私有API处理颜色 (小米或魅族手机的颜色改变) */
	@SuppressLint("PrivateApi")
	private fun blackTextStatusBarPrivateApi(isBlackText: Boolean) {
		/**
		 * 改变小米的状态栏字体颜色为黑色, 要求MIUI6以上
		 * Tested on: MIUIV7 5.0 Redmi-Note3
		 */
		@Throws(Exception::class)
		fun processMIUI(isBlackText: Boolean) {
			val clazz = window.javaClass
			val darkModeFlag: Int
			val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
			val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
			darkModeFlag = field.getInt(layoutParams)
			val extraFlagField = clazz.getMethod(
				"setExtraFlags",
				Int::class.javaPrimitiveType,
				Int::class.javaPrimitiveType
			)
			extraFlagField.invoke(window, if (isBlackText) darkModeFlag else 0, darkModeFlag)
		}

		/**	改变魅族的状态栏字体为黑色，要求FlyMe4以上 */
		@Throws(Exception::class)
		fun processFlyMe(isBlackText: Boolean) {
			val lp = window.attributes
			val instance = Class.forName("android.view.WindowManager\$LayoutParams")
			val value = instance.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON").getInt(lp)
			val field = instance.getDeclaredField("meizuFlags")
			field.isAccessible = true
			val origin = field.getInt(lp)
			if (isBlackText) {
				field.set(lp, origin or value)
			} else {
				field.set(lp, value.inv() and origin)
			}
		}

		//成功执行则说明手机型号正确,执行失败时尝试下个型号
		if (kotlin.runCatching {
				processFlyMe(isBlackText)
			}.isSuccess) {
		} else if (kotlin.runCatching {
				processMIUI(isBlackText)
			}.isSuccess) {
		}
	}

	/** 透明导航栏 */
	fun transparentNavigationBar(@ColorInt color: Int = Color.TRANSPARENT) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			window.navigationBarColor = color
		}
	}

	/** 不占位导航栏 */
	fun invisibleNavigationBar(isInvisible: Boolean = true) {
		when {
			isInvisible &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			}

			!isInvisible &&
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
				window.decorView.systemUiVisibility =
					window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
			}
		}
	}

	companion object {
		/**
		 * 获取状态栏高度(px)
		 *
		 * mhdpi = dp * 1
		 * hdpi = dp * 1.5
		 * xhdpi = dp * 2
		 * xxhdpi = dp * 3
		 * eg : 1920x1080, xxhdpi, => status/all = 25/640(dp) = 75/1080(px)
		 *
		 * Default status dp = 24 or 25
		 * don't forget toolbar's dp = 48
		 */
		@IntRange(from = 0, to = 75)
		@SuppressLint("ObsoleteSdkInt")
		fun getStatusBarHeightPx(context: Context): Int {
			return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
				0
			} else {
				val appContext = context.applicationContext
				var result = 0
				val resourceId =
					appContext.resources.getIdentifier("status_bar_height", "dimen", "android")
				if (resourceId > 0) {
					result = appContext.resources.getDimensionPixelSize(resourceId)
				}
				result
			}
		}

		/** 底部导航栏高度(px) */
		@IntRange(from = 0, to = 75)
		@SuppressLint("ObsoleteSdkInt")
		fun getNavigationBarHeightPx(context: Context): Int {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
				return 0
			}
			val appContext = context.applicationContext
			var result = 0
			val resourceId =
				appContext.resources.getIdentifier("navigation_bar_height", "dimen", "android")
			if (resourceId > 0) {
				result = appContext.resources.getDimensionPixelSize(resourceId)
			}
			return result
		}
	}

	/**返回添加标记的结果(前提是未添加)*/
	infix fun Int.clearFlag(i: Int): Int {
		return if (this and i != 0)
			this xor i
		else
			this
	}

	/**返回移除标记的结果(前提是已添加)*/
	infix fun Int.addFlag(i: Int): Int {
		return if (this and i == 0)
			this or i
		else
			this
	}
}