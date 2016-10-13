package com.focustech.cief.filemanage.dataserver.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;

import com.focustech.cief.filemanage.dataserver.common.utils.FilePathUtil;
import com.focustech.cief.filemanage.dataserver.common.utils.StringUtil;
import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;
import com.focustech.cief.filemanage.dataserver.core.subfoldname.SubFoldNameManager;
import com.focustech.cief.filemanage.dataserver.core.write.FileWrite;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.FileBlock;
import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.cief.filemanage.dataserver.service.IFileBlockService;
import com.focustech.common.utils.MathUtils;
import com.focustech.common.utils.SHAUtil;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 文件操作父类
 * *
 * @author lihaijun
 *
 */
public abstract class AbstractFileOperateController extends BaseFileOperateController{
	@Autowired
	private IFileBlockService<FileBlock> blockService;
	@Autowired
	private DataServerNode serverNode;
	/**
	 * 文件上传
	 * *
	 * @param request
	 */
	protected String upload(HttpServletRequest request){
		//返回文件信息
    	String urlParam = decodeUrlParameter(request);
    	if(serverNode != null){
			//查询block信息，架构改变
			FileBlock fileBlock = blockService.createBlock(urlParam, serverNode);
			if(fileBlock != null){
				String subBlockName = fileBlock.getName();//createSubBlockFoldName(urlParam);
				String subBlockPath = FilePathUtil.getSubBlockPhysicalPath(serverNode, subBlockName);
				//创建子block目录
				File foldFile = new File(subBlockPath);
				if (!foldFile.exists()) {
					foldFile.mkdirs();
				}
				//写文件
				return writeFile(request, urlParam, serverNode, subBlockName);
			}
    	}
		return "";
	}

	/**
	 * 文件上传(上传到指定文件的同目录下)
	 * *
	 * @param request
	 */
	protected String uploadToTargetFilePath(HttpServletRequest request){
		//返回文件信息
		String rv = "";
		//请求参数
    	String urlParam = decodeUrlParameter(request);
		String oriFileSn = request.getParameter(FileParam.ORIGN_SN);
		if(!StringUtil.isEmpty(oriFileSn)){
			AbstractFile oriFile = baseFileService.findBySn(Long.parseLong(oriFileSn));
			rv = writeFile(request, urlParam, serverNode, oriFile.getBlockName());
		}
		return rv;
	}
	/**
	 * 写文件
	 * *
	 * @param request 请求对象
	 * @param urlParam url参数
	 * @param fileServer fileServer对象
	 * @param subBlockPath 本地文件存放路径
	 * @param subBlockVisitUrl 文件访问路径
	 * @return
	 */
	private String writeFile(HttpServletRequest request, String urlParam, DataServerNode fileServer, String subBlockName) {
		DiskFileItemFactory itemFactory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(itemFactory);
		fileUpload.setHeaderEncoding(ENCODE_UTF8);
		final UploadProgress preProgress = createUploadProgress(urlParam);
		if(preProgress.isUploadProgress()){
			fileUpload.setProgressListener(new ProgressListener() {
				@Override
				public void update(long uploading, long totalSize, int count) {
					preProgress.setCount(count);
					preProgress.setUploading(uploading);
					preProgress.setTotalSize(totalSize);
					preProgress.setPercentage(MathUtils.div(uploading, totalSize, 2));
				}
			});
		}
		List<FileItem> fileList = null;
		try {
			fileList = fileUpload.parseRequest(request);
			Iterator<FileItem> it = fileList.iterator();
			FileWrite fileWrite = new FileWrite();
			JSONArray jary = new JSONArray();
			while (it.hasNext()) {
				FileItem item = it.next();
				if (!item.isFormField()) {
					JSONObject jsonObj = fileWrite.writeFile(item, subBlockName, fileServer, urlParam);
					if(null == jsonObj){
						continue;
					}
					if(fileList.size() == 1){
						return jsonObj.toString();
					} else {
						jary.add(jsonObj);
					}
				}
			}
			return jary.toString();
		}
		catch (FileUploadException  e) {
			e.printStackTrace();
		}
		return "";
	}
	//是否记录上传进度数据
	public static final String UPLOAD_PROGRESS = "uploadProgress";
	//进度数据key值
	public static final String PROGRESS_KEY = "progressKey";
	/**
	 * 创建上传进度信息
	 * *
	 * @param urlParam
	 * @return
	 */
	private UploadProgress createUploadProgress(String urlParam){
		boolean uploadProgress = false;
		String progressKey = "";
		if(StringUtils.isNotEmpty(urlParam)){
			JSONObject urlParamJo = JSONObject.fromObject(urlParam);
			if(urlParamJo.containsKey(UPLOAD_PROGRESS)){
				uploadProgress = urlParamJo.getBoolean(UPLOAD_PROGRESS);
			}
			if(urlParamJo.containsKey(PROGRESS_KEY)){
				progressKey = (urlParamJo.getString(PROGRESS_KEY));
				if(StringUtils.isNotEmpty(progressKey)){
					if(progressKey.lastIndexOf(".") != -1){
						progressKey = progressKey.substring(0, progressKey.lastIndexOf("."));
					}
					try {
						byte[] sha1Digest = SHAUtil.getSHA1Digest(progressKey);
						progressKey = SHAUtil.byte2Hex(sha1Digest);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		UploadProgress preProgress = new UploadProgress();
		preProgress.setProgressKey(progressKey);
		preProgress.setUploadProgress(uploadProgress);
		return preProgress;
	}

	/**
	 * 获取上传文件服务器的ID
	 * *
	 * @param rootRequestUrl
	 * @return
	 */
	private FileServer getFileServer(String rootRequestUrl){
		String domainAndPort = rootRequestUrl.substring(rootRequestUrl.lastIndexOf("/") + 1);
		String[] domainAndPortAry = domainAndPort.split(":");
		FileServer fileServer = fileServerService.select(domainAndPortAry[0], TCUtil.iv(domainAndPortAry[1]));
		return fileServer;
	}
	/**
	 * 生成块子文件目录名称
	 * 0-日期格式;1-人物模型文件夹;2-3D产品体验区文件夹;3-演播厅视频直播模型文件夹
	 * *
	 * @param urlParam
	 * @return
	 */
	private String createSubBlockFoldName(String urlParam) {
		//子目录名称-可以自定义存放文件的文件夹名称
		String subFoldType = FileTypeOrExtProcRelation.SUB_FOLDER_DATE;
		if(!StringUtil.isEmpty(urlParam)){
			JSONObject urlParamJsonObj = JSONObject.fromObject(urlParam);
		    subFoldType = urlParamJsonObj.containsKey(FileParam.SUB_FOLDER_TYPE) ? urlParamJsonObj.getString(FileParam.SUB_FOLDER_TYPE) : FileTypeOrExtProcRelation.SUB_FOLDER_DATE;
		}
		return SubFoldNameManager.getRealSubFoldName(subFoldType);
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
			if(!StringUtil.isEmpty(parameter)){
				urlParam = URLDecoder.decode(parameter, ENCODE_UTF8);
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return urlParam;
	}
}
