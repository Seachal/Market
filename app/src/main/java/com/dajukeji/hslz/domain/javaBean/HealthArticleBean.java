package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class HealthArticleBean {
    /**
     * message : 查询成功
     * content : {"pages":1,"articleList":[{"addTime":"2017-11-26 18:32:57","icon":"upload/brand 不v          ","id":196630,"title":"213213","url":"app/mall/article/readarticleRead.htm?id=196630"}],"pageSize":12,"currentPage":1}
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
         * pages : 1
         * articleList : [{"addTime":"2017-11-26 18:32:57","icon":"upload/brand 不v          ","id":196630,"title":"213213","url":"app/mall/article/readarticleRead.htm?id=196630"}]
         * pageSize : 12
         * currentPage : 1
         */
        private int pages;
        private List<ArticleListEntity> articleList;
        private int pageSize;
        private int currentPage;

        public void setPages(int pages) {
            this.pages = pages;
        }

        public void setArticleList(List<ArticleListEntity> articleList) {
            this.articleList = articleList;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getPages() {
            return pages;
        }

        public List<ArticleListEntity> getArticleList() {
            return articleList;
        }

        public int getPageSize() {
            return pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public class ArticleListEntity {
            /**
             * addTime : 2017-11-26 18:32:57
             * icon : upload/brand 不v
             * id : 196630
             * title : 213213
             * url : app/mall/article/readarticleRead.htm?id=196630
             */
            private String addTime;
            private String icon;
            private int id;
            private String title;
            private String url;

            public void setAddTime(String addTime) {
                this.addTime = addTime;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAddTime() {
                return addTime;
            }

            public String getIcon() {
                return icon;
            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

            public String getUrl() {
                return url;
            }
        }
    }

}
