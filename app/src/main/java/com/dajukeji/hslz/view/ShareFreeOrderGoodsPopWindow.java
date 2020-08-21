package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import com.dajukeji.hslz.domain.freeOrder.FreeOrderGoods;
import com.dajukeji.hslz.util.loader.GlideEngine;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by Administrator on 2017/9/12.
 * 免单分享产品弹窗
 */

public class ShareFreeOrderGoodsPopWindow extends PopupWindow implements View.OnClickListener{
    private View contentView;
    private LinearLayout wx_friend,wx_zone; // 分享微信 分享朋友圈
    private Activity context;

    public ShareFreeOrderGoodsPopWindow(final Activity context,FreeOrderGoods freeOrderGoods) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwindow_share_freeorder_goods, null);
        this.context = context;

        int  h = context.getWindowManager().getDefaultDisplay().getHeight();
        int  w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(h);
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
                if (keyCode == KeyEvent.KEYCODE_BACK &&ShareFreeOrderGoodsPopWindow.this.isShowing()) {
                    ShareFreeOrderGoodsPopWindow.this.dismiss();
                    WindowManager.LayoutParams params=context.getWindow().getAttributes();
                    params.alpha=1.0f;
                    context.getWindow().setAttributes(params);
                    return true;
                }
                return false;
            }
        });
        initView(freeOrderGoods);
    }


    /**
     * 初始化View
     * */
    private void initView(FreeOrderGoods freeOrderGoods){
        // 产品图片
        ImageView main_photo = (ImageView) contentView.findViewById(R.id.main_photo);
        int width =  context.getResources().getDimensionPixelSize(R.dimen.x400);
        int height =  context.getResources().getDimensionPixelSize(R.dimen.y400);
        GlideEngine.loadThumbnail(context.getApplicationContext(),width,height, R.drawable.goods, main_photo,  freeOrderGoods.getContent().getMain_photo());

        TextView tv_share_title = (TextView) contentView.findViewById(R.id.tv_share_title);
        tv_share_title.setText("我在数字绿州领取了 【"+freeOrderGoods.getContent().getGoods_name()+"】 还有高额补贴哦");

        TextView tv_zero_price = (TextView) contentView.findViewById(R.id.tv_zero_price);
        tv_zero_price.setText(context.getResources().getString(R.string.rmb_symbol)+"0");

        TextView market_price =  (TextView) contentView.findViewById(R.id.market_price);
        market_price.setText("市场价"+context.getResources().getString(R.string.rmb_symbol)+freeOrderGoods.getContent().getMarket_price());

        // 二维码
        ImageView tv_qr_code = (ImageView) contentView.findViewById(R.id.tv_qr_code);
        int qr_width =  context.getResources().getDimensionPixelSize(R.dimen.x290);
        int qr_height =  context.getResources().getDimensionPixelSize(R.dimen.y290);
        GlideEngine.loadThumbnail(context.getApplicationContext(),qr_width,qr_height, R.drawable.goods, tv_qr_code, freeOrderGoods.getContent().getQrcode_image_url());
        // 用户头像
        ImageView user_head = (ImageView) contentView.findViewById(R.id.user_head);
        GlideEngine.loadImage(context.getApplicationContext(),R.drawable.goods,user_head,freeOrderGoods.getContent().getWx_head_img());

        // 用户昵称
        TextView user_name = (TextView) contentView.findViewById(R.id.user_name);
        user_name.setText(freeOrderGoods.getContent().getWx_nickname());

        wx_friend = (LinearLayout) contentView.findViewById(R.id.wx_friend);
        wx_zone = (LinearLayout) contentView.findViewById(R.id.wx_zone);
        wx_friend.setOnClickListener(this);
        wx_zone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wx_friend: //分享朋友
                int width =  context.getResources().getDimensionPixelSize(R.dimen.x782);
                int height =  context.getResources().getDimensionPixelSize(R.dimen.y1000);
                View views =  contentView.findViewById(R.id.share_image);
                final Bitmap bmps = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                views.draw(new Canvas(bmps));
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setShareType(Platform.SHARE_IMAGE);
                sp.setImageData(bmps);
                Platform wechat= ShareSDK.getPlatform (Wechat.NAME);
                // 执行图文分享
                wechat.share(sp);
                wechat.setPlatformActionListener(new MyPlatformActionListener(context)); // 设置分享事件回调*/
                break;
            case R.id.wx_zone: //分享朋友圈
                int widths =  context.getResources().getDimensionPixelSize(R.dimen.x782);
                int heights =  context.getResources().getDimensionPixelSize(R.dimen.y1000);
                View view =  contentView.findViewById(R.id.share_image);
                final Bitmap bmp = Bitmap.createBitmap(widths, heights, Bitmap.Config.ARGB_8888);
                view.draw(new Canvas(bmp));
                Platform.ShareParams spm = new Platform.ShareParams();
                spm.setShareType(Platform.SHARE_IMAGE);
                spm.setImageData(bmp);
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
          /*  MLog.INSTANCE.d("ShareSDK", "onComplete ---->  分享成功");
            platform.isClientValid();
            platform.getName();*/
            Toast.makeText(context,"分享成功",Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        public void onError(Platform platform, int i, Throwable throwable) {
          /*  MLog.INSTANCE.d("ShareSDK", "onError ---->  分享失败" + throwable.getStackTrace().toString());
            MLog.INSTANCE.d("ShareSDK", "onError ---->  分享失败" + throwable.getMessage());
            throwable.getMessage();
            throwable.printStackTrace();*/
            Toast.makeText(context,"分享失败",Toast.LENGTH_SHORT).show();
            dismiss();
        }

        @Override
        public void onCancel(Platform platform, int i) {
            Toast.makeText(context,"取消分享",Toast.LENGTH_SHORT).show();
            dismiss();
        }
    }
}
