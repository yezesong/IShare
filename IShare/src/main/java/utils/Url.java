package utils;

import com.magcomm.sharelibrary.utils.ShareUtils;

/**
 * 作者:Created by yezesong on 16-3-16:04
 * 邮箱: yezesong@magcomm.cn
 */
public class Url {
    /**
     * 服务器IP、端口号
     */
    private static String BASE_URL = "http://192.168.0.68:8080/oauth2";
    /**
     * 注册URL
     */
    public static String REGISTER_URL = ShareUtils.plusString(BASE_URL, "/oauth2/regis");

    /**
     * 获取验证码
     */
    public static String VERIFICATION_URL = ShareUtils.plusString(BASE_URL, "/oauth2/getsms");

    /**
     * 注册-获取语音验证码
     */
    public static String VOICE_URL = ShareUtils.plusString(BASE_URL, "/oauth2/getvoice");
}
