package com.wonders.commonweb.demo.controller;

import com.alibaba.druid.util.StringUtils;
import com.expansion.excel.ExcelUtils;
import com.wonders.commonweb.core.model.Message;
import com.wonders.commonweb.core.pages.ResultPages;
import com.wonders.commonweb.core.service.ICommonService;
import com.wonders.commonweb.core.utils.DataTrans;
import com.wonders.commonweb.core.utils.FileUtils;
import com.wonders.commonweb.demo.model.DemoTable;
import com.wonders.commonweb.demo.service.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.demo.controller
 * @authorName:wangjiaming
 * @createDate:2020-03-09
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/myDemo")
public class MyDemoController {

    @Autowired
    IDemoService demoService;

    @Autowired
    ICommonService commonService;

    /**
     * thymeleaf局部刷新例子
     *
     * @return
     */
    @RequestMapping("/demo")
    public String demo() {
        return "testPage/TestPage::testPage";
    }

    /**
     * thymeleaf局部刷新例子
     *
     * @return
     */
    @RequestMapping("/impPage")
    public String impPage() {
        return "testPage/filePage::testPage";
    }

    /**
     * thymeleaf局部刷新例子
     *
     * @return
     */
    @RequestMapping("/autoFomPage")
    public String autoFomPage() {
        return "testPage/autoForm::testPage";
    }

    /**
     * Excel导入例子
     * TODO:文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/importExp")
    @ResponseBody
    public Message importExp(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        List<List<List<String>>> excelList = ExcelUtils.excelChangeMap(file);
        for (List<List<String>> sheets : excelList) {
            System.out.println("第一个sheet");
            for (List<String> rows : sheets) {
                System.out.println("rows");
                for (String cells : rows) {
                    System.out.println("cells" + cells);
                    /**
                     * TODO:自行补充入库逻辑即可
                     */
                }
            }
        }
        return new Message(true, "success");
    }

    /**
     * 数据库导出例子
     * TODO:动态Excel下载 3
     * @param response
     */
    @RequestMapping("/download")
    @ResponseBody
    public void Download(HttpServletResponse response) {
        List<String> headers = new ArrayList<>();
        headers.add("title1");
        headers.add("title2");
        List<String> header1 = new ArrayList<>();
        DemoTable demoTable = new DemoTable();
        demoTable.setOwner("RHIN_APP");
        List<Map<String, Object>> results = demoService.getAllTableName2(demoTable);
        List<List<String>> excelData = DataTrans.ListMapTransDoubleList(results);
        FileUtils.DownloadExcelByDataBase(response, "测试.xls", headers, excelData);
    }

    /**
     * datagird例子
     * @param demoTable
     * @return
     */
    @RequestMapping("/demoData")
    @ResponseBody
    public ResultPages getDemoData(DemoTable demoTable) {
        return demoService.getAllTableName(demoTable);
    }


    /**
     * 1.加入动态sql分页方法
     **/
    @RequestMapping("/all_tables_lise")
    @ResponseBody
    public ResultPages all_tables_lise(DemoTable demoTable ){
        StringBuffer whereSql=new StringBuffer();
        if(!StringUtils.isEmpty(demoTable.getTableName())){
            whereSql.append("and table_name like '%"+demoTable.getTableName()+"%' " );
        }
        String sql="select owner,table_name,tablespace_name from all_tables where 1=1 "+whereSql;
        Map<String, String> params = new HashMap<>();
        params.put("sql", sql);
        //排序字段
        params.put("sortField","owner");
        //升序、降序
        params.put("sort","desc");
        //分页起始下标
        params.put("offset", demoTable.getOffset());
        //分页间距
        params.put("limit", demoTable.getLimit());
        return commonService.queryForResultList(params);
    }


}
