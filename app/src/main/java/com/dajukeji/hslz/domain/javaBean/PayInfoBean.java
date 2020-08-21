package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/26 15:18
 * 作用: 充值信息
 */
public class PayInfoBean {

    /**
     * status : 0
     * message : 成功
     * content : {"amount":"9999997.00000","moneyList":[{"money":"100"},{"money":"200"},{"money":"300"},{"money":"800"},{"money":"1000"},{"money":"5000"}],"money_cash_rate":1.2}
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
         * amount : 9999997.00000
         * moneyList : [{"money":"100"},{"money":"200"},{"money":"300"},{"money":"800"},{"money":"1000"},{"money":"5000"}]
         * money_cash_rate : 1.2
         */

        private String amount; //服务器余额，充值的金额不能大于这个数
        private double money_cash_rate;
        private int address;//是否有地址 0=没有 1=有
        private List<MoneyListBean> moneyList;
        private double percentage;//分润比例

        public int getAddress() {
            return address;
        }

        public void setAddress(int address) {
            this.address = address;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public double getMoney_cash_rate() {
            return money_cash_rate;
        }

        public void setMoney_cash_rate(double money_cash_rate) {
            this.money_cash_rate = money_cash_rate;
        }

        public List<MoneyListBean> getMoneyList() {
            return moneyList;
        }

        public void setMoneyList(List<MoneyListBean> moneyList) {
            this.moneyList = moneyList;
        }

        public static class MoneyListBean {
            /**
             * money : 100
             */

            private String money;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }
    }
}
