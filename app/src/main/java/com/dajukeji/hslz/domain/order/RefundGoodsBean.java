package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 售后申请原因
 */

public class RefundGoodsBean {

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
         * icon : upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg
         * spec_info : 颜色:白色 尺码:M
         * price : 4.44
         * count : 9
         * reasonList : [{"refund_id":2,"refund_reason":"产品有损坏"},{"refund_id":4,"refund_reason":"1212"},{"refund_id":5,"refund_reason":"9563."},{"refund_id":7,"refund_reason":"14788999999"}]
         * refund : 40.0
         * editRefund : [{"service":1,"refund_info":"11","return_reason_id":1}]
         * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
         * gr_id : 81
         * deduct_amount : 5.0
         */

        private String icon;
        private String spec_info;
        private double price;
        private int count;
        private double refund;
        private String goods_name;
        private String gr_id;
        private double deduct_amount;
        private List<ReasonListBean> reasonList;
        private List<EditRefundBean> editRefund;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
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

        public double getRefund() {
            return refund;
        }

        public void setRefund(double refund) {
            this.refund = refund;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGr_id() {
            return gr_id;
        }

        public void setGr_id(String gr_id) {
            this.gr_id = gr_id;
        }

        public double getDeduct_amount() {
            return deduct_amount;
        }

        public void setDeduct_amount(double deduct_amount) {
            this.deduct_amount = deduct_amount;
        }

        public List<ReasonListBean> getReasonList() {
            return reasonList;
        }

        public void setReasonList(List<ReasonListBean> reasonList) {
            this.reasonList = reasonList;
        }

        public List<EditRefundBean> getEditRefund() {
            return editRefund;
        }

        public void setEditRefund(List<EditRefundBean> editRefund) {
            this.editRefund = editRefund;
        }

        public static class ReasonListBean {
            /**
             * refund_id : 2
             * refund_reason : 产品有损坏
             */

            private int refund_id;
            private String refund_reason;

            public int getRefund_id() {
                return refund_id;
            }

            public void setRefund_id(int refund_id) {
                this.refund_id = refund_id;
            }

            public String getRefund_reason() {
                return refund_reason;
            }

            public void setRefund_reason(String refund_reason) {
                this.refund_reason = refund_reason;
            }
        }

        public static class EditRefundBean {
            /**
             * service : 1
             * refund_info : 11
             * return_reason_id : 1
             */

            private int service;
            private String refund_info;
            private int return_reason_id;

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }

            public String getRefund_info() {
                return refund_info;
            }

            public void setRefund_info(String refund_info) {
                this.refund_info = refund_info;
            }

            public int getReturn_reason_id() {
                return return_reason_id;
            }

            public void setReturn_reason_id(int return_reason_id) {
                this.return_reason_id = return_reason_id;
            }
        }
    }
}
