package com.focustech.cief.filemanage.rm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.hx.service.HxAppService;
import com.focustech.common.utils.StringUtils;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/rm/")
public class HxRmController extends AbstractController {
	@Autowired
	private HxAppService hxAppService;
	/**
	 *
	 * *
	 * @param xmlId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/hxfile/find/{xmlId}/{systemType}", method = RequestMethod.GET)
	public void findUnityFile(@PathVariable String xmlId, @PathVariable String systemType, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isNotEmpty(xmlId)){
			JSONObject findUnityFile = hxAppService.findUnityFile(xmlId, getMobileType(systemType));
			try {
				ajaxOutput(response, findUnityFile.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 *
	 * *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/xmldat/download", method = RequestMethod.GET)
	public void listDownloadFile(HttpServletRequest request, HttpServletResponse response){
		try {
			ajaxOutput(response, hxAppService.listDownloadFile().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * *
	 * @param request lihaijun99999
	 * @param response
	 */
	@RequestMapping(value = "/hxdownload/{deviceId}/{xmlId}", method = RequestMethod.GET)
	public void listDownloadFile(@PathVariable String deviceId, @PathVariable String xmlId, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isNotEmpty(deviceId) && StringUtils.isNotEmpty(xmlId)){
			JSONObject recordDownload = hxAppService.recordDownload(deviceId, xmlId);
			try {
				ajaxOutput(response, recordDownload.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	/**
	 *
	 * *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/hxlist/{deviceId}", method = RequestMethod.GET)
	public void getHxList(@PathVariable String deviceId, HttpServletRequest request, HttpServletResponse response){
		if(StringUtils.isNotEmpty(deviceId)){
			JSONObject recordDownload = hxAppService.listTree(deviceId, getMobileType("ios"));
			try {
				ajaxOutput(response, recordDownload.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
