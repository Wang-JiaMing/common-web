package com.wonders.commonweb.demo.conf;

import com.wonders.commonweb.demo.service.HelloWebService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.xml.ws.Endpoint;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.demo.conf
 * @authorName:wangjiaming
 * @createDate:2020/3/24
 * @editor:IntelliJ IDEA
 * @other:
 **/
//WEBSERVICE 配置webservice配置文件
@Configuration
@Order(1)
public class CxfWebServiceConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private HelloWebService helloWebService;

    //WEBSERVICE: 注入servlet  bean name不能dispatcherServlet 否则会覆盖dispatcherServlet
    @Bean(name = "cxfServletRegistration")
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(),"/webservice/*");
    }

    /**
     *
     * 注册WebServiceDemoService接口到webservice服务
     * @return
     */
    @Bean
    public Endpoint endpoint(){
        EndpointImpl endpoint=new EndpointImpl(bus,helloWebService);
        endpoint.publish("/HelloWebService");//发布地址
        return endpoint;
    }

}
