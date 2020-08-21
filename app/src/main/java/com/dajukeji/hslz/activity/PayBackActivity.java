package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.network.presenter.BrandPresenter;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class PayBackActivity extends HttpBaseActivity {

    private BrandPresenter brandPresenter;
    private XRecyclerView xRecyclerView;
    private View headerView;
    private TextView order_money;
    boolean payback;

//    @Override
//    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
//        setContentView(R.layout.payback_header);
//        xRecyclerView = (XRecyclerView) findViewById(R.id.goods_detail_recommend_recycle);
//        headerView = LayoutInflater.from(getContext()).inflate(R.layout.payback_header, null);
//        findViewById(R.id.see_order).setOnClickListener(this);
//        findViewById(R.id.return_index).setOnClickListener(this);
//        payback = getIntent().getBooleanExtra(Constants.payback, false);
//        if (payback) {
//            order_money.setText("支付成功");
//        } else {
//            order_money.setText("支付失败");
//        }
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payback_header);
        xRecyclerView = (XRecyclerView) findViewById(R.id.goods_detail_recommend_recycle);
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.payback_header, null);
        findViewById(R.id.see_order).setOnClickListener(this);
        findViewById(R.id.return_index).setOnClickListener(this);
        payback = getIntent().getBooleanExtra(Constants.payback, false);
        order_money = (TextView) findViewById(R.id.order_money);
        if (payback) {
            order_money.setText("支付成功");
        } else {
            order_money.setText("支付失败");
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.see_order:

                break;
            case R.id.return_index:

                break;
        }
    }
}
