package com.focustech.cief.filemanage.dataserver.service.impl;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.core.conf.FileParam;
import com.focustech.cief.filemanage.dataserver.core.conf.FileTypeOrExtProcRelation;
import com.focustech.cief.filemanage.dataserver.core.subfoldname.SubFoldNameManager;
import com.focustech.cief.filemanage.dataserver.dao.IFileBlockDao;
import com.focustech.cief.filemanage.dataserver.dao.IFileBlockServerDao;
import com.focustech.cief.filemanage.dataserver.model.FileBlock;
import com.focustech.cief.filemanage.dataserver.model.FileBlockServer;
import com.focustech.cief.filemanage.dataserver.service.IFileBlockService;
import com.focustech.common.utils.StringUtils;
import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;
@Service
@Transactional
public class FileBlockServiceImpl implements IFileBlockService<FileBlock> {
	@Autowired
	private IFileBlockDao<FileBlock> blockDao;
	@Autowired
	private IFileBlockServerDao<FileBlockServer> blockServerDao;
	@Override
	public long save(FileBlock block) {
		blockDao.insert(block);
		return block.getSn();
	}
	@Override
	public FileBlock select(String blockName) {

		return blockDao.select(blockName);
	}
	@Override
	public FileBlock save(String blockName) {
		FileBlock block = new FileBlock();
		block.setName(blockName);
		block.setCapacity(1024 * 10L);
		block.setCreateTime(new Date());
		blockDao.insertOrUpdate(block);
		return block;
	}
	@Override
	public FileBlock createBlock(String urlParam, DataServerNode dataServerNode) {
		//创建block
		String blockName = createSubBlockFoldName(urlParam);
		FileBlock fileBlock = select(blockName);
		if(fileBlock == null){
			fileBlock = save(blockName);
		}
		if(fileBlock != null && dataServerNode != null){
			//保存block与DataServer关系
			Long serverSn = dataServerNode.getSn();
			FileBlockServer blockServer = blockServerDao.select(fileBlock.getSn(), serverSn);
			if(blockServer == null){
				blockServer = new FileBlockServer();
				blockServer.setServerSn(serverSn);
				blockServer.setFileBlock(fileBlock);
				blockServerDao.insertOrUpdate(blockServer);
			}
		}
		return fileBlock;
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
		if(!StringUtils.isEmpty(urlParam)){
			JSONObject urlParamJsonObj = JSONObject.fromObject(urlParam);
		    subFoldType = urlParamJsonObj.containsKey(FileParam.SUB_FOLDER_TYPE) ? urlParamJsonObj.getString(FileParam.SUB_FOLDER_TYPE) : FileTypeOrExtProcRelation.SUB_FOLDER_DATE;
		}
		return SubFoldNameManager.getRealSubFoldName(subFoldType);
	}
	@Override
	public FileBlock select(Long sn) {
		return blockDao.select(sn);
	}
}
