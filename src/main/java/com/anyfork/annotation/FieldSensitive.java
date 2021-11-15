package com.anyfork.annotation;

import com.anyfork.field.sensitive.SensitiveInfoSerializer;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.lang.annotation.*;

/**
 * @PackageName: com.anyfork.annotation
 * @ClassName: FieldSensitive
 * @Description: 字段脱敏注解
 * @Author: 小紫念沁
 * @Date: 2021/10/28 16:29
 * @Version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerializer.class)
public @interface FieldSensitive {

    /**
     * 字段脱敏策略类型，可以用内置类型(SensitiveType),亦可以自定义脱敏类型需要自定义脱敏策略，可参考DefaultSensitiveStrategy
     */
    String type();
}
