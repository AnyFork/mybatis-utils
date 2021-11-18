package com.anyfork.utils;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.AES;
import lombok.extern.slf4j.Slf4j;

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
}
