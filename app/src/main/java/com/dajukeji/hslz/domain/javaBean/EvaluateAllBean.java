package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/2/2.
 */

public class EvaluateAllBean {

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
         * evaluateList : [{"user_name":"lpl","evaluate_info":"呵呵不说话。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"},{"user_name":"spiderman","evaluate_info":"确实不错。","head_image":"http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0","goods_spec":"颜色:红色 尺码:XXL ","add_time":"2017.12.05"}]
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<EvaluateListBean> evaluateList;

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

        public List<EvaluateListBean> getEvaluateList() {
            return evaluateList;
        }

        public void setEvaluateList(List<EvaluateListBean> evaluateList) {
            this.evaluateList = evaluateList;
        }

        public static class EvaluateListBean {
            /**
             * user_name : lpl
             * evaluate_info : 呵呵不说话。
             * head_image : http://wx.qlogo.cn/mmopen/vi_32/U4B3pWawhXewXk1fp8eicwjpNkFf77ZEWZnjJCTcOGlE35zpxpAicJAiajeuFApPMwQkVDFLnnnzNMGjTRjLmlLhg/0
             * goods_spec : 颜色:红色 尺码:XXL
             * add_time : 2017.12.05
             */

            private String user_name;
            private String evaluate_info;
            private String head_image;
            private String goods_spec;
            private String add_time;

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getEvaluate_info() {
                return evaluate_info;
            }

            public void setEvaluate_info(String evaluate_info) {
                this.evaluate_info = evaluate_info;
            }

            public String getHead_image() {
                return head_image;
            }

            public void setHead_image(String head_image) {
                this.head_image = head_image;
            }

            public String getGoods_spec() {
                return goods_spec;
            }

            public void setGoods_spec(String goods_spec) {
                this.goods_spec = goods_spec;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
