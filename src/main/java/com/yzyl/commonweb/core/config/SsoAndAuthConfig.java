package com.yzyl.commonweb.core.config;

import com.wondersgroup.common.web.tools.AutoSetUserAdapterFilter;
import lombok.extern.slf4j.Slf4j;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EventListener;

/**
 * @projectName:ns-platform
 * @packageName:com.wonders.nsplatform.config
 * @authorName:wangjiaming
 * @createDate:2019-05-28
 * @editor:IntelliJ IDEA
 * @other: config fitler
 **/
//@Configuration

@Slf4j
public class SsoAndAuthConfig {

    @Value("${SSO_IP_AND_PORT}")
    private String SSO_IP_AND_PORT;

    @Value("${LOCAL_SERVER_ADDR}")
    private String LOCAL_SERVER_ADDR;

    @Bean
    public ServletListenerRegistrationBean setSingleSignListener() {
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new SingleSignOutHttpSessionListener());
        log.info("loaded SingleSignOutHttpSessionListener");
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean setSingleSignOutFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SingleSignOutFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        log.info("loaded SingleSignOutFilter");
        return registration;
    }

    @Bean
    public FilterRegistrationBean setAuthenticationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AuthenticationFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(2);
        registration.addInitParameter("casServerLoginUrl", SSO_IP_AND_PORT + "/sso/login");
        registration.addInitParameter("serverName", LOCAL_SERVER_ADDR);
        registration.addInitParameter("myMatchUrl", "ws");
        log.info("loaded AuthenticationFilter");
        return registration;
    }

    @Bean
    public FilterRegistrationBean setCas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Cas20ProxyReceivingTicketValidationFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(3);

        registration.addInitParameter("casServerUrlPrefix", SSO_IP_AND_PORT + "/sso");
        registration.addInitParameter("serverName", LOCAL_SERVER_ADDR);

        log.info("loaded Cas20ProxyReceivingTicketValidationFilter");

        return registration;
    }

    @Bean
    public FilterRegistrationBean setHttpServletRequestWrapperFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new HttpServletRequestWrapperFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(4);

        log.info("loaded HttpServletRequestWrapperFilter");


        return registration;
    }

    @Bean
    public FilterRegistrationBean setAssertionThreadLocalFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AssertionThreadLocalFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(5);

        log.info("loaded AssertionThreadLocalFilter");


        return registration;
    }

    @Bean
    public FilterRegistrationBean setAutoSetUserAdapterFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new AutoSetUserAdapterFilter());
        registration.addUrlPatterns("/*");

        registration.addInitParameter("ssowsUrl", SSO_IP_AND_PORT + "/ssows/services/ssoservice?wsdl");

        registration.setOrder(6);

        log.info("loaded AutoSetUserAdapterFilter");

        return registration;
    }


}
