package com.dajukeji.hslz.domain.coupon;

import java.util.List;

/**
 * Created by Administrator on 2018/1/5.
 * 京券类目
 */

public class JDCouponType {

    /**
     * status : 0
     * message : 二合一转链
     * content : {"goodsType":[{"ICON":"/uploadFiles/goodsType/54430dc9c8434be0bfd18b618579d138.jpg","CREATER":"admin","NAME":"外套","CATID_ID":"123456","CREATE_TIME":"Jan 3, 2018 8:48:00 PM","JD_FILTRATE_CONDITION_ID":6,"UPDATER":"admin","UPDATE_TIME":"Jan 3, 2018 8:48:45 PM","ORDER_BY":2,"STATUS":1,"JDGOODS_TYPE_ID":13,"ENGLISH_NAME":"231"},{"CREATER":"admin","NAME":"女装","CATID_ID":"1","CREATE_TIME":"Jan 5, 2018 11:38:00 AM","JD_FILTRATE_CONDITION_ID":6,"ORDER_BY":3,"STATUS":1,"JDGOODS_TYPE_ID":14,"ENGLISH_NAME":"woman"},{"CREATER":"admin","NAME":"京东","CATID_ID":"123213","CREATE_TIME":"Jan 3, 2018 8:48:00 PM","JD_FILTRATE_CONDITION_ID":3,"ORDER_BY":1,"STATUS":1,"JDGOODS_TYPE_ID":12,"ENGLISH_NAME":"jingd"}]}
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
        private List<GoodsTypeBean> goodsType;

        public List<GoodsTypeBean> getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(List<GoodsTypeBean> goodsType) {
            this.goodsType = goodsType;
        }

        public static class GoodsTypeBean {
            /**
             * ICON : /uploadFiles/goodsType/54430dc9c8434be0bfd18b618579d138.jpg
             * CREATER : admin
             * NAME : 外套
             * CATID_ID : 123456
             * CREATE_TIME : Jan 3, 2018 8:48:00 PM
             * JD_FILTRATE_CONDITION_ID : 6
             * UPDATER : admin
             * UPDATE_TIME : Jan 3, 2018 8:48:45 PM
             * ORDER_BY : 2
             * STATUS : 1
             * JDGOODS_TYPE_ID : 13
             * ENGLISH_NAME : 231
             */

            private String ICON;
            private String CREATER;
            private String NAME;
            private String CATID_ID;
            private String CREATE_TIME;
            private int JD_FILTRATE_CONDITION_ID;
            private String UPDATER;
            private String UPDATE_TIME;
            private int ORDER_BY;
            private int STATUS;
            private int JDGOODS_TYPE_ID;
            private String ENGLISH_NAME;

            public String getICON() {
                return ICON;
            }

            public void setICON(String ICON) {
                this.ICON = ICON;
            }

            public String getCREATER() {
                return CREATER;
            }

            public void setCREATER(String CREATER) {
                this.CREATER = CREATER;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getCATID_ID() {
                return CATID_ID;
            }

            public void setCATID_ID(String CATID_ID) {
                this.CATID_ID = CATID_ID;
            }

            public String getCREATE_TIME() {
                return CREATE_TIME;
            }

            public void setCREATE_TIME(String CREATE_TIME) {
                this.CREATE_TIME = CREATE_TIME;
            }

            public int getJD_FILTRATE_CONDITION_ID() {
                return JD_FILTRATE_CONDITION_ID;
            }

            public void setJD_FILTRATE_CONDITION_ID(int JD_FILTRATE_CONDITION_ID) {
                this.JD_FILTRATE_CONDITION_ID = JD_FILTRATE_CONDITION_ID;
            }

            public String getUPDATER() {
                return UPDATER;
            }

            public void setUPDATER(String UPDATER) {
                this.UPDATER = UPDATER;
            }

            public String getUPDATE_TIME() {
                return UPDATE_TIME;
            }

            public void setUPDATE_TIME(String UPDATE_TIME) {
                this.UPDATE_TIME = UPDATE_TIME;
            }

            public int getORDER_BY() {
                return ORDER_BY;
            }

            public void setORDER_BY(int ORDER_BY) {
                this.ORDER_BY = ORDER_BY;
            }

            public int getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(int STATUS) {
                this.STATUS = STATUS;
            }

            public int getJDGOODS_TYPE_ID() {
                return JDGOODS_TYPE_ID;
            }

            public void setJDGOODS_TYPE_ID(int JDGOODS_TYPE_ID) {
                this.JDGOODS_TYPE_ID = JDGOODS_TYPE_ID;
            }

            public String getENGLISH_NAME() {
                return ENGLISH_NAME;
            }

            public void setENGLISH_NAME(String ENGLISH_NAME) {
                this.ENGLISH_NAME = ENGLISH_NAME;
            }
        }
    }
}
