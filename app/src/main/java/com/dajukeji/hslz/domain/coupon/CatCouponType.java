package com.dajukeji.hslz.domain.coupon;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 * 猫券目录
 */

public class CatCouponType {

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
        private List<DataListBean> dataList;

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * ICON : /uploadFiles/goodsType/88a609ba98d64ba2b7ace52fcd602463.jpg
             * CATID_LIST : 1
             * CREATER : luopeng
             * IS_PARENT : 0
             * NAME : 女装
             * CREATE_TIME : Sep 9, 2017 5:56:00 PM
             * UPDATER : admin
             * GOODS_TYPE_ID : 16
             * TB_FILTRATE_ID : 4
             * UPDATE_TIME : Jan 10, 2018 10:53:50 AM
             * ORDER_BY : 1
             * STATUS : 1
             * FLAG : 0
             * ENGLISH_NAME : Women's dres
             * GOODS_NUMBER : 100
             */

            private String ICON;
            private String CATID_LIST;
            private String CREATER;
            private int IS_PARENT;
            private String NAME;
            private String CREATE_TIME;
            private String UPDATER;
            private int GOODS_TYPE_ID;
            private int TB_FILTRATE_ID;
            private String UPDATE_TIME;
            private int ORDER_BY;
            private String STATUS;
            private String FLAG;
            private String ENGLISH_NAME;
            private int GOODS_NUMBER;

            public String getICON() {
                return ICON;
            }

            public void setICON(String ICON) {
                this.ICON = ICON;
            }

            public String getCATID_LIST() {
                return CATID_LIST;
            }

            public void setCATID_LIST(String CATID_LIST) {
                this.CATID_LIST = CATID_LIST;
            }

            public String getCREATER() {
                return CREATER;
            }

            public void setCREATER(String CREATER) {
                this.CREATER = CREATER;
            }

            public int getIS_PARENT() {
                return IS_PARENT;
            }

            public void setIS_PARENT(int IS_PARENT) {
                this.IS_PARENT = IS_PARENT;
            }

            public String getNAME() {
                return NAME;
            }

            public void setNAME(String NAME) {
                this.NAME = NAME;
            }

            public String getCREATE_TIME() {
                return CREATE_TIME;
            }

            public void setCREATE_TIME(String CREATE_TIME) {
                this.CREATE_TIME = CREATE_TIME;
            }

            public String getUPDATER() {
                return UPDATER;
            }

            public void setUPDATER(String UPDATER) {
                this.UPDATER = UPDATER;
            }

            public int getGOODS_TYPE_ID() {
                return GOODS_TYPE_ID;
            }

            public void setGOODS_TYPE_ID(int GOODS_TYPE_ID) {
                this.GOODS_TYPE_ID = GOODS_TYPE_ID;
            }

            public int getTB_FILTRATE_ID() {
                return TB_FILTRATE_ID;
            }

            public void setTB_FILTRATE_ID(int TB_FILTRATE_ID) {
                this.TB_FILTRATE_ID = TB_FILTRATE_ID;
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

            public String getSTATUS() {
                return STATUS;
            }

            public void setSTATUS(String STATUS) {
                this.STATUS = STATUS;
            }

            public String getFLAG() {
                return FLAG;
            }

            public void setFLAG(String FLAG) {
                this.FLAG = FLAG;
            }

            public String getENGLISH_NAME() {
                return ENGLISH_NAME;
            }

            public void setENGLISH_NAME(String ENGLISH_NAME) {
                this.ENGLISH_NAME = ENGLISH_NAME;
            }

            public int getGOODS_NUMBER() {
                return GOODS_NUMBER;
            }

            public void setGOODS_NUMBER(int GOODS_NUMBER) {
                this.GOODS_NUMBER = GOODS_NUMBER;
            }
        }
    }
}
