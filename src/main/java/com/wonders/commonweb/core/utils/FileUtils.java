package com.wonders.commonweb.core.utils;

import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.utils
 * @authorName:wangjiaming
 * @createDate:2020-02-25
 * @editor:IntelliJ IDEA
 * @other:
 **/
public class FileUtils {

    /**
     * 导出数据库数据到Excel
     * @param response
     * @param fileName excel文件名
     * @param headers   excel标题栏
     * @param data  excel数据，嵌套List，第一次是行，第二次是单元格
     */
    public static void DownloadExcelByDataBase(HttpServletResponse response, String fileName, List<String> headers, List<List<String>> data) {
        //获取集合
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        int rowNum = 0;
        //自定义列标题
//        String[] headers = {"姓名", "性别", "职位"};
        HSSFRow row = sheet.createRow(rowNum);
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }
        for (List<String> rows : data) {//行
            rowNum++;
            HSSFRow rowData = sheet.createRow(rowNum);
            for (int j = 0; j < rows.size(); j++) {
                rowData.createCell(j).setCellValue(rows.get(j));
            }
        }
        if (workbook != null) {
            try {
                String downloadFileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                String headStr = "attachment; filename=\"" + downloadFileName + "\"";
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);
                OutputStream out = response.getOutputStream();
                workbook.write(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
