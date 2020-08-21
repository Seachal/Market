package com.dajukeji.hslz.domain;

/**
 * Created by Administrator on 2017/9/18.
 */

public class User {

    /**
     * status : 0
     * message : 绑定成功
     * content : {"token":"32806","nickName":"罗鹏","headimgurl":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","agencyId":"6fb576f9ad9c4c45a90b4f9a4175afd5"}
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
         * token : 32806
         * nickName : 罗鹏
         * headimgurl : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
         * agencyId : 6fb576f9ad9c4c45a90b4f9a4175afd5
         */

        private String token;
        private String nickName;
        private String headimgurl;
        private String phoneNumber;
        private String agencyId;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getHeadimgurl() {
            return headimgurl;
        }

        public void setHeadimgurl(String headimgurl) {
            this.headimgurl = headimgurl;
        }

        public String getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(String agencyId) {
            this.agencyId = agencyId;
        }
    }
}
