package com.wonders.commonweb.service.impl;

import com.wonders.commonweb.dao.IDemoDao;
import com.wonders.commonweb.model.DemoTable;
import com.wonders.commonweb.pages.ResultList;
import com.wonders.commonweb.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.service.impl
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Service
public class DemoServiceImpl implements IDemoService {

    @Autowired
    IDemoDao demoDao;

    @Override
    public ResultList getAllTableName(DemoTable demoTable) {
        /**
         * 获取页面传入的行数和页数
         *      使用分页插件进行分页
         */
//        PageHelper.offsetPage(Integer.valueOf(demoTable.getOffset()), Integer.valueOf(demoTable.getLimit()));
//        List<DemoTable> allTableName=demoDao.selectAllTableName(demoTable);
//        PageInfo<DemoTable> pojoPageInfo = new PageInfo<>(allTableName);
//        ResultList resultList = new ResultList(pojoPageInfo.getTotal(), allTableName);
//        return resultList;
        return null;
    }
}
