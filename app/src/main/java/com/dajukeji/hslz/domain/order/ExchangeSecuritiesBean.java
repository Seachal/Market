package com.dajukeji.hslz.domain.order;

/**
 * 作者: Li_ke
 * 日期: 2019/1/22 14:32
 * 作用:
 */
public class ExchangeSecuritiesBean {

    /**
     * status : 0
     * message : 兑换产品成功
     * content : {"convert_order_id":231879,"convert_order_no":"3317520190122143103"}
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
         * convert_order_id : 231879
         * convert_order_no : 3317520190122143103
         */

        private int convert_order_id;
        private String convert_order_no;

        public int getConvert_order_id() {
            return convert_order_id;
        }

        public void setConvert_order_id(int convert_order_id) {
            this.convert_order_id = convert_order_id;
        }

        public String getConvert_order_no() {
            return convert_order_no;
        }

        public void setConvert_order_no(String convert_order_no) {
            this.convert_order_no = convert_order_no;
        }
    }
}
