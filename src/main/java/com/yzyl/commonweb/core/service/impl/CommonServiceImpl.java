package com.yzyl.commonweb.core.service.impl;

import com.yzyl.commonweb.core.dao.ICommonDao;
import com.yzyl.commonweb.core.pages.ResultPages;
import com.yzyl.commonweb.core.service.ICommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    @Override
    public ResultPages queryForResultList(Map<String, String> params) {
        String sortString = null;
        if (params.containsKey("sortField")) {
            if (params.containsKey("sort")) {
                sortString = "order by " + params.get("sortField") + " " + params.get("sort");
            }else{
                sortString = "order by " + params.get("sortField");
            }
        }

        Map<String, String> useParams = new HashMap<>();
        for (String key : params.keySet()) {
            if (!key.equals("pageSize") && !key.equals("pageNo")&& !key.equals("sortField")&& !key.equals("sort")) {
                useParams.put(key, params.get(key));
            }
        }
        StringBuffer totalSql = new StringBuffer();
        totalSql.append("select count(1) total from(" + params.get("sql") + ")t");
        useParams.put("sql", totalSql.toString());
        List<Map<String, Object>> totalResult = commonDao.query(useParams);

        StringBuffer mainSql = new StringBuffer();
        mainSql.append("select * from(select tmp.*,rownum rn from(" + params.get("sql") + ")tmp ");
        if (sortString != null) {
            mainSql.append(sortString);
        }
        mainSql.append(")page where page.rn>" + (Integer.valueOf(params.get("offset"))) + " and page.rn<=" + ((Integer.valueOf(params.get("offset")))+ Integer.valueOf(params.get("limit"))));
        useParams.put("sql", mainSql.toString());
        List<Map<String, Object>> mainResult = commonDao.query(useParams);
        ResultPages resultPages = new ResultPages(Long.valueOf(totalResult.get(0).get("TOTAL").toString()), mainResult);
        return resultPages;
    }
}
