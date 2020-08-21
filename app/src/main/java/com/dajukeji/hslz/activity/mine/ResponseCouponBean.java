package com.dajukeji.hslz.activity.mine;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/26 16:10
 * 作用:
 */
public class ResponseCouponBean {

    /**
     * status : 0
     * message : 成功
     * content : {"myCash":{"errorCode":"0000","errorMsg":"请求成功","data":{"userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","accountType":0,"amount":10113.2,"freeze":0,"status":1,"pageNum":1,"pageSize":10,"total":5,"pageTotal":5,"items":[{"payNo":"OSJHSCCTA201901261445159984B023C","reqeustNo":"CCTA201901261445159984b023c","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":-1,"businessType":7,"payAmount":1,"balance":9713.2,"payStatus":1,"payTime":"2019-01-26 14:45:16"},{"payNo":"ISJHSCCTA20190126131012955506282","reqeustNo":"CCTA20190126131012955506282","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:13"},{"payNo":"ISJHSCCTA2019012613101206441ADF7","reqeustNo":"CCTA2019012613101206441adf7","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:12"},{"payNo":"ISJHSCCTA20190126131011337AE10AF","reqeustNo":"CCTA20190126131011337ae10af","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:11"},{"payNo":"ISJHSCCTA201901261310088266A3468","reqeustNo":"CCTA201901261310088266a3468","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:09"}]}}}
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
         * myCash : {"errorCode":"0000","errorMsg":"请求成功","data":{"userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","accountType":0,"amount":10113.2,"freeze":0,"status":1,"pageNum":1,"pageSize":10,"total":5,"pageTotal":5,"items":[{"payNo":"OSJHSCCTA201901261445159984B023C","reqeustNo":"CCTA201901261445159984b023c","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":-1,"businessType":7,"payAmount":1,"balance":9713.2,"payStatus":1,"payTime":"2019-01-26 14:45:16"},{"payNo":"ISJHSCCTA20190126131012955506282","reqeustNo":"CCTA20190126131012955506282","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:13"},{"payNo":"ISJHSCCTA2019012613101206441ADF7","reqeustNo":"CCTA2019012613101206441adf7","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:12"},{"payNo":"ISJHSCCTA20190126131011337AE10AF","reqeustNo":"CCTA20190126131011337ae10af","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:11"},{"payNo":"ISJHSCCTA201901261310088266A3468","reqeustNo":"CCTA201901261310088266a3468","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:09"}]}}
         */

        private MyCashBean myCash;

        public MyCashBean getMyCash() {
            return myCash;
        }

        public void setMyCash(MyCashBean myCash) {
            this.myCash = myCash;
        }

        public static class MyCashBean {
            /**
             * errorCode : 0000
             * errorMsg : 请求成功
             * data : {"userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","accountType":0,"amount":10113.2,"freeze":0,"status":1,"pageNum":1,"pageSize":10,"total":5,"pageTotal":5,"items":[{"payNo":"OSJHSCCTA201901261445159984B023C","reqeustNo":"CCTA201901261445159984b023c","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":-1,"businessType":7,"payAmount":1,"balance":9713.2,"payStatus":1,"payTime":"2019-01-26 14:45:16"},{"payNo":"ISJHSCCTA20190126131012955506282","reqeustNo":"CCTA20190126131012955506282","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:13"},{"payNo":"ISJHSCCTA2019012613101206441ADF7","reqeustNo":"CCTA2019012613101206441adf7","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:12"},{"payNo":"ISJHSCCTA20190126131011337AE10AF","reqeustNo":"CCTA20190126131011337ae10af","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:11"},{"payNo":"ISJHSCCTA201901261310088266A3468","reqeustNo":"CCTA201901261310088266a3468","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:09"}]}
             */

            private String errorCode;
            private String errorMsg;
            private DataBean data;

            public String getErrorCode() {
                return errorCode;
            }

            public void setErrorCode(String errorCode) {
                this.errorCode = errorCode;
            }

            public String getErrorMsg() {
                return errorMsg;
            }

            public void setErrorMsg(String errorMsg) {
                this.errorMsg = errorMsg;
            }

            public DataBean getData() {
                return data;
            }

            public void setData(DataBean data) {
                this.data = data;
            }

            public static class DataBean {
                /**
                 * userCode : 8beeaf1c-8212-4b27-80b0-100eb2d4ed2b
                 * accountType : 0
                 * amount : 10113.2
                 * freeze : 0
                 * status : 1
                 * pageNum : 1
                 * pageSize : 10
                 * total : 5
                 * pageTotal : 5
                 * items : [{"payNo":"OSJHSCCTA201901261445159984B023C","reqeustNo":"CCTA201901261445159984b023c","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":-1,"businessType":7,"payAmount":1,"balance":9713.2,"payStatus":1,"payTime":"2019-01-26 14:45:16"},{"payNo":"ISJHSCCTA20190126131012955506282","reqeustNo":"CCTA20190126131012955506282","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:13"},{"payNo":"ISJHSCCTA2019012613101206441ADF7","reqeustNo":"CCTA2019012613101206441adf7","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:12"},{"payNo":"ISJHSCCTA20190126131011337AE10AF","reqeustNo":"CCTA20190126131011337ae10af","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:11"},{"payNo":"ISJHSCCTA201901261310088266A3468","reqeustNo":"CCTA201901261310088266a3468","userCode":"8beeaf1c-8212-4b27-80b0-100eb2d4ed2b","flowType":1,"businessType":7,"payAmount":100,"balance":9814.2,"payStatus":1,"payTime":"2019-01-26 13:10:09"}]
                 */

                private String userCode;
                private int accountType;
                private double amount;
                private double freeze;
                private int status;
                private int pageNum;
                private int pageSize;
                private int total;
                private int pageTotal;
                private List<ItemsBean> items;

                public String getUserCode() {
                    return userCode;
                }

                public void setUserCode(String userCode) {
                    this.userCode = userCode;
                }

                public int getAccountType() {
                    return accountType;
                }

                public void setAccountType(int accountType) {
                    this.accountType = accountType;
                }

                public double getAmount() {
                    return amount;
                }

                public void setAmount(double amount) {
                    this.amount = amount;
                }

                public double getFreeze() {
                    return freeze;
                }

                public void setFreeze(double freeze) {
                    this.freeze = freeze;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getPageNum() {
                    return pageNum;
                }

                public void setPageNum(int pageNum) {
                    this.pageNum = pageNum;
                }

                public int getPageSize() {
                    return pageSize;
                }

                public void setPageSize(int pageSize) {
                    this.pageSize = pageSize;
                }

                public int getTotal() {
                    return total;
                }

                public void setTotal(int total) {
                    this.total = total;
                }

                public int getPageTotal() {
                    return pageTotal;
                }

                public void setPageTotal(int pageTotal) {
                    this.pageTotal = pageTotal;
                }

                public List<ItemsBean> getItems() {
                    if (items == null) return new ArrayList<>();
                    return items;
                }

                public void setItems(List<ItemsBean> items) {
                    this.items = items;
                }

                public static class ItemsBean {
                    /**
                     * payNo : OSJHSCCTA201901261445159984B023C
                     * reqeustNo : CCTA201901261445159984b023c
                     * userCode : 8beeaf1c-8212-4b27-80b0-100eb2d4ed2b
                     * flowType : -1
                     * businessType : 7
                     * payAmount : 1
                     * balance : 9713.2
                     * payStatus : 1
                     * payTime : 2019-01-26 14:45:16
                     */

                    private String payNo;
                    private String reqeustNo;
                    private String userCode;
                    private int flowType;
                    private int businessType;
                    private double payAmount;
                    private double balance;
                    private int payStatus;
                    private String payTime;

                    public String getPayNo() {
                        return payNo;
                    }

                    public void setPayNo(String payNo) {
                        this.payNo = payNo;
                    }

                    public String getRequestNo() {
                        return reqeustNo;
                    }

                    public void setRequestNo(String reqeustNo) {
                        this.reqeustNo = reqeustNo;
                    }

                    public String getUserCode() {
                        return userCode;
                    }

                    public void setUserCode(String userCode) {
                        this.userCode = userCode;
                    }

                    public int getFlowType() {
                        return flowType;
                    }

                    public void setFlowType(int flowType) {
                        this.flowType = flowType;
                    }

                    public int getBusinessType() {
                        return businessType;
                    }

                    public void setBusinessType(int businessType) {
                        this.businessType = businessType;
                    }

                    public double getPayAmount() {
                        return payAmount;
                    }

                    public void setPayAmount(double payAmount) {
                        this.payAmount = payAmount;
                    }

                    public double getBalance() {
                        return balance;
                    }

                    public void setBalance(double balance) {
                        this.balance = balance;
                    }

                    public int getPayStatus() {
                        return payStatus;
                    }

                    public void setPayStatus(int payStatus) {
                        this.payStatus = payStatus;
                    }

                    public String getPayTime() {
                        return payTime;
                    }

                    public void setPayTime(String payTime) {
                        this.payTime = payTime;
                    }
                }
            }
        }
    }
}
