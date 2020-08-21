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
import com.dajukeji.hslz.activity.RefundOrderDetailActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.ReturnLogisticsBean;
import com.dajukeji.hslz.domain.order.ReturnSaveBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.SelectedButton;
import com.dajukeji.hslz.view.dialog.SelectLogisticsDialog;

import butterknife.BindArray;

/**
 * 申请退货品填写运单
 */

public class GoodsAfterSaleWaybillActivity extends HttpBaseActivity {

    private RefundOrderPresenter refundOrderPresenter;

    private LinearLayout apply_afterSale_topStatus; // 头部状态

    @BindArray(R.array.apply_after_sale_status_array)
    String mStatusArray[];
    private RelativeLayout apply_after_sale_company; // 理由
    private SelectedButton apply_after_sale_submit; // 提交
    private SelectLogisticsDialog dialog; // 物流公司
    private TextView tv_after_sale_company; // 物流公司
    private EditText et_after_sale; //物流单号
    private TextView tv_after_sale_money; //金额
    private ImageView item_order_list_img; // 产品图片
    private TextView item_order_list_name; // 产品名称
    private TextView item_order_list_good_sku; //产品规格
    private TextView item_order_list_present_price; // 产品价格
    private TextView tv_goods_count; // 产品数量
    private EditText et_return_reason;
    private ReturnLogisticsBean mreturnLogisticsBean;
    private String gr_id;
    private int company_id; // 物流公司Id

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_goods_after_sale_waybill);
        refundOrderPresenter = new RefundOrderPresenter(this);
    }

    @Override
    protected void initView() {
        setTitleBar(R.string.text_apply_after_sale, true);
        Bundle bundle = getIntent().getExtras();
        gr_id = bundle.getString("gr_id");
        refundOrderPresenter.returnLogistics(getContext(), SPUtil.getPrefString("token",""),gr_id, DataType.myRefund.returnLogistics.toString());
        apply_afterSale_topStatus = (LinearLayout) findViewById(R.id.apply_afterSale_topStatus);
        apply_after_sale_company = (RelativeLayout) findViewById(R.id.apply_after_sale_company);
        apply_after_sale_submit = (SelectedButton) findViewById(R.id.apply_after_sale_submit);
        et_after_sale = (EditText) findViewById(R.id.et_after_sale);
        tv_after_sale_company = (TextView) findViewById(R.id.tv_after_sale_company);
        tv_after_sale_money = (TextView) findViewById(R.id.tv_after_sale_money);
        item_order_list_img = (ImageView) findViewById(R.id.item_order_list_img);
        item_order_list_name = (TextView) findViewById(R.id.item_order_list_name);
        item_order_list_good_sku = (TextView) findViewById(R.id.item_order_list_good_sku);
        item_order_list_present_price = (TextView) findViewById(R.id.item_order_list_present_price);
        tv_goods_count = (TextView) findViewById(R.id.tv_goods_count);
        et_return_reason = (EditText) findViewById(R.id.et_return_reason);
        apply_after_sale_company.setOnClickListener(this);
        apply_after_sale_submit.setOnClickListener(this);
        initTopStatus();
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.myRefund.returnLogistics.toString())){
            final ReturnLogisticsBean returnLogisticsBean = (ReturnLogisticsBean) object;
            this.mreturnLogisticsBean = returnLogisticsBean;
            tv_after_sale_money.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",returnLogisticsBean.getContent().getRefund())); // 金额
            this.company_id  = returnLogisticsBean.getContent().getCompany_list().get(0).getCompany_id();
            tv_after_sale_company.setText(returnLogisticsBean.getContent().getCompany_list().get(0).getCompany_name());

            int size =  getResources().getDimensionPixelSize(R.dimen.x260);
            GlideEngine.loadThumbnail(getContext().getApplicationContext(),size, R.drawable.goods, item_order_list_img,returnLogisticsBean.getContent().getRList().get(0).getIcon());
            item_order_list_name.setText(returnLogisticsBean.getContent().getRList().get(0).getGoods_name());
            item_order_list_good_sku.setText(returnLogisticsBean.getContent().getRList().get(0).getSpec_info());
            item_order_list_present_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f", returnLogisticsBean.getContent().getRList().get(0).getOne_price()));
            tv_goods_count.setText(returnLogisticsBean.getContent().getRList().get(0).getCount()+"");
        }else if(contentType.equals(DataType.myRefund.saveWaybill.toString())){
            hideDialogLoading();
            ReturnSaveBean returnSaveBean = (ReturnSaveBean) object;
            showToast("提交成功");
            Intent intent = new Intent(getContext(), RefundOrderDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("gr_id",returnSaveBean.getContent().getGr_id());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }


    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.apply_after_sale_company:
                dialog = new SelectLogisticsDialog(this,mreturnLogisticsBean).builder();
                dialog.show();
                dialog.setOnBackListener(new SelectLogisticsDialog.onBackListener() {
                    @Override
                    public void selectResult(String result,int id) {
                        tv_after_sale_company.setText(result);
                        company_id = id;
                    }
                });
                break;
            case R.id.apply_after_sale_submit:
                if(!et_after_sale.getText().toString().equals("")){
                    showDialogLoading();
                    refundOrderPresenter.saveWaybill(getContext(),SPUtil.getPrefString("token",""),gr_id,company_id+"",et_after_sale.getText().toString(),et_return_reason.getText().toString(),DataType.myRefund.saveWaybill.toString());
                }else {
                    showToast("请填写运单号");
                }
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
        if(i < 3 ){
            mTv.setTextColor(getResources().getColor(R.color.status_green_color));
        }

        if(i == mStatusArray.length - 1){
            mImg.setVisibility(View.GONE);
        }

        view.setLayoutParams(lp);
        return view;
    }


}
