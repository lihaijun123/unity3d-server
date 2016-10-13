package com.focustech.cief.filemanage.dataserver.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.common.utils.DownloadUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.SuffixContentTypeConfig;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.rpc.DataServerHessianService;

/**
 * 文件服务器选择
 *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/i1/")
public class RootServerController {
	private final Logger log = LoggerFactory.getLogger(RootServerController.class);
	@Autowired
	private DataServerHessianService dataServerHessianService;

	private ExecutorService pool = Executors.newFixedThreadPool(4);
	/**
	 * 获取文件
	 * *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "{cfsFileName}", method = RequestMethod.GET)
	public void fetchFile(@PathVariable String cfsFileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String servletPath = request.getRequestURI();
			String cfsFileNames = servletPath.substring(servletPath.lastIndexOf("/") + 1);
			if(!StringUtils.isEmpty(cfsFileNames)){
				cfsFileNames = HttpUtil.decodeUrl(cfsFileNames);
				if(cfsFileNames.contains(".")){
					String[] cfsFileNameInfo = cfsFileNames.split("\\.");
					cfsFileName = cfsFileNameInfo[0];
					if(!StringUtils.isEmpty(cfsFileName)){
						String suffix = "." + cfsFileNameInfo[1];
						String fileName = cfsFileName + suffix;
						//获取文件数据
						FutureTask<byte[]> task = new FutureTask<byte[]>(new FileDataGetTask(fileName));
						pool.submit(task);
						byte[] btyAry = task.get();
						int length = btyAry.length;
						int begin = 0;
						int end = length - 1;
						log.debug("length:" + length);
						String headeRange = request.getHeader("Range");
						if(headeRange != null){
							Range range = new Range(headeRange, length);
							log.debug("Range:" + headeRange);
							try {
								begin = range.getBegin();
								end = range.getEnd();
								length = range.getLength();
								log.debug("begin:" + begin);
								log.debug("end:" + end);
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
						response.setContentType(SuffixContentTypeConfig.suffixContentTypeMap.get(suffix));
						doDownloadFile(response, btyAry, begin, end);
					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * *
	 * @author lihaijun
	 *
	 */
	class FileDataGetTask implements Callable<byte[]> {

		private String fileName;

		public FileDataGetTask(String fileName){
			this.fileName = fileName;
		}

		@Override
		public byte[] call() throws Exception {
			byte[] fetchFile = dataServerHessianService.fetchFile(fileName);
			return fetchFile;
		}
	}
	/**
	 * 获取文件数据
	 * *
	 * @param fileName
	 * @return
	 */
	private byte[] getFileData(String cfsFileNames) {
		return dataServerHessianService.fetchFile(cfsFileNames);
	}

	/**
	 *
	 * *
	 * @param response
	 * @param file
	 * @param begin
	 * @param end
	 * @throws IOException
	 */
	private void doDownloadFile(HttpServletResponse response, byte[] file, int begin, int end) throws IOException {
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
	 * 下载文件
	 * *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String servletPath = request.getParameter("cfsFileUrl");
		String cfsFileNames = servletPath.substring(servletPath.lastIndexOf("/") + 1);
		if(!StringUtils.isEmpty(cfsFileNames)){
			cfsFileNames = HttpUtil.decodeUrl(cfsFileNames);
			if(cfsFileNames.contains(".")){
				String[] cfsFileNameInfo = cfsFileNames.split("\\.");
				String cfsFileName = cfsFileNameInfo[0];
				if(!StringUtils.isEmpty(cfsFileName)){
					String suffix = "." + cfsFileNameInfo[1];
					String rawFileName = request.getParameter("cfsFileName");
					if(!StringUtils.isEmpty(rawFileName)){
						DownloadUtil.setDownloadHeader(HttpUtil.decodeUrl(rawFileName), request, response);
						ServletOutputStream outputStream = response.getOutputStream();

					}
				}
			}
		}
	}

    /**
     * 获取url参数
     * *
     * @param request
     * @return
     */
	private JSONObject getUrlParam(HttpServletRequest request) {
		String urlParam = decodeUrlParameter(request);
		JSONObject jsonParam = new JSONObject();
		if(!StringUtils.isEmpty(urlParam)){
			jsonParam = JSONObject.fromObject(urlParam);
		} else {
			jsonParam.put(1, 1);
		}
		return jsonParam;
	}


    /**
     * ajax输出
     *
     * @param response HttpServletResponse
     * @param outputString 输出字符
     * @throws IOException 输出流异常
     */
    protected void ajaxOutput(HttpServletResponse response, String outputString){
    	response.setCharacterEncoding("utf-8");
    	try {
			response.getWriter().print(outputString);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
	 * 解码url参数
	 * *
	 * @param request
	 * @return
	 */
	private String decodeUrlParameter(HttpServletRequest request) {
		//请求参数
    	String urlParam = "";
		try {
			String parameter = request.getParameter(FileParam.URL_PARAM);
			if(!StringUtils.isEmpty(parameter)){
				urlParam = URLDecoder.decode(parameter, "utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return urlParam;
	}
}

class Range {
	private Integer begin;
	private Integer end;
	private Integer length;
	private Integer totalLength;
	public Range(String range, Integer fileSize){
		this.totalLength = fileSize;
		range = range.replaceAll("bytes=", "");
		String[] split = range.split("-");
		if(split.length == 1){
			String str1 = split[0].trim();
			if(StringUtils.isNotEmpty(str1)){
				begin = TCUtil.iv(str1);
			}
			end = totalLength - 1;
		}
		if(split.length == 2){
			String str1 = split[0].trim();
			if(StringUtils.isNotEmpty(str1)){
				begin = TCUtil.iv(str1);
			}
			String str2 = split[1].trim();
			if(StringUtils.isNotEmpty(str2)){
				end = TCUtil.iv(str2);
			}
		}
		if(begin != null && end == null){
			//bytes 0-
			length = (fileSize - begin);
		}
		if(begin != null && end != null){
			// bytes 0-1
			length = (end - begin + 1);
		}
	}

	@Override
	public String toString() {
		return "bytes " + getBegin() + "-" + getEnd() + "/" + getTotalLength() + ";" + length;
	}

	public String toRange(){
		return "bytes " + getBegin() + "-" + getEnd() + "/" + getTotalLength();
	}

	public Integer getTotalLength() {
		return totalLength;
	}

	public void setTotalLength(Integer totalLength) {
		this.totalLength = totalLength;
	}

	public Integer getBegin() {
		return begin;
	}
	public void setBegin(Integer begin) {
		this.begin = begin;
	}
	public Integer getEnd() {
		return end;
	}
	public void setEnd(Integer end) {
		this.end = end;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
}

