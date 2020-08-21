package com.dajukeji.hslz.im.utils;

import android.text.TextUtils;

import com.dajukeji.hslz.util.MLog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;

/**
 * iid refresh
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final static String TAG = "InstanceID";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        MLog.INSTANCE.d(TAG, "Refreshed token: " + refreshedToken);

        if(!TextUtils.isEmpty(refreshedToken)) {
            TIMOfflinePushToken param = new TIMOfflinePushToken();
            param.setToken(refreshedToken);
            param.setBussid(169);
            TIMManager.getInstance().setOfflinePushToken(param);
        }
    }
}
