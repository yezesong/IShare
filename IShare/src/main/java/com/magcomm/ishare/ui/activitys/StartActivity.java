package com.magcomm.ishare.ui.activitys;

import android.content.Intent;

import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.config.AppConfig;

/**
 * Created by yezesong on 16-2-23.
 */
public class StartActivity extends BaseActivity {

    public static final String TAG = StartActivity.class.getSimpleName();

    private void jumpTo() {
        //判断是否已经登录
        boolean isLogined = AppConfig.getInstance().getBoolean(AppConfig.LOGINED, false);
        Intent jumpIntent = new Intent();
        if (!isLogined) {
            //登录界面
            jumpIntent.setClass(aty, LoginActivity.class);
        } else {
            //主界面
            jumpIntent.setClass(aty, MainActivity.class);
        }
        startActivity(jumpIntent);
        finish();
    }

    @Override
    public void setRootView() {
        jumpTo();
    }
}
