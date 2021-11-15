package com.anyfork.utils;

import com.anyfork.enums.Algorithm;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName: FieldEncryptUtils
 * @Description: 加解密统一处理工具
 * @Author: 小紫念沁
 * @Date: 2021/11/11 17:28
 * @Version 1.0
 */
public class FieldEncryptUtils {

    public static String encryptOrDecryptHandler(Algorithm var0, String var1, boolean var2, String var3, String var4) throws Exception {
        return JasyptUtils.jasypt(var0, var1, var2, var4);
    }
}
