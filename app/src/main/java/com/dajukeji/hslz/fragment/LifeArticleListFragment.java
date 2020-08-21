package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ArticleWebActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.HealthArticleBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.ArticlePresenter;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class LifeArticleListFragment extends HttpBaseFragment {

    private XRecyclerView xRecyclerView;
    private ArticlePresenter articlePresenter;
    private BaseRecyclerAdapter<HealthArticleBean.ContentEntity.ArticleListEntity> recyclerAdapter;

    private boolean isFirstPage = true;
    private int currentPage = 1;
    private int pageSize = 1;

    public LifeArticleListFragment(){
        articlePresenter = new ArticlePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.xrecycler_layout, null);
        }
        initView(rootView);
        return rootView;
    }


    private void initView(View v){
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        recyclerAdapter = new BaseRecyclerAdapter<HealthArticleBean.ContentEntity.ArticleListEntity>(getContext(), null, R.layout.item_health_know) {
            @Override
            public void convert(BaseRecyclerHolder holder, final HealthArticleBean.ContentEntity.ArticleListEntity data, int position, boolean isScrolling) {
                holder.setText(R.id.health_know_time, data.getAddTime());
                holder.setText(R.id.health_know_title, data.getTitle());
                ImageView health_know_image =   holder.getView( R.id.health_know_image);
                holder.setImageWithGlide(getActivity(),health_know_image,data.getIcon());
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<HealthArticleBean.ContentEntity.ArticleListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, HealthArticleBean.ContentEntity.ArticleListEntity data, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("weblocation", data.getUrl());
                bundle.putString("title", data.getTitle());
                Intent intent = new Intent(getContext().getApplicationContext(), ArticleWebActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        articlePresenter.lifeArticleList(getContext(),page,DataType.myPresenterType.getHealthArticleList.toString());
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.myPresenterType.getHealthArticleList.toString())) {
            hideDialogLoading();
            HealthArticleBean healthArticleBean = (HealthArticleBean) object;
            complete();
            recyclerAdapter.setData(healthArticleBean.getContent().getArticleList());
            pageSize = healthArticleBean.getContent().getPages();
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
        OkGo.getInstance().cancelTag(getActivity());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }
}
