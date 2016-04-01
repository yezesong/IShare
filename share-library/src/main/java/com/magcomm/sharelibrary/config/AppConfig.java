/*
 * Copyright (c) 2015, 张涛.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.magcomm.sharelibrary.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.magcomm.sharelibrary.LibApp;
import com.magcomm.sharelibrary.utils.FileUtils;

import java.io.File;
import java.util.Set;

/**
 * 配置文件常量
 * Created by yezesong on 16-2-23.
 */

public class AppConfig {
    public static final double VERSION = 2.6;

    /**
     * 错误处理广播
     */
    public static final String RECEIVER_ERROR = AppConfig.class.getName()
            + "org.magcomm.android.frame.error";
    /**
     * 无网络警告广播
     */
    public static final String RECEIVER_NOT_NET_WARN = AppConfig.class.getName()
            + "org.magcomm.android.frame.notnet";
    /**
     * preference键值对
     */

    /**
     * App根目录.
     */
    public static final String APP_PATH_ROOT = FileUtils.getRootPath().getAbsolutePath() + File.separator;
    public static final String SETTING_FILE = "ishare_preference";
    public static final String ONLY_WIFI = "only_wifi";
    public static final String saveFolder = APP_PATH_ROOT + "IShare";
    public static final String httpCachePath = saveFolder + "/httpCache";
    public static final String audioPath = File.separator + saveFolder + "/audio/";
    public static final String LOGINED = "is_login";
    private static AppConfig appConfig;

    private SharedPreferences preferences;

    /**
     * 是否是测试环境.
     */
    public static final boolean DEBUG = false;

    private AppConfig() {
        preferences = LibApp.getInstance().getSharedPreferences("ishare", Context.MODE_PRIVATE);
        FileUtils.initDirectory(saveFolder);
        FileUtils.initDirectory(audioPath);
    }

    public static AppConfig getInstance() {
        if (appConfig == null)
            appConfig = new AppConfig();
        return appConfig;
    }

    public void putInt(String key, int value) {
        preferences.edit().putInt(key, value).commit();
    }

    public int getInt(String key, int defValue) {
        return preferences.getInt(key, defValue);
    }

    public void putString(String key, String value) {
        preferences.edit().putString(key, value).commit();
    }

    public String getString(String key, String defValue) {
        return preferences.getString(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        preferences.edit().putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return preferences.getBoolean(key, defValue);
    }

    public void putLong(String key, long value) {
        preferences.edit().putLong(key, value).commit();
    }

    public long getLong(String key, long defValue) {
        return preferences.getLong(key, defValue);
    }

    public void putFloat(String key, float value) {
        preferences.edit().putFloat(key, value).commit();
    }

    public float getFloat(String key, float defValue) {
        return preferences.getFloat(key, defValue);
    }

    public void putStringSet(String key, Set<String> value) {
        preferences.edit().putStringSet(key, value).commit();
    }

    public Set<String> getStringSet(String key, Set<String> defValue) {
        return preferences.getStringSet(key, defValue);
    }
}
