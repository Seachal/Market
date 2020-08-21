package com.dajukeji.hslz.activity.mine;

import com.google.gson.annotations.SerializedName;

public class PaywxBean {

    /**
     * status : 0
     * message : 下单成功
     * content : {"sign":"E431B3C89D9A78075AC2C2A7FBCD661F","timestamp":1509698186,"noncestr":"310eea9fa85e9065c59067288a1c205c","partnerid":"1377616702","paytype":"wxpay","package":"Sign=WXPay","appid":"wx8be958660b092629","order_id":"229962"}
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
         * sign : E431B3C89D9A78075AC2C2A7FBCD661F
         * timestamp : 1509698186
         * noncestr : 310eea9fa85e9065c59067288a1c205c
         * partnerid : 1377616702
         * paytype : wxpay
         * package : Sign=WXPay
         * appid : wx8be958660b092629
         * order_id : 229962
         */

        private String sign;
        private int timestamp;
        private String noncestr;
        private String partnerid;
        private String paytype;
        @SerializedName("package")
        private String packageX;
        private String appid;
        private String order_id;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
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

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
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

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
