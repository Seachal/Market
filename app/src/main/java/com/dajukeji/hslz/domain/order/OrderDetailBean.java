package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/23.
 */

public class OrderDetailBean {

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
         * receiver : 乒乒乓乓
         * auto_des : 还剩6天23时53分自动确认收货
         * accept_station : 客户 签收人: 广大网点代签 已签收 感谢使用圆通速递，期待再次为您服务
         * pay_time : 2018-01-19 19:54
         * receive_time : 2018-01-20 19:54
         * total_price : 298.0
         * order_status : 30
         * order_status_des : 待收货
         * goodsList : [{"refund_type":2,"spec_info":"颜色:红色 尺码:XXS ","price":54,"count":2,"original_price":54,"goods_name":"女人味风衣","return_days":7,"refund_count":0,"can_not_refund_reason":"已超过退货有效期限","gc_id":325,"photo":""},{"refund_type":2,"spec_info":"颜色:紫色 尺码:XXL ","price":9.9,"count":2,"original_price":23,"goods_name":"儿童味风衣","return_days":7,"refund_count":1,"can_not_refund_reason":"已超过退货有效期限","gc_id":326,"photo":""}]
         * address : 辽宁省丹东市东港市来来来看见回话
         * back_integration : 0
         * ship_price : 8.0
         * create_time : 2018-01-19 19:52
         * real_pay : 271.8
         * order_sn : 3283120180119195228
         * accept_time : 2017-12-22 18:57:59
         * cheap_amount : 26.2
         * deduct_amount : 0.0
         * ship_time : 2018-01-22 22:08
         * mobile : 18226396850
         */

        private String receiver;
        private String auto_des;
        private String accept_station;
        private String pay_time;
        private String receive_time;
        private double total_price;
        private int order_status;
        private String order_status_des;
        private String address;
        private int back_integration;
        private double ship_price;
        private String create_time;
        private double real_pay;
        private String order_sn;
        private String accept_time;
        private double cheap_amount;
        private double deduct_amount;
        private String ship_time;
        private String mobile;
        private int payable;
        private String chat_id;
        private String store_name;

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getChat_id() {
            return chat_id;
        }

        public void setChat_id(String chat_id) {
            this.chat_id = chat_id;
        }

        public int getPayable() {
            return payable;
        }

        public void setPayable(int payable) {
            this.payable = payable;
        }

        private List<GoodsListBean> goodsList;

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getAuto_des() {
            return auto_des;
        }

        public void setAuto_des(String auto_des) {
            this.auto_des = auto_des;
        }

        public String getAccept_station() {
            return accept_station;
        }

        public void setAccept_station(String accept_station) {
            this.accept_station = accept_station;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getReceive_time() {
            return receive_time;
        }

        public void setReceive_time(String receive_time) {
            this.receive_time = receive_time;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getOrder_status_des() {
            return order_status_des;
        }

        public void setOrder_status_des(String order_status_des) {
            this.order_status_des = order_status_des;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getBack_integration() {
            return back_integration;
        }

        public void setBack_integration(int back_integration) {
            this.back_integration = back_integration;
        }

        public double getShip_price() {
            return ship_price;
        }

        public void setShip_price(double ship_price) {
            this.ship_price = ship_price;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public double getReal_pay() {
            return real_pay;
        }

        public void setReal_pay(double real_pay) {
            this.real_pay = real_pay;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getAccept_time() {
            return accept_time;
        }

        public void setAccept_time(String accept_time) {
            this.accept_time = accept_time;
        }

        public double getCheap_amount() {
            return cheap_amount;
        }

        public void setCheap_amount(double cheap_amount) {
            this.cheap_amount = cheap_amount;
        }

        public double getDeduct_amount() {
            return deduct_amount;
        }

        public void setDeduct_amount(double deduct_amount) {
            this.deduct_amount = deduct_amount;
        }

        public String getShip_time() {
            return ship_time;
        }

        public void setShip_time(String ship_time) {
            this.ship_time = ship_time;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * refund_type : 2 退款类型，0为不能退款退货，1为能退款，2为能退货
             * spec_info : 颜色:红色 尺码:XXS
             * price : 54.0
             * count : 2
             * original_price : 54.0
             * goods_name : 女人味风衣
             * return_days : 7
             * refund_count : 0
             * can_not_refund_reason : 已超过退货有效期限
             * gc_id : 325
             * photo :
             */

            private int refund_type;
            private String spec_info;
            private double price;
            private int count;
            private double original_price;
            private String goods_name;
            private int return_days;
            private int refund_count;
            private String can_not_refund_reason;
            private int gc_id;
            private String photo;
            private String zone_type;
            private long goods_id;
            private boolean securities;//false=产品 true=产品券

            public boolean isSecurities() {
                return securities;
            }

            public void setSecurities(boolean securities) {
                this.securities = securities;
            }

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public long getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(long goods_id) {
                this.goods_id = goods_id;
            }

            public int getRefund_type() {
                return refund_type;
            }

            public void setRefund_type(int refund_type) {
                this.refund_type = refund_type;
            }

            public String getSpec_info() {
                return spec_info;
            }

            public void setSpec_info(String spec_info) {
                this.spec_info = spec_info;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(double original_price) {
                this.original_price = original_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getReturn_days() {
                return return_days;
            }

            public void setReturn_days(int return_days) {
                this.return_days = return_days;
            }

            public int getRefund_count() {
                return refund_count;
            }

            public void setRefund_count(int refund_count) {
                this.refund_count = refund_count;
            }

            public String getCan_not_refund_reason() {
                return can_not_refund_reason;
            }

            public void setCan_not_refund_reason(String can_not_refund_reason) {
                this.can_not_refund_reason = can_not_refund_reason;
            }

            public int getGc_id() {
                return gc_id;
            }

            public void setGc_id(int gc_id) {
                this.gc_id = gc_id;
            }

            public String getPhoto() {
                return photo;
            }

            public void setPhoto(String photo) {
                this.photo = photo;
            }
        }
    }
}
