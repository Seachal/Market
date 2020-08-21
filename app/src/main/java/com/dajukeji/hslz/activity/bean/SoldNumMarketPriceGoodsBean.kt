package com.dajukeji.hslz.activity.bean

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dajukeji.hslz.R
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder

/**
 * 作者: Li_ke
 * 日期: 2019/2/15 13:39
 * 作用: 有已售数量与对比价的商品
 */
data class SoldNumMarketPriceGoodsBean(
	val goodsBean: GoodsBean,
	val saleNum: Int,
	val marketPrice: Double
)

/**显示商品+已售数的ListAdapter*/
class SoldNumGoodsRecyclerAdapter :
	BaseQuickAdapter<SoldNumMarketPriceGoodsBean, BaseViewHolder>(
		R.layout.item_goods_display_sold1
	) {
	override fun convert(holder: BaseViewHolder?, item: SoldNumMarketPriceGoodsBean?) {
		holder!!;item!!
		Glide.with(mContext).load(item.goodsBean.icon).into(holder.getView(R.id.iv_goodsImg))//图
		holder.setText(R.id.tv_goodsName, item.goodsBean.name)//名
		holder.setText(R.id.tv_goodsPrice, String.format("券%.2f", item.goodsBean.price))//价格
		holder.setText(R.id.tv_goodsSaleNum, String.format("%d件已售", item.saleNum))//已售
	}
}

/**显示商品+已售数的ListAdapter(针对XRecyclerView)*/
class SoldNumGoodsXRecyclerAdapter(private val context: Context) :
	BaseRecyclerAdapter<SoldNumMarketPriceGoodsBean>(
		context, null, R.layout.item_goods_display_sold1
	) {

	override fun convert(
		holder: BaseRecyclerHolder?, data: SoldNumMarketPriceGoodsBean?, position: Int,
		isScrolling: Boolean
	) {
		holder!!;data!!
		Glide.with(context).load(data.goodsBean.icon).into(holder.getView(R.id.iv_goodsImg))//图
		holder.setText(R.id.tv_goodsName, data.goodsBean.name)//名
		holder.setText(R.id.tv_goodsPrice, String.format("券%.2f", data.goodsBean.price))//价格
		holder.setText(R.id.tv_goodsSaleNum, String.format("%d件已售", data.saleNum))//已售
		holder.setText(R.id.shichangjia_price, String.format("￥%.2f",data.marketPrice))//市场价
		holder.getView<LinearLayout>(R.id.linear).visibility = View.VISIBLE
		holder.getView<TextView>(R.id.shichangjia_price).paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
//		holder.getView<TextView>(R.id.shichangjia).visibility = View.VISIBLE

	}
}