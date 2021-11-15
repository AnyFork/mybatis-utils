package com.anyfork.modules;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 小紫念沁
 * @Description: 公共实体类
 * @date 2021/10/26 15:28
 */
@Setter
@Getter
public class BaseEntity implements Serializable {

    @TableField(value = "created_time", fill = FieldFill.INSERT)
    public LocalDateTime createdTime;

    @TableField(value = "updated_time", fill = FieldFill.UPDATE)
    public LocalDateTime updatedTime;

    @TableField(value = "created_by", fill = FieldFill.INSERT)
    public Long createdBy;

    @TableField(value = "updated_by", fill = FieldFill.UPDATE)
    public Long updatedBy;

    @TableField(exist = false)
    public Integer page;

    @TableField(exist = false)
    public Integer pageSize;

    /**
     * 校验分组
     */
    public interface AddValidator {};
    public interface UpdateValidator {};
    public interface SelectValidator {};
    public interface DelValidator {};
    public interface PageValidator {};
    public interface SelectPageListValidator {};
    public interface SelectedListValidator {};
    public interface GetListValidator {};

}
