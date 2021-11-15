package com.anyfork.interceptor;

import com.anyfork.field.encrypt.EncryptorProperties;
import com.anyfork.field.encrypt.FieldEncryptHandler;
import com.anyfork.field.encrypt.IEncryptor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

/**
 * @PackageName: com.anyfork.interceptor
 * @ClassName: FieldEncryptInterceptor
 * @Description: 字段加密拦截器
 * @Author: 小紫念沁
 * @Date: 2021/11/11 16:59
 * @Version 1.0
 */
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class, Object.class})})
public class FieldEncryptInterceptor implements Interceptor {

    private final IEncryptor encryptor;

    private final EncryptorProperties encryptorProperties;

    public FieldEncryptInterceptor(IEncryptor encryptor, EncryptorProperties encryptorProperties) {
        this.encryptor = encryptor;
        this.encryptorProperties = encryptorProperties;
    }
    @Override
    public Object intercept(Invocation var1) throws Throwable {
        return FieldEncryptHandler.handlerResult(var1, this.encryptorProperties, this.encryptor);
    }

    @Override
    public Object plugin(Object var1) {
        return var1 instanceof Executor ? Plugin.wrap(var1, this) : var1;
    }

}
