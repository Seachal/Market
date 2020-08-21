package com.dajukeji.hslz.activity.search

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.MobileActivity
import com.dajukeji.hslz.activity.UserMessageActivity
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity
import com.dajukeji.hslz.util.SPUtil

/**
 * 快速初始化 @layout/layout_search_toolbar 布局
 */
class SearchToolbar(val activity: Activity) {
	fun init(): SearchToolbar {
		//返回
		activity.findViewById<View>(R.id.v_back).setOnClickListener {
			activity.finish()
		}
		//搜索
		activity.findViewById<View>(R.id.v_search).setOnClickListener {
			activity.startActivity(Intent(activity, ShopFindActivity::class.java))
		}
		//消息
		activity.findViewById<View>(R.id.v_message).setOnClickListener {
			//验证已登录
			if (SPUtil.getPrefString("token", "") != "") {
				val messageIntent = Intent(activity, UserMessageActivity::class.java)
				activity.startActivity(messageIntent)
			} else {
				activity.startActivity(Intent(activity, MobileActivity::class.java))
			}
		}

		return this
	}

	var searchContent: String
		set(value) {
			activity.findViewById<TextView>(R.id.tv_searchContent).text = value
		}
		get() = activity.findViewById<TextView>(R.id.tv_searchContent).text.toString()
}