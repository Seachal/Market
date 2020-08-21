package com.dajukeji.hslz.domain.order;

/**
 * Created by Administrator on 2018/2/1.
 */

public class OrderStatusCountBean {

    /**
     * status : 0
     * message : 订单状态数量
     * content : {"wait_for_receive":0,"wait_for_pay":33,"wait_for_send":3,"wait_for_comment":9,"after_sale":4}
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
         * wait_for_receive : 0
         * wait_for_pay : 33
         * wait_for_send : 3
         * wait_for_comment : 9
         * after_sale : 4
         */

        private int wait_for_receive;
        private int wait_for_pay;
        private int wait_for_send;
        private int wait_for_comment;
        private int after_sale;
        private String platform_chat_id;

        public String getPlatform_chat_id() {
            return platform_chat_id;
        }

        public void setPlatform_chat_id(String platform_chat_id) {
            this.platform_chat_id = platform_chat_id;
        }

        public int getWait_for_receive() {
            return wait_for_receive;
        }

        public void setWait_for_receive(int wait_for_receive) {
            this.wait_for_receive = wait_for_receive;
        }

        public int getWait_for_pay() {
            return wait_for_pay;
        }

        public void setWait_for_pay(int wait_for_pay) {
            this.wait_for_pay = wait_for_pay;
        }

        public int getWait_for_send() {
            return wait_for_send;
        }

        public void setWait_for_send(int wait_for_send) {
            this.wait_for_send = wait_for_send;
        }

        public int getWait_for_comment() {
            return wait_for_comment;
        }

        public void setWait_for_comment(int wait_for_comment) {
            this.wait_for_comment = wait_for_comment;
        }

        public int getAfter_sale() {
            return after_sale;
        }

        public void setAfter_sale(int after_sale) {
            this.after_sale = after_sale;
        }
    }
}
