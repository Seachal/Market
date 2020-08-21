package com.dajukeji.hslz.fragment.mallFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ComparePriceGoodDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.comparePrice.PlanGoodsListBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.ComparePricePresenter;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.MyProgressBar;
import com.dajukeji.hslz.view.SnapUpCountDownTimerView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 全网比价产品
 * <p/>
 * interface.
 */
@SuppressLint("ValidFragment")
public class WholePriceFragment extends HttpBaseFragment {

    private ComparePricePresenter comparePricePresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;

    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<PlanGoodsListBean.ContentBean.OnePlanGoodsListBean> recyclerAdapter;
    private int pageSize;
    private long plan_id; // 计划ID
    private int left_second; // 开始或结束时间毫秒数
    private int index; ///当前计划位置
    private View headerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.plan_id = getArguments().getLong("plan_id");
        this.left_second = getArguments().getInt("left_second");
        this.index = getArguments().getInt("index");
        comparePricePresenter = new ComparePricePresenter(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.xrecycler_order_layout, container, false);
        }
        headerView = inflater.inflate(R.layout.item_whole_price_limit,null);
        initView(rootView);
        return rootView;
    }


    private void initView(View v) {
        ll_empty_order = (LinearLayout) v.findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) v.findViewById(R.id.tv_order_empty);
        im_empty = (ImageView) v.findViewById(R.id.im_empty);
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        initHeaderView();
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
        recyclerAdapter = new BaseRecyclerAdapter<PlanGoodsListBean.ContentBean.OnePlanGoodsListBean>(getContext(), null, R.layout.item_whole_price) {
            @Override
            public void convert(BaseRecyclerHolder holder, PlanGoodsListBean.ContentBean.OnePlanGoodsListBean data, int position, boolean isScrolling) {
                TextView item_title = holder.getView(R.id.item_title);
                item_title.setText(data.getGoods_name());

                TextView final_price = holder.getView(R.id.final_price);
                final_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getGoods_current_price()));

                TextView price = holder.getView(R.id.price);
                price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getGoods_price()));
                price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );

                ImageView pict_url =  holder.getView(R.id.pict_url);
                int width =  getResources().getDimensionPixelSize(R.dimen.x400);
                int height =  getResources().getDimensionPixelSize(R.dimen.y400);
                GlideEngine.loadThumbnails(getContext(),width,height, R.drawable.goods, pict_url, data.getImage());
                MyProgressBar rest_pro =   holder.getView(R.id.rest_pro);
                if(rest_pro!=null){
                    rest_pro.setMax(data.getMax_goods_inventory());
                    rest_pro.setProgress(data.getGoods_inventory());
                }
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<PlanGoodsListBean.ContentBean.OnePlanGoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, PlanGoodsListBean.ContentBean.OnePlanGoodsListBean data, int position) {
                Intent intent = new Intent(getContext().getApplicationContext(), ComparePriceGoodDetailActivity.class);
                intent.putExtra("goods_id",data.getGoods_id());
                startActivity(intent);
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * 头部倒计时
     */
    private void initHeaderView(){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headerView.setLayoutParams(layoutParams);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String hms = formatter.format(left_second);
        String[] times =hms.split(":");
        SnapUpCountDownTimerView snap_time = (SnapUpCountDownTimerView) headerView.findViewById(R.id.snap_time);
        snap_time.setTime(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));
        snap_time.start();

        TextView tv_time_text = (TextView) headerView.findViewById(R.id.tv_time_text);
        if(index!=0){
            tv_time_text.setText("离本场开始还有:");
        }
        xRecyclerView.addHeaderView(headerView);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        showDialogLoading();
        comparePricePresenter.getGoodsList(getContext(), page,plan_id , DataType.good.getGoodList.toString());
    }


    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.good.getGoodList.toString())) {
            hideDialogLoading();
            PlanGoodsListBean planGoodsListBean = (PlanGoodsListBean) object;
            complete();
            if(planGoodsListBean.getContent().getOne_plan_goods_list().isEmpty()){
                ll_empty_order.setVisibility(View.VISIBLE);
                tv_order_empty.setText("暂无产品");
                im_empty.setVisibility(View.GONE);
                xRecyclerView.setVisibility(View.GONE);
            }else {
                recyclerAdapter.setData(planGoodsListBean.getContent().getOne_plan_goods_list());
                pageSize = planGoodsListBean.getContent().getPages();
                currentPage++;
            }
        }
    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getActivity());
    }
}
