package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.BoomHotGoodsActivity;
import com.dajukeji.hslz.activity.NewActivity;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.SubsideActivity;
import com.dajukeji.hslz.activity.WebActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.adapter.recycleradapter.DividerGridItemDecoration;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.GoodLisBean;
import com.dajukeji.hslz.domain.javaBean.IndexBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.GoodPresenter;
import com.dajukeji.hslz.util.LogUtil;
import com.dajukeji.hslz.util.MyAdGallery;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ${wangjiasheng} on 2017/12/22 0022.
 * 这里是显示首页内容的主要fragment
 */
public class IndexFragment extends HttpBaseFragment {
    private ImageView pingxuan, xinpin, baokuan;
    private GoodPresenter goodPresenter;
    private XRecyclerView xRecyclerView;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<GoodLisBean.ContentBean.CommonGoodsListBean> recyclerAdapter;
    private int pageSize;
    private View headerView;
    private MyAdGallery myAdGallery1;
    private ImageView go_top_iv;
    private int scrollDistance = 0;
    private TextView article_describe1, article_describe2;
    private List<IndexBean.ContentBean.TopBannerListBean> topBannerList;
    private List<IndexBean.ContentBean.MiddleBannerTwoListBean> middletwoBannerList;
    private List<IndexBean.ContentBean.MiddleBannerThreeListBean> middlethreeBannerList;
    private List<IndexBean.ContentBean.MiddleBannerFourListBean> middlefourBannerList;
    private SimpleDraweeView banner_one, banner_two;
    private ImageView banner_1, banner_2, banner_3, banner_4;
    private ViewGroup group_advertising;//活动专区整个布局
    private ImageView banner_six, banner_seven, banner_eight, banner_nine;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodPresenter = new GoodPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //承载了整个布局的recyclerView
        View view = inflater.inflate(R.layout.xrecycler_layout, null);
        //这里是我们看到的首页头部，原理是将整个头部布局作为首页的展示，其内容包括了"猜你喜欢"、"热销榜"、"首页三大区"
        headerView = inflater.inflate(R.layout.header_index_xrecycler, null);
        initView(view);
        return view;
    }


    private void initView(View v) {
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        initHeaderView();

        go_top_iv = (ImageView) v.findViewById(R.id.go_top_iv);
        go_top_iv.setOnClickListener(this);
        reload_rl = (RelativeLayout) v.findViewById(R.id.reload_rl);
        reload_button = (Button) v.findViewById(R.id.reload_button);
        reload_button.setOnClickListener(this);
        xRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //给recyclerView设置布局，以及加载数据的监听
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                if (recyclerAdapter.equals(null)) {

                } else {
                    recyclerAdapter.clear();
                }
                loadList(currentPage);
                isFirstPage = true;
            }

            @Override
            public void onLoadMore() {
                if (currentPage > pageSize) {
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                isFirstPage = false;
            }
        });

        //这个adapter中加载的是热销好货以下的商品数据
        recyclerAdapter = new BaseRecyclerAdapter<GoodLisBean.ContentBean.CommonGoodsListBean>(getContext(), null, R.layout.item_store_goods) {
            @Override
            public void convert(BaseRecyclerHolder holder, GoodLisBean.ContentBean.CommonGoodsListBean data, int position, boolean isScrolling) {
                int mScreenWidth = ScreenUtils.getScreenWidth(getContext());
                int height = getResources().getDimensionPixelSize(R.dimen.y534);
                int mPaddingRight = getResources().getDimensionPixelSize(R.dimen.x12);
                int mPaddingBottom = getResources().getDimensionPixelSize(R.dimen.y12);
//                if (position % 2 == 0) {
//                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
//                } else {
//                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
//                }
                //对上面的if语句进行优化
                //在二进制中，如果position的最后一位是1，那就表示这是一个奇数，反之是一个偶数
                if((position&1)==1){
                    holder.itemView.setPadding(0, 0, 0, mPaddingBottom);
                }else {
                    holder.itemView.setPadding(0, 0, mPaddingRight, mPaddingBottom);
                }

                ImageView good_img = holder.getView(R.id.good_img);
                holder.setThumbWithGlide(getContext(),
                        mScreenWidth / 2,
                        height,
                        R.drawable.goods,
                        good_img,
                        data.getGoods_main_photo());
                holder.setText(R.id.good_name_tt, data.getGoods_name());
                TextView good_dis_left = holder.getView(R.id.brand_item_good_dis_left);
                TextView good_pre_price = holder.getView(R.id.good_pre_price);
                if (data.getGoods_price() != 0.0) {
//                    good_dis_left.setVisibility(View.GONE);
                    good_pre_price.setText(String.format("￥%.2f", data.getRef_price()));
                    good_pre_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//                    good_pre_price.setVisibility(View.GONE);
                } else {
                    good_pre_price.setVisibility(View.GONE);
//                    good_dis_left.setText(data.getPrice_des());
//                    good_dis_left.setVisibility(View.VISIBLE);
                }
//                holder.setText(R.id.brand_item_good_dis_left, data.getPrice_des());
                holder.setText(R.id.good_current_price, data.getGoods_price() + "");
                ImageView good_zonetype_img = holder.getView(R.id.good_zonetype_img);
//                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
//                    good_zonetype_img.setVisibility(View.VISIBLE);
//                    good_zonetype_img.setImageResource(R.drawable.compare_price_card);
//                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
//                    good_zonetype_img.setVisibility(View.VISIBLE);
//                    good_zonetype_img.setImageResource(R.drawable.subside_card);
//                } else if (data.getZone_type().equals(DataType.zone_type.ninepointnine.toString())) {
//                    good_zonetype_img.setVisibility(View.VISIBLE);
//                    good_zonetype_img.setImageResource(R.drawable.nine_card);
//                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
//                    good_zonetype_img.setVisibility(View.VISIBLE);
//
//                    good_zonetype_img.setImageResource(R.drawable.brand_sale_card);
//                } else {
//                    good_zonetype_img.setVisibility(View.GONE);
//                }
            }
        };
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<GoodLisBean.ContentBean.CommonGoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, GoodLisBean.ContentBean.CommonGoodsListBean data, int position) {
//                Intent intent = new Intent(getContext().getApplicationContext(), NormalGoodDetailActivity.class);
//                intent.putExtra("goods_id", data.getId());
//                startActivity(intent);
                Intent intent = new Intent();
                intent.setClass(getContext(), NormalGoodDetailActivity.class);
//                if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
//
//                } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
//                    intent.setClass(getContext(), SubsidyGoodDetailActivity.class);
//                } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
//                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
//                    intent.putExtra(Constants.is_brand_good, true);
//                } else {
//                    intent.setClass(getContext(), NormalGoodDetailActivity.class);
//                }
                intent.putExtra(Constants.goods_id, data.getId());
                startActivity(intent);
            }
        });
        xRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext(), 20, R.color.white));
        xRecyclerView.setAdapter(recyclerAdapter);
        loadList(currentPage);

        //以下代码用于将头部文件隐藏
        xRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollDistance = scrollDistance + dy;
                LogUtil.info("scrollDy", "dy =" + scrollDistance);
                if (scrollDistance >= headerView.getHeight()) {
                    go_top_iv.setVisibility(View.VISIBLE);
                } else {
                    go_top_iv.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean supportX() {
        return true;
    }

    /**
     * 初始化头部文件
     */
    protected void initHeaderView() {
        xRecyclerView.addHeaderView(headerView);
        myAdGallery1 = (MyAdGallery) headerView.findViewById(R.id.index_page_gallery);
        pingxuan = (ImageView) headerView.findViewById(R.id.pingxuan);
        pingxuan.setOnClickListener(this);
        baokuan = (ImageView) headerView.findViewById(R.id.baokuan);
        baokuan.setOnClickListener(this);
        xinpin = (ImageView) headerView.findViewById(R.id.xinpin);
        xinpin.setOnClickListener(this);
        banner_one = headerView.findViewById(R.id.banner_one);
        banner_two = headerView.findViewById(R.id.banner_two);
        banner_1 = headerView.findViewById(R.id.banner_1);
        banner_2 = headerView.findViewById(R.id.banner_2);
        banner_3 = headerView.findViewById(R.id.banner_3);
        banner_4 = headerView.findViewById(R.id.banner_4);
        group_advertising = headerView.findViewById(R.id.group_advertising);
        banner_six = headerView.findViewById(R.id.banner_six);
        banner_seven = headerView.findViewById(R.id.banner_seven);
        banner_eight = headerView.findViewById(R.id.banner_eight);
        banner_nine = headerView.findViewById(R.id.banner_nine);
        banner_one.setOnClickListener(this);
        banner_two.setOnClickListener(this);
        banner_1.setOnClickListener(this);
        banner_3.setOnClickListener(this);
        banner_2.setOnClickListener(this);
        banner_six.setOnClickListener(this);
        banner_seven.setOnClickListener(this);
        banner_eight.setOnClickListener(this);
        banner_nine.setOnClickListener(this);
        headerView.findViewById(R.id.to_health_know).setOnClickListener(this);
        article_describe1 = (TextView) headerView.findViewById(R.id.article_describe1);
        article_describe2 = (TextView) headerView.findViewById(R.id.article_describe2);
//        List<String> photourllist = new ArrayList();
//        photourllist.add(HttpAddress.mBaseUrl2 + "upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg");
//        photourllist.add(HttpAddress.mBaseUrl2 + "upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg");
//        photourllist.add(HttpAddress.mBaseUrl2 + "upload/store/1/2016/04/06/1572d08b-1404-449f-a746-0336612f15a9.jpg");
//        photourllist.add(HttpAddress.mBaseUrl2 + "upload/store/1/2016/04/06/2a4c105f-d92e-4268-b7e9-c2f015e1a614.jpg");
//        myAdGallery1.start(AliSdkApplication.getContext(), photourllist, null, 3000, null, 0, 0);
//        myAdGallery2.start(AliSdkApplication.getContext(), photourllist, null, 3000, null, 0, 0);
//        myAdGallery3.start(AliSdkApplication.getContext(), photourllist, null, 3000, null, 0, 0);

        initNewImg(null);
    }

    /**
     * 从网络中开始加载数据
     * @param page 表示加载 page 页的内容
     */
    private void loadList(int page) {
        goodPresenter.getGoodsLisd(getContext(), page, DataType.good.getGoodLis.toString());
        goodPresenter.getIndex(IndexFragment.this, DataType.mall.index.toString());
    }

    /**
     * 执行完 goodPresenter的请求之后，回调此方法
     * @param object
     * @param dataType
     */
    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        reload_rl.setVisibility(View.GONE);

        if (dataType.equals(DataType.good.getGoodLis.toString())) {
            //加载商品数据列表的回调
            GoodLisBean goodListBean = (GoodLisBean) object;
            complete();
            currentPage++;
            if (goodListBean.getStatus().equals("0")) {
                List<GoodLisBean.ContentBean.CommonGoodsListBean> list = goodListBean.getContent().getCommonGoodsList();
                recyclerAdapter.setData(list);
            } else {
                Toast.makeText(getContext(), "空的", Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(getContext(),goodListBean.getContent().getCommonGoodsList()+"",Toast.LENGTH_SHORT).show();
            pageSize = goodListBean.getContent().getPages();


        } else if (dataType.equals(DataType.mall.index.toString())) {
            final IndexBean indexBean = (IndexBean) object;

            //如果服务器的数据为空，那么就隐藏公告栏
            if (indexBean.getContent().getTitleList()!=null){
                article_describe1.setText(indexBean.getContent().getTitleList().get(0));
                article_describe2.setText(indexBean.getContent().getTitleList().get(1));
            }else {
                headerView.findViewById(R.id.to_health_know).setVisibility(View.GONE);
            }



            //红包雨开关
            if (0 == indexBean.getContent().getRed() && !((MainActivity) getActivity()).isInitRedPackedFloatWindow) {
                ((MainActivity) getActivity()).initFloatWindow();
                ((MainActivity) getActivity()).isInitRedPackedFloatWindow = true;
                Log.d("hongbao kaiguan",indexBean.getContent().getRed()+"");
            }else if (1 == indexBean.getContent().getRed()){
                ((MainActivity)getActivity()).destroyFloatWindow();
                ((MainActivity) getActivity()).isInitRedPackedFloatWindow = false;
            }

            //兑换专区
            View exchangeArea = headerView.findViewById(R.id.v_exchangeArea);
            //H5还没做好，先隐藏兑换专区入口
            Log.d("html5",indexBean.getContent().getH5Url()+" "+indexBean.getContent().getH5());
            if ("0".equals(indexBean.getContent().getH5())) {
                final String h5Url = indexBean.getContent().getH5Url();
                exchangeArea.setVisibility(View.VISIBLE);
                exchangeArea.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(WebActivity.Companion.getStartIntent(getActivity(), h5Url));

                        //下面是测试h5支付链接的
//                        String urlString = "http://dajukeji.com/exchangeArea/index.html#/activationquota";
//                        startActivity(WebActivity.Companion.getStartIntent(getActivity(), urlString));
                    }
                });
            } else {
                exchangeArea.setVisibility(View.GONE);
            }


            List<String> photourllist = new ArrayList();
            topBannerList = indexBean.getContent().getTopBannerList();
            middletwoBannerList = indexBean.getContent().getMiddleBannerTwoList();
            middlethreeBannerList = indexBean.getContent().getMiddleBannerThreeList();
            middlefourBannerList = indexBean.getContent().getMiddleBannerFourList();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.goods);

            //热销
            final List<IndexBean.ContentBean.HostGoodsListBean> hostGoodsList = indexBean.getContent().getHostGoodsList();
            if (hostGoodsList != null && hostGoodsList.size() > 0) {
                ((TextView) headerView.findViewById(R.id.tv_rexiao1)).setText(hostGoodsList.get(0).getGoods_name());
                Glide.with(getActivity()).load(hostGoodsList.get(0).getGoods_main_photo()).into((ImageView) headerView.findViewById(R.id.iv_rexiao1));
                ((TextView) headerView.findViewById(R.id.tv_money_rexiao1)).setText(String.format("%.2f", hostGoodsList.get(0).getGoods_price()));
                headerView.findViewById(R.id.iv_rexiao1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                        intent1.putExtra("goods_id", hostGoodsList.get(0).getId());
                        startActivity(intent1);
                    }
                });
            }
            if (hostGoodsList != null && hostGoodsList.size() > 1) {
                ((TextView) headerView.findViewById(R.id.tv_rexiao2)).setText(hostGoodsList.get(1).getGoods_name());
                Glide.with(getActivity()).load(hostGoodsList.get(1).getGoods_main_photo()).into((ImageView) headerView.findViewById(R.id.iv_rexiao2));
                ((TextView) headerView.findViewById(R.id.tv_money_rexiao2)).setText(String.format("%.2f", hostGoodsList.get(1).getGoods_price()));
                headerView.findViewById(R.id.iv_rexiao2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                        intent1.putExtra("goods_id", hostGoodsList.get(1).getId());
                        startActivity(intent1);
                    }
                });
            }
            if (hostGoodsList != null && hostGoodsList.size() > 2) {
                ((TextView) headerView.findViewById(R.id.tv_rexiao3)).setText(hostGoodsList.get(2).getGoods_name());
                Glide.with(getActivity()).load(hostGoodsList.get(2).getGoods_main_photo()).into((ImageView) headerView.findViewById(R.id.iv_rexiao3));
                ((TextView) headerView.findViewById(R.id.tv_money_rexiao3)).setText(String.format("%.2f", hostGoodsList.get(2).getGoods_price()));
                headerView.findViewById(R.id.iv_rexiao3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                        intent1.putExtra("goods_id", hostGoodsList.get(2).getId());
                        startActivity(intent1);
                    }
                });
            }

            //广告位：4位
            if (middlefourBannerList != null && middlefourBannerList.size() > 0) {
                Glide.with(getActivity()).load(middlefourBannerList.get(0).getPic_url()).apply(requestOptions).into(banner_six);
                banner_six.setVisibility(View.VISIBLE);
            } else {
                banner_six.setVisibility(View.GONE);
            }
            if (middlefourBannerList != null && middlefourBannerList.size() > 1) {
                Glide.with(getActivity()).load(middlefourBannerList.get(1).getPic_url()).apply(requestOptions).into(banner_seven);
                banner_seven.setVisibility(View.VISIBLE);
            } else {
                banner_seven.setVisibility(View.GONE);
            }
            if (middlefourBannerList != null && middlefourBannerList.size() > 2) {
                Glide.with(getActivity()).load(middlefourBannerList.get(2).getPic_url()).apply(requestOptions).into(banner_eight);
                banner_eight.setVisibility(View.VISIBLE);
            } else {
                banner_eight.setVisibility(View.GONE);
            }
            if (middlefourBannerList != null && middlefourBannerList.size() > 3) {
                banner_nine.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(middlefourBannerList.get(3).getPic_url()).apply(requestOptions).into(banner_nine);
            } else {
                banner_nine.setVisibility(View.GONE);
            }

            //广告位：3位
            initNewImg(indexBean.getContent());//假设有图片
//            if (middlethreeBannerList.size() > 0) {
//                Glide.with(getActivity()).load(middlethreeBannerList.get(0).getPic_url()).apply(requestOptions).into(banner_1);
//                banner_1.setVisibility(View.VISIBLE);
//            } else {
//                banner_1.setVisibility(View.GONE);
//            }
//            if (middlethreeBannerList.size() > 1) {
//                Glide.with(getActivity()).load(middlethreeBannerList.get(1).getPic_url()).apply(requestOptions).into(banner_2);
//                banner_2.setVisibility(View.VISIBLE);
//            } else {
//                banner_2.setVisibility(View.GONE);
//            }
//            if (middlethreeBannerList.size() > 2) {
//                Glide.with(getActivity()).load(middlethreeBannerList.get(2).getPic_url()).apply(requestOptions).into(banner_3);
//                banner_3.setVisibility(View.VISIBLE);
//            } else {
//                banner_3.setVisibility(View.GONE);
//            }

            //广告位：2位
            if (middletwoBannerList != null && middletwoBannerList.size() > 1) {
                Glide.with(getActivity()).load(middletwoBannerList.get(0).getPic_url()).apply(requestOptions).into(banner_one);
                banner_one.setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.banner_one_underline).setVisibility(View.VISIBLE);
            } else {
                banner_one.setVisibility(View.GONE);
                headerView.findViewById(R.id.banner_one_underline).setVisibility(View.GONE);
            }
            if (middletwoBannerList != null && middletwoBannerList.size() > 2) {
                Glide.with(getActivity()).load(middletwoBannerList.get(1).getPic_url()).apply(requestOptions).into(banner_two);
                banner_two.setVisibility(View.VISIBLE);
                headerView.findViewById(R.id.banner_two_underline).setVisibility(View.VISIBLE);
            } else {
                banner_two.setVisibility(View.GONE);
                headerView.findViewById(R.id.banner_two_underline).setVisibility(View.GONE);
            }


            LinearLayout layout = new LinearLayout(getActivity());
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            if (!indexBean.getContent().getTopBannerList().isEmpty()) {
                for (IndexBean.ContentBean.TopBannerListBean bannerEntity : topBannerList) {
                    photourllist.add(bannerEntity.getPic_url());
                }
                myAdGallery1.start(getContext(), photourllist, R.drawable.goods, 2800, layout, R.drawable.checkbox_true, R.drawable.checkbox_false);
                photourllist.clear();
            } else {
                myAdGallery1.setVisibility(View.GONE);
            }


            myAdGallery1.setMyOnItemClickListener(new MyAdGallery.MyOnItemClickListener() {
                @Override
                public void onItemClick(int curIndex) {
                    judgeBanner(topBannerList.get(curIndex));
                }
            });

        }
    }

    // 初始化四层图片模块
    private void initNewImg(IndexBean.ContentBean content) {
        //顶部图片
        try {
            ImageView ivTop = (ImageView) headerView.findViewById(R.id.iv_home_top);
            String img = content.getTopBannerList().get(0).getPic_url();
            if (img!=null||img.equals("")){
                Glide.with(this).load(img).into(ivTop);//从网络加载图片到imageview
            }else {
                ((ImageView)headerView.findViewById(R.id.iv_home_top)).setVisibility(View.GONE);
            }
        } catch (Exception e) {
        }
        //1-4层,4张大图
        ImgBean im1 = new ImgBean();
        im1.vImgID = R.id.banner_1;
        try {
            im1.img = content.getMiddleBannerFourList().get(0).getPic_url();
            im1.type = content.getMiddleBannerFourList().get(0).getType();
            im1.id = content.getMiddleBannerFourList().get(0).getExtra();
        } catch (Exception e) {
        }

        ImgBean im2 = new ImgBean();
        im2.vImgID = R.id.banner_2;
        try {
            im2.img = content.getMiddleBannerFourList().get(1).getPic_url();
            im2.type = content.getMiddleBannerFourList().get(1).getType();
            im2.id = content.getMiddleBannerFourList().get(1).getExtra();
        } catch (Exception e) {
        }

        ImgBean im3 = new ImgBean();
        im3.vImgID = R.id.banner_3;
        try {
            im3.img = content.getMiddleBannerFourList().get(2).getPic_url();
            im3.type = content.getMiddleBannerFourList().get(2).getType();
            im3.id = content.getMiddleBannerFourList().get(2).getExtra();
        } catch (Exception e) {
        }

        ImgBean im4 = new ImgBean();
        im4.vImgID = R.id.banner_4;
        try {
            im4.img = content.getMiddleBannerFourList().get(3).getPic_url();
            im4.type = content.getMiddleBannerFourList().get(3).getType();
            im4.id = content.getMiddleBannerFourList().get(3).getExtra();
        } catch (Exception e) {
        }

        //1-4层的底部3小图
        ImgBean im1_1 = new ImgBean();
        try {
            im1_1.vImgID = R.id.iv_h1_1;
            im1_1.vTextID = R.id.tv_h1_1;
            im1_1.vMoneyID = R.id.tv_h1_1m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE0Bean bean = content.getStoreGoodsList().get(0).getGoodsListnE0().get(0);
            im1_1.id = bean.getId();
            im1_1.type = 0;
            im1_1.name = bean.getGoods_name();
            im1_1.img = bean.getGoods_main_photo();
            im1_1.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im1_2 = new ImgBean();
        try {
            im1_2.vImgID = R.id.iv_h1_2;
            im1_2.vTextID = R.id.tv_h1_2;
            im1_2.vMoneyID = R.id.tv_h1_2m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE0Bean bean = content.getStoreGoodsList().get(0).getGoodsListnE0().get(1);
            im1_2.id = bean.getId();
            im1_2.type = 0;
            im1_2.name = bean.getGoods_name();
            im1_2.img = bean.getGoods_main_photo();
            im1_2.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im1_3 = new ImgBean();
        try {
            im1_3.vImgID = R.id.iv_h1_3;
            im1_3.vTextID = R.id.tv_h1_3;
            im1_3.vMoneyID = R.id.tv_h1_3m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE0Bean bean = content.getStoreGoodsList().get(0).getGoodsListnE0().get(2);
            im1_3.id = bean.getId();
            im1_3.type = 0;
            im1_3.name = bean.getGoods_name();
            im1_3.img = bean.getGoods_main_photo();
            im1_3.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im2_1 = new ImgBean();
        try {
            im2_1.vImgID = R.id.iv_h2_1;
            im2_1.vTextID = R.id.tv_h2_1;
            im2_1.vMoneyID = R.id.tv_h2_1m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE1Bean bean = content.getStoreGoodsList().get(1).getGoodsListnE1().get(0);
            im2_1.id = bean.getId();
            im2_1.type = 0;
            im2_1.name = bean.getGoods_name();
            im2_1.img = bean.getGoods_main_photo();
            im2_1.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im2_2 = new ImgBean();
        try {
            im2_2.vImgID = R.id.iv_h2_2;
            im2_2.vTextID = R.id.tv_h2_2;
            im2_2.vMoneyID = R.id.tv_h2_2m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE1Bean bean = content.getStoreGoodsList().get(1).getGoodsListnE1().get(1);
            im2_2.id = bean.getId();
            im2_2.type = 0;
            im2_2.name = bean.getGoods_name();
            im2_2.img = bean.getGoods_main_photo();
            im2_2.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im2_3 = new ImgBean();
        try {
            im2_3.vImgID = R.id.iv_h2_3;
            im2_3.vTextID = R.id.tv_h2_3;
            im2_3.vMoneyID = R.id.tv_h2_3m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE1Bean bean = content.getStoreGoodsList().get(1).getGoodsListnE1().get(2);
            im2_3.id = bean.getId();
            im2_3.type = 0;
            im2_3.name = bean.getGoods_name();
            im2_3.img = bean.getGoods_main_photo();
            im2_3.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im3_1 = new ImgBean();
        try {
            im3_1.vImgID = R.id.iv_h3_1;
            im3_1.vTextID = R.id.tv_h3_1;
            im3_1.vMoneyID = R.id.tv_h3_1m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE2Bean bean = content.getStoreGoodsList().get(2).getGoodsListnE2().get(0);
            im3_1.id = bean.getId();
            im3_1.type = 0;
            im3_1.name = bean.getGoods_name();
            im3_1.img = bean.getGoods_main_photo();
            im3_1.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im3_2 = new ImgBean();
        try {
            im3_2.vImgID = R.id.iv_h3_2;
            im3_2.vTextID = R.id.tv_h3_2;
            im3_2.vMoneyID = R.id.tv_h3_2m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE2Bean bean = content.getStoreGoodsList().get(2).getGoodsListnE2().get(1);
            im3_2.id = bean.getId();
            im3_2.type = 0;
            im3_2.name = bean.getGoods_name();
            im3_2.img = bean.getGoods_main_photo();
            im3_2.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im3_3 = new ImgBean();
        try {
            im3_3.vImgID = R.id.iv_h3_3;
            im3_3.vTextID = R.id.tv_h3_3;
            im3_3.vMoneyID = R.id.tv_h3_3m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE2Bean bean = content.getStoreGoodsList().get(2).getGoodsListnE2().get(2);
            im3_3.id = bean.getId();
            im3_3.type = 0;
            im3_3.name = bean.getGoods_name();
            im3_3.img = bean.getGoods_main_photo();
            im3_3.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im4_1 = new ImgBean();
        try {
            im4_1.vImgID = R.id.iv_h4_1;
            im4_1.vTextID = R.id.tv_h4_1;
            im4_1.vMoneyID = R.id.tv_h4_1m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE3Bean bean = content.getStoreGoodsList().get(3).getGoodsListnE3().get(0);
            im4_1.id = bean.getId();
            im4_1.type = 0;
            im4_1.name = bean.getGoods_name();
            im4_1.img = bean.getGoods_main_photo();
            im4_1.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im4_2 = new ImgBean();
        try {
            im4_2.vImgID = R.id.iv_h4_2;
            im4_2.vTextID = R.id.tv_h4_2;
            im4_2.vMoneyID = R.id.tv_h4_2m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE3Bean bean = content.getStoreGoodsList().get(3).getGoodsListnE3().get(1);
            im4_2.id = bean.getId();
            im4_2.type = 0;
            im4_2.name = bean.getGoods_name();
            im4_2.img = bean.getGoods_main_photo();
            im4_2.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        ImgBean im4_3 = new ImgBean();
        try {
            im4_3.vImgID = R.id.iv_h4_3;
            im4_3.vTextID = R.id.tv_h4_3;
            im4_3.vMoneyID = R.id.tv_h4_3m;
            IndexBean.ContentBean.StoreGoodsListBean.GoodsListnE3Bean bean = content.getStoreGoodsList().get(3).getGoodsListnE3().get(2);
            im4_3.id = bean.getId();
            im4_3.type = 0;
            im4_3.name = bean.getGoods_name();
            im4_3.img = bean.getGoods_main_photo();
            im4_3.money = bean.getGoods_price();
        } catch (Exception e) {
        }

        loadNewBean(im1);
        loadNewBean(im2);
        loadNewBean(im3);
        loadNewBean(im4);
        loadNewBean(im1_1);
        loadNewBean(im1_2);
        loadNewBean(im1_3);
        loadNewBean(im2_1);
        loadNewBean(im2_2);
        loadNewBean(im2_3);
        loadNewBean(im3_1);
        loadNewBean(im3_2);
        loadNewBean(im3_3);
        loadNewBean(im4_1);
        loadNewBean(im4_2);
        loadNewBean(im4_3);
    }

    private void loadNewBean(final ImgBean bean) {
        boolean isLoadImg = false, isClickable = false, isLoadName = false, isLoadMoney = false;
        if (bean.vImgID != 0 && bean.img != null) {
            isLoadImg = true;
            Glide.with(this).load(bean.img).into((ImageView) headerView.findViewById(bean.vImgID));
            headerView.findViewById(bean.vImgID).setVisibility(View.VISIBLE);
        }
        if (bean.type != -1 && bean.id != -1) {
            isClickable = true;
            headerView.findViewById(bean.vImgID).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (bean.type) {
                        case 0:
                            Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                            intent1.putExtra("goods_id", bean.id);
                            startActivity(intent1);
                            break;
                        case 1:
                            Intent storeintent = new Intent(getContext(), BrandStoreDetailActivity.class);
                            storeintent.putExtra("brand_id", bean.id);
                            startActivity(storeintent);
                            break;
                    }
                }
            });
        }
        if (bean.vTextID != 0 && !TextUtils.isEmpty(bean.name)) {
            isLoadName = true;
            ((TextView) headerView.findViewById(bean.vTextID)).setText(bean.name);
            headerView.findViewById(bean.vTextID).setVisibility(View.VISIBLE);
        }
        if (bean.money != -1.0 && bean.vMoneyID != 0) {
            isLoadMoney = true;
            ((TextView) headerView.findViewById(bean.vMoneyID)).setText(String.format("券 %.2f", bean.money));
            headerView.findViewById(bean.vMoneyID).setVisibility(View.VISIBLE);
        }
        //都没加载,则隐藏此view
        if (!isClickable && !isLoadImg && !isLoadName && !isLoadMoney) {
            if (bean.vImgID != 0)
                headerView.findViewById(bean.vImgID).setVisibility(View.GONE);
            if (bean.vTextID != 0)
                headerView.findViewById(bean.vTextID).setVisibility(View.GONE);
            if (bean.vMoneyID != 0)
                headerView.findViewById(bean.vMoneyID).setVisibility(View.GONE);
        }
    }

    public static class ImgBean {
        int id = -1;
        int type = -1;//0=点击后跳商品， 1=点击后跳商店
        String img;//图
        String name;//名
        double money = -1.0;//金额
        int vImgID;//图片viewID
        int vTextID;//名字viewID
        int vMoneyID;//金额viewID
    }

    private void judgBanner(IndexBean.ContentBean.MiddleBannerTwoListBean bannerEntity) {

        switch (bannerEntity.getType()) {
            case 0:
                Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                intent1.putExtra("goods_id", bannerEntity.getExtra());
                startActivity(intent1);
                break;
            case 1:
                Intent storeintent = new Intent(getContext(), BrandStoreDetailActivity.class);
                storeintent.putExtra("brand_id", bannerEntity.getExtra());
                startActivity(storeintent);
                break;
        }

    }

    private void judgBanne(IndexBean.ContentBean.MiddleBannerThreeListBean bannerEntity) {

        switch (bannerEntity.getType()) {
            case 0:
                Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                intent1.putExtra("goods_id", bannerEntity.getExtra());
                startActivity(intent1);
                break;
            case 1:
                Intent storeintent = new Intent(getContext(), BrandStoreDetailActivity.class);
                storeintent.putExtra("brand_id", bannerEntity.getExtra());
                startActivity(storeintent);
                break;
        }

    }

    private void judgBann(IndexBean.ContentBean.MiddleBannerFourListBean bannerEntity) {

        switch (bannerEntity.getType()) {
            case 0:
                Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                intent1.putExtra("goods_id", bannerEntity.getExtra());
                startActivity(intent1);
                break;
            case 1:
                Intent storeintent = new Intent(getContext(), BrandStoreDetailActivity.class);
                storeintent.putExtra("brand_id", bannerEntity.getExtra());
                startActivity(storeintent);
                break;
        }

    }


    private void judgeBanner(IndexBean.ContentBean.TopBannerListBean bannerEntity) {
        switch (bannerEntity.getType()) {
//            //产品类型，仅当type为0时才会有意义，compareprice：全网比价产品，ninepointnine：9块9产品，
//            // creative：创意新品产品，cutprice：砍价产品，brand：品牌大促产品，为空字符串为不参与活动产品
            case 0:
                Intent intent1 = new Intent(getActivity(), NormalGoodDetailActivity.class);
                intent1.putExtra("goods_id", bannerEntity.getExtra());
                startActivity(intent1);
                break;
            case 1:
                Intent storeintent = new Intent(getContext(), BrandStoreDetailActivity.class);
                storeintent.putExtra("brand_id", bannerEntity.getExtra());
                startActivity(storeintent);
                break;
        }
    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.banner_one:
                judgBanner(middletwoBannerList.get(0));
                break;
            case R.id.banner_two:
                judgBanner(middletwoBannerList.get(1));
                break;
            case R.id.banner_1:
                judgBanne(middlethreeBannerList.get(0));
                break;
            case R.id.banner_2:
                judgBanne(middlethreeBannerList.get(1));
                break;
            case R.id.banner_3:
                judgBanne(middlethreeBannerList.get(2));
                break;
            case R.id.banner_six:
                judgBann(middlefourBannerList.get(1));
                break;
            case R.id.banner_seven:
                judgBann(middlefourBannerList.get(0));
                break;
            case R.id.banner_eight:
                judgBann(middlefourBannerList.get(1));
                break;
            case R.id.banner_nine:
                judgBann(middlefourBannerList.get(2));
                break;
            case R.id.pingxuan:
                Intent subsidyIntent = new Intent(getActivity(), SubsideActivity.class);
                startActivity(subsidyIntent);
                break;
            case R.id.xinpin:
                Intent subsidyntent = new Intent(getActivity(), NewActivity.class);
                startActivity(subsidyntent);
                break;
            case R.id.baokuan:
//                Intent sy = new Intent(getActivity(), BoomActivity.class);
                Intent sy = new Intent(getActivity(), BoomHotGoodsActivity.class);
                startActivity(sy);
                break;
            case R.id.to_health_know:
//                暂时隐藏通知点击
//                Intent healthIntent = new Intent(getActivity(), HealthArticleListActivity.class);
//                startActivity(healthIntent);
                break;
            case R.id.go_top_iv:
                xRecyclerView.scrollToPosition(0);
                go_top_iv.setVisibility(View.GONE);
                scrollDistance = 0;
                break;
            case R.id.reload_button:
                loadList(currentPage);
                break;
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        {
            if (dataType.equals(DataType.good.getGoodList.toString())) {
                complete();
                if (currentPage == 1) {
                    reload_rl.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
