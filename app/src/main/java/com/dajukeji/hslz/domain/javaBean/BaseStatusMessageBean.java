package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:06
 * 作用:
 */
public class BaseStatusMessageBean {


    /**
     * status : 0
     * message : 验证码发送成功
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
