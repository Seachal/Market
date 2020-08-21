package com.dajukeji.hslz.view.dialog

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.dajukeji.hslz.R
import com.dajukeji.hslz.global.GlobalContants
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.util.ToastUtils
import com.tencent.mm.sdk.modelmsg.SendMessageToWX
import com.tencent.mm.sdk.modelmsg.WXMediaMessage
import com.tencent.mm.sdk.modelmsg.WXWebpageObject
import com.tencent.mm.sdk.openapi.WXAPIFactory
import kotlinx.android.synthetic.main.dialog_share.view.*


/**
 * 作者: Li_ke
 * 日期: 2019/2/22 17:00
 * 作用: 底部内容分享Dialog
 */
@SuppressLint("ValidFragment")
class InviteShareDialog(val inviteCode: String) : BaseDialogFragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		dialog.setGravity(Gravity.BOTTOM)
		return inflater.inflate(R.layout.dialog_share, container, false)
	}

	override fun onStart() {
		super.onStart()
		dialog.setSize(w = MATCH_PARENT)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.v_cancel.setOnClickListener { dismiss() }
		//剪切板
		view.v_shareToCopy.setOnClickListener {
			val clipboardManager =
				context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
			clipboardManager.primaryClip = ClipData.newPlainText(
				null,
				HttpAddress.mBaseUrl2+"App/register.html?invite_code=$inviteCode"
			)
			ToastUtils.getInstance().showToast(context, "已复制到剪切板")
			dialog.dismiss()
		}
		//朋友圈
		view.v_shareToWechatFriends.setOnClickListener {
			//初始化微信api
			val mApi = WXAPIFactory.createWXAPI(context, GlobalContants.wxAppID, true)
			mApi.registerApp(GlobalContants.wxAppID)

			//用 WXTextObject 对象初始化一个 WXMediaMessage 对象
			val msg = WXMediaMessage()
			msg.mediaObject =
				WXWebpageObject(HttpAddress.mBaseUrl2+"App/register.html?invite_code=$inviteCode")
			msg.title = "数字绿州"
			msg.description = "产品数字化，数字凭证化的全球电商平台，服务于生产企业与消费用户，实现由用户定价的价值传递体系。"
			msg.setThumbImage(BitmapFactory.decodeResource(resources, R.mipmap.logo))

			val req = SendMessageToWX.Req()
			req.message = msg
			req.scene = SendMessageToWX.Req.WXSceneTimeline
			req.transaction = "分享到朋友圈"
			//调用api接口，发送数据到微信
			mApi.sendReq(req)
			dialog.dismiss()
		}
		//微信
		view.v_shareToWechat.setOnClickListener {
			//初始化微信api
			val mApi = WXAPIFactory.createWXAPI(context, GlobalContants.wxAppID, true)
			mApi.registerApp(GlobalContants.wxAppID)

			//用 WXTextObject 对象初始化一个 WXMediaMessage 对象
			val msg = WXMediaMessage()
			msg.mediaObject =
				WXWebpageObject(HttpAddress.mBaseUrl2+"App/register.html?invite_code=$inviteCode")
			msg.title = "数字绿州"
			msg.description = "产品数字化，数字凭证化的全球电商平台，服务于生产企业与消费用户，实现由用户定价的价值传递体系。"
			msg.setThumbImage(BitmapFactory.decodeResource(resources, R.mipmap.logo))

			val req = SendMessageToWX.Req()
			req.message = msg
			req.scene = SendMessageToWX.Req.WXSceneSession
			req.transaction = "分享到微信"
			//调用api接口，发送数据到微信
			mApi.sendReq(req)
			dialog.dismiss()
		}
	}
}

