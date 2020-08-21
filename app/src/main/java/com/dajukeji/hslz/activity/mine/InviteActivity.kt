package com.dajukeji.hslz.activity.mine

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.TextView
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.LkBaseActivity
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder
import com.dajukeji.hslz.domain.javaBean.ResponseInviteCodeBean
import com.dajukeji.hslz.network.presenter.InviteCodePresenter
import com.dajukeji.hslz.view.dialog.InviteShareDialog
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_invite.*

/**
 * 我的邀请/我的推荐页
 */
class InviteActivity : LkBaseActivity() {
	override fun layoutId(): Int = R.layout.activity_invite
	private var shareDialog: InviteShareDialog? = null
	private var page: Int = 1
	private val adapter by lazy {
		object : BaseRecyclerAdapter<InvitedManBean>(this, null, R.layout.item_invite) {
			override fun convert(
				holder: BaseRecyclerHolder?, data: InvitedManBean?, position: Int,
				isScrolling: Boolean
			) {
				data!!;holder!!
				holder.getView<TextView>(R.id.tv_name).text = data.name
				holder.getView<TextView>(R.id.tv_time).text = "注册时间：${data.time}"
			}
		}
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
//		StatusBarUtils.Builder().from(this).setTransparentStatusBar(true).process()

		setTitleBar("我的推荐", true)
//		v_back.setOnClickListener { finish() }

		btn_shareApp.setOnClickListener {
			shareDialog?.show(supportFragmentManager, "分享Dialog")
		}

		recyclerView.layoutManager = LinearLayoutManager(this)
//		recyclerView.setPullRefreshEnabled(false)
//		recyclerView.setLoadingMoreEnabled(false)
		recyclerView.adapter = adapter
		recyclerView.setLoadingListener(object : XRecyclerView.LoadingListener {
			override fun onRefresh() {
				page = 1
				InviteCodePresenter(this@InviteActivity).getInviteCodeInfo(this, page, "我的邀请信息")
			}

			override fun onLoadMore() {
				InviteCodePresenter(this@InviteActivity).getInviteCodeInfo(this, page, "我的邀请信息")
			}
		})

		InviteCodePresenter(this).getInviteCodeInfo(this, 1, "我的邀请信息")
	}

	/**
	 * 从网络传回数据时，object数据可能为空
	 */
	override fun setResultData(`object`: Any?, contentType: String?) {
		super.setResultData(`object`, contentType)
		when (contentType) {
			"我的邀请信息" -> {
				val codeBean = `object` as ResponseInviteCodeBean
				//报错原因是codeBean.content.invite_code有时候为空
				Log.d("yaoqing"," "+codeBean.toString())
				if(codeBean.content.invite_code == null||codeBean.content.invite_code.equals("")){
					return
				}
				tv_inviteCode.text = codeBean.content.invite_code//报错说推荐码为空，增加为空判断即可
				tv_invitedManTitle.text = "已邀请${codeBean.content.number}人"
				shareDialog = InviteShareDialog(codeBean.content.invite_code)
				//已邀请人列表
				val bean = codeBean.content.userList.map {
					InvitedManBean(it.user_id, it.nick_name ?: "null", it.addTime ?: "null")
				}
				page++
				if (page == 2) {//刷新/加载
					adapter.setNewData(bean)
					recyclerView.setLoadingMoreEnabled(true)
				} else {
					adapter.setData(bean)
				}
				if (page > codeBean.content.pages) {
					recyclerView.setLoadingMoreEnabled(false)
				}
				recyclerView.refreshComplete()
				recyclerView.loadMoreComplete()
			}
		}
	}

	data class InvitedManBean(val id: Int, val name: String, val time: String)
}