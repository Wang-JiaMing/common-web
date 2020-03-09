package com.wonders.commonweb.core.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2020-02-25
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/down")
public class DownFileController {

    /**
     * 下载模板文件
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "dowmLoadTemplate", method = RequestMethod.POST)
    public void dowmLoadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String filePath = request.getParameter("d_filePath");
            String downfileName = request.getParameter("d_downfileName");
            downLoadTemplate(request, response, filePath,downfileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载模板
     *
     * @param req
     * @param response
     * @param filePath
     * @param downfileName
     */
    public void downLoadTemplate(HttpServletRequest req, HttpServletResponse response, String filePath, String downfileName) {
        InputStream in = null;
        OutputStream os = null;
        try {
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            in = new FileInputStream(new File(ResourceUtils.getURL("classpath:").getPath()+"static/downFiles/"+filePath));//req.getSession().getServletContext().getResourceAsStream(ResourceUtils.getURL("classpath:").getPath()+filePath);
            if(in==null){
                ClassPathResource resource = new ClassPathResource("\\static\\downFiles\\"+filePath);
                in=resource.getInputStream();
            }

            String fileName = downfileName;
            os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + suffix);
            response.setContentType("application/octet-stream");
            byte[] buf = new byte[1024];
            int i = 0;
            while ((i = in.read(buf)) != -1) {
                os.write(buf, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
