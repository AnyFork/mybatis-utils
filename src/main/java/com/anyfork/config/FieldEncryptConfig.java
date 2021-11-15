package com.anyfork.config;

import com.anyfork.field.encrypt.EncryptorProperties;
import com.anyfork.field.encrypt.DefaultEncryptor;
import com.anyfork.field.encrypt.IEncryptor;
import com.anyfork.interceptor.FieldEncryptInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @PackageName: com.anyfork.config
 * @ClassName: FieldEncryptConfig
 * @Description: 字段加解密配置类，当加解密口令存在时进行bean的依赖注入
 * @Author: 小紫念沁
 * @Date: 2021/11/11 16:45
 * @Version 1.0
 */
@Lazy
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "anyfork.encryptor",name = "password")
@Slf4j
public class FieldEncryptConfig {

    @Resource
    private EncryptorProperties encryptorProperties;

    /**
     * 注入默认加解密处理类
     **/
    @Bean
    @ConditionalOnMissingBean
    public IEncryptor encryptor() {
        return new DefaultEncryptor();
    }

    /**
     * 注入字段加密拦截器
     **/
    @Bean
    @ConditionalOnMissingBean
    public FieldEncryptInterceptor fieldEncryptInterceptor(IEncryptor encryptor) {
        log.info("Successful to load fieldEncryptInterceptor");
        return new FieldEncryptInterceptor(encryptor, encryptorProperties);
    }

}
