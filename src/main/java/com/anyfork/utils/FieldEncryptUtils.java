package com.anyfork.utils;

import com.anyfork.enums.Algorithm;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: FieldEncryptUtils
 * @Description: 加解密统一处理工具
 * @Author: 小紫念沁
 * @Date: 2021/11/11 17:28
 * @Version 1.0
 */
public class FieldEncryptUtils {

    public static String encryptOrDecryptHandler(Algorithm algorithm, String keyPassword, boolean flag, String publicKey, String plainTextOrCipherText) throws Exception {
        if (algorithm == Algorithm.MD5_32) {
            return flag ? Md5Utils.md532(plainTextOrCipherText,false) : plainTextOrCipherText;
        } else if (algorithm == Algorithm.MD5_16) {
            return flag ? Md5Utils.md516(plainTextOrCipherText,false) : plainTextOrCipherText;
        } else if (algorithm == Algorithm.AES) {
            return flag ? AesUtils.encryptHexString(keyPassword, plainTextOrCipherText) : AesUtils.hexDecryptString(keyPassword, plainTextOrCipherText);
        } else if (algorithm == Algorithm.SM2) {
            return flag ? Sm2Utils.encryptToBase64String(plainTextOrCipherText, publicKey) : Sm2Utils.decryptFromBase64String(plainTextOrCipherText, publicKey);
        } else if (algorithm == Algorithm.SM3) {
            return flag ? Sm3Utils.encode(plainTextOrCipherText) : plainTextOrCipherText;
        } else if (algorithm == Algorithm.SM4) {
            return flag ? Sm4Utils.encrypt(keyPassword, plainTextOrCipherText) : Sm4Utils.decrypt(keyPassword, plainTextOrCipherText);
        } else if (algorithm == Algorithm.RSA) {
            return flag ? RsaUtils.encryptByPublicKey(plainTextOrCipherText, publicKey) : RsaUtils.decryptByPrivateKey(plainTextOrCipherText, publicKey);
        } else if (algorithm == Algorithm.BASE64) {
            return flag ? Base64Utils.encodeBase64(plainTextOrCipherText) : Base64Utils.decodeBase64(plainTextOrCipherText);
        } else {
            if (algorithm == Algorithm.PBEWithHMACSHA512AndAES_256) {
                AES256TextEncryptor var5 = new AES256TextEncryptor();
                var5.setPassword(keyPassword);
                return flag ? var5.encrypt(plainTextOrCipherText) : var5.decrypt(plainTextOrCipherText);
            } else {
                StandardPBEStringEncryptor var4 = new StandardPBEStringEncryptor();
                var4.setPassword(keyPassword);
                var4.setAlgorithm(algorithm.name());
                return flag ? var4.encrypt(plainTextOrCipherText) : var4.decrypt(plainTextOrCipherText);
            }
        }
    }
}
