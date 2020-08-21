package com.dajukeji.hslz.network.presenter

import com.dajukeji.hslz.domain.javaBean.ResponseInviteCodeBean
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.network.IView
import com.dajukeji.hslz.network.OkGoEngine
import com.dajukeji.hslz.network.RequestCallBack
import com.dajukeji.hslz.util.SPUtil
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.cache.CacheMode
import com.lzy.okgo.model.HttpParams

class InviteCodePresenter(private val iView: IView) {
	private val okGoEngine: OkGoEngine = OkGoEngine()
	private val gson: Gson = Gson()
	private val cacheMode: CacheMode? = null

	/**邀请信息*/
	fun getInviteCodeInfo(tag: Any, page: Int, contentType: String) {
		iView.showLoading()
		val url = HttpAddress.mBaseUrl2 + HttpAddress.inviteCodeInfo
		val map = HttpParams()
		map.put("token", /*"oPJ8m555s06bg2ch2J7HOU-IZBqw")*/SPUtil.getPrefString("token", ""))
		map.put("currentPage", page)
		okGoEngine.postMap(tag, url, map, object : RequestCallBack {

			override fun onsuccess(json: String?) {
				try {
					val bean = gson.fromJson(json, ResponseInviteCodeBean::class.java)
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
