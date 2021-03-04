package com.wonders.commonweb.demo.restApi;

import com.wonders.commonweb.core.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.demo.restApi
 * @authorName:wangjiaming
 * @createDate:2021/3/4
 * @editor:IntelliJ IDEA
 * @other:
 **/
@RestController
@RequestMapping("/demoRestApi")
public class DemoInterfaceRest {

    @RequestMapping("/test")
    public Message testRestApi(@RequestBody String str){
        return new Message(true,"server:"+str);
    }

}
