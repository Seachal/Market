package com.dajukeji.hslz.domain.javaBean;

/**
 * Created by Administrator on 2018/1/26 0026.
 */

public class MonetaryBean {
    /**
     * message : 待付款费用
     * content : {"deduct_amount":9,"transfee_amount":3,"goods_amount":64,"real_pay":30,"cheap_amount":28}
     * status : 0
     */
    private String message;
    private ContentEntity content;
    private String status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public ContentEntity getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public class ContentEntity {
        /**
         * deduct_amount : 9.0
         * transfee_amount : 3.0
         * goods_amount : 64.0
         * real_pay : 30.0
         * cheap_amount : 28.0
         */
        private double deduct_amount;
        private double transfee_amount;
        private double goods_amount;
        private double real_pay;
        private double cheap_amount;

        public void setDeduct_amount(double deduct_amount) {
            this.deduct_amount = deduct_amount;
        }

        public void setTransfee_amount(double transfee_amount) {
            this.transfee_amount = transfee_amount;
        }

        public void setGoods_amount(double goods_amount) {
            this.goods_amount = goods_amount;
        }

        public void setReal_pay(double real_pay) {
            this.real_pay = real_pay;
        }

        public void setCheap_amount(double cheap_amount) {
            this.cheap_amount = cheap_amount;
        }

        public double getDeduct_amount() {
            return deduct_amount;
        }

        public double getTransfee_amount() {
            return transfee_amount;
        }

        public double getGoods_amount() {
            return goods_amount;
        }

        public double getReal_pay() {
            return real_pay;
        }

        public double getCheap_amount() {
            return cheap_amount;
        }
    }
}
