package com.dajukeji.hslz.network.presenter

import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean
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

class UserInfoPersenter(private val iView: IView) {
	private val okGoEngine: OkGoEngine = OkGoEngine()
	private val gson: Gson = Gson()
	private val cacheMode: CacheMode? = null

	/**get个人信息*/
	fun getUserInfo(tag: Any, contentType: String) {
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
