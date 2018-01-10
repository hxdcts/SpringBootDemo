package com.cts.spring.boot.demo.config;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class EncryptPropertyPlaceholderAutoConfiguration {

//    private static final String SECURITY_PROPERTIES_FILE = "security.properties";
//
//    @Bean
//    @ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
//    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
//        encryptor.setPassword("security");
//        EncryptablePropertySourcesPlaceholderConfigurer
//                configurer = new EncryptablePropertySourcesPlaceholderConfigurer(encryptor);
//        configurer.setLocation(new ClassPathResource(SECURITY_PROPERTIES_FILE));
//        return configurer;
//    }
}
