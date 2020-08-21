package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/1/27 0027.
 * 在地址管理中改变默认地址，删除地址，更新地址的事件
 */

public class AddressChangeEvent {

    public final boolean removed;

    public AddressChangeEvent(boolean removed) {
        this.removed = removed;
    }
}
