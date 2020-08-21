package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 * 售后申请原因
 */

public class ReturnGoodsBean {

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
         * one_deduct_amount : 0.56
         * gc_id : 230021
         * icon : upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg
         * spec_info : 颜色:白色 尺码:M
         * editReturn : [{"return_info":"申请退","return_reason":"发错款式","refund":172.5,"service":0,"return_reason_id":1,"return_count":1,"deduct_amount":1}]
         * reasonList : [{"reason":"发错款式","z_apply_reason_id":1},{"reason":"123","z_apply_reason_id":3},{"reason":"100","z_apply_reason_id":6}]
         * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
         * sum : 9
         * gr_id : 81
         * one_price : 4.44
         */

        private double one_deduct_amount;
        private String gc_id;
        private String icon;
        private String spec_info;
        private String goods_name;
        private int sum;
        private String gr_id;
        private double one_price;
        private List<EditReturnBean> editReturn;
        private List<ReasonListBean> reasonList;

        public double getOne_deduct_amount() {
            return one_deduct_amount;
        }

        public void setOne_deduct_amount(double one_deduct_amount) {
            this.one_deduct_amount = one_deduct_amount;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

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

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getSum() {
            return sum;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public String getGr_id() {
            return gr_id;
        }

        public void setGr_id(String gr_id) {
            this.gr_id = gr_id;
        }

        public double getOne_price() {
            return one_price;
        }

        public void setOne_price(double one_price) {
            this.one_price = one_price;
        }

        public List<EditReturnBean> getEditReturn() {
            return editReturn;
        }

        public void setEditReturn(List<EditReturnBean> editReturn) {
            this.editReturn = editReturn;
        }

        public List<ReasonListBean> getReasonList() {
            return reasonList;
        }

        public void setReasonList(List<ReasonListBean> reasonList) {
            this.reasonList = reasonList;
        }

        public static class EditReturnBean {
            /**
             * return_info : 申请退
             * return_reason : 发错款式
             * refund : 172.5
             * service : 0
             * return_reason_id : 1
             * return_count : 1
             * deduct_amount : 1.0
             */

            private String return_info;
            private String return_reason;
            private double refund;
            private int service;
            private int return_reason_id;
            private int return_count;
            private double deduct_amount;

            public String getReturn_info() {
                return return_info;
            }

            public void setReturn_info(String return_info) {
                this.return_info = return_info;
            }

            public String getReturn_reason() {
                return return_reason;
            }

            public void setReturn_reason(String return_reason) {
                this.return_reason = return_reason;
            }

            public double getRefund() {
                return refund;
            }

            public void setRefund(double refund) {
                this.refund = refund;
            }

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }

            public int getReturn_reason_id() {
                return return_reason_id;
            }

            public void setReturn_reason_id(int return_reason_id) {
                this.return_reason_id = return_reason_id;
            }

            public int getReturn_count() {
                return return_count;
            }

            public void setReturn_count(int return_count) {
                this.return_count = return_count;
            }

            public double getDeduct_amount() {
                return deduct_amount;
            }

            public void setDeduct_amount(double deduct_amount) {
                this.deduct_amount = deduct_amount;
            }
        }

        public static class ReasonListBean {
            /**
             * reason : 发错款式
             * z_apply_reason_id : 1
             */

            private String reason;
            private int z_apply_reason_id;

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public int getZ_apply_reason_id() {
                return z_apply_reason_id;
            }

            public void setZ_apply_reason_id(int z_apply_reason_id) {
                this.z_apply_reason_id = z_apply_reason_id;
            }
        }
    }
}
