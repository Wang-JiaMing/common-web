package com.yzyl.commonweb.core.utils;

import org.jasig.cas.client.util.AssertionHolder;
import org.springframework.util.FileCopyUtils;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author : wuzhiheng
 * @Description :
 * @Date Created in 16:35 2019-05-30
 */
public class SsoUtils {

    /**
     * 获取当前登录的用户名
     * @return
     */
    public static String getLoginName(){
        return AssertionHolder.getAssertion().getPrincipal().getName();
    }

    /**
     * 发起请求
     * @param urlStr
     * @return
     * @throws Exception
     */
    public static String httpClient(String urlStr) throws Exception{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(false);
        conn.setDoInput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(10000);

        conn.connect();

        String result = FileCopyUtils.copyToString(new InputStreamReader(conn.getInputStream()));
        return result;
    }

    /**
     * 提取url的前缀 http://localhost:8081/systemMonitor/getSystemReport -> localhost:8081
     * @param url
     * @return
     */
    public static String getIpAndPort(String url){
        String[] split = url.split("/");
        return split[2].replaceAll(":.*","");
    }

    public static void main(String[] args){
        System.out.println(getIpAndPort("http://localhost:8081/systemMonitor/getSystemReport"));
    }
}
