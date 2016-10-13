package com.focustech.cief.filemanage.rm.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.hx.controller.AbstractController;
import com.focustech.cief.filemanage.log.model.StatScanImgLog;
import com.focustech.cief.filemanage.log.service.StatScanImgLogService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.IPTool;
import com.focustech.common.utils.StringUtils;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/rm/scanimg")
public class StatScanImgLogRmController extends AbstractController{
	@Autowired
	private StatScanImgLogService<StatScanImgLog> imgLogService;
	/**
	 * http://192.168.1.105/fs/rm/scanimg?userId=NcAUyopKeoVN&appName=kuk&picName=dsfsdf
	 * *
	 * @param mobile
	 * @param email
	 * @param appName
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void record(String userId, String appName, String picName, HttpServletRequest request, HttpServletResponse response) throws IOException{
		if(StringUtils.isNotEmpty(userId) && StringUtils.isNotEmpty(appName) && StringUtils.isNotEmpty(picName)){
			try {
				StatScanImgLog scanImgLog = new StatScanImgLog();
				scanImgLog.setUserId(EncryptUtil.decode(userId));
				scanImgLog.setAppName(appName);
				scanImgLog.setPicName(picName);
				scanImgLog.setAddTime(new Date());
				scanImgLog.setIpAddr(IPTool.getRealIp(request));
				imgLogService.insertOrUpdate(scanImgLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
