package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.taocoupon.ShopFindActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.GoodsClassBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.fragment.mallFragment.FreeShippingFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.network.presenter.ShopFindPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 新品区
 */
public class NewActivity extends HttpBaseActivity {
    private LinearLayout linear, linear_two;
    private ImageView back;
    private GoodPresenter goodPresenter;
    private MessagePresenter messagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        linear = (LinearLayout) findViewById(R.id.linear);
        back = (ImageView) findViewById(R.id.back);
        linear_two = (LinearLayout) findViewById(R.id.linear_two);
        goodPresenter = new GoodPresenter(this);
        goodPresenter.getGoodClassList(getContext(), DataType.good.getGoodClassList.toString());
        messagePresenter = new MessagePresenter(this);
        messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token", ""), DataType.message.notWriteNo.toString());
        EventBus.getDefault().register(this);

        linear_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent messageIntent = new Intent(getActivity(), UserMessageActivity.class);
//                startActivity(messageIntent);
                if (!SPUtil.getPrefString("token", "").equals("")) {

                    Intent messageIntent = new Intent(NewActivity.this, UserMessageActivity.class);
                    startActivity(messageIntent);
                } else {
                    startActivity(new Intent(NewActivity.this, MobileActivity.class));
                }

            }
        });
        linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShopFindActivity.getStartIntent(NewActivity.this, ShopFindPresenter.FindType.NEW_LOCAL));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.good.getGoodClassList.toString())) {
            GoodsClassBean goodsClassBean = (GoodsClassBean) object;

            FreeShippingFragment freeShippingFragment = new FreeShippingFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("gc_id", goodsClassBean.getContent().getGoodsClass().get(1).getId());
            bundle.putBoolean("isSCJ_Show",true);//产品说要显示市场价来做对比
            freeShippingFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.framelayout, freeShippingFragment).commit();
//                mFragmentList.add(freeShippingFragment);

//            mAdapter = new shop_tab_adapter(getSupportFragmentManager(), mFragmentList, mTitleList);
//            mViewPager.setOffscreenPageLimit(mFragmentList.size());
//            mViewPager.setAdapter(mAdapter);
//            mTabLayout.setViewPager(mViewPager);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if (event.message.equals("message")) {
            messagePresenter.notWriteNo(getContext(), SPUtil.getPrefString("token", ""), DataType.message.notWriteNo.toString());
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        hideDialogLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        EventBus.getDefault().unregister(this);
    }

}

