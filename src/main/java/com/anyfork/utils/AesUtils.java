package com.anyfork.utils;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: AesUtils
 * @Description: AES加解密工具
 * @Author: 小紫念沁
 * @Date: 2021/11/16 18:26
 * @Version 1.0
 */
public class AesUtils {

    private static final String IV_PARAMETER_SPEC =generateRandomKey();

    /**
     * 生成16位随机数
     * @return 返回随机数
     **/
    public static String generateRandomKey() {
        return IdWorker.get32UUID().substring(0, 16);
    }
   
    /**
     * 将输入的秘钥长度进行处理，截取16位秘钥
     * @param key 对称加密秘钥
     * @return SecretKeySpec
     **/
    private static SecretKeySpec generateAesKey(String key) {
        byte[] var1 = new byte[16];
        int var2 = 0;
        byte[] var3 = key.getBytes(StandardCharsets.UTF_8);
        for (byte var6 : var3) {
            int var10001 = var2++;
            var1[var10001 % 16] ^= var6;
        }
        return new SecretKeySpec(var1, "AES");
    }

    /**
     * 解码
     **/
    private static byte[] decrypt(String key, byte[] str) {
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, generateAesKey(key), new IvParameterSpec(IV_PARAMETER_SPEC.getBytes(StandardCharsets.UTF_8)));
            return cipher.doFinal(str);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    /**
     * 编码
     **/
    private static byte[] encrypt(String key, String plaintext) {
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, generateAesKey(key), new IvParameterSpec(IV_PARAMETER_SPEC.getBytes(StandardCharsets.UTF_8)));
            return cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
           throw  new RuntimeException(e);
        }
    }
    
    /**
     * AES加密结果封装成HEX
     * @param key 秘钥
     * @param plaintext 明文
     * @return String 密文
     **/
    public static String encryptHexString(String key,String plaintext){
        return bytesToHex(encrypt(key, plaintext));
    }

    /**
     * AES加密结果封装成Base64
     * @param key 秘钥
     * @param plaintext 明文
     * @return String 密文
     **/
    public static String encryptToBase64(String key,String plaintext){
        return Base64.getEncoder().encodeToString(encrypt(key, plaintext));
    }

    /**
     * 密文为HEX解密
     * @param key 秘钥
     * @param ciphertext 密文
     * @return String 明文
     **/
    public static String hexDecryptString(String key,String ciphertext){
        //将16进制密文转换为byte[]
        byte[] bytes = hexToByteArray(ciphertext);
        //解码
        return new String(decrypt(key,bytes));
    }

    /**
     * 密文为base64解码
     * @param key 秘钥
     * @param ciphertext 密文
     * @return String 明文
     **/
    public static String base64DecryptString(String key,String ciphertext){
        //将base64转换为byte[]
        byte[] bytes = Base64.getDecoder().decode(ciphertext.getBytes(StandardCharsets.UTF_8));
        //解码
        return new String(decrypt(key,bytes));
    }
        
    /**
     * 字节数组转HEX
     * @param md 字节数组
     * @return String 16进制
     **/
    public static String bytesToHex(byte[] md) {
        StringBuilder stringBuilder = new StringBuilder(md.length * 2);
        for (byte byte0 : md) {
            String hex = Integer.toHexString(byte0& 0xFF);
            if(hex.length() < 2){
                stringBuilder.append(0);
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

    /**
     * hex字符串转byte数组
     * @param inHex 待转换的Hex字符串
     * @return  转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex){
        int hexLen = inHex.length();
        byte[] result;
        if (hexLen % 2 == 1){
            //奇数
            hexLen++;
            result = new byte[(hexLen/2)];
            inHex="0"+inHex;
        }else {
            //偶数
            result = new byte[(hexLen/2)];
        }
        int j=0;
        for (int i = 0; i < hexLen; i+=2){
            result[j]= (byte) Integer.parseInt(inHex.substring(i,i+2),16);
            j++;
        }
        return result;
    }
}


