package com.dajukeji.hslz.domain.order;

/**
 * Created by Administrator on 2018/2/1.
 * 通过产品编号获取待付款费用
 */

public class OrderWaitPriceBean {


    /**
     * status : 0
     * message : 待付款费用
     * content : {"goods_amount_total":64,"deduct_amount_total":2.53,"integration_amount_total":253,"real_pay_total":37.47,"cheap_amount_total":28,"transfee_total":4}
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
         * goods_amount_total : 64.0
         * deduct_amount_total : 2.53
         * integration_amount_total : 253
         * real_pay_total : 37.47
         * cheap_amount_total : 28.0
         * transfee_total : 4.0
         */

        private double goods_amount_total;
        private double deduct_amount_total;
        private int integration_amount_total;
        private double real_pay_total;
        private double cheap_amount_total;
        private double transfee_total;
        private String spec_info;

        public String getSpec_info() {
            return spec_info;
        }

        public void setSpec_info(String spec_info) {
            this.spec_info = spec_info;
        }

        public double getGoods_amount_total() {
            return goods_amount_total;
        }

        public void setGoods_amount_total(double goods_amount_total) {
            this.goods_amount_total = goods_amount_total;
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

        public double getTransfee_total() {
            return transfee_total;
        }

        public void setTransfee_total(double transfee_total) {
            this.transfee_total = transfee_total;
        }
    }
}
