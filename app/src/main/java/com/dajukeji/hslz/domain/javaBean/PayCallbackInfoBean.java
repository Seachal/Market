package com.dajukeji.hslz.domain.javaBean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ${wangjiasheng} on 2017/12/13 0013.
 */

public class PayCallbackInfoBean {

    /**
     * status : 0
     * message : 支付信息
     * content : {"alipaymessge":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016090800466518&biz_content=%7B%22body%22%3A%22%E7%9C%81%E4%B8%80%E6%B3%A2%E6%94%AF%E4%BB%98%E5%AE%9D%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22229490%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%9C%81%E4%B8%80%E6%B3%A2%E6%94%AF%E4%BB%98%E5%AE%9D%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2227.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fspidermancsp.free.ngrok.cc%2Fwemall%2Fapp%2Fmall%2Fpay%2Falipayback.htm&sign=Fim8k5s0DImgu6hbh6XeZVyNruyG0Mmn%2B%2F77mQbbS3kLDoA7s4LeQZCxXGnpN0gbJb6m7jlGXDp8nwipFoAXPD4mtTu6TxUSzF%2FOFQSNrDEY14PJh9KQFmN4JOSTZi4eY1WkdKUPNBv%2FNk%2FnCw1PSO4ZMrZKa7Nc6iQdqEUpU%2FZMk61M2m4GjrJs1P4QJqcB9Sppa4LnjVaCvEjZX6ljnsY0%2BJqgnCOzk2iU5Un4HesTMAC5X2yEGrujjzXblOeKMxZ5OPUgPjk51jjDOWHuUX2tcfBnwIsQqs3BLjBk%2FFpYx%2FY5HdurObRFTeCRWiGKBpYsYSzsbWlrvfNqDvWQWw%3D%3D&sign_type=RSA2&timestamp=2017-11-20+00%3A52%3A26&version=1.0","paytype":"alipay","sign":"F4508EB4ACCCD3580DF4E54143A53031","timestamp":1511110521,"noncestr":"4f6e590378fdbf15bd6beaf2073d4622","partnerid":"1377616702","package":"Sign=WXPay","appid":"wx61c4bd647f247da9"}
     */

    private String status;
    private String message;
    private ContentBean content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * alipaymessge : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016090800466518&biz_content=%7B%22body%22%3A%22%E7%9C%81%E4%B8%80%E6%B3%A2%E6%94%AF%E4%BB%98%E5%AE%9D%E6%94%AF%E4%BB%98%22%2C%22out_trade_no%22%3A%22229490%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E7%9C%81%E4%B8%80%E6%B3%A2%E6%94%AF%E4%BB%98%E5%AE%9D%E6%94%AF%E4%BB%98%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2227.00%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fspidermancsp.free.ngrok.cc%2Fwemall%2Fapp%2Fmall%2Fpay%2Falipayback.htm&sign=Fim8k5s0DImgu6hbh6XeZVyNruyG0Mmn%2B%2F77mQbbS3kLDoA7s4LeQZCxXGnpN0gbJb6m7jlGXDp8nwipFoAXPD4mtTu6TxUSzF%2FOFQSNrDEY14PJh9KQFmN4JOSTZi4eY1WkdKUPNBv%2FNk%2FnCw1PSO4ZMrZKa7Nc6iQdqEUpU%2FZMk61M2m4GjrJs1P4QJqcB9Sppa4LnjVaCvEjZX6ljnsY0%2BJqgnCOzk2iU5Un4HesTMAC5X2yEGrujjzXblOeKMxZ5OPUgPjk51jjDOWHuUX2tcfBnwIsQqs3BLjBk%2FFpYx%2FY5HdurObRFTeCRWiGKBpYsYSzsbWlrvfNqDvWQWw%3D%3D&sign_type=RSA2&timestamp=2017-11-20+00%3A52%3A26&version=1.0
         * paytype : alipay
         * sign : F4508EB4ACCCD3580DF4E54143A53031
         * timestamp : 1511110521
         * noncestr : 4f6e590378fdbf15bd6beaf2073d4622
         * partnerid : 1377616702
         * package : Sign=WXPay
         * appid : wx61c4bd647f247da9
         */

        private String alipaymessge;
        private String paytype;
        private String sign;
        private String timestamp;
        private String noncestr;
        private String partnerid;
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String prepayid;
        private String order_id;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getAlipaymessge() {
            return alipaymessge;
        }

        public void setAlipaymessge(String alipaymessge) {
            this.alipaymessge = alipaymessge;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }
    }
}
