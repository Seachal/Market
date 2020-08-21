package com.dajukeji.hslz.domain.order;

import java.util.List;

/**
 * Created by Administrator on 2018/1/27.
 */

public class OrderEvaluateBean {


    /**
     * status : 0
     * message : 订单评价页面
     * content : {"gclist":[{"gc_id":230123,"seller_description":"这是一件好衣服","main_photo":"upload/store_logo/good.jpg"}]}
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
        private List<GclistBean> gclist;

        public List<GclistBean> getGclist() {
            return gclist;
        }

        public void setGclist(List<GclistBean> gclist) {
            this.gclist = gclist;
        }

        public static class GclistBean {
            /**
             * gc_id : 230123
             * seller_description : 这是一件好衣服
             * main_photo : upload/store_logo/good.jpg
             */

            private int gc_id;
            private String seller_description;
            private String main_photo;

            public int getGc_id() {
                return gc_id;
            }

            public void setGc_id(int gc_id) {
                this.gc_id = gc_id;
            }

            public String getSeller_description() {
                return seller_description;
            }

            public void setSeller_description(String seller_description) {
                this.seller_description = seller_description;
            }

            public String getMain_photo() {
                return main_photo;
            }

            public void setMain_photo(String main_photo) {
                this.main_photo = main_photo;
            }
        }
    }
}
