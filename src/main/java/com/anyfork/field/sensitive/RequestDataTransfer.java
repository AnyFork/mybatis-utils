package com.anyfork.field.sensitive;

import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: com.anyfork.databind
 * @ClassName: RequestDataTransfer
 * @Description: 线程共享变量
 * @Author: 小紫念沁
 * @Date: 2021/10/28 17:32
 * @Version 1.0
 */
public class RequestDataTransfer {
    /**
     * 本地线程共享变量
     **/
    private static final ThreadLocal<Map<String, Object>> REQUEST_DATA = new ThreadLocal<>();
    
    /**
     * 将数据加入到 REQUEST_DATA
     * @param  requestData 数据集
     **/
    public static void put(Map<String, Object> requestData) {
        REQUEST_DATA.set(requestData);
    }
    
    /**
     * 设置值
     * @param key key
     * @param value 值
     **/
    public static void put(final String key, final Object value) {
        Map<String, Object> dataMap = getAll();
        if (null != dataMap && !dataMap.isEmpty()) {
            dataMap.put(key, value);
        } else {
            HashMap<String, Object> hashMap=new HashMap<>();
            hashMap.put(key,value);
            put(hashMap);
        }
    }
   
    /**
     * 通过key值获取数据
     * @param key key值
     * @return Object
     **/
    public static Object get(String key) {
        Map<String, Object> dataMap = getAll();
        return null != dataMap && !dataMap.isEmpty() ?  dataMap.get(key) : null;
    }
    
    /**
     * 返回所有数据
     * @return Map<String, Object>
     **/
    public static Map<String, Object> getAll() {
        return REQUEST_DATA.get();
    }

    /**
     * 清除数据
     **/
    public static void remove() {
        REQUEST_DATA.remove();
    }

    /**
     *跳过脱敏设置
     **/
    public static void skipSensitive() {
        put("skip_sensitive", "1");
    }
}
