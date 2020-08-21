package com.dajukeji.hslz.domain.comparePrice;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class PlanGoodsListBean {


    /**
     * status : 0
     * message : 全网比价产品列表
     * content : {"pages":1,"pageSize":12,"currentPage":1,"one_plan_goods_list":[{"goods_price":45,"goods_id":98485,"goods_current_price":56,"goods_name":"全网比价","image":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png"}]}
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
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         * one_plan_goods_list : [{"goods_price":45,"goods_id":98485,"goods_current_price":56,"goods_name":"全网比价","image":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png"}]
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<OnePlanGoodsListBean> one_plan_goods_list;

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

        public List<OnePlanGoodsListBean> getOne_plan_goods_list() {
            return one_plan_goods_list;
        }

        public void setOne_plan_goods_list(List<OnePlanGoodsListBean> one_plan_goods_list) {
            this.one_plan_goods_list = one_plan_goods_list;
        }

        public static class OnePlanGoodsListBean {
            /**
             * goods_price : 45
             * goods_id : 98485
             * goods_current_price : 56
             * goods_name : 全网比价
             * image : upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png
             */

            private double goods_price;
            private int goods_id;
            private double goods_current_price;
            private String goods_name;
            private String image;
            private int goods_inventory; // 当前库存
            private int max_goods_inventory; // 最大库存

            public int getGoods_inventory() {
                return goods_inventory;
            }

            public void setGoods_inventory(int goods_inventory) {
                this.goods_inventory = goods_inventory;
            }

            public int getMax_goods_inventory() {
                return max_goods_inventory;
            }

            public void setMax_goods_inventory(int max_goods_inventory) {
                this.max_goods_inventory = max_goods_inventory;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
