package com.wonders.commonweb.controller;

import com.wonders.commonweb.dao.IDemoDao;
import com.wonders.commonweb.model.Table;
import com.wonders.commonweb.pages.ResultList;
import com.wonders.commonweb.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/indexPage")
public class IndexController {

    @RequestMapping("/index")
    public String toIndex() {
        return "pages/index";
    }



}
