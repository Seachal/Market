package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.dajukeji.hslz.domain.javaBean.FiveWholesaleOrderListBean;
import com.dajukeji.hslz.domain.javaBean.GoodDetailsBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.event.ShopCartChangeEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.FavoritePresenter;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.util.MeasureWidgetHeight;
import com.dajukeji.hslz.util.MyAdGallery;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.TokenUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.dialog.SkuDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.luck.picture.lib.tools.ScreenUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/2 0002.
 * 产品详情页
 */

public class DHZQGoodDetailActivity extends HttpBaseActivity {

    private int goods_id;
    private GoodPresenter goodPresenter;
    private FavoritePresenter favoritePresenter;
    /**
     * 主体内容ScrollView
     */
    private NestedScrollView good_detail_scrollview;
    private RecyclerView goods_detail_recommend_recycle;
    private RelativeLayout goods_detail_babe_rl, goods_detail_rl, goods_detail_evaluation_rl, goods_detail_recommend_rl;
    private ImageView goods_detail_babe_iv, goods_detail_iv, goods_detail_evaluation_iv, goods_detail_recommend_iv;
    private TextView goods_detail_babe_tt, goods_detail_tt, goods_detail_evaluation_tt, goods_detail_recommend_tt;
    private SwipeRefreshLayout goods_detail_refresh;
    private int height1, height2, height3;
    private int scrollDistance = 0;
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
    private LinearLayout gooddetail_weblayout;
    private LinearLayout good_detail_info_ll, good_detail_evaluate_ll, good_detail_recommend_ll, include_titlebar_ll, include_top_ll;   //四个控件
    private MeasureWidgetHeight measureWidgetHeight;
    private FiveWholesaleOrderListBean fiveWholesaleOrderListBean;
    private UserAddressListBean addressListBean;
    private TextView add_to_shopcart, sure_buy;
    private int sureBuy = 0, joinGroupBuy = 1, createGroupBuy = 2;
    private boolean is_brand_good;
    private TextView sale_by_auction;
    private TextView goods_detail_describe; // 店家自诉
    private TextView tv_goods_evaluate; //暂无评论
    private int evaluateCount; // 全部评论数
    private ImageView store_img; // 店铺ICON
    private TextView store_name_tt; // 店铺名称
    private TextView store_status, store_level, store_speed; // 店铺描述
    private TextView tv_go_shop; // 进入店铺
    private TextView store_dis_tt; // 店铺描述
    private ImageView iv_collect;// 是否收藏
    private String chat_id = "";
    private TextView good_detail_tt;    //产品详情
    private LinearLayout content_layout;
    private ImageView go_top_iv;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private String storename;
    private ViewGroup llJingDong;
    private ViewGroup llTaoBao;
    private TextView tvJingDong;
    private TextView tvTaoBao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_good_details);
        reload_button = (Button) findViewById(R.id.reload_button);
        reload_rl = (RelativeLayout) findViewById(R.id.reload_rl);
        go_top_iv = (ImageView) findViewById(R.id.go_top_iv);
        go_top_iv.setOnClickListener(this);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        sale_by_auction = (TextView) findViewById(R.id.sale_by_auction);

        goods_id = getIntent().getIntExtra("goods_id", 0);

        is_brand_good = getIntent().getBooleanExtra(Constants.is_brand_good, false);
        setTitleBar(R.string.goods_detail, true);
        good_detail_tt = (TextView) findViewById(R.id.good_detail_tt);
        good_detail_info_ll = (LinearLayout) findViewById(R.id.good_detail_info_ll);
        good_detail_evaluate_ll = (LinearLayout) findViewById(R.id.good_detail_evaluate_ll);
        good_detail_recommend_ll = (LinearLayout) findViewById(R.id.good_detail_recommend_ll);
        goods_detail_refresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        include_titlebar_ll = (LinearLayout) findViewById(R.id.include_titlebar_ll);
        include_top_ll = (LinearLayout) findViewById(R.id.include_top_ll);
        goods_detail_head_banner = (MyAdGallery) findViewById(R.id.goods_detail_head_banner);
        goode_detail_group_by_num = (TextView) findViewById(R.id.goode_detail_group_by_num);
        goods_detail_webview = (WebView) findViewById(R.id.goods_detail_webview);
        gooddetail_weblayout = (LinearLayout) findViewById(R.id.gooddetail_weblayout);
        WebSettings webSettings = goods_detail_webview.getSettings();
//        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        /**
         * 用WebView显示图片，可使用这个参数 设置网页布局类型：
         * 1、LayoutAlgorithm.NARROW_COLUMNS ：适应内容大小
         * 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
         */
        tab_list = (LinearLayout) findViewById(R.id.tab_list);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setJavaScriptEnabled(true);
        goods_detail_webview.loadUrl("");
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
        add_to_shopcart = (TextView) findViewById(R.id.add_to_shopcart);


        //配合兑换专区的跳转
        add_to_shopcart.setVisibility(View.GONE);
        ((TextView)findViewById(R.id.shichangjia)).setVisibility(View.GONE);
        sure_buy = (TextView) findViewById(R.id.sure_buy);
        sure_buy.setText("立即兑换");

        add_to_shopcart.setOnClickListener(this);
        sure_buy.setOnClickListener(this);
        goods_detail_babe_rl.setOnClickListener(this);
        goods_detail_rl.setOnClickListener(this);
        goods_detail_evaluation_rl.setOnClickListener(this);
        goods_detail_recommend_rl.setOnClickListener(this);
        goods_current_price = (TextView) findViewById(R.id.goods_current_price);//券的价格，现价
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
        see_all_rl.setOnClickListener(this);
        llJingDong = findViewById(R.id.ll_jingDong);
        llTaoBao = findViewById(R.id.ll_taoBao);
        tvJingDong = findViewById(R.id.tv_jingDongMoney);
        tvTaoBao = findViewById(R.id.tv_taoBaoMoney);
        goods_detail_evaluate = (TextView) findViewById(R.id.goods_detail_evaluate);
        goods_detail_recommend_recycle = (RecyclerView) findViewById(R.id.goods_detail_recommend_recycle);
        goods_detail_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //配合兑换专区
                goodPresenter.getExchangeGoodDetail(DHZQGoodDetailActivity.this, goods_id, DataType.myPresenterType.getGoodDetail.toString());
            }
        });
        good_detail_scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //更新每个主体模块的高度（宝贝、评价、详情、推荐）
                getWidgetHeight();
                scrollDistance = scrollY;
//                int height1 = good_detail_info_ll.getHeight();
//                int height2 = good_detail_info_ll.getHeight() + gooddetail_weblayout.getHeight();
//                int height3 = good_detail_info_ll.getHeight() + gooddetail_weblayout.getHeight() + good_detail_evaluate_ll.getHeight();
                //根据当前滚动的y轴标志当前所在区域（宝贝、评价、详情、推荐）
                if (scrollY >= height1 && scrollY < height2) {
                    renderingWhitchRL(2);
                } else if (scrollY >= height2 && scrollY < height3) {
                    renderingWhitchRL(3);
                } else if (scrollY >= height3) {
                    renderingWhitchRL(4);
                } else {
                    renderingWhitchRL(1);
                }
                //回到顶部按钮
                if (scrollY > height1) {
                    go_top_iv.setVisibility(View.VISIBLE);
                } else {
                    go_top_iv.setVisibility(View.GONE);
                }
            }
        });
        if (is_brand_good) {
            sale_by_auction.setVisibility(View.VISIBLE);
            sale_by_auction.setOnClickListener(this);
            add_to_shopcart.setVisibility(View.GONE);
            sure_buy.setVisibility(View.GONE);
//            goods_current_price.setTextSize(20);
//            goods_current_price.setTextColor(getResources().getColor(R.color.title_black_color));
//            goods_price.setTextSize(20);
//            goods_price.setTextColor(getResources().getColor(R.color.orange));
        } else {

        }

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
        iv_collect = (ImageView) findViewById(R.id.iv_collect); // 是否收藏
    }

    @Override
    protected void initData() {
        super.initData();
        favoritePresenter = new FavoritePresenter(this);
        measureWidgetHeight = new MeasureWidgetHeight();
        goodPresenter = new GoodPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //配合兑换专区的商品详情
        goodPresenter.getExchangeGoodDetail(DHZQGoodDetailActivity.this, goods_id, DataType.myPresenterType.getGoodDetail.toString());
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
            case R.id.sale_by_auction:

                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (null != goodDetailsBean)
                            selectSku("sale_by_auction");
                    } else {
                        startActivity(new Intent(getContext(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getContext(), WeChatLoginActivity.class));
                }
                break;
            case R.id.goods_detail_share_ll:
                startActivity(new Intent(DHZQGoodDetailActivity.this, MainActivity.class));
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
            case R.id.add_to_shopcart:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (null != goodDetailsBean){

                            //增加对已下架商品的判断
                            if (goodDetailsBean.getContent().getGoods_ststus() == -2){
                                showToast("该商品已下架");
                                return;
                            }else if (goodDetailsBean.getContent().getGoods_ststus() == 1){
                                showToast("该商品待补货，请稍后再试！");
                                return;
                            }

                            selectSku("add_to_shopcart");
                        }

                    } else {
                        startActivity(new Intent(getContext(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getContext(), WeChatLoginActivity.class));
                }
                break;
            case R.id.sure_buy:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (null != goodDetailsBean){

                            //增加对已下架商品的判断
                            if (goodDetailsBean.getContent().getGoods_ststus() == -2){
                                showToast("该商品已下架");
                                return;
                            }else if (goodDetailsBean.getContent().getGoods_ststus() == 1){
                                showToast("该商品待补货，请稍后再试！");
                                return;
                            }

                            selectSku("sure_buy");
                        }
                    } else {
                        startActivity(new Intent(getContext(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getContext(), WeChatLoginActivity.class));
                }

                break;
            case R.id.goods_detail_collection_ll: // 是否收藏产品
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
            case R.id.reload_button://重新加载按钮
                //配合兑换专区的刷新
                goodPresenter.getExchangeGoodDetail(DHZQGoodDetailActivity.this, goods_id, DataType.myPresenterType.getGoodDetail.toString());
                reload_rl.setVisibility(View.GONE);
                break;
            case R.id.go_top_iv:
                good_detail_scrollview.scrollTo(0, 0);
                break;
        }
    }

    private GoodDetailsBean goodDetailsBean;

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);

        if (contentType.equals(DataType.myPresenterType.getGoodDetail.toString())) {
            content_layout.setVisibility(View.VISIBLE);
            reload_rl.setVisibility(View.GONE);
            final GoodDetailsBean goodDetails = (GoodDetailsBean) object;
            goodDetailsBean = goodDetails;//产品信息
            renderingWhitchRL(1);

            //给产品信息扩展规格：购买方式，改变其在规格Dialog中显示的样子
            SkuDialog.addBuySpec(goodDetailsBean);
            //京东、淘宝价格
            GoodDetailsBean.ContentBean.GoodsProductInfo goodsProductInfo = goodDetailsBean.getContent().getGoodsProductInfo();
            if (goodsProductInfo != null) {
                tvJingDong.setText("￥" + goodsProductInfo.getJdPrice());
                tvTaoBao.setText("￥" + goodsProductInfo.getTbPrice());
                ((TextView) findViewById(R.id.tv_cityMoney)).setText("￥" + goodsProductInfo.getTbPrice());//需要被划掉的市场价
                tvJingDong.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tvTaoBao.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                ((TextView) findViewById(R.id.tv_cityMoney)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                ((TextView) findViewById(R.id.tv_cityMoney)).setVisibility(View.GONE);
//                llJingDong.setVisibility(goodsProductInfo.getJdPrice() != 0.0 ? View.VISIBLE : View.GONE);
//                llTaoBao.setVisibility(goodsProductInfo.getTbPrice() != 0.0 ? View.VISIBLE : View.GONE);
            }

            storename = goodDetails.getContent().getStore().getStore_name();
            chat_id = goodDetails.getContent().getStore().getChat_id();
//            goods_detail_webview.setWebViewClient(new WebViewClient() {
//                @Override
//                public void onPageFinished(WebView view, String url) {
//                    super.onPageFinished(view, url);
//
//                }
//
//                @Override
//                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                    view.loadUrl(url);
//                    return true;
//                }
//            });
            goods_detail_webview.loadUrl(goodDetails.getContent().getGoods_details());
//            if (!is_brand_good) {


            //兑换专区的配合变更
            goods_current_price.setText("所需额度" + goodDetails.getContent().getGoods_current_price() + "");

            goods_price.setText(getResources().getString(R.string.rmb_symbol) + goodDetails.getContent().getGoods_price() + "");
            goods_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            }
//            else {
//                goods_current_price.setText(getResources().getString(R.string.rmb_symbol) + goodDetails.getContent().getGoods_current_price());
//                goods_price.setText((goodDetails.getContent().getGoods_current_price() - goodDetails.getContent().getBrand_price()+""));
//            }
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

            // 店铺
            int size = getResources().getDimensionPixelSize(R.dimen.x110);
            GlideEngine.loadThumbnail(this, size, R.drawable.goods, store_img, goodDetails.getContent().getStore().getStore_logo());
            store_name_tt.setText(goodDetails.getContent().getStore().getStore_name());
            store_status.setText(goodDetails.getContent().getStore().getStore_type());
            store_level.setText(goodDetails.getContent().getStore().getStore_level());
            store_speed.setText(goodDetails.getContent().getStore().getStore_speed());
            if (goodDetails.getContent().getStore().getStore_info() == null || goodDetails.getContent().getStore().getStore_info().equals("")) {
                store_dis_tt.setText("暂无描述");
            } else {
                store_dis_tt.setText(goodDetails.getContent().getStore().getStore_info());
            }

            // 是否收藏
            if (goodDetails.getContent().getCollect_flag() == 1) {
                GlideEngine.loadThumbnail(getContext(), R.drawable.collectiontab_btn_nor, iv_collect, R.drawable.collectiontab_btn_sel);
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
                        ImageView good_img = holder.getView(R.id.good_img);
                        if (position % 2 == 0) {
                            holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                        } else {
                            holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                        }
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
                        intent.setClass(getContext(), DHZQGoodDetailActivity.class);
                            intent.putExtra(Constants.is_brand_good, true);
                        } else {
                            intent.setClass(getContext(), DHZQGoodDetailActivity.class);
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
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtils.getScreenWidth(getContext()));
                goods_detail_head_banner.setLayoutParams(layoutParams);
                goods_detail_head_banner.start(getApplication(), ims, R.drawable.goods, 3000, null, 0, 0);
            }
            tab_list.removeAllViews();
            if (tab_list.getChildCount() == 0) {
                for (int i = 0; i < goodDetails.getContent().getServiceList().size(); i++) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.item_gooddetail_tab, null);
                    TextView gooddetailTab_text = (TextView) view.findViewById(R.id.gooddetailTab_text);
                    gooddetailTab_text.setText(goodDetails.getContent().getServiceList().get(i));
                    tab_list.addView(view);
                }
            }
        } else if (contentType.equals(DataType.myPresenterType.getFiveWholesaleOrderList.toString())) {
            fiveWholesaleOrderListBean = (FiveWholesaleOrderListBean) object;
        } else if (contentType.equals(DataType.myPresenterType.getAddressList.toString())) {
            addressListBean = (UserAddressListBean) object;
        } else if (contentType.equals(DataType.good.addToCart.toString())) {
            showToast(getResources().getString(R.string.add_cart_success));
            EventBus.getDefault().post(new ShopCartChangeEvent(""));
        } else if (contentType.equals(DataType.collect.addCollect.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")) {
                showToast("收藏成功");
                goodDetailsBean.getContent().setCollect_flag(1);
                GlideEngine.loadThumbnail(getContext(), R.drawable.collectiontab_btn_nor, iv_collect, R.drawable.collectiontab_btn_sel);
            }
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
            reload_button.setOnClickListener(this);
        }
    }

    /**
     * 切换为选中区域i的样式
     */
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
     * 点击购买或者加入拼团去选择产品规格
     */
    private void selectSku(final String tag) {

        /*
        //测试规格选择效果用
        GoodDetailsBean.ContentBean.GoodsSpecListBean e = new GoodDetailsBean.ContentBean.GoodsSpecListBean();
        ArrayList<GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean e1 = new GoodDetailsBean.ContentBean.GoodsSpecListBean.SpecListBean();
            e1.setItem("产品(‘’）");
            e1.setItem_id(321+i);
            list.add(e1);
        }
        e.setSpec_name("购买类型");
        e.setSpec_list(list);
        goodDetailsBean.getContent().getGoods_spec_list().add(e);
        */

        //弹框让用户选规格
        SkuDialog skuDialog = new SkuDialog(getContext(), goodDetailsBean.getContent());
        skuDialog.setGoods_id_webActivity("兑换专区的Dialog");//配合兑换专区的更新
        skuDialog.builder();
        Gson gson = new Gson();
        //如果点击确定下单
        skuDialog.show();

        //按钮的监听
        skuDialog.setOnSelectListener(new SkuDialog.onSelectListener() {
            @Override
            public void onComplete(long goodId, int count, String sku, String goodPrice) {
                String[] strings = SkuDialog.removeBuySpec(sku);
                String buyType = strings[0];//购买类型
                sku = strings[1];//购买规格

                //购买类型转型为接口所需类型
                int buyType1 = 0;//默认购买产品
                boolean isVoucher = false;
                if ("-12".equals(buyType)) {
                    buyType1 = 0;
                    isVoucher = false;
                } else if ("-13".equals(buyType)) {
                    buyType1 = 1;
                    isVoucher = true;
                }

                if (tag.equals("sure_buy")) {
                    Intent intent = new Intent(DHZQGoodDetailActivity.this, OrderEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isVoucher", isVoucher);
                    if (goodDetailsBean.getContent().getZone_type().equals("ninepointnine")) {
                        bundle.putString("orderType", "ninepointnine");
                    } else if (goodDetailsBean.getContent().getZone_type().equals("creative")) {
                        bundle.putString("orderType", "creative");
                    } else {
                        bundle.putString("orderType", "nolPay");
                    }
//                  //从这里开始再建一个类来适配
                    bundle.putInt("buyType", buyType1);
                    bundle.putString("imgurl", goodDetailsBean.getContent().getGoods_main_photo());
                    bundle.putString("goods_id", goods_id + "");
                    bundle.putInt("count", count);
                    bundle.putString("gsp", sku);
                    bundle.putDouble("goods_price", Double.parseDouble(goodPrice));
                    bundle.putString("good_name", goodDetailsBean.getContent().getGoods_name());
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else if (tag.equals("sale_by_auction")) {
                    Intent intent = new Intent(DHZQGoodDetailActivity.this, OrderEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("orderType", "brandgood");
                    bundle.putString("imgurl", goodDetailsBean.getContent().getGoods_main_photo());
                    bundle.putString("goods_id", goods_id + "");
                    bundle.putInt("count", count);
                    bundle.putString("gsp", sku);
                    bundle.putDouble("goods_price", Double.parseDouble(goodPrice));
                    bundle.putString("good_name", goodDetailsBean.getContent().getGoods_name());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(DHZQGoodDetailActivity.this);
    }
}
