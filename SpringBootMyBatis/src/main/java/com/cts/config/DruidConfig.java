package com.cts.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by LF on 2017/4/18.
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean servletRegistration() {
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new StatViewServlet());        //添加初始化参数：initParams
        servletRegistration.addUrlMappings("/druid/*");
        //白名单
        servletRegistration.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistration.addInitParameter("deny", "192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistration.addInitParameter("loginUsername", "root");
        servletRegistration.addInitParameter("loginPassword", "root");
        //是否能够重置数据.
        servletRegistration.addInitParameter("resetEnable", "false");
        return servletRegistration;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}	