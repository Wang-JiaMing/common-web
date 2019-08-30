package com.wonders.commonweb.controller;

import com.wonders.commonweb.model.DemoTable;
import com.wonders.commonweb.pages.ResultList;
import com.wonders.commonweb.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    IDemoService demoService;

    /**
     * thymeleaf局部刷新例子
     *
     * @return
     */
    @RequestMapping("/demo")
    public String demo() {
        return "testPage/TestPage::testPage";
    }

    @RequestMapping("/demoData")
    @ResponseBody
    public ResultList getDemoData(DemoTable demoTable) {
        return demoService.getAllTableName(demoTable);
    }

}
