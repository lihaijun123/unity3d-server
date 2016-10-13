package com.focustech.cief.filemanage.chart.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoService;
import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.TCUtil;

/***
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/chart/appdownload")
public class AppDownloadChartController extends AbstractController{
	@Autowired
	private AppInfoService<AppInfo> appInfoService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) throws IOException {

		return "/chart/app_download";
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
		List<AppInfo> list = appInfoService.list(null);
		if(ListUtils.isNotEmpty(list)){
			if(list.size() > 0){
				for(AppInfo appInfo : list){
					List<AppInfoDetail> detail = appInfo.getDetail();
					int num = 0;
					for (AppInfoDetail appInfoDetail : detail) {
						Integer downloadNum = appInfoDetail.getDownloadNum();
						if(downloadNum != null){
							num += downloadNum;
						}
					}
					String app_name = appInfo.getName();
					JSONObject jo = new JSONObject();
					jo.put("invoke_count", TCUtil.iv(num));
					jo.put("pic_name", app_name);
					jary.add(jo);
				}
			}
		}
		ajaxOutput(response, jary.toString());
	}
}
