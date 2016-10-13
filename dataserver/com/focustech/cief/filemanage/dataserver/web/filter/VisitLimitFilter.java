package com.focustech.cief.filemanage.dataserver.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.focustech.cief.filemanage.dataserver.common.utils.ValueFormatUtil;
/**
 * 访问控制过滤器
 * *
 * @author lihaijun
 *
 */
public class VisitLimitFilter implements Filter {
	private static final String VISIT_LIMIT_IMAGE_PATH = "/ban_visit.bmp";
	private String rootFoldName;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		String url = req.getServletPath();
		boolean isLimitVisitUrl = false;
		int rootVisitFoldName = url.indexOf(rootFoldName);
		if(rootVisitFoldName == 1){
			//如果以fileUpload开头的链接表示是访问服务器资源
			isLimitVisitUrl = isLimitUrl(url);
		}
		if(isLimitVisitUrl){
			//返回默认资源
			resp.sendRedirect(VISIT_LIMIT_IMAGE_PATH);
			return;
		}
		fc.doFilter(req, resp);
		return;
	}

	/**
	 * 是否是非法访问资源url
	 * *
	 * @param url
	 * @return
	 */
	private boolean isLimitUrl(String url){
		String limitUrlExample_1 = "/" + rootFoldName;
		String limitUrlExample_2 = "/" + rootFoldName + "/";
		if(limitUrlExample_1.equals(url) || limitUrlExample_2.equals(url)){
			return true;
		}
		String[] urlAry = url.split("/");
		if(urlAry.length == 3 && urlAry[2].length() == 8){
			return true;
		}
		if(urlAry.length == 4 && urlAry[3].length() == 0){
			return true;
		}
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String rootFoldName = config.getInitParameter("rootFoldName");
		this.rootFoldName = ValueFormatUtil.sv(rootFoldName);
	}

	@Override
	public void destroy() {

	}
}
