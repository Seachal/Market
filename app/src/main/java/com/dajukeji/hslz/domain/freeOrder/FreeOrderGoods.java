package com.dajukeji.hslz.domain.freeOrder;

/**
 * Created by Administrator on 2017/12/16.
 */

public class FreeOrderGoods {

    /**
     * status : 0
     * message : 二维码
     * content : {"wx_nickname":"方丈","wx_head_img":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","market_price":123,"goods_name":"男装、时尚女装>男装>时尚T恤","qrcode_image_content":"http://weixin.qq.com/q/02-E9GoT26fM01tuHK1q1z","qrcode_image_url":"https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEe8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLUU5R29UMjZmTTAxdHVISzFxMXoAAgReXkdaAwQAjScA","main_photo":""}
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
         * wx_nickname : 方丈
         * wx_head_img : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
         * market_price : 123.0
         * goods_name : 男装、时尚女装>男装>时尚T恤
         * qrcode_image_content : http://weixin.qq.com/q/02-E9GoT26fM01tuHK1q1z
         * qrcode_image_url : https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEe8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLUU5R29UMjZmTTAxdHVISzFxMXoAAgReXkdaAwQAjScA
         * main_photo :
         */

        private String wx_nickname;
        private String wx_head_img;
        private double market_price;
        private String goods_name;
        private String qrcode_image_content;
        private String qrcode_image_url;
        private String main_photo;

        public String getWx_nickname() {
            return wx_nickname;
        }

        public void setWx_nickname(String wx_nickname) {
            this.wx_nickname = wx_nickname;
        }

        public String getWx_head_img() {
            return wx_head_img;
        }

        public void setWx_head_img(String wx_head_img) {
            this.wx_head_img = wx_head_img;
        }

        public double getMarket_price() {
            return market_price;
        }

        public void setMarket_price(double market_price) {
            this.market_price = market_price;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getQrcode_image_content() {
            return qrcode_image_content;
        }

        public void setQrcode_image_content(String qrcode_image_content) {
            this.qrcode_image_content = qrcode_image_content;
        }

        public String getQrcode_image_url() {
            return qrcode_image_url;
        }

        public void setQrcode_image_url(String qrcode_image_url) {
            this.qrcode_image_url = qrcode_image_url;
        }

        public String getMain_photo() {
            return main_photo;
        }

        public void setMain_photo(String main_photo) {
            this.main_photo = main_photo;
        }
    }
}
