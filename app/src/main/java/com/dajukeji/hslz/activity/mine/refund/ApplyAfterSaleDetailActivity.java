package com.dajukeji.hslz.activity.mine.refund;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.ReturnPromptDetailBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

import butterknife.BindArray;

/**
 * 申请售后退款详情
 */

public class ApplyAfterSaleDetailActivity extends HttpBaseActivity {

    private RefundOrderPresenter refundOrderPresenter;
    private LinearLayout apply_afterSale_topStatus; // 头部状态

    @BindArray(R.array.apply_after_apply_status_array)
    String mStatusArray[];

    private TextView apply_after_sale_state;
    private TextView apply_after_sale_service;
    private TextView apply_after_sale_reason;
    private TextView apply_after_sale_money;
    private TextView tv_one_deduct_amount;

    private ImageView item_order_list_img; // 产品图片
    private TextView item_order_list_name; // 产品名称
    private TextView item_order_list_good_sku; //产品规格
    private TextView item_order_list_present_price; // 产品价格
    private TextView tv_goods_count; // 产品数量

    private String gr_id;
    private int return_status;
    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_apply_after_sale_detail);
        refundOrderPresenter = new RefundOrderPresenter(this);
    }

    @Override
    protected void initView() {
        setTitleBar(R.string.text_apply_after_sale_detail, true);
        Bundle bundle = getIntent().getExtras();
        gr_id = bundle.getString("gr_id");
        refundOrderPresenter.returnDetail(getContext(), SPUtil.getPrefString("token",""),gr_id+"", DataType.myRefund.returnDetail.toString());
        apply_afterSale_topStatus = (LinearLayout) findViewById(R.id.apply_afterSale_topStatus);

        apply_after_sale_state = (TextView) findViewById(R.id.apply_after_sale_state);
        apply_after_sale_service = (TextView) findViewById(R.id.apply_after_sale_service);
        apply_after_sale_reason = (TextView) findViewById(R.id.apply_after_sale_reason);
        apply_after_sale_money = (TextView) findViewById(R.id.apply_after_sale_money);
        tv_one_deduct_amount = (TextView) findViewById(R.id.tv_one_deduct_amount);

        item_order_list_img = (ImageView) findViewById(R.id.item_order_list_img);
        item_order_list_name = (TextView) findViewById(R.id.item_order_list_name);
        item_order_list_good_sku = (TextView) findViewById(R.id.item_order_list_good_sku);
        item_order_list_present_price = (TextView) findViewById(R.id.item_order_list_present_price);
        tv_goods_count = (TextView) findViewById(R.id.tv_goods_count);
        initTopStatus();
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.myRefund.returnDetail.toString())){
            ReturnPromptDetailBean returnPromptDetailBean = (ReturnPromptDetailBean) object;
            return_status = returnPromptDetailBean.getContent().getRefund_status();

            if(returnPromptDetailBean.getContent().getService()==0){ // 退货
                if(returnPromptDetailBean.getContent().getRefund_status()==0){
                    apply_after_sale_state.setText("申请中");
                }else if(returnPromptDetailBean.getContent().getRefund_status()==1){
                    apply_after_sale_state.setText("同意退货");
                }else if(returnPromptDetailBean.getContent().getRefund_status()==2){
                    apply_after_sale_state.setText("确认收货");
                }else if(returnPromptDetailBean.getContent().getRefund_status()==3){
                    apply_after_sale_state.setText("拒绝");
                }
            }else if(returnPromptDetailBean.getContent().getService()==1){ // 退款
                if(returnPromptDetailBean.getContent().getRefund_status()==0){
                    apply_after_sale_state.setText("申请中");
                }else if(returnPromptDetailBean.getContent().getRefund_status()==2){
                    apply_after_sale_state.setText("同意退款");
                }else if(returnPromptDetailBean.getContent().getRefund_status()==3){
                    apply_after_sale_state.setText("拒绝");
                }
            }
            if(returnPromptDetailBean.getContent().getService()==0){ // 退货
                apply_after_sale_service.setText("退款退货");
            }else if(returnPromptDetailBean.getContent().getService()==1){ // 退款
                apply_after_sale_service.setText("仅退款（无需退货）");
            }
            apply_after_sale_reason.setText(returnPromptDetailBean.getContent().getRefund_reason());
            apply_after_sale_money.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f", returnPromptDetailBean.getContent().getRefund()));
            tv_one_deduct_amount.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f", returnPromptDetailBean.getContent().getDeduct_amount()));
            int size =  getResources().getDimensionPixelSize(R.dimen.x260);
            GlideEngine.loadThumbnail(getContext().getApplicationContext(),size, R.drawable.goods, item_order_list_img,returnPromptDetailBean.getContent().getRList().get(0).getIcon());
            item_order_list_name.setText(returnPromptDetailBean.getContent().getRList().get(0).getGoods_name());
            item_order_list_good_sku.setText(returnPromptDetailBean.getContent().getRList().get(0).getSpec_info());
            item_order_list_present_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f", returnPromptDetailBean.getContent().getRList().get(0).getNow_price()));
            tv_goods_count.setText(returnPromptDetailBean.getContent().getRList().get(0).getCount()+"");
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
        if(i == return_status ){
            mTv.setTextColor(getResources().getColor(R.color.status_green_color));
        }

        if(i == mStatusArray.length - 1){
            mImg.setVisibility(View.GONE);
        }

        view.setLayoutParams(lp);
        return view;
    }

}
