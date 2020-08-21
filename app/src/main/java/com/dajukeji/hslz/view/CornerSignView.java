package com.dajukeji.hslz.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.util.DisplayUtil;

/**
 * Created by cdr on 2017/12/11.
 * 角标
 */

public class CornerSignView  extends RelativeLayout {

    private int mMsgCount;
    private TextView mTvNum;
    private ImageView mImg;

    public CornerSignView(Context context) {
        this(context, null);
    }

    public CornerSignView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CornerSignView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CornerSignView);
        float imgWidth = typedArray.getDimension(R.styleable.CornerSignView_imgWidth, DisplayUtil.dp2px(context, 0));
        float imgHeight = typedArray.getDimension(R.styleable.CornerSignView_imgHeight, DisplayUtil.dp2px(context, 0));
        Drawable imgDrawable = getResources().getDrawable(typedArray.getResourceId(R.styleable.CornerSignView_imgDrawable, 0));

        RelativeLayout rlLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_corner_sign, this, true);
        mTvNum = (TextView) rlLayout.findViewById(R.id.corner_sign_num);
        mImg = (ImageView) rlLayout.findViewById(R.id.corner_sign_img);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mImg.getLayoutParams();
        params.width = (int) imgWidth;
        params.height = (int) imgHeight;
        mImg.setLayoutParams(params);
        mImg.setImageDrawable(imgDrawable);

        typedArray.recycle();
    }

    public void setMessageCount(int count) {
        mMsgCount = count;
        if (count == 0) {
            mTvNum.setVisibility(View.GONE);
        } else {
            mTvNum.setVisibility(View.VISIBLE);
            if (count < 100) {
                mTvNum.setText(count + "");
            } else {
                mTvNum.setText("99+");
            }
        }
        invalidate();
    }

    public void deleteAll(){
        mMsgCount = 0;
        mTvNum.setVisibility(View.GONE);
    }

}
