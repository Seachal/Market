package com.dajukeji.hslz.activity;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;

public class NewVoucherBean {
    //这些字段可以自定义来便捷传递用
    public int goods_id;
    public int securities_id;

    public String title;
    public String goodsName;
    public String goodsImg;
    public String goodsSpec;
    public int count;
    public String goodsOutTime;
    public String voucher;//券价格

    public void bondHolder(Context context, BaseRecyclerHolder holder) {
        NewVoucherBean data = this;

        ((TextView) holder.getView(R.id.tv_title)).setText(data.title);
        Glide.with(context).load(data.goodsImg).into((ImageView) holder.getView(R.id.iv_goodsImg));
        ((TextView) holder.getView(R.id.tv_goodsName)).setText(data.goodsName);
        ((TextView) holder.getView(R.id.tv_goodsSpec)).setText(data.goodsSpec);
        ((TextView) holder.getView(R.id.tv_count)).setText("×" + data.count);
        ((TextView) holder.getView(R.id.tv_outTime)).setText(data.goodsOutTime);
        ((TextView) holder.getView(R.id.tv_voucher)).setText("券" + data.voucher);
    }
}