package com.dajukeji.hslz.fragment


import android.os.Bundle
import android.view.View
import com.dajukeji.hslz.R
import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean
import com.dajukeji.hslz.network.IView
import com.dajukeji.hslz.network.presenter.MsgCodePresenter
import com.dajukeji.hslz.util.FormatCheckUtils
import com.dajukeji.hslz.util.ToastUtils
import kotlinx.android.synthetic.main.fragment_msg_code_check.view.*


/**
 * 短信验证码
 *
 */
class SendMsgCodeFragment : LkBaseFragment() {

	val phone get() = rootView?.edit_phone?.text?.toString()
	val msgCode get() = rootView?.edit_msgCode?.text?.toString()
	var checkMsgCodeCallback: (() -> Unit)? = null

	private var presenter = MsgCodePresenter(this)

	override fun layoutId() = R.layout.fragment_msg_code_check

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		//发送验证码点击操作
		rootView.btn_sendMsgCode.setOnClickListener {
			//校验信息格式
			val errorMsg = (FormatCheckUtils.checkPhone(phone))
			if (errorMsg != null)
				ToastUtils.getInstance().showToast(context, errorMsg)
			else
				presenter.sendMsgCode(this, phone, "发送验证码")
		}

	}

	/**注册请求回调,请求到后走此回调*/
	fun registerRequestResult(iView: IView) {
		presenter = MsgCodePresenter(iView)
	}

	/**验证短信验证码*/
	fun checkMsgCode(callback: (() -> Unit)?) {
		checkMsgCodeCallback = callback
		//校验信息格式
		val errorMsg =
			(FormatCheckUtils.checkPhone(phone) ?: FormatCheckUtils.checkMsgCode(msgCode))
		if (errorMsg != null)
			ToastUtils.getInstance().showToast(context, errorMsg)
		else
			presenter.checkMsgCode(this, phone, msgCode, "校验验证码")
	}

	override fun setResultData(`object`: Any?, dataType: String?) {
		super.setResultData(`object`, dataType)

		when (dataType) {
			"发送验证码" -> {
				val bean = `object` as BaseStatusMessageBean
				ToastUtils.getInstance().showToast(context, bean.message)
				//发送成功
				if (bean.status == "0") {
					rootView.btn_sendMsgCode.sentCode()
				}
			}
			"校验验证码" -> {
				val bean = `object` as BaseStatusMessageBean
				ToastUtils.getInstance().showToast(context, bean.message)
				if (bean.status == "0")
					checkMsgCodeCallback?.invoke()
			}
		}
	}
}
