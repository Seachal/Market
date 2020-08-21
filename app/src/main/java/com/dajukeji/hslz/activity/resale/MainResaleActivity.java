package com.dajukeji.hslz.activity.resale;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ExchangeSecuritiesActivity;
import com.dajukeji.hslz.activity.NewVoucherBean;
import com.dajukeji.hslz.activity.VoucherHelpActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.ResaleListBean;
import com.dajukeji.hslz.network.presenter.ResaleListPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转卖场首页
 */
public class MainResaleActivity extends HttpBaseActivity {

    @BindView(R.id.recyclerView)
    XRecyclerView recyclerView;
    private BaseRecyclerAdapter<MainResaleBean> adapter;
    private ResaleListPresenter presenter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = new ResaleListPresenter(this);
        setContentView(R.layout.activity_main_resale);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.refresh();
    }

    @Override
    protected void initView() {
        //标题栏
        setTitleBar("转卖市场", true);
        findViewById(R.id.iv_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainResaleActivity.this, VoucherHelpActivity.class));
            }
        });
        //列表
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseRecyclerAdapter<MainResaleBean>(this, null, R.layout.item_resale_main) {
            @Override
            public void convert(BaseRecyclerHolder holder, final MainResaleBean data, int position, boolean isScrolling) {
                data.bean.bondHolder(getContext(), holder);
                ((TextView) holder.getView(R.id.tv_title_voucher)).setText(data.voucherPrice);
                //购买
                holder.getView(R.id.tv_buy).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 购买
                        Intent intent = new Intent(MainResaleActivity.this, ExchangeSecuritiesActivity.class);
                        intent.putExtra("securities_id", data.bean.securities_id);
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                recyclerView.refreshComplete();
                recyclerView.loadMoreComplete();
                presenter.getResaleList(this, page, "列表数据");
            }

            @Override
            public void onLoadMore() {
                presenter.getResaleList(this, page, "列表数据");
            }
        });
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "列表数据":
                ResaleListBean listBean = (ResaleListBean) object;
                if (page <= listBean.getContent().getPages())
                    page++;
                //服务器data -> UI data
                List<MainResaleBean> list = new ArrayList<>();
                for (ResaleListBean.ContentBean.TfGoodsListBean bean : listBean.getContent().getTfGoodsList()) {
                    MainResaleBean e = new MainResaleBean();
                    e.voucherPrice = "" + bean.getTransfer_price();//转买价
                    e.bean.voucher = "" + bean.getGoods_price();//原价
                    e.bean.goodsImg = bean.getGoods_main_photo();//图标
                    e.bean.goodsSpec = bean.getSpec_info();
                    e.bean.count = bean.getCount();
                    e.bean.goodsName = bean.getGoods_name();
                    //TODO
                    list.add(e);
                }
                break;
        }
    }

    @OnClick({R.id.iv_nav_center, R.id.click_nav_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_nav_center:
                //TODO 选择出售
                break;
            case R.id.click_nav_right:
                //TODO 交易记录
                break;
        }
    }

    private static class MainResaleBean {
        NewVoucherBean bean = new NewVoucherBean();
        String voucherPrice;//券
    }

}
