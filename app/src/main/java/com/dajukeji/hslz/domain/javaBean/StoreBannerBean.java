package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/6 0006.
 */

public class StoreBannerBean {
    /**
     * status : 0
     * message : 店铺banner列表
     * content : {"banner_list":[{"goods_id":1,"banner_logo":"upload/brand/4aaf9ac2-0b17-4253-ae49-9ab244cd21cb.png"},{"goods_id":3,"banner_logo":"upload/brand/1e36f3c6-6929-405e-8e87-39bbcdd9db1e.png"}]}
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
        private List<BannerListBean> banner_list;

        public List<BannerListBean> getBanner_list() {
            return banner_list;
        }

        public void setBanner_list(List<BannerListBean> banner_list) {
            this.banner_list = banner_list;
        }

        public static class BannerListBean {
            /**
             * goods_id : 1
             * banner_logo : upload/brand/4aaf9ac2-0b17-4253-ae49-9ab244cd21cb.png
             */

            private int goods_id;
            private String banner_logo;

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getBanner_logo() {
                return banner_logo;
            }

            public void setBanner_logo(String banner_logo) {
                this.banner_logo = banner_logo;
            }
        }
    }
}
