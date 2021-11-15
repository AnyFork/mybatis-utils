package com.anyfork.field.encrypt;


import com.anyfork.enums.Algorithm;

public interface IEncryptor {
    String encrypt(Algorithm var1, String var2, String var3, String var4, Object var5) throws Exception;

    String decrypt(Algorithm var1, String var2, String var3, String var4, Object var5) throws Exception;
}
