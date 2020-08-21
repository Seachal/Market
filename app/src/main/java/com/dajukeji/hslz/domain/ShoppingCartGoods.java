package com.dajukeji.hslz.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/26.
 * 购物车信息
 */

public class ShoppingCartGoods {

    /**
     * status : 0
     * message : 购物车
     * content : {"store_cart_list":[{"one_store_total":178,"sc_id":226,"goods_cart_list":[{"total":178,"gc_id":229795,"spec_info":"颜色:紫色 尺码:XL ","goods_price":178,"count":1,"status":0,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子"}],"store_id":1,"store_name":"平台自营店"}],"all_store_total":178}
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
         * store_cart_list : [{"one_store_total":178,"sc_id":226,"goods_cart_list":[{"total":178,"gc_id":229795,"spec_info":"颜色:紫色 尺码:XL ","goods_price":178,"count":1,"status":0,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子"}],"store_id":1,"store_name":"平台自营店"}]
         * all_store_total : 178
         */

        private float all_store_total;
        private List<StoreCartListBean> store_cart_list;

        public float getAll_store_total() {
            return all_store_total;
        }

        public void setAll_store_total(int all_store_total) {
            this.all_store_total = all_store_total;
        }

        public List<StoreCartListBean> getStore_cart_list() {
            return store_cart_list;
        }

        public void setStore_cart_list(List<StoreCartListBean> store_cart_list) {
            this.store_cart_list = store_cart_list;
        }

        public static class StoreCartListBean {
            /**
             * one_store_total : 178
             * sc_id : 226
             * goods_cart_list : [{"total":178,"gc_id":229795,"spec_info":"颜色:紫色 尺码:XL ","goods_price":178,"count":1,"status":0,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子"}]
             * store_id : 1
             * store_name : 平台自营店
             */

            private float one_store_total;
            private long sc_id;
            private long store_id;
            private String store_name;
            private List<GoodsCartListBean> goods_cart_list;

            public float getOne_store_total() {
                return one_store_total;
            }

            public void setOne_store_total(int one_store_total) {
                this.one_store_total = one_store_total;
            }

            public long getSc_id() {
                return sc_id;
            }

            public void setSc_id(long sc_id) {
                this.sc_id = sc_id;
            }

            public long getStore_id() {
                return store_id;
            }

            public void setStore_id(long store_id) {
                this.store_id = store_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public List<GoodsCartListBean> getGoods_cart_list() {
                return goods_cart_list;
            }

            public void setGoods_cart_list(List<GoodsCartListBean> goods_cart_list) {
                this.goods_cart_list = goods_cart_list;
            }

            public static class GoodsCartListBean implements Serializable {
                /**
                 * total : 178
                 * gc_id : 229795
                 * spec_info : 颜色:紫色 尺码:XL
                 * goods_price : 178
                 * count : 1
                 * status : 0
                 * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
                 * goods_image
                 */

                private float total;
                private long gc_id;
                private long goods_id; // 当前产品ID
                private String spec_info;
                private float goods_price;
                private int count;
                private int status;
                private String goods_name;
                private String goods_image; // 产品图片
                private boolean isCheck; // 是否为选择状态


                public long getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(long goods_id) {
                    this.goods_id = goods_id;
                }

                public boolean isCheck() {
                    return isCheck;
                }

                public void setCheck(boolean check) {
                    isCheck = check;
                }

                public String getGoods_image() {
                    return goods_image;
                }

                public void setGoods_image(String goods_image) {
                    this.goods_image = goods_image;
                }

                public float getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public long getGc_id() {
                    return gc_id;
                }

                public void setGc_id(long gc_id) {
                    this.gc_id = gc_id;
                }

                public String getSpec_info() {
                    return spec_info;
                }

                public void setSpec_info(String spec_info) {
                    this.spec_info = spec_info;
                }

                public float getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(float goods_price) {
                    this.goods_price = goods_price;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }
            }
        }
    }
}
