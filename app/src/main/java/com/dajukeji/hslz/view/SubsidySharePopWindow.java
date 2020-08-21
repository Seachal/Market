package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.MySubsidyBean;
import com.dajukeji.hslz.domain.javaBean.SubsidyShareBean;
import com.dajukeji.hslz.util.loader.GlideEngine;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/9/12.
 * 补贴分享弹窗
 */

public class SubsidySharePopWindow extends PopupWindow implements View.OnClickListener{
    private View contentView;
    private ImageView popwindow_close;
    private LinearLayout wx_zone , wx_friends; // 分享微信 分享朋友圈
    private Activity context;
    private GoodDetailsBean goodDetailsBean;
    private MySubsidyBean.ContentBean.OrderListBean mySubsidyBean;
    private SubsidyShareBean subsidyShareBean;

    public SubsidySharePopWindow(final Activity context, Object goodDetail, SubsidyShareBean subsidyShareBean ,String type) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_subsidy_share, null);
        this.context = context;
        if(type.equals("today")){
            this.goodDetailsBean = (GoodDetailsBean) goodDetail;
        }else{
            mySubsidyBean =(MySubsidyBean.ContentBean.OrderListBean) goodDetail;
        }

        this.subsidyShareBean = subsidyShareBean;

        int height =  context.getResources().getDimensionPixelSize(R.dimen.y950);
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
                if (keyCode == KeyEvent.KEYCODE_BACK &&SubsidySharePopWindow.this.isShowing()) {
                    SubsidySharePopWindow.this.dismiss();
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
        wx_zone = (LinearLayout) contentView.findViewById(R.id.wx_zone);
        wx_friends = (LinearLayout) contentView.findViewById(R.id.wx_friends);
        popwindow_close = (ImageView) contentView.findViewById(R.id.popwindow_close);
        popwindow_close.setOnClickListener(new View.OnClickListener() { // 关闭
            @Override
            public void onClick(View v) {
                SubsidySharePopWindow.this.dismiss();
            }
        });
        if(goodDetailsBean!=null){
            // 产品图片
            ImageView main_photo = (ImageView) contentView.findViewById(R.id.share_goods_image);
            int width =  context.getResources().getDimensionPixelSize(R.dimen.x340);
            int height =  context.getResources().getDimensionPixelSize(R.dimen.y340);
            GlideEngine.loadThumbnail(context.getApplicationContext(),width,height, R.drawable.goods, main_photo, goodDetailsBean.getContent().getGoods_main_photo());
            TextView tv_share_title =(TextView) contentView.findViewById(R.id.tv_share_title);
            tv_share_title.setText("老铁！我正在数字绿州领取 【"+goodDetailsBean.getContent().getGoods_name()+"】"
                    +context.getResources().getString(R.string.rmb_symbol)+
                    String.format("%.2f",(goodDetailsBean.getContent().getGoods_price()-goodDetailsBean.getContent().getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");

            TextView tv_share_price = (TextView) contentView.findViewById(R.id.tv_share_price);
            tv_share_price.setText(context.getResources().getString(R.string.rmb_symbol)+goodDetailsBean.getContent().getLowest_price());

            TextView tv_market_price  = (TextView) contentView.findViewById(R.id.tv_market_price);
            tv_market_price.setText("市场价"+context.getResources().getString(R.string.rmb_symbol)+goodDetailsBean.getContent().getGoods_current_price());
        }else if(mySubsidyBean!=null){
            // 产品图片
            ImageView main_photo = (ImageView) contentView.findViewById(R.id.share_goods_image);
            int width =  context.getResources().getDimensionPixelSize(R.dimen.x340);
            int height =  context.getResources().getDimensionPixelSize(R.dimen.y340);
            GlideEngine.loadThumbnail(context.getApplicationContext(),width,height, R.drawable.goods, main_photo, mySubsidyBean.getGoods_main_photo());
            TextView tv_share_title =(TextView) contentView.findViewById(R.id.tv_share_title);
            tv_share_title.setText("老铁！我正在数字绿州领取 【"+mySubsidyBean.getGoods_name()+"】"
                    +context.getResources().getString(R.string.rmb_symbol)+
                    String.format("%.2f",(mySubsidyBean.getOriginal_price()-mySubsidyBean.getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");

            TextView tv_share_price = (TextView) contentView.findViewById(R.id.tv_share_price);
            tv_share_price.setText(context.getResources().getString(R.string.rmb_symbol)+mySubsidyBean.getLowest_price());

            TextView tv_market_price  = (TextView) contentView.findViewById(R.id.tv_market_price);
            tv_market_price.setText("市场价"+context.getResources().getString(R.string.rmb_symbol)+mySubsidyBean.getOriginal_price());
        }

        wx_zone.setOnClickListener(this);
        wx_friends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wx_zone: //分享朋友圈
                Platform.ShareParams spz = new Platform.ShareParams();
                spz.setShareType(Platform.SHARE_WEBPAGE);
                if(goodDetailsBean!=null){
                    spz.setImageUrl(goodDetailsBean.getContent().getGoods_main_photo());
                    spz.setUrl(subsidyShareBean.getContent().getUrl());
                    spz.setTitle("老铁！我正在数字绿州领取 【"+goodDetailsBean.getContent().getGoods_name()+"】"
                            +context.getResources().getString(R.string.rmb_symbol)+
                            String.format("%.2f",(goodDetailsBean.getContent().getGoods_price()-goodDetailsBean.getContent().getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");
                }else if(mySubsidyBean!=null){
                    spz.setImageUrl( mySubsidyBean.getGoods_main_photo());
                    spz.setUrl(subsidyShareBean.getContent().getUrl());
                    spz.setTitle("老铁！我正在数字绿州领取 【"+mySubsidyBean.getGoods_name()+"】"
                            +context.getResources().getString(R.string.rmb_symbol)+
                            String.format("%.2f",(mySubsidyBean.getOriginal_price()-mySubsidyBean.getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");
                }

                Platform wechatMon= ShareSDK.getPlatform (WechatMoments.NAME);
                // 执行图文分享
                wechatMon.share(spz);
                wechatMon.setPlatformActionListener(new MyPlatformActionListener(context)); // 设置分享事件回调*/
                break;
            case R.id.wx_friends: //分享朋友圈
                Platform.ShareParams spf = new Platform.ShareParams();
                spf.setShareType(Platform.SHARE_WEBPAGE);
                if(goodDetailsBean!=null){
                    spf.setImageUrl(goodDetailsBean.getContent().getGoods_main_photo());
                    spf.setUrl(subsidyShareBean.getContent().getUrl());
                    spf.setTitle("老铁！我正在数字绿州领取 【"+goodDetailsBean.getContent().getGoods_name()+"】"
                            +context.getResources().getString(R.string.rmb_symbol)+
                            String.format("%.2f",(goodDetailsBean.getContent().getGoods_price()-goodDetailsBean.getContent().getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");
                }else if(mySubsidyBean!=null){
                    spf.setImageUrl(mySubsidyBean.getGoods_main_photo());
                    spf.setUrl(subsidyShareBean.getContent().getUrl());
                    spf.setTitle("老铁！我正在数字绿州领取 【"+mySubsidyBean.getGoods_name()+"】"
                            +context.getResources().getString(R.string.rmb_symbol)+
                            String.format("%.2f",(mySubsidyBean.getOriginal_price()-mySubsidyBean.getLowest_price()))+"元补贴 帮我争取一下，非常感谢!");
                }
                Platform wechat= ShareSDK.getPlatform (Wechat.NAME);
                // 执行图文分享
                wechat.share(spf);
                wechat.setPlatformActionListener(new MyPlatformActionListener(context)); // 设置分享事件回调*/
                break;

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
