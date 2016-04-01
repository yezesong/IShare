package com.magcomm.sharelibrary.utils;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * ToastUtils
 *
 * @author yezesong date 2016-2-25
 */
public class ToastUtils {

    private static Application context;

    /**
     * 仅传入上下文
     *
     * @param mContext
     */
    public static void init(Application mContext) {
        ToastUtils.context = mContext;
    }

    public static void show(int resId) {
        show(context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration) {
        show(context.getResources().getText(resId), duration);
    }

    public static void show(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void showLong(int resId) {
        show(context.getResources().getText(resId), Toast.LENGTH_LONG);
    }

    public static void showLong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void show(CharSequence text, int duration) {
        Toast.makeText(context, text, duration).show();
    }

    public static void show(int resId, Object... args) {
        show(String.format(context.getResources().getString(resId), args), Toast.LENGTH_SHORT);
    }

    public static void show(String format, Object... args) {
        show(String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(int resId, int duration, Object... args) {
        show(String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /*这是一个带图片的Toast*/
    public static void showImage(Context context, String text, int Res) {
        Toast toast = Toast.makeText(context,
                text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setPadding(80, 30, 80, 30);
        imageCodeProject.setImageResource(Res);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }
}