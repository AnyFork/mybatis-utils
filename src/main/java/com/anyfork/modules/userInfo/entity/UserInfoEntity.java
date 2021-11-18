package com.anyfork.modules.userInfo.entity;

import com.anyfork.annotation.FieldEncrypt;
import com.anyfork.annotation.FieldSensitive;
import com.anyfork.constant.SensitiveType;
import com.anyfork.enums.Algorithm;
import com.anyfork.enums.GenderEnum;
import com.anyfork.modules.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @PackageName: com.anyfork.modules.userInfo.entity
 * @ClassName: UserInfoEntity
 * @Description: 用户实体，必须加上@AllArgsConstructor  @NoArgsConstructor，否则加上select = false会报错
 * @Author: 小紫念沁
 * @Date: 2021/10/28 12:45
 * @Version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName(value = "user_info")
@Builder
public class UserInfoEntity extends BaseEntity implements Serializable {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    @TableField("user_name")
    @FieldSensitive(type =SensitiveType.CHINESE_NAME)
    @FieldEncrypt(algorithm = Algorithm.RSA)
    private String userName;

    @TableField("user_phone")
    @FieldSensitive(type = SensitiveType.MOBILE)
    @FieldEncrypt(algorithm = Algorithm.PBEWithMD5AndDES)
    private String userPhone;

    @TableField("user_birth")
    private LocalDate userBirth;

    @TableField("user_password")
    @FieldSensitive(type =SensitiveType.PASSWORD)
    @FieldEncrypt(algorithm = Algorithm.MD5_32)
    private String userPassword;

    @TableField(value = "user_status",fill = FieldFill.INSERT)
    private Integer userStatus;

    @TableField("user_gender")
    private GenderEnum userGender;

    @TableField(value = "delete_flag",fill = FieldFill.INSERT,select = false)
    @TableLogic
    private Integer deleteFlag;

    @TableField(value = "version",fill = FieldFill.INSERT,select = false)
    @Version
    private Integer version;
}
