package com.dajukeji.hslz.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.CommonBaseAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.ViewHolder;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.flowlayout.FlowLayout;
import com.dajukeji.hslz.view.flowlayout.TagAdapter;
import com.dajukeji.hslz.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cdr on 2017/12/6.
 * 规格选择弹窗
 */

public class SkuDialog {

    private Context mContext;
    private Dialog mDialog;
    private Display mDisplay;
    private CommonBaseAdapter mAdapter;
    private GoodDetailsBean.ContentBean mDataBean;

    private List<Integer> mSkuList;
    private List<String> mSelectTipsList;
    private String mSkuKey = "";
    private int mNum = 0;
    private int mStock = 0;
    private String mGoodPrice;
    private String goods_id_webActivity = "";

    @BindView(R.id.dialog_spec_select_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.rl_select_number)
    RelativeLayout mSelectNumber;

    @BindView(R.id.dialog_spec_select_pic)
    ImageView mImgPic;

    @BindView(R.id.dialog_spec_select_price)
    TextView mTvPrice;

    @BindView(R.id.dialog_spec_select_stock)
    TextView mTvStock;

    @BindView(R.id.dialog_spec_select_tips)
    TextView mTvSelectTips;

    @BindView(R.id.dialog_spec_select_num)
    TextView mTvNum;

    @BindView(R.id.goods_name)
    TextView goods_name;

    @OnClick({R.id.dialog_spec_select_close, R.id.dialog_spec_select_add, R.id.dialog_spec_select_reduce, R.id.dialog_spec_select_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_spec_select_close:
                this.dismiss();
                break;

            case R.id.dialog_spec_select_reduce:
                if (mStock == 0) return;

                if (mNum - 1 > 0) {
                    mNum--;
                }
                mTvNum.setText(mNum + "");
                break;

            case R.id.dialog_spec_select_add:
                if (mStock == 0) return;

                if (mNum + 1 <= mStock) {
                    mNum++;
                }
                mTvNum.setText(mNum + "");
                break;

            case R.id.dialog_spec_select_ok:
                if (!mDataBean.getGoods_spec_list().isEmpty() && !mSkuList.contains(0)) { //规格配置需要配置对应属性的库存、价格
                    if (mStock == 0) {
                        Toast.makeText(mContext, "产品已无库存", Toast.LENGTH_SHORT).show();
                    } else {
                        StringBuffer buffer = new StringBuffer();
                        String[] skuKey = mSkuKey.split("_");
                        for (int i = 0; i < skuKey.length; i++) {
                            buffer.append(skuKey[i] + ",");
                        }
                        buffer.delete(buffer.length() - 1, buffer.length());
                        mListener.onComplete(mDataBean.getGoods_id(), Integer.parseInt(mTvNum.getText().toString()), buffer.toString(), mGoodPrice);
                        buffer = null;
                        skuKey = null;
                        this.dismiss();
                    }
                } else if (mDataBean.getGoods_spec_list().isEmpty()) { //全局配置表示所有规格无单独库存、价格配置
                    if (mStock == 0) {
                        Toast.makeText(mContext, "产品已无库存", Toast.LENGTH_SHORT).show();
                    } else {
                        mListener.onComplete(mDataBean.getGoods_id(), Integer.parseInt(mTvNum.getText().toString()), "", mGoodPrice);
                        this.dismiss();
                    }
                } else {
                    Toast.makeText(mContext, "请先选择规格", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public SkuDialog(Context context, GoodDetailsBean.ContentBean dataBean) {
        this.mContext = context;
        this.mDataBean = dataBean;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        this.mDisplay = windowManager.getDefaultDisplay();
    }

    public SkuDialog builder() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_sku_select, null);
        ButterKnife.bind(this, view);
        view.setMinimumWidth(mDisplay.getWidth());

        initView();

        // 定义Dialog布局和参数
        mDialog = new Dialog(mContext, R.style.CommonDialogStyle);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        Window dialogWindow = mDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.BottomDialogAnimation);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (mDisplay.getWidth());
        lp.height = (int) (mDisplay.getHeight() * 0.75);
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        // 如果是免单与砍价则不选择产品数量，默认产品数量为一
        if (mDataBean.getZone_type().equals("free") || mDataBean.getZone_type().equals("cutprice")) {
            mSelectNumber.setVisibility(View.GONE);
        }

        return this;
    }

    private void initView() {
        mDataBean.setGoods_details(mDataBean.getGoods_main_photo());
        refreshView(String.valueOf(mDataBean.getGoods_current_price()), mDataBean.getGoods_inventory(), mDataBean.getGoods_details());
        initRecyclerView();
    }

    private void initRecyclerView() {
        goods_name.setText(mDataBean.getGoods_name()); // 产品名称
        if (mDataBean.getGoods_spec_list().isEmpty()) { // 如果是全局产品数量配置
            if (mDataBean.getZone_type().equals("compareprice")) {  //如果为全网比价则购买数量有最大限制
                mStock = mDataBean.getLimit_count();
            } else {
                mStock = mDataBean.getGoods_inventory();
            }
            mGoodPrice = Double.toString(mDataBean.getGoods_current_price());
            mNum = 1;
            refreshView(String.valueOf(mDataBean.getGoods_current_price()), mDataBean.getGoods_inventory(), null);

            return;
        }
        if (mDataBean.getGoods_spec_list() == null || mDataBean.getGoods_spec_list().size() == 0) {
            return;
        }

//        次行代码导致RecyclerView不滚动
//        mRecyclerView.setLayoutManager(new FullyLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false) {
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommonBaseAdapter<GoodDetailsBean.ContentBean.GoodsSpecListBean>(mContext, mDataBean.getGoods_spec_list(), false) {
            @Override
            protected void convert(final ViewHolder holder, final GoodDetailsBean.ContentBean.GoodsSpecListBean data) {
                holder.setText(R.id.item_dialog_sku_name, data.getSpec_name());

                final TagFlowLayout tagFlowLayout = holder.getView(R.id.item_dialog_sku_tagFlowLayout);
                final LayoutInflater inflater = LayoutInflater.from(mContext);
                tagFlowLayout.setAdapter(new TagAdapter<GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean>(data.getSpec_list()){

                    @Override
                    public View getView(FlowLayout parent, int position, GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean specListBean) {
                        TextView tv = (TextView) inflater.inflate(R.layout.item_sku_tagflowlayout, tagFlowLayout, false);
                        tv.setText(specListBean.getItem());
                        return tv;
                    }

                    @Override
                    public void onSelected(int position, View view) {

                        mSkuList.set(holder.getAdapterPosition(), data.getSpec_list().get(position).getItem_id());
                        mSelectTipsList.set(holder.getAdapterPosition(), data.getSpec_list().get(position).getItem());
                        if (!TextUtils.isEmpty(data.getSpec_list().get(position).getSrc())) {
                            GlideEngine.loadThumbnail(mContext, 460, R.drawable.goods, mImgPic, data.getSpec_list().get(position).getSrc());
                        }

                        if (!mDataBean.getGoods_spec_list().isEmpty() && !mSkuList.contains(0)) {
                            List<Integer> tempList = new ArrayList<Integer>();
                            tempList.addAll(mSkuList);
                            Collections.sort(tempList);

                            StringBuffer buffer = new StringBuffer();
                            for (int i = 0; i < tempList.size(); i++) {
                                buffer.append(tempList.get(i) + "_");
                            }
                            buffer.delete(buffer.length() - 1, buffer.length());
                            mSkuKey = buffer.toString();

                            if (mDataBean.getInventory_type().equals("all")) {
                                mStock = mDataBean.getGoods_inventory();
                                mGoodPrice = Double.toString(mDataBean.getGoods_current_price());
                                if (mStock == 0) {
                                    mNum = 0;
                                } else {
                                    mNum = 1;
                                }
                                refreshView(Double.toString(mDataBean.getGoods_current_price()), mDataBean.getGoods_inventory(), null);
                            } else {
                                for (GoodDetailsBean.ContentBean.SpecGoodsPriceBean goodPrice : mDataBean.getSpec_goods_price()) {
                                    //价格比较规则：拼接规格id后比较后台来的spec_goods_price字段，若拼接后的id与其字段相同，则使用后台给的价格。
                                    //由于后台没有传来是否是产品券字段，只是前端自主加上的，需要去掉产品券规格后再比对规格的价格。
                                    if (TextUtils.equals(removeBuySpecGetInfo(mSkuKey), goodPrice.getKey())) {
                                        if (mDataBean.getZone_type().equals("compareprice")) {  //如果为全网比价则购买数量有最大限制
                                            mStock = mDataBean.getLimit_count();
                                        } else {
                                            mStock = Integer.parseInt(goodPrice.getStore_count());
                                        }

                                        mGoodPrice = goodPrice.getPrice();
                                        if (mStock == 0) {
                                            mNum = 0;
                                        } else {
                                            mNum = 1;
                                        }
                                        refreshView(goodPrice.getPrice(), Integer.parseInt(goodPrice.getStore_count()), null);
                                    }
                                }
                            }
                        } else {
                            mSkuKey = "";
                            mNum = 0;
                            mStock = 0;
                            mGoodPrice = "";
                            refreshView(String.valueOf(mDataBean.getGoods_current_price()), mDataBean.getGoods_inventory(), null);
                        }
                    }

                    @Override
                    public void unSelected(int position, View view) {

                        mSkuList.set(holder.getAdapterPosition(), 0);
                        mSelectTipsList.set(holder.getAdapterPosition(), "");
                        GlideEngine.loadThumbnail(mContext, 460, R.drawable.goods, mImgPic, mDataBean.getGoods_details());

                        mSkuKey = "";
                        mNum = 0;
                        mStock = 0;
                        refreshView(String.valueOf(mDataBean.getGoods_current_price()), mDataBean.getGoods_inventory(), null);
                    }

                },mDataBean);

            }

            @Override
            protected int getItemLayoutId() {
                return R.layout.item_dialog_sku;
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public String getGoods_id_webActivity() {
        return goods_id_webActivity;
    }

    public void setGoods_id_webActivity(String goods_id_webActivity) {
        this.goods_id_webActivity = goods_id_webActivity;
    }

    private void refreshView(String price, int stock, String src) {
        if (!TextUtils.isEmpty(src)) {
            GlideEngine.loadThumbnail(mContext, 460, R.drawable.goods, mImgPic, src);
        }

        if (mDataBean.getIsShare() == 1) { // 全网比价分享购买
            mTvPrice.setText(mContext.getResources().getString(R.string.rmb_symbol) + mDataBean.getShare_price());
        } else if (mDataBean.getIsShare() == 2) {
            mTvPrice.setText(mContext.getResources().getString(R.string.rmb_symbol) + mDataBean.getNot_share_price());
        } else if (mDataBean.getZone_type().equals("integral")) {
            mTvPrice.setText(mDataBean.getIntegration() + "省券");
        }else if (mDataBean.getGoods_ststus()==-16){//兑换专区做的更新
            mTvPrice.setText(mDataBean.getGoods_price()+"额度");
        }else if (!goods_id_webActivity.equals("")){//兑换专区做的更新
            mTvPrice.setText(mDataBean.getGoods_price()+"额度");
        } else {
            mTvPrice.setText(mContext.getResources().getString(R.string.rmb_symbol) + price);
        }
        mTvStock.setText("库存" + stock + "件");
        mTvNum.setText(mNum + "");

        if (mDataBean.getGoods_spec_list() == null || mDataBean.getGoods_spec_list().size() == 0) {
            mTvSelectTips.setText("");
            return;
        }

        if (mSkuList == null) {
            mSkuList = new ArrayList<>(mDataBean.getGoods_spec_list().size());
            for (int i = 0; i < mDataBean.getGoods_spec_list().size(); i++) {
                mSkuList.add(0);
            }
        }

        if (mSelectTipsList == null) {
            mSelectTipsList = new ArrayList<>(mDataBean.getGoods_spec_list().size());
            for (int i = 0; i < mDataBean.getGoods_spec_list().size(); i++) {
                mSelectTipsList.add("");
            }
        }

        StringBuffer tipsBuffer = new StringBuffer();
        if (!mSelectTipsList.contains("")) {
            for (String tips : mSelectTipsList) {
                tipsBuffer.append("\"" + tips + "\" ");
            }
            mTvSelectTips.setText("已选: " + tipsBuffer.toString());
        } else {
            for (int i = 0; i < mSelectTipsList.size(); i++) {
                if (TextUtils.isEmpty(mSelectTipsList.get(i))) {
                    tipsBuffer.append(mDataBean.getGoods_spec_list().get(i).getSpec_name() + " ");
                }
            }
            mTvSelectTips.setText("请选择: " + tipsBuffer.toString());
        }
        tipsBuffer = null;
    }

    public SkuDialog setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }

    public SkuDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    private onSelectListener mListener;

    public void setOnSelectListener(onSelectListener listener) {
        this.mListener = listener;
    }

    /**
     * 为了将“购买类型”完美的塞入 SkuDialog，改变数据格式，将后台给的 goodsProductInfo 塞入 goods_spec_list 中。
     */
    public static void addBuySpec(GoodDetailsBean goodDetailsBean) {
        GoodDetailsBean.ContentBean.GoodsProductInfo productInfo = goodDetailsBean.getContent().getGoodsProductInfo();

        if (productInfo != null) {

            GoodDetailsBean.ContentBean.GoodsSpecListBean spec = new GoodDetailsBean.ContentBean.GoodsSpecListBean();
            spec.setSpec_name("购买类型");
            ArrayList<GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean> list = new ArrayList<>();
            //产品
            GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean specItem = new GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean();
            if (productInfo.getStartDeliveryTime() == null) {
                specItem.setItem("产品");
            } else
                specItem.setItem("产品（将于" + productInfo.getStartDeliveryTime() + "左右发货）");
            specItem.setItem_id(-12);
            list.add(specItem);
            //产品券
            if (productInfo.isSellProductTicket()) {
                GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean specItem2 = new GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean();
                if (productInfo.getGoodsCouponEndUseTime() == null)
                    specItem2.setItem("产品券");
                else
                    specItem2.setItem("产品券（兑换有效期至" + productInfo.getGoodsCouponEndUseTime() + "）");
                specItem2.setItem_id(-13);
                list.add(specItem2);
            }

            spec.setSpec_list(list);
            goodDetailsBean.getContent().getGoods_spec_list().add(spec);
        }
    }

    /**
     * 拿到SkuDialog返回的，选择的是哪个购买类型，并从suk中移除
     *
     * @param sukInfo SkuDialog的选中的规格字段
     * @return [0]=购买类型id; [1]=移除购买类型后的规格','分隔
     */
    public static String[] removeBuySpec(String sukInfo) {
        String buySpec = null;

        String[] split = sukInfo.split(",");
        StringBuilder result = new StringBuilder();
        for (String s : split) {
            if ("-12".equals(s) || "-13".equals(s)) {
                buySpec = s;
            } else {
                result.append(",").append(s);
            }
        }
        if (result.length() > 0)
            result.deleteCharAt(0);
        return new String[]{buySpec, result.toString()};
    }

    /**
     * 移除规格id拼接字段中的购买类型，从而正确的比较价格、库存
     */
    public static String removeBuySpecGetInfo(String sukInfo) {
        return sukInfo
                .replace("-12_", "")
                .replace("-13_", "")
                .replace("_-12", "")
                .replace("_-13", "")
                .replace("-12", "")
                .replace("-13", "");
    }

    public interface onSelectListener {
        /**
         * 规格选择完毕回调
         *
         * @param goodId    产品编号
         * @param count     产品数量
         * @param sku       产品规格，逗号隔开
         * @param goodPrice 产品价格
         */
        void onComplete(long goodId, int count, String sku, String goodPrice);
    }

}
