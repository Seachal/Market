package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/9 0009.
 */

public class SystemMessageBean {

    /**
     * message : 成功
     * content : {"number":12,"pages":3,"messageList":[{"goods_name":"甲由 A5多媒体台式机电脑小音箱笔记本迷你低音炮2.0音响USB影响","addTime":"2018-02-08 12:00:00","already_write":0,"icon":"upload/store/1/2016/04/04/3c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg_small.jpg","remark":"订单号：3280620180201101138","id":431,"title":"卖家已同意退货","order_id":"3280620180201101138","content":"1"},{"goods_name":"正常产品1","LogisticCode":"xx2523","addTime":"2018-02-03 22:30:00","already_write":0,"icon":"upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg","remark":"订单号：3280620180130211826","id":405,"title":"卖家已同意退货","order_id":"3280620180130211826","content":"1"}],"pageSize":12,"currentPage":1}
     * status : 0
     */
    private String message;
    private ContentEntity content;
    private String status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public ContentEntity getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public class ContentEntity {
        /**
         * number : 12
         * pages : 3
         * messageList : [{"goods_name":"甲由 A5多媒体台式机电脑小音箱笔记本迷你低音炮2.0音响USB影响","addTime":"2018-02-08 12:00:00","already_write":0,"icon":"upload/store/1/2016/04/04/3c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg_small.jpg","remark":"订单号：3280620180201101138","id":431,"title":"卖家已同意退货","order_id":"3280620180201101138","content":"1"},{"goods_name":"正常产品1","LogisticCode":"xx2523","addTime":"2018-02-03 22:30:00","already_write":0,"icon":"upload/store/32788/2018/01/29/3ce19e22-fa10-4efe-af26-4ef18d045036.jpg_small.jpg","remark":"订单号：3280620180130211826","id":405,"title":"卖家已同意退货","order_id":"3280620180130211826","content":"1"}]
         * pageSize : 12
         * currentPage : 1
         */
        private int number;
        private int pages;
        private List<MessageListEntity> messageList;
        private int pageSize;
        private int currentPage;

        public void setNumber(int number) {
            this.number = number;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public void setMessageList(List<MessageListEntity> messageList) {
            this.messageList = messageList;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getNumber() {
            return number;
        }

        public int getPages() {
            return pages;
        }

        public List<MessageListEntity> getMessageList() {
            return messageList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public class MessageListEntity {
            /**
             * goods_name : 甲由 A5多媒体台式机电脑小音箱笔记本迷你低音炮2.0音响USB影响
             * addTime : 2018-02-08 12:00:00
             * already_write : 0
             * icon : upload/store/1/2016/04/04/3c472df1-3280-4cf5-b6f3-c98c66cb136a.jpg_small.jpg
             * remark : 订单号：3280620180201101138
             * id : 431
             * title : 卖家已同意退货
             * order_id : 3280620180201101138
             * content : 1
             */
            private String addTime;
            private int already_write;
            private String icon;
            private String remark;
            private int id;
            private String title;
            private String type_id;
            private int type;
            private String jump_type;
            private String zone_type;
            private String article_title;
            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getArticle_title() {
                return article_title;
            }

            public void setArticle_title(String article_title) {
                this.article_title = article_title;
            }

            public String getJump_type() {
                return jump_type;
            }

            public void setJump_type(String jump_type) {
                this.jump_type = jump_type;
            }

            public String getZone_type() {
                return zone_type;
            }

            public void setZone_type(String zone_type) {
                this.zone_type = zone_type;
            }

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setAlready_write(int already_write) {
                this.already_write = already_write;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getAddTime() {
                return addTime;
            }

            public int getAlready_write() {
                return already_write;
            }

            public String getIcon() {
                return icon;
            }

            public String getRemark() {
                return remark;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
