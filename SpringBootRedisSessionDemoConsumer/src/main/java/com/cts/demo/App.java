package com.cts.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Hello world!
 *
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 60,redisNamespace="com.cts")
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        
    	new SpringApplicationBuilder(App.class).web(true).run(args);
    }
}
