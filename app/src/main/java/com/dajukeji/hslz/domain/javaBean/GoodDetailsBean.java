package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2017/12/6 0006.
 */

public class GoodDetailsBean {
    /**
     * status : 0
     * message : 产品详情
     * content : {"goods_collect":0,"inventory_type":"spec","ems_trans_fee":40,"evaluateList":[{"user_name":"spiderman","evaluate_info":"确实不错。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"},{"user_name":"lpl","evaluate_info":"呵呵不说话。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"}],"goods_spec_list":[{"spec_list":[{"item_id":32770,"item":"红色","src":""},{"item_id":32774,"item":"透明","src":""}],"spec_name":"颜色"},{"spec_list":[{"item_id":15,"item":"XXS","src":""},{"item_id":21,"item":"XXL","src":""}],"spec_name":"尺码"}],"goods_inventory":980,"goods_name":"女神毛衣","express_trans_fee":30,"goods_salenum":0,"photoList":["upload/store/32774/2017/12/02/dd54de43-c3a1-4051-a811-30eb0416975e.png"],"wsOrderList":[{"nick_name":"方丈","require_count":8,"headimg_url":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","left_time":61756127,"order_id":229522}],"spec_goods_price":[{"price":"241","spec_img":"","bar_code":"","key_name":"","item_id":1,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"685","sku":"","key":"15_32770"},{"price":"247","spec_img":"","bar_code":"","key_name":"","item_id":2,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"45","sku":"","key":"21_32770"},{"price":"288","spec_img":"","bar_code":"","key_name":"","item_id":3,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"127","sku":"","key":"15_32774"},{"price":"265","spec_img":"","bar_code":"","key_name":"","item_id":4,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"123","sku":"","key":"21_32774"}],"wholesaling_count":19,"wholesale_price":159,"goods_price":500,"mail_trans_fee":20,"goods_id":98506,"goods_details":"http://192.168.0.100:8080/wemall/app/mall/mall/goodsDescription.htm?id=98506","goods_current_price":299,"evaluate_size":2,"recommend_goods_list":[{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/12/02/dd54de43-c3a1-4051-a811-30eb0416975e.png","goods_price":4,"goods_current_price":23,"goods_name":"压缩5"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/12/01/45a2e5c8-e39e-4cf7-9d2f-feb12ae8cebe.jpg","goods_price":100,"goods_current_price":200,"goods_name":"好看的皮带"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","goods_price":34,"goods_current_price":4,"goods_name":"新产品"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","goods_price":334,"goods_current_price":45,"goods_name":"拼团产品"}],"goods_transfee":0}
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
         * goods_collect : 0
         * inventory_type : spec
         * ems_trans_fee : 40.0
         * evaluateList : [{"user_name":"spiderman","evaluate_info":"确实不错。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"},{"user_name":"lpl","evaluate_info":"呵呵不说话。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"}]
         * goods_spec_list : [{"spec_list":[{"item_id":32770,"item":"红色","src":""},{"item_id":32774,"item":"透明","src":""}],"spec_name":"颜色"},{"spec_list":[{"item_id":15,"item":"XXS","src":""},{"item_id":21,"item":"XXL","src":""}],"spec_name":"尺码"}]
         * goods_inventory : 980
         * goods_name : 女神毛衣
         * express_trans_fee : 30.0
         * goods_salenum : 0
         * photoList : ["upload/store/32774/2017/12/02/dd54de43-c3a1-4051-a811-30eb0416975e.png"]
         * wsOrderList : [{"nick_name":"方丈","require_count":8,"headimg_url":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","left_time":61756127,"order_id":229522}]
         * spec_goods_price : [{"price":"241","spec_img":"","bar_code":"","key_name":"","item_id":1,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"685","sku":"","key":"15_32770"},{"price":"247","spec_img":"","bar_code":"","key_name":"","item_id":2,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"45","sku":"","key":"21_32770"},{"price":"288","spec_img":"","bar_code":"","key_name":"","item_id":3,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"127","sku":"","key":"15_32774"},{"price":"265","spec_img":"","bar_code":"","key_name":"","item_id":4,"goods_id":98506,"prom_id":0,"prom_type":0,"store_count":"123","sku":"","key":"21_32774"}]
         * wholesaling_count : 19
         * wholesale_price : 159.0
         * goods_price : 500.0
         * mail_trans_fee : 20.0
         * goods_id : 98506
         * goods_details : http://192.168.0.100:8080/wemall/app/mall/mall/goodsDescription.htm?id=98506
         * goods_current_price : 299.0
         * evaluate_size : 2
         * recommend_goods_list : [{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/12/02/dd54de43-c3a1-4051-a811-30eb0416975e.png","goods_price":4,"goods_current_price":23,"goods_name":"压缩5"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/12/01/45a2e5c8-e39e-4cf7-9d2f-feb12ae8cebe.jpg","goods_price":100,"goods_current_price":200,"goods_name":"好看的皮带"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","goods_price":34,"goods_current_price":4,"goods_name":"新产品"},{"goods_collect":0,"goods_main_photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","goods_price":334,"goods_current_price":45,"goods_name":"拼团产品"}]
         * goods_transfee : 0
         */
        private List<String> serviceList;
        private GoodsProductInfo goodsProductInfo;

        public List<String> getServiceList() {
            return serviceList;
        }

        public void setServiceList(List<String> serviceList) {
            this.serviceList = serviceList;
        }

        private int invite_count;
        private long left_time;
        private int integration;
        private double brand_price;
        private int goods_collect;
        private String inventory_type;
        private double ems_trans_fee;
        private int goods_inventory;
        private String goods_name;
        private double express_trans_fee;
        private int goods_salenum;
        private int wholesaling_count;
        private double wholesale_price;
        private double goods_price;
        private int goods_ststus;//后台字段写错了。。。
        private double mail_trans_fee;
        private long goods_id;
        private String goods_details;
        private double goods_current_price;
        private int evaluate_size;
        private int goods_transfee;
        private List<EvaluateListBean> evaluateList;
        private List<GoodsSpecListBean> goods_spec_list;
        private List<String> photoList;
        private List<WsOrderListBean> wsOrderList;
        private List<SpecGoodsPriceBean> spec_goods_price;
        private List<RecommendGoodsListBean> recommend_goods_list;
        private String goods_main_photo;
        private String zone_type;
        private int limit_count;
        private double share_price;
        private double not_share_price;
        private double lowest_price;
        private int collect_flag;   //是否收藏了产品，0为未收藏，1为已收藏
        private int return_days;    //无理由退货的天数量，如果为0则不能无理由退货
        private StoreClass store;
        private int isShare;

        public int getGoods_ststus() {
            return goods_ststus;
        }

        public void setGoods_ststus(int goods_ststus) {
            this.goods_ststus = goods_ststus;
        }


        public GoodsProductInfo getGoodsProductInfo() {
            return goodsProductInfo;
        }

        public void setGoodsProductInfo(GoodsProductInfo goodsProductInfo) {
            this.goodsProductInfo = goodsProductInfo;
        }

        public static class GoodsProductInfo{

            /**
             * startDeliveryTime : Jan 16, 2019 8:00:06 PM
             * sellProductTicket : false
             * goodsCouponStartUseTime : Jan 1, 2019 7:55:45 PM
             * goodsCouponEndUseTime : Jan 30, 2019 7:56:06 PM
             * jdPrice : 20
             * tbPrice : 2
             */

            private String startDeliveryTime;
            private boolean sellProductTicket;
            private String goodsCouponStartUseTime;
            private String goodsCouponEndUseTime;
            private double jdPrice;
            private double tbPrice;

            public String getStartDeliveryTime() {
                return startDeliveryTime;
            }

            public void setStartDeliveryTime(String startDeliveryTime) {
                this.startDeliveryTime = startDeliveryTime;
            }

            public boolean isSellProductTicket() {
                return sellProductTicket;
            }

            public void setSellProductTicket(boolean sellProductTicket) {
                this.sellProductTicket = sellProductTicket;
            }

            public String getGoodsCouponStartUseTime() {
                return goodsCouponStartUseTime;
            }

            public void setGoodsCouponStartUseTime(String goodsCouponStartUseTime) {
                this.goodsCouponStartUseTime = goodsCouponStartUseTime;
            }

            public String getGoodsCouponEndUseTime() {
                return goodsCouponEndUseTime;
            }

            public void setGoodsCouponEndUseTime(String goodsCouponEndUseTime) {
                this.goodsCouponEndUseTime = goodsCouponEndUseTime;
            }

            public double getJdPrice() {
                return jdPrice;
            }

            public void setJdPrice(double jdPrice) {
                this.jdPrice = jdPrice;
            }

            public double getTbPrice() {
                return tbPrice;
            }

            public void setTbPrice(double tbPrice) {
                this.tbPrice = tbPrice;
            }
        }
        public int getIsShare() {
            return isShare;
        }

        public void setIsShare(int isShare) {
            this.isShare = isShare;
        }

        public int getInvite_count() {
            return invite_count;
        }

        public void setInvite_count(int invite_count) {
            this.invite_count = invite_count;
        }

        public long getLeft_time() {
            return left_time;
        }

        public void setLeft_time(long left_time) {
            this.left_time = left_time;
        }

        public int getIntegration() {
            return integration;
        }

        public void setIntegration(int integration) {
            this.integration = integration;
        }

        public double getBrand_price() {
            return brand_price;
        }

        public void setBrand_price(double brand_price) {
            this.brand_price = brand_price;
        }

        public StoreClass getStore() {
            return store;
        }

        public void setStore(StoreClass store) {
            this.store = store;
        }

        public static class StoreClass{
            private String store_level;
            private int store_id;
            private String store_type;
            private String store_logo;
            private String store_speed;
            private String store_name;
            private String store_info;
            private String chat_id;

            public String getChat_id() {
                return chat_id;
            }

            public void setChat_id(String chat_id) {
                this.chat_id = chat_id;
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

            public String getStore_info() {
                return store_info;
            }

            public void setStore_info(String store_info) {
                this.store_info = store_info;
            }
        }
        public int getCollect_flag() {
            return collect_flag;
        }

        public void setCollect_flag(int collect_flag) {
            this.collect_flag = collect_flag;
        }

        public int getReturn_days() {
            return return_days;
        }

        public void setReturn_days(int return_days) {
            this.return_days = return_days;
        }

        public String getSeller_description() {
            return seller_description;
        }

        public void setSeller_description(String seller_description) {
            this.seller_description = seller_description;
        }

        private String seller_description;
        public double getLowest_price() {
            return lowest_price;
        }

        public void setLowest_price(double lowest_price) {
            this.lowest_price = lowest_price;
        }

        public double getShare_price() {
            return share_price;
        }

        public void setShare_price(double share_price) {
            this.share_price = share_price;
        }

        public double getNot_share_price() {
            return not_share_price;
        }

        public void setNot_share_price(double not_share_price) {
            this.not_share_price = not_share_price;
        }

        public int getLimit_count() {
            return limit_count;
        }

        public void setLimit_count(int limit_count) {
            this.limit_count = limit_count;
        }

        public String getGoods_main_photo() {
            return goods_main_photo;
        }

        public void setGoods_main_photo(String goods_main_photo) {
            this.goods_main_photo = goods_main_photo;
        }

        public int getGoods_collect() {
            return goods_collect;
        }

        public void setGoods_collect(int goods_collect) {
            this.goods_collect = goods_collect;
        }

        public String getInventory_type() {
            return inventory_type;
        }

        public void setInventory_type(String inventory_type) {
            this.inventory_type = inventory_type;
        }

        public double getEms_trans_fee() {
            return ems_trans_fee;
        }

        public void setEms_trans_fee(double ems_trans_fee) {
            this.ems_trans_fee = ems_trans_fee;
        }

        public int getGoods_inventory() {
            return goods_inventory;
        }

        public void setGoods_inventory(int goods_inventory) {
            this.goods_inventory = goods_inventory;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getExpress_trans_fee() {
            return express_trans_fee;
        }

        public void setExpress_trans_fee(double express_trans_fee) {
            this.express_trans_fee = express_trans_fee;
        }

        public int getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(int goods_salenum) {
            this.goods_salenum = goods_salenum;
        }

        public int getWholesaling_count() {
            return wholesaling_count;
        }

        public void setWholesaling_count(int wholesaling_count) {
            this.wholesaling_count = wholesaling_count;
        }

        public double getWholesale_price() {
            return wholesale_price;
        }

        public void setWholesale_price(double wholesale_price) {
            this.wholesale_price = wholesale_price;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public double getMail_trans_fee() {
            return mail_trans_fee;
        }

        public void setMail_trans_fee(double mail_trans_fee) {
            this.mail_trans_fee = mail_trans_fee;
        }

        public long getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(long goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_details() {
            return goods_details;
        }

        public void setGoods_details(String goods_details) {
            this.goods_details = goods_details;
        }

        public double getGoods_current_price() {
            return goods_current_price;
        }

        public void setGoods_current_price(double goods_current_price) {
            this.goods_current_price = goods_current_price;
        }

        public int getEvaluate_size() {
            return evaluate_size;
        }

        public void setEvaluate_size(int evaluate_size) {
            this.evaluate_size = evaluate_size;
        }

        public int getGoods_transfee() {
            return goods_transfee;
        }

        public void setGoods_transfee(int goods_transfee) {
            this.goods_transfee = goods_transfee;
        }

        public String getZone_type() {
            return zone_type;
        }

        public void setZone_type(String zone_type) {
            this.zone_type = zone_type;
        }

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public List<GoodsSpecListBean> getGoods_spec_list() {
            return goods_spec_list;
        }

        public void setGoods_spec_list(List<GoodsSpecListBean> goods_spec_list) {
            this.goods_spec_list = goods_spec_list;
        }

        public List<String> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<String> photoList) {
            this.photoList = photoList;
        }

        public List<WsOrderListBean> getWsOrderList() {
            return wsOrderList;
        }

        public void setWsOrderList(List<WsOrderListBean> wsOrderList) {
            this.wsOrderList = wsOrderList;
        }

        public List<SpecGoodsPriceBean> getSpec_goods_price() {
            return spec_goods_price;
        }

        public void setSpec_goods_price(List<SpecGoodsPriceBean> spec_goods_price) {
            this.spec_goods_price = spec_goods_price;
        }

        public List<RecommendGoodsListBean> getRecommend_goods_list() {
            return recommend_goods_list;
        }

        public void setRecommend_goods_list(List<RecommendGoodsListBean> recommend_goods_list) {
            this.recommend_goods_list = recommend_goods_list;
        }

        public static class EvaluateListBean {
            /**
             * user_name : spiderman
             * evaluate_info : 确实不错。
             * head_image : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
             * goods_spec : 颜色:红色 尺码:XXL
             * add_time : 2017.12.05
             */

            private String user_name;
            private String evaluate_info;
            private String head_image;
            private String goods_spec;
            private String add_time;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getEvaluate_info() {
                return evaluate_info;
            }

            public void setEvaluate_info(String evaluate_info) {
                this.evaluate_info = evaluate_info;
            }

            public String getHead_image() {
                return head_image;
            }

            public void setHead_image(String head_image) {
                this.head_image = head_image;
            }

            public String getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }

        public static class GoodsSpecListBean {
            /**
             * spec_list : [{"item_id":32770,"item":"红色","src":""},{"item_id":32774,"item":"透明","src":""}]
             * spec_name : 颜色
             */

            private String spec_name;
            private List<SpecListBean> spec_list;

            public String getSpec_name() {
                return spec_name;
            }

            public void setSpec_name(String spec_name) {
                this.spec_name = spec_name;
            }

            public List<SpecListBean> getSpec_list() {
                return spec_list;
            }

            public void setSpec_list(List<SpecListBean> spec_list) {
                this.spec_list = spec_list;
            }

            public static class SpecListBean {
                /**
                 * item_id : 32770
                 * item : 红色
                 * src :
                 */

                private int item_id;
                private String item;
                private String src;

                public int getItem_id() {
                    return item_id;
                }

                public void setItem_id(int item_id) {
                    this.item_id = item_id;
                }

                public String getItem() {
                    return item;
                }

                public void setItem(String item) {
                    this.item = item;
                }

                public String getSrc() {
                    return src;
                }

                public void setSrc(String src) {
                    this.src = src;
                }
            }
        }

        public static class WsOrderListBean {
            /**
             * nick_name : 方丈
             * require_count : 8
             * headimg_url : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
             * left_time : 61756127
             * order_id : 229522
             */

            private String nick_name;
            private int require_count;
            private String headimg_url;
            private int left_time;
            private int order_id;

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getRequire_count() {
                return require_count;
            }

            public void setRequire_count(int require_count) {
                this.require_count = require_count;
            }

            public String getHeadimg_url() {
                return headimg_url;
            }

            public void setHeadimg_url(String headimg_url) {
                this.headimg_url = headimg_url;
            }

            public int getLeft_time() {
                return left_time;
            }

            public void setLeft_time(int left_time) {
                this.left_time = left_time;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }
        }

        public static class SpecGoodsPriceBean {
            /**
             * price : 241
             * spec_img :
             * bar_code :
             * key_name :
             * item_id : 1
             * goods_id : 98506
             * prom_id : 0
             * prom_type : 0
             * store_count : 685
             * sku :
             * key : 15_32770
             */

            private String price;
            private String spec_img;
            private String bar_code;
            private String key_name;
            private int item_id;
            private int goods_id;
            private int prom_id;
            private int prom_type;
            private String store_count;
            private String sku;
            private String key;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getSpec_img() {
                return spec_img;
            }

            public void setSpec_img(String spec_img) {
                this.spec_img = spec_img;
            }

            public String getBar_code() {
                return bar_code;
            }

            public void setBar_code(String bar_code) {
                this.bar_code = bar_code;
            }

            public String getKey_name() {
                return key_name;
            }

            public void setKey_name(String key_name) {
                this.key_name = key_name;
            }

            public int getItem_id() {
                return item_id;
            }

            public void setItem_id(int item_id) {
                this.item_id = item_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public int getProm_id() {
                return prom_id;
            }

            public void setProm_id(int prom_id) {
                this.prom_id = prom_id;
            }

            public int getProm_type() {
                return prom_type;
            }

            public void setProm_type(int prom_type) {
                this.prom_type = prom_type;
            }

            public String getStore_count() {
                return store_count;
            }

            public void setStore_count(String store_count) {
                this.store_count = store_count;
            }

            public String getSku() {
                return sku;
            }

            public void setSku(String sku) {
                this.sku = sku;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class RecommendGoodsListBean {

            /**
             * goods_name : 马卡龙
             * goods_price : 121
             * zone_type :
             * id : 98522
             * goods_current_price : 121
             * price_des :
             * goods_main_photo : upload/store/32774/2018/01/08/f3b37220-3890-406c-bbc0-069046e16c2f.jpg_middle.jpg
             * goods_salenum : 0
             */
            private String goods_name;
            private double goods_price;
            private String zone_type;
            private int id;
            private double goods_current_price;
            private String price_des;
            private String goods_main_photo;
            private int goods_salenum;
            private double ref_price;//市场价

            public double getRef_price() {
                return ref_price;
            }

            public void setRef_price(double ref_price) {
                this.ref_price = ref_price;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_price(int goods_price) {
                this.goods_price = goods_price;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setGoods_current_price(int goods_current_price) {
                this.goods_current_price = goods_current_price;
            }

            public void setPrice_des(String price_des) {
                this.price_des = price_des;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public void setGoods_salenum(int goods_salenum) {
                this.goods_salenum = goods_salenum;
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

            public String getPrice_des() {
                return price_des;
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
