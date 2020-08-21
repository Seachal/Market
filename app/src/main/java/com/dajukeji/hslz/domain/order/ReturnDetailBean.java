package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/31.
 * 退货退款订单详情
 */

public class ReturnDetailBean {

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
         * return_info :
         * refund_id : 2018013022044932806
         * return_reason :
         * apply_time : 2018-01-30 22:04:49
         * set_waybill :
         * agree_time :
         * set_return : 买家(罗鹏)于创建了退款申请
         * store_id : 32788
         * list : [{"icon":"upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg","spec_info":"颜色:红色 尺码:XL ","price":2,"count":1,"goods_name":"正常产品1"}]
         * refund_way : 支付宝
         * refuse_time :
         * user_name : 罗鹏
         * listhistory : [{"time":"2018-01-30 22:04:49","title":"卖家还在审核中","contents":"申请中"}]
         * company_name :
         * refund : 0.01
         * service : 0
         * refund_status : 0
         * logistics_time :
         * gr_id : 82
         * logistics : 1
         * store_name : 时尚前沿专营店
         * return_shipCode :
         */

        private String info;
        private String refund_id;
        private String reason;
        private String apply_time;
        private String set_waybill;
        private String agree_time;
        private String set_return;
        private int store_id;
        private String refund_way;
        private String refuse_time;
        private String user_name;
        private String company_name;
        private double refund;
        private int service;
        private int refund_status;
        private String logistics_time;
        private String gr_id;
        private String gc_id;
        private int logistics;
        private String store_name;
        private String return_shipCode;
        private List<ListBean> list;
        private List<ListhistoryBean> listhistory;
        private String again;

        public String getAgain() {
            return again;
        }

        public void setAgain(String again) {
            this.again = again;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getGc_id() {
            return gc_id;
        }

        public void setGc_id(String gc_id) {
            this.gc_id = gc_id;
        }

        public String getRefund_id() {
            return refund_id;
        }

        public void setRefund_id(String refund_id) {
            this.refund_id = refund_id;
        }

        public String getApply_time() {
            return apply_time;
        }

        public void setApply_time(String apply_time) {
            this.apply_time = apply_time;
        }

        public String getSet_waybill() {
            return set_waybill;
        }

        public void setSet_waybill(String set_waybill) {
            this.set_waybill = set_waybill;
        }

        public String getAgree_time() {
            return agree_time;
        }

        public void setAgree_time(String agree_time) {
            this.agree_time = agree_time;
        }

        public String getSet_return() {
            return set_return;
        }

        public void setSet_return(String set_return) {
            this.set_return = set_return;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getRefund_way() {
            return refund_way;
        }

        public void setRefund_way(String refund_way) {
            this.refund_way = refund_way;
        }

        public String getRefuse_time() {
            return refuse_time;
        }

        public void setRefuse_time(String refuse_time) {
            this.refuse_time = refuse_time;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
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

        public int getRefund_status() {
            return refund_status;
        }

        public void setRefund_status(int refund_status) {
            this.refund_status = refund_status;
        }

        public String getLogistics_time() {
            return logistics_time;
        }

        public void setLogistics_time(String logistics_time) {
            this.logistics_time = logistics_time;
        }

        public String getGr_id() {
            return gr_id;
        }

        public void setGr_id(String gr_id) {
            this.gr_id = gr_id;
        }

        public int getLogistics() {
            return logistics;
        }

        public void setLogistics(int logistics) {
            this.logistics = logistics;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getReturn_shipCode() {
            return return_shipCode;
        }

        public void setReturn_shipCode(String return_shipCode) {
            this.return_shipCode = return_shipCode;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<ListhistoryBean> getListhistory() {
            return listhistory;
        }

        public void setListhistory(List<ListhistoryBean> listhistory) {
            this.listhistory = listhistory;
        }

        public static class ListBean {
            /**
             * icon : upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg
             * spec_info : 颜色:红色 尺码:XL
             * price : 2.0
             * count : 1
             * goods_name : 正常产品1
             */

            private String icon;
            private String spec_info;
            private double price;
            private int count;
            private String goods_name;

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

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }

        public static class ListhistoryBean {
            /**
             * time : 2018-01-30 22:04:49
             * title : 卖家还在审核中
             * contents : 申请中
             */

            private String time;
            private String title;
            private String contents;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }
        }
    }
}
