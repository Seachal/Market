package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 * 退货退款提示详情
 */

public class ReturnPromptDetailBean {

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
         * rList : [{"icon":"upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg","spec_info":"颜色:红色 尺码:XXS ","count":1,"now_price":0.01,"goods_name":"正常产品1","before_price":0.01,"return_days":3}]
         * refund : 0.01
         * service : 1
         * refund_status : 1
         * refund_reason : 给我钱
         * deduct_amount : 0.0
         */

        private double refund;
        private int service;
        private int refund_status;
        private String refund_reason;
        private double deduct_amount;
        private List<RListBean> rList;

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

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public String getRefund_reason() {
            return refund_reason;
        }

        public void setRefund_reason(String refund_reason) {
            this.refund_reason = refund_reason;
        }

        public double getDeduct_amount() {
            return deduct_amount;
        }

        public void setDeduct_amount(double deduct_amount) {
            this.deduct_amount = deduct_amount;
        }

        public List<RListBean> getRList() {
            return rList;
        }

        public void setRList(List<RListBean> rList) {
            this.rList = rList;
        }

        public static class RListBean {
            /**
             * icon : upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg
             * spec_info : 颜色:红色 尺码:XXS
             * count : 1
             * now_price : 0.01
             * goods_name : 正常产品1
             * before_price : 0.01
             * return_days : 3
             */

            private String icon;
            private String spec_info;
            private int count;
            private double now_price;
            private String goods_name;
            private double before_price;
            private int return_days;

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

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public double getNow_price() {
                return now_price;
            }

            public void setNow_price(double now_price) {
                this.now_price = now_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getBefore_price() {
                return before_price;
            }

            public void setBefore_price(double before_price) {
                this.before_price = before_price;
            }

            public int getReturn_days() {
                return return_days;
            }

            public void setReturn_days(int return_days) {
                this.return_days = return_days;
            }
        }
    }
}
