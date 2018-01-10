package com.cts.spring.boot.demo.util;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置
 */
@Configuration
@EnableScheduling
@Slf4j
public class DataSourceConfig {

    @Autowired
    private DBProperties properties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        //按照目标数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap();
        targetDataSources.put("test1", properties.getTest1());
        targetDataSources.put("test2", properties.getTest2());
        //采用是想AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        //设置默认的数据源，当拿不到数据源时，使用此配置
        dataSource.setDefaultTargetDataSource(properties.getTest1());
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}