package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 16:35
 * 作用:
 */
public class GoodsClassSubBean {

    /**
     * status : 0
     * message : 商品列表
     * content : {"goodsClassList":[{"class_id":65726,"class_name":"服饰箱包","class_url":""}]}
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
        private List<GoodsClassListBean> goodsClassList;

        public List<GoodsClassListBean> getGoodsClassList() {
            return goodsClassList;
        }

        public void setGoodsClassList(List<GoodsClassListBean> goodsClassList) {
            this.goodsClassList = goodsClassList;
        }

        public static class GoodsClassListBean {
            /**
             * class_id : 65726
             * class_name : 服饰箱包
             * class_url :
             */

            private int class_id;
            private String class_name;
            private String class_url;

            public int getClass_id() {
                return class_id;
            }

            public void setClass_id(int class_id) {
                this.class_id = class_id;
            }

            public String getClass_name() {
                return class_name;
            }

            public void setClass_name(String class_name) {
                this.class_name = class_name;
            }

            public String getClass_url() {
                return class_url;
            }

            public void setClass_url(String class_url) {
                this.class_url = class_url;
            }
        }
    }
}
