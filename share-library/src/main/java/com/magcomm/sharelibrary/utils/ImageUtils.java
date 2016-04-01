package com.magcomm.sharelibrary.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

/**
 * 作者:Created by yezesong on 16-3-23:55
 * 邮箱: yezesong@magcomm.cn
 */
public class ImageUtils {
    /**
     * Android从相册中获取图片以及路径 并且存储到自己的目录中
     */
    public static String getPicturePath(Intent data, Context context) {

        String path = "";
        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
        ContentResolver resolver = context.getContentResolver();
        // 此处的用于判断接收的Activity是不是你想要的那个
        Uri originalUri = null;
        try {
            originalUri = data.getData(); // 获得图片的uri
            // 这里开始的第二部分，获取图片的路径：
            String[] proj = {MediaStore.Images.Media.DATA};
            // 好像是android多媒体数据库的封装接口，具体的看Android文档
            Cursor cursor = resolver.query(originalUri, proj, null, null, null);
            // 按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            // 将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();
            // 最后根据索引值获取图片路径
            path = cursor.getString(column_index);
            if (TextUtils.isEmpty(path)) {
                return originalUri.getPath();
            }
            return path;
        } catch (Exception e) {
            Log.e("IShare", e.toString());
            if (TextUtils.isEmpty(path)) {
                return originalUri.getPath();
            }
            return path;
        }

    }
}
