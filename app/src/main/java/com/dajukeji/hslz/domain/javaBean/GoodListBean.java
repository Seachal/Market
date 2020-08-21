package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class GoodListBean implements Serializable{

    /**
     * message : 产品列表
     * content : {"pages":16,"goodsList":[{"goods_inventory":200,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","goods_price":508,"goods_store_id":1,"id":65536,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","goods_price":895,"goods_store_id":1,"id":32768,"goods_current_price":378,"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"欧洲站2016春装新品女装春季圆领中袖A字白色连衣裙子配送腰带","goods_price":300,"goods_store_id":1,"id":98304,"goods_current_price":131,"goods_main_photo":"upload/store/1/2016/04/06/1572d08b-1404-449f-a746-0336612f15a9.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"I\u2019m David16专柜新品爱大卫韩版春季男装长袖休闲外套DQJP11C1","goods_price":1280,"goods_store_id":1,"id":1,"goods_current_price":769,"goods_main_photo":"upload/store/1/2016/04/06/2a4c105f-d92e-4268-b7e9-c2f015e1a614.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"伶俐柠檬10651复古女装连衣裙手绘长袖长裙中国风文艺范原创设计","goods_price":300,"goods_store_id":1,"id":65537,"goods_current_price":235,"goods_main_photo":"upload/store/1/2016/04/06/364bef0c-53e0-4b6a-8ca5-e6850436d176.png","goods_salenum":0},{"goods_inventory":200,"goods_name":"春季夹克男休闲外套男装夹克衫立领多色男士薄款上衣韩版潮男茄克","goods_price":598,"goods_store_id":1,"id":32769,"goods_current_price":208,"goods_main_photo":"upload/store/1/2016/04/06/8ff57afe-41bc-46a6-bcde-6a1bb9f0683e.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"女装春夏韩版收腰修身圆领雪纺连衣裙A字裙大码百搭短袖新款淑女","goods_price":700,"goods_store_id":1,"id":98305,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/06/83293942-6aaa-45c3-a27b-772ea17661a0.png","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛旗舰店2016春季新品女装绣花不规则摆裙子七分袖雪纺连衣裙夏","goods_price":259.8,"goods_store_id":1,"id":65538,"goods_current_price":259.8,"goods_main_photo":"upload/store/1/2016/04/06/857228fa-a0a8-4e12-9859-bfb19655f2a1.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"shinena夏娜 2013秋冬新款白色公主裙韩版翻领修身蕾丝修身连衣裙","goods_price":300,"goods_store_id":1,"id":98306,"goods_current_price":235,"goods_main_photo":"upload/store/1/2016/03/09/c9d4616b-3ff7-4a20-8a21-0cf6bf0459bd.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"秋冬新款男装 冲锋衣羽绒服 中长厚外套","goods_price":900,"goods_store_id":1,"id":3,"goods_current_price":789,"goods_main_photo":"upload/store/1/2016/04/06/da8d5bed-5b9d-4fe5-823f-610c4562d956.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"姿忆秀加绒棉衣女装中长款2015冬装新款修身外套秋冬季韩版连帽","goods_price":700,"goods_store_id":1,"id":65539,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/06/ed7600d6-62cb-48d5-b971-6884f2b2203c.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛长裙2016夏装新款刺绣无袖背心裙子气质中长款雪纺连衣裙女春","goods_price":300,"goods_store_id":1,"id":98307,"goods_current_price":199.8,"goods_main_photo":"upload/store/1/2016/04/06/65b46d4f-65e0-42bc-ba4c-83878e42305c.jpg","goods_salenum":0}],"pageSize":12,"currentPage":1}
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
         * pages : 16
         * goodsList : [{"goods_inventory":200,"goods_name":"冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套","goods_price":508,"goods_store_id":1,"id":65536,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","goods_price":895,"goods_store_id":1,"id":32768,"goods_current_price":378,"goods_main_photo":"upload/store/1/2016/04/06/707f0bde-c7e6-47ed-880e-45029af157b0.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"欧洲站2016春装新品女装春季圆领中袖A字白色连衣裙子配送腰带","goods_price":300,"goods_store_id":1,"id":98304,"goods_current_price":131,"goods_main_photo":"upload/store/1/2016/04/06/1572d08b-1404-449f-a746-0336612f15a9.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"I\u2019m David16专柜新品爱大卫韩版春季男装长袖休闲外套DQJP11C1","goods_price":1280,"goods_store_id":1,"id":1,"goods_current_price":769,"goods_main_photo":"upload/store/1/2016/04/06/2a4c105f-d92e-4268-b7e9-c2f015e1a614.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"伶俐柠檬10651复古女装连衣裙手绘长袖长裙中国风文艺范原创设计","goods_price":300,"goods_store_id":1,"id":65537,"goods_current_price":235,"goods_main_photo":"upload/store/1/2016/04/06/364bef0c-53e0-4b6a-8ca5-e6850436d176.png","goods_salenum":0},{"goods_inventory":200,"goods_name":"春季夹克男休闲外套男装夹克衫立领多色男士薄款上衣韩版潮男茄克","goods_price":598,"goods_store_id":1,"id":32769,"goods_current_price":208,"goods_main_photo":"upload/store/1/2016/04/06/8ff57afe-41bc-46a6-bcde-6a1bb9f0683e.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"女装春夏韩版收腰修身圆领雪纺连衣裙A字裙大码百搭短袖新款淑女","goods_price":700,"goods_store_id":1,"id":98305,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/06/83293942-6aaa-45c3-a27b-772ea17661a0.png","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛旗舰店2016春季新品女装绣花不规则摆裙子七分袖雪纺连衣裙夏","goods_price":259.8,"goods_store_id":1,"id":65538,"goods_current_price":259.8,"goods_main_photo":"upload/store/1/2016/04/06/857228fa-a0a8-4e12-9859-bfb19655f2a1.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"shinena夏娜 2013秋冬新款白色公主裙韩版翻领修身蕾丝修身连衣裙","goods_price":300,"goods_store_id":1,"id":98306,"goods_current_price":235,"goods_main_photo":"upload/store/1/2016/03/09/c9d4616b-3ff7-4a20-8a21-0cf6bf0459bd.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"秋冬新款男装 冲锋衣羽绒服 中长厚外套","goods_price":900,"goods_store_id":1,"id":3,"goods_current_price":789,"goods_main_photo":"upload/store/1/2016/04/06/da8d5bed-5b9d-4fe5-823f-610c4562d956.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"姿忆秀加绒棉衣女装中长款2015冬装新款修身外套秋冬季韩版连帽","goods_price":700,"goods_store_id":1,"id":65539,"goods_current_price":600,"goods_main_photo":"upload/store/1/2016/04/06/ed7600d6-62cb-48d5-b971-6884f2b2203c.jpg","goods_salenum":0},{"goods_inventory":200,"goods_name":"裂帛长裙2016夏装新款刺绣无袖背心裙子气质中长款雪纺连衣裙女春","goods_price":300,"goods_store_id":1,"id":98307,"goods_current_price":199.8,"goods_main_photo":"upload/store/1/2016/04/06/65b46d4f-65e0-42bc-ba4c-83878e42305c.jpg","goods_salenum":0}]
         * pageSize : 12
         * currentPage : 1
         */
        private int pages;
        private List<GoodsListEntity> goodsList;
        private List<GoodsListEntity> commonGoodsList;
        private int pageSize;
        private int currentPage;

        public List<GoodsListEntity> getCommonGoodsList() {
            return commonGoodsList;
        }

        public void setCommonGoodsList(List<GoodsListEntity> commonGoodsList) {
            this.commonGoodsList = commonGoodsList;
        }

        public void setPages(int pages) {
            this.pages = pages;
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

        public List<GoodsListEntity> getGoodsList() {
            return goodsList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public class GoodsListEntity {
            /**
             * goods_inventory : 200
             * goods_name : 冬少女装韩版短款羽绒服 白领时尚连帽休闲棉服 短外套
             * goods_price : 508.0
             * goods_store_id : 1
             * id : 65536
             * goods_current_price : 600.0
             * goods_main_photo : upload/store/1/2016/04/04/3dccce3e-b6b9-4f7e-9a5c-694a162aa88d.jpg
             * goods_salenum : 0
             */
            private String price_des;
            private int goods_inventory;
            private String goods_name;
            private double goods_price;
            private int goods_store_id;
            private int id;
            private double goods_current_price;
            private String goods_main_photo;
            private int goods_salenum;
            private int invite_count; // 受邀人数
            private double lowest_price;
            private int max_goods_inventory; // 最大库存
            private int integration;

            public int getIntegration() {
                return integration;
            }

            public void setIntegration(int integration) {
                this.integration = integration;
            }

            public int getMax_goods_inventory() {
                return max_goods_inventory;
            }

            public void setMax_goods_inventory(int max_goods_inventory) {
                this.max_goods_inventory = max_goods_inventory;
            }
            private String zone_type;

            public String getPrice_des() {
                return price_des;
            }

            public void setPrice_des(String price_des) {
                this.price_des = price_des;
            }

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public double getLowest_price() {
                return lowest_price;
            }

            public void setLowest_price(double lowest_price) {
                this.lowest_price = lowest_price;
            }

            public int getInvite_count() {
                return invite_count;
            }

            public void setInvite_count(int invite_count) {
                this.invite_count = invite_count;
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

            public void setGoods_store_id(int goods_store_id) {
                this.goods_store_id = goods_store_id;
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

            public int getGoods_store_id() {
                return goods_store_id;
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
