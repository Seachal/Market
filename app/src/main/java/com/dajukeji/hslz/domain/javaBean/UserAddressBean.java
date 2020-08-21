package com.dajukeji.hslz.domain.javaBean;

/**
 * Created by ${wangjiasheng} on 2017/12/12 0012.
 */

public class UserAddressBean {
    /**
     * status : 0
     * message : 地址信息
     * content : {"id":32800,"default_address":"1","area_id":4522542,"trueName":"乒乒乓乓","address":"辽宁省丹东市东港市","area_info":"来来来看见回话","mobile":"18226396850"}
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
         * id : 32800
         * default_address : 1
         * area_id : 4522542
         * trueName : 乒乒乓乓
         * address : 辽宁省丹东市东港市
         * area_info : 来来来看见回话
         * mobile : 18226396850
         */

        private long id;
        private String default_address;
        private long area_id;
        private String trueName;
        private String address;
        private String area_info;
        private String mobile;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getDefault_address() {
            return default_address;
        }

        public void setDefault_address(String default_address) {
            this.default_address = default_address;
        }

        public long getArea_id() {
            return area_id;
        }

        public void setArea_id(long area_id) {
            this.area_id = area_id;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getArea_info() {
            return area_info;
        }

        public void setArea_info(String area_info) {
            this.area_info = area_info;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

//    private String status;
//    private String message;
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public ContentBean getContent() {
//        return content;
//    }
//
//    public void setContent(ContentBean content) {
//        this.content = content;
//    }
//
//    private ContentBean content;
//
//    public static class ContentBean {
//        /**
//         * id : 32815
//         * default_address : 0
//         * area_id : 4522753
//         * trueName : 陈仕鹏
//         * address : 黑龙江省双鸭山市四方台区
//         * area_info : 44
//         * mobile : 3
//         */
//
//        private long id;
//        private String default_address;
//        private int area_id;
//        private String trueName;
//        private String address;
//        private String area_info;
//        private String mobile;
//
//        public long getId() {
//            return id;
//        }
//
//        public void setId(long id) {
//            this.id = id;
//        }
//
//        public String getDefault_address() {
//            return default_address;
//        }
//
//        public void setDefault_address(String default_address) {
//            this.default_address = default_address;
//        }
//
//        public int getArea_id() {
//            return area_id;
//        }
//
//        public void setArea_id(int area_id) {
//            this.area_id = area_id;
//        }
//
//        public String getTrueName() {
//            return trueName;
//        }
//
//        public void setTrueName(String trueName) {
//            this.trueName = trueName;
//        }
//
//        public String getAddress() {
//            return address;
//        }
//
//        public void setAddress(String address) {
//            this.address = address;
//        }
//
//        public String getArea_info() {
//            return area_info;
//        }
//
//        public void setArea_info(String area_info) {
//            this.area_info = area_info;
//        }
//
//        public String getMobile() {
//            return mobile;
//        }
//
//        public void setMobile(String mobile) {
//            this.mobile = mobile;
//        }
//    }
}
