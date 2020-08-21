package com.dajukeji.hslz.fragment.mallFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.lzy.okgo.OkGo;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.OrderEditActivity;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerAdapter;
import com.dajukeji.hslz.adapter.recycleradapter.BaseRecyclerHolder;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.MySubsidyBean;
import com.dajukeji.hslz.domain.javaBean.SubsidyShareBean;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.SubsidyPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.SuperpositionLayout;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.CircleImageView;
import com.dajukeji.hslz.view.OrderDialog;
import com.dajukeji.hslz.view.SubsidySharePopWindow;
import com.dajukeji.hslz.view.SubsidyUserPopWindow;

public class MySubsidyFragment extends HttpBaseFragment {

    private SubsidyPresenter subsidyPresenter;
    private XRecyclerView xRecyclerView;
    private LinearLayout ll_empty_order; // 为空时界面
    private TextView tv_order_empty; // 文字
    private ImageView im_empty;
    private MySubsidyBean.ContentBean.OrderListBean shareSubsidy;
    private int currentPage = 1;
    private boolean isFirstPage = true;
    private BaseRecyclerAdapter<MySubsidyBean.ContentBean.OrderListBean> recyclerAdapter;
    private int pageSize;
    private View headerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subsidyPresenter = new SubsidyPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.xrecycler_order_layout, container, false);
        }
        headerView = inflater.inflate(R.layout.item_header_image, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View v) {
        ll_empty_order = (LinearLayout) v.findViewById(R.id.ll_empty_order);
        tv_order_empty = (TextView) v.findViewById(R.id.tv_order_empty);
        im_empty = (ImageView) v.findViewById(R.id.im_empty);
        xRecyclerView = (XRecyclerView) v.findViewById(R.id.recyclerView);
        initHeaderView(xRecyclerView); // 添加头部图片
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                recyclerAdapter.clear();
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
        recyclerAdapter = new BaseRecyclerAdapter<MySubsidyBean.ContentBean.OrderListBean>(getContext(), null, R.layout.item_my_subsidy) {
            @Override
            public void convert(BaseRecyclerHolder holder, final MySubsidyBean.ContentBean.OrderListBean data, int position, boolean isScrolling) {
                ImageView pict_url = holder.getView(R.id.pict_url);
                TextView item_title = holder.getView(R.id.item_title);

                int size = getResources().getDimensionPixelSize(R.dimen.x400);
                GlideEngine.loadThumbnail(getActivity(), size, R.drawable.goods, pict_url, data.getGoods_main_photo());
                item_title.setText(data.getGoods_name());
                SuperpositionLayout mSupLayout = holder.getView(R.id.item_order_list_superpositionLayout);
                mSupLayout.removeAllViews();

                holder.setText(R.id.final_price, getResources().getString(R.string.rmb_symbol) + String.format("%.2f", data.getOriginal_price()));
                holder.setText(R.id.price, getResources().getString(R.string.rmb_symbol) + String.format("%.2f", data.getLowest_price()));
                holder.setText(R.id.tv_deadline, data.getInvalid_time());
                holder.setText(R.id.tv_get_subsidy, "获取补贴" + String.format("%.2f", (data.getOriginal_price() - data.getCurrent_price())) + "元");
                holder.setText(R.id.tv_need_subsidy, "还差" + String.format("%.2f", (data.getCurrent_price() - data.getLowest_price())) + "元");
                //用户头像
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                if (data.getWx_headimgurl_list().size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        if (i < 2) {
                            CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_subsidy_superposition, mSupLayout, false);
                            holder.setImageWithGlide(getActivity(), imageView, data.getWx_headimgurl_list().get(i));
                            mSupLayout.addView(imageView);
                        } else {
                            CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_subsidy_superposition, mSupLayout, false);
                            holder.setImageWithGlide(getActivity(), imageView, R.drawable.subsidy_more);
                            mSupLayout.addView(imageView);
                        }
                    }
                } else {
                    for (int i = 0; i < data.getWx_headimgurl_list().size(); i++) {
                        CircleImageView imageView = (CircleImageView) inflater.inflate(R.layout.item_subsidy_superposition, mSupLayout, false);
                        holder.setImageWithGlide(getActivity(), imageView, data.getWx_headimgurl_list().get(i));
                        mSupLayout.addView(imageView);
                    }
                }
                mSupLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubsidyUserPopWindow subsidyUserPopWindow = new SubsidyUserPopWindow(getActivity(), data);
                        subsidyUserPopWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
                    }
                });
                // 继续分享
                TextView tvToShare = holder.getView(R.id.item_order_list_toShare);

                switch (data.getOrder_status()) {
                    //已取消
                    case 0:
                        tvToShare.setText("已取消");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //待付款
                    case 10:
                        if (data.getShareable() == 1) { //可以继续分享
                            tvToShare.setText("继续分享");
                            tvToShare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    showDialogLoading();
                                    shareSubsidy = data;
                                    subsidyPresenter.shareCutPriceOrder(getContext(), Long.toString(data.getCut_price_id()), SPUtil.getPrefString("token", ""), DataType.subsidy.shareCutPriceOrder.toString());
                                }
                            });
                        } else {

                            if (data.getDeletable() == 1) {
                                tvToShare.setText("删除");
                                tvToShare.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        OrderDialog orderDialog = new OrderDialog(getContext(), "确认删除?", "删除后不可恢复", "确认");
                                        orderDialog.show();
                                        orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                                            @Override
                                            public void sureClick() {
                                                subsidyPresenter.deleteOrder(getContext(), SPUtil.getPrefString("token", ""), data.getCut_price_id(), DataType.myOrder.delete.toString());
                                            }
                                        });
                                    }
                                });
                            } else if (data.getDeletable() == 0) {
                                tvToShare.setText("待付款");
                            }
                        }
                        break;

                    //待发货
                    case 20:
                        tvToShare.setText("待发货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //已发货/待收货
                    case 30:
                        tvToShare.setText("待收货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //已收货/待评价
                    case 40:
                        tvToShare.setText("待评价");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //买家申请退货
                    case 45:
                        tvToShare.setText("买家申请退货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //卖家同意退货
                    case 46:
                        tvToShare.setText("卖家同意退货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //买家申请退货
                    case 47:
                        tvToShare.setText("买家申请退货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //卖家拒绝退货
                    case 48:
                        tvToShare.setText("卖家拒绝退货");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //退货失败
                    case 49:
                        tvToShare.setText("退货失败");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //已评价
                    case 50:
                        tvToShare.setText("已评价");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;

                    //已完成
                    case 60:
                        tvToShare.setText("已完成");
                        tvToShare.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast("请在订单中心查看");
                            }
                        });
                        break;
                }
            }
        };
        xRecyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<MySubsidyBean.ContentBean.OrderListBean>() {
            @Override
            public void onItemClick(BaseRecyclerHolder viewHolder, MySubsidyBean.ContentBean.OrderListBean data, int position) {
                if (data.getPayable() == 1) { // 可以支付
                    Intent intent = new Intent(getActivity(), OrderEditActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("orderType", "subsidyPay");
                    bundle.putString("imgurl", data.getGoods_main_photo());
                    bundle.putLong("cut_price_id", data.getCut_price_id());
                    bundle.putString("good_name", data.getGoods_name());
                    bundle.putDouble("goods_price", data.getCurrent_price());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 头部布局
     */
    private void initHeaderView(XRecyclerView xRecyclerView) {
        xRecyclerView.addHeaderView(headerView);
        int height = getResources().getDimensionPixelSize(R.dimen.y820);
        int width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        ImageView header = (ImageView) headerView.findViewById(R.id.iv_header_image);
        header.setLayoutParams(layoutParams);
        GlideEngine.loadThumbnails(getActivity().getApplicationContext(), width, height, R.drawable.goods, header, R.drawable.my_subsidy_header_bg);
    }

    @Override
    public boolean supportX() {
        return true;
    }

    /**
     * 获取我的补贴列表
     */
    private void loadList(int currentPage) {
        showDialogLoading();
        subsidyPresenter.myCutPriceOrderList(getContext(), currentPage, SPUtil.getPrefString("token", ""), DataType.subsidy.mySubsidy.toString());
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.subsidy.mySubsidy.toString())) {
            hideDialogLoading();
            MySubsidyBean mySubsidyBean = (MySubsidyBean) object;
            complete();
            if (mySubsidyBean.getContent().getOrderList().isEmpty()) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(0, getResources().getDimensionPixelSize(R.dimen.y820), 0, 0);
                ll_empty_order.setVisibility(View.VISIBLE);
                ll_empty_order.setLayoutParams(layoutParams);
                tv_order_empty.setText("您暂无补贴订单");
                im_empty.setVisibility(View.GONE);
            } else {
                recyclerAdapter.setData(mySubsidyBean.getContent().getOrderList());
                pageSize = mySubsidyBean.getContent().getPages();
                currentPage++;
            }
        } else if (dataType.equals(DataType.subsidy.shareCutPriceOrder.toString())) {
            hideDialogLoading();
            SubsidyShareBean subsidyShareBean = (SubsidyShareBean) object;
            SubsidySharePopWindow shareFreeOrderGoodsPopWindow = new SubsidySharePopWindow(getActivity(), shareSubsidy, subsidyShareBean, "me");
            shareFreeOrderGoodsPopWindow.showAtLocation(getView(), Gravity.BOTTOM, 0, 0);
            shareFreeOrderGoodsPopWindow.setOnClickOkListener(new SubsidySharePopWindow.OnClickOkListener() {
                @Override
                public void compareShare() {
                    showToast("分享成功!");
                }
            });
        } else if (dataType.equals(DataType.myOrder.delete.toString())) {
            hideDialogLoading();
            String sucess = (String) object;
            if (sucess.equals("sucess")) {
                showToast("删除成功");
                recyclerAdapter.clear();
                loadList(1);
            }
        }
    }

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
