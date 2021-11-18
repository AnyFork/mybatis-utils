package com.anyfork.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: Sm4Utils
 * @Description: sm4加解密工具
 * @Author: 小紫念沁
 * @Date: 2021/11/18 17:58
 * @Version 1.0
 */
public class Sm4Utils {

    static {
        if (null == Security.getProvider("BC")) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    public static String generateSm4Keys() throws Exception {
        KeyGenerator var0 = KeyGenerator.getInstance("SM4", "BC");
        var0.init(128, new SecureRandom());
        return Hex.toHexString(var0.generateKey().getEncoded());
    }
    /**
     * 加密
     * @param keyPassword 秘钥
     * @param plaintext 明文
     * @return String 密文
     **/
    public static String encrypt(String keyPassword, String plaintext) throws Exception {
        byte[] var2 = toBytes(Strings.toUTF8ByteArray(plaintext), Hex.decode(keyPassword), Cipher.ENCRYPT_MODE);
        return null == var2 ? null : Hex.toHexString(var2);
    }

    /**
     * 加密
     * @param keyPassword 秘钥
     * @param ciphertext 密文
     * @return String 明文
     **/
    public static String decrypt(String keyPassword, String ciphertext) throws Exception {
        byte[] var2 = toBytes(Hex.decode(ciphertext), Hex.decode(keyPassword), Cipher.DECRYPT_MODE);
        return null == var2 ? null : new String(var2, StandardCharsets.UTF_8);
    }

    private static byte[] toBytes(byte[] var0, byte[] var1, int var2) throws Exception {
        SecretKeySpec var3 = new SecretKeySpec(var1, "SM4");
        Cipher var4 = Cipher.getInstance("SM4/ECB/PKCS5Padding", "BC");
        var4.init(var2, var3);
        return var4.doFinal(var0);
    }

    public static void main(String[] args) throws Exception {
        String s = generateSm4Keys();
        String encrypt = encrypt(s, "11111111");
        System.out.println(encrypt);
        String decrypt = decrypt(s, encrypt);
        System.out.println(decrypt);

    }
}
