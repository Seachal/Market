package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/6 0006.
 */

public class StoreGoodClassBean {
    /**
     * status : 0
     * message : 店铺产品分类列表
     * content : {"store_goods_class_list":[{"id":32777,"name":"笔记本"},{"id":32778,"name":"一体机"}]}
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
        private List<StoreGoodsClassListBean> store_goods_class_list;

        public List<StoreGoodsClassListBean> getStore_goods_class_list() {
            return store_goods_class_list;
        }

        public void setStore_goods_class_list(List<StoreGoodsClassListBean> store_goods_class_list) {
            this.store_goods_class_list = store_goods_class_list;
        }

        public static class StoreGoodsClassListBean {
            /**
             * id : 32777
             * name : 笔记本
             */

            private int id;
            private String name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
