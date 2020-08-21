package com.dajukeji.hslz.util.update;

import android.content.Context;

public class UpdateChecker {

    public static void checkForDialog(Context context) {
        if (context != null) {
            new CheckUpdateTask(context, true).execute();
        }
    }
}
