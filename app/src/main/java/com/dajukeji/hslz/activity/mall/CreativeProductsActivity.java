package com.dajukeji.hslz.activity.mall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.UserMessageActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 创意新品
 */
public class CreativeProductsActivity extends HttpBaseActivity {

    private GoodPresenter goodPresenter;
    private MessagePresenter messagePresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity> recyclerAdapter;
    private int pageSize;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_creative_products);
        setTitleBar(R.string.text_creative_products,true,0,0);
        goodPresenter = new GoodPresenter(this);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        ll_empty_order = (LinearLayout) findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) findViewById(R.id.tv_order_empty);
        im_empty = (ImageView) findViewById(R.id.im_empty);
        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                recyclerAdapter.clear();
                loadList(currentPage);
                isFirstPage = true;
            }

            @Override
            public void onLoadMore() {
                if (currentPage >pageSize) {
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });

        recyclerAdapter = new BaseRecyclerAdapter<GoodListBean.ContentEntity.GoodsListEntity>(getContext(), null, R.layout.item_creative_products) {
            @Override
            public void convert(BaseRecyclerHolder holder, GoodListBean.ContentEntity.GoodsListEntity data, int position, boolean isScrolling) {
                ImageView pict_url = holder.getView(R.id.pict_url);
                int size =  getResources().getDimensionPixelSize(R.dimen.x441);
                GlideEngine.loadThumbnail(CreativeProductsActivity.this,size, R.drawable.goods, pict_url, data.getGoods_main_photo());

                TextView item_title = holder.getView(R.id.item_title);
                item_title.setText(data.getGoods_name());
                TextView final_price = holder.getView(R.id.final_price);
                final_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getGoods_price()));
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodListBean.ContentEntity.GoodsListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, GoodListBean.ContentEntity.GoodsListEntity data, int position) {
                Intent intent = new Intent(getContext().getApplicationContext(), NormalGoodDetailActivity.class);
                intent.putExtra("goods_id", data.getId());
                CreativeProductsActivity.this.startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList(currentPage);
        $(R.id.title_bar_notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        Intent messageIntent = new Intent(CreativeProductsActivity.this, UserMessageActivity.class);
                        startActivity(messageIntent);
                    }else{
                        startActivity(new Intent(CreativeProductsActivity.this, MobailePhoneLoginActivity.class));
                    }

                }else {
                    startActivity(new Intent(CreativeProductsActivity.this, WeChatLoginActivity.class));
                }
            }
        });
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        goodPresenter.getGoodsList(getContext(),page,"creative", DataType.mall.creative.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.mall.creative.toString())){
            GoodListBean goodListBean = (GoodListBean) object;
            complete();
            if(goodListBean.getContent().getGoodsList().isEmpty()){
                ll_empty_order.setVisibility(View.VISIBLE);
                tv_order_empty.setText("暂无创意新品产品");
                im_empty.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(goodListBean.getContent().getGoodsList());
                pageSize = goodListBean.getContent().getPages();
                currentPage++;
            }
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if(event.message.equals("message")){
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    private void complete() {
        hideDialogLoading();
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

}
