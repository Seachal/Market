package com.dajukeji.hslz.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.CouponBean;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

public class IndextwFragment extends HttpBaseFragment {
    private XRecyclerView xRecyclerView;
    private List<CouponBean.DataBean.TransactionlistBean.ItemsBean> list;
    private BaseRecyclerAdapter<CouponBean.DataBean.TransactionlistBean.ItemsBean> recyclerAdapter;
    private int userId;
    private int pageNum = 1;
    private int pageSize = 10;
    private int flowType = 1;
    private GoodPresenter goodPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.xrecycler_layout, container, false);
        goodPresenter = new GoodPresenter(this);
        userId = SPUtil.getPrefInt("userId", 0);
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
//        xRecyclerView.setPullRefreshEnabled(false);
//        xRecyclerView.setLoadingMoreEnabled(false);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                goodPresenter.getCashCoupon(getContext(), String.valueOf(pageNum), pageSize + "", flowType, DataType.good.getCoupon.toString());
            }

            @Override
            public void onLoadMore() {
                goodPresenter.getCashCoupon(getContext(), String.valueOf(pageNum), pageSize + "", flowType, DataType.good.getCoupon.toString());
            }
        });
        if (recyclerAdapter == null)
            recyclerAdapter = new BaseRecyclerAdapter<CouponBean.DataBean.TransactionlistBean.ItemsBean>(getContext(), list, R.layout.fragment_index_two) {
                @Override
                public void convert(BaseRecyclerHolder holder, CouponBean.DataBean.TransactionlistBean.ItemsBean data, int position, boolean isScrolling) {
                    TextView paint = holder.getView(R.id.paint);
                    TextView money = holder.getView(R.id.money);
                    TextView price = holder.getView(R.id.price);
                    TextView time = holder.getView(R.id.time);
                    paint.setText(data.getTitle());
                    time.setText(data.getPayTime());
                    money.setText(String.format("余额：%.5f", data.getBalance()));
                    price.setText(String.format((data.getFlowType() >= 0 ? "+" : "-") + "%.5f", data.getPayAmount() * 1.0));
                }
            };
        xRecyclerView.setAdapter(recyclerAdapter);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        pageNum = 1;
        goodPresenter.getCashCoupon(getContext(), pageNum + "", pageSize + "", flowType, DataType.good.getCoupon.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        CouponBean couponBean = (CouponBean) object;
        pageNum = couponBean.getData().getTransactionlist().getPageNum() + 1;

        //到底否
        if (pageNum <= couponBean.getData().getTransactionlist().getPageTotal()) {//未到底
            xRecyclerView.setLoadingMoreEnabled(true);
        } else {//已到底
            xRecyclerView.setLoadingMoreEnabled(false);
        }

        //数据更新
        list = couponBean.getData().getTransactionlist().getItems();
        if (pageNum == 2) {//下拉刷新
            recyclerAdapter.setNewData(list);
        } else {//上拉加载
            recyclerAdapter.setData(list);
        }

        //恢复加载中
        xRecyclerView.refreshComplete();
        xRecyclerView.loadMoreComplete();
    }
}
