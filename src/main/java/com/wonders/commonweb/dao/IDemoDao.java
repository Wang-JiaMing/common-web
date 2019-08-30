package com.wonders.commonweb.dao;

import com.wonders.commonweb.model.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

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
    List<Table> selectAllTableName(Table table);

}
