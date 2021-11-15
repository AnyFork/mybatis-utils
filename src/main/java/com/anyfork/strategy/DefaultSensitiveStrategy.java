package com.anyfork.strategy;

import cn.hutool.core.util.StrUtil;
import com.anyfork.constant.SensitiveType;
import com.anyfork.field.sensitive.ISensitiveStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * @PackageName: com.anyfork.databind
 * @ClassName: DefaultSensitiveStrategy
 * @Description: 默认字段脱敏策略
 * @Author: 小紫念沁
 * @Date: 2021/10/28 18:06
 * @Version 1.0
 */
@Slf4j
public class DefaultSensitiveStrategy implements ISensitiveStrategy {

    private static Map<String, Function<String, String>> STRATEGY_FUNCTION_MAP;

    public DefaultSensitiveStrategy() {
        STRATEGY_FUNCTION_MAP = initStrategyFunctionMap();
    }

    /**
     * 初始化默认脱敏规则
     */
    private Map<String, Function<String, String>> initStrategyFunctionMap(){
        Map<String, Function<String, String>> stringFunctionHashMap = new HashMap<>(9);
        //中文名称
        stringFunctionHashMap.put(SensitiveType.CHINESE_NAME, s->s.replaceAll("(\\S)\\S(\\S*)", "$1*$2"));
        //身份证
        stringFunctionHashMap.put(SensitiveType.ID_CARD, s -> s.replaceAll("(\\d{4})\\d{10}(\\w{4})", "$1****$2"));
        //座机
        stringFunctionHashMap.put(SensitiveType.PHONE, s -> StrUtil.isEmpty(s)?null:s.substring(0,6)+"****"+s.substring(s.length()-4));
        //手机号码
        stringFunctionHashMap.put(SensitiveType.MOBILE, s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
        //地址
        stringFunctionHashMap.put(SensitiveType.ADDRESS, s -> s.replaceAll("(\\S{3})\\S{2}(\\S*)\\S{2}", "$1****$2****"));
        //银行卡
        stringFunctionHashMap.put(SensitiveType.BANK_CARD, s-> StrUtil.isEmpty(s)?null:s.substring(0,6)+"*****"+s.substring(s.length()-4));
        //密码脱敏
        stringFunctionHashMap.put(SensitiveType.PASSWORD, s-> StrUtil.isEmpty(s)?null:"******");
        //车牌号
        stringFunctionHashMap.put(SensitiveType.CAR_NUMBER, s-> StrUtil.isEmpty(s)?null:s.substring(0,5)+"**"+s.substring(s.length()-2));
        return stringFunctionHashMap;
    }
   
    /**
     * 自定义脱敏策略
     */
    public DefaultSensitiveStrategy addStrategy(String var1, Function<String, String> var2) {
        STRATEGY_FUNCTION_MAP.put(var1, var2);
        return this;
    }

    @Override
    public Map<String, Function<String, String>> getStrategyFunctionMap() {
        return STRATEGY_FUNCTION_MAP;
    }

    @Override
    public String handle(String type, String value) {
        Function<String, String> stringStringFunction = this.getStrategyFunctionMap().get(type);
        if(null==stringStringFunction){
            log.error("Mismatched desensitization type or desensitization type is not empty");
            return value;
        }
        return stringStringFunction.apply(value);
    }
}
