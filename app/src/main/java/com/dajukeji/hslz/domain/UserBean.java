package com.dajukeji.hslz.domain;

import java.util.List;

public class UserBean {

    /**
     * status : 0
     * message : 地址列表
     * content : {"addressList":[{"id":32775,"default_address":"0","area_id":4524162,"trueName":"陈仕鹏","address":"广东省深圳市宝安区","area_info":"西乡XXXXX","mobile":"123"}]}
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
        private List<AddressListBean> addressList;

        public List<AddressListBean> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<AddressListBean> addressList) {
            this.addressList = addressList;
        }

        public static class AddressListBean {
            /**
             * id : 32775
             * default_address : 0
             * area_id : 4524162
             * trueName : 陈仕鹏
             * address : 广东省深圳市宝安区
             * area_info : 西乡XXXXX
             * mobile : 123
             */

            private int id;
            private String default_address;
            private int area_id;
            private String trueName;
            private String address;
            private String area_info;
            private String mobile;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDefault_address() {
                return default_address;
            }

            public void setDefault_address(String default_address) {
                this.default_address = default_address;
            }

            public int getArea_id() {
                return area_id;
            }

            public void setArea_id(int area_id) {
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
    }
}
