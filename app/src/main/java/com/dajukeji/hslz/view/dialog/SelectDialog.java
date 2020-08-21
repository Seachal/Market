package com.dajukeji.hslz.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.domain.order.RefundGoodsBean;
import com.dajukeji.hslz.domain.order.ReturnGoodsBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cdr on 2017/11/29.
 */

public class SelectDialog {

    @BindView(R.id.dialog_recyclerView)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter<ReturnGoodsBean.ContentBean.ReasonListBean> mrAdapter;
    private BaseRecyclerAdapter<RefundGoodsBean.ContentBean.ReasonListBean> mfAdapter;
    private Context context;
    private Dialog dialog;
    private Display display;

    private ReturnGoodsBean returnGoodsBean;
    private RefundGoodsBean refundGoodsBean;
    private String type ;
    public SelectDialog(Context context ,Object content,String type) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        if(type.equals("return")){
            this.returnGoodsBean = (ReturnGoodsBean) content;
        }else if(type.equals("refund")){
            this.refundGoodsBean = (RefundGoodsBean) content;
        }
        this.type = type;
    }

    public SelectDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_recyclerview, null);
        ButterKnife.bind(this, view);
        view.setMinimumWidth(display.getWidth());

        initRecyclerView(view);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.CommonDialogStyle);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER_VERTICAL);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int)(display.getWidth());
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    private void initRecyclerView(View view){

        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        if(type.equals("return")){
            mrAdapter = new BaseRecyclerAdapter<ReturnGoodsBean.ContentBean.ReasonListBean>(context,  null,R.layout.item_recyclerview_text) {
                @Override
                public void convert(BaseRecyclerHolder holder, ReturnGoodsBean.ContentBean.ReasonListBean data, int position, boolean isScrolling) {
                    holder.setText(R.id.dialog_recyclerView_item_textView,data.getReason());
                }
            };
            mrAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ReturnGoodsBean.ContentBean.ReasonListBean>() {
                @Override
                public void onItemClick(BaseRecyclerHolder viewHolder, ReturnGoodsBean.ContentBean.ReasonListBean data, int position) {
                    onBackListener.selectResult(data.getReason(),data.getZ_apply_reason_id());
                    dimiss();
                }
            });
            mrAdapter.setData(returnGoodsBean.getContent().getReasonList());
            mRecyclerView.setAdapter(mrAdapter);
        }else if(type.equals("refund")) {
            mfAdapter = new BaseRecyclerAdapter<RefundGoodsBean.ContentBean.ReasonListBean>(context,  null,R.layout.item_recyclerview_text) {
                @Override
                public void convert(BaseRecyclerHolder holder, RefundGoodsBean.ContentBean.ReasonListBean data, int position, boolean isScrolling) {
                    holder.setText(R.id.dialog_recyclerView_item_textView,data.getRefund_reason());
                }
            };
            mfAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<RefundGoodsBean.ContentBean.ReasonListBean>() {
                @Override
                public void onItemClick(BaseRecyclerHolder viewHolder, RefundGoodsBean.ContentBean.ReasonListBean data, int position) {
                    onBackListener.selectResult(data.getRefund_reason(),data.getRefund_id());
                    dimiss();
                }
            });
            if (refundGoodsBean.getContent()==null||refundGoodsBean==null||refundGoodsBean.getContent().getReasonList()==null){
                return;
            }
            mfAdapter.setData(refundGoodsBean.getContent().getReasonList());
            mRecyclerView.setAdapter(mfAdapter);
        }

    }

    private onBackListener onBackListener;

    public interface onBackListener{
        void selectResult(String result,int reason_id);
    }

    public void setOnBackListener(onBackListener onBackListener){
        this.onBackListener = onBackListener;
    }

    public void show() {
        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dimiss(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
            dialog = null;
        }
    }

}
