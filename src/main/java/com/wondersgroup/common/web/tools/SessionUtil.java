/**
 * 创建日期：Nov 1, 2012
 * 作者： "王胤洪"
 * 版权： 指明该文件的版权信息
 * 功能： 指明该文件所实现的功能
 */

package com.wondersgroup.common.web.tools;

import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

/**
 * 从SESSION中获取信息的工具类
 * 
 * @author "王胤洪"
 * 
 */
public class SessionUtil {

	private static String ssowsUrl;

	/**
	 * 在SESSION中放置对象
	 * 
	 * @author LiaoChangjun
	 * 
	 *         创建时间：2013-2-25 下午03:09:27
	 * @return
	 */

	public static void put(HttpServletRequest request, String key, Object obj) {
		Assert.notNull(request);
		request.getSession(true).setAttribute(key, obj);
	}

	/**
	 * 
	 * @Title: 保存对象到SESSION
	 * 
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @param request
	 * @param key
	 * @return Object
	 */
	public static Object get(HttpServletRequest request, String key) {

		return request.getSession(true).getAttribute(key);
	}

	public static String getSsowsUrl() {
		return ssowsUrl;
	}

	public static void setSsowsUrl(String ssowsUrl) {
		SessionUtil.ssowsUrl = ssowsUrl;
	}

}
