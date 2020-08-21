package com.dajukeji.hslz.util

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 17:52
 * 作用: 检测文本格式是否正确， 当返回 null 时意味着格式正确，否则返回错误原因
 */
object FormatCheckUtils {

	fun checkPhone(str: String?): String? {
		return when {
			str?.isNotEmpty() != true -> "手机号不能为空"
			!PhoneFormatCheckUtils.isPhoneLegal(str) -> "手机号格式不对"
			else -> null
		}
	}

	/**检测验证码*/
	fun checkMsgCode(str: String?): String? {
		return when {
			str?.isNotEmpty() != true -> "验证码不能为空"
			else -> null
		}
	}

	/**检测密码*/
	fun checkPassword(password: String?, confirmPwd: String?): String? {
		return when {
			password?.isNotEmpty() != true -> "密码不能为空"
			confirmPwd?.isNotEmpty() != true -> "确认密码不能为空"
			password.length < 6 -> "密码太短了,请输入6-16位字母或数字"
			password.length > 16 -> "密码太长了,请输入6-16位字母或数字"
			password != confirmPwd -> "两次密码输入不一致"
			else -> null
		}
	}
}