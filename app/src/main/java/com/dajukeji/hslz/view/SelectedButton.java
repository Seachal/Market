package com.dajukeji.hslz.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by cdr on 2017/4/8.
 */
public class SelectedButton extends AppCompatButton implements View.OnTouchListener {

    public SelectedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Drawable drawable = this.getBackground();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                drawable.setColorFilter(0xFFECECEC, PorterDuff.Mode.MULTIPLY);
                this.setBackgroundDrawable(drawable);
                break;

            case MotionEvent.ACTION_UP:
                drawable.clearColorFilter();
//                this.invalidate();
                break;
        }

        return false;
    }
}
