package com.dajukeji.hslz.fragment.mallFragment;


import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ColletActivity;
import com.dajukeji.hslz.activity.DaiActivity;
import com.dajukeji.hslz.activity.SubsidyGoodDetailActivity;
import com.dajukeji.hslz.activity.mine.ProductsBean;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.ZichanBean;
import com.dajukeji.hslz.domain.javaBean.GoodListBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.presenter.SubsidyPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.MyProgressBar;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * 评选区的主要页面
 */
public  class TodaySubsidyFragment extends HttpBaseFragment {
    private SubsidyPresenter subsidyPresenter;
    private XRecyclerView xRecyclerView;
//    private Button bt_ing,bt_yg;
//    private View veiw_lor,view_co;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<ProductsBean.ContentBean.GoodsListBean> recyclerAdapter;
    private int pageSize;
    private View headerView;
    private List<ProductsBean.ContentBean.GoodsListBean> listBeans;

    private int pingxuan = 0;//这个值来判断“正在评选”和“预告评选”，0为正在评选，1为预告评选

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subsidyPresenter = new SubsidyPresenter(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView= inflater.inflate(R.layout.xrecycler_layout, container, false);
        }
        headerView = inflater.inflate(R.layout.item_header_image,null);
        initView(rootView);
        return rootView;
    }

    private void initView(View v){
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        initHeaderView(); // 添加头部图片
        loadList(currentPage);//开始加载第一页
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {//当recyclerView开始刷新时
                currentPage = 1;
                recyclerAdapter.clear();//清空数据
                loadList(currentPage);
                isFirstPage = true;//第一页的刷新
            }

            @Override
            public void onLoadMore() {//下拉至最后一页时，提示用户已经加载完毕
                if (currentPage > pageSize) {
                    showToast("最后一页");
                    xRecyclerView.loadMoreComplete();
                    return;
                }
                currentPage++;//正常下拉加载时，页数也要相应增加
                loadList(currentPage);//开始网络加载新的页面
                isFirstPage = false;//已经不是第一页了
            }
        });
        //从这里设置每个item的样式
        recyclerAdapter = new BaseRecyclerAdapter<ProductsBean.ContentBean.GoodsListBean>(getContext(), listBeans, R.layout.item_today_subsidy) {
            @Override
            public void convert(BaseRecyclerHolder holder, ProductsBean.ContentBean.GoodsListBean data, int position, boolean isScrolling) {
                ImageView pict_url =  holder.getView(R.id.pict_url);
                TextView  item_title = holder.getView(R.id.item_title);
                holder.getView(R.id.layout_surplus).setVisibility(View.GONE);
                ((TextView)holder.getView(R.id.do_immediately)).setText("敬请期待");
                int size =  getResources().getDimensionPixelSize(R.dimen.x400);
                GlideEngine.loadThumbnail(getActivity().getApplicationContext(),size, R.drawable.goods, pict_url,data.getGoods_main_photo());
                item_title.setText(data.getGoods_name());

//                holder.setText(R.id.tv_subsidy_price,"【补贴立即领取"+ String.format("%.2f",data.getGoods_price()-data.getLowest_price())+"元】");
//                TextView price = holder.getView(R.id.price);
//                price.setText("券 ¥"+String.format("%.2f",data.getGoods_price()));
//                price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
                holder.setText(R.id.final_price,getResources().getString(R.string.rmb_symbol)+data.getGoods_price());
//                holder.setText(R.id.final_price,getResources().getString(R.string.rmb_symbol1)+String.format("%.2f",data.getLowest_price()));

//                MyProgressBar surplus_pro =   holder.getView(R.id.surplus_pro);
//                surplus_pro.setMax(data.getMax_goods_inventory());
//                surplus_pro.setProgress(data.getGoods_inventory());
            }
        };
        //这里设置监听了
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<ProductsBean.ContentBean.GoodsListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, ProductsBean.ContentBean.GoodsListBean data, int position) {
                Intent intent = new Intent(getContext().getApplicationContext(), SubsidyGoodDetailActivity.class);
                intent.putExtra("goods_id",data.getId());//把产品的id传过去
                intent.putExtra("pingxuan","yugao");//预告的商品是不能点击购买的（只能看，不能买）
                startActivity(intent);
            }

        });
        xRecyclerView.setAdapter(recyclerAdapter);
    }

    /**
     * 头部布局
     * */
    private void initHeaderView(){
        xRecyclerView.addHeaderView(headerView);
        final Button bt = (Button) headerView.findViewById(R.id.bt_ing);
        final Button bt1 = (Button) headerView.findViewById(R.id.bt_yg);
        final View v = headerView.findViewById(R.id.view_co);
        final View v1 = headerView.findViewById(R.id.view_lor);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt.setTextColor(getActivity().getResources().getColorStateList(R.color.button_red));
                bt1.setTextColor(getActivity().getResources().getColorStateList(R.color.black));
                v.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                v1.setBackgroundColor(getActivity().getResources().getColor(R.color.button_red));
                pingxuan = 0;//0表示正在评选
                currentPage = 1;//如果点了按钮，那就是从第一页开始加载
                recyclerAdapter.clear();//先清空一下本地缓存
                loadList(currentPage);//开始网络加载新的页面
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt.setTextColor(getActivity().getResources().getColorStateList(R.color.black));
                bt1.setTextColor(getActivity().getResources().getColorStateList(R.color.button_red));
                v.setBackgroundColor(getActivity().getResources().getColor(R.color.button_red));
                v1.setBackgroundColor(getActivity().getResources().getColor(R.color.white));
                pingxuan = 1;//1表示预告评选
                currentPage = 1;//如果点了按钮，那就是从第一页开始加载
                recyclerAdapter.clear();//先清空一下本地缓存
                loadList(currentPage);//开始网络加载新的页面
            }
        });

    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int currentPage){
//        subsidyPresenter.getProductGoodsList(getContext(), SPUtil.getPrefString("token",""),currentPage,0, DataType.mall.getProduct.toString());
        String url = HttpAddress.mBaseUrl2+"app/hslz/mall/new_products_drop_down.htm";
        OkHttpUtils.post()
                .url(url)
                .addParams("token",SPUtil.getPrefString("token",""))
                .addParams("currentPage",currentPage+"")
                .addParams("type",pingxuan+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        final ProductsBean goodListBean = new Gson().fromJson(response, ProductsBean.class);
                        if (goodListBean.getStatus().equals("0")) {
                            complete();
                            recyclerAdapter.setData(goodListBean.getContent().getGoodsList());
                            pageSize = goodListBean.getContent().getPageSize();

                        }else {
                            Toast.makeText(getActivity(),goodListBean.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

//    @Override
//    public void setResultData(Object object, String dataType) {
//        super.setResultData(object, dataType);
//        if(dataType.equals(DataType.mall.subsidy.toString())){
//            ProductsBean goodListBean = (ProductsBean) object;
//            if (goodListBean.getStatus().equals("0")) {
//                complete();
//                recyclerAdapter.setData(goodListBean.getContent().getGoodsList());
//                pageSize = goodListBean.getContent().getPageSize();
//                currentPage++;
//            }else {
//                Toast.makeText(getActivity(),goodListBean.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        complete();
    }

    private void complete() {
        if (isFirstPage) {
            xRecyclerView.refreshComplete();
        } else {
            xRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getActivity());
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        loadList(currentPage);
    }
}
