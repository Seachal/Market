package com.dajukeji.hslz.activity.mallorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.address.AddressListActivity;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.jpay.JPay;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.AmountView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static com.dajukeji.hslz.network.HttpAddress.mBaseUrl2;

public class OrderPayActivity extends HttpBaseActivity implements View.OnClickListener {

    private AmountView mAmountView;
    private LinearLayout order_address; // 选择收货地址

    private LinearLayout pay_order; //发起支付

    private LinearLayout chick_wechat;// 选择微信支付
    private LinearLayout chick_alipay;// 选择支付宝支付
    private ImageView is_wechat; // 选择微信支付图片
    private ImageView is_alipay; // 选择支付宝支付图片
    private TextView consignee_info; // 收货人信息
    private TextView consignee_address; //收货人地址
    private TextView real_price; // 订单支付金额

    private String payType = "wxpay"; // 默认支付方式为微信支付
    private UserAddressBean addressBean; // 用户地址
/*

    private String orderInfo="alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016090800466518&biz_content=%7B%22body%22%3A%22%E4%BD%A0%E5%80%BC%E5%BE%97%E6%8B%A5%E6%9C%89%22%2C%22out_trade_no%22%3A%2270501111111S001111187%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%B9%B0%E4%B9%B0%E4%B9%B0%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2269701.81%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%E5%95%86%E6%88%B7%E5%A4%96%E7%BD%91%E5%8F%AF%E4%BB%A5%E8%AE%BF%E9%97%AE%E7%9A%84%E5%BC%82%E6%AD%A5%E5%9C%B0%E5%9D%80&sign=C7IXmBz67G0e4CS%2FyeWX5kc1HlSKRZN0hLV5qmtd8HM7BsYgfGOVz%2BIY3rX%2F1Wpmy8PPzz2lxk4IE%2Bb4q4iH%2B5C8C1WJXv1ANkQ%2B1jj8%2Bu4k9kXJ7xrIW1AFDyEbvkqRFX5ThMD6MmDySPEW7Fj2WmoejDeQd3Vk5mfmXfRu9IgJGtj7UvlJ%2FPooOuDHpu6nxZGI8s7paX2a%2BTVpeYFDujvYqxaeDybeC%2BcbI5kKnwWQ9nCPn40%2FBCcrFCuaGbhft9VA2CHS%2FQtuhge0km4uDo9FodKTT4sMN0%2BgYDyHLmevnP7RJodJwCTtGNwd8CxJQF3fkrx5HYgajOMcE5WaRg%3D%3D&sign_type=RSA2&timestamp=2017-11-02+14%3A43%3A28&version=1.0&sign=C7IXmBz67G0e4CS%2FyeWX5kc1HlSKRZN0hLV5qmtd8HM7BsYgfGOVz%2BIY3rX%2F1Wpmy8PPzz2lxk4IE%2Bb4q4iH%2B5C8C1WJXv1ANkQ%2B1jj8%2Bu4k9kXJ7xrIW1AFDyEbvkqRFX5ThMD6MmDySPEW7Fj2WmoejDeQd3Vk5mfmXfRu9IgJGtj7UvlJ%2FPooOuDHpu6nxZGI8s7paX2a%2BTVpeYFDujvYqxaeDybeC%2BcbI5kKnwWQ9nCPn40%2FBCcrFCuaGbhft9VA2CHS%2FQtuhge0km4uDo9FodKTT4sMN0%2BgYDyHLmevnP7RJodJwCTtGNwd8CxJQF3fkrx5HYgajOMcE5WaRg%3D%3D"; // 支付宝支付订单信息
    private String payParameters = "********"; // 微信支付信息 为JSON字符串格式如下：
*/

    /*{
        "appId": "",
            "partnerId": "",
            "prepayId": "",
            "sign": "",
            "nonceStr" : "",
            "timeStamp": ""
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent goods = getIntent();
        String Item_id = goods.getStringExtra("Item_id");
        Toast.makeText(this, Item_id, Toast.LENGTH_SHORT).show();

        initDate();
        initView();
    }

    private void initDate() {
        getDefaultAddress();// 设置默认地址
    }

    @Override
    protected void initView() {
        initDate();
        setContentView(R.layout.activity_order_pay);
        setTitleBar(R.string.text_order_pay, true);
        mAmountView = (AmountView) findViewById(R.id.amount_view);
        order_address = (LinearLayout) findViewById(R.id.order_address);
        pay_order = (LinearLayout) findViewById(R.id.pay_order);
        chick_wechat = (LinearLayout) findViewById(R.id.chick_wechat);
        chick_alipay = (LinearLayout) findViewById(R.id.chick_alipay);
        is_wechat = (ImageView) findViewById(R.id.is_wechat);
        is_alipay = (ImageView) findViewById(R.id.is_alipay);
        consignee_info = (TextView) findViewById(R.id.consignee_info);
        consignee_address = (TextView) findViewById(R.id.consignee_address);
        real_price = (TextView) findViewById(R.id.real_price);

        real_price.setText("￥" + "12.36");

        mAmountView.setGoods_storage(2); // 最大购买数量
        mAmountView.setAmount(2);
        mAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                real_price.setText("￥" + amount * 12.36);
            }
        });
        order_address.setOnClickListener(this);
        pay_order.setOnClickListener(this);
        chick_wechat.setOnClickListener(this);
        chick_alipay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_address:
                Intent intent = new Intent(OrderPayActivity.this, AddressListActivity.class);
                startActivityForResult(intent, 100);
                break;
            case R.id.chick_wechat:
                is_alipay.setVisibility(View.INVISIBLE);
                is_wechat.setVisibility(View.VISIBLE);
                payType = "wxpay";
                break;
            case R.id.chick_alipay:
                is_wechat.setVisibility(View.INVISIBLE);
                is_alipay.setVisibility(View.VISIBLE);
                payType = "alipay";
                break;
            case R.id.pay_order:
                orderPay();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 选择收货人信息
         */
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            final Map map = (Map) data.getSerializableExtra("consigneeInfo");
            consignee_info.setText(map.get("consignee_username").toString() + "  " + map.get("consignee_phone").toString());
            consignee_address.setText(map.get("consignee_address").toString());
        }
    }

    private void orderPay() { // 唤起支付
        String url = mBaseUrl2 + "app/mall/pay/pay.htm";
        OkHttpUtils
                .post()
                .url(url)
                .addParams("pay_type", payType)
                .addParams("goodsId", "Id252455222")
                .addParams("userId", "x2552")
                .addParams("goodsCount", "2")
                .build()
                .execute(new MyStringCallback());
    }

    /**
     * 网络请求回调
     */
    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
        }

        @Override
        public void onResponse(String response, int id) {
            try {
                JSONObject json = new JSONObject(response);
                if (json.get("status").toString().equals("0")) {
                    JSONObject payResult = new JSONObject(json.get("content").toString());
                    String payType = payResult.get("paytype").toString();
                    if (payType.equals("wxpay")) { // 微信支付*/
                        wxPay(json.get("content").toString()); // 调用微信支付

                    } else { // 支付宝支付

                        String orderInfo = payResult.get("alipaymessge").toString();
                        aliPay(orderInfo); //调用支付宝支付
                    }
                } else {
                    Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(OrderPayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {

        }
    }


    private void aliPay(String orderInfo) { // 支付宝支付
        JPay.getIntance(OrderPayActivity.this).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(OrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(OrderPayActivity.this, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(OrderPayActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void wxPay(String payParameters) { // 微信支付
        JPay.getIntance(OrderPayActivity.this).toPay(JPay.PayMode.WXPAY, payParameters, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(OrderPayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(OrderPayActivity.this, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(OrderPayActivity.this, "取消了支付", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDefaultAddress() { //设置默认地址
        String url = mBaseUrl2 + HttpAddress.getDefaultAddress;
        OkHttpUtils.post().url(url)
                .addParams("token", SPUtil.getPrefString("token",""))
                .build()//
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Gson gson = new Gson();
                        addressBean = (UserAddressBean) gson.fromJson(response, UserAddressBean.class);
                        if (addressBean.getStatus().equals("0")) {
                            if (addressBean.getContent() != null) {
                                consignee_info.setText(addressBean.getContent().getTrueName() + " " + addressBean.getContent().getMobile());  // 设置默认收货人信息
                                consignee_address.setText(addressBean.getContent().getAddress() + addressBean.getContent().getArea_info()); //设置收货地址
                            }
                        } else {
                            Toast.makeText(OrderPayActivity.this, "获取地址失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
