package com.dajukeji.hslz.view;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.coupon.CatCoupon;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Administrator on 2017/9/12.
 */

public class SharePopWindow extends PopupWindow implements View.OnClickListener {
    private View conentView;
    private Activity context;
    private CatCoupon.ContentBean.GoodsListBean coupon;
    //点击的View Id
    private int onclickId;

    private ClipboardManager mClipboardManager; //复制粘贴
    //剪切板Data对象
    private ClipData mClipData;

    private ImageView pict_url; //产品图片

    public SharePopWindow(final Activity context,CatCoupon.ContentBean.GoodsListBean coupon) {
        this.context=context;
        this.coupon=coupon;
        mClipboardManager = (ClipboardManager)context.getSystemService(CLIPBOARD_SERVICE); //初始化复制粘贴

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popuwindow_share, null);
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
        this.setOnDismissListener(new PopupWindow.OnDismissListener() { // 在dismiss中恢复透明度
            public void onDismiss() {
                if(onclickId!=R.id.share_picture){
                    changeNotTransparent(context);
                }
            }
        });

       /* // 实例化一个ColorDrawable颜色为半透明
        //ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        //this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimationPreview);
        //产品标题
        TextView goods_title = (TextView) conentView
                .findViewById(R.id.goods_title);
        goods_title.setText(this.coupon.getTitle());
        //推荐语
        TextView item_description = (TextView) conentView
                .findViewById(R.id.item_description);
        item_description.setText(this.coupon.getItem_description());

        pict_url = (ImageView) conentView
                .findViewById(R.id.pict_url);
        // 设置图片的宽高
        int imSize =  context.getResources().getDimensionPixelSize(R.dimen.x362);
        GlideEngine.loadThumbnail(context,imSize, R.drawable.goods, pict_url, this.coupon.getPict_url());

        TextView copy_text = (TextView) conentView
                .findViewById(R.id.copy_text);
        TextView copy_link = (TextView) conentView
                .findViewById(R.id.copy_link);
        TextView share_picture = (TextView) conentView
                .findViewById(R.id.share_picture);
        ImageView wx_friend = (ImageView) conentView
                .findViewById(R.id.wx_friend);
        ImageView wx_zone = (ImageView) conentView
                .findViewById(R.id.wx_zone);

        TextView share_url = (TextView) conentView.findViewById(R.id.share_url); // 显示分享网页链接
        share_url.setText(HttpAddress.mBaseUrl+coupon.getShare_url());

        TextView coupon_info  =(TextView) conentView.findViewById(R.id.coupon_info);
        coupon_info.setText("优惠券: "+coupon.getCoupon_info());

        TextView zk_final_price = (TextView) conentView.findViewById(R.id.zk_final_price);
        zk_final_price.setText("折后价: " + coupon.getZk_final_price());

        copy_text.setOnClickListener(this);
        copy_link.setOnClickListener(this);
        share_picture.setOnClickListener(this);
        wx_friend.setOnClickListener(this);
        wx_zone.setOnClickListener(this);*/

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
          /*  case R.id.copy_text:
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("shopText", coupon.getShop_title()+","+coupon.getTitle()+","+coupon.getItem_description()+","+coupon.getCoupon_info());
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(context,"复制成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.copy_link:
                //创建一个新的文本clip对象
                mClipData = ClipData.newPlainText("clickUrl", coupon.getCoupon_click_url());
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(context,"复制成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_picture:
                FriendOrZonePopuWindow popupWindow=new FriendOrZonePopuWindow(context,coupon.getPict_url());
                View parent=LayoutInflater.from(context).inflate(R.layout.activity_goods_detail,null,false);
                popupWindow.showPopupWindowInParentCenter(parent);
                onclickId=R.id.share_picture;
                break;
            case R.id.wx_friend: // 微信好友分享
                Platform.ShareParams sp = new Platform.ShareParams();
                sp.setShareType(Platform.SHARE_WEBPAGE);
                sp.setTitle(coupon.getTitle());
                sp.setText(coupon.getCoupon_info());
                sp.setImageUrl(coupon.getPict_url());
                sp.setUrl(HttpAddress.mBaseUrl+coupon.getShare_url());
                Platform wechat= ShareSDK.getPlatform (Wechat.NAME);
                // 执行图文分享
                wechat.share(sp);
                break;
            case R.id.wx_zone: // 微信朋友圈分享
                Platform.ShareParams spm = new Platform.ShareParams();
                spm.setShareType(Platform.SHARE_WEBPAGE);
                spm.setTitle(coupon.getTitle());
                spm.setText(coupon.getShop_title()+"  "+coupon.getTitle()+"         "+coupon.getItem_description()+"     "+coupon.getShare_url());
                spm.setImageUrl(coupon.getPict_url());
                spm.setUrl(HttpAddress.mBaseUrl+coupon.getShare_url());
                Platform wechatMon= ShareSDK.getPlatform (WechatMoments.NAME);
                // 执行图文分享
                wechatMon.share(spm);
                break;*/
        }
    }

    public void showPopupWindowInParentCenter(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.BOTTOM,0,0);
        } else {
            this.dismiss();
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

}
