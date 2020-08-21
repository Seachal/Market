package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by cdr on 2017/12/12.
 * 我的收藏
 */

public class FavouriteShopBean {

    /**
     * status : 0
     * message : 收藏列表
     * content : {"pages":1,"pageSize":12,"currentPage":1,"favoriteList":[{"icon":"resources/style/common/imagesgood.jpg","goodsList":[{"goods_id":98460,"goods_icon":"upload/store/1/2016/04/047409991b-ff7a-4481-8402-dbead4fc7285.jpg"},{"goods_id":98456,"goods_icon":"upload/store/1/2016/04/04b23a1411-8002-48eb-bc3a-3624ed6ee3ce.jpg"},{"goods_id":98455,"goods_icon":"upload/store/1/2016/04/043c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg"}],"store_level":"普通商家","store_status":"专卖店","store_id":1,"store_speed":"步行速度","store_name":"好卖店","favorite_id":131084,"store_info":""}]}
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
         * favoriteList : [{"icon":"resources/style/common/imagesgood.jpg","goodsList":[{"goods_id":98460,"goods_icon":"upload/store/1/2016/04/047409991b-ff7a-4481-8402-dbead4fc7285.jpg"},{"goods_id":98456,"goods_icon":"upload/store/1/2016/04/04b23a1411-8002-48eb-bc3a-3624ed6ee3ce.jpg"},{"goods_id":98455,"goods_icon":"upload/store/1/2016/04/043c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg"}],"store_level":"普通商家","store_status":"专卖店","store_id":1,"store_speed":"步行速度","store_name":"好卖店","favorite_id":131084,"store_info":""}]
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
             * icon : resources/style/common/imagesgood.jpg
             * goodsList : [{"goods_id":98460,"goods_icon":"upload/store/1/2016/04/047409991b-ff7a-4481-8402-dbead4fc7285.jpg"},{"goods_id":98456,"goods_icon":"upload/store/1/2016/04/04b23a1411-8002-48eb-bc3a-3624ed6ee3ce.jpg"},{"goods_id":98455,"goods_icon":"upload/store/1/2016/04/043c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg"}]
             * store_level : 普通商家
             * store_status : 专卖店
             * store_id : 1
             * store_speed : 步行速度
             * store_name : 好卖店
             * favorite_id : 131084
             * store_info :
             */

            private String icon;
            private String store_level;
            private String store_status;
            private int store_id;
            private String store_speed;
            private String store_name;
            private long favorite_id;
            private String store_info;
            private List<GoodsListBean> goodsList;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getStore_level() {
                return store_level;
            }

            public void setStore_level(String store_level) {
                this.store_level = store_level;
            }

            public String getStore_status() {
                return store_status;
            }

            public void setStore_status(String store_status) {
                this.store_status = store_status;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getStore_speed() {
                return store_speed;
            }

            public void setStore_speed(String store_speed) {
                this.store_speed = store_speed;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public long getFavorite_id() {
                return favorite_id;
            }

            public void setFavorite_id(long favorite_id) {
                this.favorite_id = favorite_id;
            }

            public String getStore_info() {
                return store_info;
            }

            public void setStore_info(String store_info) {
                this.store_info = store_info;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean {
                /**
                 * goods_id : 98460
                 * goods_icon : upload/store/1/2016/04/047409991b-ff7a-4481-8402-dbead4fc7285.jpg
                 */

                private int goods_id;
                private String goods_icon;

                private String zone_type;

                public String getZone_type() {
                    return zone_type;
                }

                public void setZone_type(String zone_type) {
                    this.zone_type = zone_type;
                }

                public int getGoods_id() {
                    return goods_id;
                }

                public void setGoods_id(int goods_id) {
                    this.goods_id = goods_id;
                }

                public String getGoods_icon() {
                    return goods_icon;
                }

                public void setGoods_icon(String goods_icon) {
                    this.goods_icon = goods_icon;
                }
            }
        }
    }
}
