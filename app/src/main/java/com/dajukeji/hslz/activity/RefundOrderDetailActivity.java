package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.mine.refund.ApplyAfterSaleActivity;
import com.dajukeji.hslz.activity.mine.refund.GoodsAfterSaleActivity;
import com.dajukeji.hslz.activity.mine.refund.GoodsAfterSaleWaybillActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.ReturnDetailBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.RefundOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.SelectedButton;

/**
 * 退款详情 页面
 * */
public class RefundOrderDetailActivity extends HttpBaseActivity {

    private RefundOrderPresenter refundOrderPresenter;
    private TextView tv_order_status,tv_auto_des; //订单状态
    private SelectedButton apply_after_sale_waybill ,apply_after_sale_again;
    private TextView tv_refund_total,tv_refund_way ,tv_return_type; //总价 ,返回
    private LinearLayout ll_history;
    private TextView tv_store_name; // 协商历史
    private RecyclerView rv_return_listhistory;

    private LinearLayout ll_return_company; // 物流公司
    private TextView tv_user_name,tv_apply_time,tv_user_refund_content,tv_company_name,tv_company_id;
    private LinearLayout ll_return_reason;
    private TextView tv_user_name_last ,tv_pay_time_last,tv_return_reason_two,tv_service_type,tv_return_money,tv_return_reason,tv_return_message;
    private RecyclerView rv_refund_goods;
    private LinearLayout ll_return_reason_last,ll_return_price,ll_apply_time_last,ll_return_sn;
    private TextView tv_return_reason_last ,tv_return_price,tv_apply_time_last ,tv_refund_code;
    private String gr_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_refund_order_detail);
        setTitleBar(R.string.refund_order_detail,true);
        refundOrderPresenter = new RefundOrderPresenter(this);
        Bundle bundle = getIntent().getExtras();
        gr_id =bundle.getString("gr_id");
        refundOrderPresenter.returnSee(getContext(), SPUtil.getPrefString("token",""),gr_id,DataType.myRefund.returnWait.toString());
    }

    @Override
    protected void initView() {
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_auto_des = (TextView) findViewById(R.id.tv_auto_des);
        apply_after_sale_waybill = (SelectedButton) findViewById(R.id.apply_after_sale_waybill);
        apply_after_sale_again = (SelectedButton) findViewById(R.id.apply_after_sale_again);
        tv_refund_total = (TextView) findViewById(R.id.tv_refund_total);
        tv_refund_way = (TextView) findViewById(R.id.tv_refund_way);
        tv_return_type = (TextView) findViewById(R.id.tv_return_type);
        ll_history = (LinearLayout) findViewById(R.id.ll_history);
        ll_return_company = (LinearLayout) findViewById(R.id.ll_return_company);
        ll_return_reason = (LinearLayout) findViewById(R.id.ll_return_reason);
        rv_refund_goods = (RecyclerView) findViewById(R.id.rv_refund_goods);
        tv_store_name = (TextView) findViewById(R.id.tv_store_name);
        rv_return_listhistory = (RecyclerView) findViewById(R.id.rv_return_listhistory);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        tv_apply_time = (TextView) findViewById(R.id.tv_apply_time);
        tv_user_refund_content = (TextView) findViewById(R.id.tv_user_refund_content);
        tv_company_name = (TextView) findViewById(R.id.tv_company_name);
        tv_company_id = (TextView) findViewById(R.id.tv_company_id);
        tv_user_name_last = (TextView) findViewById(R.id.tv_user_name_last);
        tv_pay_time_last = (TextView) findViewById(R.id.tv_pay_time_last);
        tv_return_reason_two = (TextView) findViewById(R.id.tv_return_reason_two);
        tv_service_type = (TextView) findViewById(R.id.tv_service_type);
        tv_return_money = (TextView) findViewById(R.id.tv_return_money);
        tv_return_reason = (TextView) findViewById(R.id.tv_return_reason);
        tv_return_message = (TextView) findViewById(R.id.tv_return_message);
        tv_return_reason_last = (TextView) findViewById(R.id.tv_return_reason_last);
        tv_return_price = (TextView) findViewById(R.id.tv_return_price);
        tv_apply_time_last = (TextView) findViewById(R.id.tv_apply_time_last);
        tv_refund_code = (TextView) findViewById(R.id.tv_refund_code);
        ll_return_reason_last = (LinearLayout) findViewById(R.id.ll_return_reason_last);
        ll_return_price = (LinearLayout) findViewById(R.id.ll_return_price);
        ll_apply_time_last = (LinearLayout) findViewById(R.id.ll_apply_time_last);
        ll_return_sn = (LinearLayout) findViewById(R.id.ll_return_sn);
    }


    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.myRefund.returnWait.toString())){
            hideDialogLoading();
            ReturnDetailBean returnDetailBean = (ReturnDetailBean) object;
            final ReturnDetailBean.ContentBean data = returnDetailBean.getContent();
            if(data.getRefund_way().equals("微信")){
                tv_return_type.setText("退回代金券余额");
            }else {
                tv_return_type.setText("退回代金券余额");
            }
            if(data.getService()==0){ // 退货（service为0）退货状态0为申请中，1为同意退货，2为确认收货3拒绝（service为1）退款状态0为申请中，2同意退款3拒绝
                switch (data.getRefund_status()){
                    case 0: //申请退货
                        tv_order_status.setText("申请退货中"); // 订单状态
                        break;
                    case 1://同意退货
                        tv_order_status.setText("待寄回产品"); // 订单状态
                        if(data.getLogistics()==0){
                            apply_after_sale_waybill.setVisibility(View.VISIBLE);
                            apply_after_sale_waybill.setOnClickListener(new View.OnClickListener() { // 填写运单号
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), GoodsAfterSaleWaybillActivity.class);
                                    Bundle bundle =new Bundle();
                                    bundle.putString("gr_id",data.getGr_id());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
                        }
                        break;
                    case 2://确认收货
                        tv_order_status.setText("退货成功"); // 订单状态
                        break;
                    case 3://拒绝退货
                        tv_order_status.setText("退货申请失败"); // 订单状态
                        if(data.getAgain().equals("0")){
                            apply_after_sale_again.setVisibility(View.VISIBLE);
                            apply_after_sale_again.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), GoodsAfterSaleActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("gc_id",Long.parseLong(data.getGc_id()));
                                    bundle.putLong("gr_id",Long.parseLong(data.getGr_id()));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                        break;
                }
            }else if(data.getService()==1){ //退款
                switch (data.getRefund_status()){
                    case 0: //申请退款
                        tv_order_status.setText("申请退款中"); // 订单状态
                        break;
                    case 2://同意退款（成功）
                        tv_order_status.setText("退款成功"); // 订单状态
                        break;
                    case 3://拒绝退款
                        tv_order_status.setText("退款申请失败"); // 订单状态
                        if(data.getAgain().equals("0")) {
                            apply_after_sale_again.setVisibility(View.VISIBLE);
                            apply_after_sale_again.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(getContext(), ApplyAfterSaleActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putLong("gc_id", Long.parseLong(data.getGc_id()));
                                    bundle.putLong("gr_id", Long.parseLong(data.getGr_id()));
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                        break;
                }
            }
            tv_auto_des.setText(data.getApply_time());
            tv_refund_total.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getRefund()));
            tv_refund_way.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getRefund()));
            ll_history.setVisibility(View.VISIBLE);
            tv_store_name.setText(data.getStore_name());
            if(data.getCompany_name()!=null&&!data.getCompany_name().equals("")){ // 是否填写运单号
                ll_return_company.setVisibility(View.VISIBLE);
                tv_user_name.setText(data.getUser_name());
                tv_company_name.setText("物流公司："+data.getCompany_name());
                tv_user_refund_content.setText("【创建运单】"+data.getSet_waybill());
                tv_company_id.setText("运单号："+data.getReturn_shipCode());
                tv_apply_time.setText(data.getLogistics_time());
            }else{
                ll_return_company.setVisibility(View.GONE);
            }
            tv_user_name_last.setText(data.getUser_name());
            tv_pay_time_last.setText(data.getApply_time());
            tv_return_reason_two.setText("【创建退单】"+data.getSet_return());
            if(data.getService()==0){
                tv_service_type.setText("退款类型：退货退款");
            }else {
                tv_service_type.setText("退款类型：仅退款（无需退货）");
            }
            tv_return_money.setText("退款金额："+getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getRefund()));
            if(data.getInfo()!=null){
                tv_return_message.setText("退款描述："+data.getInfo());
            }else {
                tv_return_message.setText("退款描述：");
            }
            if(data.getReason()!=null){
                tv_return_reason.setText("退款原因："+data.getReason());
            }else {
                tv_return_reason.setText("退款原因：");
            }

            tv_return_reason_last.setText(data.getReason());
            tv_return_price.setText(getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getRefund()));
            tv_apply_time_last.setText(data.getApply_time());
            tv_refund_code.setText(data.getRefund_id());
            initReturnGoods(rv_refund_goods,returnDetailBean); // 初始化产品数据
            if(returnDetailBean.getContent().getListhistory()!=null&&!returnDetailBean.getContent().getListhistory().isEmpty()){
                listhistory(rv_return_listhistory,returnDetailBean); // 协商历史数据
            }
        }
    }

    /**
     * 退款产品数据列表
     */
    private void initReturnGoods(RecyclerView recyclerView, ReturnDetailBean returnDetailBean){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final BaseRecyclerAdapter<ReturnDetailBean.ContentBean.ListBean> list = new BaseRecyclerAdapter<ReturnDetailBean.ContentBean.ListBean>(getContext(), null, R.layout.item_refund_order_content) {
            @Override
            public void convert(BaseRecyclerHolder holder, ReturnDetailBean.ContentBean.ListBean data, int position, boolean isScrolling) {
                ImageView iv_goods = holder.getView(R.id.item_order_list_img);
                int width =  getResources().getDimensionPixelSize(R.dimen.x260);
                int height =  getResources().getDimensionPixelSize(R.dimen.y260);
                GlideEngine.loadThumbnails(getContext().getApplicationContext(),width,height, R.drawable.goods, iv_goods, data.getIcon()); //产品图片
                holder.setText(R.id.item_order_list_name,data.getGoods_name()); // 产品名称
                holder.setText(R.id.item_order_list_good_sku,data.getSpec_info()); //产品规格
                holder.setText(R.id.item_order_list_present_price,getResources().getString(R.string.rmb_symbol)+String.format("%.2f",data.getPrice())); // 现价
                holder.setText(R.id.item_order_list_num,"*"+data.getCount());
                TextView tv_order_label =  holder.getView(R.id.tv_order_label);
                tv_order_label.setVisibility(View.GONE);
            }
        };
        list.setData(returnDetailBean.getContent().getList());
        recyclerView.setAdapter(list);
        list.notifyDataSetChanged();
    }

    /**
     * 协商历史数据列表
     */
    private void listhistory(RecyclerView recyclerView, ReturnDetailBean returnDetailBean){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final BaseRecyclerAdapter<ReturnDetailBean.ContentBean.ListhistoryBean> list = new BaseRecyclerAdapter<ReturnDetailBean.ContentBean.ListhistoryBean>(getContext(), null, R.layout.item_refund_history_content) {
            @Override
            public void convert(BaseRecyclerHolder holder, ReturnDetailBean.ContentBean.ListhistoryBean data, int position, boolean isScrolling) {
                holder.setText(R.id.tv_title,"【标题】"+data.getTitle());
                holder.setText(R.id.tv_time,data.getTime());
                holder.setText(R.id.tv_contents,"【内容】"+data.getContents());
            }
        };
        list.setData(returnDetailBean.getContent().getListhistory());
        recyclerView.setAdapter(list);
        list.notifyDataSetChanged();
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
    }
}
