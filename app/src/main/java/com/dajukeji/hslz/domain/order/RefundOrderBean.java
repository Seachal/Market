package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 * 售后全部订单列表
 */

public class RefundOrderBean {


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
         * customerServiceList : [{"service":0,"store_id":1,"number":4,"gList":[{"icon":"upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg","spec_info":"颜色:白色 尺码:M ","count":4,"now_price":4.8,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子","before_price":178,"return_days":0}],"gr_id":100,"store_name":"平台自营店","return_status":0},{"service":0,"store_id":1,"number":3,"gList":[{"icon":"upload/store/1/2016/04/04/8683c4df-abcc-48f9-ba14-8c19c44c18be.jpg_small.jpg","spec_info":"颜色:白色 尺码:M ","count":3,"now_price":6,"goods_name":"欧洲站2016春装新品女装春季圆领中袖A字白色连衣裙子配送腰带","before_price":131,"return_days":0}],"gr_id":55,"store_name":"平台自营店","return_status":2}]
         * pages : 4
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<CustomerServiceListBean> customerServiceList;

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

        public List<CustomerServiceListBean> getCustomerServiceList() {
            return customerServiceList;
        }

        public void setCustomerServiceList(List<CustomerServiceListBean> customerServiceList) {
            this.customerServiceList = customerServiceList;
        }

        public static class CustomerServiceListBean {
            /**
             * service : 0
             * store_id : 1
             * number : 4
             * gList : [{"icon":"upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg","spec_info":"颜色:白色 尺码:M ","count":4,"now_price":4.8,"goods_name":"2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子","before_price":178,"return_days":0}]
             * gr_id : 100
             * store_name : 平台自营店
             * return_status : 0
             */

            private int service;
            private int logistics;
            private int store_id;
            private int number;
            private int gr_id;
            private String store_name;
            private int return_status;
            private List<GListBean> gList;
            private String chat_id;

            public String getChat_id() {
                return chat_id;
            }

            public void setChat_id(String chat_id) {
                this.chat_id = chat_id;
            }

            public int getLogistics() {
                return logistics;
            }

            public void setLogistics(int logistics) {
                this.logistics = logistics;
            }

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getGr_id() {
                return gr_id;
            }

            public void setGr_id(int gr_id) {
                this.gr_id = gr_id;
            }

            public String getStore_name() {
                return store_name;
            }

            public void setStore_name(String store_name) {
                this.store_name = store_name;
            }

            public int getReturn_status() {
                return return_status;
            }

            public void setReturn_status(int return_status) {
                this.return_status = return_status;
            }

            public List<GListBean> getGList() {
                return gList;
            }

            public void setGList(List<GListBean> gList) {
                this.gList = gList;
            }

            public static class GListBean {
                /**
                 * icon : upload/store/1/2016/04/04/c51ca320-b9d9-4bed-b64a-f4768557f57c.jpg_small.jpg
                 * spec_info : 颜色:白色 尺码:M
                 * count : 4
                 * now_price : 4.8
                 * goods_name : 2016韩版新品 女装绣花钉珠七分袖连衣裙女士打底A字裙子
                 * before_price : 178.0
                 * return_days : 0
                 */

                private String icon;
                private String spec_info;
                private int count;
                private double now_price;
                private String goods_name;
                private double before_price;
                private int return_days;

                public String getIcon() {
                    return icon;
                }

                public void setIcon(String icon) {
                    this.icon = icon;
                }

                public String getSpec_info() {
                    return spec_info;
                }

                public void setSpec_info(String spec_info) {
                    this.spec_info = spec_info;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public double getNow_price() {
                    return now_price;
                }

                public void setNow_price(double now_price) {
                    this.now_price = now_price;
                }

                public String getGoods_name() {
                    return goods_name;
                }

                public void setGoods_name(String goods_name) {
                    this.goods_name = goods_name;
                }

                public double getBefore_price() {
                    return before_price;
                }

                public void setBefore_price(double before_price) {
                    this.before_price = before_price;
                }

                public int getReturn_days() {
                    return return_days;
                }

                public void setReturn_days(int return_days) {
                    this.return_days = return_days;
                }
            }
        }
    }
}
