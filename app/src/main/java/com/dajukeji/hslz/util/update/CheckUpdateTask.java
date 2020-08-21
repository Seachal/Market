package com.dajukeji.hslz.util.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.dajukeji.hslz.MainActivity;

import com.dajukeji.hslz.domain.javaBean.CheckVersionBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.VersionUtil;


/**
 * @author feicien (ithcheng@gmail.com)
 * @since 2016-07-05 19:21
 */
class CheckUpdateTask extends AsyncTask<Void, Void, String> {

    private ProgressDialog dialog;
    private Context mContext;
    private boolean mShowProgressDialog;
    private static final String url = HttpAddress.mBaseUrl2+HttpAddress.checkUpdate;

    CheckUpdateTask(Context context, boolean showProgressDialog) {
        this.mContext = context;
        this.mShowProgressDialog = showProgressDialog;
    }


    protected void onPreExecute() {
        if (mShowProgressDialog && !(mContext instanceof MainActivity)) {
            dialog = new ProgressDialog(mContext);
            dialog.setMessage(mContext.getString(R.string.android_auto_update_dialog_checking));
            dialog.show();
        }
    }


    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (result != null && !TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }

    private void parseJson(String result) {
        try {
            Gson gson = new Gson();
            CheckVersionBean checkVersionBean = gson.fromJson(result, CheckVersionBean.class);

            String updateMessage = checkVersionBean.getContent().getContent();
            String apkUrl =HttpAddress.mOldBaseUrl + checkVersionBean.getContent().getUrl();
            int apkCode = checkVersionBean.getContent().getVersionCode();

            int versionCode = VersionUtil.getVersionCode(mContext);

            if (apkCode > versionCode) {
                showDialog(mContext, updateMessage, apkUrl);
            } else if (mShowProgressDialog && !(mContext instanceof MainActivity)) {
                Toast.makeText(mContext, mContext.getString(R.string.android_auto_update_toast_no_new_update), Toast.LENGTH_SHORT).show();
            }
        } catch (JsonSyntaxException e) {
        }
    }


    /**
     * Show dialog
     */
    private void showDialog(Context context, String content, String apkUrl) {
        UpdateDialog.show(context, content, apkUrl);
    }

    @Override
    protected String doInBackground(Void... args) {
        return HttpUtils.get(url);
    }
}
