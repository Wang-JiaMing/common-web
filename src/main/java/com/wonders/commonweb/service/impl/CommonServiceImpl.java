package com.wonders.commonweb.service.impl;

import com.wonders.commonweb.dao.ICommonDao;
import com.wonders.commonweb.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.service.impl
 * @authorName:wangjiaming
 * @createDate:2020-01-31
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Service
public class CommonServiceImpl implements ICommonService {

    @Autowired
    ICommonDao commonDao;

    @Override
    public List<Map<String, Object>> query(Map<String, String> params) {
        return commonDao.query(params);
    }
}
