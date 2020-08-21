package com.dajukeji.hslz.domain.freeOrder;

/**
 * Created by Administrator on 2018/3/23.
 */

public class InviteeFreeOrderBean {

    /**
     * status : 0
     * message : 兑换成功
     * content : {}
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
    }
}
