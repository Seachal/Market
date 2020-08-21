package com.dajukeji.hslz.activity.mine.usersetting;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.activity.AboutActivity;
import com.dajukeji.hslz.activity.mine.usersetting.agreement.AgreementActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.base.BaseActivity;
import com.dajukeji.hslz.event.UserMessageEvent;
import com.dajukeji.hslz.util.DataCleanManager;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.VersionUtil;
import com.dajukeji.hslz.util.update.UpdateChecker;
import com.dajukeji.hslz.view.OrderDialog;
import com.dajukeji.hslz.view.dialog.ComplaintAppraiseDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的设置
 */

public class UserSettingActivity extends BaseActivity {

    @BindView(R.id.setting_total_cache)
    TextView mTvTotalCache;

    @BindView(R.id.setting_version)
    TextView mTvVersion;

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_setting);
        setTitleBar(R.string.text_setting, true);
    }

    @Override
    protected void initView() {
        try {
            mTvVersion.setText("当前版本 " + VersionUtil.getVersionName(this));
            mTvTotalCache.setText(DataCleanManager.getTotalCacheSize(this)); // 获得app缓存
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.setting_faq, R.id.setting_user_agreement, R.id.setting_feedback, R.id.setting_appraise, R.id.setting_clear_cache, R.id.setting_login_out ,R.id.setting_version_update,R.id.setting_gy})
    public void onClick(View view){
        switch (view.getId())
        {
            case R.id.setting_faq :
                startActivity(new Intent(UserSettingActivity.this, CommonProblemActivity.class));
                break;

            case R.id.setting_user_agreement :
                startActivity(new Intent(UserSettingActivity.this, AgreementActivity.class));
                break;

            case R.id.setting_feedback :
                if(!SPUtil.getPrefString("token","").equals("")){
                    if(!SPUtil.getPrefString("phoneNumber","").equals("")){
                        startActivity(new Intent(UserSettingActivity.this, FeedBackActivity.class));
                    }else{
                        startActivity(new Intent(UserSettingActivity.this, MobailePhoneLoginActivity.class));
                    }
                }else {
                    startActivity(new Intent(UserSettingActivity.this, WeChatLoginActivity.class));
                }
                break;
            case R.id.setting_gy :
                startActivity(new Intent(UserSettingActivity.this, AboutActivity.class));
                break;

            case R.id.setting_appraise :
                ComplaintAppraiseDialog dialog = new ComplaintAppraiseDialog(this).builder();
                dialog.show();
                dialog.setOnBackListener(new ComplaintAppraiseDialog.onBackListener() {
                    @Override
                    public void toAppraise() {
                        toAppMarket();
                    }

                    @Override
                    public void toComplaint() {
                        toAppMarket();
                    }
                });
                break;

            case R.id.setting_clear_cache :
                showAlertCache();
                break;
            case R.id.setting_version_update:
                UpdateChecker.checkForDialog(UserSettingActivity.this);
                break;
            case R.id.setting_login_out :
                if(!SPUtil.getPrefString("token","").equals("")){
                    OrderDialog orderDialog = new OrderDialog(UserSettingActivity.this,"确认退出?","","确认");
                    orderDialog.show();
                    orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                        @Override
                        public void sureClick() {
                            SPUtil.clearAll();
                            SPUtil.setPrefBoolean("FirstRun",false);
                            EventBus.getDefault().post(new UserMessageEvent("user_out"));
                            finish();
                        }
                    });
                }else {
                    finish();
                    return;
                }
                break;

        }
    }

    private void  showAlertCache(){// 清理缓存
        new AlertDialog.Builder(this)
                .setMessage("是否清空缓存?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener
                        () {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataCleanManager.clearAllCache(UserSettingActivity.this);// 清理缓存
                        initView();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    private void toAppMarket(){
        try{
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch(Exception e){
            Toast.makeText(UserSettingActivity.this, "您的手机没有安装Android应用市场", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
