package com.dajukeji.hslz.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.ColletActivity;
import com.dajukeji.hslz.activity.DaiActivity;
import com.dajukeji.hslz.activity.GoodsVoucherActivity;
import com.dajukeji.hslz.activity.WebActivity;
import com.dajukeji.hslz.base.HttpBaseFragment;
import com.dajukeji.hslz.domain.javaBean.UserInfoBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.presenter.UserInfoPersenter;
import com.dajukeji.hslz.util.MD5Utils;
import com.dajukeji.hslz.util.SPUtil;

import kotlin.random.Random;

/**
 * 我的资产页面
 */
public class ColletFragment extends HttpBaseFragment {

    private RelativeLayout dai, chan, zhuan;
    private LinearLayout linear_chan, linear_hong, linear_guang, linear_zhuan, linear_xiao;
    private int num;
    private TextView yhm, dqj;
    private String phone;
    private UserInfoPersenter userPersenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_collet, null);
        }
        initView(rootView);
        return rootView;
    }

    @SuppressLint("WrongViewCast")
    private void initView(View view) {
//        setTitleBar("我的资产", true);//
        ((TextView)rootView.findViewById(R.id.title_bar_title)).setText("我的资产");
        ((RelativeLayout)rootView.findViewById(R.id.title_bar_return)).setVisibility(View.GONE);
        dai = rootView.findViewById(R.id.dai);
        chan = rootView.findViewById(R.id.chan);
//        zhuan = rootView.findViewById(R.id.zhuan);
        linear_chan = (LinearLayout) rootView.findViewById(R.id.linear_chan);
        linear_hong = (LinearLayout) rootView.findViewById(R.id.linear_hong);
        linear_guang = (LinearLayout) rootView.findViewById(R.id.linear_guang);
        linear_zhuan = (LinearLayout) rootView.findViewById(R.id.linear_zhaun);
        linear_xiao = (LinearLayout) rootView.findViewById(R.id.linear_xiao);
        yhm = rootView.findViewById(R.id.yhm);
        dqj = rootView.findViewById(R.id.dqj);
//        ToastUtils.getInstance().showToast(ColletActivity.this,SPUtil.getPrefString("PhoneNumber",""));
        phone = SPUtil.getPrefString("phoneNumber", "");
//        Toast.makeText(ColletActivity.this, phone+"  ss", Toast.LENGTH_SHORT).show();
        userPersenter = new UserInfoPersenter(this);
        userPersenter.getUserInfo(this, "个人资料信息");

        //代金券
        dai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DaiActivity.class);
//                intent.putExtra("userId", zichanBean.getData().getId());
                startActivity(intent);
            }
        });
        //产品券
        chan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), GoodsVoucherActivity.class));
            }
        });
        //转卖场 砍掉这个功能
//        zhuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //按照H5说的拼接参数
//                String token = SPUtil.getPrefString("token", "");
//                StringBuilder nonce = new StringBuilder();
//                for (int i = 0; i < 6; i++) {
//                    nonce.append(Random.Default.nextInt(10));
//                }
//                String sign = MD5Utils.INSTANCE.dataToMD5(nonce + token + "helloworld123");
//
//                //跳转
//                String urlString = HttpAddress.mBaseUrl2 + "zmc/shopping_list.html?token=" + token + "&sign=" + sign + "&nonce=" + nonce;
//                startActivity(WebActivity.Companion.getStartIntent(getContext(), urlString));
////
//////                Uri uri = Uri.parse(urlString);
//////                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//////                startActivity(intent);
//
////                startActivity(new Intent(ColletActivity.this, MainResaleActivity.class));
//            }
//        });
        //产品销售额
        linear_chan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HttpAddress.mBaseUrl2+"App/index.html" +
                        "?token=" + SPUtil.getPrefString("token", "") +
                        "?type=1";
                startActivity(WebActivity.Companion.getStartIntent(getContext(), url));
//                num = 1;
//                Intent intent = new Intent(ColletActivity.this, AllActivity.class);
//                intent.putExtra("num", num);
//                startActivity(intent);
            }
        });
        //红包分成
        linear_hong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HttpAddress.mBaseUrl2+"App/index.html" +
                        "?token=" + SPUtil.getPrefString("token", "") +
                        "?type=4";
                startActivity(WebActivity.Companion.getStartIntent(getContext(), url));
//                num = 3;
//                Intent intent = new Intent(ColletActivity.this, AllActivity.class);
//                intent.putExtra("num", num);
//                startActivity(intent);
            }
        });
        //广告收益
        linear_guang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HttpAddress.mBaseUrl2+"App/index.html" +
                        "?token=" + SPUtil.getPrefString("token", "") +
                        "?type=5";
                startActivity(WebActivity.Companion.getStartIntent(getContext(), url));
//                num = 2;
//                Intent intent = new Intent(ColletActivity.this, AllActivity.class);
//                intent.putExtra("num", num);
//                startActivity(intent);
            }
        });
        //转卖场交易费
        linear_zhuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HttpAddress.mBaseUrl2+"App/index.html" +
                        "?token=" + SPUtil.getPrefString("token", "") +
                        "?type=3";
                startActivity(WebActivity.Companion.getStartIntent(getContext(), url));
//                num = 5;
//                Intent intent = new Intent(ColletActivity.this, AllActivity.class);
//                intent.putExtra("num", num);
//                startActivity(intent);
            }
        });
        //销售代金券
        linear_xiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = HttpAddress.mBaseUrl2+"App/index.html" +
                        "?token=" + SPUtil.getPrefString("token", "") +
                        "?type=2";
                startActivity(WebActivity.Companion.getStartIntent(getContext(), url));
//                num = 4;
//                Intent intent = new Intent(ColletActivity.this, AllActivity.class);
//                intent.putExtra("num", num);
//                startActivity(intent);
            }
        });

    }

    @Override
    public void setResultData(Object object, String dataType) {
        super.setResultData(object, dataType);
        switch (dataType) {
            case "个人资料信息":
                UserInfoBean infoBean = (UserInfoBean) object;
                yhm.setText(infoBean.getContent().getUserName());
                if (TextUtils.isEmpty(infoBean.getContent().getGradeRegion())) {
                    dqj.setText("普通用户");
                } else {
                    dqj.setText(infoBean.getContent().getAreaName() + "——" + infoBean.getContent().getGradeRegion());
                }
                //若不是经销商,就隐藏后两个item
                if (!TextUtils.isEmpty(infoBean.getContent().getAreaName())) {
                    rootView.findViewById(R.id.linear_xiao).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.linear_xiao_underline).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.linear_zhaun).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.linear_zhaun_underline).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.linear_hong).setVisibility(View.VISIBLE);
                    rootView.findViewById(R.id.linear_hong_underline).setVisibility(View.VISIBLE);
                }
//                SPUtil.setPrefInt("userId", infoBean.getContent().i());
                break;
        }
    }
}
