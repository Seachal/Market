package com.dajukeji.hslz.fragment

import android.os.Bundle
import android.view.View
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.ClassDetailsActivity


/**
 * 首页-分类 Fragment
 * 商品分类页面
 */
class HomeClassFragment : LkBaseFragment() {

	override fun layoutId() = R.layout.fragment_home_class

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initTitleBar("商品分类")

		//这是一个左侧的主菜单
		val tabFragment =
			childFragmentManager.findFragmentById(R.id.fragment_leftTabClass) as TabHomeClassFragment
		//这是右侧的子菜单
		val subClassListFragment =
			childFragmentManager.findFragmentById(R.id.fragment_rightSubClassList) as SubClassListFragment

		//点击分类tab会更新子分类列表
		tabFragment.onSelectedTabListener = { id: Long -> subClassListFragment.refreshDataByTabId(id) }

		//点击子分类会跳转分类详情页
		subClassListFragment.onItemClick = { bean ->
			startActivity(ClassDetailsActivity.getStartIntent(context, bean.id, bean.name))
		}
	}
}
