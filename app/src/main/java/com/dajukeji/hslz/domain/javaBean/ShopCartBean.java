package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class ShopCartBean {
    /**
     * message : 购物车
     * content : {"all_store_total":3074,"store_cart_ist":[{"store_id":1,"one_store_total":874,"goods_cart_list":[{"spec_info":"","goods_name":"威古氏 欧美女士偏光太阳镜时尚多色大镜框墨镜","total":105,"goods_image":"xx.jgp","goods_price":35,"gc_id":3543,"count":3,"status":0},{"spec_info":"颜色:蓝色 尺码:XXL ","goods_name":"I\u2019m David16专柜新品爱大卫韩版春季男装长袖休闲外套DQJP11C1","total":769,"goods_image":"xx.jgp","goods_price":769,"gc_id":3544,"count":1,"status":0}],"sc_id":433,"store_name":"平台自营店"},{"store_id":32769,"one_store_total":2200,"goods_cart_list":[{"spec_info":"","goods_name":"Samsung三星 Galaxy S6 Edge SM-G9250手机7","total":2200,"goods_image":"xx.jgp","goods_price":2200,"gc_id":3545,"count":1,"status":0}],"sc_id":432,"store_name":"宏达通信"}]}
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
         * all_store_total : 3074
         * store_cart_ist : [{"store_id":1,"one_store_total":874,"goods_cart_list":[{"spec_info":"","goods_name":"威古氏 欧美女士偏光太阳镜时尚多色大镜框墨镜","total":105,"goods_image":"xx.jgp","goods_price":35,"gc_id":3543,"count":3,"status":0},{"spec_info":"颜色:蓝色 尺码:XXL ","goods_name":"I\u2019m David16专柜新品爱大卫韩版春季男装长袖休闲外套DQJP11C1","total":769,"goods_image":"xx.jgp","goods_price":769,"gc_id":3544,"count":1,"status":0}],"sc_id":433,"store_name":"平台自营店"},{"store_id":32769,"one_store_total":2200,"goods_cart_list":[{"spec_info":"","goods_name":"Samsung三星 Galaxy S6 Edge SM-G9250手机7","total":2200,"goods_image":"xx.jgp","goods_price":2200,"gc_id":3545,"count":1,"status":0}],"sc_id":432,"store_name":"宏达通信"}]
         */
        private int all_store_total;
        private List<Store_cart_istEntity> store_cart_ist;

        public void setAll_store_total(int all_store_total) {
            this.all_store_total = all_store_total;
        }

        public void setStore_cart_ist(List<Store_cart_istEntity> store_cart_ist) {
            this.store_cart_ist = store_cart_ist;
        }

        public int getAll_store_total() {
            return all_store_total;
        }

        public List<Store_cart_istEntity> getStore_cart_ist() {
            return store_cart_ist;
        }

        public class Store_cart_istEntity {
            /**
             * store_id : 1
             * one_store_total : 874
             * goods_cart_list : [{"spec_info":"","goods_name":"威古氏 欧美女士偏光太阳镜时尚多色大镜框墨镜","total":105,"goods_image":"xx.jgp","goods_price":35,"gc_id":3543,"count":3,"status":0},{"spec_info":"颜色:蓝色 尺码:XXL ","goods_name":"I\u2019m David16专柜新品爱大卫韩版春季男装长袖休闲外套DQJP11C1","total":769,"goods_image":"xx.jgp","goods_price":769,"gc_id":3544,"count":1,"status":0}]
             * sc_id : 433
             * store_name : 平台自营店
             */
            private int store_id;
            private int one_store_total;
            private List<Goods_cart_listEntity> goods_cart_list;
            private int sc_id;
            private String store_name;

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public void setOne_store_total(int one_store_total) {
                this.one_store_total = one_store_total;
            }

            public void setGoods_cart_list(List<Goods_cart_listEntity> goods_cart_list) {
                this.goods_cart_list = goods_cart_list;
            }

            public void setSc_id(int sc_id) {
                this.sc_id = sc_id;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getStore_id() {
                return store_id;
            }

            public int getOne_store_total() {
                return one_store_total;
            }

            public List<Goods_cart_listEntity> getGoods_cart_list() {
                return goods_cart_list;
            }

            public int getSc_id() {
                return sc_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public class Goods_cart_listEntity {
                /**
                 * spec_info :
                 * goods_name : 威古氏 欧美女士偏光太阳镜时尚多色大镜框墨镜
                 * total : 105
                 * goods_image : xx.jgp
                 * goods_price : 35
                 * gc_id : 3543
                 * count : 3
                 * status : 0
                 */
                private String spec_info;
                private String goods_name;
                private int total;
                private String goods_image;
                private int goods_price;
                private int gc_id;
                private int count;
                private int status;

                public void setSpec_info(String spec_info) {
                    this.spec_info = spec_info;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }

                public void setGoods_price(int goods_price) {
                    this.goods_price = goods_price;
                }

                public void setGc_id(int gc_id) {
                    this.gc_id = gc_id;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getSpec_info() {
                    return spec_info;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public int getTotal() {
                    return total;
                }

                public String getGoods_image() {
                    return goods_image;
                }

                public int getGoods_price() {
                    return goods_price;
                }

                public int getGc_id() {
                    return gc_id;
                }

                public int getCount() {
                    return count;
                }

                public int getStatus() {
                    return status;
                }
            }
        }
    }
}
