package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luck.picture.lib.tools.ScreenUtils;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.adapter.recycleradapter.CommonBaseAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.ViewHolder;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.SubsidyShareBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FavoritePresenter;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.network.presenter.SubsidyPresenter;
import com.dajukeji.hslz.util.MeasureWidgetHeight;
import com.dajukeji.hslz.util.MyAdGallery;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.TokenUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.SubsidySharePopWindow;
import com.dajukeji.hslz.view.dialog.SkuDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/2 0002.
 * 评选区的产品，点击之后就会跳到这边来
 */

public class SubsidyGoodDetailActivity extends HttpBaseActivity {

    private int goods_id;
    private GoodPresenter goodPresenter;
    private FavoritePresenter favoritePresenter;
    private SubsidyPresenter subsidyPresenter;
    private NestedScrollView good_detail_scrollview;
    private RecyclerView goods_detail_recommend_recycle;
    private RelativeLayout goods_detail_babe_rl, goods_detail_rl, goods_detail_evaluation_rl, goods_detail_recommend_rl;
    private ImageView goods_detail_babe_iv, goods_detail_iv, goods_detail_evaluation_iv, goods_detail_recommend_iv;
    private TextView goods_detail_babe_tt, goods_detail_tt, goods_detail_evaluation_tt, goods_detail_recommend_tt;
    private SwipeRefreshLayout goods_detail_refresh;
    private MyAdGallery goods_detail_head_banner;
    private TextView goods_current_price, goods_price, sale_info_tt, goods_name;
    private LinearLayout tab_list;
    private LinearLayout goods_detail_share_ll, goods_detail_collection_ll, goods_detail_mixpool;
    private RelativeLayout see_more_rl;
    private TextView goods_detail_evaluate;
    private RelativeLayout see_all_rl;
    private RecyclerView goods_detail_evaluate_recycle, group_by_people_info;
    private TextView goode_detail_group_by_num;
    private WebView goods_detail_webview;
    private MeasureWidgetHeight measureWidgetHeight;
    private TextView goods_detail_describe; // 店家自
    // 诉
    private TextView tv_goods_evaluate;
    private TextView tv_subsidy_buy; // 发起补贴
/*    private TextView tv_subsidy_immediately; // 立即购买*/
    private int evaluateCount; // 全部评论数
    private ImageView store_img; // 店铺ICON
    private TextView store_name_tt; // 店铺名称
    private TextView store_status, store_level, store_speed; // 店铺描述
    private TextView tv_go_shop; // 进入店铺
    private TextView store_dis_tt; // 店铺描述
    private ImageView iv_collect;
    private String chat_id = "";
    private int height1, height2, height3;
    private int scrollDistance = 0;
    private LinearLayout good_detail_info_ll, gooddetail_weblayout, good_detail_evaluate_ll, good_detail_recommend_ll;
    private LinearLayout content_layout;
    private ImageView go_top_iv;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private String storename;

    private String pingxuan = "";

    private static final String yugao = "yugao";

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        super.loadLayout(savedInstanceState);
        pingxuan = getIntent().getStringExtra("pingxuan");
        favoritePresenter = new FavoritePresenter(this);
        goods_id = getIntent().getIntExtra("goods_id", 0);
        goodPresenter = new GoodPresenter(this);
        goodPresenter.getGoodDetail(getContext(), goods_id, DataType.myPresenterType.getGoodDetail.toString());
        subsidyPresenter = new SubsidyPresenter(this);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_subsidy_good_details);
        setTitleBar(R.string.goods_detail, true);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        go_top_iv = (ImageView) findViewById(R.id.go_top_iv);
        reload_rl = (RelativeLayout) findViewById(R.id.reload_rl);
        reload_button = (Button) findViewById(R.id.reload_button);
        go_top_iv.setOnClickListener(this);
        reload_button.setOnClickListener(this);
        tv_subsidy_buy = (TextView) findViewById(R.id.tv_subsidy_buy);
        tv_subsidy_buy.setOnClickListener(this);
        gooddetail_weblayout = (LinearLayout) findViewById(R.id.gooddetail_weblayout);
        good_detail_recommend_ll = (LinearLayout) findViewById(R.id.good_detail_recommend_ll);
        good_detail_info_ll = (LinearLayout) findViewById(R.id.good_detail_info_ll);
        good_detail_evaluate_ll = (LinearLayout) findViewById(R.id.good_detail_evaluate_ll);
        good_detail_recommend_ll = (LinearLayout) findViewById(R.id.good_detail_recommend_ll);
        goods_detail_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        goods_detail_head_banner = (MyAdGallery) findViewById(R.id.goods_detail_head_banner);
        goode_detail_group_by_num = (TextView) findViewById(R.id.goode_detail_group_by_num);
        goods_detail_webview = (WebView) findViewById(R.id.goods_detail_webview);
        WebSettings webSettings = goods_detail_webview.getSettings();
//        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        /*
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        tab_list = (LinearLayout) findViewById(R.id.tab_list);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);
        good_detail_scrollview = (NestedScrollView) findViewById(R.id.good_detail_scrollview);
        goods_detail_babe_rl = (RelativeLayout) findViewById(R.id.goods_detail_babe_rl);
        goods_detail_rl = (RelativeLayout) findViewById(R.id.goods_detail_rl);
        goods_detail_evaluation_rl = (RelativeLayout) findViewById(R.id.goods_detail_evaluation_rl);
        goods_detail_recommend_rl = (RelativeLayout) findViewById(R.id.goods_detail_recommend_rl);
        goods_detail_babe_iv = (ImageView) findViewById(R.id.goods_detail_babe_iv);
        goods_detail_iv = (ImageView) findViewById(R.id.goods_detail_iv);
        goods_detail_evaluation_iv = (ImageView) findViewById(R.id.goods_detail_evaluation_iv);
        goods_detail_recommend_iv = (ImageView) findViewById(R.id.goods_detail_recommend_iv);
        goods_detail_babe_tt = (TextView) findViewById(R.id.goods_detail_babe_tt);
        goods_detail_tt = (TextView) findViewById(R.id.goods_detail_tt);
        goods_detail_evaluation_tt = (TextView) findViewById(R.id.goods_detail_evaluation_tt);
        goods_detail_recommend_tt = (TextView) findViewById(R.id.goods_detail_recommend_tt);
        goods_detail_babe_rl.setOnClickListener(this);
        goods_detail_rl.setOnClickListener(this);
        goods_detail_evaluation_rl.setOnClickListener(this);
        goods_detail_recommend_rl.setOnClickListener(this);
        goods_current_price = (TextView) findViewById(R.id.goods_current_price);
        goods_price = (TextView) findViewById(R.id.goods_price);
        sale_info_tt = (TextView) findViewById(R.id.sale_info_tt);
        goods_name = (TextView) findViewById(R.id.goods_name);
//        goods_detial_tab = (ImageView) findViewById(R.id.goods_detial_tab);
        goods_detail_share_ll = (LinearLayout) findViewById(R.id.goods_detail_share_ll);
        goods_detail_share_ll.setOnClickListener(this);
        goods_detail_collection_ll = (LinearLayout) findViewById(R.id.goods_detail_collection_ll);
        goods_detail_collection_ll.setOnClickListener(this);
        goods_detail_mixpool = (LinearLayout) findViewById(R.id.goods_detail_mixpool);
        goods_detail_mixpool.setOnClickListener(this);
        group_by_people_info = (RecyclerView) findViewById(R.id.group_by_people_info);
        see_more_rl = (RelativeLayout) findViewById(R.id.see_more_rl);
        see_more_rl.setOnClickListener(this);
        goods_detail_evaluate_recycle = (RecyclerView) findViewById(R.id.goods_detail_evaluate_recycle);
        see_all_rl = (RelativeLayout) findViewById(R.id.see_all_rl);
        goods_detail_evaluate = (TextView) findViewById(R.id.goods_detail_evaluate);
        goods_detail_recommend_recycle = (RecyclerView) findViewById(R.id.goods_detail_recommend_recycle);
        goods_detail_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                goodPresenter.getGoodDetail(getContext(), goods_id, DataType.myPresenterType.getGoodDetail.toString());
            }
        });
        good_detail_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                getWidgetHeight();
                scrollDistance = scrollY;
                if (scrollY >= height1 && scrollY < height2) {
                    renderingWhitchRL(2);
                } else if (scrollY >= height2 && scrollY < height3) {
                    renderingWhitchRL(3);
                } else if (scrollY >= height3) {
                    renderingWhitchRL(4);
                } else {
                    renderingWhitchRL(1);
                }
                if (scrollY>height1) {
                    go_top_iv.setVisibility(View.VISIBLE);
                } else {
                    go_top_iv.setVisibility(View.GONE);
                }
            }
        });
        goods_detail_describe = (TextView) findViewById(R.id.goods_detail_describe);
        tv_goods_evaluate = (TextView) findViewById(R.id.tv_goods_evaluate);

        store_img = (ImageView) findViewById(R.id.store_img);
        store_name_tt = (TextView) findViewById(R.id.store_name_tt);
        store_status = (TextView) findViewById(R.id.store_status);
        store_level = (TextView) findViewById(R.id.store_level);
        store_speed = (TextView) findViewById(R.id.store_speed);
        tv_go_shop = (TextView) findViewById(R.id.tv_go_shop);
        store_dis_tt = (TextView) findViewById(R.id.store_dis_tt);
        tv_go_shop.setOnClickListener(this); // 进入店铺
        iv_collect = (ImageView) findViewById(R.id.iv_collect);
    }


    @Override
    protected boolean refreshNoList() {
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.goods_detail_mixpool:
//                if (!SPUtil.getPrefString("chatId", "").equals("") && !SPUtil.getPrefString("chatSig", "").equals("")) {
//                    if (!chat_id.equals("")) {
//                        ChatActivity.navToChat(this, chat_id, TIMConversationType.C2C);
//                    }
//                } else {
//                    startActivity(new Intent(getContext(), WeChatLoginActivity.class));
//                }
                TokenUtil.openChat(getContext(), chat_id, storename);
                break;
            case R.id.goods_detail_share_ll:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
            case R.id.goods_detail_babe_rl:
                renderingWhitchRL(1);
                getWidgetHeight();
                good_detail_scrollview.stopNestedScroll();
                good_detail_scrollview.scrollTo(0, 0);
                break;
            case R.id.goods_detail_evaluation_rl:
                renderingWhitchRL(2);
                getWidgetHeight();
                good_detail_scrollview.stopNestedScroll();
                good_detail_scrollview.scrollTo(0, height1);
                break;
            case R.id.goods_detail_rl:
                renderingWhitchRL(3);
                getWidgetHeight();
                good_detail_scrollview.stopNestedScroll();
                good_detail_scrollview.scrollTo(0, height2);
                break;
            case R.id.goods_detail_recommend_rl:
                renderingWhitchRL(4);
                getWidgetHeight();
                good_detail_scrollview.stopNestedScroll();
                good_detail_scrollview.scrollTo(0, height3);
                break;
            case R.id.see_all_rl:
                Intent seeAllEvaluate = new Intent(this, EvaluateAllActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("goods_id", goods_id + "");
                bundle.putString("count", evaluateCount + "");
                seeAllEvaluate.putExtras(bundle);
                startActivity(seeAllEvaluate);
                break;
            case R.id.tv_subsidy_buy:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (goodDetailsBean != null) {
                            if (pingxuan.equals(yugao)){
                                return;
                            }
                            selectSku("subsidy_buy");
                        }
                    } else {
                        startActivity(new Intent(this, MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(this, WeChatLoginActivity.class));
                }
                break;
            case R.id.goods_detail_collection_ll:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (null != goodDetailsBean) {
                            if (goodDetailsBean.getContent().getCollect_flag() == 1) {
                                favoritePresenter.deleteFavorite(getContext(), SPUtil.getPrefString("token", ""), "0", goodDetailsBean.getContent().getGoods_id(), DataType.favorite.delete.toString());
                            } else {
                                favoritePresenter.addCollect(getContext(), SPUtil.getPrefString("token", ""), goodDetailsBean.getContent().getGoods_id(), Constants.collectGoods, DataType.collect.addCollect.toString());
                            }
                        }
                    } else {
                        startActivity(new Intent(this, MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(this, WeChatLoginActivity.class));
                }
                break;
            case R.id.tv_go_shop:
                Intent intent = new Intent(this, BrandStoreDetailActivity.class);
                intent.putExtra("brand_id", goodDetailsBean.getContent().getStore().getStore_id());
                startActivity(intent);
                break;
            case R.id.reload_button:
                reload_rl.setVisibility(View.GONE);
                goodPresenter.getGoodDetail(getContext(), goods_id, DataType.myPresenterType.getGoodDetail.toString());
                break;
            case R.id.go_top_iv:
                go_top_iv.setVisibility(View.GONE);
                good_detail_scrollview.scrollTo(0, 0);
                break;
        }
    }

    private GoodDetailsBean goodDetailsBean;


    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        if (contentType.equals(DataType.myPresenterType.getGoodDetail.toString())) {
            renderingWhitchRL(1);
            content_layout.setVisibility(View.VISIBLE);
            reload_rl.setVisibility(View.GONE);
            go_top_iv.setVisibility(View.GONE);
            final GoodDetailsBean goodDetails = (GoodDetailsBean) object;
            goodDetailsBean = goodDetails;
            storename = goodDetails.getContent().getStore().getStore_name();
            chat_id = goodDetailsBean.getContent().getStore().getChat_id();
            goods_detail_webview.loadUrl(goodDetails.getContent().getGoods_details());
            goods_detail_webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    getWidgetHeight();
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });

/*            tv_subsidy_immediately.setText(getResources().getString(R.string.rmb_symbol) + goodDetails.getContent().getGoods_price() + "\n" + "直接购买");*/
            if (!pingxuan.equals(yugao)){
                tv_subsidy_buy.setText(getResources().getString(R.string.rmb_symbol) + goodDetails.getContent().getLowest_price() + "\n" + "发起补贴");
            }else {
                tv_subsidy_buy.setText("敬请期待");
            }


            goods_current_price.setText(getResources().getString(R.string.rmb_symbol) + String.format("%.2f", goodDetails.getContent().getGoods_price()));
            goods_current_price.setTextColor(Color.parseColor("#000000"));
            goods_current_price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            goods_price.setTextColor(Color.parseColor("#ff4f00"));
            goods_price.setText("--> " + getResources().getString(R.string.rmb_symbol) + goodDetails.getContent().getLowest_price());
            goods_price.setTextSize(15);
            goods_price.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

            sale_info_tt.setText("已销售" + goodDetails.getContent().getGoods_salenum() + "件");
            goods_name.setText(goodDetails.getContent().getGoods_name());
            //评论列表
            if (goodDetails.getContent().getEvaluateList() != null && goodDetails.getContent().getEvaluateList().size() > 0) {
                goods_detail_evaluate_recycle.setLayoutManager(new LinearLayoutManager(getContext()));
                see_all_rl.setVisibility(View.VISIBLE);
                this.evaluateCount = goodDetails.getContent().getEvaluate_size();
                goods_detail_evaluate.setText("(" + goodDetails.getContent().getEvaluate_size() + ")");
                see_all_rl.setOnClickListener(this);
                goods_detail_evaluate_recycle.setAdapter(new CommonBaseAdapter<GoodDetailsBean.ContentBean.EvaluateListBean>(
                        getContext(), goodDetails.getContent().getEvaluateList(), false) {
                    @Override
                    protected void convert(ViewHolder holder, GoodDetailsBean.ContentBean.EvaluateListBean data) {
                        holder.setText(R.id.people_name_tt, data.getUser_name());
                        holder.setText(R.id.people_time_tt, data.getAdd_time());
                        holder.setText(R.id.evaluate_tt, data.getEvaluate_info());
                        holder.setText(R.id.goods_detaim_evaluate_bottom, data.getGoods_spec());
                        holder.setImageWithGlide(getContext(), R.id.people_head_img, data.getHead_image());
                    }

                    @Override
                    protected int getItemLayoutId() {
                        return R.layout.item_goods_detail_evaluate;
                    }
                });

            } else {
                tv_goods_evaluate.setVisibility(View.VISIBLE);
            }
            // 是否收藏
            if (goodDetails.getContent().getCollect_flag() == 1) {
                GlideEngine.loadThumbnail(getContext(), R.drawable.collectiontab_btn_nor, iv_collect, R.drawable.collectiontab_btn_sel);
            }
            // 店铺
            int size = getResources().getDimensionPixelSize(R.dimen.x110);
            GlideEngine.loadThumbnail(this, size, R.drawable.goods, store_img,goodDetails.getContent().getStore().getStore_logo());
            store_name_tt.setText(goodDetails.getContent().getStore().getStore_name());
            store_status.setText(goodDetails.getContent().getStore().getStore_type());
            store_level.setText(goodDetails.getContent().getStore().getStore_level());
            store_speed.setText(goodDetails.getContent().getStore().getStore_speed());
            if (goodDetails.getContent().getStore().getStore_info() == null || goodDetails.getContent().getStore().getStore_info().equals("")) {
                store_dis_tt.setText("暂无描述");
            } else {
                store_dis_tt.setText(goodDetails.getContent().getStore().getStore_info());
            }

            // 店家自诉
            if (goodDetails.getContent().getSeller_description() == null || goodDetails.getContent().getSeller_description().equals("")) {
                goods_detail_describe.setText("暂无定店家自述");
            } else {
                goods_detail_describe.setText(goodDetails.getContent().getSeller_description());
            }

            //推荐产品列表
            if (goodDetails.getContent().getRecommend_goods_list() != null && goodDetails.getContent().getRecommend_goods_list().size() != 0) {
                goods_detail_recommend_recycle.setLayoutManager(new GridLayoutManager(getContext(), 2));
                BaseRecyclerAdapter<GoodDetailsBean.ContentBean.RecommendGoodsListBean> madapter = new BaseRecyclerAdapter<GoodDetailsBean.ContentBean.RecommendGoodsListBean>(getContext(),
                        goodDetails.getContent().getRecommend_goods_list(), R.layout.item_store_goods) {
                    @Override
                    public void convert(BaseRecyclerHolder holder, GoodDetailsBean.ContentBean.RecommendGoodsListBean data, int position, boolean isScrolling) {
                        int mScreenWidth = ScreenUtils.getScreenWidth(getContext());
                        int height = getResources().getDimensionPixelSize(R.dimen.y534);
                        int mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
                        int mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
                        if (position % 2 == 0) {
                            holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                        } else {
                            holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                        }
                        ImageView good_img = holder.getView(R.id.good_img);
                        holder.setThumbWithGlide(getContext(), mScreenWidth / 2, height, R.drawable.goods, good_img, data.getGoods_main_photo());
                        holder.setText(R.id.good_name_tt, data.getGoods_name());
                        TextView good_dis_left = holder.getView(R.id.brand_item_good_dis_left);
                        TextView good_pre_price = holder.getView(R.id.good_pre_price);
                        if (data.getPrice_des() == null || data.getPrice_des().equals("")) {
                            good_dis_left.setVisibility(View.GONE);
                            good_pre_price.setText(getResources().getString(R.string.rmb_symbol) + data.getGoods_price());
                            good_pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            good_pre_price.setVisibility(View.GONE);
                        } else {
                            good_pre_price.setVisibility(View.GONE);
                            good_dis_left.setText(data.getPrice_des());
                            good_dis_left.setVisibility(View.VISIBLE);
                        }
                        holder.setText(R.id.good_current_price, data.getGoods_current_price() + "");
                        ImageView good_zonetype_img = holder.getView(R.id.good_zonetype_img);
                        if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                            good_zonetype_img.setVisibility(View.VISIBLE);
                            good_zonetype_img.setImageResource(R.drawable.compare_price_card);
                        } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                            good_zonetype_img.setVisibility(View.VISIBLE);
                            good_zonetype_img.setImageResource(R.drawable.subside_card);
                        } else if (data.getZone_type().equals(DataType.zone_type.ninepointnine.toString())) {
                            good_zonetype_img.setVisibility(View.VISIBLE);
                            good_zonetype_img.setImageResource(R.drawable.nine_card);
                        } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                            good_zonetype_img.setVisibility(View.VISIBLE);
                            good_zonetype_img.setImageResource(R.drawable.brand_sale_card);
                        } else {
                            good_zonetype_img.setVisibility(View.GONE);
                        }
                    }
                };

                madapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodDetailsBean.ContentBean.RecommendGoodsListBean>() {
                    @Override
                    public void onItemClick(BaseRecyclerHolder viewHolder, GoodDetailsBean.ContentBean.RecommendGoodsListBean data, int position) {
                        Intent intent = new Intent();
                        if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                            intent.setClass(getContext(), ComparePriceGoodDetailActivity.class);
                        } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                            intent.setClass(getContext(), SubsidyGoodDetailActivity.class);
                        } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                            intent.setClass(getContext(), NormalGoodDetailActivity.class);
                            intent.putExtra(Constants.is_brand_good, true);
                        } else {
                            intent.setClass(getContext(), NormalGoodDetailActivity.class);
                        }
                        intent.putExtra(Constants.goods_id, data.getId());
                        startActivity(intent);
                    }
                });

                goods_detail_recommend_recycle.setAdapter(madapter);
            }
            if (null != goodDetails.getContent().getPhotoList() && goodDetails.getContent().getPhotoList().size() >= 1) {
                List<String> ims = new ArrayList<>(); // 产品图片轮
                for (String url : goodDetails.getContent().getPhotoList()) {
                    ims.add(url);
                }
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ScreenUtils.getScreenWidth(getContext()));
                goods_detail_head_banner.setLayoutParams(layoutParams);
                goods_detail_head_banner.start(getApplication(), ims, R.drawable.goods, 3000, null, 0, 0);
            }
            tab_list.removeAllViews();
            for (int i = 0; i < goodDetails.getContent().getServiceList().size(); i++) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gooddetail_tab, null);
                TextView gooddetailTab_text = (TextView) view.findViewById(R.id.gooddetailTab_text);
                gooddetailTab_text.setText(goodDetails.getContent().getServiceList().get(i));
                tab_list.addView(view);
            }
        } else if (contentType.equals(DataType.collect.addCollect.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")) {
                showToast("收藏成功");
                goodDetailsBean.getContent().setCollect_flag(1);
                GlideEngine.loadThumbnail(getContext(), R.drawable.collectiontab_btn_nor, iv_collect, R.drawable.collectiontab_btn_sel);
            }
        } else if (contentType.equals(DataType.subsidy.createCutPriceOrder.toString())) {
            SubsidyShareBean subsidyShareBean = (SubsidyShareBean) object;
            SubsidySharePopWindow shareFreeOrderGoodsPopWindow = new SubsidySharePopWindow(SubsidyGoodDetailActivity.this, goodDetailsBean, subsidyShareBean, "today");
            shareFreeOrderGoodsPopWindow.showAtLocation(getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new SubsidySharePopWindow.OnClickOkListener() {
                @Override
                public void compareShare() {
                    showToast("分享成功!");
                }
            });
        } else if (contentType.equals(DataType.favorite.delete.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")) {
                showToast("取消收藏");
                goodDetailsBean.getContent().setCollect_flag(0);
                GlideEngine.loadThumbnail(getContext(), R.drawable.collectiontab_btn_nor, iv_collect, R.drawable.collectiontab_btn_nor);
            }
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        if (dataType.equals(DataType.myPresenterType.getGoodDetail.toString())) {
            content_layout.setVisibility(View.GONE);
            reload_rl.setVisibility(View.VISIBLE);
        }
    }

    private void renderingWhitchRL(int i) {
        switch (i) {
            case 1:
                goods_detail_babe_iv.setVisibility(View.VISIBLE);
                goods_detail_iv.setVisibility(View.GONE);
                goods_detail_evaluation_iv.setVisibility(View.GONE);
                goods_detail_recommend_iv.setVisibility(View.GONE);
                goods_detail_babe_tt.setTextColor(getResources().getColor(R.color.orange));
                goods_detail_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_evaluation_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_recommend_tt.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 2:
                goods_detail_babe_iv.setVisibility(View.GONE);
                goods_detail_iv.setVisibility(View.GONE);
                goods_detail_evaluation_iv.setVisibility(View.VISIBLE);
                goods_detail_recommend_iv.setVisibility(View.GONE);
                goods_detail_babe_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_evaluation_tt.setTextColor(getResources().getColor(R.color.orange));
                goods_detail_recommend_tt.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 3:
                goods_detail_babe_iv.setVisibility(View.GONE);
                goods_detail_iv.setVisibility(View.VISIBLE);
                goods_detail_evaluation_iv.setVisibility(View.GONE);
                goods_detail_recommend_iv.setVisibility(View.GONE);
                goods_detail_babe_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_tt.setTextColor(getResources().getColor(R.color.orange));
                goods_detail_evaluation_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_recommend_tt.setTextColor(getResources().getColor(R.color.gray));
                break;
            case 4:
                goods_detail_babe_iv.setVisibility(View.GONE);
                goods_detail_iv.setVisibility(View.GONE);
                goods_detail_evaluation_iv.setVisibility(View.GONE);
                goods_detail_recommend_iv.setVisibility(View.VISIBLE);
                goods_detail_babe_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_evaluation_tt.setTextColor(getResources().getColor(R.color.gray));
                goods_detail_recommend_tt.setTextColor(getResources().getColor(R.color.orange));
                break;
        }
    }

    private void getWidgetHeight() {
        height1 = good_detail_info_ll.getHeight();
        height2 = good_detail_evaluate_ll.getHeight() + good_detail_info_ll.getHeight();
        height3 = good_detail_info_ll.getHeight() + gooddetail_weblayout.getHeight() + good_detail_evaluate_ll.getHeight();
    }

    /**
     * 点击购买或者发起补贴选择产品规格
     */
    private void selectSku(final String type) {
        SkuDialog skuDialog = new SkuDialog(getContext(), goodDetailsBean.getContent()).builder();
        skuDialog.show();
        skuDialog.setOnSelectListener(new SkuDialog.onSelectListener() {
            @Override
            public void onComplete(final long goodId, int count, final String sku, String goodPrice) {
                if(type.equals("subsidy_buy")){
                    subsidyPresenter.createCutPriceOrder(getContext(), SPUtil.getPrefString("token", ""), goodId, sku, DataType.subsidy.createCutPriceOrder.toString());
                }else if(type.equals("immediately_buy")) {
                   /* Intent intent = new Intent(SubsidyGoodDetailActivity.this, OrderEditActivity.class);
                    Bundle bundle = new Bundle();
                    if(goodDetailsBean.getContent().getZone_type().equals("ninepointnine")){
                        bundle.putString("orderType","ninepointnine");
                    }else if(goodDetailsBean.getContent().getZone_type().equals("creative")){
                        bundle.putString("orderType","creative");
                    }else{
                        bundle.putString("orderType","nolPay");
                    }

                    bundle.putString("imgurl", goodDetailsBean.getContent().getGoods_main_photo());
                    bundle.putString("goods_id", goods_id + "");
                    bundle.putInt("count", count);
                    bundle.putString("gsp", sku);
                    bundle.putDouble("goods_price", Double.parseDouble(goodPrice));
                    bundle.putString("good_name", goodDetailsBean.getContent().getGoods_name());
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getContext());
    }
}
