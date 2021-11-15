package com.anyfork.annotation;

import com.anyfork.field.encrypt.IEncryptor;
import com.anyfork.enums.Algorithm;

import java.lang.annotation.*;

/**
 * @PackageName: com.anyfork.annotation
 * @ClassName: FieldEncrypt
 * @Description: 字段加解密注解
 * @Author: 小紫念沁
 * @Date: 2021/11/07 16:17
 * @Version 1.0
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface FieldEncrypt {

    String password() default "";

    Algorithm algorithm() default Algorithm.PBEWithMD5AndDES;

    Class<? extends IEncryptor> encryptor() default IEncryptor.class;

}
