package com.focustech.cief.filemanage.dataserver.core.write;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;

import com.focustech.cief.filemanage.dataserver.model.FileServer;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * 写文件接口
 * *
 * @author lihaijun
 *
 */
public interface IFileWrite {
	/**
	 * 写文件
	 * *
	 * @param fileItem 文件流
	 * @param fileLocalSavePath 本地保存路径
	 * @param fileVisitUrl 文件访问路径
	 * @param fileServer 服务器
	 * @param urlParam 请求参数
	 * @return 返回JSON信息
	 */
	public JSONObject writeFile(FileItem fileItem, String subBlockName, DataServerNode fileServer, String urlParam);
}
