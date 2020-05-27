package com.wonders.commonweb.core.controller;

import com.wonders.commonweb.core.service.ICommonService;
import com.wonders.commonweb.core.utils.SsoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/indexPage")
@Slf4j
public class IndexController {

    @Autowired
    Environment environment;

    @Autowired
    ICommonService commonService;

    @Value("${SSO_IP_AND_PORT}${INNER_RESOURCE_URL}")
    private String INNER_RESOURCE_URL;

    @RequestMapping("/index")
    public String toIndex(Model model) {
        String loginName = null;
        try {
            loginName = SsoUtils.getLoginName();
        } catch (Exception e) {
            loginName="临时用户";
            log.error("通过SSO获取用户名失败");
        }

        model.addAttribute("IS_SSO_MENUS",environment.getProperty("IS_SSO_MENUS"));
        model.addAttribute("TITLE",environment.getProperty("title"));
        model.addAttribute("SSO_IP_AND_PORT",environment.getProperty("SSO_IP_AND_PORT"));
        model.addAttribute("loginName", loginName);
        return "pages/index";
    }

    /**
     * 查询内部资源
     * @return
     */
    @RequestMapping("/menus")
    @ResponseBody
    public Object listInnerResource(HttpServletResponse response) throws Exception{
        String userName = SsoUtils.getLoginName();
        String userIdSql="select userid from tb_auth_user where loginname = '"+userName+"'";
        Map<String,String> sqlParams=new HashMap<>();
        sqlParams.put("sql",userIdSql);
        List<Map<String,Object>> reuslt=commonService.query(sqlParams);
        sqlParams.clear();
        String url = INNER_RESOURCE_URL.replace("USER_ID",reuslt.get(0).get("USERID").toString());
        String result = SsoUtils.httpClient(url);
        System.out.println(result);
        return result;
    }

}
