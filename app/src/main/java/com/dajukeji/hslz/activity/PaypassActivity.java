package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.BaseStatusMessageBean;
import com.dajukeji.hslz.network.presenter.SetupPasswordPresenter;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;

public class PaypassActivity extends HttpBaseActivity implements View.OnClickListener {
    private Button right_btn;
    private PayPwdEditText ppet, ppe;
    private String pass;
    private int old;
    private SetupPasswordPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypass);
        setTitleBar("设置支付密码", true);
        Intent intent = getIntent();
        pass = intent.getStringExtra("oldPass");
        if (pass == "") {
            old = 1;
        } else {
            old = 0;
        }
        ppet = (PayPwdEditText) findViewById(R.id.dialog_ppet);
        ppe = (PayPwdEditText) findViewById(R.id.dialog_pet);
        right_btn = findViewById(R.id.right_btn);
        right_btn.setOnClickListener(this);
        ppet.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
        ppe.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);

        presenter = new SetupPasswordPresenter(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_btn:
                if (ppe.getPwdText().equals("") || ppet.getPwdText().equals("")) {
                    Toast.makeText(getContext(), "请把两次密码都输入了哦", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(getContext(),ppe.getPwdText()+"",Toast.LENGTH_SHORT).show();
                    if (ppet.getPwdText().equals(ppe.getPwdText())) {

                        presenter.setupPayPassword(this, SPUtil.getPrefString("phoneNumber", ""), ppe.getPwdText(), "修改支付密码");
                        /* 旧接口
                        String url = HttpAddress.mBaseUrl2 + HttpAddress.getPaypass;
                        OkHttpUtils.post()//
                                .url(url)
                                .addParams("userPhone", SPUtil.getPrefString("phoneNumber", ""))
                                .addParams("payPass", ppe.getPwdText())
                                .addParams("checkOld", "0")//一定不校验旧支付密码
//                                .addParams("checkOld",old+"")
                                .addParams("newPayPass", pass + "")
                                .build()
                                .execute(new StringCallback() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        Toast.makeText(getContext(), "请求错误", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onResponse(String response, int id) {
                                        PaypassBean paypassBean = new Gson().fromJson(response, PaypassBean.class);
                                        if (paypassBean.getCode() == 0) {
                                            Toast.makeText(getContext(), paypassBean.getMsg() + "", Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else {
                                            Toast.makeText(getContext(), paypassBean.getMsg() + "", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });*/
                    } else {
                        Toast.makeText(getContext(), "密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
        switch (contentType) {
            case "修改支付密码":
                BaseStatusMessageBean object1 = (BaseStatusMessageBean) object;
                ToastUtils.getInstance().showToast(this, object1.getMessage());
                if ("0".equals(object1.getStatus()))
                    finish();
                break;
        }
    }
}