package com.dajukeji.hslz.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.dajukeji.hslz.R;

/**
 * Created by cdr on 2017/12/2.
 */

public class TextViewUtils {

    /**
     * TextView设置现价
     */
    public static void setPresentPrice(TextView textView, float presentPrice){
        String str = "¥" + String.valueOf(presentPrice);
        SpannableString spannableString = new SpannableString(str);

        //设置 ¥ 的字体大小为 38px
        spannableString.setSpan(new AbsoluteSizeSpan(38), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //判断是否包含小数点
        if(str.contains(".")){
            //设置 ¥ 到小数点之间的字体大小为 60px
            spannableString.setSpan(new AbsoluteSizeSpan(55), 1, str.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置小数点的字体大小为 50px
            spannableString.setSpan(new AbsoluteSizeSpan(50), str.toString().indexOf("."), str.indexOf(".") + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            //设置小数点到结束之间的字体大小为 40px
            spannableString.setSpan(new AbsoluteSizeSpan(40), str.toString().indexOf("."), str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else{
            spannableString.setSpan(new AbsoluteSizeSpan(55), 1, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannableString);

    }

    /**
     * 订单item设置总价与运费
     * @param textView 文本控件
     * @param totalPrice 总价
     * @param shipPrice 运费
     * @param context 上下文
     */
    public static void setTotalPrice(TextView textView, double totalPrice, double shipPrice, Context context){
        String str = "实付:" + context.getResources().getString(R.string.rmb_symbol) + String.valueOf(totalPrice);
        if(shipPrice == 0){
            str = str + "(免运费)";
            textView.setText(str);
        }else{
            str = str + "(运费:" + shipPrice + ")";
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff3232")), spannableString.toString().indexOf("("), spannableString.toString().indexOf(")"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        }
    }



    /**
     * 获取字体高度
     * @param fontSize
     * @return
     */
    public static int getFontHeight(float fontSize)
    {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

}
