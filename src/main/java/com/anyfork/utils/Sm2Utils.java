package com.anyfork.utils;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Objects;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/17 16:12
 * @Version 1.0
 */
public class Sm2Utils {

    public static byte[] encryptPublicKey(byte[] var0, PublicKey var1) {
        ECPublicKeyParameters var2 = null;
        if (var1 instanceof BCECPublicKey) {
            BCECPublicKey var3 = (BCECPublicKey)var1;
            ECParameterSpec var4 = var3.getParameters();
            ECDomainParameters var5 = new ECDomainParameters(var4.getCurve(), var4.getG(), var4.getN());
            var2 = new ECPublicKeyParameters(var3.getQ(), var5);
        }
        try {
            SM2Engine var7 = new SM2Engine();
            var7.init(true, new ParametersWithRandom(var2, new SecureRandom()));
            return var7.processBlock(var0, 0, var0.length);
        } catch (InvalidCipherTextException var6) {
           var6.printStackTrace();
           return null;
        }
    }

    public static String encryptToBase64String(String var0, String var1) throws Exception {
        return Base64Utils.encodeBase64(encryptPublicKey(var0.getBytes(StandardCharsets.UTF_8), KeyGenerator.createPublicKey(var1)));
    }

    public static byte[] decodePrivateKey(byte[] var0, PrivateKey var1) {
        SM2Engine var2 = new SM2Engine();
        BCECPrivateKey var3 = (BCECPrivateKey)var1;
        ECParameterSpec var4 = var3.getParameters();
        ECDomainParameters var5 = new ECDomainParameters(var4.getCurve(), var4.getG(), var4.getN());
        ECPrivateKeyParameters var6 = new ECPrivateKeyParameters(var3.getD(), var5);
        var2.init(false, var6);
        try {
            return var2.processBlock(var0, 0, var0.length);
        } catch (InvalidCipherTextException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static String decryptFromBase64String(String var0, String var1) throws Exception {
        return new String(Objects.requireNonNull(decodePrivateKey(Base64Utils.bytesDecodeBase64(var0), KeyGenerator.createPrivateKey(var1))));
    }

    public static void main(String[] args) throws Exception {
        String[] strings = KeyGenerator.generateSm2Keys();
        String publicKey =strings[0];
        String sm2PrivateKey = strings[1];
        String encrypt = Sm2Utils.encryptToBase64String("123456,###@@@哈哈哈哈哈你好么这是还念念你阿史那隼hi安东尼奥的呢尿素袋那你说的那是的呢那是的那是的呢那是当年的那首你师弟那是当年的那首",publicKey);
        System.out.println(encrypt);
        String decrypt = Sm2Utils.decryptFromBase64String(encrypt, sm2PrivateKey);
        System.out.println(decrypt);
    }
}
