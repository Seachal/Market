package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.order.MyVoucherBean;
import com.dajukeji.hslz.network.presenter.MyVoucherPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品券页面
 */
public class GoodsVoucherActivity extends HttpBaseActivity {

    private int page = 1;
    private MyVoucherPresenter presenter;
    private XRecyclerView recyclerView;
    private BaseRecyclerAdapter<GoodsVoucherBean> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_voucher);
        super.onCreate(savedInstanceState);
        setTitleBar("我的产品券", true);

        presenter = new MyVoucherPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView.refresh();
    }

    @Override
    protected void initView() {
        super.initView();

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseRecyclerAdapter<GoodsVoucherBean>(this, null, R.layout.item_my_goods_voucher) {
            @Override
            public void convert(BaseRecyclerHolder holder, final GoodsVoucherBean data, int position, boolean isScrolling) {
                data.bean.bondHolder(getContext(), holder);
                //状态
                holder.getView(R.id.tv_state_exchange).setVisibility(View.GONE);
                holder.getView(R.id.tv_state_resale).setVisibility(View.GONE);
                holder.getView(R.id.tv_state_expired).setVisibility(View.GONE);
                holder.getView(R.id.tv_state_exchanged).setVisibility(View.GONE);
                switch (data.state) {
                    case 0:
                        holder.getView(R.id.tv_state_exchange).setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        holder.getView(R.id.tv_state_resale).setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        holder.getView(R.id.tv_state_expired).setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        holder.getView(R.id.tv_state_exchanged).setVisibility(View.VISIBLE);
                        break;
                }
                //兑换
                holder.getView(R.id.tv_state_exchange).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(GoodsVoucherActivity.this, ExchangeSecuritiesActivity.class);
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
                presenter.getMyVoucherList(this, page, SPUtil.getPrefString("token", ""), "本人产品券");
            }

            @Override
            public void onLoadMore() {
                presenter.getMyVoucherList(this, page, SPUtil.getPrefString("token", ""), "本人产品券");
            }
        });

        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodsVoucherBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, GoodsVoucherBean data, int position) {
                Intent intent = new Intent(GoodsVoucherActivity.this, NormalGoodDetailActivity.class);
                intent.putExtra("goods_id", data.bean.goods_id);
                intent.putExtra(Constants.is_brand_good, false);
                startActivity(intent);
            }
        });

        //帮助
        findViewById(R.id.iv_help).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GoodsVoucherActivity.this, VoucherHelpActivity.class));
            }
        });
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "本人产品券":
                MyVoucherBean voucherBean = (MyVoucherBean) object;
                if (voucherBean.getStatus().equals("1")) {//无数据
                    //TODO 空视图
                    recyclerView.refreshComplete();
                    recyclerView.loadMoreComplete();

                } else {
                    page++;
                    if (page <= voucherBean.getContent().getPages()) {//未到底
                        recyclerView.setLoadingMoreEnabled(true);
                    } else {//已到底
                        recyclerView.setLoadingMoreEnabled(false);
                    }

                    //data -> view
                    List<GoodsVoucherBean> list = new ArrayList<>();
                    for (MyVoucherBean.ContentBean.TfGoodsListBean bean : voucherBean.getContent().getTfGoodsList()) {
                        GoodsVoucherBean vBean = new GoodsVoucherBean();
                        vBean.bean.goods_id = bean.getGoods_id();
                        vBean.bean.securities_id = bean.getSecurities_id();
                        vBean.bean.title = bean.getStore_name();
                        switch (bean.getSecurities_status()) {
                            case 0:
                                vBean.state = 2;
                                break;
                            case 1:
                                vBean.state = 0;
                                break;
                            case 2:
                                vBean.state = 3;
                                break;
                            case 3:
                                vBean.state = 1;
                                break;
                        }
                        vBean.bean.goodsName = bean.getGoods_name();
                        vBean.bean.goodsImg = bean.getGoods_main_photo();
                        vBean.bean.goodsSpec = bean.getSpec_info();
                        vBean.bean.count = bean.getCount();
                        vBean.bean.goodsOutTime = "到期时间:"+ bean.getValid_start_date() + "至" + bean.getValid_end_date();
                        vBean.bean.voucher = "" + bean.getTransfer_price();
//                    String lostVoucher;

                        list.add(vBean);
                    }
                    if (page == 2) {//只是刷新
                        adapter.setNewData(list);
                    } else {//是下拉更多
                        adapter.setData(list);
                    }
                    recyclerView.refreshComplete();
                    recyclerView.loadMoreComplete();
                }
                break;
        }
    }

    private final static class GoodsVoucherBean {
        NewVoucherBean bean = new NewVoucherBean();
        int state;//0=可兑换 1=转卖中 2=已过期 3=已兑换
    }
}
