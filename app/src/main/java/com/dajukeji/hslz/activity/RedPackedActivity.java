package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.RedbagBean;
import com.dajukeji.hslz.network.presenter.RedPackagePresenter;
import com.dajukeji.hslz.red.bezier.RedPacketsSurfaceVew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 抢红包页
 */
public class RedPackedActivity extends HttpBaseActivity {
    private int store;
    private RedPacketsSurfaceVew redPacketsSurfaceVew;
    private RelativeLayout title_bar_return;
    private boolean isRain = false;
    private List<RedbagBean.ContentBean.RedListBean> listBeans;
    private RedPackagePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packed);
//        setTitleBar("抢红包", true);
        redPacketsSurfaceVew = (RedPacketsSurfaceVew) findViewById(R.id.bezier_surface);
        title_bar_return = findViewById(R.id.title_bar_return);
        title_bar_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        init();
//        redPacketsSurfaceVew.post(new Runnable() {
//            @Override
//            public void run() {
//                redPacketsSurfaceVew.startRain();
//            }
//        });
        redPacketsSurfaceVew.setBackgroundResource(R.mipmap.bgg);

        //点击任意红包后触发
        redPacketsSurfaceVew.onRedPackageClick = new RedPacketsSurfaceVew.IOnRedPackageClick() {
            @Override
            public void onClick(RedbagBean.ContentBean.RedListBean bean) {
                //id没有实际使用，因为设计动画View时未考虑具体红包，只是发放所有红包

                RedbagBean.ContentBean.RedListBean beanTemp;

                /**
                 * 这里的循环导致了，只有红包列表中最后一个红包被拿到
                 */
//                for (RedbagBean.ContentBean.RedListBean bean : listBeans) {
//                    if (bean.getRed_id() == id){
//                        store = bean.getStore_id();
//                        money = "" + bean.getMoney();
//                        red_id = bean.getRed_id();
//                    }
//
//                }

                Log.d("konghongbao",bean.getRed_id()+"");

                Intent intent = new Intent(RedPackedActivity.this, BrandStoreDetailActivity.class);
                intent.putExtra("brand_id", bean.getStore_id());
                intent.putExtra("els", 1);
                intent.putExtra("red_id", bean.getRed_id());
//                intent.putExtra("money", money);//改用红包id get 金额
                RedPackedActivity.this.startActivity(intent);
//                presenter.getRedPackaged(this,beanTemp.get+"","把红包id传给服务器");
                finish();
            }
        };

        presenter = new RedPackagePresenter(this);
        presenter.getRedPackageList(this,"红包列表");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        redPacketsSurfaceVew.post(new Runnable() {
//            @Override
//            public void run() {
//                redPacketsSurfaceVew.startRain();
//            }
//        });
    }

    /**
     * 从服务器拿到数据了
     * @param object
     * @param contentType
     */
    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if("红包列表".equals(contentType)) {
            RedbagBean redbagBean = (RedbagBean) object;
            int y = 0;//红包数量
            listBeans = new ArrayList<>();
            listBeans = redbagBean.getContent().getRedList();
            for (int i = 0; i < listBeans.size(); i++) {
                y += listBeans.get(i).getMunber();//列表中红包的数量（每个店铺发送munber个红包，一共有listBeans.size个店铺 ）
                store = listBeans.get(i).getStore_id();//红包的店铺id
            }
            redPacketsSurfaceVew.numbers(y, store,listBeans);
            if (!isRain) {
                //BUG：先初始化，后开启，否则在发红包时发现没红包，就不循环发红包的动作了
                redPacketsSurfaceVew.startRain();
                Log.d("konghongbao:",listBeans.size()+"");
                isRain = true;
            }
//                        redPacketsSurfaceVew.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
////                                redPacketsSurfaceVew.performClick();
//                                Intent intent = new Intent(RedPackedActivity.this,BrandStoreDetailActivity.class);
//                                intent.putExtra("brand_id",store);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });
        }

    }

    private void init() {

    }
}