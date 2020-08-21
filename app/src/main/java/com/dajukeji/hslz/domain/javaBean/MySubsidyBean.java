package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/15.
 */

public class MySubsidyBean {

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
         * pages : 1
         * pageSize : 12
         * currentPage : 1
         * orderList :
         */

        private int pages;
        private int pageSize;
        private int currentPage;
        private List<OrderListBean> orderList;

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

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * goods_main_photo : upload/store/32774/2018/01/08/222fd6a9-0951-4c14-817c-a3d87a79bc1e.jpg
             * cut_price_id : 229618
             * help_detail :
             * current_price : 69630.0
             * original_price : 2321.0
             * wx_headimgurl_list
             * goods_name : 鱼肝油
             * lowest_price : 69630.0
             * invalid_time : 01月18日 20:54
             */

            private String goods_main_photo;
            private long cut_price_id;
            private HelpDetailBean help_detail;
            private double current_price;
            private double original_price;
            private String goods_name;
            private double lowest_price;
            private String invalid_time;
            private List<String> wx_headimgurl_list;
            private int shareable;
            private int payable;
            private int order_status;
            private int deletable;

            public int getDeletable() {
                return deletable;
            }

            public void setDeletable(int deletable) {
                this.deletable = deletable;
            }

            public int getPayable() {
                return payable;
            }

            public void setPayable(int payable) {
                this.payable = payable;
            }

            public int getShareable() {
                return shareable;
            }

            public void setShareable(int shareable) {
                this.shareable = shareable;
            }

            public int getOrder_status() {
                return order_status;
            }

            public void setOrder_status(int order_status) {
                this.order_status = order_status;
            }

            public String getGoods_main_photo() {
                return goods_main_photo;
            }

            public void setGoods_main_photo(String goods_main_photo) {
                this.goods_main_photo = goods_main_photo;
            }

            public long getCut_price_id() {
                return cut_price_id;
            }

            public void setCut_price_id(long cut_price_id) {
                this.cut_price_id = cut_price_id;
            }

            public HelpDetailBean getHelp_detail() {
                return help_detail;
            }

            public void setHelp_detail(HelpDetailBean help_detail) {
                this.help_detail = help_detail;
            }

            public double getCurrent_price() {
                return current_price;
            }

            public void setCurrent_price(double current_price) {
                this.current_price = current_price;
            }

            public double getOriginal_price() {
                return original_price;
            }

            public void setOriginal_price(double original_price) {
                this.original_price = original_price;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public double getLowest_price() {
                return lowest_price;
            }

            public void setLowest_price(double lowest_price) {
                this.lowest_price = lowest_price;
            }

            public String getInvalid_time() {
                return invalid_time;
            }

            public void setInvalid_time(String invalid_time) {
                this.invalid_time = invalid_time;
            }

            public List<String> getWx_headimgurl_list() {
                return wx_headimgurl_list;
            }

            public void setWx_headimgurl_list(List<String> wx_headimgurl_list) {
                this.wx_headimgurl_list = wx_headimgurl_list;
            }

            public static class HelpDetailBean {
                /**
                 * hoster_headimgurl : http://wx.qlogo.cn/mmopen/vi_32/TYkqRHePPz2xs4DdPVtPwoTZLkmzz8BOXaLZ8hC6hGagjWvnojiaibAzvmx6M13bIAtI126YU90SYgibza871Q90g/132
                 * hoster_nickname : 罗鹏
                 * number : 1
                 * helperList : [{"time":"2018/01/17 20:55:16","nickname":"Spiderman","headimgurl":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLAb5mLgVxMSOVLYvRQzG8CicBSP1RGQ7icNFHYkib28iaXSkiaR1SGNkib0gL2GuWtVjo947sNOePNLJGA/132"}]
                 * hoster_time : 01月17日 20:54
                 */

                private String hoster_headimgurl;
                private String hoster_nickname;
                private int number;
                private String hoster_time;
                private List<HelperListBean> helperList;

                public String getHoster_headimgurl() {
                    return hoster_headimgurl;
                }

                public void setHoster_headimgurl(String hoster_headimgurl) {
                    this.hoster_headimgurl = hoster_headimgurl;
                }

                public String getHoster_nickname() {
                    return hoster_nickname;
                }

                public void setHoster_nickname(String hoster_nickname) {
                    this.hoster_nickname = hoster_nickname;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getHoster_time() {
                    return hoster_time;
                }

                public void setHoster_time(String hoster_time) {
                    this.hoster_time = hoster_time;
                }

                public List<HelperListBean> getHelperList() {
                    return helperList;
                }

                public void setHelperList(List<HelperListBean> helperList) {
                    this.helperList = helperList;
                }

                public static class HelperListBean {
                    /**
                     * time : 2018/01/17 20:55:16
                     * nickname : Spiderman
                     * headimgurl : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLAb5mLgVxMSOVLYvRQzG8CicBSP1RGQ7icNFHYkib28iaXSkiaR1SGNkib0gL2GuWtVjo947sNOePNLJGA/132
                     */

                    private String time;
                    private String nickname;
                    private String headimgurl;

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String getNickname() {
                        return nickname;
                    }

                    public void setNickname(String nickname) {
                        this.nickname = nickname;
                    }

                    public String getHeadimgurl() {
                        return headimgurl;
                    }

                    public void setHeadimgurl(String headimgurl) {
                        this.headimgurl = headimgurl;
                    }
                }
            }
        }
    }
}
