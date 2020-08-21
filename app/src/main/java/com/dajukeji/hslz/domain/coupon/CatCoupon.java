package com.dajukeji.hslz.domain.coupon;

import com.dajukeji.hslz.domain.base.BaseBean;

import java.util.List;

/**
 * Created by cdr on 2017/12/4.
 * 猫券列表
 */

public class CatCoupon extends BaseBean {

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
        private List<GoodsListBean> goodsList;

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public static class GoodsListBean {
            /**
             * COUPON_INFO : 300
             * COUPON_START_TIME : 2018-01-16 00:00:00
             * COUPON_ID : a737c268e8044a92bb07458c736e44b0
             * ZK_FINAL_PRICE : 790.00
             * USER_TYPE : B
             * NUM_IID : 547538872382
             * VOLUME : 28
             * CATEGORY : 7
             * COMMISSION_RATE : 25.60
             * COUPON_CLICK_URL : http://shop.m.taobao.com/shop/coupon.htm?sellerId=1061810132&activityId=a737c268e8044a92bb07458c736e44b0
             * SHOP_TITLE : odm旗舰店
             * COUPON_END_TIME : 2018-01-24 23:59:59
             * SELLER_ID : 9626
             * COUPON_REMAIN_COUNT : 10000
             * ITEM_DESCRIPTION : 防水耐摔，低调奢华，典雅内敛，经典蓝光镜面皮带腕表，简约休闲男士腕表，轻薄时尚优雅范乐高积木概念，方块表盘设计，矿物强化玻璃镜面，从不追随，只要原创，突破常规，极限之作
             * PICT_URL : http://img.alicdn.com/imgextra/i4/1061810132/TB2_I6IXeZkyKJjSszbXXblwFXa_!!1061810132.jpg
             * TBK_COUPON_ID : 4
             * COUPON_TOTAL_COUNT : 10000
             * TITLE : 香港ODM新款专柜手表个性休闲情侣表男女士手表创意石英手表DD166
             */

            private String COUPON_INFO;
            private String COUPON_START_TIME;
            private String COUPON_ID;
            private String ZK_FINAL_PRICE;
            private String USER_TYPE;
            private String NUM_IID;
            private String VOLUME;
            private String CATEGORY;
            private String COMMISSION_RATE;
            private String COUPON_CLICK_URL;
            private String SHOP_TITLE;
            private String COUPON_END_TIME;
            private String SELLER_ID;
            private String COUPON_REMAIN_COUNT;
            private String ITEM_DESCRIPTION;
            private String PICT_URL;
            private int TBK_COUPON_ID;
            private String COUPON_TOTAL_COUNT;
            private String TITLE;
            private String m_coupon_click_url; //二合一链接
            private String m_coupon_info; // 优惠券信息
            private String m_coupon_end_time; // 结束时间
            private String m_coupon_start_time; // 开始时间

            public String getM_coupon_click_url() {
                return m_coupon_click_url;
            }

            public void setM_coupon_click_url(String m_coupon_click_url) {
                this.m_coupon_click_url = m_coupon_click_url;
            }

            public String getM_coupon_info() {
                return m_coupon_info;
            }

            public void setM_coupon_info(String m_coupon_info) {
                this.m_coupon_info = m_coupon_info;
            }

            public String getM_coupon_end_time() {
                return m_coupon_end_time;
            }

            public void setM_coupon_end_time(String m_coupon_end_time) {
                this.m_coupon_end_time = m_coupon_end_time;
            }

            public String getM_coupon_start_time() {
                return m_coupon_start_time;
            }

            public void setM_coupon_start_time(String m_coupon_start_time) {
                this.m_coupon_start_time = m_coupon_start_time;
            }

            public String getCOUPON_INFO() {
                return COUPON_INFO;
            }

            public void setCOUPON_INFO(String COUPON_INFO) {
                this.COUPON_INFO = COUPON_INFO;
            }

            public String getCOUPON_START_TIME() {
                return COUPON_START_TIME;
            }

            public void setCOUPON_START_TIME(String COUPON_START_TIME) {
                this.COUPON_START_TIME = COUPON_START_TIME;
            }

            public String getCOUPON_ID() {
                return COUPON_ID;
            }

            public void setCOUPON_ID(String COUPON_ID) {
                this.COUPON_ID = COUPON_ID;
            }

            public String getZK_FINAL_PRICE() {
                return ZK_FINAL_PRICE;
            }

            public void setZK_FINAL_PRICE(String ZK_FINAL_PRICE) {
                this.ZK_FINAL_PRICE = ZK_FINAL_PRICE;
            }

            public String getUSER_TYPE() {
                return USER_TYPE;
            }

            public void setUSER_TYPE(String USER_TYPE) {
                this.USER_TYPE = USER_TYPE;
            }

            public String getNUM_IID() {
                return NUM_IID;
            }

            public void setNUM_IID(String NUM_IID) {
                this.NUM_IID = NUM_IID;
            }

            public String getVOLUME() {
                return VOLUME;
            }

            public void setVOLUME(String VOLUME) {
                this.VOLUME = VOLUME;
            }

            public String getCATEGORY() {
                return CATEGORY;
            }

            public void setCATEGORY(String CATEGORY) {
                this.CATEGORY = CATEGORY;
            }

            public String getCOMMISSION_RATE() {
                return COMMISSION_RATE;
            }

            public void setCOMMISSION_RATE(String COMMISSION_RATE) {
                this.COMMISSION_RATE = COMMISSION_RATE;
            }

            public String getCOUPON_CLICK_URL() {
                return COUPON_CLICK_URL;
            }

            public void setCOUPON_CLICK_URL(String COUPON_CLICK_URL) {
                this.COUPON_CLICK_URL = COUPON_CLICK_URL;
            }

            public String getSHOP_TITLE() {
                return SHOP_TITLE;
            }

            public void setSHOP_TITLE(String SHOP_TITLE) {
                this.SHOP_TITLE = SHOP_TITLE;
            }

            public String getCOUPON_END_TIME() {
                return COUPON_END_TIME;
            }

            public void setCOUPON_END_TIME(String COUPON_END_TIME) {
                this.COUPON_END_TIME = COUPON_END_TIME;
            }

            public String getSELLER_ID() {
                return SELLER_ID;
            }

            public void setSELLER_ID(String SELLER_ID) {
                this.SELLER_ID = SELLER_ID;
            }

            public String getCOUPON_REMAIN_COUNT() {
                return COUPON_REMAIN_COUNT;
            }

            public void setCOUPON_REMAIN_COUNT(String COUPON_REMAIN_COUNT) {
                this.COUPON_REMAIN_COUNT = COUPON_REMAIN_COUNT;
            }

            public String getITEM_DESCRIPTION() {
                return ITEM_DESCRIPTION;
            }

            public void setITEM_DESCRIPTION(String ITEM_DESCRIPTION) {
                this.ITEM_DESCRIPTION = ITEM_DESCRIPTION;
            }

            public String getPICT_URL() {
                return PICT_URL;
            }

            public void setPICT_URL(String PICT_URL) {
                this.PICT_URL = PICT_URL;
            }

            public int getTBK_COUPON_ID() {
                return TBK_COUPON_ID;
            }

            public void setTBK_COUPON_ID(int TBK_COUPON_ID) {
                this.TBK_COUPON_ID = TBK_COUPON_ID;
            }

            public String getCOUPON_TOTAL_COUNT() {
                return COUPON_TOTAL_COUNT;
            }

            public void setCOUPON_TOTAL_COUNT(String COUPON_TOTAL_COUNT) {
                this.COUPON_TOTAL_COUNT = COUPON_TOTAL_COUNT;
            }

            public String getTITLE() {
                return TITLE;
            }

            public void setTITLE(String TITLE) {
                this.TITLE = TITLE;
            }
        }
    }
}
