package com.dajukeji.hslz.activity.mine;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CouponBean implements Serializable {
    /**
     * code : 0
     * msg : null
     * count : 0
     * data : {"accountInfo":{"amount":20,"freeze":0,"accountType":0,"userCode":"ac9cb058-ddcd-4192-abf9-30ede6dc3897","status":1},"transactionlist":{"total":2,"pageSize":1,"pageNum":1,"items":[{"reqeustNo":"15test2","payTime":"2019-01-15 02:23:15","userName":"H","userCode":"ac9cb058-ddcd-4192-abf9-30ede6dc3897","businessDesc":null,"payNo":"USER_RECHARGE534739346849386496","businessNo":null,"payType":4,"payAmount":10,"balance":10,"money":120,"price":12,"userType":"0","businessType":1,"dataSource":"sjhs","payStatus":1,"flowType":1}]}}
     * currentPublicData : null
     */

    private int code;
    private String msg;
    private int count;
    private DataBean data;
    private Object currentPublicData;

    public static CouponBean newForResponse(ResponseCouponBean bean) {
        CouponBean couponBean = new CouponBean();
        //code、msg
        try {
            couponBean.code = Integer.valueOf(bean.getStatus());
            couponBean.msg = bean.getMessage();

            if (TextUtils.isEmpty(couponBean.msg))
                couponBean.msg = bean.getContent().getMyCash().getErrorMsg();

            if (couponBean.code == 0) {
                couponBean.code = Integer.valueOf(bean.getContent().getMyCash().getErrorCode());
                if (couponBean.code != 0)//使用正确的错误信息
                    couponBean.msg = bean.getContent().getMyCash().getErrorMsg();
            }

        } catch (Exception e) {
        }

        if (couponBean.code == 0) {
            //余额，冻结金额
            couponBean.data = new DataBean();
            couponBean.data.accountInfo = new DataBean.AccountInfoBean();
            couponBean.data.accountInfo.amount = bean.getContent().getMyCash().getData().getAmount();
            couponBean.data.accountInfo.freeze = bean.getContent().getMyCash().getData().getFreeze();
            //转账记录
            couponBean.data.transactionlist = new DataBean.TransactionlistBean();
            couponBean.data.transactionlist.total = bean.getContent().getMyCash().getData().getTotal();
            couponBean.data.transactionlist.pageSize = bean.getContent().getMyCash().getData().getPageSize();
            couponBean.data.transactionlist.pageTotal = bean.getContent().getMyCash().getData().getPageTotal();
            couponBean.data.transactionlist.pageNum = bean.getContent().getMyCash().getData().getPageNum();
            couponBean.data.transactionlist.items = new ArrayList<>();
            for (ResponseCouponBean.ContentBean.MyCashBean.DataBean.ItemsBean item : bean.getContent().getMyCash().getData().getItems()) {
                DataBean.TransactionlistBean.ItemsBean item1 = new DataBean.TransactionlistBean.ItemsBean();
                item1.payNo = item.getPayNo();
                item1.requestNo = item.getRequestNo();
                item1.businessNo = item.getRequestNo();
                item1.businessType = item.getBusinessType();
                item1.flowType = item.getFlowType();
                item1.payStatus = item.getPayStatus();
                item1.userCode = item.getUserCode();
                item1.payAmount = item.getPayAmount();
//            item1.businessDesc = item.getBalance();
                item1.payTime = item.getPayTime();
                item1.balance = item.getBalance();

                couponBean.data.transactionlist.items.add(item1);
            }
        }

        return couponBean;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public Object getCurrentPublicData() {
        return currentPublicData;
    }

    public void setCurrentPublicData(Object currentPublicData) {
        this.currentPublicData = currentPublicData;
    }

    public static class DataBean implements Serializable {
        /**
         * accountInfo : {"amount":20,"freeze":0,"accountType":0,"userCode":"ac9cb058-ddcd-4192-abf9-30ede6dc3897","status":1}
         * transactionlist : {"total":2,"pageSize":1,"pageNum":1,"items":[{"reqeustNo":"15test2","payTime":"2019-01-15 02:23:15","userName":"H","userCode":"ac9cb058-ddcd-4192-abf9-30ede6dc3897","businessDesc":null,"payNo":"USER_RECHARGE534739346849386496","businessNo":null,"payType":4,"payAmount":10,"balance":10,"money":120,"price":12,"userType":"0","businessType":1,"dataSource":"sjhs","payStatus":1,"flowType":1}]}
         */
        private AccountInfoBean accountInfo;
        private TransactionlistBean transactionlist;

        public AccountInfoBean getAccountInfo() {
            return accountInfo;
        }

        public void setAccountInfo(AccountInfoBean accountInfo) {
            this.accountInfo = accountInfo;
        }

        public TransactionlistBean getTransactionlist() {
            return transactionlist;
        }

        public void setTransactionlist(TransactionlistBean transactionlist) {
            this.transactionlist = transactionlist;
        }

        public static class AccountInfoBean implements Serializable {
            /**
             * amount : 20
             * freeze : 0
             * accountType : 0
             * userCode : ac9cb058-ddcd-4192-abf9-30ede6dc3897
             * status : 1
             */

            private double amount;
            private double freeze;
            private int accountType;
            private String userCode;
            private int status;

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

            public int getAccountType() {
                return accountType;
            }

            public void setAccountType(int accountType) {
                this.accountType = accountType;
            }

            public String getUserCode() {
                return userCode;
            }

            public void setUserCode(String userCode) {
                this.userCode = userCode;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class TransactionlistBean implements Serializable {
            /**
             * total : 2
             * pageSize : 1
             * pageNum : 1
             * items : [{"reqeustNo":"15test2","payTime":"2019-01-15 02:23:15","userName":"H","userCode":"ac9cb058-ddcd-4192-abf9-30ede6dc3897","businessDesc":null,"payNo":"USER_RECHARGE534739346849386496","businessNo":null,"payType":4,"payAmount":10,"balance":10,"money":120,"price":12,"userType":"0","businessType":1,"dataSource":"sjhs","payStatus":1,"flowType":1}]
             */

            private int total;
            private int pageSize;
            private int pageNum;
            private int pageTotal;
            private List<ItemsBean> items;

            public int getPageTotal() {
                return pageTotal;
            }

            public void setPageTotal(int pageTotal) {
                this.pageTotal = pageTotal;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPageSize() {
                return pageSize;
            }

            public void setPageSize(int pageSize) {
                this.pageSize = pageSize;
            }

            public int getPageNum() {
                return pageNum;
            }

            public void setPageNum(int pageNum) {
                this.pageNum = pageNum;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean implements Serializable {
                /**
                 * reqeustNo : 15test2
                 * payTime : 2019-01-15 02:23:15
                 * userName : H
                 * userCode : ac9cb058-ddcd-4192-abf9-30ede6dc3897
                 * businessDesc : null
                 * payNo : USER_RECHARGE534739346849386496
                 * businessNo : null
                 * payType : 4
                 * payAmount : 10
                 * balance : 10
                 * money : 120
                 * price : 12
                 * userType : 0
                 * businessType : 1
                 * dataSource : sjhs
                 * payStatus : 1
                 * flowType : 1
                 */

                private String requestNo;
                private String payTime;
                private String userName;
                private String userCode;
                private Object businessDesc;
                private String payNo;
                private Object businessNo;
                private int payType;
                private double payAmount;
                private double balance;
                private double money;
                private double price;
                private String userType;
                private int businessType;
                private String dataSource;
                private int payStatus;
                private int flowType;

                public String getTitle() {
                    String result = "" + businessType;
                    //交易类型
                    if (businessType == 1) {
                        result = "用户充值";
                    } else if (businessType == 2) {
                        result = "产品销售分成收入";
                    } else if (businessType == 3) {
                        result = "广告收益分成";
                    } else if (businessType == 4) {
                        result = "红包分成";
                    } else if (businessType == 5) {
                        result = "销售代金券分成";
                    } else if (businessType == 6) {
                        result = "转卖场交易费分成";
                    } else if (businessType == 7) {
                        result = "代金券转账";
                    } else if (businessType == 8) {
                        result = "产品券买卖";
                    } else if (businessType == 9) {
                        result = "购买商品";
                    } else if (businessType == 10) {
                        result = "红包收益";
                    } else result = "未知的类型";
                    return result;
                }

                public String getPriceStr() {
                    if (flowType >= 0) {
                        return "+" + payAmount;
                    } else {
                        return "-" + payAmount;
                    }
                }

                public String getRequestNo() {
                    return requestNo;
                }

                public void setRequestNo(String requestNo) {
                    this.requestNo = requestNo;
                }

                public String getPayTime() {
                    return payTime;
                }

                public void setPayTime(String payTime) {
                    this.payTime = payTime;
                }

                public String getUserName() {
                    return userName;
                }

                public void setUserName(String userName) {
                    this.userName = userName;
                }

                public String getUserCode() {
                    return userCode;
                }

                public void setUserCode(String userCode) {
                    this.userCode = userCode;
                }

                public Object getBusinessDesc() {
                    return businessDesc;
                }

                public void setBusinessDesc(Object businessDesc) {
                    this.businessDesc = businessDesc;
                }

                public String getPayNo() {
                    return payNo;
                }

                public void setPayNo(String payNo) {
                    this.payNo = payNo;
                }

                public Object getBusinessNo() {
                    return businessNo;
                }

                public void setBusinessNo(Object businessNo) {
                    this.businessNo = businessNo;
                }

                public int getPayType() {
                    return payType;
                }

                public void setPayType(int payType) {
                    this.payType = payType;
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

                public double getMoney() {
                    return money;
                }

                public void setMoney(double money) {
                    this.money = money;
                }

                public double getPrice() {
                    return price;
                }

                public void setPrice(double price) {
                    this.price = price;
                }

                public String getUserType() {
                    return userType;
                }

                public void setUserType(String userType) {
                    this.userType = userType;
                }

                public int getBusinessType() {
                    return businessType;
                }

                public void setBusinessType(int businessType) {
                    this.businessType = businessType;
                }

                public String getDataSource() {
                    return dataSource;
                }

                public void setDataSource(String dataSource) {
                    this.dataSource = dataSource;
                }

                public int getPayStatus() {
                    return payStatus;
                }

                public void setPayStatus(int payStatus) {
                    this.payStatus = payStatus;
                }

                public int getFlowType() {
                    return flowType;
                }

                public void setFlowType(int flowType) {
                    this.flowType = flowType;
                }
            }
        }
    }
}
