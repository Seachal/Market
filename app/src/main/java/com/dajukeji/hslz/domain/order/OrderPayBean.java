package com.dajukeji.hslz.domain.order;

/**
 * Created by Administrator on 2018/1/31.
 */

public class OrderPayBean {

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
         * goods_amount_total : 0.01
         * keep_hours : 24
         * deduct_amount_total : 0.0
         * integration_amount_total : 0
         * real_pay_total : 0.01
         * cheap_amount_total : 0.0
         * back_integration : 0
         * transfee_total : 0.0
         * create_time : 2018-01-31 18:04:54
         * order_sn : 3280620180131180454
         */

        private double goods_amount_total;
        private int keep_hours;
        private double deduct_amount_total;
        private int integration_amount_total;
        private double real_pay_total;
        private double cheap_amount_total;
        private int back_integration;
        private double transfee_total;
        private String create_time;
        private String order_sn;

        public double getGoods_amount_total() {
            return goods_amount_total;
        }

        public void setGoods_amount_total(double goods_amount_total) {
            this.goods_amount_total = goods_amount_total;
        }

        public int getKeep_hours() {
            return keep_hours;
        }

        public void setKeep_hours(int keep_hours) {
            this.keep_hours = keep_hours;
        }

        public double getDeduct_amount_total() {
            return deduct_amount_total;
        }

        public void setDeduct_amount_total(double deduct_amount_total) {
            this.deduct_amount_total = deduct_amount_total;
        }

        public int getIntegration_amount_total() {
            return integration_amount_total;
        }

        public void setIntegration_amount_total(int integration_amount_total) {
            this.integration_amount_total = integration_amount_total;
        }

        public double getReal_pay_total() {
            return real_pay_total;
        }

        public void setReal_pay_total(double real_pay_total) {
            this.real_pay_total = real_pay_total;
        }

        public double getCheap_amount_total() {
            return cheap_amount_total;
        }

        public void setCheap_amount_total(double cheap_amount_total) {
            this.cheap_amount_total = cheap_amount_total;
        }

        public int getBack_integration() {
            return back_integration;
        }

        public void setBack_integration(int back_integration) {
            this.back_integration = back_integration;
        }

        public double getTransfee_total() {
            return transfee_total;
        }

        public void setTransfee_total(double transfee_total) {
            this.transfee_total = transfee_total;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }
    }
}
