package com.yzyl.commonweb.core.config;

import com.wonders.filter.LogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.core.config
 * @authorName:wangjiaming
 * @createDate:2021/3/4
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Configuration
@Slf4j
public class LogConfig {

    @Value("${LOG_URL}")
    private String LOG_URL;

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(10);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("session_user", "edu.yale.its.tp.cas.client.filter.user");
        initParameters.put("log_url", LOG_URL);
        //南沙单点
        //initParameters.put("model", "model");
        filterRegistrationBean.setInitParameters(initParameters);
        return filterRegistrationBean;
    }
}
