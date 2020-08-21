package com.dajukeji.hslz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.jpay.JPay;

public class TestPayActivity extends AppCompatActivity {
    private EditText et_pay;
    private Button bt_pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pay);
        et_pay = (EditText) findViewById(R.id.et_pay);
        bt_pay = (Button) findViewById(R.id.bt_pay);
        bt_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aliPay(et_pay.getText().toString().trim());
            }
        });
    }

    private void aliPay(String orderInfo) { // 支付宝支付
        JPay.getIntance(TestPayActivity.this).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(TestPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TestPayActivity.this, OrderDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(TestPayActivity.this, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(TestPayActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
