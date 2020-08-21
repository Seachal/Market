package com.dajukeji.hslz.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.IntroduceImgBean;
import com.dajukeji.hslz.network.presenter.IntroduceImgPresenter;
import com.dajukeji.hslz.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/8 0008.
 */

public class GuideActivity extends HttpBaseActivity {

    private IntroduceImgPresenter presenter;
    private ViewPager splashViewPager;
    private View dot_one, dot_two, dot_three, dot_four;
    private List<ImageView> imageViewList;
    private Adapter madapter;
    private int currentItem;
    private int[] resourceArray = {R.drawable.guide_img_one, R.drawable.guide_img_two, R.drawable.guide_img_three, R.drawable.guide_img_four};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashViewPager = (ViewPager) findViewById(R.id.viewpager);
        imageViewList = new ArrayList<>();
        dot_one = findViewById(R.id.dot_one);
        dot_two = findViewById(R.id.dot_two);
        dot_three = findViewById(R.id.dot_three);
        dot_four = findViewById(R.id.dot_four);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < resourceArray.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundColor(Color.WHITE);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            Glide.with(this).load(resourceArray[i]).into(imageView);//改为使用网络图
            this.imageViewList.add(imageView);
        }
        madapter = new Adapter(imageViewList, this);
        splashViewPager.setAdapter(madapter);
        splashViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LogUtil.info("scrollposition", "positionOffsetPixels"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                switch (position) {
                    case 0:
                        selectOne();
                        break;
                    case 1:
                        selectTwo();
                        break;
                    case 2:
                        selectThree();
                        break;
                    case 3:
                        selectFour();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        splashViewPager.setOnTouchListener(new View.OnTouchListener() {
            float startX, endX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                }
                if (endX < startX && currentItem == resourceArray.length - 1) {
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    SPUtil.setPrefBoolean("FirstRun", false);
                    finish();
                }
                return false;
            }
        });

        //动态加载引导页
        presenter = new IntroduceImgPresenter(this);
        presenter.getIntroduceImg(this, "引导图片");
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "引导图片":
                IntroduceImgBean bean = (IntroduceImgBean) object;
                if ("0".equals(bean.getStatus())) {
                    for (int i = 0; i < bean.getContent().getStartupPageList().size() && i < 4; i++) {
                        String pic_url = bean.getContent().getStartupPageList().get(i).getPic_url();
                        Glide.with(this).load(pic_url).into(imageViewList.get(i));
                    }
                }
                break;
        }
    }

    private void selectOne() {
        dot_one.setBackgroundResource(R.drawable.shape_dot_select);
        dot_two.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_three.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_four.setBackgroundResource(R.drawable.shape_dot_unselect);
    }

    private void selectTwo() {
        dot_one.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_two.setBackgroundResource(R.drawable.shape_dot_select);
        dot_three.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_four.setBackgroundResource(R.drawable.shape_dot_unselect);
    }

    private void selectThree() {
        dot_one.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_two.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_three.setBackgroundResource(R.drawable.shape_dot_select);
        dot_four.setBackgroundResource(R.drawable.shape_dot_unselect);
    }

    private void selectFour() {
        dot_one.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_two.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_three.setBackgroundResource(R.drawable.shape_dot_unselect);
        dot_four.setBackgroundResource(R.drawable.shape_dot_select);
    }

    class Adapter extends PagerAdapter {
        private List<ImageView> resource;
        private Context context;

        private Adapter(List<ImageView> resource, Context context) {
            this.resource = resource;
            this.context = context;
        }

        @Override
        public int getCount() {
            return resource.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position));
            return resource.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            SPUtil.setPrefBoolean("FirstRun", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
