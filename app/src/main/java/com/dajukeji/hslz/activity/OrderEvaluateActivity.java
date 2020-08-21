package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.order.OrderEvaluateBean;
import com.dajukeji.hslz.event.OrderChangeEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MyOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.OrderDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单评价 页面
 */

public class OrderEvaluateActivity extends HttpBaseActivity {

    private MyOrderPresenter myOrderPresenter;
    private RecyclerView xRecyclerView;
    private BaseRecyclerAdapter<OrderEvaluateBean.ContentBean.GclistBean> recyclerAdapter;

    private TextView tv_goods_release; // 发布
    private RelativeLayout title_bar_return;
    private long id; // 订单号
    private List<String> evaluateId = new ArrayList<>(); // 子订单号
    private Map<String,String> evaluate_info_x = new HashMap<>(); //评论列表
    private Map<String,String> is_satisfied_x = new HashMap<>(); //满意
    private Map<String,String> is_nickname_x = new HashMap<>(); // 匿名
    private List<EditText> et_info = new ArrayList<>(); // 产品评论

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        setContentView(R.layout.activity_order_evaluate);
        myOrderPresenter = new MyOrderPresenter(this);
        Bundle bundle =  getIntent().getExtras();
        id = bundle.getLong("id");
    }

    @Override
    protected void initView() {
        title_bar_return = (RelativeLayout) findViewById(R.id.title_bar_return);
        tv_goods_release = (TextView) findViewById(R.id.tv_goods_release);
        title_bar_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        xRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter = new BaseRecyclerAdapter<OrderEvaluateBean.ContentBean.GclistBean>(getContext(), null, R.layout.item_evaluate_content) {
            @Override
            public void convert(BaseRecyclerHolder holder, final OrderEvaluateBean.ContentBean.GclistBean data, int position, boolean isScrolling) {
                ImageView pict_url = holder.getView(R.id.pict_url);
                int width =  getResources().getDimensionPixelSize(R.dimen.x100);
                int height =  getResources().getDimensionPixelSize(R.dimen.y100);
                GlideEngine.loadThumbnails(getContext().getApplicationContext(),width,height, R.drawable.goods, pict_url, data.getMain_photo()); //产品图片
                holder.setText(R.id.tv_seller_description,data.getSeller_description());
                final CheckBox agree_checkBox =  holder.getView(R.id.agree_checkBox); // 认可
                final CheckBox not_agree_checkBox =  holder.getView(R.id.not_agree_checkBox); // 不认可
                final CheckBox nickname_checkBox =  holder.getView(R.id.nickname_checkBox); // 匿名
                LinearLayout ll_agree =  holder.getView(R.id.ll_agree);
                LinearLayout ll_not_agree =  holder.getView(R.id.ll_not_agree);
                LinearLayout ll_nickname =  holder.getView(R.id.ll_nickname);
                is_satisfied_x.put(Integer.toString(data.getGc_id()),"1"); // 默认认可
                is_nickname_x.put(Integer.toString(data.getGc_id()),"0"); // 默认不匿名

                ll_agree.setOnClickListener(new View.OnClickListener() { // 认可
                    @Override
                    public void onClick(View v) {
                        if(not_agree_checkBox.isChecked()){
                            agree_checkBox.setChecked(true);
                            not_agree_checkBox.setChecked(false);
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"1");
                        }else if(!agree_checkBox.isChecked() && !not_agree_checkBox.isChecked()){
                            agree_checkBox.setChecked(true);
                            not_agree_checkBox.setChecked(false);
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"1");
                        }else if( agree_checkBox.isChecked()){
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"1");
                        }
                    }
                });
                ll_not_agree.setOnClickListener(new View.OnClickListener() {// 不认可
                    @Override
                    public void onClick(View v) {
                        if( agree_checkBox.isChecked()){
                            agree_checkBox.setChecked(false);
                            not_agree_checkBox.setChecked(true);
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"0");
                        }else if(!agree_checkBox.isChecked() && !not_agree_checkBox.isChecked()){
                            not_agree_checkBox.setChecked(true);
                            agree_checkBox.setChecked(false);
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"0");
                        }else if(not_agree_checkBox.isChecked()){
                            is_satisfied_x.put(Integer.toString(data.getGc_id()),"0");
                        }
                    }
                });
                ll_nickname.setOnClickListener(new View.OnClickListener() { // 匿名
                    @Override
                    public void onClick(View v) {
                        if(nickname_checkBox.isChecked()){
                            nickname_checkBox.setChecked(false);
                            is_nickname_x.put(Integer.toString(data.getGc_id()),"0");
                        }else if(!nickname_checkBox.isChecked()){
                            nickname_checkBox.setChecked(true);
                            is_nickname_x.put(Integer.toString(data.getGc_id()),"1");
                        }
                    }
                });
                EditText et_goods_evaluate = holder.getView(R.id.et_goods_evaluate);
                et_info.add(et_goods_evaluate);
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<OrderEvaluateBean.ContentBean.GclistBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, OrderEvaluateBean.ContentBean.GclistBean data, int position) {
            }
        });
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList();

        tv_goods_release.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderDialog orderDialog = new OrderDialog(getContext(),"","是否发布评论?","确认");
                orderDialog.show();
                orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                    @Override
                    public void sureClick() {
                        if(!evaluateId.isEmpty()){
                            for(int i=0; i<evaluateId.size();i++){
                                evaluate_info_x.put(evaluateId.get(i),et_info.get(i).getText().toString());
                                myOrderPresenter.orderEvaluate(getContext(), SPUtil.getPrefString("token",""),id,evaluateId,evaluate_info_x,is_satisfied_x,is_nickname_x,DataType.myOrder.release.toString());
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList() {
        showDialogLoading();
        myOrderPresenter.toEvaluate(getContext(), SPUtil.getPrefString("token",""),id,DataType.myOrder.orderEvalute.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.myOrder.orderEvalute.toString())){
            hideDialogLoading();
            OrderEvaluateBean orderEvaluateBean = (OrderEvaluateBean) object;
            recyclerAdapter.setData(orderEvaluateBean.getContent().getGclist());
            initEvaluate(orderEvaluateBean.getContent().getGclist());
        }else if(contentType.equals(DataType.myOrder.release.toString())){
            hideDialogLoading();
            String sucess = (String) object;
            EventBus.getDefault().post(new OrderChangeEvent("change"));
            if (sucess.equals("sucess")){
                showToast("评价成功");
                finish();
            }
        }
    }

    /**
     * 要评价的订单
     * */
    private void initEvaluate(List<OrderEvaluateBean.ContentBean.GclistBean> gclistBean){
        for (OrderEvaluateBean.ContentBean.GclistBean data:gclistBean) {
            evaluateId.add(Integer.toString(data.getGc_id()));
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

}
