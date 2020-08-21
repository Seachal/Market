package com.dajukeji.hslz.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView

class MyItemDecoration : RecyclerView.ItemDecoration() {

	companion object {
		fun simpleDecoration(
			context: Context,
			color: Int = Color.BLACK,
			orientation: Int = DividerItemDecoration.VERTICAL
		): DividerItemDecoration {
			val itemDecoration = DividerItemDecoration(context, orientation)
			itemDecoration.setDrawable(ColorDrawable(color))
			return itemDecoration
		}
	}
}