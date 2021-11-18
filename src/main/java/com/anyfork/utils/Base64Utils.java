package com.anyfork.utils;

import org.bouncycastle.util.encoders.UTF8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: Base64Utils
 * @Description: base64 加解密工具
 * @Author: 小紫念沁
 * @Date: 2021/11/17 15:25
 * @Version 1.0
 */
public class Base64Utils {
    
    /**
     * base64加密
     * @param plaintext 明文
     * @return string 密文
     **/
    public static String encodeBase64(String plaintext){
        return Base64.getEncoder().encodeToString(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64加密
     * @param plaintext 明文
     * @return string 密文
     **/
    public static String encodeBase64(byte[] plaintext){
        return Base64.getEncoder().encodeToString(plaintext);
    }

    /**
     * base64解密
     * @param ciphertext 密文
     * @return string 明文
     **/
    public static String decodeBase64(String ciphertext){
        return new String(bytesDecodeBase64(ciphertext));
    }

    /**
     * base64解密
     * @param ciphertext 密文
     * @return byte[] 明文
     **/
    public static byte[] bytesDecodeBase64(String ciphertext) {
        return Base64.getDecoder().decode(ciphertext);
    }
}
