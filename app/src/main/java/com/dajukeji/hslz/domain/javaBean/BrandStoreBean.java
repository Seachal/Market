package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandStoreBean implements Serializable {

    /**
     * status : 0
     * message : 品牌大促店铺列表
     * content : {"store_list":[{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32768,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"四川特产旗舰店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":1,"store_type":"专卖店","store_logo":"resources/style/common/images/good.jpg","store_name":"好好卖专卖店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32769,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"宏达通信"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32770,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"好时光"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32771,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"兔兔集中营"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32772,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"青青服装店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32773,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"滕飞传媒"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32774,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"旗舰男装店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32775,"store_type":"专卖店","store_logo":"upload/store_logo/7d1de9c2-4846-4a98-b10c-5e6f98fb4916.jpg","store_name":"羊羊精品数码店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32777,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"红旗专卖店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32778,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"好日子特产旗舰店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32779,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"时不再来专卖店"}],"pages":2,"pageSize":12,"currentPage":1}
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
         * store_list : [{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32768,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"四川特产旗舰店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":1,"store_type":"专卖店","store_logo":"resources/style/common/images/good.jpg","store_name":"好好卖专卖店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32769,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"宏达通信"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32770,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"好时光"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32771,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"兔兔集中营"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32772,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"青青服装店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32773,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"滕飞传媒"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32774,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"旗舰男装店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32775,"store_type":"专卖店","store_logo":"upload/store_logo/7d1de9c2-4846-4a98-b10c-5e6f98fb4916.jpg","store_name":"羊羊精品数码店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32777,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"红旗专卖店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32778,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"好日子特产旗舰店"},{"store_level":"普通商家","goods_list":[{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}],"store_id":32779,"store_type":"专卖店","store_logo":"resources/style/common/images/store.jpg","store_name":"时不再来专卖店"}]
         * pages : 2
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<StoreListBean> store_list;

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

        public List<StoreListBean> getStore_list() {
            return store_list;
        }

        public void setStore_list(List<StoreListBean> store_list) {
            this.store_list = store_list;
        }

        public static class StoreListBean {
            /**
             * store_level : 普通商家
             * goods_list : [{"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_id":65536,"goods_current_price":600,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","zone_type":""},{"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_id":32768,"goods_current_price":378,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","zone_type":""}]
             * store_id : 32768
             * store_type : 专卖店
             * store_logo : resources/style/common/images/store.jpg
             * store_name : 四川特产旗舰店
             */

            private String store_level;
            private int store_id;
            private String store_type;
            private String store_speed;
            private String store_logo;
            private String store_name;
            private List<GoodsListBean> goods_list;

            public String getStore_speed() {
                return store_speed;
            }

            public void setStore_speed(String store_speed) {
                this.store_speed = store_speed;
            }

            public String getStore_level() {
                return store_level;
            }

            public void setStore_level(String store_level) {
                this.store_level = store_level;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_type() {
                return store_type;
            }

            public void setStore_type(String store_type) {
                this.store_type = store_type;
            }

            public String getStore_logo() {
                return store_logo;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<GoodsListBean> getGoods_list() {
                return goods_list;
            }

            public void setGoods_list(List<GoodsListBean> goods_list) {
                this.goods_list = goods_list;
            }

            public static class GoodsListBean {
                /**
                 * goods_main_photo : upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg
                 * goods_id : 65536
                 * goods_current_price : 600.0
                 * goods_name : 冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套
                 * zone_type :
                 */

                private String goods_main_photo;
                private int goods_id;
                private double goods_current_price;
                private String goods_name;
                private String zone_type;
                private double brand_price;

                public double getBrand_price() {
                    return brand_price;
                }

                public void setBrand_price(double share_price) {
                    this.brand_price = share_price;
                }

                public String getGoods_main_photo() {
                    return goods_main_photo;
                }

                public void setGoods_main_photo(String goods_main_photo) {
                    this.goods_main_photo = goods_main_photo;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public double getGoods_current_price() {
                    return goods_current_price;
                }

                public void setGoods_current_price(double goods_current_price) {
                    this.goods_current_price = goods_current_price;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public String getZone_type() {
                    return zone_type;
                }

                public void setZone_type(String zone_type) {
                    this.zone_type = zone_type;
                }
            }
        }
    }
}
