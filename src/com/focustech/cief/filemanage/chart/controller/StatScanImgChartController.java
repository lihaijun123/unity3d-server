package com.focustech.cief.filemanage.chart.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.log.model.StatScanImgLog;
import com.focustech.cief.filemanage.log.service.StatScanImgLogService;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/chart/scanimg")
public class StatScanImgChartController extends AbstractController {
	@Autowired
	private StatScanImgLogService<StatScanImgLog> imgLogService;


	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

		return "/chart/stat_scan_img";
	}
	/**
	 *
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/column", method = RequestMethod.GET)
	public void columnChart(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {
		JSONArray jary = new JSONArray();
		List<Map<String, String>> hightVisitCountOfDay = imgLogService.getChartData("", "", null);
		if(ListUtils.isNotEmpty(hightVisitCountOfDay)){
			if(hightVisitCountOfDay.size() > 0){
				for(Map<String, String> map : hightVisitCountOfDay){
					String invoke_count = map.get("invoke_count");
					String add_time = map.get("pic_name");
					JSONObject jo = new JSONObject();
					jo.put("invoke_count", TCUtil.iv(invoke_count));
					jo.put("pic_name", add_time);
					jary.add(jo);
				}
			}
		}
		ajaxOutput(response, jary.toString());
	}

}
