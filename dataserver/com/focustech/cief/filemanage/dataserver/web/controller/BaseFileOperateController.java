package com.focustech.cief.filemanage.dataserver.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.focustech.cief.filemanage.dataserver.HttpVisitUrl;
import com.focustech.cief.filemanage.dataserver.common.utils.EncryptUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.StringUtil;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.cief.filemanage.dataserver.service.IFileServerService;
import com.focustech.common.utils.StringUtils;

/**
 * 文件操作基础类
 * *
 * @author lihaijun
 *
 */
public abstract class BaseFileOperateController {
	/**文件上传返回-文件编号 */
	public static final String FILE_ID = "fileId";
	/**文件上传返回-文件访问路径 */
	public static final String FILE_URL = "fileUrl";
	/**文件上传返回值*/
	public static final String RETURN_JO = "jsonValue";
	/**GBK编码*/
	protected static final String ENCODE_GBK = "GBK";
	/**UTF-8编码*/
	protected static final String ENCODE_UTF8 = "utf-8";
	protected final static Log log = LogFactory.getLog(BaseFileOperateController.class.getName());
	@Autowired
	protected IBaseFileService<AbstractFile> baseFileService;
	@Autowired
	protected IFileServerService<FileServer> fileServerService;
	@Autowired
	protected HttpVisitUrl httpVisitUrl;
	/**
	 * 构建ajax回调函数，解决跨域的问题
	 * *
	 * @param request
	 * @param parameter
	 * @return
	 */
	protected String buildAjaxCallbackFunction(HttpServletRequest request, String parameter){
		String jsoncallback = request.getParameter("jsoncallback");
		if(null == jsoncallback){
			return parameter;
		}
		log.debug("Ajax回调函数参数" + parameter);
		return jsoncallback + "(" + parameter + ")";
	}

	/**
	 * 更新服务器的访问量
	 * *
	 * @param fileServer
	 */
	protected void updateVisitFrequency(FileServer fileServer) {
		fileServer.setVisitFrequency(fileServer.getVisitFrequency() + 1);
		fileServerService.save(fileServer);
	}

	/**
	 * 更新文件的访问量
	 * *
	 * @param subFoldName
	 * @param fileName
	 */
	protected void updateVisitFrequency(String subFoldName, String fileName) {
		AbstractFile abstractFile = baseFileService.getFile(subFoldName, fileName);
    	Long visitFrequency = abstractFile.getVisitFrequency();
		abstractFile.setVisitFrequency(null == visitFrequency ? 1 : visitFrequency + 1);
    	baseFileService.save(abstractFile);
	}

	/**
     * ajax输出
     *
     * @param response HttpServletResponse
     * @param outputString 输出字符
     * @throws IOException 输出流异常
     */
    protected void ajaxOutput(HttpServletResponse response, String outputString){
    	response.setCharacterEncoding(ENCODE_UTF8);
    	try {
			response.getWriter().print(decodeVisitUrl(outputString));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    private String decodeVisitUrl(String jsonStr){
    	String rv = "";
    	if (!StringUtils.isEmpty(jsonStr)) {
    		if(jsonStr.startsWith("[")){
    			JSONArray jary = JSONArray.fromObject(jsonStr);
    			for (Object object : jary) {
    				JSONObject fileInfoReturnJo = (JSONObject)object;
    				decodeVisitUrl(fileInfoReturnJo);
				}
    			rv = jary.toString();
    		}
    		if(jsonStr.startsWith("{")){
    			JSONObject fileInfoReturnJo = JSONObject.fromObject(jsonStr);
				decodeVisitUrl(fileInfoReturnJo);
				rv = fileInfoReturnJo.toString();
    		}
    	}
    	return rv;
    }

	private void decodeVisitUrl(JSONObject fileInfoReturnJo) {
		Set<String> keySet = fileInfoReturnJo.keySet();
		for (String key : keySet) {
			if(key.contains("Url") || key.contains("url")){
				String value = fileInfoReturnJo.getString(key);
				fileInfoReturnJo.put(key, httpVisitUrl.getHttpPrefix() + value);
			}
		}
	}
    /**
     * 从请求中获取下载文件的sn
     * *
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
	protected String getDownloadFileSn(HttpServletRequest request) throws UnsupportedEncodingException {
		//支持通过sn和访问路径下载文件
    	String fileSnRp = request.getParameter("fileSn");
    	String fileIdRp = request.getParameter("fileId");
    	String visitAddrRp = request.getParameter("visitAddr");
    	String fileSn = "";
    	//通过文件id直接下载
    	if(!StringUtil.isEmpty(fileSnRp)){
    		try {
    			fileSn = EncryptUtil.decode2Str(fileSnRp);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    	if(!StringUtil.isEmpty(fileIdRp)){
    		if(!StringUtil.isEmpty(fileIdRp)){
        		try {
        			fileSn = EncryptUtil.decode2Str(fileIdRp);
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
        	}
    	}
    	if(!StringUtil.isEmpty(visitAddrRp)){
    			String visitAddr = URLDecoder.decode(visitAddrRp, ENCODE_UTF8);
    			fileSn = String.valueOf(baseFileService.getFileSnByVisitAddr(visitAddr));
    	}
		return fileSn;
	}
}
