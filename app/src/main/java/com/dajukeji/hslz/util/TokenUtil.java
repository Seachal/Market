package com.dajukeji.hslz.util;

import android.content.Context;
import android.content.Intent;

import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.im.activity.ChatActivity;
import com.tencent.TIMConversationType;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class TokenUtil {

    /**
     * 判断是否登录，跳转聊天或者微信登录
     *
     * @param context
     * @param targetChatID   聊天对象id
     * @param targetNickname
     */
    public static void openChat(Context context, String targetChatID, String targetNickname) {
        if (SPUtil.getPrefString("token", "").equals("")) { //还未登录
            context.startActivity(new Intent(context, WeChatLoginActivity.class));
        } else if (SPUtil.getPrefString("chatId", "").equals("") || SPUtil.getPrefString("chatSig", "").equals("")) {
            MLog.INSTANCE.e("聊天启动失败,chatId或chatSig为空");
            ToastUtils.getInstance().showToast(context, "无法聊天，请联系数字绿州客服");
        } else if (targetChatID.equals("")) {
            MLog.INSTANCE.e("聊天启动失败,targetChatID为空");
            ToastUtils.getInstance().showToast(context, "无法聊天，请联系数字绿州客服");
        } else {
            ChatActivity.navToChat(context, targetChatID, TIMConversationType.C2C, targetNickname);
        }
    }


}
