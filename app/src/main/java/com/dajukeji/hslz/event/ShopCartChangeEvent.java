package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/1/27 0027.
 * 添加产品到购物车事件
 */

public class ShopCartChangeEvent {
    public final String message;

    public ShopCartChangeEvent(String message) {
        this.message = message;
    }
}
