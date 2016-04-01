package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.config.AppConfig;
import com.magcomm.sharelibrary.views.SettingsItem;
import com.magcomm.sharelibrary.views.TopBar;


/**
 * 作者:Created by yezesong on 16-3-9:16
 * 邮箱: yezesong@magcomm.cn
 */
public class SettingsActivity extends BaseActivity {
    private TopBar mTopBar;
    private Button mBtnQuit;
    private SettingsItem mDeleteCache;

    @Override
    public void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);
        mTopBar = bindView(R.id.topbar);
        mBtnQuit = bindView(R.id.setting_quit);
        mDeleteCache = bindView(R.id.setting_clear);
        mDeleteCache.setOnClickListener(this);
        mTopBar.setOnTopBarListener(this);
        mBtnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_clear:
                break;
            case R.id.setting_quit:
                AppConfig.getInstance().putBoolean(AppConfig.LOGINED, false);
                Intent toMain = new Intent(SettingsActivity.this, LoginActivity.class);
                toMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(toMain);
                finish();
                break;
        }
    }

    @Override
    public void onLeftClick(View v) {
        super.onLeftClick(v);
    }
}
