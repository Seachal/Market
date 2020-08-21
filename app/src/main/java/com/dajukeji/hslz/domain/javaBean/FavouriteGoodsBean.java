package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by cdr on 2017/12/12.
 * 我的收藏
 */

public class FavouriteGoodsBean {

    /**
     * status : 0
     * message : 收藏列表
     * content : {"pages":1,"pageSize":12,"currentPage":1,"favoriteList":[{"icon":"upload/store/1/2016/03/09da9d58ec-a569-435b-b5ea-381568410d70.jpg","status":0,"store_price":150,"goods_id":98448,"goods_name":"漫步者e1100 音乐发烧友特惠折扣","size":4},{"icon":"upload/store/1/2016/03/094baebb60-3c3e-4134-86a3-0ac5230f2e7f.jpg","status":0,"store_price":4888,"goods_id":98407,"goods_name":"三星GALAXY Note I9220","size":2},{"icon":"upload/store/1/2016/03/09e749591e-0538-4622-a1f8-804b703c7cf0.jpg","status":0,"store_price":4999,"goods_id":98401,"goods_name":"苹果iPhone 6（16GB） 您的体验是我们最大的努力方向","size":1},{"icon":"upload/store/1/2016/03/09dedf7bbd-c4a7-4435-94d2-e01c42119b6a.jpg","status":0,"store_price":76,"goods_id":98382,"goods_name":"花季 925纯银耳钉女 银饰耳饰 超闪瑞士钻 饰品zhned","size":1}]}
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
         * favoriteList : [{"icon":"upload/store/1/2016/03/09da9d58ec-a569-435b-b5ea-381568410d70.jpg","status":0,"store_price":150,"goods_id":98448,"goods_name":"漫步者e1100 音乐发烧友特惠折扣","size":4},{"icon":"upload/store/1/2016/03/094baebb60-3c3e-4134-86a3-0ac5230f2e7f.jpg","status":0,"store_price":4888,"goods_id":98407,"goods_name":"三星GALAXY Note I9220","size":2},{"icon":"upload/store/1/2016/03/09e749591e-0538-4622-a1f8-804b703c7cf0.jpg","status":0,"store_price":4999,"goods_id":98401,"goods_name":"苹果iPhone 6（16GB） 您的体验是我们最大的努力方向","size":1},{"icon":"upload/store/1/2016/03/09dedf7bbd-c4a7-4435-94d2-e01c42119b6a.jpg","status":0,"store_price":76,"goods_id":98382,"goods_name":"花季 925纯银耳钉女 银饰耳饰 超闪瑞士钻 饰品zhned","size":1}]
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<FavoriteListBean> favoriteList;

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

        public List<FavoriteListBean> getFavoriteList() {
            return favoriteList;
        }

        public void setFavoriteList(List<FavoriteListBean> favoriteList) {
            this.favoriteList = favoriteList;
        }

        public static class FavoriteListBean {
            /**
             * icon : upload/store/1/2016/03/09da9d58ec-a569-435b-b5ea-381568410d70.jpg
             * status : 0
             * store_price : 150.0
             * goods_id : 98448
             * goods_name : 漫步者e1100 音乐发烧友特惠折扣
             * size : 4
             */

            private String icon;
            private int status;
            private double store_price;
            private long goods_id;
            private String goods_name;
            private int size;
            private String zone_type;

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public double getStore_price() {
                return store_price;
            }

            public void setStore_price(double store_price) {
                this.store_price = store_price;
            }

            public long getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(long goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }
    }
}
