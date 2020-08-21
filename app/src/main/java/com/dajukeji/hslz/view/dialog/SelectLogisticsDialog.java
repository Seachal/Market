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
import com.dajukeji.hslz.domain.order.ReturnLogisticsBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cdr on 2017/11/29.
 */

public class SelectLogisticsDialog {

    @BindView(R.id.dialog_recyclerView)
    RecyclerView mRecyclerView;

    private BaseRecyclerAdapter<ReturnLogisticsBean.ContentBean.CompanyListBean> mAdapter;
    private Context context;
    private Dialog dialog;
    private Display display;

    private ReturnLogisticsBean returnLogisticsBean;

    public SelectLogisticsDialog(Context context , Object content) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
        this.returnLogisticsBean = (ReturnLogisticsBean) content;
    }

    public SelectLogisticsDialog builder() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_logistics_recyclerview, null);
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

            mAdapter = new BaseRecyclerAdapter<ReturnLogisticsBean.ContentBean.CompanyListBean>(context,  null,R.layout.item_recyclerview_text) {
                @Override
                public void convert(BaseRecyclerHolder holder, ReturnLogisticsBean.ContentBean.CompanyListBean data, int position, boolean isScrolling) {
                    holder.setText(R.id.dialog_recyclerView_item_textView,data.getCompany_name());
                }
            };
            mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ReturnLogisticsBean.ContentBean.CompanyListBean>() {
                @Override
                public void onItemClick(BaseRecyclerHolder viewHolder, ReturnLogisticsBean.ContentBean.CompanyListBean data, int position) {

                    onBackListener.selectResult(data.getCompany_name(),data.getCompany_id());
                    dimiss();
                }
            });
            mAdapter.setData(returnLogisticsBean.getContent().getCompany_list());
            mRecyclerView.setAdapter(mAdapter);
    }

    private onBackListener onBackListener;

    public interface onBackListener{
        void selectResult(String result, int reason_id);
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
