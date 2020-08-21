package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2017/12/5 0005.
 */

public class FiveWholesaleOrderListBean {

    /**
     * status : 1
     * message : 正在拼单
     * content : {"ws_order_list":[{"nick_name":"方丈","require_count":8,"headimg_url":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","left_time":516107437,"order_id":229522}]}
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
        private List<WsOrderListBean> ws_order_list;

        public List<WsOrderListBean> getWs_order_list() {
            return ws_order_list;
        }

        public void setWs_order_list(List<WsOrderListBean> ws_order_list) {
            this.ws_order_list = ws_order_list;
        }

        public static class WsOrderListBean {
            /**
             * nick_name : 方丈
             * require_count : 8
             * headimg_url : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
             * left_time : 516107437
             * order_id : 229522
             */

            private String nick_name;
            private int require_count;
            private String headimg_url;
            private int left_time;
            private int order_id;

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public int getRequire_count() {
                return require_count;
            }

            public void setRequire_count(int require_count) {
                this.require_count = require_count;
            }

            public String getHeadimg_url() {
                return headimg_url;
            }

            public void setHeadimg_url(String headimg_url) {
                this.headimg_url = headimg_url;
            }

            public int getLeft_time() {
                return left_time;
            }

            public void setLeft_time(int left_time) {
                this.left_time = left_time;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }
        }
    }
}
