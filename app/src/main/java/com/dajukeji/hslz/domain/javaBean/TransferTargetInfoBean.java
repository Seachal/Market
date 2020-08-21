package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/28 11:33
 * 作用:
 */
public class TransferTargetInfoBean {

    /**
     * status : 0
     * message : 成功
     * content : {"userName":"9999997.00000","trueName":"9999997.00000","user_id":99999}
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
         * userName : 9999997.00000
         * trueName : 9999997.00000
         * user_id : 99999
         */

        private String userName;
        private String trueName;
        private int user_id;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
