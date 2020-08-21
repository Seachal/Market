package com.dajukeji.hslz.activity.brandmarcket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.SortAdapter;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.BigBrandBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.view.SideBar;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandListActivity extends HttpBaseActivity {

    private RecyclerView recyclerView;
//    private BaseRecyclerAdapter<BigBrandBean.ContentBean.BigBrandListBean> mAdapter;
    private BrandPresenter brandPresenter;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter sortAdapter;
    private LinearLayoutManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        brandPresenter = new BrandPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_first_letter_list);
        dialog = (TextView) findViewById(R.id.dialog);
        setTitleBar(R.string.all_brand, true);
        sideBar = (SideBar) findViewById(R.id.sideBar);
        sideBar.setTextView(dialog);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        sortAdapter = new SortAdapter(getContext(), getLayoutInflater(), null);
        recyclerView.setAdapter(sortAdapter);
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                dialog.setText(s);
                int position = sortAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
//        sortAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//
//            }
//        });
    }

    @Override
    protected void initData() {
        super.initData();
        brandPresenter =new BrandPresenter(this);
        brandPresenter.getBigBrandList(BrandListActivity.this, -1, DataType.brand.bigBrandList.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.brand.bigBrandList.toString())) {
            BigBrandBean bigBrandBean = (BigBrandBean) object;
            sortAdapter.updateList(bigBrandBean.getContent().getBig_brand_list());
        }
    }

}
