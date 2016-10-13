package com.focustech.cief.filemanage.app.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.app.conf.AppWorkspaceConst;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 * app下载统一入口
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/app/download")
public class AppInfoDownloadGatewayController extends AbstractAppController{
	private static final Logger log = LoggerFactory.getLogger(AppInfoDownloadGatewayController.class);
	@Autowired
	private AppInfoService<AppInfo> appInfoService;

	/**
	 *
	 * *
	 * @param encryptAppSn
	 * @param modelMap
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{encryptAppSn}", method = RequestMethod.GET)
	public String viewPlistFile(@PathVariable String encryptAppSn, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		log.info(encryptAppSn);
		boolean weixinBrowser = isWeixinBrowser(request);
		int mobileSystemType = getMobileSystemType(request);
		log.info("mobileSystemType:" + mobileSystemType);
		String view = "";
		if(weixinBrowser){
			//微信浏览器
			if(AppWorkspaceConst.SYSTEM_TYPE_1 == mobileSystemType){
				//android
				view = "/app/download/wx_android";
			} else if(AppWorkspaceConst.SYSTEM_TYPE_2 == mobileSystemType){
				view = "/app/download/wx_ios";
			} else {
				//pc
				view = "/app/download/wx_android";
			}
		} else {
			//手机浏览器
			if(AppWorkspaceConst.SYSTEM_TYPE_1 == mobileSystemType){
				//android
				view = "/app/download/android";
			} else if(AppWorkspaceConst.SYSTEM_TYPE_2 == mobileSystemType){
				//ios
				view = "/app/download/ios";
			} else {
				//pc
				view = "/app/download/ios";
				mobileSystemType = AppWorkspaceConst.SYSTEM_TYPE_1;
			}
			log.info("mobileSystemType:" + mobileSystemType);
			String appUrl = "";
			if(StringUtils.isNotEmpty(encryptAppSn)){
				try {
					Long appSn = EncryptUtil.decode(encryptAppSn);
					AppInfo appInfo = appInfoService.select(appSn);
					List<AppInfoDetail> detail = appInfo.getDetail();
					String appId = "";
					boolean isThirdUrl = false;
					String thirdUrl = "";
					for (AppInfoDetail appInfoDetail : detail) {
						if(appInfoDetail.getSystemType() == mobileSystemType){
							appId = appInfoDetail.getAppId();
							isThirdUrl = (appInfoDetail.getDownloadType() == 2);
							thirdUrl = appInfoDetail.getThirdDownloadUrl();
							break;
						}
					}
					if(!isThirdUrl){
						if(AppWorkspaceConst.SYSTEM_TYPE_2 == mobileSystemType ){
							//appUrl = httpsServerUrl + "/fs/appbk/fbk" + appId + ".plist";
							appUrl = "itms-services://?action=download-manifest&url=" + httpsServerUrl + "/fs/appbk/fbk" + appId + ".plist";
						} else {
							appUrl = httpServerUrl + "/fs/appbk/fbk" + appId;
						}
					} else {
						if(!TCUtil.isEmpty(thirdUrl) && (!thirdUrl.startsWith("http://") && !thirdUrl.startsWith("https://"))){
							thirdUrl = "http://" + thirdUrl;
						}
						appUrl = thirdUrl;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			log.info("appdownload url:" + appUrl);
			modelMap.put("appUrl", appUrl);
		}
		return view;
	}
}
