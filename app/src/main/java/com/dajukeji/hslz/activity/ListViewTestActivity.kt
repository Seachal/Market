package com.dajukeji.hslz.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.dajukeji.hslz.R
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean
import com.dajukeji.hslz.view.dialog.InviteShareDialog
import kotlinx.android.synthetic.main.activity_list_view_test.*

class ListViewTestActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list_view_test)

//		xRecyclerView.layoutManager = object : LinearLayoutManager(this) {
//			override fun canScrollVertically(): Boolean {
//				return false//super.canScrollVertically()
//			}
//		}
		btn_share.setOnClickListener {
			InviteShareDialog("").show(supportFragmentManager, "")
		}

		xRecyclerView.layoutManager = LinearLayoutManager(this)
		xRecyclerView.isNestedScrollingEnabled = false

//		xRecyclerView.setLoadingMoreEnabled(false)
//		xRecyclerView.setPullRefreshEnabled(false)

		xRecyclerView.adapter = object :
			BaseRecyclerAdapter<GoodDetailsBean.ContentBean.RecommendGoodsListBean>(
				this, mutableListOf(
					GoodDetailsBean.ContentBean.RecommendGoodsListBean(),
					GoodDetailsBean.ContentBean.RecommendGoodsListBean(),
					GoodDetailsBean.ContentBean.RecommendGoodsListBean()
				), R.layout.item_store_goods
			) {
			override fun convert(
				holder: BaseRecyclerHolder?,
				data: GoodDetailsBean.ContentBean.RecommendGoodsListBean?,
				position: Int,
				isScrolling: Boolean
			) {
			}
		}

//		xRecyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
//			override fun onLoadMore() {
//
//			}
//
//			override fun onRefresh() {
//				xRecyclerView.refreshComplete()
//			}
//		})
	}
}

class TRecyclerView : RecyclerView {
	constructor(context: Context) : super(context)
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
	constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
		context, attrs, defStyleAttr
	)

	override fun canScrollVertically(direction: Int): Boolean {
		//return super.canScrollVertically(direction)
		return false
	}
}