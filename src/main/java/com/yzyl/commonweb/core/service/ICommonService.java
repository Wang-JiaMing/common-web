package com.yzyl.commonweb.core.service;

import com.yzyl.commonweb.core.pages.ResultPages;

import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.service
 * @authorName:wangjiaming
 * @createDate:2020-01-31
 * @editor:IntelliJ IDEA
 * @other:
 **/
public interface ICommonService {

    List<Map<String,Object>> query(Map<String,String> params);

    ResultPages queryForResultList(Map<String,String> params);
}
