package com.dajukeji.hslz.view.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.dajukeji.hslz.R

/**
 * 作者: Li_ke
 * 日期: 2019/2/22 15:10
 * 作用: 隐藏了标题,
 */
open class BaseDialogFragment : DialogFragment() {

	override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
		//默认框样式(无边框,无标题,内容背景透明)
		if (theme == 0)
			setStyle(STYLE_NORMAL, R.style.DefaultDialog)
		return super.onCreateDialog(savedInstanceState)
	}
}

/**改变Dialog在屏幕中的位置的位置 [gravity]参考[android.view.Gravity.CENTER]*/
fun Dialog.setGravity(gravity: Int) {
	this.window!!.attributes = this.window!!.attributes.also {
		it.gravity = gravity
	}
}

/** 改变Dialog内容区的宽高(默认为[WRAP_CONTENT])，必须在[Dialog.show]之后设置才有效，因为show()会清理宽高属性 */
fun Dialog.setSize(w: Int = WRAP_CONTENT, h: Int = WRAP_CONTENT) {
	this.window!!.attributes = this.window!!.attributes.also {
		it.width = w
		it.height = h
	}
}