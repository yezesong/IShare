package com.magcomm.ishare;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.magcomm.ishare.http.OkHttpStack;
import com.magcomm.sharelibrary.LibApp;
import com.magcomm.sharelibrary.config.AppConfig;
import com.magcomm.sharelibrary.utils.Logger;
import com.magcomm.sharelibrary.utils.ToastUtils;

/**
 * Created by yezesong on 16-2-23.
 */
public class IShareApp extends LibApp {

    public static RequestQueue mQueues;

    @Override
    public void onCreate() {
        super.onCreate();
        //HttpConfig.CACHEPATH = AppConfig.httpCachePath;
        //CrashHandler.create(this);
        mQueues = Volley.newRequestQueue(getInstance(), new OkHttpStack());
        Logger.setTag("IShare");
        Logger.setDebug(true);//这样就能看到请求过程和日志
        AppConfig.getInstance();
        ToastUtils.init(this);
    }

    public static RequestQueue getHttpQueues() {
        return mQueues;
    }
}
