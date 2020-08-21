package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
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
import com.dajukeji.hslz.util.loader.GlideEngine;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/9/12.
 * 全网比价分享弹窗
 */

public class
ComparePriceSharePopWindow extends PopupWindow implements View.OnClickListener{
    private View contentView;
    private ImageView popwindow_close;
    private LinearLayout wx_zone; // 分享微信 分享朋友圈
    private Activity context;
    private GoodDetailsBean goodDetailsBean;

    public ComparePriceSharePopWindow(final Activity context, GoodDetailsBean goodDetail) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_compare_price_share, null);
        this.context = context;
        this.goodDetailsBean = goodDetail;

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
                if (keyCode == KeyEvent.KEYCODE_BACK &&ComparePriceSharePopWindow.this.isShowing()) {
                    ComparePriceSharePopWindow.this.dismiss();
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
        popwindow_close = (ImageView) contentView.findViewById(R.id.popwindow_close);
        popwindow_close.setOnClickListener(new View.OnClickListener() { // 关闭
            @Override
            public void onClick(View v) {
                ComparePriceSharePopWindow.this.dismiss();
            }
        });
        // 产品图片
        ImageView main_photo = (ImageView) contentView.findViewById(R.id.share_goods_image);
        int width =  context.getResources().getDimensionPixelSize(R.dimen.x340);
        int height =  context.getResources().getDimensionPixelSize(R.dimen.y340);
        GlideEngine.loadThumbnail(context.getApplicationContext(),width,height, R.drawable.goods, main_photo, goodDetailsBean.getContent().getGoods_main_photo());
        TextView tv_share_title =(TextView) contentView.findViewById(R.id.tv_share_title);
        tv_share_title.setText("我在数字绿州发现慧品 【"+goodDetailsBean.getContent().getGoods_name()+"】 还有高额补贴哦！");

        TextView tv_share_price = (TextView) contentView.findViewById(R.id.tv_share_price);
        tv_share_price.setText(context.getResources().getString(R.string.rmb_symbol)+goodDetailsBean.getContent().getShare_price());

        TextView tv_market_price  = (TextView) contentView.findViewById(R.id.tv_market_price);
        tv_market_price.setText(context.getResources().getString(R.string.rmb_symbol)+goodDetailsBean.getContent().getNot_share_price());
        tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
        wx_zone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wx_zone: //分享朋友圈
                Platform.ShareParams spm = new Platform.ShareParams();
                spm.setShareType(Platform.SHARE_WEBPAGE);
                spm.setImageUrl(goodDetailsBean.getContent().getGoods_main_photo());
                spm.setUrl("http://www.syb111.com/app-release.apk");
                spm.setTitle("我在数字绿州发现慧品 【"+goodDetailsBean.getContent().getGoods_name()+"】 还有高额补贴哦！");
                Platform wechatMon= ShareSDK.getPlatform (WechatMoments.NAME);
                // 执行图文分享
                wechatMon.share(spm);
                wechatMon.setPlatformActionListener(new MyPlatformActionListener(context)); // 设置分享事件回调*/
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
