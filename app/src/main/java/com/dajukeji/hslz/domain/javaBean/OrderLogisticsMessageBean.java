package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class OrderLogisticsMessageBean {


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

        private String company_name;
        private String status;
        private String ship_code;
        private String address;
        private List<GoodsListBean> goodsList;
        private List<InformactionBean> informaction;

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getShip_code() {
            return ship_code;
        }

        public void setShip_code(String ship_code) {
            this.ship_code = ship_code;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public List<GoodsListBean> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<GoodsListBean> goodsList) {
            this.goodsList = goodsList;
        }

        public List<InformactionBean> getInformaction() {
            return informaction;
        }

        public void setInformaction(List<InformactionBean> informaction) {
            this.informaction = informaction;
        }

        public static class GoodsListBean {
            /**
             * icon : upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg
             * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
             */

            private String icon;
            private String goods_name;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }

        public static class InformactionBean {
            /**
             * AcceptStation : 客户 签收人: 广大网点代签 已签收 感谢使用圆通速递，期待再次为您服务
             * AcceptTime : 2017-12-22 18:57:59
             */

            private String AcceptStation;
            private String AcceptTime;

            public String getAcceptStation() {
                return AcceptStation;
            }

            public void setAcceptStation(String AcceptStation) {
                this.AcceptStation = AcceptStation;
            }

            public String getAcceptTime() {
                return AcceptTime;
            }

            public void setAcceptTime(String AcceptTime) {
                this.AcceptTime = AcceptTime;
            }
        }
    }
}
