package com.dajukeji.hslz.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.dajukeji.hslz.R
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder
import com.dajukeji.hslz.domain.javaBean.GoodsClassTabBean
import com.dajukeji.hslz.network.presenter.GoodsClassPresenter
import kotlinx.android.synthetic.main.fragment_tab_home_class.view.*


/**
 * 商品分类页左侧的商品一级类型
 * kotlin写的
 */
class TabHomeClassFragment : LkBaseFragment() {//继承自LkBaseFragment

	private val presenter = GoodsClassPresenter(this)//GoodsClassPresenter是个java类

	private var selectedTabId = -1L//记录当前被选中的标签

	/**
	 * 定义一个只读的变量，listAdapter，这个变量的数据类型是BaseRecyclerAdapter《TabBean》
	 * 里面的泛型是一个私有内部类，数据类，有两个参数（id，name）
	 */
	private val listAdapter: BaseRecyclerAdapter<TabBean> by lazy {
		//这里看不太懂是什么意思，context是从httpfFragment继承来的,第二个list参数为空，第三个布局是一个一级目录标签
		//好像理解了，listAdapter是一个BaseRecyclerAdapter<TabBean>类型的变量，下面的代码是为这个变量赋值的
		//lazy关键字表示，赋值操作只执行一次，以后都是直接调用listAdapter就行了
		object : BaseRecyclerAdapter<TabBean>(context, null, R.layout.item_goods_class_tab) {

			//重写了抽象方法convert，从这里可以访问Adapter中的数据持有者
			//这里主要作用是根据单击事件来改变view的显示
			override fun convert(holder: BaseRecyclerHolder?, data: TabBean?, position: Int, isScrolling: Boolean) {

				holder?.getView<TextView>(R.id.tv_tab)?.text = data?.name
				holder?.getView<TextView>(R.id.tv_tab_selected)?.text = data?.name

				if (selectedTabId == data?.id) {//已选中
					holder?.getView<TextView>(R.id.tv_tab_selected)?.visibility = View.VISIBLE
					holder?.getView<View>(R.id.v_selected)?.visibility = View.VISIBLE
					holder?.getView<TextView>(R.id.tv_tab)?.visibility = View.GONE
				} else {//未选中
					holder?.getView<TextView>(R.id.tv_tab_selected)?.visibility = View.GONE
					holder?.getView<View>(R.id.v_selected)?.visibility = View.GONE
					holder?.getView<TextView>(R.id.tv_tab)?.visibility = View.VISIBLE
				}
			}
		}

	}

	/**选中Tab监听*/
	var onSelectedTabListener: ((id: Long) -> Unit)? = null

	override fun layoutId() = R.layout.fragment_tab_home_class

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		//rootView也是从HttpBaseFragment继承而来的
		rootView.recyclerView_tab.layoutManager = LinearLayoutManager(context)//设置rootView中的Recyclerveiw布局管理器

		rootView.recyclerView_tab.adapter = listAdapter//设置rootView中的Recyclerveiw适配器为listAdapter

		//点击选中
		listAdapter.setOnItemClickListener { viewHolder, data, position ->
			selectedTabId = data.id//记录被选中的标签
			listAdapter.notifyDataSetChanged()//刷新通过adapter刷新显示
			onSelectedTabListener?.invoke(selectedTabId)//当被某个一级菜单被单击后，将id通过监听接口
		}


		//数据
		//从服务器端获取到左侧目录列表
		presenter.getGoodsClassTabList(this, "大类型")
	}

	/**
	 * 从服务端返回的结果，返回结果之后会被调用
	 */
	override fun setResultData(`object`: Any?, dataType: String?) {
		super.setResultData(`object`, dataType)
		when (dataType) {
			"大类型" -> {
				val goodsClassBean = `object` as GoodsClassTabBean
				val viewData =
					goodsClassBean.content?.goodsClassList?.mapTo(mutableListOf()) { clazz ->
						TabBean(clazz.class_id.toLong(), clazz.class_name)
					}
				listAdapter.setNewData(viewData)//清空原本的adapter，然后加入新的adapter

				//默认选中第一项
				val defaultId = viewData?.getOrNull(0)?.id ?: -1L
				selectedTabId = defaultId
				listAdapter.notifyDataSetChanged()
				//自动去拿第一项数据
				onSelectedTabListener?.invoke(defaultId)
			}
			else -> {
			}
		}
	}
}

//kotlin的内部数据类
private data class TabBean(val id: Long, val name: String)