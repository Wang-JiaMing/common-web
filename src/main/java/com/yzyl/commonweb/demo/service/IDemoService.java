package com.yzyl.commonweb.demo.service;

import com.yzyl.commonweb.demo.model.DemoTable;
import com.yzyl.commonweb.core.pages.ResultPages;

import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.service
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
public interface IDemoService {

    ResultPages getAllTableName(DemoTable demoTable);

    List<Map<String,Object>> getAllTableName2(DemoTable demoTable);

}
