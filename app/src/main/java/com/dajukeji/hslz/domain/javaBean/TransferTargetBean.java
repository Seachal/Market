package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/19 13:59
 * 作用: 转账目标
 */
public class TransferTargetBean {

    /**
     * code : 0
     * msg : null
     * count : 0
     * data : {"trueName":null,"userName":"wemall"}
     * currentPublicData : null
     */

    private int code;
    private String msg;
    private int count;
    private DataBean data;
    private Object currentPublicData;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getCurrentPublicData() {
        return currentPublicData;
    }

    public void setCurrentPublicData(Object currentPublicData) {
        this.currentPublicData = currentPublicData;
    }

    public static class DataBean {
        /**
         * trueName : null
         * userName : wemall
         */

        private String trueName;
        private String userName;

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
