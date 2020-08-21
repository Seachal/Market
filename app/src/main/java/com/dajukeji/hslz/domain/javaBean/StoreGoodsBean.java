package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/6 0006.
 */

public class StoreGoodsBean {

    /**
     * message : 产品列表
     * content : {"pages":1,"banner_list":[{"goods_id":98335,"banner_logo":"upload/brand/a297e2a1-7056-4992-adc2-690412638b51.jpg"},{"goods_id":1,"banner_logo":"upload/brand/585d7700-b52a-4dc3-bf51-9218eafd6e09.jpg"},{"goods_id":65539,"banner_logo":"upload/brand/47caa433-3416-4c50-9fb4-d6ad55e950dd.jpg"},{"goods_id":65539,"banner_logo":"upload/brand/c019b21a-2a17-41d6-b297-cc0f8d52dcaa.jpg"}],"goodsList":[{"goods_inventory":100,"goods_name":"2016夏季新款A字无袖修身显瘦连衣裙欧根纱打底韩版高腰蓬蓬裙女","goods_price":300,"zone_type":"","id":98454,"goods_current_price":138,"goods_main_photo":"upload/store/1/2016/04/04/7c8d82c1-bfa6-42fd-9921-6755d04c207c.jpg","goods_salenum":0},{"goods_inventory":100,"goods_name":"Yamaha雅马哈 RX-V375PA40 家庭影院客厅5.1套装音响音箱电视","goods_price":6200,"zone_type":"","id":98456,"goods_current_price":3999,"goods_main_photo":"upload/store/1/2016/04/04/b23a1411-8002-48eb-bc3a-3624ed6ee3ce.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛2016夏装新款立体竖条装饰刺绣圆领中袖 A型连衣裙女51151847","goods_price":439,"zone_type":"","id":98460,"goods_current_price":339.8,"goods_main_photo":"upload/store/1/2016/04/04/7409991b-ff7a-4481-8402-dbead4fc7285.jpg","goods_salenum":0},{"goods_inventory":221,"goods_name":"创维新品电视","goods_price":1234,"brand_price":10,"zone_type":"brand","id":98540,"goods_current_price":10,"goods_main_photo":"upload/store/1/2018/01/12/7702e9aa-9bbe-4e41-8bf3-f6c553583c8b.jpg","goods_salenum":0}],"pageSize":12,"currentPage":1}
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
         * pages : 1
         * banner_list : [{"goods_id":98335,"banner_logo":"upload/brand/a297e2a1-7056-4992-adc2-690412638b51.jpg"},{"goods_id":1,"banner_logo":"upload/brand/585d7700-b52a-4dc3-bf51-9218eafd6e09.jpg"},{"goods_id":65539,"banner_logo":"upload/brand/47caa433-3416-4c50-9fb4-d6ad55e950dd.jpg"},{"goods_id":65539,"banner_logo":"upload/brand/c019b21a-2a17-41d6-b297-cc0f8d52dcaa.jpg"}]
         * goodsList : [{"goods_inventory":100,"goods_name":"2016夏季新款A字无袖修身显瘦连衣裙欧根纱打底韩版高腰蓬蓬裙女","goods_price":300,"zone_type":"","id":98454,"goods_current_price":138,"goods_main_photo":"upload/store/1/2016/04/04/7c8d82c1-bfa6-42fd-9921-6755d04c207c.jpg","goods_salenum":0},{"goods_inventory":100,"goods_name":"Yamaha雅马哈 RX-V375PA40 家庭影院客厅5.1套装音响音箱电视","goods_price":6200,"zone_type":"","id":98456,"goods_current_price":3999,"goods_main_photo":"upload/store/1/2016/04/04/b23a1411-8002-48eb-bc3a-3624ed6ee3ce.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛2016夏装新款立体竖条装饰刺绣圆领中袖 A型连衣裙女51151847","goods_price":439,"zone_type":"","id":98460,"goods_current_price":339.8,"goods_main_photo":"upload/store/1/2016/04/04/7409991b-ff7a-4481-8402-dbead4fc7285.jpg","goods_salenum":0},{"goods_inventory":221,"goods_name":"创维新品电视","goods_price":1234,"brand_price":10,"zone_type":"brand","id":98540,"goods_current_price":10,"goods_main_photo":"upload/store/1/2018/01/12/7702e9aa-9bbe-4e41-8bf3-f6c553583c8b.jpg","goods_salenum":0}]
         * pageSize : 12
         * currentPage : 1
         */
        private int pages;
        private List<Banner_listEntity> banner_list;
        private List<GoodsListEntity> goodsList;
        private int pageSize;
        private int currentPage;

        public void setPages(int pages) {
            this.pages = pages;
        }

        public void setBanner_list(List<Banner_listEntity> banner_list) {
            this.banner_list = banner_list;
        }

        public void setGoodsList(List<GoodsListEntity> goodsList) {
            this.goodsList = goodsList;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPages() {
            return pages;
        }

        public List<Banner_listEntity> getBanner_list() {
            return banner_list;
        }

        public List<GoodsListEntity> getGoodsList() {
            return goodsList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public class Banner_listEntity {
            /**
             * goods_id : 98335
             * banner_logo : upload/brand/a297e2a1-7056-4992-adc2-690412638b51.jpg
             */
            private int goods_id;
            private String banner_logo;
            private String zone_type;

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setBanner_logo(String banner_logo) {
                this.banner_logo = banner_logo;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public String getBanner_logo() {
                return banner_logo;
            }
        }

        public class GoodsListEntity {
            /**
             * goods_inventory : 100
             * goods_name : 2016夏季新款A字无袖修身显瘦连衣裙欧根纱打底韩版高腰蓬蓬裙女
             * goods_price : 300.0
             * zone_type :
             * id : 98454
             * goods_current_price : 138.0
             * goods_main_photo : upload/store/1/2016/04/04/7c8d82c1-bfa6-42fd-9921-6755d04c207c.jpg
             * goods_salenum : 0
             */
            private int goods_inventory;
            private String goods_name;
            private double goods_price;
            private String zone_type;
            private int id;
            private double goods_current_price;
            private String goods_main_photo;
            private int goods_salenum;
            private String price_des;
            private int max_goods_inventory;
            private double share_price;

            public String getPrice_des() {
                return price_des;
            }

            public void setPrice_des(String price_des) {
                this.price_des = price_des;
            }

            public int getMax_goods_inventory() {
                return max_goods_inventory;
            }

            public void setMax_goods_inventory(int max_goods_inventory) {
                this.max_goods_inventory = max_goods_inventory;
            }

            public double getShare_price() {
                return share_price;
            }

            public void setShare_price(double share_price) {
                this.share_price = share_price;
            }

            public void setGoods_inventory(int goods_inventory) {
                this.goods_inventory = goods_inventory;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setGoods_current_price(double goods_current_price) {
                this.goods_current_price = goods_current_price;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public void setGoods_salenum(int goods_salenum) {
                this.goods_salenum = goods_salenum;
            }

            public int getGoods_inventory() {
                return goods_inventory;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public String getZone_type() {
                return zone_type;
            }

            public int getId() {
                return id;
            }

            public double getGoods_current_price() {
                return goods_current_price;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public int getGoods_salenum() {
                return goods_salenum;
            }
        }
    }
}
