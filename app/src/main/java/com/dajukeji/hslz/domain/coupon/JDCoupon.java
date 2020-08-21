package com.dajukeji.hslz.domain.coupon;

import java.util.List;

/**
 * Created by Administrator on 2018/1/4.
 * 京券列表
 */

public class JDCoupon {

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
         * data : [{"goods_id":"17575678930","goods_name":"Qiloo奇鹭春秋季中大童圆领长袖","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t10948/280/735811958/80112/e6a6bae0/59d728a8Ndcbfa0ff.jpg","goods_price":160.65,"goods_content":"奇鹭春秋男女童套头卫衣圆领长袖，赠运费险","goods_link":"http://item.jd.com/17575678930.html","discount_price":61,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=08badcba6ccd4754a8e1fd0558542392&roleId=10475444&to=item.jd.com/17575678930.html","discount_start":"2018-02-08","discount_end":"2018-02-12","coupon_price":99.65,"goods_type":4,"commission":20},{"goods_id":"10501639310","goods_name":"奇鹭春季女童多彩果冻休闲鞋","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t8416/167/972153/235079/abd32014/599e8e0cNc70a27a2.jpg","goods_price":109,"goods_content":"多彩/休闲/时尚/糖果色，31-37可选！下单赠袜子","goods_link":"http://item.jd.com/10501639310.html","discount_price":30,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=acdc215ff3154a1093231bb7e15d5a9d&roleId=10458136&to=qiloo.jd.com","discount_start":"2018-02-07","discount_end":"2018-02-26","coupon_price":79,"goods_type":4,"commission":20},{"goods_id":"16290243985","goods_name":"奇鹭春秋款男童圆领长袖T恤","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t8926/258/841018858/88790/11d2acac/59afabdcN99a0b687.jpg","goods_price":109.65,"goods_content":"春秋季儿童T恤 圆领细致走线，优质面料，细致袖口","goods_link":"http://item.jd.com/16290243985.html","discount_price":40,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=aa73ba0f99924aab94f1ebd4d5dc9f8d&roleId=10475259&to=item.jd.com/16290243985.html","discount_start":"2018-02-08","discount_end":"2018-02-12","coupon_price":69.65,"goods_type":4,"commission":20},{"goods_id":"17575068065","goods_name":"奇鹭卫衣春秋季中小童圆领长袖 ","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t10294/62/742370920/103071/4d160558/59d7254dN862cf764.jpg","goods_price":160.65,"goods_content":"优质面料，精致做工，卡通图案，赠运费险！","goods_link":"http://item.jd.com/17575068065.html","discount_price":71,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=2d1e1f05b98d4c40b818a9104e25ca4c&roleId=10475138&to=item.jd.com/17575068065.html","discount_start":"2018-02-08","discount_end":"2018-02-12","coupon_price":89.65,"goods_type":4,"commission":20},{"goods_id":"10962458001","goods_name":"奇鹭 秋冬男女童保暖气垫防滑耐磨童鞋 ","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t3574/242/1398185526/105877/e3d2612a/58242787N3ea91551.jpg","goods_price":119,"goods_content":"奇鹭秋冬男女童运动鞋，简约时尚高耐PU，优质气垫底防撞鞋头","goods_link":"http://item.jd.com/10962458001.html","discount_price":40,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=440f69185cfb4b76a2b4e1693c2322de&roleId=10474623&to=item.jd.com/10962458001.html","discount_start":"2018-02-08","discount_end":"2018-02-12","coupon_price":79,"goods_type":4,"commission":20},{"goods_id":1043891357,"goods_name":"otbaby500ml洗发沐浴","goods_img":"http://img.jingtuitui.com/b87712018020214074379.jpg","goods_price":69,"goods_content":"otbaby 多效柔嫩洗发沐浴露儿童洗发沐浴露，洗发 沐浴=1瓶搞定\n","goods_link":"http://item.jd.com/1043891357.html","discount_price":20,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=ffe83c8c156f43f8a2a82eec8cc5edfd&roleId=10386168&to=otbaby.jd.com","discount_start":"2018-01-31","discount_end":"2018-02-28","coupon_price":49,"goods_type":4,"commission":25},{"goods_id":"22677272568","goods_name":"【京东配送】撕不烂翻翻书14册","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t14062/28/2255869367/376847/f297986/5a386e34Nd26ea94c.jpg","goods_price":89.8,"goods_content":"培养孩子情商，做更好的自己，0-1岁-2-3岁婴幼儿书籍睡前故事书 ，和宝宝一起快乐成长~","goods_link":"http://item.jd.com/22677272568.html","discount_price":50,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=a687355e200b4c41b9f0715248e25235&roleId=10410799&to=ydgts.jd.com","discount_start":"2018-02-01","discount_end":"2018-02-15","coupon_price":39.8,"goods_type":4,"commission":20},{"goods_id":"20822692594","goods_name":"钙软糖儿童补钙健康水果味咀嚼软糖","goods_img":"http://img.jingtuitui.com/db249201802080955397223.jpg","goods_price":48,"goods_content":"国家食品监督管批准，蓝帽子产品，妈妈放心的钙，水果味软糖，营养无添加，呵护宝宝骨骼牙齿健康成长","goods_link":"http://item.jd.com/20822692594.html","discount_price":15,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=bf71952699944ba9805056bd32491725&roleId=10469949&to=leli.jd.com","discount_start":"2018-02-08","discount_end":"2018-02-12","coupon_price":33,"goods_type":4,"commission":20},{"goods_id":"19198775151","goods_name":" 班杰威尔（Banjvall） 婴儿衣服新生儿礼盒","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t2707/303/4187719596/259058/98ae3437/57af35abN4437e225.jpg","goods_price":198,"goods_content":"班杰威尔（Banjvall） 婴儿衣服新生儿礼盒 购买就送精美礼品袋","goods_link":"http://item.jd.com/19198775151.html","discount_price":70,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=4ca25e3d91cb40dc95f703326dc925dd&roleId=10469005&to=item.jd.com/19198775151.html","discount_start":"2018-02-07","discount_end":"2018-02-27","coupon_price":128,"goods_type":4,"commission":20},{"goods_id":1427458538,"goods_name":"纯棉婴儿衣服礼盒套装加厚新生儿内衣套装","goods_img":"http://img14.360buyimg.com/imgzone/jfs/t724/342/212511746/268031/42ee4e87/5493ff1fN390f50f1.jpg","goods_price":149,"goods_content":"纯棉婴儿衣服礼盒套装加厚新生儿男女宝宝18件套保暖内衣领卷立减50  购买送精美礼品袋","goods_link":"http://item.jd.com/1427458538.html","discount_price":50,"discount_link":"http://coupon.m.jd.com/coupons/show.action?key=b1dc7c0a13ae42afbed605686e24ec8c&roleId=10468991&to=item.jd.com/1427458538.html","discount_start":"2018-02-07","discount_end":"2018-02-27","coupon_price":99,"goods_type":4,"commission":20}]
         * current_page : 1
         * total_page : 27
         */

        private int current_page;
        private int total_page;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * goods_id : 17575678930
             * goods_name : Qiloo奇鹭春秋季中大童圆领长袖
             * goods_img : http://img14.360buyimg.com/imgzone/jfs/t10948/280/735811958/80112/e6a6bae0/59d728a8Ndcbfa0ff.jpg
             * goods_price : 160.65
             * goods_content : 奇鹭春秋男女童套头卫衣圆领长袖，赠运费险
             * goods_link : http://item.jd.com/17575678930.html
             * discount_price : 61
             * discount_link : http://coupon.m.jd.com/coupons/show.action?key=08badcba6ccd4754a8e1fd0558542392&roleId=10475444&to=item.jd.com/17575678930.html
             * discount_start : 2018-02-08
             * discount_end : 2018-02-12
             * coupon_price : 99.65
             * goods_type : 4
             * commission : 20
             */

            private String goods_id;
            private String goods_name;
            private String goods_img;
            private double goods_price;
            private String goods_content;
            private String goods_link;
            private double discount_price;
            private String discount_link;
            private String discount_start;
            private String discount_end;
            private double coupon_price;
            private int goods_type;
            private double commission;
            private String link;

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getGoods_img() {
                return goods_img;
            }

            public void setGoods_img(String goods_img) {
                this.goods_img = goods_img;
            }

            public double getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(double goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_content() {
                return goods_content;
            }

            public void setGoods_content(String goods_content) {
                this.goods_content = goods_content;
            }

            public String getGoods_link() {
                return goods_link;
            }

            public void setGoods_link(String goods_link) {
                this.goods_link = goods_link;
            }

            public double getDiscount_price() {
                return discount_price;
            }

            public void setDiscount_price(double discount_price) {
                this.discount_price = discount_price;
            }

            public String getDiscount_link() {
                return discount_link;
            }

            public void setDiscount_link(String discount_link) {
                this.discount_link = discount_link;
            }

            public String getDiscount_start() {
                return discount_start;
            }

            public void setDiscount_start(String discount_start) {
                this.discount_start = discount_start;
            }

            public String getDiscount_end() {
                return discount_end;
            }

            public void setDiscount_end(String discount_end) {
                this.discount_end = discount_end;
            }

            public double getCoupon_price() {
                return coupon_price;
            }

            public void setCoupon_price(double coupon_price) {
                this.coupon_price = coupon_price;
            }

            public int getGoods_type() {
                return goods_type;
            }

            public void setGoods_type(int goods_type) {
                this.goods_type = goods_type;
            }

            public double getCommission() {
                return commission;
            }

            public void setCommission(double commission) {
                this.commission = commission;
            }
        }
    }
}
