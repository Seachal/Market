package com.dajukeji.hslz.activity.taocoupon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.LkBaseActivity
import com.dajukeji.hslz.activity.NormalGoodDetailActivity
import com.dajukeji.hslz.activity.bean.GoodsBean
import com.dajukeji.hslz.activity.bean.SoldNumGoodsXRecyclerAdapter
import com.dajukeji.hslz.activity.bean.SoldNumMarketPriceGoodsBean
import com.dajukeji.hslz.domain.javaBean.ResponseFindResultBean
import com.dajukeji.hslz.network.presenter.ShopFindPresenter
import com.dajukeji.hslz.util.ToastUtils
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_shop_found.*

/**商品搜索结果*/
class ShopFoundActivity : LkBaseActivity() {
	private lateinit var presenter: ShopFindPresenter
	private val type by lazy { intent.getSerializableExtra("type") as ShopFindPresenter.FindType }
	private val searchKey by lazy { intent.getStringExtra("searchKey") }
	private val adapter by lazy { SoldNumGoodsXRecyclerAdapter(this) }
	private var page = 1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_shop_found)

		presenter = ShopFindPresenter(this)
		setTitleBar(searchKey, true)
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		recyclerView.adapter = adapter
		recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
			override fun onRefresh() {
				page = 1
				presenter.findGoods(this, type, searchKey, page, "搜索商品")
			}

			override fun onLoadMore() {
				presenter.findGoods(this, type, searchKey, page, "搜索商品")
			}
		})
		adapter.setOnItemClickListener { _, data, _ ->
			//点击跳详情
			startActivity(
				Intent(this, NormalGoodDetailActivity::class.java)
					.putExtra("goods_id", data.goodsBean.id)
			)
		}

		recyclerView.refresh()
	}

	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		when (contentType) {
			"搜索商品" -> {
				val bean = `object` as ResponseFindResultBean
				page++
				val uiBean = bean.content.goodsList.map {
					SoldNumMarketPriceGoodsBean(
						GoodsBean(
							it.id,
							it.goods_name,
							it.goods_main_photo,
							it.goods_price
						), it.goods_salenum, it.goods_current_price
					)
				}
				//无数据
				if (page == 2 && uiBean.isEmpty()) {
					ToastUtils.getInstance().showToast(context, "没有发现商品")
				}
				//是刷新or加载
				if (page == 2) {
					adapter.setNewData(uiBean)
					recyclerView.setLoadingMoreEnabled(true)
				} else {
					adapter.setData(uiBean)
				}
				//有无更多数据
				if (page > bean.content.pages) {
					recyclerView.setLoadingMoreEnabled(false)
				}
				//关闭加载状态
				recyclerView.refreshComplete()
				recyclerView.loadMoreComplete()
			}
		}
	}

	companion object {
		fun getStartIntent(
			context: Context,
			type: ShopFindPresenter.FindType? = ShopFindPresenter.FindType.HOME,
			searchKey: String
		): Intent {
			return Intent(context, ShopFoundActivity::class.java).putExtra("type", type)
				.putExtra("searchKey", searchKey)
		}
	}
}
