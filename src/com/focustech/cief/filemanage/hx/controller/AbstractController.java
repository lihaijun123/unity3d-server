package com.focustech.cief.filemanage.hx.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.focustech.cief.filemanage.hx.constant.HxConstant;
/**
 *
 * *
 * @author lihaijun
 *
 */
public class AbstractController {

	/**
	 * 获取移动设备类型
	 * *
	 * @param ua 需要转换成小写
	 * @return
	 */
	public int getMobileType(String systemType){
		/*String ua = systemType.getHeader("User-Agent").toLowerCase();
		System.out.println("ua：" + ua);
		int rv = 0;
		if(ua.contains("iphone") || ua.contains("ipad") || ua.contains("cfnetwork")){
			rv = HxConstant.MOBILE_TYPE_IOS;
		}
		if(ua.contains("android")){
			rv = HxConstant.MOBILE_TYPE_ANDROID;
		}
		System.out.println("mobileType：" + rv);
		return rv;*/
		int rv = 0;
		if("ios".equalsIgnoreCase(systemType)){
			rv = HxConstant.MOBILE_TYPE_IOS;
		} else if("android".contains(systemType)){
			rv = HxConstant.MOBILE_TYPE_ANDROID;
		} else {
			rv = HxConstant.MOBILE_TYPE_ANDROID;
		}
		return rv;
	}

	 /**
     * ajax输出
     *
     * @param response HttpServletResponse
     * @param outputString 输出字符
     * @throws IOException 输出流异常
     */
    protected void ajaxOutput(HttpServletResponse response, String outputString) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(outputString);
        response.getWriter().flush();
    }
}
