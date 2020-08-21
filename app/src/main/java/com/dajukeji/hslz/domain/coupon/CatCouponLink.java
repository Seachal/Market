package com.dajukeji.hslz.domain.coupon;

/**
 * Created by Administrator on 2018/1/16.
 */

public class CatCouponLink {


    /**
     * status : 0
     * message : 淘宝客二合一转链
     * content : {"link":{"category_id":"50468001","coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=hYehE852OCgGQASttHIRqXjKsLI5x6fiElAxyrvB6mZi9nxQBh3aHTGSXLfVPJpMYTrLZJWlG%2BYIHVFa1QZvqb9fwBwwUiqldqt9eaSObun8MPlBfA5uoQ%3D%3D&traceId=0ab84e8c15161518853815900e&&activityId=a737c268e8044a92bb07458c736e44b0","coupon_end_time":"2018-01-21","coupon_info":"满900元减300元","coupon_remain_count":"483","coupon_start_time":"2018-01-16","coupon_total_count":"500","coupon_type":"3","item_id":"547538872382","max_commission_rate":"25.60"}}
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
         * link : {"category_id":"50468001","coupon_click_url":"https://uland.taobao.com/coupon/edetail?e=hYehE852OCgGQASttHIRqXjKsLI5x6fiElAxyrvB6mZi9nxQBh3aHTGSXLfVPJpMYTrLZJWlG%2BYIHVFa1QZvqb9fwBwwUiqldqt9eaSObun8MPlBfA5uoQ%3D%3D&traceId=0ab84e8c15161518853815900e&&activityId=a737c268e8044a92bb07458c736e44b0","coupon_end_time":"2018-01-21","coupon_info":"满900元减300元","coupon_remain_count":"483","coupon_start_time":"2018-01-16","coupon_total_count":"500","coupon_type":"3","item_id":"547538872382","max_commission_rate":"25.60"}
         */

        private LinkBean link;

        public LinkBean getLink() {
            return link;
        }

        public void setLink(LinkBean link) {
            this.link = link;
        }

        public static class LinkBean {
            /**
             * category_id : 50468001
             * coupon_click_url : https://uland.taobao.com/coupon/edetail?e=hYehE852OCgGQASttHIRqXjKsLI5x6fiElAxyrvB6mZi9nxQBh3aHTGSXLfVPJpMYTrLZJWlG%2BYIHVFa1QZvqb9fwBwwUiqldqt9eaSObun8MPlBfA5uoQ%3D%3D&traceId=0ab84e8c15161518853815900e&&activityId=a737c268e8044a92bb07458c736e44b0
             * coupon_end_time : 2018-01-21
             * coupon_info : 满900元减300元
             * coupon_remain_count : 483
             * coupon_start_time : 2018-01-16
             * coupon_total_count : 500
             * coupon_type : 3
             * item_id : 547538872382
             * max_commission_rate : 25.60
             */

            private String category_id;
            private String coupon_click_url;
            private String coupon_end_time;
            private String coupon_info;
            private String coupon_remain_count;
            private String coupon_start_time;
            private String coupon_total_count;
            private String coupon_type;
            private String item_id;
            private String max_commission_rate;

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCoupon_click_url() {
                return coupon_click_url;
            }

            public void setCoupon_click_url(String coupon_click_url) {
                this.coupon_click_url = coupon_click_url;
            }

            public String getCoupon_end_time() {
                return coupon_end_time;
            }

            public void setCoupon_end_time(String coupon_end_time) {
                this.coupon_end_time = coupon_end_time;
            }

            public String getCoupon_info() {
                return coupon_info;
            }

            public void setCoupon_info(String coupon_info) {
                this.coupon_info = coupon_info;
            }

            public String getCoupon_remain_count() {
                return coupon_remain_count;
            }

            public void setCoupon_remain_count(String coupon_remain_count) {
                this.coupon_remain_count = coupon_remain_count;
            }

            public String getCoupon_start_time() {
                return coupon_start_time;
            }

            public void setCoupon_start_time(String coupon_start_time) {
                this.coupon_start_time = coupon_start_time;
            }

            public String getCoupon_total_count() {
                return coupon_total_count;
            }

            public void setCoupon_total_count(String coupon_total_count) {
                this.coupon_total_count = coupon_total_count;
            }

            public String getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(String coupon_type) {
                this.coupon_type = coupon_type;
            }

            public String getItem_id() {
                return item_id;
            }

            public void setItem_id(String item_id) {
                this.item_id = item_id;
            }

            public String getMax_commission_rate() {
                return max_commission_rate;
            }

            public void setMax_commission_rate(String max_commission_rate) {
                this.max_commission_rate = max_commission_rate;
            }
        }
    }
}
