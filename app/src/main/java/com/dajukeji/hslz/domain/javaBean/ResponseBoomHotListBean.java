package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/2/15 13:57
 * 作用:
 */
public class ResponseBoomHotListBean {

    /**
     * status : 0
     * message : 爆款区数据
     * content : {"middleBannerList":[{"extra":99105,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/6a2b10d3-54a2-44d2-a537-15b35bac4b65.png","type":0},{"extra":99105,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/62a40325-e139-4b36-a36c-97c5ac2cd3b7.png","type":0},{"extra":99105,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/6fea545c-2f2d-4bf6-98ca-c275f3ab728d.png","type":0},{"extra":99105,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/d48f83bd-0b6d-448e-852e-1e945fa0ca5c.png","type":0},{"extra":99105,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/c2122cc4-d7dc-4484-a719-c3f63dd14a75.png","type":0}],"goodsList":[{"goods_name":"sk-ii sk2男士神仙水skii精华护肤套装保湿控油正品","goods_price":780,"ref_price":1450,"id":99072,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/d58ae7c7-6996-41ab-81b3-696b9ccab3e9.jpg_middle.jpg","goods_salenum":6},{"goods_name":"SK-II面霜skii sk2能量大红瓶面霜套装清爽型淡化细","goods_price":460,"ref_price":860,"id":99074,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/bfe35e9a-2920-4f6f-9e79-45141356dfe3.jpg_middle.jpg","goods_salenum":8},{"goods_name":"Whoo后拱辰享套装 气韵生水乳护肤礼盒7件套补水滋养","goods_price":620,"ref_price":1220,"id":99076,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/729f5aea-6ac7-4049-ab6e-99bd950e3ba9.jpg_middle.jpg","goods_salenum":8},{"goods_name":"OLAY玉兰油新生塑颜金纯修护精华乳去淡化细纹提拉紧致面官方","goods_price":160,"ref_price":279,"id":99086,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/a69e98ea-e3c7-4a6e-9263-50eb2d9cd74d.jpg_middle.jpg","goods_salenum":8},{"goods_name":"sk-ii sk2男士神仙水skii精华护肤套装保湿控油正品","goods_price":948,"ref_price":1450,"id":99140,"goods_main_photo":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32816/2019/01/25/9d1bdefb-cb60-42ab-bde8-b41eb942167c.jpg_middle.jpg","goods_salenum":1}]}
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
        private List<MiddleBannerListBean> middleBannerList;
        private List<GoodsListBean> goodsList;

        public List<MiddleBannerListBean> getMiddleBannerList() {
            return middleBannerList;
        }

        public void setMiddleBannerList(List<MiddleBannerListBean> middleBannerList) {
            this.middleBannerList = middleBannerList;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class MiddleBannerListBean {
            /**
             * extra : 99105
             * pic_url : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/1/2019/01/16/6a2b10d3-54a2-44d2-a537-15b35bac4b65.png
             * type : 0
             */

            private int extra;
            private String pic_url;
            private int type;

            public int getExtra() {
                return extra;
            }

            public void setExtra(int extra) {
                this.extra = extra;
            }

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class GoodsListBean {
            /**
             * goods_name : sk-ii sk2男士神仙水skii精华护肤套装保湿控油正品
             * goods_price : 780
             * ref_price : 1450
             * id : 99072
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/d58ae7c7-6996-41ab-81b3-696b9ccab3e9.jpg_middle.jpg
             * goods_salenum : 6
             */

            private String goods_name;
            private double goods_price;
            private double ref_price;
            private int id;
            private String goods_main_photo;
            private int goods_salenum;

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public double getRef_price() {
                return ref_price;
            }

            public void setRef_price(double ref_price) {
                this.ref_price = ref_price;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public int getGoods_salenum() {
                return goods_salenum;
            }

            public void setGoods_salenum(int goods_salenum) {
                this.goods_salenum = goods_salenum;
            }
        }
    }
}
