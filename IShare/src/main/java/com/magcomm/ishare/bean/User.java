package com.magcomm.ishare.bean;

/**
 * 用户信息模型
 * 作者:Created by yezesong on 16-3-15:06
 * 邮箱: yezesong@magcomm.cn
 */
public class User {
    public static final String KEY_CACHE = "key_user_cache";
    public String id;
    //头像
    public String imgUrl;
    //分享号
    public String shareNum;
    //昵称
    public String nickName;
    //性别
    public String sex;

    // 请求json的key值
    public static final class Key {
        // 用户ID
        public static final String ID = "id";
        // 用户名
        public static final String USER_NAME = "username";
        // 密码
        public static final String PASS_WORD = "password";
        // 新密码
        public static final String NEW_PASS_WORD = "newpassword";
        // 手机号码
        public static final String PHONE = "phone";
        // 验证码
        public static final String VERIFY_CODE = "code";
        // 分享号
        public static final String SHARE_ID = "shareid";

        public static final String TOKEN = "token";
        // 头像
        public static final String HEADER_IMAGE = "headPic";
    }

    // User的配置信息
    public final static class Configure {
        public static final int SEX_MALE = 0;
        public static final int SEX_FEMALE = 1;
    }
}
