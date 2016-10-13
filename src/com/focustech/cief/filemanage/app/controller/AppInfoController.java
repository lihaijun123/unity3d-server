package com.focustech.cief.filemanage.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.app.conf.AppWorkspaceConst;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoService;
import com.focustech.cief.filemanage.dataserver.core.conf.SuffixContentTypeConfig;
import com.focustech.common.qrcodes.QrGeneratorUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 * *
 *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/appbkmanage/")
public class AppInfoController extends AbstractAppController {
	@Autowired
	private AppInfoService<AppInfo> appBackstageService;
	/**
	 * 新建 *
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String _new(ModelMap modelMap) {
		AppInfo app = new AppInfo();
		// android-手机
		AppInfoDetail detail_1 = new AppInfoDetail();
		detail_1.setSystemType(AppWorkspaceConst.SYSTEM_TYPE_1);
		detail_1.setDeviceType(AppWorkspaceConst.DEVICE_TYPE_1);
		detail_1.setTitle(getTitle(detail_1.getSystemType(),
				detail_1.getDeviceType()));
		/*
		 * //android-pad AppInfoDetail detail_2 = new AppInfoDetail();
		 * detail_2.setSystemType(AppWorkspaceConst.SYSTEM_TYPE_1);
		 * detail_2.setDeviceType(AppWorkspaceConst.DEVICE_TYPE_2);
		 * detail_2.setTitle(getTitle(detail_2.getSystemType(),
		 * detail_2.getDeviceType()));
		 */
		// ios-手机
		AppInfoDetail detail_3 = new AppInfoDetail();
		detail_3.setSystemType(AppWorkspaceConst.SYSTEM_TYPE_2);
		detail_3.setDeviceType(AppWorkspaceConst.DEVICE_TYPE_1);
		detail_3.setTitle(getTitle(detail_3.getSystemType(),
				detail_3.getDeviceType()));
		/*
		 * //ios-pad AppInfoDetail detail_4 = new AppInfoDetail();
		 * detail_4.setSystemType(AppWorkspaceConst.SYSTEM_TYPE_2);
		 * detail_4.setDeviceType(AppWorkspaceConst.DEVICE_TYPE_2);
		 * detail_4.setTitle(getTitle(detail_4.getSystemType(),
		 * detail_4.getDeviceType()));
		 */
		List<AppInfoDetail> details = new ArrayList<AppInfoDetail>();
		details.add(detail_1);
		details.add(detail_3);
		/*
		 * details.add(detail_2); details.add(detail_4);
		 */
		app.setDetail(details);
		modelMap.addAttribute("app", app);
		return "/app/new";
	}

	/**
	 * 保存 *
	 *
	 * @param bannerInfo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(AppInfo app, ModelMap modelMap) {
		String name = app.getName();
		if(StringUtils.isNotEmpty(name)){
			appBackstageService.insertOrUpdate(app);
			return "redirect:/fs/appbkmanage/edit/" + app.getSn();
		}
		modelMap.addAttribute("app", app);
		return "/app/new";
	}

	/**
	 * 修改 *
	 *
	 * @param bannerInfo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/edit/{sn}", method = RequestMethod.GET)
	public String edit(@PathVariable Long sn, ModelMap modelMap) {
		AppInfo app = appBackstageService.select(sn);
		List<AppInfoDetail> detail = app.getDetail();
		if (detail != null && !detail.isEmpty()) {
			for (AppInfoDetail appInfoDetail : detail) {
				appInfoDetail.setTitle(getTitle(
						appInfoDetail.getSystemType(),
						appInfoDetail.getDeviceType()));
				if(appInfoDetail.getSystemType() == 2){
					appInfoDetail.setPlistVisitUrl(httpServerUrl + "/fs/appbk/fbk" + appInfoDetail.getAppId() + ".plist");
				}
			}
		}
		modelMap.addAttribute("app", app);
		String content = appBackstageService.createQrCodeContent(app);
		modelMap.addAttribute("qrContent", content);
		return "/app/edit";
	}
	/**
	 * 获取二维码
	 * *
	 * @param sn
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/qrcode/{sn}", method = RequestMethod.GET)
	public void getQrcode(@PathVariable Long sn, ModelMap modelMap, HttpServletResponse response) {
		AppInfo app = appBackstageService.select(sn);
		String content = appBackstageService.createQrCodeContent(app);
		byte[] btyAry = QrGeneratorUtil.generate(content);
		try {
			if(btyAry != null && btyAry.length > 0){
				response.setHeader("Content-Length", TCUtil.sv(btyAry.length));
				response.setContentType(SuffixContentTypeConfig.suffixContentTypeMap.get(SuffixContentTypeConfig.SUFFIX_JPG));
				response.getOutputStream().write(btyAry);
			} else {
				response.getWriter().write("404");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保存修改 *
	 *
	 * @param bannerInfo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(AppInfo app, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "/app/edit";
		}
		appBackstageService.insertOrUpdate(app);
		modelMap.addAttribute("app", app);
		return "redirect:/fs/appbkmanage/edit/" + app.getSn();
	}

	/**
	 * 控制台 *
	 *
	 * @param bannerInfo
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String console(ModelMap modelMap) {
		List<AppInfo> appList = appBackstageService.list(null);
		modelMap.addAttribute("appList", appList);
		return "/app/list";
	}
}
