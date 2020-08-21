package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/29 16:33
 * 作用:
 */
public class UserInfoBean {

    /**
     * status : 0
     * message :
     * content : {"trueName":"","payPassEmpty":0,"passwordIsEmpty":1,"address":"","areaName":"","gradeRegion":"","userName":"面包"}
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
         * trueName :
         * payPassEmpty : 0
         * passwordIsEmpty : 1
         * address :
         * areaName :
         * gradeRegion :
         * userName : 面包
         */

        private String trueName;
        private int payPassEmpty;
        private int passwordIsEmpty;
        private String address;
        private String areaName; // 用户级别
        private String gradeRegion;
        private String userName;

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getPayPassEmpty() {
            return payPassEmpty;
        }

        public void setPayPassEmpty(int payPassEmpty) {
            this.payPassEmpty = payPassEmpty;
        }

        public int getPasswordIsEmpty() {
            return passwordIsEmpty;
        }

        public void setPasswordIsEmpty(int passwordIsEmpty) {
            this.passwordIsEmpty = passwordIsEmpty;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getGradeRegion() {
            return gradeRegion;
        }

        public void setGradeRegion(String gradeRegion) {
            this.gradeRegion = gradeRegion;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
