package com.dajukeji.hslz.activity.taocoupon;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.SearchRecordUtil;
import com.dajukeji.hslz.view.FlowLayout;

import java.util.List;

public class JDShopFindActivity extends HttpBaseActivity {

    private String[] mVals = new String[]
            { "卫衣", "小白鞋", "口红", "衣服", "鞋子", "T-shirt",
                    "抽纸", "大闸蟹", "运动套装", "毛巾", "大闸蟹", "母婴", "数码", "饰品"};

    private FlowLayout mFlowLayout;
    private FlowLayout id_flowlayout_history;
    private EditText shop_find_detail;
    private  TextView shop_to_find;
    private ImageView clearImage;//清除图片
    private ImageView clear_all;//清除历史记录图


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initData(); // 初始化数据
    }

    @Override
    protected  void initView(){
        setContentView(R.layout.activity_jd_shop_find);
        setTitleBar(R.string.text_search,true);
        mFlowLayout = (FlowLayout) findViewById(R.id.id_flowlayout);
        id_flowlayout_history = (FlowLayout) findViewById(R.id.id_flowlayout_history);
        shop_find_detail = (EditText) findViewById(R.id.shop_find_detail);
        clearImage= (ImageView) findViewById(R.id.clearImage);
        clear_all= (ImageView) findViewById(R.id.clear_all);
        //设置事件
        shop_to_find = (TextView)findViewById(R.id.shop_to_find);
        shop_to_find.setOnClickListener(this);
        //如果内容为空则设置图片看不到
        shop_find_detail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if("".equals(shop_find_detail.getText().toString())){
                    clearImage.setVisibility(View.GONE);
                }else {
                    clearImage.setVisibility(View.VISIBLE);
                }
            }
        });
        clearImage.setOnClickListener(this);
        clear_all.setOnClickListener(this);
    }

    /**
     * 初始化记录数据
     */
    public void initData()
    {
        LayoutInflater mInflater = LayoutInflater.from(this);
        LayoutInflater mInflaterHis = LayoutInflater.from(this);
        mFlowLayout.removeAllViews();
        for (int i = 0; i < mVals.length; i++)
        {
            TextView tv = (TextView) mInflater.inflate(R.layout.activity_shop_text_tv, mFlowLayout, false);
            tv.setText(mVals[i]);
            tv.setOnClickListener(this);
            mFlowLayout.addView(tv);
        }
        List<String> searchRecord= SearchRecordUtil.get(!"".equals(SPUtil.getPrefString("token",""))?SPUtil.getPrefString("token",""):SPUtil.getPrefString("token",""),getSharedPreferences("record", MODE_PRIVATE));
        if(searchRecord!=null){
            id_flowlayout_history.removeAllViews();
            for (String temp:searchRecord)
            {
                TextView tv = (TextView) mInflaterHis.inflate(R.layout.activity_shop_text_tv, id_flowlayout_history, false);
                tv.setText(temp);
                tv.setOnClickListener(this);
                id_flowlayout_history.addView(tv);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shop_to_find:
                String key=shop_find_detail.getText().toString();
                if("".equals(key.trim())){
                    Toast.makeText(JDShopFindActivity.this,"请输入搜索关键字",Toast.LENGTH_SHORT).show();
                }else{
                  Intent intent=new Intent(JDShopFindActivity.this,JDCouponGoodsListActivity.class);
                   intent.putExtra("so",key);
                  SearchRecordUtil.put(!"".equals(SPUtil.getPrefString("token",""))?SPUtil.getPrefString("token",""):SPUtil.getPrefString("token",""),key,getSharedPreferences("record", MODE_PRIVATE));
                   startActivity(intent);
                }
                break;
            case R.id.clearImage:
                shop_find_detail.setText("");
                clearImage.setVisibility(View.GONE);
                break;
            case R.id.clear_all:
                id_flowlayout_history.removeAllViews();
                SearchRecordUtil.clearAll(!"".equals(SPUtil.getPrefString("token",""))?SPUtil.getPrefString("token",""):SPUtil.getPrefString("token",""),getSharedPreferences("record", MODE_PRIVATE));
                break;
            default:
               TextView v1= (TextView) v;
                Intent intent=new Intent(JDShopFindActivity.this,JDCouponGoodsListActivity.class);
               intent.putExtra("so",v1.getText().toString());
               SearchRecordUtil.put(!"".equals(SPUtil.getPrefString("token",""))?SPUtil.getPrefString("token",""):SPUtil.getPrefString("token",""),v1.getText().toString(),getSharedPreferences("record", MODE_PRIVATE));
               startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
