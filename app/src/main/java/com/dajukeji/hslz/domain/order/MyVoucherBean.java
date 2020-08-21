package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/21 20:12
 * 作用:
 */
public class MyVoucherBean {

    /**
     * status : 0
     * message : 我的产品券
     * content : {"tfGoodsList":[{"store_id":32803,"spec_info":"","goods_name":"鼻贴贴","securities_status_des":"待兑换","goods_price":0.01,"count":1,"goods_id":98842,"valid_start_date":"2019-01-21","securities_id":2,"valid_end_date":"2019-01-31","transfer_price":0.01,"store_name":"热力四射青儿","goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32803/2018/03/10/885ac08a-66ad-4b80-b13b-8e465e20dcfe.jpg","securities_status":1},{"store_id":32803,"spec_info":"","goods_name":"cc霜","securities_status_des":"待兑换","goods_price":0.01,"count":1,"goods_id":98833,"valid_start_date":"2019-01-21","securities_id":1,"valid_end_date":"2019-01-31","transfer_price":0.01,"store_name":"热力四射青儿","goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32803/2018/03/10/8f7654b0-1d76-4c3c-968f-8ef6e1ffb3a9.jpg","securities_status":1}],"pages":1,"pageSize":12,"currentPage":1}
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
         * tfGoodsList : [{"store_id":32803,"spec_info":"","goods_name":"鼻贴贴","securities_status_des":"待兑换","goods_price":0.01,"count":1,"goods_id":98842,"valid_start_date":"2019-01-21","securities_id":2,"valid_end_date":"2019-01-31","transfer_price":0.01,"store_name":"热力四射青儿","goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32803/2018/03/10/885ac08a-66ad-4b80-b13b-8e465e20dcfe.jpg","securities_status":1},{"store_id":32803,"spec_info":"","goods_name":"cc霜","securities_status_des":"待兑换","goods_price":0.01,"count":1,"goods_id":98833,"valid_start_date":"2019-01-21","securities_id":1,"valid_end_date":"2019-01-31","transfer_price":0.01,"store_name":"热力四射青儿","goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32803/2018/03/10/8f7654b0-1d76-4c3c-968f-8ef6e1ffb3a9.jpg","securities_status":1}]
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<TfGoodsListBean> tfGoodsList;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<TfGoodsListBean> getTfGoodsList() {
            return tfGoodsList;
        }

        public void setTfGoodsList(List<TfGoodsListBean> tfGoodsList) {
            this.tfGoodsList = tfGoodsList;
        }

        public static class TfGoodsListBean {
            /**
             * store_id : 32803
             * spec_info :
             * goods_name : 鼻贴贴
             * securities_status_des : 待兑换
             * goods_price : 0.01
             * count : 1
             * goods_id : 98842
             * valid_start_date : 2019-01-21
             * securities_id : 2
             * valid_end_date : 2019-01-31
             * transfer_price : 0.01
             * store_name : 热力四射青儿
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32803/2018/03/10/885ac08a-66ad-4b80-b13b-8e465e20dcfe.jpg
             * securities_status : 1
             */

            private int store_id;
            private String spec_info;
            private String goods_name;
            private String securities_status_des;
            private double goods_price;
            private int count;
            private int goods_id;
            private String valid_start_date;
            private int securities_id;
            private String valid_end_date;
            private double transfer_price;
            private String store_name;
            private String goods_main_photo;
            private int securities_status;//0=已过期 1=待兑换 2=已兑换 3=转卖中 4不应该出现

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
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

            public String getSecurities_status_des() {
                return securities_status_des;
            }

            public void setSecurities_status_des(String securities_status_des) {
                this.securities_status_des = securities_status_des;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getValid_start_date() {
                return valid_start_date;
            }

            public void setValid_start_date(String valid_start_date) {
                this.valid_start_date = valid_start_date;
            }

            public int getSecurities_id() {
                return securities_id;
            }

            public void setSecurities_id(int securities_id) {
                this.securities_id = securities_id;
            }

            public String getValid_end_date() {
                return valid_end_date;
            }

            public void setValid_end_date(String valid_end_date) {
                this.valid_end_date = valid_end_date;
            }

            public double getTransfer_price() {
                return transfer_price;
            }

            public void setTransfer_price(double transfer_price) {
                this.transfer_price = transfer_price;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public int getSecurities_status() {
                return securities_status;
            }

            public void setSecurities_status(int securities_status) {
                this.securities_status = securities_status;
            }
        }
    }
}
