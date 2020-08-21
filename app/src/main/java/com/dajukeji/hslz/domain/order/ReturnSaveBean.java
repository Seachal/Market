package com.dajukeji.hslz.domain.order;

/**
 * Created by Administrator on 2018/1/31.
 */

public class ReturnSaveBean {
    /**
     * status : 0
     * message : 申请提交成功
     * content : {"gr_id":"2018010814132132805"}
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
         * gr_id : 2018010814132132805
         */

        private String gr_id;

        public String getGr_id() {
            return gr_id;
        }

        public void setGr_id(String gr_id) {
            this.gr_id = gr_id;
        }
    }
}
