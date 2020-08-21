package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/1/8.
 * 登陆时，是否有免单订单
 */

public class HavaFreeOrderEvent {
    public final String message;
    public HavaFreeOrderEvent(String message){
        this.message = message;
    }
}
