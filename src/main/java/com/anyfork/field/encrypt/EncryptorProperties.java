package com.anyfork.field.encrypt;


import com.anyfork.annotation.FieldEncrypt;
import com.anyfork.enums.Algorithm;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @PackageName: com.anyfork.field.encrypt
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/07 16:28
 * @Version 1.0
 */
@ConfigurationProperties(prefix = "anyfork.encryptor")
public class EncryptorProperties implements Serializable {

    private String password;
    private String publicKey;
    private String privateKey;
    private Algorithm algorithm;

    public String password(FieldEncrypt var1) {
        String var2 = var1.password();
        return null != this.password && "".equals(var2) ? this.password : var2;
    }

    public Algorithm algorithm(FieldEncrypt var1) {
        Algorithm var2 = var1.algorithm();
        return null != this.algorithm && var2 == Algorithm.PBEWithMD5AndDES ? this.algorithm : var2;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public Algorithm getAlgorithm() {
        return this.algorithm;
    }

    public void setPassword(String var1) {
        this.password = var1;
    }

    public void setPublicKey(String var1) {
        this.publicKey = var1;
    }

    public void setPrivateKey(String var1) {
        this.privateKey = var1;
    }

    public void setAlgorithm(Algorithm var1) {
        this.algorithm = var1;
    }
}
