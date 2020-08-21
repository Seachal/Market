package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/6 0006.
 */

public class StoreIndexBean {

    /**
     * message : 店铺主页
     * content : {"store_info":{"store_id":1,"store_info":"","store_logo":"upload/store_logo/good.jpg","store_name":"非凡服饰专卖店","store_type":"专卖店","store_collection":0,"store_level":"普通商家"},"store_goods_class_list":[{"name":"户外装备","id":15},{"name":"笔记本","id":10},{"name":"体育健身","id":16},{"name":"童装","id":5},{"name":"手机","id":11},{"name":"饰品","id":32768},{"name":"个人护理","id":12},{"name":"箱包","id":32769},{"name":"男女服装","id":1},{"name":"男鞋","id":7},{"name":"女鞋","id":8},{"name":"户外服饰","id":14},{"name":"鞋包配饰","id":6},{"name":"男装","id":2},{"name":"女装","id":3},{"name":"内衣","id":4},{"name":"数码家电","id":9},{"name":"户外运动","id":13}]}
     * status : 0
     */
    private String message;
    private ContentEntity content;
    private String status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public ContentEntity getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public class ContentEntity {
        /**
         * store_info : {"store_id":1,"store_info":"","store_logo":"upload/store_logo/good.jpg","store_name":"非凡服饰专卖店","store_type":"专卖店","store_collection":0,"store_level":"普通商家"}
         * store_goods_class_list : [{"name":"户外装备","id":15},{"name":"笔记本","id":10},{"name":"体育健身","id":16},{"name":"童装","id":5},{"name":"手机","id":11},{"name":"饰品","id":32768},{"name":"个人护理","id":12},{"name":"箱包","id":32769},{"name":"男女服装","id":1},{"name":"男鞋","id":7},{"name":"女鞋","id":8},{"name":"户外服饰","id":14},{"name":"鞋包配饰","id":6},{"name":"男装","id":2},{"name":"女装","id":3},{"name":"内衣","id":4},{"name":"数码家电","id":9},{"name":"户外运动","id":13}]
         */
        private Store_infoEntity store_info;
        private List<Store_goods_class_listEntity> store_goods_class_list;

        public void setStore_info(Store_infoEntity store_info) {
            this.store_info = store_info;
        }

        public void setStore_goods_class_list(List<Store_goods_class_listEntity> store_goods_class_list) {
            this.store_goods_class_list = store_goods_class_list;
        }

        public Store_infoEntity getStore_info() {
            return store_info;
        }

        public List<Store_goods_class_listEntity> getStore_goods_class_list() {
            return store_goods_class_list;
        }

        public class Store_infoEntity {
            /**
             * store_id : 1
             * store_info :
             * store_logo : upload/store_logo/good.jpg
             * store_name : 非凡服饰专卖店
             * store_type : 专卖店
             * store_collection : 0
             * store_level : 普通商家
             */
            private int store_id;
            private String store_info;
            private String store_logo;
            private String store_name;
            private String store_speed;
            private String store_type;
            private int store_collection;
            private String store_level;
            private String chat_id;

            public String getChat_id() {
                return chat_id;
            }

            public void setChat_id(String chat_id) {
                this.chat_id = chat_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public void setStore_info(String store_info) {
                this.store_info = store_info;
            }

            public void setStore_logo(String store_logo) {
                this.store_logo = store_logo;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public void setStore_type(String store_type) {
                this.store_type = store_type;
            }

            public void setStore_collection(int store_collection) {
                this.store_collection = store_collection;
            }

            public String getStore_speed() {
                return store_speed;
            }

            public void setStore_speed(String store_speed) {
                this.store_speed = store_speed;
            }

            public void setStore_level(String store_level) {
                this.store_level = store_level;
            }

            public int getStore_id() {
                return store_id;
            }

            public String getStore_info() {
                return store_info;
            }

            public String getStore_logo() {
                return store_logo;
            }

            public String getStore_name() {
                return store_name;
            }

            public String getStore_type() {
                return store_type;
            }

            public int getStore_collection() {
                return store_collection;
            }

            public String getStore_level() {
                return store_level;
            }
        }

        public class Store_goods_class_listEntity {
            /**
             * name : 户外装备
             * id : 15
             */
            private String name;
            private int id;

            public void setName(String name) {
                this.name = name;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }
    }
}
