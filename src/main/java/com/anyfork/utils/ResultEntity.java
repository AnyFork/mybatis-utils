package com.anyfork.utils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 小紫念沁
 * @description: TODO
 * @date 2021/10/24 002416:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultEntity<T> implements Serializable {

    private boolean success=true;
    private T data;
    private Integer code;
    private String msg;
    private Long timeStamp;
}
