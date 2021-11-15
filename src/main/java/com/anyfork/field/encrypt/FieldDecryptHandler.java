package com.anyfork.field.encrypt;

import com.anyfork.annotation.FieldEncrypt;
import com.anyfork.field.FieldSetProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.DefaultResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @PackageName: com.anyfork.field.encrypt
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/15 18:55
 * @Version 1.0
 */
@Slf4j
public class FieldDecryptHandler {
   
    /**
     * 是否需要加密
     **/
    private static boolean isEncrypt = false;

    /**
     * 需加解密的class及其字段集合
     **/
    private static Map<Class<?>, List<FieldSetProperty>> cacheMap;

    /**
     * 无需加解密的class
     **/
    private static Set<Class<?>> setClass;
   
    /**
     * 自定义加解密类缓存
     **/
    private static Map<Class<? extends IEncryptor>, IEncryptor> mapClass;
    

    public static void configure(boolean var0) {
        isEncrypt = var0;
        cacheMap = new ConcurrentHashMap<>();
        setClass = new CopyOnWriteArraySet<>();
    }
    /**
     * 字段解密处理
     * @param var0  Invocation
     * @param var1  解密配置
     * @return Object
     **/
    public static Object handlerResult(Invocation var0, BiConsumer<MetaObject, FieldSetProperty> var1) throws Throwable {
        List<Object> var2 = (List<Object>) var0.proceed();
        if (var2.isEmpty()) {
            return var2;
        } else {
            DefaultResultSetHandler var3 = (DefaultResultSetHandler)var0.getTarget();
            Field var4 = var3.getClass().getDeclaredField("mappedStatement");
            var4.setAccessible(true);
            MappedStatement var5 = (MappedStatement)var4.get(var3);
            Configuration var6 = var5.getConfiguration();
            var2.forEach(item->{
                FieldDecryptHandler.isEncrypted(var6, item, var1);
            });
            return var2;
        }
    }

    /**
     * 字段加解密
     **/
    public static boolean isEncrypted(Configuration var0, Object var1, BiConsumer<MetaObject, FieldSetProperty> var2) {
        List<FieldSetProperty> var3 = getFieldProperty(var1.getClass());
        if (null!=var3&&var3.size()>0) {
            MetaObject var4 = var0.newMetaObject(var1);
            var3.parallelStream().forEach(var2x-> {
                var2.accept(var4, var2x);
            });
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取需要加解密的类及其字段
     **/
    public static List<FieldSetProperty> getFieldProperty(Class<?> var0) {
        //判断class是否需要加解密
        if (setClass.contains(var0)) {
            return null;
        } else {
            //判断cacheMap缓存中是否有class
            List<FieldSetProperty> var1 = cacheMap.get(var0);
            if (null == var1) {
                //判断是否是hashMap的父类
                if (var0.isAssignableFrom(HashMap.class)) {
                    setClass.add(var0);
                } else {
                    var1 = new ArrayList<>();
                    //获取class类及其父类所有声明的非static和transient字段
                    List<Field> var2 = getClassAndSuperClassAllFields(var0);
                    List<FieldSetProperty> finalVar = var1;
                    var2.forEach(item -> {
                        if (isEncrypt) {
                            FieldEncrypt var5 = item.getAnnotation(FieldEncrypt.class);
                            if(null!=var5){
                                if(!item.getType().isAssignableFrom(String.class)){
                                    throw new RuntimeException("annotation `@FieldEncrypt` only string types are supported.");
                                }else{
                                    finalVar.add(new FieldSetProperty(item.getName(), var5));
                                }
                            }
                        }
                    });
                    if (finalVar.isEmpty()) {
                        setClass.add(var0);
                    } else {
                        cacheMap.put(var0, var1);
                    }
                }
            }
            return var1;
        }
    }
    /**
     * 获取class类及其父类所有声明的非static和transient字段
     * @param  clazz 基类
     * @return List<Field> 所有声明的非static和transient字段
     **/
    public static List<Field> getClassAndSuperClassAllFields(Class<?> clazz) {
        if (null == clazz) {
            return null;
        } else {
            //获取clazz类所有声明的字段，不包含父类字段;
            Field[] allFields = clazz.getDeclaredFields();
            //过滤掉static和transient修饰符修饰的字段
            List<Field> filterFields = Arrays.stream(allFields).filter(item -> !Modifier.isStatic(item.getModifiers()) && !Modifier.isTransient(item.getModifiers())).collect(Collectors.toList());
            //获取clazz父类
            Class<?> superclass = clazz.getSuperclass();
            if (superclass.equals(Object.class)) {
                return filterFields;
            } else {
                //递归获取父类声明的字段
                List<Field> superFields = getClassAndSuperClassAllFields(superclass);
                //将父类声明的字段合并到子类字段中
                superFields.forEach(item->{
                    if(filterFields.stream().noneMatch(field -> field.getName().equals(item.getName()))){
                        filterFields.add(item);
                    }
                });
                return filterFields;
            }
        }
    }

   /**
    * 字段解密
    **/
    public static void isEncrypt(IEncryptor var0, EncryptorProperties var2, MetaObject var3, FieldSetProperty var4) {
        String var5 = var4.getFieldName();
        Object var6 = var3.getValue(var5);
        if (null != var6) {
            if (null != var0 && var6 instanceof String) {
                try {
                    FieldEncrypt var7 = var4.getFieldEncrypt();
                    if (null != var7) {
                        //获取解密执行类，判断是默认解密类，还是用户自定义解密类
                        IEncryptor encryptor = checkEncrypt(var0, var7.encryptor());
                        //解密
                        var6 = encryptor.decrypt(var2.algorithm(var7), var2.password(var7), var2.getPrivateKey(), (String)var6, var3);
                    }
                } catch (Exception var9) {
                    log.error("field decrypt exception: {}", var9.getMessage());
                }
            }
            var3.setValue(var5, var6);
        }
    }

    /**
     * 获取解密执行类，判断是默认解密类，还是用户自定义解密类
     **/
    protected static IEncryptor checkEncrypt(IEncryptor var0, Class<? extends IEncryptor> var1) {
        IEncryptor var2 = var0;
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
}
