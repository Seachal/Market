package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;

public class AllActivity extends HttpBaseActivity {
    private RelativeLayout title_bar_return;
    private int num;
//    private TextView text_title;
    private LinearLayout linear_back;
    private TextView text_shou,text_shou_yuan,text_ding,text_ding_yuan,text_fen,text_fen_yuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        Intent intent =getIntent();
        num = intent.getIntExtra("num",0);
        linear_back = (LinearLayout) findViewById(R.id.linear_back);
        text_ding = (TextView) findViewById(R.id.text_ding);
        text_ding_yuan = (TextView) findViewById(R.id.text_ding_yuan);
        text_shou = (TextView) findViewById(R.id.text_shou);
        text_shou_yuan = (TextView) findViewById(R.id.text_shou_yuan);
        text_fen = (TextView) findViewById(R.id.text_fen);
        text_fen_yuan = (TextView) findViewById(R.id.text_fen_yuan);
        title_bar_return  = (RelativeLayout) findViewById(R.id.title_bar_return);
        title_bar_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (num==1) {
            setTitleBar("产品销售额", true);
//            text_title.setText("产品销售额");
            linear_back.setBackgroundResource(R.mipmap.bg_chan);
            text_ding.setText("订单总额(元)");
            text_ding_yuan.setText("100");
            text_fen.setText("分成比例(%)");
            text_fen_yuan.setText("1");
            text_shou.setText("收益总额(元)");
            text_shou_yuan.setText("1");
        }else if (num==2){
            setTitleBar("广告收益",true);
//            text_title.setText("广告收益");
            linear_back.setBackgroundResource(R.mipmap.bg_guang);
            text_ding.setText("点击量(次)");
            text_ding_yuan.setText("1000");
            text_fen.setText("分成比例(%)");
            text_fen_yuan.setText("2");
            text_shou.setText("收益总额(元)");
            text_shou_yuan.setText("1");
        }else if(num==3){
            setTitleBar("红包分成",true);
//            text_title.setText("红包分成");
            linear_back.setBackgroundResource(R.mipmap.bg_hong);
            text_ding.setText("点击量(次)");
            text_ding_yuan.setText("100");
            text_fen.setText("分成比例(%)");
            text_fen_yuan.setText("2");
            text_shou.setText("收益总额(元)");
            text_shou_yuan.setText("1");
        }else if(num==4){
            setTitleBar("销售代金券",true);
//            text_title.setText("销售代金券");
            linear_back.setBackgroundResource(R.mipmap.bg_xiao);
            text_ding.setText("订单总额(元)");
            text_ding_yuan.setText("100");
            text_fen.setText("分成比例(%)");
            text_fen_yuan.setText("3");
            text_shou.setText("收益总额(元)");
            text_shou_yuan.setText("1");
        }else if(num==5){
            setTitleBar("专卖场交易费",true);
//            text_title.setText("专卖场交易费");
            linear_back.setBackgroundResource(R.mipmap.bg_zhuan);
            text_ding.setText("订单总额(元)");
            text_ding_yuan.setText("100");
            text_fen.setText("分成比例(%)");
            text_fen_yuan.setText("5");
            text_shou.setText("收益总额(元)");
            text_shou_yuan.setText("1");
        }
    }
}
