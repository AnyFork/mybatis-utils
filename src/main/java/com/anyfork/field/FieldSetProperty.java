package com.anyfork.field;

import com.anyfork.annotation.FieldEncrypt;

/**
 * @PackageName: com.anyfork.utils
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/07 20:36
 * @Version 1.0
 */
public class FieldSetProperty {
    private String fieldName;
    private FieldEncrypt fieldEncrypt;

    public String getFieldName() {
        return this.fieldName;
    }

    public FieldEncrypt getFieldEncrypt() {
        return this.fieldEncrypt;
    }


    public void setFieldName(String var1) {
        this.fieldName = var1;
    }

    public void setFieldEncrypt(FieldEncrypt var1) {
        this.fieldEncrypt = var1;
    }


    public FieldSetProperty(String var1, FieldEncrypt var2) {
        this.fieldName = var1;
        this.fieldEncrypt = var2;
    }
}
