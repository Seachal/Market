package com.dajukeji.hslz.activity.mine;

public class PayalipayBean {

    /**
     * status : 0
     * message : 下单成功
     * content : {"alipaymessge":"alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016090800466518&biz_content=%7B%22body%22%3A%222%22%2C%22out_trade_no%22%3A%22201711031509698124103%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%224%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fspidermancsp.free.ngrok.cc%2Fwemall%2Fapp%2Fmall%2Fpay%2Falipayback.htm&sign=bPCEx2hBbUOWY1%2FjTe7Hi7nQC1c0zyVF9DRYwjXK3KxIGp78eMRJlW2x6v2VweCygKZakFBX5yArehXh0sUglJbsF%2FtAUCsjquUB41gJmHwbuJwcXzsZNnCs5oFiVmQWkc4P1VQQS2tx8QQVVh3EquvKsfz5eP7kNfzgQto5XFuYloaOC8%2BpSE0tECnK6t8Ck6AuS4zrCZ7%2BB8%2BAzPaZluSZgPOKHJdEljSqnXl2TVNV3SyzPzqO%2FmpdN6TSG7atuXNZlARg72t3cle53Ep8X6tE2kbEnNCc2DlGXal34qySfGdIiWs2V9Ucs%2B%2FfbaYaXcif%2Blc0DYqSVqswOegvHQ%3D%3D&sign_type=RSA2&timestamp=2017-11-03+16%3A35%3A24&version=1.0","paytype":"alipay","order_id":"229962"}
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
         * alipaymessge : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2016090800466518&biz_content=%7B%22body%22%3A%222%22%2C%22out_trade_no%22%3A%22201711031509698124103%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%224%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fspidermancsp.free.ngrok.cc%2Fwemall%2Fapp%2Fmall%2Fpay%2Falipayback.htm&sign=bPCEx2hBbUOWY1%2FjTe7Hi7nQC1c0zyVF9DRYwjXK3KxIGp78eMRJlW2x6v2VweCygKZakFBX5yArehXh0sUglJbsF%2FtAUCsjquUB41gJmHwbuJwcXzsZNnCs5oFiVmQWkc4P1VQQS2tx8QQVVh3EquvKsfz5eP7kNfzgQto5XFuYloaOC8%2BpSE0tECnK6t8Ck6AuS4zrCZ7%2BB8%2BAzPaZluSZgPOKHJdEljSqnXl2TVNV3SyzPzqO%2FmpdN6TSG7atuXNZlARg72t3cle53Ep8X6tE2kbEnNCc2DlGXal34qySfGdIiWs2V9Ucs%2B%2FfbaYaXcif%2Blc0DYqSVqswOegvHQ%3D%3D&sign_type=RSA2&timestamp=2017-11-03+16%3A35%3A24&version=1.0
         * paytype : alipay
         * order_id : 229962
         */

        private String alipaymessge;
        private String paytype;
        private String order_id;

        public String getAlipaymessge() {
            return alipaymessge;
        }

        public void setAlipaymessge(String alipaymessge) {
            this.alipaymessge = alipaymessge;
        }

        public String getPaytype() {
            return paytype;
        }

        public void setPaytype(String paytype) {
            this.paytype = paytype;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }
    }
}
