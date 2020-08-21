package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.orderAddress.OrderEditAddressActivity;
import com.dajukeji.hslz.activity.orderAddress.OrderSelectAddressActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;

/**
 * Created by Administrator on 2017/9/12.
 * 免单分享地址产品弹窗
 */

public class ReceivingAddressPopWindow extends PopupWindow {
    private View conentView;
    private Activity context;
    private TextView consignee_name; //收货人
    private TextView consignee_phone;  //收货电话
    private TextView consignee_address;  //收货地址
    private LinearLayout select_area; // 地址展示
    public ReceivingAddressPopWindow(final Activity context, final UserAddressBean.ContentBean addressBean, final long id, final String sku, final String type) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        conentView = inflater.inflate(R.layout.popwindow_receiving_address  , null);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
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
        conentView.setFocusableInTouchMode(true);
        conentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&ReceivingAddressPopWindow.this.isShowing()) {
                    ReceivingAddressPopWindow.this.dismiss();
                    WindowManager.LayoutParams params=context.getWindow().getAttributes();
                    params.alpha=1.0f;
                    context.getWindow().setAttributes(params);
                    return true;
                }
                return false;
            }
        });
        consignee_name = (TextView) conentView.findViewById(R.id.consignee_name); // 收货人
        consignee_phone = (TextView) conentView.findViewById(R.id.consignee_phone); // 电话
        consignee_address = (TextView) conentView.findViewById(R.id.consignee_address); // 收货地址
        TextView tv_ok = (TextView) conentView.findViewById(R.id.tv_ok); // 确认按钮
        LinearLayout add_address =  (LinearLayout) conentView.findViewById(R.id.add_address); // 点击新增地址
        add_address.setOnClickListener(new View.OnClickListener() { //新增地址
            @Override
            public void onClick(View v) {
                dismiss();
                Intent adIntent = new Intent(context, OrderEditAddressActivity.class); // 编辑地址
                context.startActivity(adIntent);
            }
        });

        select_area = (LinearLayout) conentView.findViewById(R.id.select_area); // 点击选择地址
        select_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent = new Intent(context,OrderSelectAddressActivity.class); // 地址选择列表
                context.startActivityForResult(intent, 200);
            }
        });


        if(addressBean.getId()!=0 && addressBean.getTrueName()!=null){
            consignee_name.setText(addressBean.getTrueName()); // 设置默认收货人
            consignee_phone.setText(addressBean.getMobile()); //设置默认电话
            consignee_address.setText(addressBean.getAddress()+addressBean.getArea_info()); //设置收货地址
        }else{
            select_area.setVisibility(View.GONE);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {  // 分享界面
            @Override
            public void onClick(View v) {
                if(addressBean.getId()!=0 && addressBean.getTrueName()!=null){
                    dismiss();
                    if(type.equals("freeOrder")){ // 创建免单
                        mListener.createFree(id,sku,addressBean.getId(),type);
                    }else if(type.equals("exchange")){
                        mListener.createFree(id,sku,addressBean.getId(),type);
                    }else if(type.equals("invitee")){ // 兑换免单
                        mListener.createFree(id,sku,addressBean.getId(),type);
                    }else if(type.equals("award")){
                        mListener.createFree(id,sku,addressBean.getId(),type);
                    }
                }else{
                    Toast.makeText(context,"请选择收货地址",Toast.LENGTH_SHORT).show();
                }
            }
        });

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
    private OnClickOkListener mListener;

    public void setOnClickOkListener(OnClickOkListener listener){
        this.mListener = listener;
    }

    public interface OnClickOkListener{
        /**
         * 创建免单
         *
         * @param id 产品ID
         * @param sku 产品规格
         * @param addressId 地址
         * */
        void createFree(long id, String sku, long addressId ,String orderType);

    }

}
