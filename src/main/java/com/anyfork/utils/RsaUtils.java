package com.anyfork.utils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/18 18:15
 * @Version 1.0
 */
public class RsaUtils {

    public static Map<String, Key> genKeyPair() throws NoSuchAlgorithmException {
        Map<String, Key> map=new HashMap<>(2);
        KeyPair var0 = getKeyPair(1024);
        map.put("RSAPublicKey", var0.getPublic());
        map.put("RSAPrivateKey", var0.getPrivate());
        return map;
    }

    public static KeyPair getKeyPair(int var0) throws NoSuchAlgorithmException {
        KeyPairGenerator var1 = KeyPairGenerator.getInstance("RSA");
        var1.initialize(var0, new SecureRandom());
        return var1.generateKeyPair();
    }

    private static byte[] cipherDecryptData(byte[] var0, Key var1) throws Exception {
        return cipherData(var0, var1, 2, 128);
    }

    private static byte[] cipherEncryptData(byte[] var0, Key var1) throws Exception {
        return cipherData(var0, var1, 1, 117);
    }

    private static byte[] cipherData(byte[] var0, Key var1, int var2, int var3) throws Exception {
        Cipher var4 = Cipher.getInstance("RSA");
        var4.init(var2, var1);
        int var5 = var0.length;
        ByteArrayOutputStream var6 = new ByteArrayOutputStream();
        int var7 = 0;
        for(int var9 = 0; var5 - var7 > 0; var7 = var9 * var3) {
            byte[] var8;
            if (var5 - var7 > var3) {
                var8 = var4.doFinal(var0, var7, var3);
            } else {
                var8 = var4.doFinal(var0, var7, var5 - var7);
            }
            var6.write(var8, 0, var8.length);
            ++var9;
        }
        byte[] var10 = var6.toByteArray();
        var6.close();
        return var10;
    }

    public static String encryptByPublicKey(String var0, String var1) throws Exception {
        return encryptByKey(var0, publicKey(var1));
    }

    public static String encryptByPrivateKey(String var0, String var1) throws Exception {
        return encryptByKey(var0, privateKey(var1));
    }

    private static String encryptByKey(String var0, Key var1) throws Exception {
        return Base64Utils.encodeBase64(cipherEncryptData(var0.getBytes(StandardCharsets.UTF_8), var1));
    }

    public static String decryptByPrivateKey(String var0, String var1) throws Exception {
        return decryptByKey(var0, privateKey(var1));
    }

    public static String decryptByPublicKey(String var0, String var1) throws Exception {
        return decryptByKey(var0, publicKey(var1));
    }

    private static String decryptByKey(String var0, Key var1) throws Exception {
        return new String(cipherDecryptData(Base64Utils.bytesDecodeBase64(var0), var1));
    }

    public static String getPrivateKey(Map<String, Key> var0) {
        return Base64Utils.encodeBase64(var0.get("RSAPrivateKey").getEncoded());
    }

    public static String getBase64PrivateKey(Map<String, Key> var0) {
        return Base64.getMimeEncoder().encodeToString(var0.get("RSAPrivateKey").getEncoded());
    }

    public static String getPublicKey(Map<String, Key> var0) {
        return Base64Utils.encodeBase64(var0.get("RSAPublicKey").getEncoded());
    }

    public static String getBase64PublicKey(Map<String, Key> var0) {
        return Base64.getMimeEncoder().encodeToString(var0.get("RSAPublicKey").getEncoded());
    }

    public static PrivateKey privateKey(String var0) throws InvalidKeySpecException {
        return privateKeyFromPKCS8(Base64Utils.bytesDecodeBase64(var0));
    }

    public static PublicKey publicKey(String var0) throws InvalidKeySpecException {
        return publicKeyFrom(Base64Utils.bytesDecodeBase64(var0));
    }

    public static PrivateKey privateKeyFromPKCS8(byte[] var0) throws InvalidKeySpecException {
        try {
            PKCS8EncodedKeySpec var1 = new PKCS8EncodedKeySpec(var0);
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePrivate(var1);
        } catch (NoSuchAlgorithmException var3) {
            throw new IllegalStateException(var3);
        }
    }

    public static PublicKey publicKeyFrom(byte[] var0) throws InvalidKeySpecException {
        try {
            KeyFactory var1 = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec var2 = new X509EncodedKeySpec(var0);
            return var1.generatePublic(var2);
        } catch (NoSuchAlgorithmException var3) {
            throw new IllegalStateException(var3);
        }
    }
}
