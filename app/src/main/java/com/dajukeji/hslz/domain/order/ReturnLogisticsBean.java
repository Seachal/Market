package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ReturnLogisticsBean {


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
         * company_list : [{"company_id":1,"company_name":"顺丰速递"},{"company_id":2,"company_name":"申通"},{"company_id":3,"company_name":"天天快递"},{"company_id":29,"company_name":"圆通"}]
         * return_reason : 发错款式
         * rList : [{"spec_info":"颜色:白色 尺码:M ","count":1,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子","one_price":13.33}]
         * refund : 13.33
         * service : 0
         * return_id : 151564156
         * gr_id : 退货编号
         */

        private String return_reason;
        private double refund;
        private int service;
        private String return_id;
        private String gr_id;
        private List<CompanyListBean> company_list;
        private List<RListBean> rList;

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

        public String getReturn_id() {
            return return_id;
        }

        public void setReturn_id(String return_id) {
            this.return_id = return_id;
        }

        public String getGr_id() {
            return gr_id;
        }

        public void setGr_id(String gr_id) {
            this.gr_id = gr_id;
        }

        public List<CompanyListBean> getCompany_list() {
            return company_list;
        }

        public void setCompany_list(List<CompanyListBean> company_list) {
            this.company_list = company_list;
        }

        public List<RListBean> getRList() {
            return rList;
        }

        public void setRList(List<RListBean> rList) {
            this.rList = rList;
        }

        public static class CompanyListBean {
            /**
             * company_id : 1
             * company_name : 顺丰速递
             */

            private int company_id;
            private String company_name;

            public int getCompany_id() {
                return company_id;
            }

            public void setCompany_id(int company_id) {
                this.company_id = company_id;
            }

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }
        }

        public static class RListBean {
            /**
             * spec_info : 颜色:白色 尺码:M
             * count : 1
             * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
             * one_price : 13.33
             */

            private String spec_info;
            private int count;
            private String goods_name;
            private double one_price;
            private String icon;

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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getOne_price() {
                return one_price;
            }

            public void setOne_price(double one_price) {
                this.one_price = one_price;
            }
        }
    }
}
