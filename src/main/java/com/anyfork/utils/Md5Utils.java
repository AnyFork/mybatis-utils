package com.anyfork.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: EncryptUtils
 * @Description: 加解密工具
 * @Author: 小紫念沁
 * @Date: 2021/11/16 10:17
 * @Version 1.0
 */
public class Md5Utils {

    private static final String HEX = "0123456789ABCDEF";

    /**
     *  字符串md5加密,生成32位加密字符串
     * @param plaintext 字符串明文
     * @param flag 是否大小写
     * @return 加密后生成32位加密字符串
     */
    public static String md532(String plaintext,boolean flag) {
        try {
            byte[] btInput = plaintext.getBytes(StandardCharsets.UTF_8);
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuilder stringBuilder = new StringBuilder(md.length * 2);
            for (byte byte0 : md) {
                //高4位转换为HEX
                stringBuilder.append(HEX.charAt(byte0 >>> 4 & 0xf));
                //后四位转换为HEX
                stringBuilder.append(HEX.charAt(byte0& 0xf));
            }
            return flag?stringBuilder.toString().toUpperCase():stringBuilder.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  字符串md5加密,生成16位加密字符串
     * @param encryptStr 字符串明文
     * @param flag 是否大小写
     * @return 加密后生成16位加密字符串
     */
    public static String md516(String encryptStr,boolean flag) {
        String mdStr = md532(encryptStr, flag);
        return mdStr==null?null:mdStr.substring(8,24);
    }
}
