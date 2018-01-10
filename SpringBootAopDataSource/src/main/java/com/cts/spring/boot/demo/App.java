package com.cts.spring.boot.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class App 
{
    public static void main( String[] args )
    {
    	new SpringApplicationBuilder(App.class).web(true).run(args);
    }
}
