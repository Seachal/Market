package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2017/12/12 0012.
 */

public class UserAddressListBean {
    /**
     * status : 0
     * message : 地址列表
     * content : {"addressList":[{"id":32815,"default_address":"0","area_id":4522753,"trueName":"陈仕鹏","address":"黑龙江省双鸭山市四方台区","area_info":"44","mobile":"3"},{"id":32814,"default_address":"0","area_id":4524264,"trueName":"陈仕鹏","address":"广东省清远市佛冈县","area_info":"123","mobile":"123999"},{"id":32800,"default_address":"1","area_id":4522542,"trueName":"乒乒乓乓","address":"辽宁省丹东市东港市","area_info":"来来来看见回话","mobile":"18226396850"},{"id":32799,"default_address":"0","area_id":4522406,"trueName":"呸呸呸","address":"内蒙古自治区通辽市开鲁县","area_info":"啦啦啦啦啦了","mobile":"666"},{"id":32798,"default_address":"0","area_id":4522099,"trueName":"lll","address":"河北省邢台市临城县","area_info":"bhh","mobile":"855555"}]}
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
        private List<UserAddressBean.ContentBean> addressList;

        public List<UserAddressBean.ContentBean> getAddressList() {
            return addressList;
        }

        public void setAddressList(List<UserAddressBean.ContentBean> addressList) {
            this.addressList = addressList;
        }
    }
}
