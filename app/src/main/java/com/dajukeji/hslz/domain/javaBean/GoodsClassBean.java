package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class GoodsClassBean implements Serializable{

    /**
     * message : 产品类别
     * content : {"goodsClass":[{"className":"男装、时尚女装","id":1},{"className":"男鞋、女鞋、女包","id":25},{"className":"体育用品、运动户外","id":50},{"className":"数码城、手机、电子","id":69},{"className":"家电城、厨房电器","id":95},{"className":"个护化妆、清洁用品","id":141},{"className":"家纺、灯具、家装","id":180},{"className":"奶粉、育婴、玩具","id":181},{"className":"珠宝、首饰、饰品","id":182},{"className":"维修保养、车载用品","id":183},{"className":"日用百货、营养健康","id":65564},{"className":"音响、美术、摄影","id":65565},{"className":"文化娱乐、古董收藏","id":65566},{"className":"美食特产、营养保健","id":65567},{"className":"虚拟服务、充值服务","id":65568}]}
     * status : 0
     */
    private String message;
    private ContentEntity content;
    private String status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public ContentEntity getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public class ContentEntity {
        /**
         * goodsClass : [{"className":"男装、时尚女装","id":1},{"className":"男鞋、女鞋、女包","id":25},{"className":"体育用品、运动户外","id":50},{"className":"数码城、手机、电子","id":69},{"className":"家电城、厨房电器","id":95},{"className":"个护化妆、清洁用品","id":141},{"className":"家纺、灯具、家装","id":180},{"className":"奶粉、育婴、玩具","id":181},{"className":"珠宝、首饰、饰品","id":182},{"className":"维修保养、车载用品","id":183},{"className":"日用百货、营养健康","id":65564},{"className":"音响、美术、摄影","id":65565},{"className":"文化娱乐、古董收藏","id":65566},{"className":"美食特产、营养保健","id":65567},{"className":"虚拟服务、充值服务","id":65568}]
         */
        private List<GoodsClassEntity> goodsClass;

        public void setGoodsClass(List<GoodsClassEntity> goodsClass) {
            this.goodsClass = goodsClass;
        }

        public List<GoodsClassEntity> getGoodsClass() {
            return goodsClass;
        }

        public class GoodsClassEntity {
            /**
             * className : 男装、时尚女装
             * id : 1
             */
            private String className;
            private int id;

            public void setClassName(String className) {
                this.className = className;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getClassName() {
                return className;
            }

            public int getId() {
                return id;
            }
        }
    }
}
