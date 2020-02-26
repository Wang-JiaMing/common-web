package com.wonders.commonweb.common.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.dao
 * @authorName:wangjiaming
 * @createDate:2020-01-31
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Mapper
public interface ICommonDao {

    List<Map<String,Object>> query(Map<String,String> params);
}
