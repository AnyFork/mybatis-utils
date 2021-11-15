package com.anyfork.utils;

import com.anyfork.enums.ResponseEnum;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author 小紫念沁
 * @description: TODO
 * @date 2021/10/24 002417:01
 */
public class ResponseBody implements Serializable {

    public  static ResultEntity<?> success(){
        return success(Collections.EMPTY_MAP);
    }

    public  static <T> ResultEntity<T> success(@NonNull T data){
        ResultEntity<T> result=new ResultEntity<>();
        result.setSuccess(true);
        result.setCode(ResponseEnum.OK.code);
        result.setMsg(ResponseEnum.OK.msg);
        result.setData(data);
        result.setTimeStamp(System.currentTimeMillis()/1000);
        return result;
    }
    public  static <T> ResultEntity<T> success(@NonNull ResponseEnum responseEnum, @NonNull T data){
        ResultEntity<T> result=new ResultEntity<>();
        result.setSuccess(true);
        result.setCode(responseEnum.code);
        result.setMsg(responseEnum.msg);
        result.setData(data);
        result.setTimeStamp(System.currentTimeMillis()/1000);
        return result;
    }

    public  static ResultEntity<?> error(){
        return error(Collections.EMPTY_MAP);
    }

    public  static <T> ResultEntity<T> error(@NonNull T data){
        ResultEntity<T> result=new ResultEntity<>();
        result.setSuccess(false);
        result.setCode(ResponseEnum.ERROR.code);
        result.setMsg(ResponseEnum.ERROR.msg);
        result.setData(data);
        result.setTimeStamp(System.currentTimeMillis()/1000);
        return result;
    }
    public  static ResultEntity<?> error(@NonNull ResponseEnum responseEnum){
        ResultEntity<Map> result=new ResultEntity<>();
        result.setSuccess(false);
        result.setCode(responseEnum.code);
        result.setMsg(responseEnum.msg);
        result.setData(Collections.EMPTY_MAP);
        result.setTimeStamp(System.currentTimeMillis()/1000);
        return result;
    }

    public  static ResultEntity<?> error(@NonNull ResponseEnum responseEnum,String msg){
        ResultEntity<Map> result=new ResultEntity<>();
        result.setSuccess(false);
        result.setCode(responseEnum.code);
        result.setMsg(StringUtils.hasText(msg)?msg:responseEnum.msg);
        result.setData(Collections.EMPTY_MAP);
        result.setTimeStamp(System.currentTimeMillis()/1000);
        return result;
    }

}
