package com.dajukeji.hslz.domain.freeOrder;

import java.util.List;

/**
 * Created by Administrator on 2017/12/16.
 */

public class MeFreeOrderBean {

    /**
     * status : 0
     * message : 我的免单
     * content : {"pages":1,"pageSize":12,"currentPage":1,"orderList":[{"goods_main_photo":"upload/store/32774/2017/12/16/ba33417f-486e-4d0d-908a-282c2391ddbe.png","free_order_id":2,"status_description":"还差6人","status":"being","lack_number":6,"goods_name":"一款保暖棉鞋。你值得拥有","need_number":6,"invalid_time":"12月17日 17:23"},{"goods_main_photo":"upload/store/32774/2017/12/16/ba33417f-486e-4d0d-908a-282c2391ddbe.png","free_order_id":1,"status_description":"还差6人","status":"being","lack_number":6,"goods_name":"一款保暖棉鞋。你值得拥有","need_number":6,"invalid_time":"12月17日 17:22"}]}
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
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         * orderList : [{"goods_main_photo":"upload/store/32774/2017/12/16/ba33417f-486e-4d0d-908a-282c2391ddbe.png","free_order_id":2,"status_description":"还差6人","status":"being","lack_number":6,"goods_name":"一款保暖棉鞋。你值得拥有","need_number":6,"invalid_time":"12月17日 17:23"},{"goods_main_photo":"upload/store/32774/2017/12/16/ba33417f-486e-4d0d-908a-282c2391ddbe.png","free_order_id":1,"status_description":"还差6人","status":"being","lack_number":6,"goods_name":"一款保暖棉鞋。你值得拥有","need_number":6,"invalid_time":"12月17日 17:22"}]
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
             * goods_main_photo : upload/store/32774/2017/12/16/ba33417f-486e-4d0d-908a-282c2391ddbe.png
             * free_order_id : 2
             * status_description : 还差6人
             * status : being
             * lack_number : 6
             * goods_name : 一款保暖棉鞋。你值得拥有
             * need_number : 6
             * invalid_time : 12月17日 17:23
             */

            private String goods_main_photo;
            private long free_order_id;
            private String status_description;
            private String status;
            private int lack_number;
            private String goods_name;
            private int need_number;
            private String invalid_time;
            private int deletable;

            public int getDeletable() {
                return deletable;
            }

            public void setDeletable(int deletable) {
                this.deletable = deletable;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public long getFree_order_id() {
                return free_order_id;
            }

            public void setFree_order_id(long free_order_id) {
                this.free_order_id = free_order_id;
            }

            public String getStatus_description() {
                return status_description;
            }

            public void setStatus_description(String status_description) {
                this.status_description = status_description;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public int getLack_number() {
                return lack_number;
            }

            public void setLack_number(int lack_number) {
                this.lack_number = lack_number;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public int getNeed_number() {
                return need_number;
            }

            public void setNeed_number(int need_number) {
                this.need_number = need_number;
            }

            public String getInvalid_time() {
                return invalid_time;
            }

            public void setInvalid_time(String invalid_time) {
                this.invalid_time = invalid_time;
            }
        }
    }
}
