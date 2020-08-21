package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/30 13:46
 * 作用:
 */
public class GoodsClassDetailsBean {

    /**
     * status : 0
     * message : 商品列表
     * content : {"pages":2,"goodsList":[{"goods_name":"ochirly欧时力2018新女冬装系带贴布绣缎面中长款衬衫","goods_price":186,"ref_price":285,"id":99206,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/e4959328-2665-414c-9f12-51a8ae4ad6f6.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ONLY2018冬季新款简约紧身弹力高腰双扣铅笔裤牛仔裤女","goods_price":275,"ref_price":419.3,"id":99212,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/d413ab75-b794-424b-8a85-05d2d349ad06.png_middle.png","goods_salenum":0},{"goods_name":"ONLY2018秋冬新款印花贴布低腰紧身铅笔九分牛仔裤女","goods_price":146,"ref_price":224.5,"id":99213,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/4281d3d5-96a2-4ceb-b17b-63d203e657ca.png_middle.png","goods_salenum":0},{"goods_name":"ONLY2018夏季新款镂空荷叶边假两件长袖连衣裙女1184","goods_price":260,"ref_price":399,"id":99179,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/8e89ad5a-0382-45ab-a10e-38470b61feaa.jpg_middle.jpg","goods_salenum":1},{"goods_name":"ONLY2018夏季新款镂空荷叶边假两件长袖连衣裙女","goods_price":260,"ref_price":399.5,"id":99210,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/6f1c4883-9b4f-45c2-ae7d-cbe803fee62f.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ONLY2018秋冬新款套头V领包臀松紧腰针织连衣裙女","goods_price":179,"ref_price":274.5,"id":99211,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/38587ebd-eb12-4bf3-b853-c0370130aa60.png_middle.png","goods_salenum":0},{"goods_name":"Lily2018秋新款女装女人味露肩绑带宽松套头针织衫","goods_price":156,"ref_price":239,"id":99238,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32827/2019/01/30/a31f570f-61fb-408e-88c4-6bf1bc535b45.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ochirly欧时力新女装西装领拼接长款羊毛呢外套1JY43","goods_price":700,"ref_price":1076,"id":99178,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/3520e449-94e9-4795-ad17-605a4a0254c2.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ochirly欧时力新女装羊皮金属拉链机车皮衣外套1JY33","goods_price":1168,"ref_price":1795,"id":99180,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/34fda5c3-fe3c-4a0e-9ca2-0ee730d40621.jpg_middle.jpg","goods_salenum":75},{"goods_name":"ochirly欧时力2018新女冬装毛领贴布绣长款宽松羽绒服","goods_price":572,"ref_price":879,"id":99202,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/15a0dfe0-ed51-4571-a715-6fc160db612a.png_middle.png","goods_salenum":0},{"goods_name":"ochirly欧时力2018新女冬装纯色钉珠双排扣毛呢外套1","goods_price":365,"ref_price":559,"id":99203,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/f4bd625b-6c6c-4989-a06e-8b1d0bc51c8a.png_middle.png","goods_salenum":0},{"goods_name":"ochirly欧时力新女装西装领拼接长款羊毛呢外套","goods_price":700,"ref_price":1076,"id":99204,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/a757cd8a-8b68-4408-857e-dd73bf12fe6c.jpg_middle.jpg","goods_salenum":0}],"pageSize":12,"currentPage":1}
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
         * pages : 2
         * goodsList : [{"goods_name":"ochirly欧时力2018新女冬装系带贴布绣缎面中长款衬衫","goods_price":186,"ref_price":285,"id":99206,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/e4959328-2665-414c-9f12-51a8ae4ad6f6.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ONLY2018冬季新款简约紧身弹力高腰双扣铅笔裤牛仔裤女","goods_price":275,"ref_price":419.3,"id":99212,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/d413ab75-b794-424b-8a85-05d2d349ad06.png_middle.png","goods_salenum":0},{"goods_name":"ONLY2018秋冬新款印花贴布低腰紧身铅笔九分牛仔裤女","goods_price":146,"ref_price":224.5,"id":99213,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/4281d3d5-96a2-4ceb-b17b-63d203e657ca.png_middle.png","goods_salenum":0},{"goods_name":"ONLY2018夏季新款镂空荷叶边假两件长袖连衣裙女1184","goods_price":260,"ref_price":399,"id":99179,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/8e89ad5a-0382-45ab-a10e-38470b61feaa.jpg_middle.jpg","goods_salenum":1},{"goods_name":"ONLY2018夏季新款镂空荷叶边假两件长袖连衣裙女","goods_price":260,"ref_price":399.5,"id":99210,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/6f1c4883-9b4f-45c2-ae7d-cbe803fee62f.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ONLY2018秋冬新款套头V领包臀松紧腰针织连衣裙女","goods_price":179,"ref_price":274.5,"id":99211,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32819/2019/01/29/38587ebd-eb12-4bf3-b853-c0370130aa60.png_middle.png","goods_salenum":0},{"goods_name":"Lily2018秋新款女装女人味露肩绑带宽松套头针织衫","goods_price":156,"ref_price":239,"id":99238,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32827/2019/01/30/a31f570f-61fb-408e-88c4-6bf1bc535b45.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ochirly欧时力新女装西装领拼接长款羊毛呢外套1JY43","goods_price":700,"ref_price":1076,"id":99178,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/3520e449-94e9-4795-ad17-605a4a0254c2.jpg_middle.jpg","goods_salenum":0},{"goods_name":"ochirly欧时力新女装羊皮金属拉链机车皮衣外套1JY33","goods_price":1168,"ref_price":1795,"id":99180,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/01/28/34fda5c3-fe3c-4a0e-9ca2-0ee730d40621.jpg_middle.jpg","goods_salenum":75},{"goods_name":"ochirly欧时力2018新女冬装毛领贴布绣长款宽松羽绒服","goods_price":572,"ref_price":879,"id":99202,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/15a0dfe0-ed51-4571-a715-6fc160db612a.png_middle.png","goods_salenum":0},{"goods_name":"ochirly欧时力2018新女冬装纯色钉珠双排扣毛呢外套1","goods_price":365,"ref_price":559,"id":99203,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/f4bd625b-6c6c-4989-a06e-8b1d0bc51c8a.png_middle.png","goods_salenum":0},{"goods_name":"ochirly欧时力新女装西装领拼接长款羊毛呢外套","goods_price":700,"ref_price":1076,"id":99204,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/a757cd8a-8b68-4408-857e-dd73bf12fe6c.jpg_middle.jpg","goods_salenum":0}]
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
             * goods_name : ochirly欧时力2018新女冬装系带贴布绣缎面中长款衬衫
             * goods_price : 186
             * ref_price : 285
             * id : 99206
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32818/2019/01/29/e4959328-2665-414c-9f12-51a8ae4ad6f6.jpg_middle.jpg
             * goods_salenum : 0
             */

            private String goods_name;
            private double goods_price;
            private double ref_price;
            private int id;
            private String goods_main_photo;
            private int goods_salenum;

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

            public double getRef_price() {
                return ref_price;
            }

            public void setRef_price(double ref_price) {
                this.ref_price = ref_price;
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

            public int getGoods_salenum() {
                return goods_salenum;
            }

            public void setGoods_salenum(int goods_salenum) {
                this.goods_salenum = goods_salenum;
            }
        }
    }
}
