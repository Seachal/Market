package com.dajukeji.hslz.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.domain.javaBean.TransferTargetInfoBean;
import com.dajukeji.hslz.network.presenter.TransferPresenter;
import com.dajukeji.hslz.util.FormatCheckUtils;
import com.dajukeji.hslz.util.PhoneFormatCheckUtils;
import com.dajukeji.hslz.util.ToastUtils;

/**
 * 转账页
 */
public class MoneyActivity extends HttpBaseActivity {

    public EditText editTargetPhone;
    public TextView tvTargetName;
    public TextView tvTargetRealName;
    public EditText editTransferMoney;
    public EditText editPayPassword;
    private int targetID = -1;

    public TransferPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);

        String money = getIntent().getStringExtra("money");
        //initView
        ((TextView) findViewById(R.id.tv_canUsedMoney)).setText(money);
        editTargetPhone = findViewById(R.id.edit_targetPhone);
        tvTargetName = findViewById(R.id.tv_targetName);
        tvTargetRealName = findViewById(R.id.tv_targetRealName);
        editTransferMoney = findViewById(R.id.edit_transferMoney);
        editPayPassword = findViewById(R.id.edit_payPassword);
        findViewById(R.id.btn_checkTarget).setOnClickListener(this);
        findViewById(R.id.btn_doTransfer).setOnClickListener(this);
        //对方手机账号输入完毕后，将“确定”按钮变为可点击的样式
        editTargetPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phone = editable.toString();
                if (FormatCheckUtils.INSTANCE.checkPhone(phone) == null)
                    findViewById(R.id.btn_checkTarget).setEnabled(true);
                else findViewById(R.id.btn_checkTarget).setEnabled(false);
            }
        });

        setTitleBar("转账", true);

        presenter = new TransferPresenter(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        String phone = editTargetPhone.getText().toString();
        switch (v.getId()) {
            case R.id.btn_checkTarget://查询对方信息
                if (PhoneFormatCheckUtils.isPhoneLegal(phone)) {
                    presenter.findTransferTarget(this, phone, "转账人");
                } else {
                    ToastUtils.getInstance().showToast(this, "对方手机号填写有误");
                }
                break;

            case R.id.btn_doTransfer://开始转账
                String money = editTransferMoney.getText().toString();
                String password = editPayPassword.getText().toString();
                if (targetID == -1) {
                    ToastUtils.getInstance().showToast(this,"请先确认转账人信息");
                } else
                    presenter.doTransfer(this, "" + targetID, money, password, "进行转账");

                //点击转账后3S后才可再次点击
                findViewById(R.id.btn_doTransfer).setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.btn_doTransfer).setEnabled(true);
                    }
                }, 3000L);
                break;
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "转账人":
                TransferTargetInfoBean targetBean = (TransferTargetInfoBean) object;
                if ("0".equals(targetBean.getStatus())) {
                    tvTargetName.setText(targetBean.getContent().getUserName());
                    tvTargetRealName.setText(targetBean.getContent().getTrueName());
                    targetID = targetBean.getContent().getUser_id();
                } else {
                    ToastUtils.getInstance().showToast(this, targetBean.getMessage());
                }
                break;

            case "进行转账":
                BaseStatusMessageBean resultBean = (BaseStatusMessageBean) object;
                if ("0".equals(resultBean.getStatus())) {
                    ToastUtils.getInstance().showToast(this, resultBean.getMessage());
                    finish();
                } else {
                    ToastUtils.getInstance().showToast(this, resultBean.getMessage());
                }
                break;
        }
    }
}
