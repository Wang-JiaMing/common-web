package com.wonders.commonweb.core.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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

    @Select("${sql}")
    List<Map<String,Object>> query(Map<String,String> params);

    @Update("${sql}")
    void update(Map<String,String> params);

    @Insert("${sql}")
    void insert(Map<String,String> params);
}
