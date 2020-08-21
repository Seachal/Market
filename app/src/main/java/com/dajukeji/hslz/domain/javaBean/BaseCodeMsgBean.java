package com.dajukeji.hslz.domain.javaBean;

/**
 * 作者: Li_ke
 * 日期: 2019/1/25 18:06
 * 作用:
 */
public class BaseCodeMsgBean {

    /**
     * code : 0
     * msg : 短信发送成功!
     * count : null
     */

    private int code;
    private String msg;
    private Object count;

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

    public Object getCount() {
        return count;
    }

    public void setCount(Object count) {
        this.count = count;
    }
}
