package com.dajukeji.hslz.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.Group;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.orderAddress.AddressManageActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.order.ExchangeSecuritiesBean;
import com.dajukeji.hslz.domain.order.ExchangeVoucherInfoBean;
import com.dajukeji.hslz.network.presenter.AddressPresenter;
import com.dajukeji.hslz.network.presenter.ExchangeVoucherPresenter;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 兑换产品页
 */
public class ExchangeSecuritiesActivity extends HttpBaseActivity {

    @BindView(R.id.tv_addressName)
    TextView tvAddressName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.visibility_address)
    Group visibilityAddress;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_goodsImg)
    ImageView ivGoodsImg;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName;
    @BindView(R.id.tv_voucher)
    TextView tvVoucher;
    @BindView(R.id.tv_goodsSpec)
    TextView tvGoodsSpec;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.checkbox_pay)
    CheckBox checkboxPay;
    @BindView(R.id.tv_createAddress)
    TextView tvCreateAddress;
    @BindView(R.id.time)
    TextView time;

    private ExchangeVoucherPresenter infoPresenter;
    private AddressPresenter addressPresenter;
    private ExchangeVoucherInfoBean infoBean;
    private Dialog walletDialog;
    private UserAddressBean addressBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_exchange);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
        infoPresenter = new ExchangeVoucherPresenter(this);
        addressPresenter = new AddressPresenter(this);

        setTitleBar("兑换产品", true);
        int securities_id = getIntent().getIntExtra("securities_id", 0);
        infoPresenter.getExchangeVoucherInfo(this, securities_id, SPUtil.getPrefString("token", ""), "页产品券信息");
        addressPresenter.getDefaultAddress(this, SPUtil.getPrefString("token", ""), "默认地址");
    }

    @Override
    protected void initView() {
        if (infoBean != null && "0".equals(infoBean.getStatus())) {
            ExchangeVoucherInfoBean.ContentBean content = infoBean.getContent();
            Glide.with(this).load(content.getGoods_main_photo()).into(ivGoodsImg);
            tvTitle.setText(content.getStore_name());
            tvGoodsName.setText(content.getGoods_name());
            tvGoodsSpec.setText(content.getSpec_info());
            tvCount.setText("×" + content.getCount());
            tvVoucher.setText("券" + content.getTransfer_price());
            time.setText("兑换有效期："+content.getValid_start_date()+"至"+content.getValid_end_date());//增加有效日期显示，在我的产品券中点击兑换商品进入
        }
        if (addressBean != null && "0".equals(addressBean.getStatus())) {
            tvCreateAddress.setVisibility(View.GONE);
            visibilityAddress.setVisibility(View.VISIBLE);
            tvAddressName.setText(addressBean.getContent().getTrueName());
            tvPhone.setText(addressBean.getContent().getMobile());
            tvAddress.setText("地址：" + addressBean.getContent().getAddress());
        } else {
            tvCreateAddress.setVisibility(View.VISIBLE);
            visibilityAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "页产品券信息":
                infoBean = (ExchangeVoucherInfoBean) object;
                initView();
                break;
            case "默认地址":
            case "查询选中的地址":
                addressBean = (UserAddressBean) object;
                initView();
                break;
            case "产品券兑换":
                walletDialog.dismiss();
                ExchangeSecuritiesBean bean = (ExchangeSecuritiesBean) object;
                ToastUtils.getInstance().showToast(this, bean.getMessage());
                finish();
                break;
        }
    }

    @OnClick({R.id.iv_help, R.id.tv_createAddress, R.id.btn_exchange, R.id.v_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_help:
                startActivity(new Intent(this, VoucherHelpActivity.class));
                break;
            case R.id.v_address:
            case R.id.tv_createAddress:
                Intent intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra("isSelectMode", true);
                startActivityForResult(intent, 131);
                break;
            case R.id.btn_exchange:
                showEditPayPwdDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 131 && resultCode == RESULT_OK) {
            addressPresenter.searchAddressInfo(this, SPUtil.getPrefString("token", ""), data.getLongExtra("addressId", 0), "查询选中的地址");
        }
    }

    @Override
    public void showHttpError(String error, String dataType) {
        super.showHttpError(error, dataType);
        if (walletDialog != null)
            walletDialog.dismiss();
    }

    /**
     * 输入密码Dialog
     */
    private void showEditPayPwdDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout, null);
        walletDialog = new Dialog(this, R.style.walletFrameWindowStyle);
        walletDialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        final Window window = walletDialog.getWindow();
        WindowManager.LayoutParams wl = window.getAttributes();
        //紧贴软键盘弹出
        wl.gravity = Gravity.BOTTOM;
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        walletDialog.onWindowAttributesChanged(wl);
        walletDialog.setCanceledOnTouchOutside(false);
        walletDialog.show();

        view.findViewById(R.id.tv_forgetPwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ExchangeSecuritiesActivity.this, SetupPayPwdCheckActivity.class));
            }
        });
        final PayPwdEditText ppet = view.findViewById(R.id.dialog_ppet);
        ImageView img_cancle = view.findViewById(R.id.img_cancle);
        //调用initStyle方法创建你需要设置的样式
        ppet.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
        img_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walletDialog.dismiss();
            }
        });
        ppet.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调

                //手动收起软键盘
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(ppet.getWindowToken(), 0);
                //可在此执行下一步操作
                String paypass = ppet.getPwdText();

                infoPresenter.exchangeSecurities(this, SPUtil.getPrefString("token", ""), infoBean.getContent().getSecurities_id(),
                        addressBean.getContent().getId(), paypass, "产品券兑换");
            }
        });
        //延迟弹起软键盘，使PayPwdEditText获取焦点
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ppet.setFocus();
            }
        }, 100);
    }
}
