package com.dajukeji.hslz.red.bezier;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dajukeji.hslz.domain.RedbagBean;
import com.dajukeji.hslz.event.RedLogoEvent;
import com.dajukeji.hslz.util.loader.GlideEngine;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

/**
 * create by yao.cui at 2016/11/29
 * 在这里加一个RedListBean数据
 */
public class Sprites {
    public long id;
    public int mX;
    public int mY;
    public Bitmap mBitmap;
    public Bitmap mLogo;
    private Context mContext;
//    Glide.with(context).load(drawableId).into((ImageView)getView(viewId));
    public boolean isOver = false;

    private RedbagBean.ContentBean.RedListBean bean;//后来添加的数据，让红包知道自己是谁
    private Random mRandom;
    private int mParentHeight;
    private int mParentWidth;
    private ValueAnimator mAnimator;

    public Sprites(Bitmap bitmap, int pWidth, int pHeight,Context context){
        mRandom = new Random();
        this.mX = mRandom.nextInt(pWidth-bitmap.getWidth());
        this.mY = 0;
        this.mBitmap = bitmap;
        this.mParentWidth = pWidth;
        this.mParentHeight = pHeight;
        this.mContext = context;
        initAnimator();
    }

    private void initAnimator(){

        PointF point0 = new PointF(mRandom.nextInt(mParentWidth- mBitmap.getWidth()),-mBitmap.getHeight());
        PointF point1 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2), mRandom.nextInt((mParentHeight-100)/2));
        PointF point2 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2),mParentHeight/2+ mRandom.nextInt((mParentHeight/2)));
        PointF point3 = new PointF(point0.x- mBitmap.getWidth()+ mRandom.nextInt((int) mBitmap.getWidth()*2),mParentHeight);

        BezierEvaluator evaluator = new BezierEvaluator(point1,point2);//传入中间两个点
        mAnimator = ValueAnimator.ofObject(evaluator,point0,point3);//传入开始位置结束位置
        mAnimator.addUpdateListener(new BezierListener());
        mAnimator.setDuration(3000);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isOver = true;
            }
        });
        mAnimator.start();
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener{

        public BezierListener() {
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            PointF pointF = (PointF) valueAnimator.getAnimatedValue();
            mX = (int)pointF.x;
            mY = (int)pointF.y;
        }
    }

    public RedbagBean.ContentBean.RedListBean getBean(){
        return this.bean;
    }

    public void setBean(RedbagBean.ContentBean.RedListBean bean) throws IOException {
        this.bean = bean;
        Thread thread = new Thread(new LoadLogo());
        thread.start();
    }

    private class LoadLogo implements Runnable{
        @Override
        public void run() {
            URL myFileUrl = null;
            HttpURLConnection conn = null;
            try {
                myFileUrl = new URL(Sprites.this.bean.getUrl());
                conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                Sprites.this.mLogo = BitmapFactory.decodeStream(is);
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public Bitmap getmLogo() {
        return mLogo;
    }

    public void setmLogo(Bitmap mLogo) {
        this.mLogo = mLogo;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public boolean isInner(int x, int y){
//        Region region = new Region(this.mX,this.mY,this.mX+mBitmap.getWidth(),this.mY+mBitmap.getHeight());
//        return region.contains(mX,mY);

        return this.mX < x && this.mX + mBitmap.getWidth() > x && this.mY < y && this.mY + mBitmap.getHeight()>y;
    }

    public void stop(){
        if (mAnimator!= null){
            mAnimator.cancel();
            mAnimator = null;
        }
        isOver = true;
    }

    public void draw(Canvas canvas, Paint p){
        canvas.drawBitmap(mBitmap, mX, mY,p);
        if (mLogo != null){
            mLogo = Bitmap.createBitmap(mLogo,0,0,Math.min(mBitmap.getWidth(),mLogo.getWidth()),Math.min(mBitmap.getHeight()/3,mLogo.getHeight()));
            canvas.drawBitmap(mLogo,mX,mY,p);
        }
    }
}
