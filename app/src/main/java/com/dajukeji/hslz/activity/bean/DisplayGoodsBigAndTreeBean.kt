package com.dajukeji.hslz.activity.bean

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.dajukeji.hslz.R

/**1图+3物 展示商品,布局为 layout_display_goods_big_and_three*/
data class DisplayGoodsBigAndTreeBean(
	val imgBig: String,
	val goodsLeft: AbleZeroGoodsBean,
	val goodsCenter: AbleZeroGoodsBean,
	val goodsRight: AbleZeroGoodsBean
)

/**可能售空的商品*/
data class AbleZeroGoodsBean(
	val goodsBean: GoodsBean,
	val isSoldOut: Boolean = true
)

/**使用此Bean的ListAdapter*/
class DisplayGoodsBigAndTreeRecyclerAdapter :
	BaseQuickAdapter<DisplayGoodsBigAndTreeBean, BaseViewHolder>(
		R.layout.item_display_goods_big_and_three
	) {
	override fun convert(helper: BaseViewHolder?, item: DisplayGoodsBigAndTreeBean?) {
		helper!!;item!!
		Glide.with(mContext).load(item.imgBig).into(helper.getView(R.id.iv_bigImg))

		//图
		Glide.with(mContext).load(item.goodsLeft.goodsBean.icon)
			.into(helper.getView(R.id.iv_goodsImg1))
		Glide.with(mContext).load(item.goodsCenter.goodsBean.icon)
			.into(helper.getView(R.id.iv_goodsImg2))
		Glide.with(mContext).load(item.goodsRight.goodsBean.icon)
			.into(helper.getView(R.id.iv_goodsImg3))

		//已售空
		helper.getView<View>(R.id.iv_soldOut1).visibility =
			if (item.goodsLeft.isSoldOut) View.VISIBLE else View.GONE
		helper.getView<View>(R.id.iv_soldOut2).visibility =
			if (item.goodsCenter.isSoldOut) View.VISIBLE else View.GONE
		helper.getView<View>(R.id.iv_soldOut3).visibility =
			if (item.goodsRight.isSoldOut) View.VISIBLE else View.GONE

		//名字
		helper.setText(R.id.tv_goodsName1, item.goodsLeft.goodsBean.name)
		helper.setText(R.id.tv_goodsName2, item.goodsCenter.goodsBean.name)
		helper.setText(R.id.tv_goodsName3, item.goodsRight.goodsBean.name)

		//价格
		helper.setText(R.id.tv_goodsPrice1, String.format("券%.2f", item.goodsLeft.goodsBean.price))
		helper.setText(
			R.id.tv_goodsPrice2,
			String.format("券%.2f", item.goodsCenter.goodsBean.price)
		)
		helper.setText(R.id.tv_goodsPrice3, String.format("券%.2f", item.goodsRight.goodsBean.price))
	}
}