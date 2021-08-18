package com.yzyl.commonweb.demo.service.impl;

import com.yzyl.commonweb.demo.service.HelloWebService;
import org.springframework.stereotype.Service;

import javax.jws.WebService;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.demo.service.impl
 * @authorName:wangjiaming
 * @createDate:2020/3/24
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Service

//EXP 编写实现类注解
@WebService(serviceName = "HelloWebService", // 与接口中指定的name一致
        targetNamespace = "http://webservice.business.mixpay.com", // 与接口中的命名空间一致,一般是接口的包名倒
        endpointInterface = "com.yzyl.commonweb.demo.service.HelloWebService")// 接口地址
public class HelloWebServiceImpl implements HelloWebService {

    @Override
    public String Hello(String name) {
        System.out.println("欢迎你：" + name);
        return "欢迎你" + name;
    }
}
