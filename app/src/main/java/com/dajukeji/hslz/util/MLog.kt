package com.dajukeji.hslz.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.dajukeji.hslz.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


/**
 * 作者: Li_ke
 * 日期: 2018/10/25 0025 17:44
 * 作用:
 */
object MLog {
	private val IS_LOG get() = BuildConfig.DEBUG
	private const val IS_SAVE_TO_FILE = true//独立于IS_LOG

	/**log默认TAG*/
	private const val DEFAULT_TAG = "Li_ke"
	/**log文件存储根目录*/
	private var saveRootPath: String? = null
	/**log文件标识(默认)*/
	private const val logFileTag: String = "MLog"

	fun init(context: Context) {
		saveRootPath = context.filesDir.path

		if (PackageManager.PERMISSION_GRANTED == context.packageManager.checkPermission(
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				context.packageName
			)
		)
//			saveRootPath = Environment.getExternalStorageDirectory().path
			saveRootPath = context.getExternalFilesDir(null)?.path //外部存储
		else
			w("没有写入权限,无法通过文件助手查看日志")
	}

	//======================外部使用======================

	fun v(log: Any?) = log(Log.VERBOSE, DEFAULT_TAG, log, now(1))
	fun v(tag: String, log: Any?) {
		log(Log.VERBOSE, tag, log, now(1))
	}

	fun d(log: Any?) = log(Log.DEBUG, DEFAULT_TAG, log, now(1))
	fun d(tag: String, log: Any?) {
		log(Log.DEBUG, tag, log, now(1))
	}

	fun i(log: Any?) = log(Log.INFO, DEFAULT_TAG, log, now(1))
	fun i(tag: String, log: Any?) {
		log(Log.INFO, tag, log, now(1))
	}

	fun w(log: Any?) = log(Log.WARN, DEFAULT_TAG, log, now(1))
	fun w(tag: String, log: Any?) {
		log(Log.WARN, tag, log, now(1))
	}

	fun e(log: Any?) = log(Log.ERROR, DEFAULT_TAG, log, now(1))
	fun e(tag: String, log: Any?) {
		log(Log.ERROR, tag, log, now(1))
	}

	/**直接存入指定的log文件,不打印*/
	fun saveFileSpecial(logFileTag: String, log: String) {
		if (IS_SAVE_TO_FILE)
			saveToFile(logFileTag, "", "", log)
	}

	/**不可能的BUG*/
	fun wtf(tag: String, msg: String) = Log.wtf(tag, msg)

	//======================功能函数======================

	/**
	 * 真正的 log 实现函数
	 * @param priority Log等级,例如[Log.INFO]
	 * @param log 自动根据数据类型打印内容
	 * @param now 可点击跳转log调用处
	 */
	private fun log(priority: Int, tag: String, log: Any?, now: String? = null) {
		// log:Any -> log:String
		var msg = log.toString()
		if (now != null)
			msg = "$now-> $msg"

		//打印Log
		if (IS_LOG)
			Log.println(priority, tag, msg)

		//保存log
		if (IS_SAVE_TO_FILE) {
			val logLevel = when (priority) {
				Log.VERBOSE -> "v"
				Log.DEBUG -> "d"
				Log.INFO -> "i"
				Log.WARN -> "w"
				Log.ERROR -> "e"
				Log.ASSERT -> "a"
				else -> ""
			}
			try {
				saveToFile(logFileTag, tag, logLevel, msg)
			} catch (e: RuntimeException) {
				Log.e(tag, "日志保存失败-" + e.message)
			}
		}
	}

	/**保存到文件
	 * @param logFileTag log文件名标志,用于区分特殊log文件
	 * @param logLevel 此次log级别
	 */
	private fun saveToFile(logFileTag: String, logTag: String, logLevel: String, log: String) {
		check(IS_SAVE_TO_FILE)
		saveRootPath = saveRootPath ?: error("必须先初始化MLog")

		thread {
			//生成log文件位置
			val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(Date())//按天为单位
			val savePath =
				saveRootPath + File.separator + "log" + File.separator + logFileTag + "_" + dateStr + ".txt"

			//创建log上级文件夹
			File(savePath).parentFile.run {
				if (!this.exists())
					this.mkdirs()
			}

			//创建log文件
			File(savePath).run {
				if (!this.exists())
					this.createNewFile()
			}

			//此次log内容
			val logMessage: String = kotlin.run {
				val time = SimpleDateFormat("HH-mm-ss", Locale.CHINA).format(Date())
				"${time}_${logTag}_${logLevel}：$log\n"
			}

			//写入文件
			FileOutputStream(savePath, true).use {
				it.write(logMessage.toByteArray(), 0, logMessage.toByteArray().size)
			}
		}
	}

	//======================日期区点击======================

	/** 可在日志区点击的当前调用者方法
	 * @param level : 当要在新的位置使用此方法时,需传入调用层级来确定在log区点击后跳转到哪一层
	 * 比如对于代码： log(){ now() }
	 * level = 0 时，在log区点击会跳转{ now(0) }代码行，而当level = 1 时，点击会跳转到调用log()的位置
	 * 警告，不能使用kotlin默认参数,会导致kotlin生成默认代码导致代码调用层数改变
	 */
	private fun now(level: Int): String {
		//3 = 此行代码处, +1 = 方法now调用处, +level = 多加几层到想跳的调用处
		return traceInvoke(3 + 1 + level, 3 + 1 + level).first().toSimpleString()
	}

	/** 追踪调用者
	 * @param from : 追踪长度(单位:方法数)
	 * @param to   : 追踪长度(单位:方法数)
	 *
	 * trace层级参数(在更改调用层级后需更改此注释):
	 * 3 = 此方法被调用处；比如调用traceInvoke(3,3)，则在log区点击会跳转{ traceInvoke(3,3) }这一行
	 */
	private fun traceInvoke(from: Int = 3, to: Int = 6): MutableList<StackTraceElement> {
		val list = mutableListOf<StackTraceElement>()
		Thread.currentThread().stackTrace.forEachIndexed { index, stackTraceElement ->
			if (index in from..to)
				list.add(stackTraceElement)
		}
		return list
	}

	/** 转为长度短的,可在日志区点击的字符串 */
	private fun StackTraceElement.toSimpleString(): String {
		val simpleClassName = className.split(".").last()
		return if (lineNumber >= 0)
			"$simpleClassName.$methodName($fileName:$lineNumber)"
		else
			"$simpleClassName.$methodName($fileName)"
	}
}