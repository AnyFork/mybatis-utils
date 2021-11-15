package com.anyfork.field.sensitive;

import com.anyfork.annotation.FieldSensitive;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * @PackageName: com.anyfork.strategy
 * @ClassName: SensitiveInfoSerialize
 * @Description: 脱敏字段自定义脱敏策略
 * @Author: 小紫念沁
 * @Date: 2021/10/28 17:07
 * @Version 1.0
 */
@Slf4j
public class SensitiveInfoSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏策略
     **/
    private static ISensitiveStrategy SENSITIVE_STRATEGY;
    
    /**
     * 脱敏类型
     **/
    private  String type;

    public SensitiveInfoSerializer(){};

    public SensitiveInfoSerializer(String type) {
        this.type = type;
    }
    
    /**
     * 设置内置或自定义脱敏策略策略
     **/
    public static void setSensitiveStrategy(ISensitiveStrategy sensitiveStrategy) {
        SENSITIVE_STRATEGY = sensitiveStrategy;
    }

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        if (null == SENSITIVE_STRATEGY) {
            throw new RuntimeException("You used the annotation `@FieldSensitive` but did not inject `SensitiveStrategy`");
        } else {
            jsonGenerator.writeObject("1".equals(RequestDataTransfer.get("skip_sensitive")) ? value : SENSITIVE_STRATEGY.handle(this.type, value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (null != property) {
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                FieldSensitive sensitiveInfo = property.getAnnotation(FieldSensitive.class);
                if (null == sensitiveInfo) {
                    sensitiveInfo = property.getContextAnnotation(FieldSensitive.class);
                }
                if (null != sensitiveInfo) {
                    return new SensitiveInfoSerializer(sensitiveInfo.type());
                }
            }else{
                log.error("FieldSensitive annotation can only mark on the String Class ");
            }
            return prov.findValueSerializer(property.getType(), property);
        } else {
            return prov.findNullValueSerializer(null);
        }
    }
}
