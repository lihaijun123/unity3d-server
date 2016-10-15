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
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class WxLoginFilter implements Filter {
	public static final String SESSION_KEY = "wxloginInfo";
	public static final String LOGIN_PAGE_NAME = "fs/wxuser/login";
	public static final String[] STATIC_RESOURCES = {"js", "images", "css", "fonts", "monitor.html", "index.html", "html", "video", "static"};
	public static final String[] DYNAMIC_RESOURCES = {"/fs/i1/*", "/fs/rm/*", "/fs/login", "/index", "/fs/wxuser/register"};
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		Object sessinObj = session.getAttribute(SESSION_KEY);
		String servletPath = request.getServletPath();
		boolean isPass = false;
		boolean urlContainSessionId = false;
		if(isIncludePassPath(servletPath)){
			isPass = true;
		} else {
			String mobile = request.getParameter("mobile");
			if(StringUtils.isEmpty(mobile)){
				mobile = TCUtil.sv(request.getAttribute("mobile"));
			} else {
				urlContainSessionId = true;
			}
			if(sessinObj == null) {
				if(StringUtils.isEmpty(mobile)){
					mobile = TCUtil.sv(request.getSession().getAttribute("mobile"));
				}
				if(StringUtils.isEmpty(mobile)){
					Cookie[] cookies = request.getCookies();
					if(cookies != null){
						for (Cookie cookie : cookies) {
							String name = cookie.getName();
							if("mobile".equals(name)){
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
					request.setAttribute("mobile", mobile);
					request.getSession().setAttribute("mobile", mobile);
					String requestUrl = request.getRequestURI().toString();
					if(isNeedRewriteUrl(requestUrl) && !urlContainSessionId){
						String rewriteUrl = requestUrl + "?mobile=" + mobile;
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
					String rewriteUrl = requestUrl + "?mobile=" + wxUserInfo.getMobile();
					request.setAttribute("mobile", wxUserInfo.getMobile());
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
}
