package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/1/8.
 * 当 message = "message" 时意味着用户信息改变了,需要更新用户信息
 */
public class UserMessageEvent {
    public final String message;
    public UserMessageEvent(String message){
        this.message = message;
    }
}
