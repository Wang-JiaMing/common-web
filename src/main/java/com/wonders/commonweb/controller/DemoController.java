package com.wonders.commonweb.controller;

import com.expansion.excel.ExcelUtils;
import com.wonders.commonweb.model.DemoTable;
import com.wonders.commonweb.model.Message;
import com.wonders.commonweb.pages.ResultList;
import com.wonders.commonweb.service.IDemoService;
import com.wonders.commonweb.utils.DataTrans;
import com.wonders.commonweb.utils.FileUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2019-08-30
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/demoPage")
public class DemoController {

    @Autowired
    IDemoService demoService;

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
     * datagird例子
     * @param demoTable
     * @return
     */
    @RequestMapping("/demoData")
    @ResponseBody
    public ResultList getDemoData(DemoTable demoTable) {
        return demoService.getAllTableName(demoTable);
    }

    /**
     * Excel导入例子
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

                }
            }
        }
        return new Message(true, "success");
    }

    /**
     * 数据库导出例子
     * @param response
     */
    @RequestMapping("/download")
    @ResponseBody
    public void Download(HttpServletResponse response) {
        List<String> headers=new ArrayList<>();
        headers.add("title1");
        headers.add("title2");
        List<String> header1=new ArrayList<>();
        DemoTable demoTable=new DemoTable();
        demoTable.setOwner("RHIN_APP");
        List<Map<String,Object>>results=demoService.getAllTableName2(demoTable);
        List<List<String>> excelData= DataTrans.ListMapTransDoubleList(results);
        FileUtils.DownloadExcelByDataBase(response,"测试.xls",headers,excelData);
    }

}
