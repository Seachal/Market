package com.dajukeji.hslz.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/11/6.
 */

public class GsonGoods {

    /**
     * status : 0
     * message : 产品详情
     * content : {"spec":{"颜色_32768":"白色_32768,紫色_32771","尺码_2":"S_17,M_18,L_19,XL_20"},"inventory_detail":[],"inventory_type":"all","ems_trans_fee":0,"evaluateList":[{"user_name":"admin","evaluate_info":" 您的评价将是其他买家的参考","head_image":"","goods_spec":"颜色:白色 尺码:均码 ","add_time":"2014.01.02"}],"goods_inventory":195,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子","express_trans_fee":0,"goods_salenum":5,"photoList":["upload/store/1/2016/04/04/9f02c287-0962-4d4d-a474-5c7328199651.jpg","upload/store/1/2016/04/04/f0cc1d42-ad62-4274-bad1-879db5d3c9ce.jpg","upload/store/1/2016/04/04/0de6d172-5f47-445d-8ab9-bedb8bebad4a.jpg","upload/store/1/2016/04/04/039f78c8-1e7b-41ec-9734-8063b90588e2.jpg"],"goods_price":288,"mail_trans_fee":0,"goods_id":98412,"goods_details":"<p align=\"center\" style=\"text-align:left;\">\r\n\t&nbsp;<strong><span style=\"font-size:16px;\">&nbsp;<\/span><\/strong><span><strong><span style=\"font-size:16px;\">货号: 5646 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/span><\/strong><strong><span style=\"font-size:16px;\">&nbsp;<\/span><\/strong><strong><span style=\"font-size:16px;\">品牌: 草莓花朵 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <\/span><\/strong><strong><span style=\"font-size:16px;\">年份季节: 2016年夏季 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/span><\/strong><strong><span style=\"font-size:16px;\">颜色分类: 白色 紫色 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<\/span><\/strong><strong><span style=\"font-size:16px;\">&nbsp;<img src=\"/upload/store/1/2016/04/04/9f02c287-0962-4d4d-a474-5c7328199651.jpg\" /><img src=\"/upload/store/1/2016/04/04/f0cc1d42-ad62-4274-bad1-879db5d3c9ce.jpg\" /><\/span><\/strong><strong><span style=\"font-size:16px;\">尺码: S M L XL<\/span><\/strong><\/span><strong><span style=\"font-size:16px;\"><\/span><\/strong> \r\n<\/p>\r\n<p align=\"center\">\r\n\t<span><\/span> \r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>\r\n<p>\r\n\t<br />\r\n<\/p>","goods_current_price":712,"evaluate_size":1,"goods_transfee":0}
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
         * spec : {"颜色_32768":"白色_32768,紫色_32771","尺码_2":"S_17,M_18,L_19,XL_20"}
         * inventory_detail : []
         * inventory_type : all
         * ems_trans_fee : 0
         * evaluateList : [{"user_name":"admin","evaluate_info":" 您的评价将是其他买家的参考","head_image":"","goods_spec":"颜色:白色 尺码:均码 ","add_time":"2014.01.02"}]
         * goods_inventory : 195
         * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
         * express_trans_fee : 0
         * goods_salenum : 5
         * photoList : ["upload/store/1/2016/04/04/9f02c287-0962-4d4d-a474-5c7328199651.jpg","upload/store/1/2016/04/04/f0cc1d42-ad62-4274-bad1-879db5d3c9ce.jpg","upload/store/1/2016/04/04/0de6d172-5f47-445d-8ab9-bedb8bebad4a.jpg","upload/store/1/2016/04/04/039f78c8-1e7b-41ec-9734-8063b90588e2.jpg"]
         * goods_price : 288
         * mail_trans_fee : 0
         * goods_id : 98412
         * goods_details : url
         * goods_current_price : 712
         * evaluate_size : 1
         * goods_transfee : 0
         */

        private SpecBean spec;
        private String inventory_type;
        private int ems_trans_fee;
        private int goods_inventory;
        private String goods_name;
        private int express_trans_fee;
        private int goods_salenum;
        private int goods_price;
        private int mail_trans_fee;
        private int goods_id;
        private String goods_details;
        private int goods_current_price;
        private int evaluate_size;
        private int goods_transfee;
        private List<?> inventory_detail;
        private List<EvaluateListBean> evaluateList;
        private List<String> photoList;

        public SpecBean getSpec() {
            return spec;
        }

        public void setSpec(SpecBean spec) {
            this.spec = spec;
        }

        public String getInventory_type() {
            return inventory_type;
        }

        public void setInventory_type(String inventory_type) {
            this.inventory_type = inventory_type;
        }

        public int getEms_trans_fee() {
            return ems_trans_fee;
        }

        public void setEms_trans_fee(int ems_trans_fee) {
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

        public int getExpress_trans_fee() {
            return express_trans_fee;
        }

        public void setExpress_trans_fee(int express_trans_fee) {
            this.express_trans_fee = express_trans_fee;
        }

        public int getGoods_salenum() {
            return goods_salenum;
        }

        public void setGoods_salenum(int goods_salenum) {
            this.goods_salenum = goods_salenum;
        }

        public int getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(int goods_price) {
            this.goods_price = goods_price;
        }

        public int getMail_trans_fee() {
            return mail_trans_fee;
        }

        public void setMail_trans_fee(int mail_trans_fee) {
            this.mail_trans_fee = mail_trans_fee;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_details() {
            return goods_details;
        }

        public void setGoods_details(String goods_details) {
            this.goods_details = goods_details;
        }

        public int getGoods_current_price() {
            return goods_current_price;
        }

        public void setGoods_current_price(int goods_current_price) {
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

        public List<?> getInventory_detail() {
            return inventory_detail;
        }

        public void setInventory_detail(List<?> inventory_detail) {
            this.inventory_detail = inventory_detail;
        }

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public List<String> getPhotoList() {
            return photoList;
        }

        public void setPhotoList(List<String> photoList) {
            this.photoList = photoList;
        }

        public static class SpecBean {
            /**
             * 颜色_32768 : 白色_32768,紫色_32771
             * 尺码_2 : S_17,M_18,L_19,XL_20
             */

            private String 颜色_32768;
            private String 尺码_2;

            public String get颜色_32768() {
                return 颜色_32768;
            }

            public void set颜色_32768(String 颜色_32768) {
                this.颜色_32768 = 颜色_32768;
            }

            public String get尺码_2() {
                return 尺码_2;
            }

            public void set尺码_2(String 尺码_2) {
                this.尺码_2 = 尺码_2;
            }
        }

        public static class EvaluateListBean {
            /**
             * user_name : 评价人
             * evaluate_info :  评价类容
             * head_image : 用户头像
             * goods_spec : 产品信息
             * add_time : 2014.01.02
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
    }
}
