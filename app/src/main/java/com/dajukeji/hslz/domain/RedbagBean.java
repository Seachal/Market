package com.dajukeji.hslz.domain;

import java.util.List;

public class RedbagBean {

    /**
     * status : 0
     * message : 红包列表
     * content : {"redList":[{"store_id":32775,"goods_id":"","money":10,"munber":100,"url":"http://thirdwx.qlogo.cn/mmopen/vi_32/8paZEgGGdFBJVqgXiaBu6num3qic2UUfYicyk8mCDwKjCpqjjBZb1QIiaaXy5DVDLUGHiajicPydtDpEQ7hVmYhg1nqg/132","red_id":1}],"is_address":1}
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
        /**
         * redList : [{"store_id":32775,"goods_id":"","money":10,"munber":100,"url":"http://thirdwx.qlogo.cn/mmopen/vi_32/8paZEgGGdFBJVqgXiaBu6num3qic2UUfYicyk8mCDwKjCpqjjBZb1QIiaaXy5DVDLUGHiajicPydtDpEQ7hVmYhg1nqg/132","red_id":1}]
         * is_address : 1
         */

        private int is_address;
        private List<RedListBean> redList;

        public int getIs_address() {
            return is_address;
        }

        public void setIs_address(int is_address) {
            this.is_address = is_address;
        }

        public List<RedListBean> getRedList() {
            return redList;
        }

        public void setRedList(List<RedListBean> redList) {
            this.redList = redList;
        }

        public static class RedListBean {
            /**
             * store_id : 32775
             * goods_id :
             * money : 10.0
             * munber : 100
             * url : http://thirdwx.qlogo.cn/mmopen/vi_32/8paZEgGGdFBJVqgXiaBu6num3qic2UUfYicyk8mCDwKjCpqjjBZb1QIiaaXy5DVDLUGHiajicPydtDpEQ7hVmYhg1nqg/132
             * red_id : 1
             */

            private int store_id;
            private String goods_id;
            private double money;
            private int munber;
            private String url;
            private int red_id;

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public double getMoney() {
                return money;
            }

            public void setMoney(double money) {
                this.money = money;
            }

            public int getMunber() {
                return munber;
            }

            public void setMunber(int munber) {
                this.munber = munber;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getRed_id() {
                return red_id;
            }

            public void setRed_id(int red_id) {
                this.red_id = red_id;
            }
        }
    }
}
