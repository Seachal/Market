package com.dajukeji.hslz.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/10/28.
 */

public class MyProgressBar extends ProgressBar{
        String text;
        Paint mPaint;

        public MyProgressBar(Context context) {
            super(context);
            initText();
        }

        public MyProgressBar(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            initText();
        }


        public MyProgressBar(Context context, AttributeSet attrs) {
            super(context, attrs);
            initText();
        }

        @Override
        public synchronized void setProgress(int progress) {
            setText(progress);
            super.setProgress(progress);

        }

        @Override
        protected synchronized void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //this.setText();
            Rect rect = new Rect();
            this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
            int x = (getWidth() / 8);
            int y = (getHeight() / 2) - rect.centerY();
            canvas.drawText(this.text, x, y, this.mPaint);
        }

        //初始化，画笔
        private void initText(){
            this.mPaint = new Paint();
            this.mPaint.setColor(0xFFff9261);
            this.mPaint.setTextSize(23f);
            this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        }

        private void setText(){
            setText(this.getProgress());
        }

        //设置文字内容
        private void setText(int last){
            this.text = "剩余"+last+"份";
        }


}
