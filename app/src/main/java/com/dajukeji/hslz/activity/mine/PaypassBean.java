package com.dajukeji.hslz.activity.mine;

public class PaypassBean {

    /**
     * code : 0
     * msg : 添加成功！
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
