package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BrandClassBean implements Serializable {
    /**
     * status : 0
     * message : 店铺分类列表
     * content : {"store_class_list":[{"id":1,"name":"男女服装"},{"id":6,"name":"鞋包配饰"},{"id":12,"name":"户外运动"},{"id":16,"name":"数码电子"}]}
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
        private List<StoreClassListBean> store_class_list;

        public List<StoreClassListBean> getStore_class_list() {
            return store_class_list;
        }

        public void setStore_class_list(List<StoreClassListBean> store_class_list) {
            this.store_class_list = store_class_list;
        }

        public static class StoreClassListBean {
            /**
             * id : 1
             * name : 男女服装
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
