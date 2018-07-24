package me.victoriest.photio.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 */
public class Md5Utils {
    // 盐值
    private static final String salt = "Wenduqa@$@4&#%^$*";

    /**
     * MD5加密 本项目中不推荐使用该方法，请使用 getMD5(String input, String userAccount) 方法加密
     *
     * @param input 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String getMD5(String input) {
        return DigestUtils.md5Hex(input + salt);
    }

    /**
     * 用户初始化密码MD5加密
     * 实际中考虑到需要批量导入初始化数据或者重置批量用户密码这种方式生成密码有点繁琐，考虑到初始化密码也是必须修改的，这里就不使用account作为盐值了
     *
     * @param input       需要加密的字符串
     * @param userAccount 用户账号 (这里传入用户账号是为了保证不同的账号虽然初始化密码是一样的，但是MD5加密后的密码不一样，更安全)
     * @return 返回加密后的字符串
     */
    public static String getMD5(String input, String userAccount) {
        return DigestUtils.md5Hex(input + salt + userAccount);
    }

    public static void main(String[] args) {
        System.out.println(getMD5("123123123"));
    }
}
