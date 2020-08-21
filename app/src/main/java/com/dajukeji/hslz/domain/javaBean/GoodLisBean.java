package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

public class GoodLisBean {

    /**
     * status : 0
     * message : 产品列表
     * content : {"commonGoodsList":[{"goods_name":"评选产品99","goods_price":1,"id":98947,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品98","goods_price":1,"id":98948,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品87","goods_price":1,"id":98952,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品89","goods_price":1,"id":98953,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品65","goods_price":1,"id":98954,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品11","goods_price":1,"id":98979,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品23","goods_price":1,"id":98981,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品54","goods_price":1,"id":98982,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"巧克力","goods_price":123,"id":98521,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/19120564-c171-4d32-9cdf-b93978fbb396.jpg_middle.jpg"},{"goods_name":"马卡龙","goods_price":121,"id":98522,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/f3b37220-3890-406c-bbc0-069046e16c2f.jpg_middle.jpg"},{"goods_name":"肉松饼","goods_price":122,"id":98523,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/3eeffb34-701e-4d5f-a715-218d3c29e911.jpg_middle.jpg"},{"goods_name":"曲奇曲奇","goods_price":213,"id":98524,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/6fb4e310-c9f9-4066-ad1b-52c252e78e09.jpg_middle.jpg"}],"pages":15,"pageSize":12,"currentPage":1}
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
         * commonGoodsList : [{"goods_name":"评选产品99","goods_price":1,"id":98947,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品98","goods_price":1,"id":98948,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品87","goods_price":1,"id":98952,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品89","goods_price":1,"id":98953,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品65","goods_price":1,"id":98954,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品11","goods_price":1,"id":98979,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品23","goods_price":1,"id":98981,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"评选产品54","goods_price":1,"id":98982,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg"},{"goods_name":"巧克力","goods_price":123,"id":98521,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/19120564-c171-4d32-9cdf-b93978fbb396.jpg_middle.jpg"},{"goods_name":"马卡龙","goods_price":121,"id":98522,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/f3b37220-3890-406c-bbc0-069046e16c2f.jpg_middle.jpg"},{"goods_name":"肉松饼","goods_price":122,"id":98523,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/3eeffb34-701e-4d5f-a715-218d3c29e911.jpg_middle.jpg"},{"goods_name":"曲奇曲奇","goods_price":213,"id":98524,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/32774/2018/01/08/6fb4e310-c9f9-4066-ad1b-52c252e78e09.jpg_middle.jpg"}]
         * pages : 15
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<CommonGoodsListBean> commonGoodsList;

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

        public List<CommonGoodsListBean> getCommonGoodsList() {
            return commonGoodsList;
        }

        public void setCommonGoodsList(List<CommonGoodsListBean> commonGoodsList) {
            this.commonGoodsList = commonGoodsList;
        }

        public static class CommonGoodsListBean {
            /**
             * goods_name : 评选产品99
             * goods_price : 1.0
             * id : 98947
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/system/2ca3efbe-0d87-4010-86ae-465733d1638f.jpg
             */

            private String goods_name;
            private double goods_price;
            private int id;
            private String goods_main_photo;
            private double ref_price;

            public double getRef_price() {
                return ref_price;
            }

            public void setRef_price(double ref_price) {
                this.ref_price = ref_price;
            }

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
