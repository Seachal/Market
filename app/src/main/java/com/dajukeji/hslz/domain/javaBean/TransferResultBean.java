package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/19 14:19
 * 作用:
 */
public class TransferResultBean {

    /**
     * code : 0
     * msg : 请求成功
     * count : 0
     * data : null
     * currentPublicData : null
     */

    private int code;
    private String msg;
    private int count;
    private Object data;
    private Object currentPublicData;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getCurrentPublicData() {
        return currentPublicData;
    }

    public void setCurrentPublicData(Object currentPublicData) {
        this.currentPublicData = currentPublicData;
    }
}
