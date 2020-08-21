package com.dajukeji.hslz.network.presenter

import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean
import com.dajukeji.hslz.domain.javaBean.ResponseBoomHotListBean
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.network.IView
import com.dajukeji.hslz.network.OkGoEngine
import com.dajukeji.hslz.network.RequestCallBack
import com.dajukeji.hslz.util.SPUtil
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpParams

/**爆款区信息*/
class BoomHotPersenter(private val iView: IView) {
	private val okGoEngine: OkGoEngine = OkGoEngine()
	private val gson: Gson = Gson()
	private val cacheMode: CacheMode? = null

	/**get爆款区商品列表*/
	fun getGoodsList(tag: Any, contentType: String) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.boomHotList
		val map = HttpParams()
		map.put("token", SPUtil.getPrefString("token", ""))
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {
			override fun onsuccess(json: String?) {
				try {
					val bean = gson.fromJson(json, ResponseBoomHotListBean::class.java)
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

	/**修改个人信息*/
	fun setupAddressRealName(
		tag: Any, address: String? = null, realName: String? = null, contentType: String
	) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.changeUserInfo
		val map = HttpParams()
		map.put("token", SPUtil.getPrefString("token", ""))
		address?.run { map.put("address", address) }
		realName?.run { map.put("trueName", realName) }
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {

			override fun onsuccess(json: String?) {
				try {
					val phoneloginBean = gson.fromJson(json, BaseStatusMessageBean::class.java)
					if (phoneloginBean.status == "0") {
						iView.setResultData(phoneloginBean, contentType)
					} else {
						onfailed(phoneloginBean.message)
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
