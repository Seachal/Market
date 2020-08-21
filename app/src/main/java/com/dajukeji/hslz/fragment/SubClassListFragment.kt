package com.dajukeji.hslz.fragment


import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.dajukeji.hslz.R
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder
import com.dajukeji.hslz.domain.javaBean.GoodsClassSubBean
import com.dajukeji.hslz.network.presenter.GoodsClassPresenter
import com.dajukeji.hslz.util.SPUtil
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.fragment_sub_class_list.view.*

/**
 * 右侧商品二级类型列表
 */
class SubClassListFragment : LkBaseFragment() {
	private var selectedClassId: Long = -1L
	@Deprecated("接口没有页数")
	private var page = 1
	private val presenter = GoodsClassPresenter(this)
	var onItemClick: ((SubClassBean) -> Unit)? = null

	private val listAdapter: BaseRecyclerAdapter<SubClassBean>  by lazy {
		object :
			BaseRecyclerAdapter<SubClassBean>(context, null, R.layout.item_goods_class_sub) {
			override fun convert(
				holder: BaseRecyclerHolder?, data: SubClassBean?, position: Int,
				isScrolling: Boolean
			) {
				holder?.getView<TextView>(R.id.tv_text)?.text = data?.name
				Glide.with(this@SubClassListFragment).load(data?.icon)
					.into(holder!!.getView(R.id.iv_icon)!!)
			}
		}
	}

	override fun layoutId() = R.layout.fragment_sub_class_list

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		rootView.recyclerView_subClassList.setLoadingMoreEnabled(false)
		rootView.recyclerView_subClassList.setPullRefreshEnabled(false)
		rootView.recyclerView_subClassList.layoutManager = GridLayoutManager(context, 3)
		rootView.recyclerView_subClassList.adapter = listAdapter

		//下拉刷新
		rootView.swipe_refresh.isEnabled = false//关闭下拉
		rootView.swipe_refresh.setOnRefreshListener {
			page = 1
			presenter.getGoodsSubClassList(
				this, page, SPUtil.getPrefString("token", ""), selectedClassId, "查询分类列表"
			)
		}
		//上拉加载
		rootView.recyclerView_subClassList.setLoadingListener(object :
			XRecyclerView.LoadingListener {
			override fun onRefresh() {}
			override fun onLoadMore() {
				presenter.getGoodsSubClassList(
					this, page, SPUtil.getPrefString("token", ""), selectedClassId, "查询分类列表"
				)
			}
		})
		//点击选中
		listAdapter.setOnItemClickListener { viewHolder, data, position ->
			onItemClick?.invoke(data)
		}


	}

	/**通过id加载数据*/
	fun refreshDataByTabId(id: Long) {
		selectedClassId = id
		//重新拿数据
		page = 1
//		rootView.swipe_refresh.isRefreshing = true
		presenter.getGoodsSubClassList(
			this, page, SPUtil.getPrefString("token", ""), selectedClassId, "查询分类列表"
		)
	}

	override fun setResultData(`object`: Any?, dataType: String?) {
		super.setResultData(`object`, dataType)
		when (dataType) {
			"查询分类列表" -> {
				page++
				val dataBean = `object` as GoodsClassSubBean
				//停止刷新样式
				rootView.swipe_refresh.isRefreshing = false
				rootView.recyclerView_subClassList.loadMoreComplete()
/*				接口没有页数
				//刚刚下拉刷新成功,开启上拉
				if (page == 2)
					rootView.recyclerView_subClassList.setLoadingMoreEnabled(false)
				//数据太少,不能再上啦了,关闭上啦
				if (page >= dataBean.content.pages) {
					rootView.recyclerView_subClassList.setLoadingMoreEnabled(false)
				}
*/

				//显示数据
				val viewData =
					dataBean.content?.goodsClassList?.mapTo(mutableListOf()) { clazz ->
						SubClassBean(clazz.class_id.toLong(), clazz.class_name, clazz.class_url)
					}
				if (page == 2)//下拉,重新显示数据
					listAdapter.setNewData(viewData)
				else//上拉,添加更多数据
					listAdapter.setData(viewData)
			}
		}
	}

	data class SubClassBean(val id: Long, val name: String, val icon: String)
}

