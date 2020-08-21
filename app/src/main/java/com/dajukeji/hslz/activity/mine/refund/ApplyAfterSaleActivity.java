package com.dajukeji.hslz.activity.mine.refund;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.RefundGoodsBean;
import com.dajukeji.hslz.domain.order.ReturnSaveBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.SelectedButton;
import com.dajukeji.hslz.view.dialog.SelectDialog;

import butterknife.BindArray;

/**
 * 申请售后退款
 * 申请售后页面
 */

public class ApplyAfterSaleActivity extends HttpBaseActivity {

    private RefundOrderPresenter refundOrderPresenter;
    private LinearLayout apply_afterSale_topStatus; // 头部状态

    @BindArray(R.array.apply_after_apply_status_array)
    String mStatusArray[];

    private LinearLayout ll_apply_after_sale;
    private TextView tv_return_reason;
    private RelativeLayout apply_after_sale_reason; // 理由
    private SelectedButton apply_after_sale_submit; // 提交
    private TextView tv_one_deduct_amount; //  	单个产品省券抵扣金额
    private TextView apply_after_sale_refundAmount;
    private SelectDialog dialog; // 理由弹窗
    private EditText et_return_reason; //退货原因

    private ImageView item_order_list_img; // 产品图片
    private TextView item_order_list_name; // 产品名称
    private TextView item_order_list_good_sku; //产品规格
    private TextView item_order_list_present_price; // 产品价格
    private TextView tv_goods_count; // 产品数量
    private RefundGoodsBean mrefundGoodsBean;
    private long gc_id;
    private long gr_id;
    private int reson_id; // 退款原因ID
    private int count; //数量

    private String goods_id_webActivity = "";

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_after_sale);
        refundOrderPresenter = new RefundOrderPresenter(this);
        //兑换专区的更新
        goods_id_webActivity = getIntent().getStringExtra("goods_id_webActivity");
        if (goods_id_webActivity == null){
            goods_id_webActivity = "";
        }
    }

    @Override
    protected void initView() {
        setTitleBar(R.string.text_apply_after_sale, true);
        Bundle bundle = getIntent().getExtras();
        gc_id = bundle.getLong("gc_id");
        gr_id = bundle.getLong("gr_id");
        if (gr_id == 0.0) {
            refundOrderPresenter.refundInit(getContext(), SPUtil.getPrefString("token", ""), gc_id + "", "", DataType.myRefund.refundInit.toString());
        } else {
            refundOrderPresenter.refundInit(getContext(), SPUtil.getPrefString("token", ""), gc_id + "", gr_id + "", DataType.myRefund.refundInit.toString());
        }
        ll_apply_after_sale = (LinearLayout) findViewById(R.id.ll_apply_after_sale);
        tv_return_reason = (TextView) findViewById(R.id.tv_return_reason);
        apply_afterSale_topStatus = (LinearLayout) findViewById(R.id.apply_afterSale_topStatus);
        apply_after_sale_reason = (RelativeLayout) findViewById(R.id.apply_after_sale_reason);
        apply_after_sale_submit = (SelectedButton) findViewById(R.id.apply_after_sale_submit);
        apply_after_sale_refundAmount = (TextView) findViewById(R.id.apply_after_sale_refundAmount);
        item_order_list_img = (ImageView) findViewById(R.id.item_order_list_img);
        item_order_list_name = (TextView) findViewById(R.id.item_order_list_name);
        item_order_list_good_sku = (TextView) findViewById(R.id.item_order_list_good_sku);
        item_order_list_present_price = (TextView) findViewById(R.id.item_order_list_present_price);
        tv_goods_count = (TextView) findViewById(R.id.tv_goods_count);
        tv_one_deduct_amount = (TextView) findViewById(R.id.tv_one_deduct_amount);
        et_return_reason = (EditText) findViewById(R.id.et_return_reason);

        apply_after_sale_reason.setOnClickListener(this);
        apply_after_sale_submit.setOnClickListener(this);
        initTopStatus();
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.myRefund.refundInit.toString())) {
            RefundGoodsBean refundGoodsBean = (RefundGoodsBean) object;
            if (refundGoodsBean != null && refundGoodsBean.getContent() != null) {
                this.mrefundGoodsBean = refundGoodsBean;
                this.count = refundGoodsBean.getContent().getCount();
                ll_apply_after_sale.setVisibility(View.VISIBLE);
                tv_one_deduct_amount.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", refundGoodsBean.getContent().getDeduct_amount()));

                //兑换区的更新
                if (!goods_id_webActivity.equals("")){
                    apply_after_sale_refundAmount.setText("额" + String.format("%.2f", (refundGoodsBean.getContent().getCount() * refundGoodsBean.getContent().getPrice())));
                }else {
                    apply_after_sale_refundAmount.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", (refundGoodsBean.getContent().getCount() * refundGoodsBean.getContent().getPrice())));
                }

                if (refundGoodsBean.getContent().getReasonList().get(0).getRefund_reason() != null && !refundGoodsBean.getContent().getReasonList().get(0).getRefund_reason().equals("")) {
                    tv_return_reason.setText(refundGoodsBean.getContent().getReasonList().get(0).getRefund_reason());
                    reson_id = refundGoodsBean.getContent().getReasonList().get(0).getRefund_id();
                }
                int size = getResources().getDimensionPixelSize(R.dimen.x260);
                GlideEngine.loadThumbnail(getContext().getApplicationContext(), size, R.drawable.goods, item_order_list_img, refundGoodsBean.getContent().getIcon());
                item_order_list_name.setText(refundGoodsBean.getContent().getGoods_name());
                item_order_list_good_sku.setText(refundGoodsBean.getContent().getSpec_info());

                if (!goods_id_webActivity.equals("")){//兑换区更新
                    item_order_list_present_price.setText("额" + String.format("%.2f", refundGoodsBean.getContent().getPrice()));
                }else {
                    item_order_list_present_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", refundGoodsBean.getContent().getPrice()));
                }

                tv_goods_count.setText(refundGoodsBean.getContent().getCount() + "");
            } else {
                ll_apply_after_sale.setVisibility(View.GONE);
            }
        } else if (contentType.equals(DataType.myRefund.saveRefund.toString())) {
            hideDialogLoading();
            ReturnSaveBean returnSaveBean = (ReturnSaveBean) object;
            showToast("申请成功");
            Intent intent = new Intent(getContext(), ApplyAfterSaleDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("gr_id", returnSaveBean.getContent().getGr_id());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        //兑换专区的更新
        if (!goods_id_webActivity.equals("")){
            ((LinearLayout)findViewById(R.id.zhekou)).setVisibility(View.GONE);
        }

    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.apply_after_sale_reason: // 理由

                if (mrefundGoodsBean==null||mrefundGoodsBean.getContent()==null||mrefundGoodsBean.getContent().getReasonList()==null){
                    return;
                }

                dialog = new SelectDialog(this, mrefundGoodsBean, "refund").builder();
                dialog.show();
                dialog.setOnBackListener(new SelectDialog.onBackListener() {
                    @Override
                    public void selectResult(String result, int id) {
                        tv_return_reason.setText(result);
                        reson_id = id;
                    }
                });
                break;
            case R.id.apply_after_sale_submit:
                refundOrderPresenter.saveRefund(getContext(), SPUtil.getPrefString("token", ""), et_return_reason.getText().toString(), gc_id + "", count + "", reson_id + "", "1", DataType.myRefund.saveRefund.toString());
                showDialogLoading();
                break;
        }

    }

    /**
     * 初始化顶部申请售后状态进度
     */
    private void initTopStatus() {
        for (int i = 0; i < mStatusArray.length; i++) {
            apply_afterSale_topStatus.addView(addView(i));
        }
    }

    private View addView(int i) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_apply_after_sale_top, null);

        TextView mTv = (TextView) view.findViewById(R.id.item_apply_afterSale_text);
        ImageView mImg = (ImageView) view.findViewById(R.id.item_apply_afterSale_arrow);

        mTv.setText(mStatusArray[i]);
        if (i == 0) {
            mTv.setTextColor(getResources().getColor(R.color.status_green_color));
        }

        if (i == mStatusArray.length - 1) {
            mImg.setVisibility(View.GONE);
        }

        view.setLayoutParams(lp);
        return view;
    }


}
/*
申请售后逻辑：
当订单详情接口中（app/mall/order/orderDetail.htm）， 订单的 refund_type 为可退货标志时（退款类型：0为不能退款退货，1为能退款，2为能退货）
可点击退货按钮到退货页面，通过退货信息接口（app/mall/refund/init.htm）得到退货理由、金额等信息。
选择退货理由与填写具体商品问题后，请求退货（app/mall/return/save.htm?token=[oPJ8m5wamt2293FXoTGkI0fy-KRQ]&gc_id=[234151]&info=[问题・_・?]&count=[1]&reason=[9]&service=[1]）
退货申请成功后请求展示售后信息（app/mall/refund/refund_await.htm）
进入退款/售后状态的商品可在个人页面查看列表(app/mall/refund/all.htm)，可点击查看退款详情（app/mall/return/return_see.htm）
 */