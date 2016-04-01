package com.magcomm.sharelibrary.utils;

import android.text.TextUtils;

/**
 * Created by yezesong on 16-2-25.
 */
public class ShareUtils {

    public static final int RESULT_CODE_CANCEL = 0x100;

    /*public static int[] getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getResources().getDisplayMetrics();
        int[] size = {dm.widthPixels, dm.heightPixels};
        return size;
    }*/

    /**
     * 判断字串是否为合格的手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 隐藏手机号码中间四位数字
     *
     * @param mobile: 手机号码
     * @return
     */
    public static String getEncodePhone(String mobile) {
        return mobile.substring(0, 3) + "****" + mobile.substring(7, mobile.length());
    }

    /**
     * 字符串连接
     *
     * @param strs
     * @return
     */
    public static String plusString(String... strs) {
        StringBuilder builder = new StringBuilder();
        for (String str : strs) {
            if (!TextUtils.isEmpty(str)) {
                builder.append(str);
            }
        }
        return builder.toString();
    }
}
