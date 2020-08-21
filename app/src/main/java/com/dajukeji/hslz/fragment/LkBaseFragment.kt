package com.dajukeji.hslz.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dajukeji.hslz.base.HttpBaseFragment
import kotlinx.android.synthetic.main.title_bar.view.*

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 11:07
 * 作用:
 *
 * 父类[HttpBaseFragment]集成了
 * 1、当布局中有id=[swipe_refresh]的[SwipeRefreshLayout]时会配合[showLoading]、[hideLoading]自动管理其刷新中状态
 * 2、集成MVP(但P仅负责网络请求),直接用[setResultData]来接受请求结果吧
 * 3、[setResultData]与[showHttpError]时自动调用[hideLoading],进而触发[hideDialogLoading]
 * 4、可在presenter中使用 [showLoading],进而触发[showDialogLoading]
 * 5、[rootView],注意没赋值
 * 没什么卵用的[View.OnClickListener]
 *
 * 此类
 * 1、统一用[layoutId]指定布局(个人爱好),并赋值[rootView]
 * 2、使用[initTitleBar]初始化标题,注意布局必须为[R.layout.title_bar]
 */
abstract class LkBaseFragment : HttpBaseFragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		rootView = inflater.inflate(layoutId(), container, false)
		return rootView
	}

	@LayoutRes
	abstract fun layoutId(): Int

	/**
	 * [enableBack] = 开启返回退出 else 隐藏返回按钮
	 */
	protected fun initTitleBar(title: String, enableBack: Boolean = false) {
		rootView?.title_bar_title?.text = title
		rootView?.title_bar_return?.visibility = if (enableBack) View.VISIBLE else View.GONE
		if (enableBack) {
			rootView?.title_bar_return?.setOnClickListener {
				activity?.onBackPressed()
			}
		} else {
			rootView?.title_bar_return?.setOnClickListener(null)
		}
	}
}