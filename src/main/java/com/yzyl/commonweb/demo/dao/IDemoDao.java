package com.yzyl.commonweb.demo.dao;

import com.yzyl.commonweb.demo.model.DemoTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.dao
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Component
@Mapper
public interface IDemoDao {


    @Select("select table_name tableName from all_tables where owner='${owner}'")
    List<DemoTable> selectAllTableName(DemoTable demoTable);

    @Select("select table_name tableName from all_tables where owner='${owner}'")
    List<Map<String,Object>> getListMap(DemoTable demoTable);

}
