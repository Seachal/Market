package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.domain.javaBean.OrderLogisticsMessageBean;
import com.dajukeji.hslz.util.loader.GlideEngine;

/**
 * Created by Administrator on 2017/9/12.
 * 物流弹窗
 */

public class OrderLogisticsPopWindow extends PopupWindow{

    private View contentView;
    private Activity context;
    private ImageView ll_popwindow_close;
    private RecyclerView rv_logistics_message;
    private ImageView iv_goods_phone;
    private TextView tv_goods_name;
    private TextView tv_ship_code;
    private TextView tv_receive;
    private TextView tv_order_status;

    private OrderLogisticsMessageBean orderLogisticsMessageBean;

    public OrderLogisticsPopWindow(final Activity context, OrderLogisticsMessageBean orderLogisticsMessageBean) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_order_logistics, null);
        this.context = context;
        this.orderLogisticsMessageBean = orderLogisticsMessageBean;

        int height =  context.getWindowManager().getDefaultDisplay().getHeight();
        int with = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(with);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(height);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        changeTransparent(context);
        // 刷新状态
        this.update();
        this.setOnDismissListener(new OnDismissListener() { // 在dismiss中恢复透明度
         public void onDismiss() {
             changeNotTransparent(context);
         }
        });
        initView();
    }


    /**
     * 初始化View
     * */
    private void initView(){
        ll_popwindow_close = (ImageView) contentView.findViewById(R.id.ll_popwindow_close);
        ll_popwindow_close.setOnClickListener(new View.OnClickListener() { // 关闭
            @Override
            public void onClick(View v) {
                OrderLogisticsPopWindow.this.dismiss();
            }
        });
        rv_logistics_message = (RecyclerView) contentView.findViewById(R.id.rv_logistics_message);
        iv_goods_phone = (ImageView) contentView.findViewById(R.id.iv_goods_phone);
        tv_goods_name = (TextView) contentView.findViewById(R.id.tv_goods_name);
        tv_ship_code = (TextView) contentView.findViewById(R.id.tv_ship_code);
        tv_receive = (TextView) contentView.findViewById(R.id.tv_receive);
        tv_order_status = (TextView) contentView.findViewById(R.id.tv_order_status);
        int size =  context.getResources().getDimensionPixelSize(R.dimen.x150);
        GlideEngine.loadThumbnail(context,size,R.drawable.head_image,iv_goods_phone, orderLogisticsMessageBean.getContent().getGoodsList().get(0).getIcon());
        tv_goods_name.setText(orderLogisticsMessageBean.getContent().getGoodsList().get(0).getGoods_name());
        if(!orderLogisticsMessageBean.getContent().getCompany_name().equals("")){
            tv_ship_code.setText(orderLogisticsMessageBean.getContent().getCompany_name()+":"+ orderLogisticsMessageBean.getContent().getShip_code());
        }else {
            tv_ship_code.setVisibility(View.INVISIBLE);
        }
        tv_receive.setText(orderLogisticsMessageBean.getContent().getAddress());
        tv_order_status.setText(orderLogisticsMessageBean.getContent().getStatus());
        rv_logistics_message.setLayoutManager(new LinearLayoutManager(context));
        BaseRecyclerAdapter<OrderLogisticsMessageBean.ContentBean.InformactionBean> recyclerAdapter = new BaseRecyclerAdapter<OrderLogisticsMessageBean.ContentBean.InformactionBean>(context,null,R.layout.item_logistics_message) {
            @Override
            public void convert(BaseRecyclerHolder holder, OrderLogisticsMessageBean.ContentBean.InformactionBean data, int position, boolean isScrolling) {
               if(orderLogisticsMessageBean.getContent().getInformaction().isEmpty()){
                   holder.setText(R.id.tv_logistics,"等待商家发货");
               }else {
                   holder.setText(R.id.tv_logistics,data.getAcceptTime()+"\n"+data.getAcceptStation());
               }
            }
        };
        recyclerAdapter.setData(orderLogisticsMessageBean.getContent().getInformaction());
        if(orderLogisticsMessageBean.getContent().getInformaction().size()>5){
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,context.getResources().getDimensionPixelSize(R.dimen.y600));
            rv_logistics_message.setLayoutParams(layoutParams);
        }
        rv_logistics_message.setAdapter(recyclerAdapter);

    }


    /**
     * 使Activity变半透明
     * @param context
     */
    private void changeTransparent(Activity context){
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = context.getWindow() .getAttributes();
        lp.alpha = 0.4f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    /**
     * 使Activity恢复正常
     * @param context
     */
    private void changeNotTransparent(Activity context){
        // 产生背景变暗效果
        WindowManager.LayoutParams lp = context.getWindow() .getAttributes();
        lp.alpha = 1f;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

}
