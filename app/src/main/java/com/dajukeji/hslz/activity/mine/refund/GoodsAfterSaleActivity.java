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
import com.dajukeji.hslz.domain.order.ReturnGoodsBean;
import com.dajukeji.hslz.domain.order.ReturnSaveBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.AmountView;
import com.dajukeji.hslz.view.SelectedButton;
import com.dajukeji.hslz.view.dialog.SelectDialog;

import butterknife.BindArray;

/**
 * 申请退货品
 */

public class GoodsAfterSaleActivity extends HttpBaseActivity {

    private RefundOrderPresenter refundOrderPresenter;

    private LinearLayout apply_afterSale_topStatus; // 头部状态

    @BindArray(R.array.apply_after_sale_status_array)
    String mStatusArray[];

    private TextView tv_return_reason;
    private RelativeLayout apply_after_sale_reason; // 理由
    private SelectedButton apply_after_sale_submit; // 提交
    private AmountView amount_view; // 退货数量
    private TextView tv_one_deduct_amount; //  	单个产品省券抵扣金额
    private SelectDialog dialog; // 理由弹窗
    private TextView apply_after_sale_refundAmount; // 退款金额
    private TextView apply_after_sale_notPass_tips; // 退货数量
    private EditText et_return_reason; //退货原因

    private ReturnGoodsBean mreturnGoodsBean;
    private long gc_id;
    private long gr_id;
    private int reson_id; // 退款原因ID
    private int count; // 退货数量

    private String goods_id_webActivity = "";

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_after_sale);
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
        if(gr_id==0.0){
            refundOrderPresenter.to_apply(getContext(), SPUtil.getPrefString("token",""),gc_id+"","", DataType.myRefund.to_apply.toString());
        }else {
            refundOrderPresenter.to_apply(getContext(), SPUtil.getPrefString("token",""),gc_id+"",gr_id+"", DataType.myRefund.to_apply.toString());
        }

        tv_return_reason = (TextView) findViewById(R.id.tv_return_reason);
        apply_afterSale_topStatus = (LinearLayout) findViewById(R.id.apply_afterSale_topStatus);
        apply_after_sale_reason = (RelativeLayout) findViewById(R.id.apply_after_sale_reason);
        apply_after_sale_submit = (SelectedButton) findViewById(R.id.apply_after_sale_submit);
        amount_view = (AmountView) findViewById(R.id.amount_view);
        tv_one_deduct_amount = (TextView) findViewById(R.id.tv_one_deduct_amount);
        apply_after_sale_refundAmount = (TextView) findViewById(R.id.apply_after_sale_refundAmount);
        apply_after_sale_notPass_tips = (TextView) findViewById(R.id.apply_after_sale_notPass_tips);
        et_return_reason = (EditText) findViewById(R.id.et_return_reason);

        apply_after_sale_reason.setOnClickListener(this);
        apply_after_sale_submit.setOnClickListener(this);
        initTopStatus();
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.myRefund.to_apply.toString())){
            final ReturnGoodsBean returnGoodsBean = (ReturnGoodsBean) object;
            this.mreturnGoodsBean = returnGoodsBean;
            amount_view.setAmount(1);
            count = 1;
            amount_view.setGoods_storage(returnGoodsBean.getContent().getSum());
            if(returnGoodsBean.getContent().getReasonList().get(0).getReason()!=null&&!returnGoodsBean.getContent().getReasonList().get(0).getReason().equals("")){
                tv_return_reason.setText(returnGoodsBean.getContent().getReasonList().get(0).getReason());
                reson_id = returnGoodsBean.getContent().getReasonList().get(0).getZ_apply_reason_id();
            }
            apply_after_sale_notPass_tips.setText("退货数量不超过"+returnGoodsBean.getContent().getSum()+"个");

            if (!goods_id_webActivity.equals("")){
                apply_after_sale_refundAmount.setText("额"+String.format("%.2f",returnGoodsBean.getContent().getOne_price()));
            }else {
                apply_after_sale_refundAmount.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",returnGoodsBean.getContent().getOne_price()));
            }


            tv_one_deduct_amount.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",returnGoodsBean.getContent().getOne_deduct_amount()));
            amount_view.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                @Override
                public void onAmountChange(View view, int amount) {
                    count = amount;
                    if (!goods_id_webActivity.equals("")){
                        apply_after_sale_refundAmount.setText("额"+String.format("%.2f",(returnGoodsBean.getContent().getOne_price()*amount)));
                    }else {
                        apply_after_sale_refundAmount.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",(returnGoodsBean.getContent().getOne_price()*amount)));
                    }

                    tv_one_deduct_amount.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",returnGoodsBean.getContent().getOne_deduct_amount()*amount));
                }
            });
        }else if(contentType.equals(DataType.myRefund.saveRefund.toString())){
            hideDialogLoading();
            ReturnSaveBean returnSaveBean = (ReturnSaveBean) object;
            showToast("申请成功");
            Intent intent = new Intent(getContext(), GoodsAfterSaleDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("gr_id",returnSaveBean.getContent().getGr_id());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }

        //兑换专区的更新
        if (!goods_id_webActivity.equals("")){
            ((LinearLayout)findViewById(R.id.zhekou)).setVisibility(View.GONE);
        }
    }


    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.apply_after_sale_reason:
                dialog = new SelectDialog(this,mreturnGoodsBean,"return").builder();
                dialog.show();
                dialog.setOnBackListener(new SelectDialog.onBackListener() {
                    @Override
                    public void selectResult(String result,int id) {
                        tv_return_reason.setText(result);
                        reson_id = id;
                    }
                });
                break;
            case R.id.apply_after_sale_submit:
                showDialogLoading();
                refundOrderPresenter.saveRefund(getContext(),SPUtil.getPrefString("token",""),et_return_reason.getText().toString(),gc_id+"",count+"",reson_id+"","0",DataType.myRefund.saveRefund.toString());
                break;
        }

    }

    /**
     * 初始化顶部申请售后状态进度
     */
    private void initTopStatus(){
        for(int i = 0 ; i < mStatusArray.length ; i++){
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
        if(i == 0 ){
            mTv.setTextColor(getResources().getColor(R.color.status_green_color));
        }

        if(i == mStatusArray.length - 1){
            mImg.setVisibility(View.GONE);
        }

        view.setLayoutParams(lp);
        return view;
    }


}
