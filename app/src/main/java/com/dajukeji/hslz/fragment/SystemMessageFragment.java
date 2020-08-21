package com.dajukeji.hslz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ArticleWebActivity;
import com.dajukeji.hslz.activity.ComparePriceGoodDetailActivity;
import com.dajukeji.hslz.activity.NormalGoodDetailActivity;
import com.dajukeji.hslz.activity.OrderDetailActivity;
import com.dajukeji.hslz.activity.RefundOrderDetailActivity;
import com.dajukeji.hslz.activity.SubsidyGoodDetailActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandIndexActivity;
import com.dajukeji.hslz.activity.brandmarcket.BrandStoreDetailActivity;
import com.dajukeji.hslz.activity.home.coupon.CouponExchangeActivity;
import com.dajukeji.hslz.activity.mall.CreativeProductsActivity;
import com.dajukeji.hslz.activity.mall.FreeOrderActivity;
import com.dajukeji.hslz.activity.mall.FreeshippingActivity;
import com.dajukeji.hslz.activity.mall.SubsidyActivity;
import com.dajukeji.hslz.activity.mall.WholePriceActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.Constants;
import com.dajukeji.hslz.domain.javaBean.SystemMessageBean;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MessagePresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class SystemMessageFragment extends HttpBaseFragment {

    private MessagePresenter messagePresenter;
    private RelativeLayout reload_rl;
    private Button reload_button;
    private XRecyclerView recyclerView;
    private BaseRecyclerAdapter<SystemMessageBean.ContentEntity.MessageListEntity> madapter;
    private int currentPage = 1;
    private int pages;  //总页数
    private boolean firstPage = true;
    private int readMessagePosition = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messagePresenter = new MessagePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == rootView) {
            rootView = inflater.inflate(R.layout.xrecycler_layout, null);
        }
        init(rootView);
        return rootView;
    }

    @Override
    protected void lazyLoad() {
        loadList(currentPage);
    }

    private void init(View view) {
        reload_rl = (RelativeLayout) view.findViewById(R.id.reload_rl);
        reload_button = (Button) view.findViewById(R.id.reload_button);
        recyclerView = (XRecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        madapter = new BaseRecyclerAdapter<SystemMessageBean.ContentEntity.MessageListEntity>(getContext(), null, R.layout.item_user_message) {
            @Override
            public void convert(BaseRecyclerHolder holder, SystemMessageBean.ContentEntity.MessageListEntity data, int position, boolean isScrolling) {
                TextView order_status_message = holder.getView(R.id.order_status_message);
                TextView date_time_message =  holder.getView(R.id.date_time_message);
                TextView content_message =  holder.getView(R.id.content_message);
                TextView message =   holder.getView(R.id.message);
                ImageView goods_url = holder.getView(R.id.goods_url);
                int imWidth =  getResources().getDimensionPixelSize(R.dimen.x150);
                int imHeight =  getResources().getDimensionPixelSize(R.dimen.y150);
                GlideEngine.loadThumbnail(getActivity(),imWidth,imHeight, R.drawable.goods, goods_url, data.getIcon());
                if (data.getType() == 2 && data.getJump_type().equals("zone")) {
                    /** 免单专区：free
                     * 9.9包邮：ninepointnine
                     * 创意新品：creative
                     * 全网比价：compareprice
                     * 品牌大促：brand
                     * 砍价专区：cutprice
                     * 积分况换：integral*/
                        if (data.getType_id().equals("free")) {
//                            Glide.with(getContext()).load(R.drawable.free_order).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.free_order);
                       } else if (data.getType_id().equals("ninepointnine")) {
//                            Glide.with(getContext()).load(R.drawable.free_shipping).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.free_shipping);
                        } else if (data.getType_id().equals("creative")) {
//                            Glide.with(getContext()).load(R.drawable.discount).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.discount);
                        } else if (data.getType_id().equals("compareprice")) {
//                            Glide.with(getContext()).load(R.drawable.whole_price).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.whole_price);
                        } else if (data.getType_id().equals("brand")) {
//                            Glide.with(getContext()).load(R.drawable.brand_promotion).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.brand_promotion);
                        }else if (data.getType_id().equals("cutprice")) {
//                            Glide.with(getContext()).load(R.drawable.subsidy_icon).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.subsidy_icon);
                        } else if (data.getType_id().equals("integral")) {
//                            Glide.with(getContext()).load(R.drawable.sign_score).into(goods_url);
                            holder.setImageResource(R.id.goods_url, R.drawable.sign_score);
                        }
                }
                if (data.getAlready_write() == 0) {
                    holder.getView(R.id.orange_dot_iv).setVisibility(View.VISIBLE);
                    order_status_message.setTextColor(getResources().getColor(R.color.orange_text));
                } else {
                    holder.getView(R.id.orange_dot_iv).setVisibility(View.GONE);
                    order_status_message.setTextColor(getResources().getColor(R.color.fontGray));
                }
                order_status_message.setText(data.getTitle());
                date_time_message.setText(data.getAddTime());
                content_message.setText(data.getContent());
                message.setText(data.getRemark());
            }
        };
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                firstPage = true;
                currentPage = 1;
                loadList(currentPage);
            }

            @Override
            public void onLoadMore() {
                if (currentPage >= pages) {
                    recyclerView.loadMoreComplete();
                    return;
                }
                loadList(currentPage);
                firstPage = false;
            }
        });
        madapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<SystemMessageBean.ContentEntity.MessageListEntity>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, SystemMessageBean.ContentEntity.MessageListEntity data, int position) {
                switch (data.getType()) {   //type	int	消息类型0订单物流信息1申请售后信息2推送信息
                    case 0:
                        Intent orderIntent = new Intent(getContext(), OrderDetailActivity.class);
                        orderIntent.putExtra("id", data.getType_id());
                        startActivity(orderIntent);
                        break;
                    case 1:
                        Intent intent = new Intent(getContext(), RefundOrderDetailActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("gr_id",data.getType_id());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 2:
                        if (data.getJump_type().equals("article")) {
                            Intent articleintent = new Intent(getContext(), ArticleWebActivity.class);
                            articleintent.putExtra("weblocation", "app/mall/article/readarticleRead.htm?id="+data.getType_id());
                            articleintent.putExtra("title", data.getArticle_title());
                            startActivity(articleintent);
                        } else if (data.getJump_type().equals("zone")) {
                            /** 免单专区：free
                             * 9.9包邮：ninepointnine
                             * 创意新品：creative
                             * 全网比价：compareprice
                             * 品牌大促：brand
                             * 砍价专区：cutprice
                             * 积分况换：integral*/
                            if (data.getType_id().equals("free")) {
                                Intent intent1 = new Intent(getContext(), FreeOrderActivity.class);
                                startActivity(intent1);
                            } else if (data.getType_id().equals("ninepointnine")) {
                                Intent intent2 = new Intent(getContext(), FreeshippingActivity.class);
                                startActivity(intent2);
                            } else if (data.getType_id().equals("creative")) {
                                Intent intent3 = new Intent(getContext(), CreativeProductsActivity.class);
                                startActivity(intent3);
                            } else if (data.getType_id().equals("compareprice")) {
                                Intent intent4 = new Intent(getContext(), WholePriceActivity.class);
                                startActivity(intent4);
                            } else if (data.getType_id().equals("brand")) {
                                Intent intent5 = new Intent(getContext(), BrandIndexActivity.class);
                                startActivity(intent5);
                            }else if (data.getType_id().equals("cutprice")) {
                                Intent intent6 = new Intent(getContext(), SubsidyActivity.class);
                                startActivity(intent6);
                            } else if (data.getType_id().equals("integral")) {
                                Intent intent7 = new Intent(getContext(), CouponExchangeActivity.class);
                                startActivity(intent7);
                            }
                        } else if (data.getJump_type().equals("goods")) {
                            Intent good_intent = new Intent();
                            if (data.getZone_type().equals(DataType.zone_type.compareprice.toString())) {
                                good_intent.setClass(getContext(), ComparePriceGoodDetailActivity.class);
                            } else if (data.getZone_type().equals(DataType.zone_type.cutprice.toString())) {
                                good_intent.setClass(getContext(), SubsidyGoodDetailActivity.class);
                            } else if (data.getZone_type().equals(DataType.zone_type.brand.toString())) {
                                good_intent.setClass(getContext(), NormalGoodDetailActivity.class);
                                good_intent.putExtra(Constants.is_brand_good, true);
                            } else if (data.getZone_type().equals("free")) {
                                good_intent.setClass(getContext(), FreeOrderActivity.class);
                            } else if (data.getZone_type().equals("integral")) {
                                good_intent.setClass(getContext(), CouponExchangeActivity.class);
                            }else {
                                good_intent.setClass(getContext(), NormalGoodDetailActivity.class);
                            }
                            int good_id = Integer.parseInt(data.getType_id());
                            good_intent.putExtra(Constants.goods_id, good_id);
                            startActivity(good_intent);
                        } else if (data.getJump_type().equals("store")) {
                            Intent brandIntent = new Intent(getContext(), BrandStoreDetailActivity.class);
                            int storeid = Integer.parseInt(data.getType_id());
                            brandIntent.putExtra("brand_id", storeid);
                            startActivity(brandIntent);
                        }
                        break;
                }
                if (data.getAlready_write() == 0) {
                    messagePresenter.markRead(SystemMessageFragment.this, SPUtil.getPrefString("token",""), data.getId()+"", "readed");
                    readMessagePosition = position;
                }
//                data.setAlready_write(1);
//                madapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(madapter);

    }

    @Override
    public boolean supportX() {
        return true;
    }

    private void loadList(int page) {
        messagePresenter.getSystemMessage(SystemMessageFragment.this, page, SPUtil.getPrefString("token", ""), DataType.message.systemMessage.toString());
    }
    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.message.systemMessage.toString())) {
            SystemMessageBean bean = (SystemMessageBean) object;
            if (firstPage) {
                madapter.clear();
            }
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
            if (bean.getContent().getMessageList() != null && bean.getContent().getMessageList().size() > 0) {
                if (currentPage == 1) {
                    madapter.setNewData(bean.getContent().getMessageList());
                } else {
                    madapter.setData(bean.getContent().getMessageList());
                }
                pages = bean.getContent().getPages();
            }
            currentPage++;
        } else if (dataType.equals("readed")) {
            EventBus.getDefault().post(new UserMessageEvent("message"));
            madapter.getitem(readMessagePosition).setAlready_write(1);
            madapter.notifyDataSetChanged();
        }
    }
}
