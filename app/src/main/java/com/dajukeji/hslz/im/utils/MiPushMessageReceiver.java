package com.dajukeji.hslz.im.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.dajukeji.hslz.util.MLog;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;
import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 小米推送接收
 */
public class MiPushMessageReceiver extends PushMessageReceiver {

    private final String TAG = "MiPushMessageReceiver";
    private String mRegId;
    private String mTopic;
    private String mAlias;
    private String mAccount;
    private String mStartTime;
    private String mEndTime;

    private long mBussId = 72;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        MLog.INSTANCE.e(TAG,"onReceivePassThroughMessage is called. " + message.toString());
        MLog.INSTANCE.e(TAG, getSimpleDate() + " " + message.getContent());

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
        MLog.INSTANCE.e(TAG, "regId: " + mRegId + " | topic: " + mTopic + " | alias: " + mAlias
                + " | account: " + mAccount + " | starttime: " + mStartTime + " | endtime: " + mEndTime);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        MLog.INSTANCE.e(TAG,"onNotificationMessageClicked is called. " + message.toString());
        MLog.INSTANCE.e(TAG, getSimpleDate() + " " + message.getContent());

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }
//        MiPushClient.clearNotification(context);

        MLog.INSTANCE.e(TAG, "regId: " + mRegId + " | topic: " + mTopic + " | alias: " + mAlias
                + " | account: " + mAccount + " | starttime: " + mStartTime + " | endtime: " + mEndTime);
        Intent i = new Intent();
        i.setClassName("com.tencent.qcloud.timchat", "com.tencent.qcloud.timchat.ui.SplashActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        MLog.INSTANCE.e(TAG,"onNotificationMessageArrived is called. " + message.toString());
        MLog.INSTANCE.e(TAG, getSimpleDate() + " " + message.getContent());

        if (!TextUtils.isEmpty(message.getTopic())) {
            mTopic = message.getTopic();
        } else if (!TextUtils.isEmpty(message.getAlias())) {
            mAlias = message.getAlias();
        }

        MLog.INSTANCE.e(TAG, "regId: " + mRegId + " | topic: " + mTopic + " | alias: " + mAlias
                + " | account: " + mAccount + " | starttime: " + mStartTime + " | endtime: " + mEndTime);
    }

    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        MLog.INSTANCE.e(TAG, "onCommandResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);

        MLog.INSTANCE.e(TAG, "cmd: " + command + " | arg1: " + cmdArg1 + " | arg2: " + cmdArg2
                + " | result: " + message.getResultCode() + " | reason: " + message.getReason());

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSET_ACCOUNT.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAccount = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }

        MLog.INSTANCE.e(TAG, "regId: " + mRegId + " | topic: " + mTopic + " | alias: " + mAlias
                + " | account: " + mAccount + " | starttime: " + mStartTime + " | endtime: " + mEndTime);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        MLog.INSTANCE.e(TAG, "onReceiveRegisterResult is called. " + message.toString());
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);

        MLog.INSTANCE.e(TAG, "cmd: " + command + " | arg: " + cmdArg1
                + " | result: " + message.getResultCode() + " | reason: " + message.getReason());

        if (MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;

                TIMOfflinePushToken param = new TIMOfflinePushToken();
                param.setToken(mRegId);
                param.setBussid(mBussId);
                TIMManager.getInstance().setOfflinePushToken(param);
            }
        }

        MLog.INSTANCE.e(TAG, "regId: " + mRegId + " | topic: " + mTopic + " | alias: " + mAlias
                + " | account: " + mAccount + " | starttime: " + mStartTime + " | endtime: " + mEndTime);

    }

    @SuppressLint("SimpleDateFormat")
    private static String getSimpleDate() {
        return new SimpleDateFormat("MM-dd hh:mm:ss").format(new Date());
    }

}
