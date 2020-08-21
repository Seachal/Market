package com.dajukeji.hslz.activity.mine.favorite;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.fragment.mine.FavoriteGoodsFragment;
import com.dajukeji.hslz.fragment.mine.FavoriteShopFragment;
import com.dajukeji.hslz.view.NoScrollViewPager;

import java.util.ArrayList;

/**
 * 我的收藏
 */

public class FavoriteActivity extends HttpBaseActivity {

    private NoScrollViewPager noscroll_user_favorite;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private LinearLayout favorite_goods;
    private TextView tv_favorite_goods ,tv_goods_name;
    private LinearLayout favorite_shop;
    private TextView tv_favorite_shop ,tv_shop_name;

    private void initDate() {
        fragments.add(new FavoriteGoodsFragment());
        fragments.add(new FavoriteShopFragment());
    }

    @Override
    protected void initView() {
        initDate();
        setContentView(R.layout.activity_favorite);
        setTitleBar(R.string.text_favourite, true);
        noscroll_user_favorite = (NoScrollViewPager)findViewById(R.id.noscroll_user_favorite);
        favorite_goods = (LinearLayout) findViewById(R.id.favorite_goods);
        tv_favorite_goods = (TextView) findViewById(R.id.tv_favorite_goods);
        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        favorite_shop = (LinearLayout) findViewById(R.id.favorite_shop);
        tv_favorite_shop = (TextView) findViewById(R.id.tv_favorite_shop);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        noscroll_user_favorite.setAdapter(adapter);
        noscroll_user_favorite.setOffscreenPageLimit(fragments.size());
        favorite_goods.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv_favorite_shop.setVisibility(View.INVISIBLE);
                tv_favorite_goods.setVisibility(View.VISIBLE);
                tv_goods_name.setTextColor(Color.parseColor("#ff4f00"));
                tv_shop_name.setTextColor(Color.parseColor("#333333"));
                noscroll_user_favorite.setCurrentItem(0, false);
            }
        });
        favorite_shop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tv_favorite_goods.setVisibility(View.INVISIBLE);
                tv_favorite_shop.setVisibility(View.VISIBLE);
                tv_shop_name.setTextColor(Color.parseColor("#ff4f00"));
                tv_goods_name.setTextColor(Color.parseColor("#333333"));
                noscroll_user_favorite.setCurrentItem(1, false);
            }
        });

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
}
