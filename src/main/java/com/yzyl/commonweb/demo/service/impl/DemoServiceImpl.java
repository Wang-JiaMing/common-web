package com.yzyl.commonweb.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzyl.commonweb.demo.dao.IDemoDao;
import com.yzyl.commonweb.demo.model.DemoTable;
import com.yzyl.commonweb.core.pages.ResultPages;
import com.yzyl.commonweb.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    public ResultPages getAllTableName(DemoTable demoTable) {
        /**
         * 获取页面传入的行数和页数
         *      使用分页插件进行分页
         */
        PageHelper.startPage(Integer.valueOf(demoTable.getOffset()), Integer.valueOf(demoTable.getLimit()));
        List<DemoTable> allTableName=demoDao.selectAllTableName(demoTable);
        PageInfo<DemoTable> pojoPageInfo = new PageInfo<>(allTableName);
        ResultPages resultPages = new ResultPages(pojoPageInfo.getTotal(), allTableName);
        return resultPages;
    }

    @Override
    public List<Map<String, Object>> getAllTableName2(DemoTable demoTable) {
        return demoDao.getListMap(demoTable);
    }
}
