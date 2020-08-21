package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 14:50
 * 作用:
 */
public class GoodsClassTabBean {

    /**
     * status : 0
     * message : 商品列表
     * content : {"goodsClassList":[{"class_id":65726,"class_name":"服饰箱包"},{"class_id":65798,"class_name":"家纺家具"},{"class_id":65870,"class_name":"居家生活"},{"class_id":66001,"class_name":"美容个护"},{"class_id":66023,"class_name":"母婴玩具"},{"class_id":66025,"class_name":"食品保健"},{"class_id":66026,"class_name":"数码电器"},{"class_id":66027,"class_name":"水果生鲜"},{"class_id":66028,"class_name":"运动户外"}]}
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
             */

            private int class_id;
            private String class_name;

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
        }
    }
}
