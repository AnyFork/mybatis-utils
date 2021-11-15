package com.anyfork.field.sensitive;

import java.util.Map;
import java.util.function.Function;

/**
 * @PackageName: com.anyfork.strategy
 * @ClassName: ISensitiveStrategy
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/10/28 17:09
 * @Version 1.0
 */
public interface ISensitiveStrategy {

    /**
     * 脱敏策略执行方法
     * @param  type 脱敏类型值
     * @param  value 需要脱敏数据
     * @return  脱敏后结果
     **/
    default String handle(String type, String value) {
        Function<String, String> stringStringFunction = this.getStrategyFunctionMap().get(type);
        return null==stringStringFunction?value:stringStringFunction.apply(value);
    }

    /**
     * 脱敏策略
     * @return  所有脱敏策略(内置+自定义)
     **/
    Map<String, Function<String, String>> getStrategyFunctionMap();
}
