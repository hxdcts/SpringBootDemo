package com.comven.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 程序启动类
 * 由于我们要采用多数据源，这里排除数据库自动配置
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.comven.example.mapper")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
