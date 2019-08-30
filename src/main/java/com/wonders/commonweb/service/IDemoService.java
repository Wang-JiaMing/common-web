package com.wonders.commonweb.service;

import com.wonders.commonweb.model.Table;
import com.wonders.commonweb.pages.ResultList;

import java.util.List;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.service
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
public interface IDemoService {

    ResultList getAllTableName(Table table);

}
