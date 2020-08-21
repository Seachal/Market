package com.dajukeji.hslz.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.bean.DisplayGoodsBigAndTreeRecyclerAdapter
import com.dajukeji.hslz.activity.bean.GoodsBean
import com.dajukeji.hslz.activity.bean.SoldNumGoodsXRecyclerAdapter
import com.dajukeji.hslz.activity.bean.SoldNumMarketPriceGoodsBean
import com.dajukeji.hslz.activity.search.SearchToolbar
import com.dajukeji.hslz.domain.javaBean.ResponseBoomHotListBean
import com.dajukeji.hslz.network.presenter.BoomHotPersenter
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_boom_hot_goods.*

/**
 * 爆款区的Activity
 */
class BoomHotGoodsActivity : LkBaseActivity(),XRecyclerView.LoadingListener {

	private lateinit var presenter: BoomHotPersenter

	/**顶部1大3小展示区*/
	private val headAdapter = DisplayGoodsBigAndTreeRecyclerAdapter()

	/**商品列表区*/
	private var adapter:SoldNumGoodsXRecyclerAdapter? = null



	override fun layoutId(): Int = R.layout.activity_boom_hot_goods

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		SearchToolbar(this).init()
		adapter = SoldNumGoodsXRecyclerAdapter(this)

		//我把这里recyclerView改成了XRecyclerView，但id没有改 王星（3-1）
		//发现了设配器已经设置了专用的，可以跳转至产品详情页面
		recyclerView.layoutManager = GridLayoutManager(this, 2) as RecyclerView.LayoutManager?
		recyclerView.setPullRefreshEnabled(true)
		recyclerView.setLoadingMoreEnabled(true)
		recyclerView.setLoadingListener(this)
		recyclerView.adapter = adapter



//		点击跳转商品详情
		adapter!!.setOnItemClickListener { adapter, view, position ->
			val intent = Intent(this, NormalGoodDetailActivity::class.java)
			intent.putExtra("goods_id", this.adapter!!.getitem(position)!!.goodsBean.id)
			startActivity(intent)
		}

		presenter = BoomHotPersenter(this)
		presenter.getGoodsList(this, "爆款商品列表")
	}

	override fun onRefresh() {
		adapter!!.clear()
		hideLoading()
		presenter.getGoodsList(this,"爆款商品列表")
		recyclerView.refreshComplete()
	}

	override fun onLoadMore() {
		hideLoading()
		presenter.getGoodsList(this,"爆款商品列表")
	}

	private fun complete(){
		recyclerView.refreshComplete()
		recyclerView.loadMoreComplete()
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		when (contentType) {
			"爆款商品列表" -> {
				val responseBean = `object` as ResponseBoomHotListBean
				responseBean.content.goodsList.size
				val listData: MutableList<SoldNumMarketPriceGoodsBean> =
					responseBean.content.goodsList.mapTo(mutableListOf()) { responseData ->
						SoldNumMarketPriceGoodsBean(
							GoodsBean(
								responseData.id,
								responseData.goods_name,
								responseData.goods_main_photo,
								responseData.goods_price
							), responseData.goods_salenum, responseData.ref_price
						)
					}
				adapter!!.setNewData(listData)

			}
			else -> {
			}
		}
		complete()//从服务器返回数据之后，停止刷新
	}
}

