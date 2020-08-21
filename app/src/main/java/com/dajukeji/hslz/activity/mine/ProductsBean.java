package com.dajukeji.hslz.activity.mine;

import java.util.List;

public class ProductsBean {

    /**
     * status : 0
     * message : 评选区下拉更多数据
     * content : {"pages":3,"goodsList":[{"goods_name":"评选产品99","goods_price":1,"id":98947,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品98","goods_price":1,"id":98948,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品87","goods_price":1,"id":98952,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品89","goods_price":1,"id":98953,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品65","goods_price":1,"id":98954,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品45","goods_price":1,"id":98955,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品654","goods_price":1,"id":98958,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品1","goods_price":1,"id":98959,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品2","goods_price":5888,"id":98960,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品3","goods_price":1,"id":98961,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品4","goods_price":1,"id":98962,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品5","goods_price":1,"id":98963,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"}],"pageSize":12,"currentPage":1}
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
         * pages : 3
         * goodsList : [{"goods_name":"评选产品99","goods_price":1,"id":98947,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品98","goods_price":1,"id":98948,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品87","goods_price":1,"id":98952,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品89","goods_price":1,"id":98953,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品65","goods_price":1,"id":98954,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品45","goods_price":1,"id":98955,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品654","goods_price":1,"id":98958,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品1","goods_price":1,"id":98959,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品2","goods_price":5888,"id":98960,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品3","goods_price":1,"id":98961,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品4","goods_price":1,"id":98962,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品5","goods_price":1,"id":98963,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"}]
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<GoodsListBean> goodsList;

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

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * goods_name : 评选产品99
             * goods_price : 1
             * id : 98947
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg
             */

            private String goods_name;
            private double goods_price;
            private int id;
            private String goods_main_photo;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }
        }
    }
}
