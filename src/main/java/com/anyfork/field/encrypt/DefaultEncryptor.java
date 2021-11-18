package com.anyfork.field.encrypt;

import com.anyfork.enums.Algorithm;
import com.anyfork.utils.FieldEncryptUtils;

/**
 * @PackageName: com.anyfork.field.encrypt
 * @ClassName: DefaultEncryptor
 * @Description: 默认加解密处理类
 * @Author: 小紫念沁
 * @Date: 2021/11/07 16:24
 * @Version 1.0
 */
public class DefaultEncryptor implements IEncryptor{

    @Override
    public String encrypt(Algorithm algorithm, String keyPassword, String publicKey, String plainTextOrCipherText, Object metaObject) throws Exception {
        return FieldEncryptUtils.encryptOrDecryptHandler(algorithm, keyPassword, true, publicKey, plainTextOrCipherText);
    }

    @Override
    public String decrypt(Algorithm algorithm, String keyPassword, String publicKey, String plainTextOrCipherText, Object metaObject) throws Exception {
        return FieldEncryptUtils.encryptOrDecryptHandler(algorithm, keyPassword, false, publicKey, plainTextOrCipherText);
    }
}
