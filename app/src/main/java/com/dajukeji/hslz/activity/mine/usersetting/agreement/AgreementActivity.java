package com.dajukeji.hslz.activity.mine.usersetting.agreement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.base.BaseActivity;

import butterknife.OnClick;

/**
 * 用户协议
 */

public class AgreementActivity extends BaseActivity {

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_agreement);
        setTitleBar(R.string.text_user_agreement, true);
    }

    @OnClick({R.id.agreement_service_agreement, R.id.agreement_privacy_policy})
    public void onClick(View view){
        Intent mIntent = new Intent(AgreementActivity.this, PrivacyPolicyActivity.class);
        switch (view.getId())
        {
            case R.id.agreement_service_agreement:
                mIntent.putExtra("page", "service");
                break;

            case R.id.agreement_privacy_policy:
                mIntent.putExtra("page", "policy");
                break;
        }
        this.startActivity(mIntent);
    }
}
