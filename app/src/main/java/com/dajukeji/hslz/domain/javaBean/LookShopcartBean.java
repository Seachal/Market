package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class LookShopcartBean implements Serializable{
    /**
     * message : 购物车
     * content : {"store_cart_list":[{"spec_info":"颜色:黑色 鞋码:37 ","store_id":32773,"goods_name":"环球2017冬季新款学生保暖加绒雪地靴女韩版百搭棉鞋短筒短靴子女","total":0,"goods_image":"upload/store/32773/2018/01/15/54c72db0-5041-4e17-a5d9-7b67cdfee6fa.jpg","goods_price":0,"gc_id":230277,"count":1,"goods_id":98562,"store_name":"滕飞鞋服专卖店","status":0},{"spec_info":"颜色:白色 尺码:M ","store_id":1,"goods_name":"欧洲站2016春装新品女装春季圆领中袖A字白色连衣裙子配送腰带","total":131,"goods_image":"upload/store/1/2016/04/06/1572d08b-1404-449f-a746-0336612f15a9.jpg","goods_price":131,"gc_id":230271,"count":1,"goods_id":98304,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"颜色:黑色 尺码:XL ","store_id":1,"goods_name":"秋冬新款男装 冲锋衣羽绒服 中长厚外套","total":789,"goods_image":"upload/store/1/2016/04/06/da8d5bed-5b9d-4fe5-823f-610c4562d956.jpg","goods_price":789,"gc_id":230272,"count":1,"goods_id":3,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"颜色:红色 尺码:M ","store_id":1,"goods_name":"姿忆秀加绒棉衣女装中长款2015冬装新款修身外套秋冬季韩版连帽","total":600,"goods_image":"upload/store/1/2016/04/06/ed7600d6-62cb-48d5-b971-6884f2b2203c.jpg","goods_price":600,"gc_id":230273,"count":1,"goods_id":65539,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"尺码:均码 ","store_id":1,"goods_name":"三枪内衣 秋衣套装 三枪男士半高领精纺全棉套装 20048D0专柜正品","total":235,"goods_image":"upload/store/1/2016/03/09/8dede06c-7b84-43c9-8530-709d6e0b92b0.png","goods_price":235,"gc_id":230274,"count":1,"goods_id":98313,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"尺码:均码 ","store_id":1,"goods_name":"女士珊瑚绒睡裙碧美妮加厚保暖长袖睡衣","total":235,"goods_image":"upload/store/1/2016/03/09/ae2d4318-bb0e-4fdd-87f5-40579bca5443.jpg","goods_price":235,"gc_id":230275,"count":1,"goods_id":98315,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"鞋码:40 颜色:蓝色 ","store_id":1,"goods_name":"NIKE 耐克 max 男子跑步鞋 487982-104","total":235,"goods_image":"upload/store/1/2016/03/09/9956ffdb-26fa-412c-9f69-ac68db460c58.jpg","goods_price":235,"gc_id":230276,"count":1,"goods_id":98320,"store_name":"非凡服饰专卖店","status":0}]}
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

    public class ContentEntity implements Serializable{
        /**
         * store_cart_list : [{"spec_info":"颜色:黑色 鞋码:37 ","store_id":32773,"goods_name":"环球2017冬季新款学生保暖加绒雪地靴女韩版百搭棉鞋短筒短靴子女","total":0,"goods_image":"upload/store/32773/2018/01/15/54c72db0-5041-4e17-a5d9-7b67cdfee6fa.jpg","goods_price":0,"gc_id":230277,"count":1,"goods_id":98562,"store_name":"滕飞鞋服专卖店","status":0},{"spec_info":"颜色:白色 尺码:M ","store_id":1,"goods_name":"欧洲站2016春装新品女装春季圆领中袖A字白色连衣裙子配送腰带","total":131,"goods_image":"upload/store/1/2016/04/06/1572d08b-1404-449f-a746-0336612f15a9.jpg","goods_price":131,"gc_id":230271,"count":1,"goods_id":98304,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"颜色:黑色 尺码:XL ","store_id":1,"goods_name":"秋冬新款男装 冲锋衣羽绒服 中长厚外套","total":789,"goods_image":"upload/store/1/2016/04/06/da8d5bed-5b9d-4fe5-823f-610c4562d956.jpg","goods_price":789,"gc_id":230272,"count":1,"goods_id":3,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"颜色:红色 尺码:M ","store_id":1,"goods_name":"姿忆秀加绒棉衣女装中长款2015冬装新款修身外套秋冬季韩版连帽","total":600,"goods_image":"upload/store/1/2016/04/06/ed7600d6-62cb-48d5-b971-6884f2b2203c.jpg","goods_price":600,"gc_id":230273,"count":1,"goods_id":65539,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"尺码:均码 ","store_id":1,"goods_name":"三枪内衣 秋衣套装 三枪男士半高领精纺全棉套装 20048D0专柜正品","total":235,"goods_image":"upload/store/1/2016/03/09/8dede06c-7b84-43c9-8530-709d6e0b92b0.png","goods_price":235,"gc_id":230274,"count":1,"goods_id":98313,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"尺码:均码 ","store_id":1,"goods_name":"女士珊瑚绒睡裙碧美妮加厚保暖长袖睡衣","total":235,"goods_image":"upload/store/1/2016/03/09/ae2d4318-bb0e-4fdd-87f5-40579bca5443.jpg","goods_price":235,"gc_id":230275,"count":1,"goods_id":98315,"store_name":"非凡服饰专卖店","status":0},{"spec_info":"鞋码:40 颜色:蓝色 ","store_id":1,"goods_name":"NIKE 耐克 max 男子跑步鞋 487982-104","total":235,"goods_image":"upload/store/1/2016/03/09/9956ffdb-26fa-412c-9f69-ac68db460c58.jpg","goods_price":235,"gc_id":230276,"count":1,"goods_id":98320,"store_name":"非凡服饰专卖店","status":0}]
         */
        private List<Store_cart_listEntity> store_cart_list;

        public void setStore_cart_list(List<Store_cart_listEntity> store_cart_list) {
            this.store_cart_list = store_cart_list;
        }

        public List<Store_cart_listEntity> getStore_cart_list() {
            return store_cart_list;
        }

        public class Store_cart_listEntity implements Serializable{
            /**
             * spec_info : 颜色:黑色 鞋码:37
             * store_id : 32773
             * goods_name : 环球2017冬季新款学生保暖加绒雪地靴女韩版百搭棉鞋短筒短靴子女
             * total : 0.0
             * goods_image : upload/store/32773/2018/01/15/54c72db0-5041-4e17-a5d9-7b67cdfee6fa.jpg
             * goods_price : 0.0
             * gc_id : 230277
             * count : 1
             * goods_id : 98562
             * store_name : 滕飞鞋服专卖店
             * status : 0
             */
            private String spec_info;
            private int store_id;
            private String goods_name;
            private double total;
            private String goods_image;
            private double goods_price;
            private int gc_id;
            private int count;
            private int goods_id;
            private String store_name;
            private int status;
            private boolean selected;
            private boolean securities;//false=产品 true=产品券

            public boolean isSecurities() {
                return securities;
            }

            public void setSecurities(boolean securities) {
                this.securities = securities;
            }

            public boolean isSelected() {
                return selected;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public void setSpec_info(String spec_info) {
                this.spec_info = spec_info;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setTotal(double total) {
                this.total = total;
            }

            public void setGoods_image(String goods_image) {
                this.goods_image = goods_image;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public void setGc_id(int gc_id) {
                this.gc_id = gc_id;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSpec_info() {
                return spec_info;
            }

            public int getStore_id() {
                return store_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public double getTotal() {
                return total;
            }

            public String getGoods_image() {
                return goods_image;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public int getGc_id() {
                return gc_id;
            }

            public int getCount() {
                return count;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public int getStatus() {
                return status;
            }
        }
    }

}
