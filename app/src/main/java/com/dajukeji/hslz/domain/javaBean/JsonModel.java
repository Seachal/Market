package com.dajukeji.hslz.domain.javaBean;


import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by cdr on 2017/11/28.
 * 返回json格式参数类
 */

public class JsonModel<T> implements Serializable {

    private String status;         //请求结果码
    private String message;          //请求附带提示
    private T content;           //请求成功附带的数据对象

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

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public static JsonModel fromJson(String json, Class clazz) {
        Type objectType = type(JsonModel.class, clazz);
        Gson gson = new Gson();
        return gson.fromJson(json, objectType);
    }

    public String toJson(Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(JsonModel.class, clazz);
        return gson.toJson(this, objectType);
    }

    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }

}
