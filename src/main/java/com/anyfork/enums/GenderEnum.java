package com.anyfork.enums;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @PackageName: com.dx.mini.enums
 * @ClassName: GenderEnum
 * @Description: 性别枚举类型
 * @author: 小紫念沁
 * @date: 2020/5/3 9:52
 */
@AllArgsConstructor
@Getter
public enum GenderEnum {

    /**
     *  男性
     */
    MALE(1, "男"),
    /**
     * 女性
     */
    FEMALE(2, "女"),
    /**
     * 不详
     */
    NEUTER(3, "不详");

    @EnumValue
    @JsonValue
    private final int key;

    private final String value;

    @JsonCreator
    public static GenderEnum findEnum(String code){
        for (GenderEnum item : GenderEnum.values()) {
            if (item.getKey()==Integer.parseInt(code)) {
                return item;
            }
        }
        return null;
    }
}
