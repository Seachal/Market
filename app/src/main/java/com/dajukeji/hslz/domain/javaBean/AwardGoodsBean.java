package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 * 中奖产品
 */

public class AwardGoodsBean {

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
         * awardList : [{"icon":"","time":"","price":4,"status":"0","id":98533,"goods_name":"酒行天下"},{"icon":"","time":"","price":4,"status":"0","id":98533,"goods_name":"酒行天下"}]
         * currentPage : 1
         * pageSize : 12
         * pages : 2
         */

        private int currentPage;
        private int pageSize;
        private int pages;
        private List<AwardListBean> awardList;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<AwardListBean> getAwardList() {
            return awardList;
        }

        public void setAwardList(List<AwardListBean> awardList) {
            this.awardList = awardList;
        }

        public static class AwardListBean {
            /**
             * icon :
             * time :
             * price : 4.0
             * status : 0
             * id : 98533
             * goods_name : 酒行天下
             */

            private String icon;
            private String time;
            private double price;
            private String status;
            private long id;
            private String goods_name;

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }
        }
    }
}
