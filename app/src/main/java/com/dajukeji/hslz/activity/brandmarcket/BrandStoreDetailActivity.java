package com.dajukeji.hslz.activity.brandmarcket;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.RedPackedActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.RedPackageMoneyBean;
import com.dajukeji.hslz.domain.javaBean.StoreIndexBean;
import com.dajukeji.hslz.fragment.StoreGoodListFragment;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.BrandPresenter;
import com.dajukeji.hslz.network.presenter.FavoritePresenter;
import com.dajukeji.hslz.network.presenter.RedPackagePresenter;
import com.dajukeji.hslz.util.LoadingDialog;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.TokenUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/3 0003.
 */

public class BrandStoreDetailActivity extends HttpBaseActivity {
    private int brand_id;
    private LinearLayout customer_service_ll, collect_store_ll;
    private TabLayout good_class_tab;
    private ViewPager viewPager;
    private BrandPresenter brandPresenter;
    private FavoritePresenter favoritePresenter;
    private RedPackagePresenter redPackagePresenter;
    private List<String> list_title;
    private List<Fragment> fragmentList;
    private ImageView brand_store_icon;
    private TextView brand_item_store_name, store_type, store_level, store_speed, store_dis_tt;
    private CoordinatorLayout content_layout;
    private String chat_id = "";
    private ImageView collect_store_iv;
    private int is_collected;   //收藏的状态：-1门店信息为空；0未收藏；1已收藏
    private String storename;
    private RelativeLayout title_bar_return;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        setContentView(R.layout.activity_brand_store_detail);
//        setTitleBar(getResources().getString(R.string.store_detail), true);
        collect_store_iv = (ImageView) findViewById(R.id.collect_store_iv);
        store_dis_tt = (TextView) findViewById(R.id.store_dis_tt);
        brand_store_icon = (ImageView) findViewById(R.id.brand_store_icon);
        brand_item_store_name = (TextView) findViewById(R.id.brand_item_store_name);
        store_type = (TextView) findViewById(R.id.store_type);
        store_level = (TextView) findViewById(R.id.store_level);
        store_speed = (TextView) findViewById(R.id.store_speed);
        customer_service_ll = (LinearLayout) findViewById(R.id.customer_service_ll);
        collect_store_ll = (LinearLayout) findViewById(R.id.collect_store_ll);
        good_class_tab = (TabLayout) findViewById(R.id.good_class_tab);
        good_class_tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        content_layout = (CoordinatorLayout) findViewById(R.id.content_layout);
        title_bar_return = findViewById(R.id.title_bar_return);
        title_bar_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        collect_store_ll.setOnClickListener(this);
        customer_service_ll.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.customer_service_ll:
//                if (!SPUtil.getPrefString("chatId", "").equals("") && !SPUtil.getPrefString("chatSig", "").equals("")) {
//                    if (!chat_id.equals("")) {
//                        ChatActivity.navToChat(this, chat_id, TIMConversationType.C2C);
//                    }
//                } else {
//                    startActivity(new Intent(getContext(), WeChatLoginActivity.class));
//                }
                TokenUtil.openChat(getContext(), chat_id, storename);
//                showToast("客服");
                break;
            case R.id.collect_store_ll:
                switch (is_collected) {
                    case -1:

                        break;
                    case 0:
                        favoritePresenter.addCollect(BrandStoreDetailActivity.this, SPUtil.getPrefString("token", ""), brand_id, Constants.collectStore, DataType.collect.addCollect.toString());
                        break;
                    case 1:
                        favoritePresenter.deleteFavorite(BrandStoreDetailActivity.this, SPUtil.getPrefString("token", ""), Constants.collectStore, brand_id, DataType.collect.deleteCollect.toString());
                        break;
                }
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        brand_id = getIntent().getIntExtra("brand_id", 0);
        int red_id = getIntent().getIntExtra("red_id", 0);
        //查询到实际红包金额后再弹出dialog
        redPackagePresenter = new RedPackagePresenter(this);
        redPackagePresenter.grabRedPackage(this, red_id, SPUtil.getPrefString("token", ""), "查询红包金额");
        Log.d("hongbaoTOKEN",SPUtil.getPrefString("token", "")+"  "+red_id);
        brandPresenter = new BrandPresenter(this);
        favoritePresenter = new FavoritePresenter(this);
        fragmentList = new ArrayList<>();
        list_title = new ArrayList<>();
        brandPresenter.getStoreIndex(BrandStoreDetailActivity.class, brand_id, DataType.brand.storeIndex.toString());
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.brand.storeIndex.toString())) {
            StoreIndexBean storeIndexBean = (StoreIndexBean) object;
            if (storeIndexBean.getMessage().equals(getResources().getString(R.string.store_index)) && storeIndexBean.getStatus().equals("0")) {
                content_layout.setVisibility(View.VISIBLE);
                storename = storeIndexBean.getContent().getStore_info().getStore_name();
                StoreIndexBean.ContentEntity.Store_infoEntity storeInfo = storeIndexBean.getContent().getStore_info();
                if (storeInfo.getStore_info() == null || storeInfo.getStore_info().equals("")) {
                    store_dis_tt.setText("暂无描述");
                } else {
                    store_dis_tt.setText(storeInfo.getStore_info());
                }
                store_level.setText(storeInfo.getStore_level());
                store_type.setText(storeInfo.getStore_type());
                store_speed.setText(storeInfo.getStore_speed());
                brand_item_store_name.setText(storeInfo.getStore_name());
                is_collected = storeInfo.getStore_collection();
                if (storeInfo.getStore_collection() == 1) {
                    collect_store_iv.setImageResource(R.drawable.me_collection);
                } else {
                    collect_store_iv.setImageResource(R.drawable.icon_collection_star);
                }
                int width = getResources().getDimensionPixelSize(R.dimen.x110);
                int height = getResources().getDimensionPixelSize(R.dimen.y110);
                GlideEngine.loadThumbnail(getContext(), width, height, R.drawable.goods, brand_store_icon, storeInfo.getStore_logo());
                chat_id = storeIndexBean.getContent().getStore_info().getChat_id();
                list_title.add(getResources().getString(R.string.store_hot_good));
                Bundle b = new Bundle();
                b.putInt(Constants.store_id, brand_id);
                b.putInt(Constants.gc_id, -1);
                StoreGoodListFragment indexGoodList = new StoreGoodListFragment();
                indexGoodList.setArguments(b);
                fragmentList.add(indexGoodList);
                List<StoreIndexBean.ContentEntity.Store_goods_class_listEntity> gc_list = storeIndexBean.getContent().getStore_goods_class_list();
                for (StoreIndexBean.ContentEntity.Store_goods_class_listEntity classListBean : gc_list) {
                    list_title.add(classListBean.getName());
                    StoreGoodListFragment storeGoodListFragment = new StoreGoodListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.store_id, brand_id);
                    bundle.putInt(Constants.gc_id, classListBean.getId());
                    storeGoodListFragment.setArguments(bundle);
                    fragmentList.add(storeGoodListFragment);
                    good_class_tab.addTab(good_class_tab.newTab().setText(classListBean.getName()));
                }
                viewPager.setOffscreenPageLimit(gc_list.size());
                viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), fragmentList, list_title));
                good_class_tab.setupWithViewPager(viewPager);
            } else {
                showHttpError("", DataType.brand.storeIndex.toString());
            }

        } else if (contentType.equals(DataType.collect.addCollect.toString())) {
            showToast(getResources().getString(R.string.add_collect_success));
            is_collected = 1;
            collect_store_iv.setImageResource(R.drawable.me_collection);
        } else if (contentType.equals(DataType.collect.deleteCollect.toString())) {
            showToast("取消成功");
            is_collected = 0;
            collect_store_iv.setImageResource(R.drawable.icon_collection_star);
        } else if (contentType.equals("查询红包金额")) {
            int num = getIntent().getIntExtra("els", 0);
            redPackagePresenter.getRedPackaged(this,((RedPackageMoneyBean) object).getContent().getRecord_id()+"","红包领取后返回给服务器");
            Log.d("hongbao dialog",((RedPackageMoneyBean) object).getContent().getRecord_id()+"");
            Log.d("hongbao dialog",((RedPackageMoneyBean) object).getStatus());
            Log.d("hongbao dialog",((RedPackageMoneyBean) object).getContent().getMoney()+" "+((RedPackageMoneyBean) object).getContent().getRecord_id());
            double money = ((RedPackageMoneyBean) object).getContent().getMoney();
            Dialog dialog = LoadingDialog.getLoadingDialog(BrandStoreDetailActivity.this, String.format("%.2f", money));
            if (num == 1) {
                dialog.show();
                title_bar_return.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(BrandStoreDetailActivity.this, RedPackedActivity.class));
                        finish();
                    }
                });
            } else {
                title_bar_return.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    startActivity(new Intent(BrandStoreDetailActivity.this, RedPackedActivity.class));
                        finish();
                    }
                });
            }
        }else if (contentType.equals("红包领取后返回给服务器")){
            try {
                Log.d("hongbao linqu",((JSONObject)object).get("status").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        if (dataType.equals(DataType.brand.storeIndex.toString())) {
            content_layout.setVisibility(View.GONE);
        }
    }

    class PagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList;
        List<String> titleList;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titleList = titleList;
        }

        @Override

        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position % list_title.size());
        }
    }
}
