package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/1/8.
 * 订单改变
 */

public class OrderChangeEvent {
    public final String message;
    public OrderChangeEvent(String message){
        this.message = message;
    }
}
