package com.yzyl.commonweb.demo.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

//WEBSERVICE:2引入webservice标签
@WebService
public interface HelloWebService {
    //WEBSERVICE:3 标注暴露方法
    @WebMethod
    public String Hello(@WebParam(name = "testXml") String name);

}
