package com.magcomm.sharelibrary;

import android.app.Application;

import java.util.Map;

/**
 * Created by yezesong on 16-2-25.
 */
public class LibApp extends Application {
    public static Map<String, Long> map;
    // 用于存放倒计时时间
    private static LibApp _instance;
    public static int screenW;
    public static int screenH;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    public static LibApp getInstance() {
        return _instance;
    }
}
