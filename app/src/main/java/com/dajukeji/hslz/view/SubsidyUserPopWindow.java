package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.domain.javaBean.MySubsidyBean;
import com.dajukeji.hslz.util.loader.GlideEngine;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by Administrator on 2017/9/12.
 * 补贴分享弹窗
 */

public class SubsidyUserPopWindow extends PopupWindow implements View.OnClickListener{

    private View contentView;
    private Activity context;
    private ImageView ll_popwindow_close;
    private RecyclerView rv_subsidy_user;
    private CircleImageView main_subsidy_user;
    private TextView tv_main_subsidy_name;
    private TextView tv_subsidy_main_time;
    private TextView tv_subsidy_count;

    private MySubsidyBean.ContentBean.OrderListBean mySubsidyBean;

    public SubsidyUserPopWindow(final Activity context, MySubsidyBean.ContentBean.OrderListBean mySubsidyBean) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_subsidy_user, null);
        this.context = context;
        this.mySubsidyBean = mySubsidyBean;


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
        contentView.setFocusableInTouchMode(true);
        contentView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK &&SubsidyUserPopWindow.this.isShowing()) {
                    SubsidyUserPopWindow.this.dismiss();
                    WindowManager.LayoutParams params=context.getWindow().getAttributes();
                    params.alpha=1.0f;
                    context.getWindow().setAttributes(params);
                    return true;
                }
                return false;
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
                SubsidyUserPopWindow.this.dismiss();
            }
        });
        rv_subsidy_user = (RecyclerView) contentView.findViewById(R.id.rv_subsidy_user);
        main_subsidy_user = (CircleImageView) contentView.findViewById(R.id.main_subsidy_user);
        tv_main_subsidy_name = (TextView) contentView.findViewById(R.id.tv_main_subsidy_name);
        tv_subsidy_main_time = (TextView) contentView.findViewById(R.id.tv_subsidy_main_time);
        tv_subsidy_count = (TextView) contentView.findViewById(R.id.tv_subsidy_count);
        int size =  context.getResources().getDimensionPixelSize(R.dimen.x140);
        GlideEngine.loadThumbnail(context,size,R.drawable.head_image,main_subsidy_user, mySubsidyBean.getHelp_detail().getHoster_headimgurl());
        tv_main_subsidy_name.setText(mySubsidyBean.getHelp_detail().getHoster_nickname());
        tv_subsidy_main_time.setText(mySubsidyBean.getHelp_detail().getHoster_time()+"建单");
        tv_subsidy_count.setText("共"+mySubsidyBean.getHelp_detail().getNumber()+"人");
        rv_subsidy_user.setLayoutManager(new LinearLayoutManager(context));
        BaseRecyclerAdapter<MySubsidyBean.ContentBean.OrderListBean.HelpDetailBean.HelperListBean> recyclerAdapter = new BaseRecyclerAdapter<MySubsidyBean.ContentBean.OrderListBean.HelpDetailBean.HelperListBean>(context,null,R.layout.item_subsidy_help_user) {
            @Override
            public void convert(BaseRecyclerHolder holder, MySubsidyBean.ContentBean.OrderListBean.HelpDetailBean.HelperListBean data, int position, boolean isScrolling) {
                int size =  context.getResources().getDimensionPixelSize(R.dimen.x70);
                CircleImageView cv_help_user = holder.getView(R.id.cv_help_user);
                holder.setImageWithGlide(context,cv_help_user,data.getHeadimgurl());
                holder.setText(R.id.tv_help_name,data.getNickname());
                holder.setText(R.id.tv_help_time,data.getTime()+"助力");
            }
        };
        recyclerAdapter.setData(mySubsidyBean.getHelp_detail().getHelperList());
        rv_subsidy_user.setAdapter(recyclerAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
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

    private class MyPlatformActionListener implements PlatformActionListener {
        private Context context;

        public MyPlatformActionListener(Context context){
            this.context = context;
        }

        @Override
        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
            mListener.compareShare();
            dismiss();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
            Toast.makeText(context,"分享失败",Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(context,"取消分享",Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
    private OnClickOkListener mListener;

    public void setOnClickOkListener(OnClickOkListener listener){
        this.mListener = listener;
    }

    public interface OnClickOkListener{
        /**
         * 分享成功
         * */
        void compareShare();
    }

}
