package com.dajukeji.hslz.activity

import android.support.annotation.LayoutRes
import com.dajukeji.hslz.base.HttpBaseActivity

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:13
 * 作用:
 * 父类集成了:
 * 1、ButterKnife.bind(this);，但是我大kotlin并不需要~
 * 2、MVP(P仅负责网络请求),回调[setResultData]
 * 3、当布局中有id=[swipe_refresh]的[SwipeRefreshLayout]时会配合[showLoading]、[hideLoading]自动管理其刷新中状态
 * 4、使用[setTitleBar]初始化标题,注意布局必须为[R.layout.title_bar]
 *
 */
abstract class LkBaseActivity : HttpBaseActivity() {

	override fun initView() {
		layoutId().let { id ->
			if (id != 0)
				setContentView(id)
		}
		super.initView()
	}

	@LayoutRes
	open fun layoutId(): Int = 0
}