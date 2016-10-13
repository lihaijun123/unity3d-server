package com.focustech.cief.filemanage.app.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.app.conf.AppWorkspaceConst;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoDetailService;
import com.focustech.cief.filemanage.app.service.AppInfoService;
import com.focustech.cief.filemanage.app.service.impl.AppCache;
import com.focustech.cief.filemanage.dataserver.common.utils.DownloadUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 * app下载
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping(value = "/fs/appbk")
public class AppInfoRpcController  extends AbstractAppController {

	private static final String APP_PREFIX = "fbk";
	@Autowired
	private AppInfoService<AppInfo> appBackstageService;
	@Autowired
	private AppInfoDetailService<AppInfoDetail> appBackstageDetailService;
	@Autowired
	private AppCache appCache;

	@Autowired
	private IBaseFileService<AbstractFile> baseFileService;
	/**
	 * 远程ios下载
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{fileName}.plist", method = RequestMethod.GET)
	public void viewPlistFile(@PathVariable String fileName, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response){
		if(!StringUtils.isEmpty(fileName) && fileName.startsWith(APP_PREFIX)){
			if(!fileName.endsWith(".plist")){
				fileName += ".plist";
			}
			String appId = fileName.replace(APP_PREFIX, "").replace(".plist", "").trim();
			AppInfoDetail appDetail = appBackstageDetailService.select(appId);
			if(appDetail != null){
				String plistTemplate = request.getSession().getServletContext().getRealPath("./") + "/WEB-INF/config/plist/template.plist";
				File file = new File(plistTemplate);
				if(file.exists()){
					StringBuffer sb = new StringBuffer();;
					try {
						BufferedReader bufferedInStream  = new BufferedReader(new FileReader(file));
						try {
							while(true){
								String line = bufferedInStream.readLine();
								if(StringUtils.isEmpty(line)){
									break;
								}
								sb.append(line);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if(bufferedInStream != null){
								bufferedInStream.close();
							}
						}
						response.setContentType("text/xml");
						response.setCharacterEncoding(ENCODE_UTF8);
						String plistContent = sb.toString().replace("APP_ID", APP_PREFIX + appDetail.getAppId())
						.replace("APP_NAME", appDetail.getName()).replace("PK_CODE", appDetail.getCodeId())
						.replace("APP_VERSION", StringUtils.isEmpty(appDetail.getVersionNum()) ? "1.0" : appDetail.getVersionNum())
						.replace("HTTPS_SERVER_URL", httpsServerUrl);
						//替换
						response.getWriter().write(plistContent);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}
	}
	/**
	 * 远程下载
	 * *
	 * @param modelMap
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/{fileName}", method = RequestMethod.GET)
	public void downloadFile(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
		try {
			//非移动端禁止下载
			/*if(getMobileType(request) <= 0){
				response.getOutputStream().write("Resources are not there-1".getBytes());
				return;
			}*/
			log.info(fileName);
			if(!StringUtils.isEmpty(fileName) && fileName.startsWith(APP_PREFIX)){
				AppInfoDetail appDetail = null;
				String appId = "";
				if(fileName.indexOf(".") == -1){
					appId = fileName.replace(APP_PREFIX, "").trim();
				} else {
					appId = fileName.substring(0, fileName.indexOf(".") - 1).replace(APP_PREFIX, "").trim();
				}
				appDetail = appCache.get(appId, AppInfoDetail.class);
				if(appDetail != null){
					Long fileSn = appDetail.getAppFileSn();
					if(fileSn != null){
						String suffix = "";
						if(AppWorkspaceConst.SYSTEM_TYPE_1 == appDetail.getSystemType()){
							suffix = ".apk";
						} else {
							suffix = ".ipa";
						}
						//同一个ip下载次数限制
						Map<String, String> fileLocalPathMap = baseFileService.getFileLocalPath(TCUtil.sv(fileSn));
						if(!fileLocalPathMap.isEmpty()){
							fileName = APP_PREFIX + appId + suffix;
							String localPath = fileLocalPathMap.get(FileParam.DOWNLOAD_FILE_PATH);

							//更新下载次数
							appDetail.setDownloadNum(TCUtil.iv(appDetail.getDownloadNum()) + 1);
							appBackstageDetailService.updateOrInsert(appDetail);
							log.info("################################:" + fileName);
							log.info("################################:" + localPath);
							DownloadUtil.download(localPath, fileName, request, response);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
