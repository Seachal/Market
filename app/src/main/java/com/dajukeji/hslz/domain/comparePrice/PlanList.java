package com.dajukeji.hslz.domain.comparePrice;

import java.util.List;

/**
 * Created by Administrator on 2018/1/2.
 */

public class PlanList {


    /**
     * status : 0
     * message : 全网比价计划
     * content : {"plan_list":[{"begin_time":"15:59","plan_status":"0","end_time":"15:59","left_second":454746287,"plan_id":4},{"begin_time":"16:12","plan_status":"0","end_time":"16:12","left_second":455555287,"plan_id":6},{"begin_time":"21:36","plan_status":"0","end_time":"21:36","left_second":647770287,"plan_id":7}]}
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
        private List<PlanListBean> plan_list;

        public List<PlanListBean> getPlan_list() {
            return plan_list;
        }

        public void setPlan_list(List<PlanListBean> plan_list) {
            this.plan_list = plan_list;
        }

        public static class PlanListBean {
            /**
             * begin_time : 15:59
             * plan_status : 0
             * end_time : 15:59
             * left_second : 454746287
             * plan_id : 4
             */

            private String begin_time;
            private String plan_status;
            private String end_time;
            private int left_second;
            private long plan_id;

            public String getBegin_time() {
                return begin_time;
            }

            public void setBegin_time(String begin_time) {
                this.begin_time = begin_time;
            }

            public String getPlan_status() {
                return plan_status;
            }

            public void setPlan_status(String plan_status) {
                this.plan_status = plan_status;
            }

            public String getEnd_time() {
                return end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public int getLeft_second() {
                return left_second;
            }

            public void setLeft_second(int left_second) {
                this.left_second = left_second;
            }

            public long getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(long plan_id) {
                this.plan_id = plan_id;
            }
        }
    }
}
