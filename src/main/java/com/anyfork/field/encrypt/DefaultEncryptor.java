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
    public String encrypt(Algorithm var1, String var2, String var3, String var4, Object var5) throws Exception {
        return FieldEncryptUtils.encryptOrDecryptHandler(var1, var2, true, var3, var4);
    }

    @Override
    public String decrypt(Algorithm var1, String var2, String var3, String var4, Object var5) throws Exception {
        return FieldEncryptUtils.encryptOrDecryptHandler(var1, var2, false, var3, var4);
    }
}
