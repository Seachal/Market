package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/24 13:29
 * 作用:
 */
public class RedPackageMoneyBean {

    /**
     * status : 0
     * message : 成功
     * content : {"money":0.005}
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
         * money : 0.005
         */

        private double money;

        private long record_id;

        public long getRecord_id(){
            return record_id;
        }

        public void setRecord_id(long record_id){
            this.record_id = record_id;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }
}
