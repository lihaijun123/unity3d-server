package com.focustech.cief.filemanage.chart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.log.model.StatAppUseTimeLog;
import com.focustech.cief.filemanage.log.service.StatAppUseTimeLogService;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/chart/usetime")
public class StatAppUseTimeChartController extends AbstractController {
	@Autowired
	private StatAppUseTimeLogService<StatAppUseTimeLog> statAppUseTimeLogService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<StatAppUseTimeLog> list = statAppUseTimeLogService.list();
		modelMap.put("list", list);
		return "/chart/stat_usetime";
	}
	/**
	 *
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/table", method = RequestMethod.GET)
	public void table(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

	}

}
