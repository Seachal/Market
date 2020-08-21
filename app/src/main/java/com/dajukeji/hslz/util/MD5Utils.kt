package com.dajukeji.hslz.util

import java.security.MessageDigest

/**
 * 作者: Li_ke
 * 日期: 2019/1/30 15:00
 * 作用:
 */
object MD5Utils {
	fun dataToMD5(str: String): String {
		val digest = MessageDigest.getInstance("MD5")
		val bytes = digest.digest(str.toByteArray())
		//转成16进制字符串
		val stringBuffer = StringBuffer()
		for (b in bytes) {
			//转16进制
			var s = Integer.toHexString(b.toInt() and 0xFF)
			if (s.length == 1) s = "0$s"
			stringBuffer.append(s)
		}
		return stringBuffer.toString()
	}
}