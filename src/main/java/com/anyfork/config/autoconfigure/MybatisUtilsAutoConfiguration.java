package com.anyfork.config.autoconfigure;

import com.anyfork.config.FieldEncryptConfig;
import com.anyfork.field.encrypt.EncryptorProperties;
import com.anyfork.field.encrypt.IEncryptor;
import com.anyfork.field.sensitive.ISensitiveStrategy;
import com.anyfork.field.sensitive.SensitiveInfoSerializer;
import com.anyfork.interceptor.FieldDecryptInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;

import javax.annotation.Resource;

/**
 * @PackageName: com.anyfork.config.autoconfigure
 * @ClassName:
 * @Description: TODO
 * @Author: 小紫念沁
 * @Date: 2021/11/07 16:40
 * @Version 1.0
 */
@Lazy
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({EncryptorProperties.class})
@Import({FieldEncryptConfig.class})
@Slf4j
public class MybatisUtilsAutoConfiguration implements InitializingBean {

    @Resource
    private EncryptorProperties encryptorProperties;

    @Resource
    private ISensitiveStrategy sensitiveStrategy;

    @Resource
    private IEncryptor encryptor;

    /**
     * 注入字段解密拦截器
     **/
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "anyfork.encryptor",name = "password")
    public FieldDecryptInterceptor fieldDecryptInterceptor() {
        if (null == encryptor) {
            log.error("Failed to injected IEncryptor");
            return null;
        } else {
            log.info("Successful to load fieldDecryptInterceptor");
            return new FieldDecryptInterceptor(encryptor,encryptorProperties);
        }
    }

    @Override
    public void afterPropertiesSet(){
        //如果脱敏策略bean存在，设置脱敏策略.
        if (null !=sensitiveStrategy) {
            SensitiveInfoSerializer.setSensitiveStrategy(sensitiveStrategy);
        }
    }
}
