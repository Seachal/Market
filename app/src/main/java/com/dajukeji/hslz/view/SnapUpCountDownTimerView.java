
package com.dajukeji.hslz.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("HandlerLeak")
public class SnapUpCountDownTimerView extends LinearLayout {

    private TextView tv_hour_decade;
    private TextView tv_hour_unit;
    private TextView tv_min_decade;
    private TextView tv_min_unit;
    private TextView tv_sec_decade;
    private TextView tv_sec_unit;

    private TextView colon_hour;
    private TextView colon_minute;

    private LinearLayout ll_hour;
    private LinearLayout ll_minute;
    private LinearLayout ll_second;

    private Context context;

    private int hour_decade;
    private int hour_unit;
    private int min_decade;
    private int min_unit;
    private int sec_decade;
    private int sec_unit;

    private Timer timer;

    private String SNAP_INDEX = "SNAP_INDEX"; // 首页模式
    private String SNAP_FREE_ORDER = "SNAP_FREE_ORDER"; // 免单模式
    private String SNAP_WHOLE_PRICE = "SNAP_WHOLE_PRICE"; // 全网比价

    private Handler handler = new Handler() {

        public void handleMessage(Message msg) {
            countDown();
        }
    };

    public SnapUpCountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_count_downtimer, this);

        tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
        tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
        tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
        tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
        tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
        tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);

        colon_hour = (TextView) view.findViewById(R.id.colon_hour);
        colon_minute = (TextView) view.findViewById(R.id.colon_minute);

        ll_hour = (LinearLayout) view.findViewById(R.id.ll_hour);
        ll_minute = (LinearLayout) view.findViewById(R.id.ll_minute);
        ll_second =(LinearLayout)  view.findViewById(R.id.ll_second);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.SnapUpCountDownTimerView);
        int size = array.getInteger(R.styleable.SnapUpCountDownTimerView_viewSize, 12);

        if(array.getString(R.styleable.SnapUpCountDownTimerView_SnapMode).equals(SNAP_INDEX)){ // 在首页显示
            setIndexMode();
        }else if(array.getString(R.styleable.SnapUpCountDownTimerView_SnapMode).equals(SNAP_FREE_ORDER)){ // 在免单显示
            setFreeOrder();
        }else if(array.getString(R.styleable.SnapUpCountDownTimerView_SnapMode).equals(SNAP_WHOLE_PRICE)){  //在全网比价显示
            setWholePrice();
        }


        tv_hour_decade.setTextSize(size);
        tv_hour_unit.setTextSize(size);
        tv_min_decade.setTextSize(size);
        tv_min_unit.setTextSize(size);
        tv_sec_decade.setTextSize(size);
        tv_sec_unit.setTextSize(size);
        colon_hour.setTextSize(size);
        colon_minute.setTextSize(size);
    }


    public void start() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(0);
                }
            }, 0, 1000);
        }
    }


    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public void setTime(int hour, int min, int sec) {

        if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0
                || sec < 0) {
            throw new RuntimeException("时间格式错误,请检查你的代码");
        }

        hour_decade = hour / 10;
        hour_unit = hour - hour_decade * 10;

        min_decade = min / 10;
        min_unit = min - min_decade * 10;

        sec_decade = sec / 10;
        sec_unit = sec - sec_decade * 10;

        tv_hour_decade.setText(hour_decade + "");
        tv_hour_unit.setText(hour_unit + "");
        tv_min_decade.setText(min_decade + "");
        tv_min_unit.setText(min_unit + "");
        tv_sec_decade.setText(sec_decade + "");
        tv_sec_unit.setText(sec_unit + "");
    }


    private void countDown() {
        if (isCarry4Unit(tv_sec_unit)) {
            if (isCarry4Decade(tv_sec_decade)) {
                if (isCarry4Unit(tv_min_unit)) {
                    if (isCarry4Decade(tv_min_decade)) {
                        if (isCarry4Unit(tv_hour_unit)) {
                            if (isCarry4Decade(tv_hour_decade)) {
                                Toast.makeText(context, "计数完成",
                                        Toast.LENGTH_SHORT).show();
                                stop();
                                setTime(0, 0, 0);//重置为0
                            }
                        }
                    }
                }
            }
        }
    }


    private boolean isCarry4Decade(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 5;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }


    private boolean isCarry4Unit(TextView tv) {

        int time = Integer.valueOf(tv.getText().toString());
        time = time - 1;
        if (time < 0) {
            time = 9;
            tv.setText(time + "");
            return true;
        } else {
            tv.setText(time + "");
            return false;
        }
    }

    /*
    * 首页显示时间模式
    * */
    private void setIndexMode(){
        ll_hour.setBackgroundResource(R.drawable.bg_snap_index);
        ll_minute.setBackgroundResource(R.drawable.bg_snap_index);
        ll_second.setBackgroundResource(R.drawable.bg_snap_index);
        colon_hour.setTextColor(Color.parseColor("#ff3945"));
        colon_minute.setTextColor(Color.parseColor("#ff3945"));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5,0,5,0);
        colon_minute.setLayoutParams(layoutParams);
        colon_hour.setLayoutParams(layoutParams);
    }
    /*
    *
    * */
    private void setFreeOrder(){
        ll_hour.setBackgroundResource(R.drawable.bg_snap_free_order);
        ll_minute.setBackgroundResource(R.drawable.bg_snap_free_order);
        ll_second.setBackgroundResource(R.drawable.bg_snap_free_order);
    }
    /*
    *
    * */
    private void setWholePrice(){
        ll_hour.setBackgroundColor(Color.parseColor("#ffffff"));
        ll_minute.setBackgroundColor(Color.parseColor("#ffffff"));
        ll_second.setBackgroundColor(Color.parseColor("#ffffff"));

        colon_hour.setTextColor(Color.parseColor("#ff4f00"));
        colon_minute.setTextColor(Color.parseColor("#ff4f00"));

        tv_hour_decade.setTextColor(Color.parseColor("#ff4f00"));
        tv_hour_unit.setTextColor(Color.parseColor("#ff4f00"));
        tv_min_decade.setTextColor(Color.parseColor("#ff4f00"));
        tv_min_unit.setTextColor(Color.parseColor("#ff4f00"));
        tv_sec_decade.setTextColor(Color.parseColor("#ff4f00"));
        tv_sec_unit.setTextColor(Color.parseColor("#ff4f00"));

    }
}
