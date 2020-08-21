package com.dajukeji.hslz.event;

/**
 * Created by Administrator on 2018/2/6 0006.
 */

public class WXLoginEvent {
    public String chatId;
    public String chatSig;

    public WXLoginEvent(String chatId, String chatSig) {
        this.chatId = chatId;
        this.chatSig = chatSig;
    }
}
