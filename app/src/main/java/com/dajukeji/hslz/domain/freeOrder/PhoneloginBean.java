package com.dajukeji.hslz.domain.freeOrder;

public class PhoneloginBean {

    /**
     * status : 0
     * message : 登录成功
     * content : {"token":"dfwokckeklewdd","nickName":"Spiderman","headimgurl":"dfmowi.jpg","phoneNumber":"13790445950","agencyId":"sjdf3904jgk34m20dsf","sex":"男","chatId":"syb_32843","chatSig":"212","userId":32834}
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
         * token : dfwokckeklewdd
         * nickName : Spiderman
         * headimgurl : dfmowi.jpg
         * phoneNumber : 13790445950
         * agencyId : sjdf3904jgk34m20dsf
         * sex : 男
         * chatId : syb_32843
         * chatSig : 212
         * userId : 32834
         */

        private String token;
        private String nickName;
        private String headimgurl;
        private String phoneNumber;
        private String agencyId;
        private String sex;
        private String chatId;
        private String chatSig;
        private int userId;

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

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAgencyId() {
            return agencyId;
        }

        public void setAgencyId(String agencyId) {
            this.agencyId = agencyId;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getChatId() {
            return chatId;
        }

        public void setChatId(String chatId) {
            this.chatId = chatId;
        }

        public String getChatSig() {
            return chatSig;
        }

        public void setChatSig(String chatSig) {
            this.chatSig = chatSig;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
