package com.dajukeji.hslz.im.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by cdr on 2018/1/18.
 */

public class TextFilterView extends AppCompatTextView {

    public TextFilterView(Context context) {
        this(context, null);
    }

    public TextFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setDefaultColor(getCurrentTextColor());
    }

    private int mDefaultColor;

    private void setDefaultColor(int color) {
        this.mDefaultColor = color;
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);

        Drawable background = getBackground();
        if (background != null) {
            if (pressed) {
                background.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                background.clearColorFilter();
            }
        }

        if (pressed) {
            int alpha = Color.alpha(getDefaultColor());
            if (alpha < Color.alpha(0x80FFFFFF)) {
                alpha *= 2;
            } else {
                alpha /= 2;
            }

            int maxAlpha = Color.alpha(Color.WHITE);
            int minAlpha = Color.alpha(Color.TRANSPARENT);
            if (alpha > maxAlpha) {
                alpha = maxAlpha;
            } else if (alpha < minAlpha) {
                alpha = minAlpha;
            }

            super.setTextColor(ColorStateList.valueOf(getDefaultColor()).withAlpha(alpha).getDefaultColor());
        } else {
            super.setTextColor(getDefaultColor());
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Drawable background = getBackground();
        if (background != null) {
            if (enabled) {
                background.setColorFilter(Color.LTGRAY, PorterDuff.Mode.MULTIPLY);
            } else {
                background.clearColorFilter();
            }
        }

        if (enabled) {
            int alpha = Color.alpha(getDefaultColor());
            if (alpha < Color.alpha(0x80FFFFFF)) {
                alpha *= 2;
            } else {
                alpha /= 2;
            }

            int maxAlpha = Color.alpha(Color.WHITE);
            int minAlpha = Color.alpha(Color.TRANSPARENT);
            if (alpha > maxAlpha) {
                alpha = maxAlpha;
            } else if (alpha < minAlpha) {
                alpha = minAlpha;
            }

            super.setTextColor(ColorStateList.valueOf(getDefaultColor()).withAlpha(alpha).getDefaultColor());
        } else {
            super.setTextColor(getDefaultColor());
        }
    }

    @Override
    public void setTextColor(int color) {
        if (isPressed())
            return;

        super.setTextColor(color);
        setDefaultColor(getCurrentTextColor());
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        if (isPressed())
            return;

        super.setTextColor(colors);
        setDefaultColor(getCurrentTextColor());
    }
}
