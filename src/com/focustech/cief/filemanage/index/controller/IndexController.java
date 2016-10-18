package com.focustech.cief.filemanage.index.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.app.controller.AbstractAppController;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.service.AppInfoService;
/**
 * 
 * 
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController extends AbstractAppController{
	@Autowired
	private AppInfoService<AppInfo> appInfoService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index1(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		List<AppInfo> list = appInfoService.list(3, true);
		modelMap.addAttribute("list", list);
		return "/index";
	}
	/**
	 * 
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/0", method = RequestMethod.GET)
	public String index0(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		List<AppInfo> list = appInfoService.list(3, false);
		modelMap.addAttribute("list", list);
		return "/index";
	}
}
