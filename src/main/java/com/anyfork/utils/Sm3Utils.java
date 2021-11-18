package com.anyfork.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.util.encoders.Hex;

import java.nio.charset.StandardCharsets;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: Sm3Utils
 * @Description: sm3工具类
 * @Author: 小紫念沁
 * @Date: 2021/11/18 17:53
 * @Version 1.0
 */
public class Sm3Utils {

    public static String encode(String var0) {
        return Hex.toHexString(encodeToBytes(var0.getBytes(StandardCharsets.UTF_8)));
    }

    public static String encode(byte[] var0) {
        return Hex.toHexString(encodeToBytes(var0));
    }

    private static byte[] encodeToBytes(byte[] var0) {
        SM3Digest var1 = new SM3Digest();
        var1.update(var0, 0, var0.length);
        byte[] var2 = new byte[var1.getDigestSize()];
        var1.doFinal(var2, 0);
        return var2;
    }
}
