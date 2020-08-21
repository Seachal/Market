package com.dajukeji.hslz.activity

import android.os.Bundle
import com.dajukeji.hslz.R
import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean
import com.dajukeji.hslz.network.presenter.SetupPasswordPresenter
import com.dajukeji.hslz.util.FormatCheckUtils
import com.dajukeji.hslz.util.SPUtil
import com.dajukeji.hslz.util.ToastUtils
import kotlinx.android.synthetic.main.activity_setup_login_pwd.*

class SetupLoginPwdActivity : LkBaseActivity() {

	private val presenter = SetupPasswordPresenter(this)
	override fun layoutId() = R.layout.activity_setup_login_pwd

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setTitleBar("设置登录密码", true)

		//保存密码
		btn_save.setOnClickListener {
			val password = edit_password.text.toString()
			//格式检测
			val errorMsg = FormatCheckUtils.checkPassword(
				password, edit_confirmPassword.text.toString()
			)
			//报错 or 请求重设
			if (errorMsg != null) {
				ToastUtils.getInstance().showToast(this, errorMsg)
			} else {
				val phone = SPUtil.getPrefString("phoneNumber", "")
				presenter.setupLoginPasswrod(this, phone, password, "重设登录密码")
			}
		}
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		when (contentType) {
			"重设登录密码" -> {
				val bean = `object` as BaseStatusMessageBean
				ToastUtils.getInstance().showToast(this, bean.message)
				//修改成功
				if (bean.status == "0") {
					setResult(RESULT_OK)
					finish()
				}
			}
			else -> {
			}
		}
	}
}
