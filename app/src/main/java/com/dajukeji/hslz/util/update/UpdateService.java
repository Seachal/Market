package com.dajukeji.hslz.util.update;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.dajukeji.hslz.util.LogUtil;

import java.io.File;

/**
 * Created by codeest on 16/10/10.
 * 版本更新
 * <p>
 * 必要权限：<uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
 * 表现为：在8.0以上，安装其它应用时提示（默认禁止此应用安装软件，要允许吗?）。不写则无法安装引用
*/
public class UpdateService extends Service {
    private BroadcastReceiver receiver;
    private static final String apkSubPath = "App.apk";
    public static final String apkPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + apkSubPath;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //监听apk下载成功广播。
        addDownloadSucceedInstallListener();

        deleteOldApk();
        startDownload(intent.getStringExtra(URL), intent.getStringExtra(NAME));
        return Service.START_STICKY;
    }

    /**下载成功后安装*/
    private void addDownloadSucceedInstallListener() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                unregisterReceiver(receiver);
                receiver = null;

                //打开下载的apk
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                Uri apkUri = getDownloadUri(context);

                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);

                //关闭Service
                stopSelf();
            }
        };
        //下载成功后会发出广播,监听这个“下载完成”广播
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    private Uri getDownloadUri(Context context) {
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(//API24以上使用FileProvider共享文件
                    context,
                    context.getPackageName() + ".fileprovider",//唯一标识
                    new File(apkPath));//目标文件
        } else {
            apkUri = Uri.fromFile(new File(apkPath));
        }
        return apkUri;
    }

    /**移除旧安装包*/
    private void deleteOldApk() {
        File file = new File(apkPath);
        if (file.exists()) {
            File oldFile = new File(file.getParentFile(), file.getName() + "_old_" + System.currentTimeMillis());
            boolean b = file.renameTo(oldFile);
            LogUtil.info("Q:改名成功吗? \nA:" + b + "安装包还存在吗:" + file.exists());
            oldFile.deleteOnExit();
        }
    }

    private void startDownload(String appUrl, String appName) {
        if (!TextUtils.isEmpty(appUrl)) {
            //使用自带的下载服务
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(appUrl)); //TODO 如何设置下载
            request.setTitle(appName + "下载中");
//            request.setDescription("新版本下载中");
            request.setMimeType("application/vnd.android.package-archive");//mime - 描述文件内容的因特网标准,用于区分文件内容。这里对应 .apk 文件

//            request.setDestinationUri(getDownloadUri(getApplication())); 只能下载到外部存储中
            request.setDestinationInExternalFilesDir(getApplication(), Environment.DIRECTORY_DOWNLOADS, apkSubPath);//下载至
            request.allowScanningByMediaScanner();//下载的文件可以被 MediaScanner 扫描到
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);//可见的
            DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
            long id = dm.enqueue(request);//下载任务ID
            Cursor c = dm.query(new DownloadManager.Query().setFilterById(id));
            int[] bytesAndStatus = new int[]{-1, -1, 0};
            try {
                if (c != null && c.moveToFirst()) {
                    bytesAndStatus[0] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    bytesAndStatus[1] = c.getInt(c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    bytesAndStatus[2] = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
                }
            } finally {
                if (c != null)
                    c.close();
            }
        }
    }

    /**应用名*/
    private String getApplicationName() {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) packageManager.getApplicationLabel(applicationInfo);
    }

    private static final String URL = "url";
    private static final String NAME = "name";

    public static Intent getStartIntent(Context context, String url, String name) {
//        apkPath = context.getCacheDir() + "/" + apkSubPath;
        return new Intent(context, UpdateService.class).putExtra(URL, url).putExtra(NAME, name);
    }
}
