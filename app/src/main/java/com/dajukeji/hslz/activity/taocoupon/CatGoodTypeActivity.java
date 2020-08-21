package com.dajukeji.hslz.activity.taocoupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.coupon.CatCouponType;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.CouponPresenter;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * 猫券目录
 * */

public class CatGoodTypeActivity extends HttpBaseActivity {

    private CouponPresenter couponPresenter;
    private RecyclerView mRecylerView ;
    private LinearLayout search_bar;
    private BaseRecyclerAdapter<CatCouponType.ContentBean.DataListBean> recyclerAdapter;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_cat_good_type);
        setTitleBar(R.string.text_search,true);
        couponPresenter = new CouponPresenter(this);
        couponPresenter.getCatTypeList(getContext(), DataType.coupon.getTypeList.toString());
    }

    @Override
    protected void initView() {
        mRecylerView = (RecyclerView) findViewById(R.id.cat_coupon_goodType_list);
        search_bar = (LinearLayout) findViewById(R.id.search_bar);
        search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CatGoodTypeActivity.this, ShopFindActivity.class));
            }
        });
        mRecylerView.setLayoutManager(new GridLayoutManager(getContext(),4));
        recyclerAdapter =new BaseRecyclerAdapter<CatCouponType.ContentBean.DataListBean>(getContext(),null,R.layout.item_find_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, CatCouponType.ContentBean.DataListBean data, int position, boolean isScrolling) {
                int  size = getResources().getDimensionPixelSize(R.dimen.x210);
                int  mPadding = getResources().getDimensionPixelSize(R.dimen.x30);
                ImageView mImg = holder.getView(R.id.find_goods_imageView);
                TextView mText = holder.getView(R.id.find_goods_textView);
                mText.setText(data.getNAME());
                GlideEngine.loadThumbnail(getContext(), size, R.drawable.goods, mImg, data.getICON());
                if((position+1) % 4 == 1 ){
                    holder.itemView.setPadding(mPadding, 0, 0, 0);
                }else if((position+1) % 4 == 2 ){
                    holder.itemView.setPadding(mPadding, 0, mPadding, 0);
                }else if((position+1) % 4 == 3 ){
                    holder.itemView.setPadding(0, 0, mPadding, 0);
                }else if((position+1) % 4 == 0 ){
                    holder.itemView.setPadding(0, 0, mPadding, 0);
                }
            }
        };
       recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<CatCouponType.ContentBean.DataListBean>() {
           @Override
           public void onItemClick(BaseRecyclerHolder viewHolder, CatCouponType.ContentBean.DataListBean data, int position) {
               Intent intent = new Intent(CatGoodTypeActivity.this,CouponGoodsListActivity.class);
               intent.putExtra("cat",data.getCATID_LIST());
               intent.putExtra("name",data.getNAME());
               startActivity(intent);
           }
       });
        mRecylerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.coupon.getTypeList.toString())){
            CatCouponType catCouponType = (CatCouponType)object;
            recyclerAdapter.setData(catCouponType.getContent().getDataList());
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
