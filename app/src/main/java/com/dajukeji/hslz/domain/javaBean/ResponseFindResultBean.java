package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/2/26 10:43
 * 作用:
 */
public class ResponseFindResultBean {

    /**
     * status : 0
     * message : 评选商品列表
     * content : {"pages":4,"goodsList":[{"goods_inventory":20,"goods_name":"大宝花草茶润肌面膜保湿补水 天丝免洗面膜 提亮肤色舒缓滋养","goods_price":39,"id":99690,"goods_current_price":39,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/9452206d-f3cd-4ca9-8817-b8fcecf64b83.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝男士护肤品套装洁面皂凝露保湿润肤控油清洁化妆品套装正品","goods_price":45,"id":99689,"goods_current_price":45,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/7a1d6073-eabf-49de-9cd7-fb7537b7e78c.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝护肤品套装女男士补水保湿控油水乳液化妆品旗舰店官方正品","goods_price":85,"id":99688,"goods_current_price":85,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/0458d0f6-b274-4061-9e81-b69722650a92.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝护肤品套装女男补水保湿美白淡斑水乳化妆品官方直售正品少女","goods_price":85,"id":99687,"goods_current_price":85,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/37599b1a-b5c8-401c-8fe9-c4b8e8808d53.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝SOD蛋白蜜女秋冬胶原蛋白面霜男士护肤滋润补水保湿乳10","goods_price":23,"id":99686,"goods_current_price":23,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/b482cfc4-4b90-4381-b23e-197edc76283b.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝SOD滋养护手霜2支滋润补水保湿套装女男士手部护理干裂粗","goods_price":9,"id":99685,"goods_current_price":9,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/33f683f1-25b4-42a5-ae5c-fbae43c5a85a.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"YSL圣罗兰蒙德里安五色眼影 大地色烟熏色紫色5g带刷具 正","goods_price":390,"id":99630,"goods_current_price":390,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32816/2019/02/25/82109e9e-68ea-4785-b00b-b5363aafccfc.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"立白洗衣液全效馨香大瓶装去渍洁净留香机洗正品家庭装2kg 深","goods_price":22,"id":99600,"goods_current_price":22,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/25/1a5182a5-1587-4c14-9592-7d6c3366d700.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"碧浪洁护如新清雅茉莉香型洗衣液3KG大瓶装 持久馨香 洗护合","goods_price":34,"id":99576,"goods_current_price":34,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/c024f230-ff4b-4eda-b9fb-66feabd1c1a2.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"蓝月亮洗衣液 薰衣草香3kg 大瓶装洁净亮白增艳机洗手洗衣液","goods_price":31,"id":99570,"goods_current_price":31,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/a5f8aec2-4767-432e-a572-85efe1d9677b.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"新品相宜本草红景天淡纹幼白大魔盒美白补水保湿化妆品套装礼盒","goods_price":390,"id":99551,"goods_current_price":390,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/22/74d5f78b-6669-4c0d-82d7-2981ed956b45.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"Lux力士大白瓶杨幂水润丝滑男女士正品洗发水护发素套装500","goods_price":40,"id":99527,"goods_current_price":40,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/9d5d128f-5d00-4fc8-974c-87383159030f.jpg_middle.jpg","goods_salenum":0}],"pageSize":12,"currentPage":1}
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
         * pages : 4
         * goodsList : [{"goods_inventory":20,"goods_name":"大宝花草茶润肌面膜保湿补水 天丝免洗面膜 提亮肤色舒缓滋养","goods_price":39,"id":99690,"goods_current_price":39,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/9452206d-f3cd-4ca9-8817-b8fcecf64b83.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝男士护肤品套装洁面皂凝露保湿润肤控油清洁化妆品套装正品","goods_price":45,"id":99689,"goods_current_price":45,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/7a1d6073-eabf-49de-9cd7-fb7537b7e78c.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝护肤品套装女男士补水保湿控油水乳液化妆品旗舰店官方正品","goods_price":85,"id":99688,"goods_current_price":85,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/0458d0f6-b274-4061-9e81-b69722650a92.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝护肤品套装女男补水保湿美白淡斑水乳化妆品官方直售正品少女","goods_price":85,"id":99687,"goods_current_price":85,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/37599b1a-b5c8-401c-8fe9-c4b8e8808d53.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝SOD蛋白蜜女秋冬胶原蛋白面霜男士护肤滋润补水保湿乳10","goods_price":23,"id":99686,"goods_current_price":23,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/b482cfc4-4b90-4381-b23e-197edc76283b.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"大宝SOD滋养护手霜2支滋润补水保湿套装女男士手部护理干裂粗","goods_price":9,"id":99685,"goods_current_price":9,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/33f683f1-25b4-42a5-ae5c-fbae43c5a85a.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"YSL圣罗兰蒙德里安五色眼影 大地色烟熏色紫色5g带刷具 正","goods_price":390,"id":99630,"goods_current_price":390,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32816/2019/02/25/82109e9e-68ea-4785-b00b-b5363aafccfc.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"立白洗衣液全效馨香大瓶装去渍洁净留香机洗正品家庭装2kg 深","goods_price":22,"id":99600,"goods_current_price":22,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/25/1a5182a5-1587-4c14-9592-7d6c3366d700.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"碧浪洁护如新清雅茉莉香型洗衣液3KG大瓶装 持久馨香 洗护合","goods_price":34,"id":99576,"goods_current_price":34,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/c024f230-ff4b-4eda-b9fb-66feabd1c1a2.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"蓝月亮洗衣液 薰衣草香3kg 大瓶装洁净亮白增艳机洗手洗衣液","goods_price":31,"id":99570,"goods_current_price":31,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/a5f8aec2-4767-432e-a572-85efe1d9677b.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"新品相宜本草红景天淡纹幼白大魔盒美白补水保湿化妆品套装礼盒","goods_price":390,"id":99551,"goods_current_price":390,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/22/74d5f78b-6669-4c0d-82d7-2981ed956b45.jpg_middle.jpg","goods_salenum":0},{"goods_inventory":20,"goods_name":"Lux力士大白瓶杨幂水润丝滑男女士正品洗发水护发素套装500","goods_price":40,"id":99527,"goods_current_price":40,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32805/2019/02/22/9d5d128f-5d00-4fc8-974c-87383159030f.jpg_middle.jpg","goods_salenum":0}]
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
             * goods_inventory : 20
             * goods_name : 大宝花草茶润肌面膜保湿补水 天丝免洗面膜 提亮肤色舒缓滋养
             * goods_price : 39
             * id : 99690
             * goods_current_price : 39
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32831/2019/02/25/9452206d-f3cd-4ca9-8817-b8fcecf64b83.jpg_middle.jpg
             * goods_salenum : 0
             */

            private int goods_inventory;
            private String goods_name;
            private double goods_price;
            private int id;
            private double goods_current_price;
            private String goods_main_photo;
            private int goods_salenum;

            public int getGoods_inventory() {
                return goods_inventory;
            }

            public void setGoods_inventory(int goods_inventory) {
                this.goods_inventory = goods_inventory;
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

            public double getGoods_current_price() {
                return goods_current_price;
            }

            public void setGoods_current_price(double goods_current_price) {
                this.goods_current_price = goods_current_price;
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
