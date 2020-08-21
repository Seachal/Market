package com.dajukeji.hslz.activity

import android.content.Intent
import android.os.Bundle
import com.dajukeji.hslz.R
import com.dajukeji.hslz.fragment.SendMsgCodeFragment
import kotlinx.android.synthetic.main.activity_check_user.*

/**
 * 忘记登录密码
 */
class ForgetPwdCheckActivity : LkBaseActivity() {

	private val sendMsgCodeFragment
		get() = supportFragmentManager.findFragmentById(R.id.fragment_sendMsgCode) as SendMsgCodeFragment

	override fun layoutId() = R.layout.activity_check_user

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setTitleBar("忘记密码", true)
		sendMsgCodeFragment.registerRequestResult(this)

		btn_next.setOnClickListener {
			sendMsgCodeFragment.checkMsgCode {
				val intent = Intent(this, SetupLoginPwdBeforeLoginActivity::class.java)
				intent.putExtra("phoneNumber", sendMsgCodeFragment.phone)
				startActivity(intent)
				finish()
			}
		}
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		sendMsgCodeFragment.setResultData(`object`, contentType)
	}
}