package com.anyfork.enums;

/**
 * @PackageName: com.anyfork.enums
 * @ClassName: Algorithm
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/07 16:19
 * @Version 1.0
 */
public enum Algorithm {
    MD5_32,
    MD5_16,
    AES,
    SM2,
    SM3,
    SM4,
    RSA,
    BASE64,
    PBEWithMD5AndDES,
    PBEWithMD5AndTripleDES,
    PBEWithHMACSHA512AndAES_256,
    PBEWithSHA1AndDESede,
    PBEWithSHA1AndRC2_40;
    private Algorithm() {
    }
}
