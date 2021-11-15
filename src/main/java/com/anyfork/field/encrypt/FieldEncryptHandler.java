package com.anyfork.field.encrypt;

import com.anyfork.annotation.FieldEncrypt;
import com.anyfork.field.FieldSetProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/15 12:04
 * @Version 1.0
 */
@Slf4j
public class FieldEncryptHandler {

    private static Map<Class<? extends IEncryptor>, IEncryptor> mapClass;
    
    /**
     * 字段加密处理
     * @param var0  Invocation
     * @param var1  加解密配置
     * @param var2  加解密处理执行器
     * @return null
     **/
    public static Object handlerResult(Invocation var0, EncryptorProperties var1, IEncryptor var2) throws Throwable {
        Object[] var3 = var0.getArgs();
        MappedStatement mappedStatement = (MappedStatement)var3[0];
        if (getSqlCommandType(mappedStatement.getSqlCommandType())) {
            Object var5 = var3[1];
            Configuration var6 = mappedStatement.getConfiguration();
            if (var5 instanceof Map) {
                return var0.proceed();
            } else {
                isEncrypt(var6, var2, var1, var5);
            }
        }
        return var0.proceed();
    }

    /**
     * 字段加密
     **/
    public static boolean isEncrypt(Configuration var0, IEncryptor var1, EncryptorProperties var2, Object var3) {
        return isExistEncryptField(var0, var3, (var2x, var3x) -> {
            FieldEncrypt var4 = var3x.getFieldEncrypt();
            if (null != var4) {
                Object var5 = var2x.getValue(var3x.getFieldName());
                if (null != var5) {
                    try {
                        //获取加密执行类，判断是默认加密类，还是用户自定义加密类
                        IEncryptor encryptor = checkEncrypt(var1, var4.encryptor());
                        //获取加密后的结果
                        String var6 =  encryptor.encrypt(var2.algorithm(var4), var2.password(var4), var2.getPublicKey(), (String)var5, var2x);
                        var2x.setValue(var3x.getFieldName(), var6);
                    } catch (Exception var7) {
                        log.error("field encrypt exception: {}", var7.getMessage());
                    }
                }
            }
        });
    }
    
    /**
     * 获取加密执行类，判断是默认加密类，还是用户自定义加密类
     **/
    protected static IEncryptor checkEncrypt(IEncryptor var0, Class<? extends IEncryptor> var1) {
        IEncryptor var2 = var0;
        //判断是默认内置的加密类，还是用户自定义加密类
        if (IEncryptor.class != var1) {
            if (null == mapClass) {
                mapClass = new HashMap<>();
            }
            var2 = mapClass.get(var1);
            if (null == var2) {
                try {
                    var2 =var1.newInstance();
                    mapClass.put(var1, var2);
                } catch (Exception var4) {
                    log.error("fieldEncrypt encryptor newInstance error", var4);
                }
            }
        }
        return var2;
    }

    /**
     * 判断Sql类型，是否是update 或者 insert
     * @param var0 SqlCommandType
     * @return boolean
     **/
    public static boolean getSqlCommandType(SqlCommandType var0) {
        return SqlCommandType.UPDATE == var0 || SqlCommandType.INSERT == var0;
    }

    /**
     * 判断是否存在需要加密的字段，存在则加密，返回true,否则返回false;
     **/
    public static boolean isExistEncryptField(Configuration var0, Object var1, BiConsumer<MetaObject, FieldSetProperty> var2) {
        //获取需要加解密的类及其字段
        List<FieldSetProperty> var3 = FieldDecryptHandler.getFieldProperty(var1.getClass());
        if (null!=var3&& var3.stream().anyMatch((var0x) -> null != var0x.getFieldEncrypt())) {
            MetaObject var4 = var0.newMetaObject(var1);
            var3.stream().filter((var0x) -> null != var0x.getFieldEncrypt()).forEach((var2x) -> {
                var2.accept(var4, var2x);
            });
            return true;
        } else {
            return false;
        }
    }
}
