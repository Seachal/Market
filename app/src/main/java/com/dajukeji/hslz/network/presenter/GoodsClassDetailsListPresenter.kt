package com.dajukeji.hslz.network.presenter

import com.dajukeji.hslz.domain.javaBean.GoodsClassDetailsBean
import com.dajukeji.hslz.domain.javaBean.UserInfoBean
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.network.IView
import com.dajukeji.hslz.network.OkGoEngine
import com.dajukeji.hslz.network.RequestCallBack
import com.dajukeji.hslz.util.SPUtil
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpParams

class GoodsClassDetailsListPresenter(private val iView: IView) {
	private val okGoEngine: OkGoEngine = OkGoEngine()
	private val gson: Gson = Gson()
	private val cacheMode: CacheMode? = null

	/**get商品列表*/
	private fun getUserInfo(tag: Any, contentType: String) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.userInfo
		val map = HttpParams()
		map.put("token", SPUtil.getPrefString("token", ""))
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {
			override fun onsuccess(json: String?) {
				try {
					val userInfoBean = gson.fromJson(json, UserInfoBean::class.java)
					if (userInfoBean.status == "0") {
						iView.setResultData(userInfoBean, contentType)
					} else {
						onfailed(userInfoBean.message)
						iView.hideLoading()
					}
				} catch (e: JsonSyntaxException) {
					e.printStackTrace()
				}

			}

			override fun onfailed(exception: String?) {
				iView.showHttpError(exception, contentType)
			}
		})
	}

	/**get商品列表*/
	fun getGoodsClassDetailsList(tag: Any, page: Int, class_id: Long, contentType: String) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.goodsClassDetailsList
		val map = HttpParams()
		map.put("token", SPUtil.getPrefString("token", ""))
		map.put("currentPage", page)
		map.put("class_id", class_id)
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {

			override fun onsuccess(json: String?) {
				try {
					val bean = gson.fromJson(json, GoodsClassDetailsBean::class.java)
					if (bean.status == "0") {
						iView.setResultData(bean, contentType)
					} else {
						onfailed(bean.message)
						iView.hideLoading()
					}
				} catch (e: JsonSyntaxException) {
					e.printStackTrace()
				}

			}

			override fun onfailed(exception: String?) {
				iView.showHttpError(exception, contentType)
			}
		})
	}
}
