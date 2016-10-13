package com.focustech.cief.filemanage.app.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.focustech.cief.filemanage.app.conf.AppWorkspaceConst;
import com.focustech.cief.filemanage.dataserver.web.controller.BaseController;
import com.focustech.common.utils.TCUtil;

/**
 *
 * *
 * @author lihaijun
 *
 */
public class AbstractAppController extends BaseController {
	@Value(value = "${https.server.url}")
	protected String httpsServerUrl;
	
	@Value(value = "${http.server.url}")
	protected String httpServerUrl;

	protected Logger log = LoggerFactory.getLogger(BaseController.class.getName());

	/**
	 * 多线程下载
	 * *
	 * @param fileName
	 * @param request
	 * @param response
	 * @param data
	 * @throws IOException
	 */
	protected void mThreadDownload(String fileName, HttpServletRequest request, HttpServletResponse response, byte[] data) throws IOException {
		//多线程下载
		//获取文件数据
		int totalLength = data.length;
		int length = data.length;
		int begin = 0;
		int end = length - 1;
		String headeRange = request.getHeader("Range");
		log.debug("Range:" + headeRange);
		if(headeRange != null){
			Range range = new Range(headeRange, length);
			try {
				begin = range.getBegin();
				end = range.getEnd();
				length = range.getLength();
				log.debug("begin:" + begin);
				log.debug("end:" + end);
				log.debug("length:" + length);
			} catch (NumberFormatException e) {
				log.error(headeRange + " is not Number");
			}
			String contentRange = range.toRange();
			log.debug("Content-Range:" + contentRange);
			response.setHeader("Content-Range", contentRange);
			response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
		}
		response.setHeader("Accept-Ranges", "bytes");
		response.setHeader("Content-Length", TCUtil.sv(length));
		String contentType = "";
		if(fileName.endsWith(".apk")){
			contentType = "application/vnd.android.package-archive";
		} else if(fileName.endsWith(".ipa")){
			contentType = "application/vnd.iphone";
		} else {
			contentType = "application/octet-stream;charset=UTF-8";
		}
		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName.replace("+", "%20") + "\"");
		log.debug("contentType:" + contentType);
		doDownloadFile(response, data, begin, end);
	}


	/**
	 * 多线程下载
	 * *
	 * @param response
	 * @param file
	 * @param begin
	 * @param end
	 * @throws IOException
	 */
	protected void doDownloadFile(HttpServletResponse response, byte[] file, int begin, int end) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			byte[] b = new byte[1024];
			is = new BufferedInputStream(new ByteArrayInputStream(file));
			os = new BufferedOutputStream(response.getOutputStream());
			// 跳过已下载的文件内容
			is.skip(begin);
			// I/O 读写
			for (int i, left = end - begin + 1; left > 0 && ((i = is.read(b, 0, Math.min(b.length, left))) != -1); left -= i){
				os.write(b, 0, i);
			}
			os.flush();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {

				}
			}
		}
	}

	/**
	 * 标题
	 * *
	 * @param systemType
	 * @param deviceType
	 * @return
	 */
	protected String getTitle(int systemType, int deviceType){
		if(systemType == AppWorkspaceConst.SYSTEM_TYPE_1 && deviceType == AppWorkspaceConst.DEVICE_TYPE_1){
			return "android-手机";
		}
		if(systemType == AppWorkspaceConst.SYSTEM_TYPE_1 && deviceType == AppWorkspaceConst.DEVICE_TYPE_2){
			return "android-pad";
		}
		if(systemType == AppWorkspaceConst.SYSTEM_TYPE_2 && deviceType == AppWorkspaceConst.DEVICE_TYPE_1){
			return "ios-手机";
		}
		if(systemType == AppWorkspaceConst.SYSTEM_TYPE_2 && deviceType == AppWorkspaceConst.DEVICE_TYPE_2){
			return "ios-pad";
		}
	    return "";
	}
	/**
	 * 获取移动设备类型
	 * *
	 * @param ua 需要转换成小写
	 * @return
	 */
	public int getMobileType(HttpServletRequest req){
		String ua = req.getHeader("User-Agent").toLowerCase();
		if(ua.contains("iphone") || ua.contains("ipad")){
			return AppWorkspaceConst.SYSTEM_TYPE_2;
		}
		if(ua.contains("android")){
			return AppWorkspaceConst.SYSTEM_TYPE_1;
		}
		return 0;

	}


	public boolean isWeixinBrowser(HttpServletRequest req){
		String ua = TCUtil.sv(req.getHeader("User-Agent")).toLowerCase();
		return ua.contains("micromessenger");
	}

	 public int getMobileSystemType(HttpServletRequest req){
        String ua = TCUtil.sv(req.getHeader("User-Agent")).toLowerCase();
        if(ua.contains("iphone") || ua.contains("ipad"))
            return 2;
        return !ua.contains("android") ? 0 : 1;
	}
}
