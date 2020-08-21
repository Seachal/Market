package com.dajukeji.hslz.domain.javaBean;

import java.util.List;

/**
 * 作者: Li_ke
 * 日期: 2019/1/28 10:51
 * 作用:
 */
public class IntroduceImgBean {

    /**
     * status : 0
     * message : 启动页列表
     * content : {"startupPageList":[{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/user/1/2019/01/28/a502b0c8-48ae-4b00-b6c3-fabfbd8cf497.png"},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/user/1/2019/01/28/56771366-512f-41c6-a3d3-b187e1134c1e.png"},{"pic_url":"http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/user/1/2019/01/28/2b05dc35-a180-4efe-9313-15e1f4df9b74.png"}]}
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
        private List<StartupPageListBean> startupPageList;

        public List<StartupPageListBean> getStartupPageList() {
            return startupPageList;
        }

        public void setStartupPageList(List<StartupPageListBean> startupPageList) {
            this.startupPageList = startupPageList;
        }

        public static class StartupPageListBean {
            /**
             * pic_url : http://sjhs.oss-cn-shenzhen.aliyuncs.com/test/user/1/2019/01/28/a502b0c8-48ae-4b00-b6c3-fabfbd8cf497.png
             */

            private String pic_url;

            public String getPic_url() {
                return pic_url;
            }

            public void setPic_url(String pic_url) {
                this.pic_url = pic_url;
            }
        }
    }
}
