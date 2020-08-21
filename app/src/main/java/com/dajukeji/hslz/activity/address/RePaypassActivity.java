package com.dajukeji.hslz.activity.address;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.PaypassActivity;
import com.dajukeji.hslz.activity.mine.PaypassBean;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.PayPwdEditText;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.zip.Inflater;

import okhttp3.Call;

public class RePaypassActivity extends HttpBaseActivity {

    private PayPwdEditText ppe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_paypass);
        setTitleBar("修改支付密码",true);
//        Intent intent = getIntent();
//        String pass = intent.getStringExtra("cashCouponPassword");
        ppe = (PayPwdEditText) findViewById(R.id.dialog_pet);
        ppe.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.gray, R.color.black, 30);
        ppe.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {
                String url = HttpAddress.mBaseUrl2+"wallet/app/dealers/user/checkPayPass.action";
                OkHttpUtils.post()//
                        .url(url)
                        .addParams("userPhone", SPUtil.getPrefString("phoneNumber",""))
                        .addParams("oldPass",ppe.getPwdText())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getContext(),"请求错误",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                PaypassBean paypassBean = new Gson().fromJson(response,PaypassBean.class);
                                if (paypassBean.getCode()==0){
                                    Toast.makeText(getContext(),paypassBean.getMsg()+"",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RePaypassActivity.this, PaypassActivity.class);
                                    intent.putExtra("oldPass",ppe.getPwdText());
                                    startActivity(intent);
                                    finish();
                                }else {
                                    Toast.makeText(getContext(),paypassBean.getMsg()+"",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
