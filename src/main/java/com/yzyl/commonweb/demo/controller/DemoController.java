package com.yzyl.commonweb.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2019-08-30
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/demoPage")
public class DemoController {

    @RequestMapping("/index")
    public String demo(String pageName) {
        return "demo/"+pageName+"::pageContent";
    }

}
