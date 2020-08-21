package com.dajukeji.hslz.fragment.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dajukeji.hslz.MainActivity;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ColletActivity;
import com.dajukeji.hslz.activity.mall.FreeOrderActivity;
import com.dajukeji.hslz.activity.mall.SubsidyActivity;
import com.dajukeji.hslz.activity.mine.InviteActivity;
import com.dajukeji.hslz.activity.mine.favorite.FavoriteActivity;
import com.dajukeji.hslz.activity.mine.order.OrderActivity;
import com.dajukeji.hslz.activity.mine.refund.RefundActivity;
import com.dajukeji.hslz.activity.mine.usersetting.UserSettingActivity;
import com.dajukeji.hslz.activity.orderAddress.AddressManageActivity;
import com.dajukeji.hslz.activity.userlogin.AccountActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.order.OrderStatusCountBean;
import com.dajukeji.hslz.event.ActionBarEvent;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.network.DataType;
import com.dajukeji.hslz.network.presenter.MyOrderPresenter;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.TokenUtil;
import com.dajukeji.hslz.util.loader.GlideEngine;
import com.dajukeji.hslz.view.CircleImageView;
import com.dajukeji.hslz.view.CornerSignView;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 底部导航栏-我的
 */
public class MeFragment extends HttpBaseFragment {

    private MyOrderPresenter myOrderPresenter;

    @BindView(R.id.mine_faceImg)
    CircleImageView mImgFace; // 用户头像

    @BindView(R.id.mine_nickName)
    TextView mTvNickName; //用户昵称

    @BindView(R.id.ll_user_name)
    LinearLayout ll_user_name;

    @BindView(R.id.tv_user_no)
    TextView tv_user_no;

    @BindView(R.id.wait_for_pay)
    CornerSignView wait_for_pay;

    @BindView(R.id.wait_for_send)
    CornerSignView wait_for_send;

    @BindView(R.id.wait_for_receive)
    CornerSignView wait_for_receive;

    @BindView(R.id.wait_for_comment)
    CornerSignView wait_for_comment;

    @BindView(R.id.wait_for_sale)
    CornerSignView wait_for_sale;

    private String friendId = ""; // 官方客服

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        myOrderPresenter = new MyOrderPresenter(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        initUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, null);
        }
        ButterKnife.bind(this, rootView);
        initView();
        myOrderPresenter.orderStatusCount(getContext(), SPUtil.getPrefString("token", ""), DataType.myOrder.orderStatusCount.toString());
        return rootView;
    }

    @OnClick({R.id.ll_user_message, R.id.mine_all_order, R.id.mine_wait_pay, R.id.mine_wait_deliver, R.id.mine_wait_receive,
            R.id.mine_my_coupon, R.id.mine_my_favourite, R.id.mine_to_refund, R.id.mine_my_hong, R.id.mine_wait_appraise, R.id.mine_my_invite,
            R.id.mine_my_cut_price, R.id.mine_my_free_order, R.id.mine_receive_address, R.id.mine_official_service, R.id.mine_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            //我的订单
            case R.id.mine_all_order:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), OrderActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //待付款
            case R.id.mine_wait_pay:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("status", 1));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //待发货
            case R.id.mine_wait_deliver:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("status", 2));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //待收货
            case R.id.mine_wait_receive:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("status", 3));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //待评价
            case R.id.mine_wait_appraise:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("status", 4));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;


            //我的收藏
            case R.id.mine_my_favourite:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), FavoriteActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;
            //我的推荐
            case R.id.mine_my_invite:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), InviteActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;
            //我的红包
            case R.id.mine_my_hong:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), FavoriteActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //退款 / 售后
            case R.id.mine_to_refund:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), RefundActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //我的砍价
            case R.id.mine_my_cut_price:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), SubsidyActivity.class).putExtra("type", "me"));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //我的免单
            case R.id.mine_my_free_order:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), FreeOrderActivity.class).putExtra("type", "me"));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //我的省券
            case R.id.mine_my_coupon:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
//                        startActivity(new Intent(getActivity(),MyCouponGoodsActivity.class));
                        startActivity(new Intent(getActivity(), ColletActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }
                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //收货地址
            case R.id.mine_receive_address:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), AddressManageActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //官方客服
            case R.id.mine_official_service:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        if (!friendId.equals("")) {
                            TokenUtil.openChat(getContext(), friendId, "官方客服");
                        }
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;

            //设置
            case R.id.mine_setting:
                startActivity(new Intent(getActivity(), UserSettingActivity.class));
                break;


            case R.id.ll_user_message:
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                        startActivity(new Intent(getActivity(), AccountActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), MobailePhoneLoginActivity.class));
                    }

                } else {
                    startActivity(new Intent(getActivity(), WeChatLoginActivity.class));
                }
                break;
        }
    }

    protected void initView() {
        if (!SPUtil.getPrefString("token", "").equals("")) {
            ll_user_name.setVisibility(View.VISIBLE);
            tv_user_no.setVisibility(View.GONE);
        } else {
            ll_user_name.setVisibility(View.GONE);
            tv_user_no.setVisibility(View.VISIBLE);
            wait_for_pay.setMessageCount(0);
            wait_for_send.setMessageCount(0);
            wait_for_receive.setMessageCount(0);
            wait_for_comment.setMessageCount(0);
            wait_for_sale.setMessageCount(0);
        }
        initUserInfo();
    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        if (dataType.equals(DataType.myOrder.orderStatusCount.toString())) {

            OrderStatusCountBean statusCountBean = (OrderStatusCountBean) object;
            wait_for_pay.setMessageCount(statusCountBean.getContent().getWait_for_pay());
            wait_for_send.setMessageCount(statusCountBean.getContent().getWait_for_send());
            wait_for_receive.setMessageCount(statusCountBean.getContent().getWait_for_receive());
            wait_for_comment.setMessageCount(statusCountBean.getContent().getWait_for_comment());
            wait_for_sale.setMessageCount(statusCountBean.getContent().getAfter_sale());
            friendId = statusCountBean.getContent().getPlatform_chat_id();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // 统计订单数
        if (!SPUtil.getPrefString("token", "").equals("")) {
            myOrderPresenter.orderStatusCount(getContext(), SPUtil.getPrefString("token", ""), DataType.myOrder.orderStatusCount.toString());
        }
        initUserInfo();
    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        ((MainActivity) getActivity()).currentPage = 4;
        EventBus.getDefault().post(new ActionBarEvent("action"));
    }

    private void initUserInfo() {
        if (!TextUtils.isEmpty(SPUtil.getPrefString("nickName", ""))) { // 设置用户昵称
            ll_user_name.setVisibility(View.VISIBLE);
            tv_user_no.setVisibility(View.GONE);
            mTvNickName.setText(SPUtil.getPrefString("nickName", ""));
        } else {
        }
        int width = getResources().getDimensionPixelSize(R.dimen.x190);
        int height = getResources().getDimensionPixelSize(R.dimen.y190);
        if (!TextUtils.isEmpty(SPUtil.getPrefString("headimgurl", ""))) { // 设置用户头像
            GlideEngine.loadThumbnail(getActivity(), width, height, R.drawable.head_image, mImgFace, SPUtil.getPrefString("headimgurl", ""));
        } else {
            GlideEngine.loadThumbnail(getActivity(), width, height, R.drawable.head_image, mImgFace, R.drawable.head_image);
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        wait_for_pay.setMessageCount(0);
        wait_for_send.setMessageCount(0);
        wait_for_receive.setMessageCount(0);
        wait_for_comment.setMessageCount(0);
        wait_for_sale.setMessageCount(0);
    }


    //微信登录成功后要通知刷新页面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserMessageEvent event) {
        if (event.message.equals("message")) {
            initView();
            initUserInfo();
            myOrderPresenter.orderStatusCount(getContext(), SPUtil.getPrefString("token", ""), DataType.myOrder.orderStatusCount.toString());
        } else if (event.message.equals("user_out")) {
            initView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(getActivity());
        EventBus.getDefault().unregister(getActivity());
//        AlibcTradeSDK.destory();因阿里百川错误而注释
    }
}
