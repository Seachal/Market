package com.dajukeji.hslz.activity

import android.content.Intent
import android.os.Bundle
import com.dajukeji.hslz.R
import com.dajukeji.hslz.fragment.SendMsgCodeFragment
import kotlinx.android.synthetic.main.activity_check_user.*

/**
 * 修改支付密码验证页
 */
class SetupPayPwdCheckActivity : LkBaseActivity() {

	private val sendMsgCodeFragment
		get() = supportFragmentManager.findFragmentById(R.id.fragment_sendMsgCode) as SendMsgCodeFragment

	override fun layoutId() = R.layout.activity_check_user

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setTitleBar("修改支付密码验证", true)
		sendMsgCodeFragment.registerRequestResult(this)

		btn_next.setOnClickListener {
			sendMsgCodeFragment.checkMsgCode{
				startActivity(Intent(this, PaypassActivity::class.java))
				finish()
			}
		}
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		sendMsgCodeFragment.setResultData(`object`, contentType)
	}
}
