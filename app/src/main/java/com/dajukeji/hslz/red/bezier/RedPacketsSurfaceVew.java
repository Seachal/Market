package com.dajukeji.hslz.red.bezier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.domain.RedbagBean;
import com.dajukeji.hslz.red.meteorshower.SpriteManager;
import com.dajukeji.hslz.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 结合公司大神的方法 自定义surfaceview 然后通过贝塞尔曲线动画来计算 坐标，不断重绘界面实现
 * <p>
 * create by yao.cui at 2016/11/29
 */
public class RedPacketsSurfaceVew extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private static final int INTERVAL = 300;//默认绘制间隔 单位 ms
    private static final int DURATION = 10 * 1000; // 默认执行时长 单位 ms
    private static final int GRAVITY = 80;

    private int mInterval = INTERVAL;
    private int mDuration = DURATION;
    private int mGravity = GRAVITY;
    private int number = 0;
    private int mHeight;
    private int mWidth;
    private int store;//品牌? 没有参与计算
    private int y;//红包总个数,掉落时 -1
    /**
     * 此线程用于画掉落的红包
     */
    private Thread mDrawThread;
    /**
     * 此线程用于发红包
     */
    private Thread mMsgThread;

    private boolean isStop = false;

    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private Bitmap mBitmap;
    private boolean isLoaded = false;
    private List<Bitmap> listLogo = new ArrayList<>();
    private ArrayList<Sprites> mSprits = new ArrayList();
    private List<RedbagBean.ContentBean.RedListBean> listBeans;

    private SpriteManager spriteManager;
    private Context context;
    //外放一个借口监听点击事件
    public IOnRedPackageClick onRedPackageClick;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                addSprites();//增加一个红包
            }
        }
    };

    public RedPacketsSurfaceVew(Context context) {
        super(context);
        init(context);
    }

    public RedPacketsSurfaceVew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RedPacketsSurfaceVew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.setZOrderOnTop(true);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);//抗齿锯

        //生成View
//        View redBagView = LayoutInflater.from(context).inflate(R.layout.log_red_bag,null);
//        ((ImageView) redBagView.findViewById(R.id.iv_log)).setImageBitmap();

        //默认红包
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.red_bag);
        setOnTouchListener(this);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        spriteManager = SpriteManager.getInstance();
        spriteManager.init(context, mWidth, mHeight);
    }

    /**
     * 当surfaceView绘制完成，就把线程跑起来
     * @param surfaceHolder
     */
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mDrawThread = new Thread(new DrawThread());
        mMsgThread = new Thread(new MsgThread());

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    /**
     * 当surfaceView被销毁时执行stop
     * @param surfaceHolder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        LogUtil.info("红包雨", "结束");
        isStop = true;
        stop();
    }

    /**
     * 添加红包进入
     */
    private void addSprites() {
        Sprites sprites = new Sprites(mBitmap, mWidth, mHeight,getContext());
        if (listBeans != null){
            try {
                sprites.setBean(listBeans.get(0));
//                sprites.setmLogo(listLogo.get(0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (listBeans.size() > 1){
                listBeans.remove(0);
            }
        }
        mSprits.add(sprites);

    }

//    private class LoadLogo implements Runnable{
//        @Override
//        public void run() {
//            URL myFileUrl = null;
//            HttpURLConnection conn = null;
//            try {
//                for (RedbagBean.ContentBean.RedListBean bean : listBeans){
//                    myFileUrl = new URL(bean.getUrl());
//                    conn = (HttpURLConnection) myFileUrl.openConnection();
//                    conn.setDoInput(true);
//                    conn.connect();
//                    InputStream is = conn.getInputStream();
////                    Sprites.this.mLogo = BitmapFactory.decodeStream(is);
//                    listLogo.add(BitmapFactory.decodeStream(is));
//                    is.close();
//                }
////                isLoaded = true;
//                startRain();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }

    /**
     * 给出了红包数量
     * @param s
     * @param num
     */
    public void numbers(int s, int num , List<RedbagBean.ContentBean.RedListBean> list) {
        LogUtil.info("红包雨 初始化", "s=y=红包个数=" + s + ",num=store=" + num);
        y = s;
        store = num;
        this.listBeans = list;

//        Thread thread = new Thread(new LoadLogo());
//        thread.start();
    }

    /**
     * 开启两个线程，一个是从服务端拿数据的线程，一个是绘制surfaceView的线程
     */
    public void startRain() {
        LogUtil.info("红包雨", "开始");
        if (mDrawThread != null) {
            mDrawThread.start();
        }

        if (mMsgThread != null) {
            mMsgThread.start();
        }

    }

    /**
     * 移除已经执行完的红包，防止arraylist里面对象越来越多
     */
    private void recycle() {

        ArrayList<Sprites> recycleSp = new ArrayList<>();

        for (int i = 0, siz = mSprits.size(); i < siz; i++) {
            if (mSprits.get(i).isOver) {
                recycleSp.add(mSprits.get(i));
            }
        }

        for (int i = 0; i < recycleSp.size(); i++) {
            mSprits.remove(recycleSp.get(i));
        }
    }

    /**
     * 停止红包雨 清空红包对象
     */
    public void stop() {
        for (int i = 0, size = mSprits.size(); i < size; i++) {
            Sprites sprites = mSprits.get(i);
            sprites.stop();
            sprites = null;
        }
        mSprits.clear();
        mBitmap.recycle();

    }

    /**
     * 检测是否点到了红包
     * @param x
     * @param y
     * @return
     */
    private Sprites getTouchSprites(int x, int y) {
        for (int i = 0, size = mSprits.size(); i < size; i++) {
            if (mSprits.get(i).isInner(x, y)) {
                return mSprits.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Sprites sprites = getTouchSprites((int) motionEvent.getX(), (int) motionEvent.getY());
                if (sprites != null) {
                    sprites.isOver = true;
                    number++;//记录每一个被点击到红包
                }
                if (number > 0) {
                    //id未生效，因为
                    //id开始生效了，在sprites里添加了一个Bean
                    onRedPackageClick.onClick(sprites.getBean());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    private class DrawThread implements Runnable {
        @Override
        public void run() {
//            while (!isLoaded){
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            while (y > -5) {// y>0 就停止动画会导致最后生成的红包没有下落到最底部，所以给个缓冲
                if (isStop) break;//添加终止标记
                recycle();

                Canvas canvas = null;
                synchronized (mSurfaceHolder) {
                    canvas = mSurfaceHolder.lockCanvas();

                    if (canvas == null) {
                        isStop = true;
                        return;
                    }

                    /**清空画布*/
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

                    for (int i = 0, size = mSprits.size(); i < size; i++) {
                        mSprits.get(i).draw(canvas, mPaint);
                    }

                    mSurfaceHolder.unlockCanvasAndPost(canvas);

                }
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    class MsgThread implements Runnable {
        @Override
        public void run() {
//            while (!isLoaded){
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            while (y > 0) {
                LogUtil.info("红包雨 发红包中", "剩余数:" + y);
                if (isStop) break;//添加终止标记
                y--;
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(1);
            }
        }
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);

        return bmp;
    }

    public interface IOnRedPackageClick {
        void onClick(RedbagBean.ContentBean.RedListBean bean);
    }
}
