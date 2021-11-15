package com.anyfork.interceptor;

import com.anyfork.field.encrypt.EncryptorProperties;
import com.anyfork.field.encrypt.FieldDecryptHandler;
import com.anyfork.field.encrypt.IEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Statement;

/**
 * @PackageName: com.anyfork.config
 * @ClassName: MybatisInterceptor
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/10/28 14:44
 * @Version 1.0
 */
@Intercepts({ @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = { Statement.class }) })
@Slf4j
public class FieldDecryptInterceptor implements Interceptor {

    private final IEncryptor encryptor;

    private final EncryptorProperties encryptorProperties;


    public FieldDecryptInterceptor(IEncryptor var1, EncryptorProperties var3) {
        this.encryptor = var1;
        this.encryptorProperties = var3;
        FieldDecryptHandler.configure(null != var1);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return FieldDecryptHandler.handlerResult(invocation, (var1x, var2) -> {
            FieldDecryptHandler.isEncrypt(this.encryptor, this.encryptorProperties, var1x, var2);
        });
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }
}
