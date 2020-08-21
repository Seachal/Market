package com.dajukeji.hslz.view

import android.content.Context
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import com.dajukeji.hslz.R
import java.lang.ref.WeakReference

/**
 * 发送短信验证码Button
 * 自带发送冷却、样式、文本，60秒后才可再次发送，不可发送时 enable = false
 */
class SendVerifyButton : Button {

	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
		context,
		attrs,
		defStyleAttr
	)

	init {
		background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			context.resources.getDrawable(R.drawable.selector_enable_button, null)
		} else
			context.resources.getDrawable(R.drawable.selector_enable_button)
		"".replace("","")
		text = "发送验证码"
		VerifyCountDown.register(this)
	}

	/**成功发送验证码时手动调用*/
	fun sentCode(){
		VerifyCountDown.sentVerifyCode()
	}

	private object VerifyCountDown : CountDownTimer(60 * 1000L, 1000L) {
		private var isRunning = false
		private var millisUntilFinished = 0L
		private val verifyViewList = mutableListOf<WeakReference<TextView>>()

		fun register(verifyView: TextView) {
			verifyViewList.add(WeakReference(verifyView))
			if (isRunning) {
				verifyView.isEnabled = false
				verifyView.text = "${millisUntilFinished / 1000}"
			}
		}

		fun sentVerifyCode() {
			isRunning = true
			start()
		}

		override fun onTick(millisUntilFinished: Long) {
			for (reference in ArrayList(verifyViewList)) {
				val textView = reference.get()
				if (textView == null) verifyViewList.remove(reference)

				textView?.text = "${millisUntilFinished / 1000}"
				textView?.isEnabled = false
			}
			this.millisUntilFinished = millisUntilFinished
		}

		override fun onFinish() {
			isRunning = false
			for (reference in ArrayList(verifyViewList)) {
				val textView = reference.get()
				if (textView == null) verifyViewList.remove(reference)

				textView?.text = "发送验证码"//直接购买，下单，圈
				textView?.isEnabled = true
			}
		}
	}
}