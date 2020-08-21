package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24 0024.
 */

public class IndexBean {


    /**
     * status : 0
     * message : 首页数据
     * content : {"middleBannerThreeList":[{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg","type":2},{"extra":32798,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/c66f2362-c5b4-4bbd-8f60-b7d3b807238c.jpg","type":1},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c11f03eb-1456-4c72-bc68-614fff8b9e7e.jpg","type":2}],"storeGoodsList":[[],[],[]],"middleBannerFourList":[{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg","type":2},{"extra":32798,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/c66f2362-c5b4-4bbd-8f60-b7d3b807238c.jpg","type":1},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c11f03eb-1456-4c72-bc68-614fff8b9e7e.jpg","type":2},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/ee0fd563-6e6e-45fa-92b6-d477743a5893.jpg","type":2}],"titleList":["女性健康常见的九种问题?","出生时像爸爸的孩子更健康"],"topBannerList":[{"extra":32798,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/c66f2362-c5b4-4bbd-8f60-b7d3b807238c.jpg","type":1},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c11f03eb-1456-4c72-bc68-614fff8b9e7e.jpg","type":2},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/ee0fd563-6e6e-45fa-92b6-d477743a5893.jpg","type":2},{"extra":98927,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/24/930666f0-9ac2-4a35-8926-0798614d1c93.png","type":0},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg","type":2}],"middleBannerTwoList":[{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg","type":2},{"extra":32798,"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/store/admin/2018/03/26/c66f2362-c5b4-4bbd-8f60-b7d3b807238c.jpg","type":1}],"hostGoodsList":[]}
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
        private String h5;
        private String h5Url;
        private int red;// 0=显示红包雨；1=不显示红包雨
        private List<MiddleBannerThreeListBean> middleBannerThreeList;
        private List<TopBannerListBean> topBannerList;
        private List<MiddleBannerTwoListBean> middleBannerTwoList;
        private List<HostGoodsListBean> hostGoodsList;
        private List<StoreGoodsListBean> storeGoodsList;
        private List<MiddleBannerFourListBean> middleBannerFourList;
        private List<String> titleList;

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public List<StoreGoodsListBean> getStoreGoodsList() {
            return storeGoodsList;
        }

        public String getH5() {
            return h5;
        }

        public void setH5(String h5) {
            this.h5 = h5;
        }

        public String getH5Url() {
            return h5Url;
        }

        public void setH5Url(String h5Url) {
            this.h5Url = h5Url;
        }

        public void setStoreGoodsList(List<StoreGoodsListBean> storeGoodsList) {
            this.storeGoodsList = storeGoodsList;
        }

        public List<MiddleBannerFourListBean> getMiddleBannerFourList() {
            return middleBannerFourList;
        }

        public void setMiddleBannerFourList(List<MiddleBannerFourListBean> middleBannerFourList) {
            this.middleBannerFourList = middleBannerFourList;
        }

        public List<String> getTitleList() {
            return titleList;
        }

        public void setTitleList(List<String> titleList) {
            this.titleList = titleList;
        }

        public List<TopBannerListBean> getTopBannerList() {
            return topBannerList;
        }

        public void setTopBannerList(List<TopBannerListBean> topBannerList) {
            this.topBannerList = topBannerList;
        }

        public List<HostGoodsListBean> getHostGoodsList() {
            return hostGoodsList;
        }

        public void setHostGoodsList(List<HostGoodsListBean> hostGoodsList) {
            this.hostGoodsList = hostGoodsList;
        }

        public static class StoreGoodsListBean {
            private List<GoodsListnE0Bean> goodsListnE0;
            private List<GoodsListnE1Bean> goodsListnE1;
            private List<GoodsListnE2Bean> goodsListnE2;
            private List<GoodsListnE3Bean> goodsListnE3;

            public List<GoodsListnE0Bean> getGoodsListnE0() {
                return goodsListnE0;
            }

            public void setGoodsListnE0(List<GoodsListnE0Bean> goodsListnE0) {
                this.goodsListnE0 = goodsListnE0;
            }

            public List<GoodsListnE1Bean> getGoodsListnE1() {
                return goodsListnE1;
            }

            public void setGoodsListnE1(List<GoodsListnE1Bean> goodsListnE1) {
                this.goodsListnE1 = goodsListnE1;
            }

            public List<GoodsListnE2Bean> getGoodsListnE2() {
                return goodsListnE2;
            }

            public void setGoodsListnE2(List<GoodsListnE2Bean> goodsListnE2) {
                this.goodsListnE2 = goodsListnE2;
            }

            public List<GoodsListnE3Bean> getGoodsListnE3() {
                return goodsListnE3;
            }

            public void setGoodsListnE3(List<GoodsListnE3Bean> goodsListnE3) {
                this.goodsListnE3 = goodsListnE3;
            }

            public static class GoodsListnE0Bean {
                /**
                 * goods_name : Romon罗蒙圆领套头修身卫衣男2018秋冬休闲上衣印花薄款
                 * goods_price : 100
                 * id : 99065
                 * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/17106a0e-fbde-4891-84ef-d62f0368f157.jpg_middle.jpg
                 * goods_salenum : 0
                 */

                private String goods_name;
                private double goods_price;
                private int id;
                private String goods_main_photo;
                private double goods_salenum;

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

                public double getGoods_salenum() {
                    return goods_salenum;
                }

                public void setGoods_salenum(double goods_salenum) {
                    this.goods_salenum = goods_salenum;
                }
            }

            public static class GoodsListnE1Bean {
                /**
                 * goods_name : vivo X23幻彩版水滴全面屏拍照超大广角智能4G限量版手
                 * goods_price : 1500
                 * id : 99067
                 * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/951ee2b6-af37-495a-9484-25a1394c79d5.jpg_middle.jpg
                 * goods_salenum : 0
                 */

                private String goods_name;
                private int goods_price;
                private int id;
                private String goods_main_photo;
                private int goods_salenum;

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(int goods_price) {
                    this.goods_price = goods_price;
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

            public static class GoodsListnE2Bean {
                /**
                 * goods_name : 雅诗兰黛粉底液 全新沁水粉底液30ml 补水保湿 防晒遮瑕
                 * goods_price : 260
                 * id : 99092
                 * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/7cbdf01c-f821-4121-a015-f08a8f89350f.jpg_middle.jpg
                 * goods_salenum : 0
                 */

                private String goods_name;
                private int goods_price;
                private int id;
                private String goods_main_photo;
                private int goods_salenum;

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getGoods_price() {
                    return goods_price;
                }

                public void setGoods_price(int goods_price) {
                    this.goods_price = goods_price;
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

            public static class GoodsListnE3Bean {
                /**
                 * goods_name : Romon罗蒙圆领套头修身卫衣男2018秋冬休闲上衣印花薄款
                 * goods_price : 100
                 * id : 99065
                 * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/18/17106a0e-fbde-4891-84ef-d62f0368f157.jpg_middle.jpg
                 * goods_salenum : 0
                 */

                private String goods_name;
                private double goods_price;
                private int id;
                private String goods_main_photo;
                private double goods_salenum;

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

                public double getGoods_salenum() {
                    return goods_salenum;
                }

                public void setGoods_salenum(double goods_salenum) {
                    this.goods_salenum = goods_salenum;
                }
            }
        }

        public static class MiddleBannerFourListBean {
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

        public static class TopBannerListBean {
            /**
             * extra : 99088
             * pic_url : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/21/e7d8e2d7-83e7-46a2-8edf-766465332a80.jpg
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

        public static class HostGoodsListBean {
            /**
             * goods_name : 阿道夫正品洗发水护发素套装轻柔滋润修护洗发乳液旗舰店官网
             * goods_price : 68
             * id : 99015
             * goods_main_photo : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/store/32815/2019/01/17/2c5fc0d2-b5de-4747-95d2-8bf5651b2b7a.jpg_middle.jpg
             * goods_salenum : 0
             */

            private String goods_name;
            private double goods_price;
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

        public List<MiddleBannerThreeListBean> getMiddleBannerThreeList() {
            return middleBannerThreeList;
        }

        public List<MiddleBannerTwoListBean> getMiddleBannerTwoList() {
            return middleBannerTwoList;
        }

        public void setMiddleBannerTwoList(List<MiddleBannerTwoListBean> middleBannerTwoList) {
            this.middleBannerTwoList = middleBannerTwoList;
        }

        public static class MiddleBannerThreeListBean {
            /**
             * pic_url : http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg
             * type : 2
             * extra : 32798
             */

            private String pic_url;
            private int type;
            private int extra;

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

            public int getExtra() {
                return extra;
            }

            public void setExtra(int extra) {
                this.extra = extra;
            }
        }

        public static class MiddleBannerTwoListBean {
            /**
             * pic_url : http://sjhs.oss-cn-shenzhen.aliyuncs.com/upload/brand/c86cc4e6-d481-4e16-8fc0-bd2f99245fc9.jpg
             * type : 2
             * extra : 32798
             */

            private String pic_url;
            private int type;
            private int extra;

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

            public int getExtra() {
                return extra;
            }

            public void setExtra(int extra) {
                this.extra = extra;
            }
        }
    }
}