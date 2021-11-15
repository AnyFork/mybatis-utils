package com.anyfork.utils;
import cn.hutool.core.util.StrUtil;
import com.anyfork.enums.Algorithm;
import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;

/**
 * @Package: com.dx.mini.utils
 * @ClassName: jasyptUtils
 * @Description: 加密解密工具
 * @author: 小紫念沁
 * @date: 2020/5/3 22:23
 */
@Slf4j
public class JasyptUtils {

    /**
     * 获取随机加密的key，生成 16 位随机 AES 密钥
     */
    public static String getKey(){
        return AES.generateRandomKey();
    }

    /**
     * 数据加密
     * @param data 原始数据
     * @param key 加密的key
     * @return 加密后的值
     */
    public static String encrypt(String data,String key){
        if(StrUtil.isNotBlank(data)&&StrUtil.isNotBlank(key)){
            return AES.encrypt(data,key);
        }
        return null;
    }

    /**
     * 数据解密
     * @param data 加密后的数据
     * @param key 随机秘钥
     * @return  原始数据
     */
    public static String decrypt(String data,String key){
        if(StrUtil.isNotBlank(data)&& StrUtil.isNotBlank(key)){
            return AES.decrypt(data,key);
        }
        return null;
    }

    public static String jasypt(Algorithm var0, String var1, boolean var2, String var3) {
        if (var0 == Algorithm.PBEWithHMACSHA512AndAES_256) {
            AES256TextEncryptor var5 = new AES256TextEncryptor();
            var5.setPassword(var1);
            return var2 ? var5.encrypt(var3) : var5.decrypt(var3);
        } else {
            StandardPBEStringEncryptor var4 = new StandardPBEStringEncryptor();
            var4.setPassword(var1);
            var4.setAlgorithm(var0.name());
            return var2 ? var4.encrypt(var3) : var4.decrypt(var3);
        }
    }


    /**
     * 1目前文本加密三种：BasicTextEncryptor(加密方式:PBEWithMD5AndDES),StrongTextEncryptor(加密方式:PBEWithMD5AndTripleDES),AES256TextEncryptor(加密方式：PBEWithHMACSHA512AndAES_256)
     * 2前2中方式为3.x以下版本，默认对应的iv-generator-classname：org.jasypt.iv.NoIvGenerator，第三种：默认对应的iv-generator-classname：org.jasypt.iv.RandomIvGenerator
     */
    public static void main(String[] args) {
        String url="jdbc:p6spy:mysql://192.168.0.104:3306/simple_admin?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true";
        String key = "4b0722efc5392f97";
        String encrypt = JasyptUtils.encrypt(url, key);
        String decrypt = JasyptUtils.decrypt(encrypt, key);
        log.info("随机key：{}",key);
        log.info("加密后密码：{}",encrypt);
        log.info("解密后的密码：{}",decrypt);
    }
}
