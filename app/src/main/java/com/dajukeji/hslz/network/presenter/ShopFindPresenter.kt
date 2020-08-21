package com.dajukeji.hslz.network.presenter

import com.dajukeji.hslz.domain.javaBean.ResponseFindResultBean
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.network.IView
import com.dajukeji.hslz.network.OkGoEngine
import com.dajukeji.hslz.network.RequestCallBack
import com.dajukeji.hslz.util.SPUtil
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpParams

class ShopFindPresenter(private val iView: IView) {
	private val okGoEngine: OkGoEngine = OkGoEngine()
	private val gson: Gson = Gson()
	private val cacheMode: CacheMode? = null

	/**寻找商品
	 * @param type : （首页home_page，selection评选区，new新品区，short爆款区）
	 */
	fun findGoods(tag: Any, type: FindType, searchKey: String, page: Int, contentType: String) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.findGoods
		val map = HttpParams()
		map.put("token", SPUtil.getPrefString("token", ""))
		map.put("currentPage", page)
		map.put("keyword", searchKey)
		map.put(
			"type", when (type) {
				FindType.HOME -> "home_page"
				FindType.NEW_LOCAL -> "new"
				FindType.BOOM_HOT_LOCAL -> "short"
				FindType.EVALUATE_LOCAL -> "selection"
			}
		)
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {
			override fun onsuccess(json: String?) {
				try {
					val bean = gson.fromJson(json, ResponseFindResultBean::class.java)
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

	enum class FindType {
		HOME, NEW_LOCAL, BOOM_HOT_LOCAL, EVALUATE_LOCAL
	}
}
