package com.dajukeji.hslz.domain;

import java.util.List;

/**
 * Created by Administrator on 2017/10/27.
 */

public class UserNoticeMessage {

    /**
     * pages : 1
     * pageSize : 12
     * currentPage : 1
     * orderList : [{"content":"马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251","icon":"upload/store/1/2016/04/06707f0bde-c7e6-47ed-880e-45029af157b0.jpg","title":"拼单失败","order_id":"3277720160318112141","addTime":"2017/11/01"}]
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
         * content : 马克华菲羽绒服 2015冬装轻薄羽绒外套 男士修身立领保暖 潮 7251
         * icon : upload/store/1/2016/04/06707f0bde-c7e6-47ed-880e-45029af157b0.jpg
         * title : 拼单失败
         * order_id : 3277720160318112141
         * addTime : 2017/11/01
         */

        private String content;
        private String icon;
        private String title;
        private String order_id;
        private String addTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
    }
}
