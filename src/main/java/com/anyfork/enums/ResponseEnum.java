package com.anyfork.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 小紫念沁
 * @description: Api响应结果状态码
 * @date 2021/10/24 17:05
 */
@AllArgsConstructor
@Getter
public enum ResponseEnum {
    /**
     * 枚举类型
     */
    OK(200, "操作成功!"),
    ERROR(500, "操作失败!"),
    VALIDATED_ERROR(301, "参数校验异常"),
    BUSINESS_EXCEPTION(302, "自定义业务异常"),
    PARAM_NOT_MATCH(303,"参数类型转换错误!"),
    BODY_NOT_MATCH(304,"请求的数据格式不符!"),
    JWT_NOT_MATCH(305,"JWT无效,请重新登陆!"),
    JSON_NOT_MATCH(305,"JSON格式数据中存在字段类型格式转换错误!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500,"服务器异常，请稍后再试!"),
    TIME_OUT(502,"网络超时");

    public int code;
    public String msg;

    public static ResponseEnum getEnumByCode(Integer resultCode){
        ResponseEnum[] commonEnums= ResponseEnum.values();
        for (ResponseEnum commonEnum:commonEnums) {
            if(commonEnum.code==resultCode){
                return  commonEnum;
            }
        }
        return null;
    }

    public static ResponseEnum getEnumByMsg(String resultMsg){
        ResponseEnum[] commonEnums= ResponseEnum.values();
        for (ResponseEnum commonEnum:commonEnums) {
            if(commonEnum.msg.equals(resultMsg)){
                return  commonEnum;
            }
        }
        return null;
    }
}
