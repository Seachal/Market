package com.dajukeji.hslz.domain.javaBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ${wangjiasheng} on 2018/1/2 0002.
 */

public class BigBrandBean implements Serializable{
    /**
     * status : 0
     * message : 品牌列表
     * content : {"big_brand_list":[{"brand_id":16,"brand_logo":"upload/brand/b277e462-049e-4114-a12b-7fc358d585b2.png"},{"brand_id":14,"brand_logo":"upload/brand/2df181a9-df2f-429d-96f2-d3f9e81dfd5d.jpg"},{"brand_id":15,"brand_logo":"upload/brand/84fda309-f7d2-470e-9a24-f3513e61b86a.png"}]}
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
        private List<BigBrandListBean> big_brand_list;

        public List<BigBrandListBean> getBig_brand_list() {
            return big_brand_list;
        }

        public void setBig_brand_list(List<BigBrandListBean> big_brand_list) {
            this.big_brand_list = big_brand_list;
        }

        public static class BigBrandListBean {
            /**
             * brand_id : 16
             * brand_logo : upload/brand/b277e462-049e-4114-a12b-7fc358d585b2.png
             */

            private int brand_id;
            private String brand_logo;
            private String brand_name;
            private String first_letter;

            public String getFirst_letter() {
                return first_letter;
            }

            public void setFirst_letter(String first_letter) {
                this.first_letter = first_letter;
            }

            public String getBrand_name() {
                return brand_name;
            }

            public void setBrand_name(String brand_name) {
                this.brand_name = brand_name;
            }

            public int getBrand_id() {
                return brand_id;
            }

            public void setBrand_id(int brand_id) {
                this.brand_id = brand_id;
            }

            public String getBrand_logo() {
                return brand_logo;
            }

            public void setBrand_logo(String brand_logo) {
                this.brand_logo = brand_logo;
            }
        }
    }
}
