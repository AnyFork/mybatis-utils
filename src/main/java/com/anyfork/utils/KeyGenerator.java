package com.anyfork.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: KeyGenerator
 * @Description: 国密SM2 私钥和公钥，SM4 私钥生成器
 * @Author: 小紫念沁
 * @Date: 2021/11/17 16:20
 * @Version 1.0
 */
public class KeyGenerator {

    public static String generateSM4Key() throws Exception {
        return Sm4Utils.generateSm4Keys();
    }

    public static String[] generateSm2Keys() throws Exception {
        ECGenParameterSpec var0 = new ECGenParameterSpec("sm2p256v1");
        KeyPairGenerator var1 = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
        var1.initialize(var0);
        var1.initialize(var0, new SecureRandom());
        KeyPair var2 = var1.generateKeyPair();
        return new String[]{new String(Base64.getEncoder().encode(var2.getPublic().getEncoded())), new String(Base64.getEncoder().encode(var2.getPrivate().getEncoded()))};
    }

    public static PublicKey createPublicKey(String var0) throws NoSuchAlgorithmException, InvalidKeySpecException {
        X509EncodedKeySpec var1 = new X509EncodedKeySpec(Base64.getDecoder().decode(var0));
        return getKeyFactory("EC").generatePublic(var1);
    }

    public static PrivateKey createPrivateKey(String var0) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec var1 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(var0));
        return getKeyFactory("EC").generatePrivate(var1);
    }

    public static KeyFactory getKeyFactory(String var0) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(var0, new BouncyCastleProvider());
    }
}
