package com.focustech.cief.filemanage.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class LoginFilter implements Filter {
	public static final String SESSION_KEY = "loginInfo";
	public static final String LOGIN_PAGE_NAME = "fs/login";
	public static final String[] STATIC_RESOURCES = {"js", "images", "css", "fonts", "monitor.html", "index.html", "html", "video", "static"};
	public static final String[] DYNAMIC_RESOURCES = {"/fs/i1/*", "/fs/rm/*", "/fs/login", "/fs/appbk/*", "/fs/app/download/*", "/index"};
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpSession session = request.getSession();
		Object sessinObj = session.getAttribute(SESSION_KEY);
		String servletPath = request.getServletPath();
		boolean isPass = false;
		if(isIncludePassPath(servletPath)){
			isPass = true;
		} else {
			if(sessinObj == null) {
				HttpServletResponse response = (HttpServletResponse)resp;
				response.sendRedirect("/" + LOGIN_PAGE_NAME);
			} else {
				RequestThreadLocal.setLoginInfo(sessinObj);
				isPass = true;
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

}
