package com.dajukeji.hslz.domain.javaBean;

/**
 * Created by Administrator on 2018/1/16.
 * 补贴分享链接
 */

public class SubsidyShareBean {


    /**
     * status : 0
     * message : 砍单链接
     * content : {"url":"http://localhost:8003/wemall/app/weixin/web/application.htm?id=1"}
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
         * url : http://localhost:8003/wemall/app/weixin/web/application.htm?id=1
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
