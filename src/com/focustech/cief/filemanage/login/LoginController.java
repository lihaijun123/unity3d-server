package com.focustech.cief.filemanage.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.web.controller.BaseController;
import com.focustech.common.utils.HttpUtil;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/login")
public class LoginController extends BaseController{

	@Value("${login.users}")
	private String loginUsers;


	@ModelAttribute
	public void setMessage(HttpServletRequest req, ModelMap modelMap){
		modelMap.put("message", HttpUtil.decodeUrl(HttpUtil.sv(req, "message")));
	}

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap){
		return "/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String doLogin(LoginInfo loginInfo, ModelMap modelMap, HttpServletRequest req, HttpServletResponse response){
		String view = redirect("/fs/login");
		if(loginInfo != null && loginInfo.check(loginUsers)){
			req.getSession().setAttribute(LoginFilter.SESSION_KEY, loginInfo);
			RequestThreadLocal.setLoginInfo(loginInfo);
			view = redirect("/fs/user/list");
		} else {
			view += "?message=" + HttpUtil.encodeUrl("用户名或者密码错误");
		}
		return view;
	}
	/**
	 *
	 * *
	 * @param modelMap
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String doLogout(ModelMap modelMap, HttpServletRequest req){
		req.getSession().removeAttribute(LoginFilter.SESSION_KEY);
		return redirect("/login");
	}

	public String getLoginUsers() {
		return loginUsers;
	}

	public void setLoginUsers(String loginUsers) {
		this.loginUsers = loginUsers;
	}
}
