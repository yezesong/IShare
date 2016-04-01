package com.magcomm.sharelibrary.utils;

import android.util.Log;

/**
 * 作者:Created by yezesong on 16-3-16:27
 * 邮箱: yezesong@magcomm.cn
 * <p/>
 * Debug的控制类
 */
public class Debug {
    private static final String TAG = "IShare";
    /**
     * 开发需要的log 正式版本会通过宏控自动规避掉
     * 一般敏感信息 是敏感信息log
     *
     * @param log
     */
    public static void log(String log) {
        Log.d(TAG, log);
    }
}
