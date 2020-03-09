package com.wonders.commonweb.core.service;

import com.wonders.commonweb.core.model.DemoTable;
import com.wonders.commonweb.core.pages.ResultList;

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

    ResultList getAllTableName(DemoTable demoTable);

    List<Map<String,Object>> getAllTableName2(DemoTable demoTable);
}
