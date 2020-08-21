package com.dajukeji.hslz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreListActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.BigBrandBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/3 0003.
 */

public class SortAdapter extends RecyclerView.Adapter<SortAdapter.ViewHolder> {
    private Context context;
    private List<BigBrandBean.ContentBean.BigBrandListBean> mData;
    private LayoutInflater mInflater;
    public SortAdapter(Context context, LayoutInflater layoutInflater, List<BigBrandBean.ContentBean.BigBrandListBean> datas) {
        this.context = context;
        mData = datas;
        this.mInflater = layoutInflater;
    }
    @Override
    public SortAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_brand_shop_name, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.store_name_tt = (TextView) view.findViewById(R.id.store_name_tt);
        holder.first_letter_tt = (TextView) view.findViewById(R.id.first_letter_tt);
        holder.store_name_rl = (RelativeLayout) view.findViewById(R.id.store_name_rl);
        holder.first_letter_rl = (RelativeLayout) view.findViewById(R.id.first_letter_rl);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SortAdapter.ViewHolder holder, final int position) {
        holder.store_name_tt.setText(mData.get(position).getBrand_name());
//        holder.first_letter_tt.setText(mData.get(position).getFirst_letter());
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            holder.first_letter_rl.setVisibility(View.VISIBLE);
            holder.first_letter_tt.setText(mData.get(position).getFirst_letter());
        } else {
            holder.first_letter_rl.setVisibility(View.GONE);
        }
        holder.store_name_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BrandStoreListActivity.class);
                intent.putExtra(Constants.brand_id, mData.get(position).getBrand_id());
                intent.putExtra(Constants.brand_name, mData.get(position).getBrand_name());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView store_name_tt, first_letter_tt;
        RelativeLayout first_letter_rl, store_name_rl;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void updateList(List<BigBrandBean.ContentBean.BigBrandListBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public Object getItem(int pos) {
        return mData.get(pos);
    }

    /**
     * 获取对应位置的首字母char ascii值
     * @param position
     * @return
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getFirst_letter().charAt(0);
    }

    /**
     * 根据首字母的char ascii值获取第一次出现该字母的位置
     * @param section
     * @return
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i ++) {
            char c = mData.get(i).getFirst_letter().charAt(0);
            if (section == c) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取首次出现position
     * @return
     */
    public int letterFirstPosition(int section) {
        for (int i = 0; i < getItemCount(); i ++) {
            char c = mData.get(i).getFirst_letter().charAt(0);
            if (section == c) {
                return i;
            }
        }
        return -1;
    }
}
