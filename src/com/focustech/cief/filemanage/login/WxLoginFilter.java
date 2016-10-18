package com.focustech.cief.filemanage.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.focustech.cief.filemanage.wxuser.model.WxUserInfo;
import com.focustech.common.utils.Encode;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class WxLoginFilter implements Filter {
	public static final String SID = "sid";
	public static final String SESSION_KEY = "wxloginInfo";
	public static final String LOGIN_PAGE_NAME = "fs/wxuser/login";
	public static final String[] STATIC_RESOURCES = {};
	public static final String[] DYNAMIC_RESOURCES = {};
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		Object sessinObj = session.getAttribute(SESSION_KEY);
		String servletPath = request.getServletPath();
		boolean isPass = false;
		boolean urlContainSessionId = false;
		if(isIncludePassPath(servletPath) || isNotValidOfPreFilter(request)){
			isPass = true;
		} else {
			String mobile = request.getParameter(WxLoginFilter.SID);
			if(StringUtils.isEmpty(mobile)){
				mobile = TCUtil.sv(request.getAttribute(WxLoginFilter.SID));
			} else {
				urlContainSessionId = true;
			}
			if(sessinObj == null) {
				if(StringUtils.isEmpty(mobile)){
					mobile = TCUtil.sv(request.getSession().getAttribute(WxLoginFilter.SID));
				}
				if(StringUtils.isEmpty(mobile)){
					Cookie[] cookies = request.getCookies();
					if(cookies != null){
						for (Cookie cookie : cookies) {
							String name = cookie.getName();
							if(WxLoginFilter.SID.equals(name)){
								mobile = cookie.getValue();
							}
						}
					}
				}
				if(StringUtils.isEmpty(mobile)){
					StringBuffer requestURL = request.getRequestURL();
					request.getSession().setAttribute("gtp", requestURL.toString());
					response.sendRedirect("/" + LOGIN_PAGE_NAME);
				} else {
					isPass = true;
					request.setAttribute(WxLoginFilter.SID, mobile);
					request.getSession().setAttribute(WxLoginFilter.SID, mobile);
					String requestUrl = request.getRequestURI().toString();
					if(isNeedRewriteUrl(requestUrl) && !urlContainSessionId){
						String getRewriteUrl = requestUrl + "?" + WxLoginFilter.SID + "=" + Encode.encoder(mobile);
						String rewriteUrl = getRewriteUrl;
						response.sendRedirect(rewriteUrl);
						return;
					}
				}
			} else {
				RequestThreadLocal.setLoginInfo(sessinObj);
				isPass = true;
				String requestUrl = request.getRequestURI().toString();
				if(isNeedRewriteUrl(requestUrl) && !urlContainSessionId){
					WxUserInfo wxUserInfo = (WxUserInfo)sessinObj;
					String rewriteUrl = requestUrl + "?" + WxLoginFilter.SID + "=" + Encode.encoder(wxUserInfo.getMobile());
					request.setAttribute(WxLoginFilter.SID, wxUserInfo.getMobile());
					response.sendRedirect(rewriteUrl);
					return;
				}
			}
		}
		if(isPass){
			fc.doFilter(req, resp);
		}
	}

	public boolean isIncludePassPath(String servletPath){
		boolean flag = false;
		if(servletPath.equalsIgnoreCase("/" + LOGIN_PAGE_NAME)){
			flag = true;
		} else {
			for(String resource : STATIC_RESOURCES){
				if(servletPath.startsWith("/" + resource)){
					flag = true;
					break;
				}
			}
			if(!flag){
				for(String resource : DYNAMIC_RESOURCES){
					if(resource.endsWith("*")){
						String substring = resource.substring(0, resource.indexOf("*"));
						if(servletPath.startsWith(substring)){
							flag = true;
							break;
						}
					} else if(servletPath.equalsIgnoreCase(resource)){
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig fcg) throws ServletException {

	}

	public boolean isNeedRewriteUrl(String url){
		return url.contains("/fs/app/download");
	}
	
	public boolean isNotValidOfPreFilter(HttpServletRequest request){
		String servletPath = request.getServletPath();
		int filterIdx = TCUtil.iv(request.getAttribute("filterIdx"));
		if(filterIdx == 1){
			return !servletPath.contains("/fs/app/download/1/") && !servletPath.contains("/fs/appbk/1/");
		}
		return false;
	}
}
