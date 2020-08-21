package com.dajukeji.hslz.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.bean.GoodsBean
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder
import com.dajukeji.hslz.domain.javaBean.GoodsClassDetailsBean
import com.dajukeji.hslz.network.presenter.GoodsClassDetailsListPresenter
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_class_details.*

/**分类详情页*/
class ClassDetailsActivity : LkBaseActivity() {

	private val classId by lazy { intent.getLongExtra("class_id", -1L) }
	private val className by lazy { intent.getStringExtra("class_name") }

	private var page = 1
	private val presenter = GoodsClassDetailsListPresenter(this)

	private val adapter: BaseRecyclerAdapter<GoodsBean> by lazy {
		object : BaseRecyclerAdapter<GoodsBean>(this, null, R.layout.item_goods_class_details) {
			override fun convert(
				holder: BaseRecyclerHolder?, data: GoodsBean?, position: Int, isScrolling: Boolean
			) {
				data!!;holder!!

				Glide.with(this@ClassDetailsActivity).load(data.icon)
					.into(holder.getView(R.id.iv_icon))
				(holder.getView(R.id.tv_text) as TextView).text = data.name
				(holder.getView(R.id.tv_price) as TextView).text =
						String.format("券 %.2f", data.price)
			}
		}
	}

	override fun layoutId(): Int = R.layout.activity_class_details

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setTitleBar(className, true)
		//Recycler
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		recyclerView.adapter = adapter

		//刷新加载
		recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
			override fun onRefresh() {
				presenter.getGoodsClassDetailsList(this, 1, classId, "下拉刷新")
			}

			override fun onLoadMore() {
				presenter.getGoodsClassDetailsList(this, page + 1, classId, "上拉加载")
			}
		})
		//点击
		adapter.setOnItemClickListener { viewHolder, data, position ->
			val intent = Intent(this, NormalGoodDetailActivity::class.java)
			intent.putExtra("goods_id", data.id)
			startActivity(intent)
		}

		recyclerView.refresh()
		presenter.getGoodsClassDetailsList(this, 1, classId, "下拉刷新")
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		when (contentType) {
			"下拉刷新" -> {
				page = 1
				val bean = `object` as GoodsClassDetailsBean
				//停止加载动画
				recyclerView.refreshComplete()
				recyclerView.loadMoreComplete()
				//还有数据否
				if (page < bean.content.pages)
					recyclerView.setLoadingMoreEnabled(true)
				//显示数据
				val data = bean.content.goodsList.mapTo(mutableListOf()) {
					GoodsBean(it.id, it.goods_name, it.goods_main_photo, it.goods_price)
				}
				adapter.setNewData(data)
				//空视图否
				visible_empty.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
			}
			"上拉加载" -> {
				val bean = `object` as GoodsClassDetailsBean
				//停止加载动画
				recyclerView.refreshComplete()
				recyclerView.loadMoreComplete()
				//还有数据否
				if (page + 1 >= bean.content.pages) {
					recyclerView.setLoadingMoreEnabled(false)
				} else {
					page += 1
				}
				//显示数据
				val data = bean.content.goodsList.map {
					GoodsBean(it.id, it.goods_name, it.goods_main_photo, it.goods_price)
				}
				adapter.setData(data)
			}
		}
	}

	companion object {
		fun getStartIntent(context: Context?, class_id: Long, class_name: String): Intent {
			return Intent(context, ClassDetailsActivity::class.java)
				.putExtra("class_id", class_id)
				.putExtra("class_name", class_name)
		}
	}
}
