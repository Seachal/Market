package com.dajukeji.hslz.domain.freeOrder;

/**
 * Created by Administrator on 2018/2/7.
 */

public class BandPhoneBean {


    /**
     * status : 0
     * message : 绑定成功
     * content : {"have_free_order":1}
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
         * have_free_order : 1
         * ifFor : 0=需要刷新用户信息 1=不需要刷新用户信息
         */

        private int have_free_order;
        private int isFor;

        public int getHave_free_order() {
            return have_free_order;
        }

        public void setHave_free_order(int have_free_order) {
            this.have_free_order = have_free_order;
        }

        public int getIsFor() {
            return isFor;
        }

        public void setIsFor(int isFor) {
            this.isFor = isFor;
        }
    }
}
