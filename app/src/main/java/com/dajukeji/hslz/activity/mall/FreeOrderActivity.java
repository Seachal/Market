package com.dajukeji.hslz.activity.mall;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.freeOrder.FreeOrderGoods;
import com.dajukeji.hslz.domain.javaBean.MessageCountBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.event.AddressChangeEvent;
import com.dajukeji.hslz.event.SelectAddressEvent;
import com.dajukeji.hslz.fragment.FreeOrder.MeFreeOrderFragment;
import com.dajukeji.hslz.fragment.FreeOrder.TodayFreeOrderFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FreeOrderPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.NoScrollViewPager;
import com.dajukeji.hslz.view.ReceivingAddressPopWindow;
import com.dajukeji.hslz.view.ShareFreeOrderGoodsPopWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


/**
 * 免单专区
 */
public class FreeOrderActivity extends HttpBaseActivity implements View.OnClickListener{

    private FreeOrderPresenter freeOrderPresenter;
    private MessagePresenter messagePresenter;
    private NoScrollViewPager noscroll_free_order;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private LinearLayout ll_today_free ,ll_me_free; // 今日免单，我的免单
    private TextView tv_today_free , tv_my_free; // 文本
    private ImageView iv_today_free , iv_my_free; // 图片

    private int setOffscreenPage = 0; // 当前页面偏移量
    private String choseFree = "today"; // 当前选择界面
    private UserAddressBean.ContentBean addressBean; // 用户地址

    public long goods_id; // 当前选择的产品ID
    public String sku; // 当前选择的产品规格
    public String type; // 当前选择类型
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        freeOrderPresenter =new FreeOrderPresenter(this);
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token",""),DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);
    }

    private void initDate() {
        fragments.add(new TodayFreeOrderFragment());
        fragments.add(new MeFreeOrderFragment());
    }

    @Override
    protected void initView() {
        initDate();
        setContentView(R.layout.activity_free_order);
        setTitleBar(R.string.text_free_order,true,0,0);
        noscroll_free_order = (NoScrollViewPager)findViewById(R.id.noscroll_free_order);
        ll_today_free = (LinearLayout) findViewById(R.id.ll_today_free);  // 今日免单
        ll_me_free = (LinearLayout) findViewById(R.id.ll_me_free);  //我的免单
        tv_today_free = (TextView) findViewById(R.id.tv_today_free);
        tv_my_free = (TextView) findViewById(R.id.tv_my_free);
        iv_today_free = (ImageView) findViewById(R.id.iv_today_free);
        iv_my_free = (ImageView) findViewById(R.id.iv_my_free);

        noscroll_free_order.setAdapter(adapter);
        String type = getIntent().getStringExtra("type");
        if(type!=null&&!type.equals("")){
            choseFree = type;
            tv_my_free.setTextColor(Color.rgb(15, 15, 15));
            tv_today_free.setTextColor(Color.rgb(87, 87, 87));
            iv_my_free.setImageResource(R.drawable.userfreetab_btn_sel);
            iv_today_free.setImageResource(R.drawable.freetab_btn_nor);
            noscroll_free_order.setCurrentItem(1, false);
        }
        ll_today_free.setOnClickListener(this);
        ll_me_free.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_today_free: // 今日免单
                if(choseFree.equals("me")){  // 如果当前页为我的免单，切换为今日免单
                    tv_today_free.setTextColor(Color.rgb(15, 15, 15));
                    tv_my_free.setTextColor(Color.rgb(87, 87, 87));
                    iv_today_free.setImageResource(R.drawable.freetab_btn_sel);
                    iv_my_free.setImageResource(R.drawable.userfreetab_btn_nor);
                }
                choseFree = "today";
                noscroll_free_order.setCurrentItem(0, false);
                break;
            case R.id.ll_me_free: // 我的免单
                if(choseFree.equals("today")){ // 如果当前页为今日免单，切换为我的免单
                    tv_my_free.setTextColor(Color.rgb(15, 15, 15));
                    tv_today_free.setTextColor(Color.rgb(87, 87, 87));
                    iv_my_free.setImageResource(R.drawable.userfreetab_btn_sel);
                    iv_today_free.setImageResource(R.drawable.freetab_btn_nor);
                }
                choseFree = "me";
                noscroll_free_order.setCurrentItem(1, false);
                break;
        }

    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if(contentType.equals(DataType.freeOrder.createInvitee.toString())){
            showToast("兑换成功");
        }else if(contentType.equals(DataType.freeOrder.createFree.toString())){
            hideDialogLoading();
            FreeOrderGoods freeOrderGoods = (FreeOrderGoods) object;
            ShareFreeOrderGoodsPopWindow shareFreeOrderGoodsPopWindow = new ShareFreeOrderGoodsPopWindow(this,freeOrderGoods);
            shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
        }else if(contentType.equals(DataType.freeOrder.createInvitee.toString())){
            showToast("兑换成功");
        }else if(contentType.equals(DataType.message.notWriteNo.toString())){
            hideDialogLoading();
            MessageCountBean countBean = (MessageCountBean) object;
            ((TextView) $(R.id.title_bar_notice_num)).setVisibility(View.VISIBLE);
            ((TextView) $(R.id.title_bar_notice_num)).setText(String.valueOf(countBean.getContent().getNumber()));
        }else if(contentType.equals(DataType.freeOrder.getDefaultAddress.toString())){
            hideDialogLoading();
            UserAddressBean userAddressBean = (UserAddressBean) object;
            ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this,userAddressBean.getContent(),this.goods_id,this.sku,type);
            shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
            shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
                @Override
                public void createFree(long goodId, String sku, long addressId,String type) {
                    showDialogLoading();
                    freeOrderPresenter.createFreeOrder(getContext(),goodId,sku,addressId,SPUtil.getPrefString("token",""),DataType.freeOrder.createFree.toString());
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SelectAddressEvent event) {
        UserAddressBean.ContentBean useraddressbean = event.userAddressBean;
        ReceivingAddressPopWindow shareFreeOrderGoodsPopWindow = new ReceivingAddressPopWindow(this,useraddressbean,this.goods_id,this.sku,type);
        shareFreeOrderGoodsPopWindow.showAtLocation(getCurrentFocus(), Gravity.BOTTOM,0,0);
        shareFreeOrderGoodsPopWindow.setBackgroundDrawable(new BitmapDrawable());
        shareFreeOrderGoodsPopWindow.setOnClickOkListener(new ReceivingAddressPopWindow.OnClickOkListener() {
            @Override
            public void createFree(long id, String sku, long addressId,String type) {
                if(type.equals("invitee")){
                    freeOrderPresenter.createInviteeFreeOrder(getContext(),id,addressId, SPUtil.getPrefString("token",""), DataType.freeOrder.createInvitee.toString());
                }else if(type.equals("freeOrder")){
                    freeOrderPresenter.createFreeOrder(getContext(),id,sku,addressId,SPUtil.getPrefString("token",""),DataType.freeOrder.createFree.toString());
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AddressChangeEvent event) {
        freeOrderPresenter.getDefaultAddress(getContext(),SPUtil.getPrefString("token",""),DataType.freeOrder.getDefaultAddress.toString());
    }

    /**
     * viewpager适配器
     */
    private PagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }
}
