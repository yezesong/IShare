package utils;

import com.magcomm.ishare.IShareApp;

/**
 * 作者:Created by yezesong on 16-3-23:34
 * 邮箱: yezesong@magcomm.cn
 */
public class Toast {
    public static void show(String message) {
        android.widget.Toast.makeText(IShareApp.getInstance(), message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
