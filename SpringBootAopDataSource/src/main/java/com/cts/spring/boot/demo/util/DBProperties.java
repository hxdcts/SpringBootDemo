package com.cts.spring.boot.demo.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

/**
 * 实际数据源配置
 */
@Component
@Data
@ConfigurationProperties(prefix = "hikari")
public class DBProperties {
    private HikariDataSource test1;
    private HikariDataSource test2;
	public HikariDataSource getTest1() {
		return test1;
	}
	public void setTest1(HikariDataSource test1) {
		this.test1 = test1;
	}
	public HikariDataSource getTest2() {
		return test2;
	}
	public void setTest2(HikariDataSource test2) {
		this.test2 = test2;
	}
    
}