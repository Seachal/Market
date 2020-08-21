package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.EvaluateAllBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * 全部评论
 */

public class EvaluateAllActivity extends HttpBaseActivity {

    private GoodPresenter goodPresenter;
    private XRecyclerView xRecyclerView;

    private BaseRecyclerAdapter<EvaluateAllBean.ContentBean.EvaluateListBean> recyclerAdapter;
    private TextView goods_detail_evaluate; // 全部数量
    private int currentPage = 1;
    private int pageSize;
    private boolean isFirstPage = true;
    public String goods_id; // 当前选择的产品ID
    private String count; // 数量

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_evaluate_all);
        setTitleBar(R.string.evaluate_all,true);
        goodPresenter = new GoodPresenter(this);
        Bundle bundle = getIntent().getExtras();
        goods_id = bundle.getString("goods_id");
        count = bundle.getString("count");
    }

    @Override
    protected void initView() {
        xRecyclerView = (XRecyclerView) findViewById(R.id.recyclerView);
        goods_detail_evaluate = (TextView) findViewById(R.id.goods_detail_evaluate);
        goods_detail_evaluate.setText("(" + count + ")");
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
                if (currentPage > pageSize) {
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });

        recyclerAdapter = new BaseRecyclerAdapter<EvaluateAllBean.ContentBean.EvaluateListBean>(getContext(), null, R.layout.item_goods_all_evaluate) {
            @Override
            public void convert(BaseRecyclerHolder holder, EvaluateAllBean.ContentBean.EvaluateListBean data, int position, boolean isScrolling) {
                ImageView people_head_img = holder.getView(R.id.people_head_img);
                int size =  getResources().getDimensionPixelSize(R.dimen.x45);
                GlideEngine.loadThumbnail(getContext().getApplicationContext(),size, R.drawable.goods, people_head_img, data.getHead_image());
                holder.setText(R.id.people_name_tt, data.getUser_name());
                holder.setText(R.id.people_time_tt, data.getAdd_time());
                holder.setText(R.id.evaluate_tt, data.getEvaluate_info());
                holder.setText(R.id.goods_detaim_evaluate_bottom, data.getGoods_spec());
            }
        };
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList(currentPage);
    }


    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        goodPresenter.goodsEvaluateList(getContext(),page, SPUtil.getPrefString("token",""),goods_id,DataType.good.goodsEvaluateList.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if(dataType.equals(DataType.good.goodsEvaluateList.toString())){
            hideDialogLoading();
            EvaluateAllBean evaluateAllBean = (EvaluateAllBean) object;
            complete();
            recyclerAdapter.setData(evaluateAllBean.getContent().getEvaluateList());
            pageSize = evaluateAllBean.getContent().getPages();
            currentPage++;
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
    }
}
