package com.focustech.cief.filemanage.dataserver.web.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.common.utils.DownloadUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileType;
import com.focustech.cief.filemanage.dataserver.core.processor.after.AbstractFileAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.processor.after.AttachAfterSaveProcessor;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromCompanyFS;
import com.focustech.cief.filemanage.dataserver.core.read.ReadFileFromWhere;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.HttpUtil;
import com.focustech.common.utils.MessageUtils;
import com.focustech.common.utils.StringUtils;
import com.focustech.core.Assert;
/**
 *	文件服务器管理文件，上传，下载，删除，修改
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/dataserver")
public class FileManagerController extends AbstractFileOperateController{
	/**
	 * 文件上传
	 * *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//文件编号，访问路径
    	String fileInfoJsonStr = upload(request);
    	log.debug("上传成功:" + fileInfoJsonStr);
    	ajaxOutput(response, fileInfoJsonStr);
    }

    /**
	 * 文件上传到指定文件的目录下
	 * *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
    @RequestMapping(params = "method=uploadToTargetFilePath", method = RequestMethod.POST)
    public void uploadToTargetFilePath(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//文件编号，访问路径
    	String fileInfoJsonStr = uploadToTargetFilePath(request);
    	log.debug("上传成功:" + fileInfoJsonStr);
    	ajaxOutput(response, fileInfoJsonStr);
    }
    /**
     * 文件下载
     * *
     * @param request
     * @param response
     * @throws IOException
     */
    @Deprecated
    @RequestMapping(params = "method=download", method = RequestMethod.GET)
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	String fileSn = getDownloadFileSn(request);
    	Assert.notEmpty(fileSn);
    	//文件所在服务器的物理路径
    	Map<String, String> downloadInfoMap = baseFileService.getFileLocalPath(fileSn);
    	//updateVisitFrequency(subFoldName, fileName);
    	//下载
    	DownloadUtil.download(downloadInfoMap.get(FileParam.DOWNLOAD_FILE_PATH), downloadInfoMap.get(FileParam.DOWNLOAD_FILE_NAME), downloadInfoMap.get(FileParam.DOWNLOAD_FILE_VISIT_ADDR), request, response);
    }

    /**
     * 删除文件
     * *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "method=delete")
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	//便于加密处理
    	String subFoldName = request.getParameter("subFoldName");
    	String fileName = request.getParameter("fileName");
    	//后续加密处理
    	Assert.notEmpty(subFoldName);
    	Assert.notEmpty(fileName);
    	//物理删除
    	//baseInfoService.deleteByPhysics(subFoldName, fileName);
    	//逻辑删除
    	baseFileService.deleteByLogic(subFoldName, fileName);
    	ajaxOutput(response, buildAjaxCallbackFunction(request, ""));
    }
    /**
     * 复制文件
     * *
     * @param sn
     * @param type
     * @param request
     * @param response
     * @throws IOException
     */
    @Deprecated
    @RequestMapping(params = "method=copy")
    public void copy(String sn, String type, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	AbstractFile targetFile = baseFileService.copy(sn, type);
    	JSONObject jo = new JSONObject();
    	jo.put(FileParam.FILE_ID, targetFile.getSn());
    	jo.put(FileParam.FILE_URL, targetFile.getVisitAddr());
    	ajaxOutput(response, jo.toString());
    }
    /**
     * 把目标文件的访问路径更新到原始文件的访问路径上,目标文件将删除
     * *
     * @param orinSn 源文件的Sn
     * @param targetSn 新文件的Sn
     * @param type 文件类型
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "method=updateTargetFileVisitAddrToOrinFile")
    public void updateTargetFileVisitAddrToOrinFile(String orinSn, String targetSn, String type, HttpServletRequest request, HttpServletResponse response) throws IOException{
    	AbstractFile targetFile = baseFileService.updateTargetFileVisitAddrToOrinFile(orinSn, targetSn);
    	JSONObject jo = new JSONObject();
    	jo.put(FileParam.FILE_ID, targetFile.getSn());
    	jo.put(FileParam.FILE_URL, targetFile.getVisitAddr());
    	ajaxOutput(response, jo.toString());
    }

    /**
     * 组件化
     * *
     * @param orinSn 源文件的Sn
     * @param targetSn 新文件的Sn
     * @param type 文件类型
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(params = "method=doComp")
    public void doComp(String orinSn, HttpServletResponse response){
    	JSONObject returnJo = new JSONObject();
		try {
			AbstractFile abstractFile = baseFileService.findBySn(Long.parseLong(orinSn));
			AbstractFileAfterSaveProcessor afterSaveObj = new AttachAfterSaveProcessor();
			JSONObject jo = new JSONObject();
			jo.put(AbstractFileAfterSaveProcessor.ATTACH_TYPE, FileType.MODEL);
			jo.put(AbstractFileAfterSaveProcessor.IS_COMP, Boolean.TRUE);
			Map<String, Object> urlMap = new HashMap<String, Object>();
			urlMap.put(FileParam.URL_PARAM, jo.toString());
			JSONObject rJo = new JSONObject();
			Map<String, String> returnMap = afterSaveObj.doAfterSave(abstractFile, urlMap);
			String[] returnMapKey = afterSaveObj.getReturnMapKeyAry();
			if(null != returnMap && null != returnMapKey){
				for(int i = 0, j = returnMapKey.length; i < j; i ++){
					rJo.put(returnMapKey[i], returnMap.get(returnMapKey[i]));
				}
			}
			returnJo.put(FileParam.RETURN_JO, rJo.toString());
		} catch (Exception e) {
			returnJo.put(FileParam.ERROR_RESPONSE, e.getMessage());
			e.printStackTrace();
		}
		ajaxOutput(response, returnJo.toString());
    }
    /**
     * 获取文件内容
     * *
     * @param cfsFileName 文件名称
     * @param request
     * @param response
     */
    @RequestMapping(params = "method=fetchFile")
    public void fetchFile(String cfsFileName, HttpServletRequest request, HttpServletResponse response){
    	if(!StringUtils.isEmpty(cfsFileName)){
    		cfsFileName = HttpUtil.decodeUrl(cfsFileName);
    		//解析文件名称，文件名称带有元数据信息
			String rawData = "";
			if(cfsFileName.contains("-")){
				rawData = cfsFileName.substring(0, cfsFileName.indexOf("-"));
			} else {
				rawData = cfsFileName.substring(0, cfsFileName.indexOf("."));
			}
			String serverId = rawData.substring(1, 2);
			String blockId = rawData.substring(2, rawData.length() - 10);

			ServletOutputStream outputStream = null;
    		AbstractFile cfsFile = baseFileService.fetchFile(serverId, EncryptUtil.decodeText(blockId), cfsFileName);
    		//文件物理路径
    		String key = FilePathUtil.getFilePhysicalPath(cfsFile);
    		FileInputStream fis = null;
    		InputStream bis = null;
			try {
				String fsSystem = MessageUtils.getInfoValue("FS_SYSTEM");
				ReadFileFromWhere fileFromWhere = null;
        		if("CFS".equals(fsSystem)){
        			fileFromWhere = new ReadFileFromCFS();
        		} else {
        			key = cfsFile.getLocalName();
        			fileFromWhere = ReadFileFromCompanyFS.newInstance();
        		}
				bis = fileFromWhere.read(key);
				outputStream = response.getOutputStream();
				byte[] bzp = new byte[1024];
				int length = 0;
				while((length = bis.read(bzp)) != -1){
					outputStream.write(bzp, 0, length);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(bis != null){
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(fis != null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
    	}
    }

    @RequestMapping(params = "method=redeploy", method = RequestMethod.GET)
	public void getUrlInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//待上传文件的字节数
		Thread tread = new Thread(new Runnable() {
			@Override
			public void run() {
				fileServerService.reDeploy();
			}
		});
		tread.start();
		response.getWriter().print(buildAjaxCallbackFunction(request, ""));
	}
}
