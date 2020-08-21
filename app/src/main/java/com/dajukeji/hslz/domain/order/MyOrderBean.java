package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by cdr on 2017/12/11.
 * 普通订单
 */

public class MyOrderBean {

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
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         * orderList : [{"order_status_des":"交易关闭","order_status_second_des":"退款成功","payable":0,"goodsList":[{"spec_info":"颜色:黑色 尺码:XL ","price":23,"count":2,"original_price":23,"goods_name":"老人味风衣","return_days":0,"photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","return_status":"退款中"},{"spec_info":"颜色:紫色 尺码:XXS ","price":18,"count":2,"original_price":45,"goods_name":"学生味风衣","return_days":0,"photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","return_status":"退款成功"}],"ship_price":0,"store_id":32788,"order_id":229707,"total_count":4,"store_name":"时尚前沿专营店","order_status":47,"totalPrice":63.8,"addTime":"2018-01-19 21:59:16","deletable":0,"write_logistics_able":0}]
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<OrderListBean> orderList;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * order_status_des : 交易关闭
             * order_status_second_des : 退款成功
             * payable : 0
             * goodsList : [{"spec_info":"颜色:黑色 尺码:XL ","price":23,"count":2,"original_price":23,"goods_name":"老人味风衣","return_days":0,"photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","return_status":"退款中"},{"spec_info":"颜色:紫色 尺码:XXS ","price":18,"count":2,"original_price":45,"goods_name":"学生味风衣","return_days":0,"photo":"upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png","return_status":"退款成功"}]
             * ship_price : 0.0
             * store_id : 32788
             * order_id : 229707
             * total_count : 4
             * store_name : 时尚前沿专营店
             * order_status : 47
             * totalPrice : 63.8
             * addTime : 2018-01-19 21:59:16
             * deletable : 0
             * write_logistics_able : 0
             */

            private String order_status_des;
            private String order_status_second_des;
            private int payable;
            private double ship_price;
            private int store_id;
            private int order_id;
            private int total_count;
            private String store_name;
            private int order_status;
            private double totalPrice;
            private String addTime;
            private int deletable;
            private String chat_id;
            private int write_logistics_able;
            private String order_type;
            private List<GoodsListBean> goodsList;

            public String getOrder_type() {
                return order_type;
            }

            public void setOrder_type(String order_type) {
                this.order_type = order_type;
            }

            public String getChat_id() {
                return chat_id;
            }

            public void setChat_id(String chat_id) {
                this.chat_id = chat_id;
            }

            public String getOrder_status_des() {
                return order_status_des;
            }

            public void setOrder_status_des(String order_status_des) {
                this.order_status_des = order_status_des;
            }

            public String getOrder_status_second_des() {
                return order_status_second_des;
            }

            public void setOrder_status_second_des(String order_status_second_des) {
                this.order_status_second_des = order_status_second_des;
            }

            public int getPayable() {
                return payable;
            }

            public void setPayable(int payable) {
                this.payable = payable;
            }

            public double getShip_price() {
                return ship_price;
            }

            public void setShip_price(double ship_price) {
                this.ship_price = ship_price;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            public int getTotal_count() {
                return total_count;
            }

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public String getAddTime() {
                return addTime;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public int getDeletable() {
                return deletable;
            }

            public void setDeletable(int deletable) {
                this.deletable = deletable;
            }

            public int getWrite_logistics_able() {
                return write_logistics_able;
            }

            public void setWrite_logistics_able(int write_logistics_able) {
                this.write_logistics_able = write_logistics_able;
            }

            public List<GoodsListBean> getGoodsList() {
                return goodsList;
            }

            public void setGoodsList(List<GoodsListBean> goodsList) {
                this.goodsList = goodsList;
            }

            public static class GoodsListBean {
                /**
                 * spec_info : 颜色:黑色 尺码:XL
                 * price : 23.0
                 * count : 2
                 * original_price : 23.0
                 * goods_name : 老人味风衣
                 * return_days : 0
                 * photo : upload/store/32774/2017/11/26/27d2d1bd-3043-4d76-81d7-f9dad99c0dec.png
                 * return_status : 退款中
                 */

                private String spec_info;
                private double price;
                private int count;
                private double original_price;
                private String goods_name;
                private int return_days;
                private String photo;
                private String return_status;

                public String getSpec_info() {
                    return spec_info;
                }

                public void setSpec_info(String spec_info) {
                    this.spec_info = spec_info;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public double getOriginal_price() {
                    return original_price;
                }

                public void setOriginal_price(double original_price) {
                    this.original_price = original_price;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public int getReturn_days() {
                    return return_days;
                }

                public void setReturn_days(int return_days) {
                    this.return_days = return_days;
                }

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
                }

                public String getReturn_status() {
                    return return_status;
                }

                public void setReturn_status(String return_status) {
                    this.return_status = return_status;
                }
            }
        }
    }
}
