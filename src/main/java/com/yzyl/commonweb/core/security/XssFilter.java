package com.yzyl.commonweb.core.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "*", filterName = "userFilter")
@Slf4j
public class XssFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        request=new XssHttpServletRequestWrapper((HttpServletRequest) request);
//        System.out.println(request.getParameter("a"));
        chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("加载Xss安全拦截器");
    }

    @Override
    public void destroy() {

    }
}
