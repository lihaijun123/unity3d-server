package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromWhere;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.TCUtil;
/**
 * 文件下载
 * *
 * @author lihaijun
 *
 */
public class DownloadUtil {
	/**
	 * 文件下载
	 * *
	 * @param filePath
	 * @param in
	 * @param response
	 */
	public static void download(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out = null;
        BufferedInputStream bufferedInStream = null;
        try {
        	if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
        		//IE
        		fileName = URLEncoder.encode(fileName, "UTF-8");
        	}else if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
        		//firefox
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        	}else{
        		// other
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        	}
        	response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName.replace("+", "%20") + "\"");
        	File file = new File(filePath);
        	FileInputStream fileInputStream = new FileInputStream(file);
        	bufferedInStream = new BufferedInputStream(fileInputStream);
        	if (null == filePath || null == bufferedInStream || null == response) {
        		return;
        	}
        	response.setHeader("Content-Length", TCUtil.sv(bufferedInStream.available()));
            out = response.getOutputStream();
            int len = 0;
            byte buf[] = new byte[1024];
            while ((len = bufferedInStream.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            try {
                if (null != bufferedInStream) {
                	bufferedInStream.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            }
            catch (IOException e1) {
            	e1.printStackTrace();
            }
        }
    }
	public static void download(String fileName, byte[] data, HttpServletRequest request, HttpServletResponse response) {
		String contentType = "";
		if(fileName.endsWith(".apk")){
			contentType = "application/vnd.android.package-archive";
		} else if(fileName.endsWith(".ipa")){
			contentType = "application/vnd.iphone";
		} else {
			contentType = "application/octet-stream;charset=UTF-8";
		}
		response.setContentType(contentType);
		ServletOutputStream out = null;
		BufferedInputStream bufferedInStream = null;
		try {
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
				//IE
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}else if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				//firefox
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}else{
				// other
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			response.setHeader("Content-Length", TCUtil.sv(data.length));
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName.replace("+", "%20") + "\"");
			out = response.getOutputStream();
			out.write(data);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != bufferedInStream) {
					bufferedInStream.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 文件下载
	 * *
	 * @param filePath
	 * @param in
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public static void setDownloadHeader(String fileName, HttpServletRequest request, HttpServletResponse response) {
        try {
			response.setContentType("application/octet-stream;charset=UTF-8");
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
				//IE
				fileName = HttpUtil.encodeUrl(fileName);
			}else if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
				//firefox
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}else{
				// other
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
			}
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName.replace("+", "%20") + "\"");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

	/**
	 * 文件下载
	 * *
	 * @param filePath
	 * @param in
	 * @param response
	 */
	public static void download(String filePath, String fileName, String visitAddr, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out = null;
        BufferedInputStream bufferedInStream = null;
        try {
        	if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
        		//IE
        		fileName = URLEncoder.encode(fileName, "UTF-8");
        	}else if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
        		//firefox
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        	}else{
        		// other
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
        	}
        	response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName.replace("+", "%20") + "\"");
        	InputStream inputStream = null;
        	ReadFileFromWhere readFileFromWhere = null;
        	String fsSystem = MessageUtils.getInfoValue("FS_SYSTEM");
        	if("FFS".equals(fsSystem)){
        		readFileFromWhere = ReadFileFromCompanyFS.newInstance();
        		inputStream = readFileFromWhere.read(visitAddr);
    		} else {
    			readFileFromWhere = new ReadFileFromCFS();
        		inputStream = readFileFromWhere.read(filePath);
    		}
            out = response.getOutputStream();
            int len = 0;
            byte buf[] = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                out.write(buf, 0, len);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            try {
                if (null != bufferedInStream) {
                	bufferedInStream.close();
                }
                if (out != null) {
                    out.flush();
                    out.close();
                }
            }
            catch (IOException e1) {
            	e1.printStackTrace();
            }
        }
    }
}
