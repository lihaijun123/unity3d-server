package com.focustech.cief.filemanage.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.app.dao.AppInfoDao;
import com.focustech.cief.filemanage.app.dao.AppInfoDetailDao;
import com.focustech.cief.filemanage.app.id.AppNumber;
import com.focustech.cief.filemanage.app.model.AppInfo;
import com.focustech.cief.filemanage.app.model.AppInfoDetail;
import com.focustech.cief.filemanage.app.service.AppInfoService;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.model.BaseEntity;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class AppInfoServiceImpl implements AppInfoService<AppInfo>{
	@Autowired
	private AppInfoDao<AppInfo> appBackstageDao;
	@Autowired
	private AppInfoDetailDao<AppInfoDetail> appBackstageDetailDao;
	@Autowired
	private AppNumber appNumber;
	@Autowired
	private AppCache appCache;
	@Autowired
	private IBaseFileService<AbstractFile> baseFileService;
	
	@Value(value = "${http.server.url}")
	protected String httpServerUrl;
	
	@Override
	public void insertOrUpdate(AppInfo app) {
		List<AppInfoDetail> detail = app.getDetail();
		//保存app明细
		for (AppInfoDetail appInfoDetail : detail) {
			//appid
			if(StringUtils.isEmpty(appInfoDetail.getAppId())){
				String number = appNumber.getNumber();
				AppInfoDetail selectByAppId = null;
				while(true){
					selectByAppId = appBackstageDetailDao.selectByAppId(number);
					if(selectByAppId != null){
						number = appNumber.getNumber();
						continue;
					}
					break;
				}
				appInfoDetail.setAppId(number);
			}
			String detailName = appInfoDetail.getName();
			if(StringUtils.isEmpty(detailName)){
				appInfoDetail.setName(app.getName());
			}
			String detailRemark = appInfoDetail.getRemark();
			if(StringUtils.isEmpty(detailRemark)){
				appInfoDetail.setRemark(app.getRemark());
			}
			setDBCommRecord(appInfoDetail);
			appInfoDetail.setApp(app);
		}
		setDBCommRecord(app);
		appBackstageDao.insertOrUpdate(app);
		//更新明细
		for (AppInfoDetail appBackstageDetail : app.getDetail()) {
			Long appFileSn = appBackstageDetail.getAppFileSn();
			if(appFileSn != null){
				//app文件大小
				Map<String, String> fileMap = baseFileService.select(appFileSn);
				float size = TCUtil.fv(fileMap.get("SIZE"));
				appBackstageDetail.setAppSize((float)size / 1024);//单位mb
			}
			appBackstageDetail.setApp(app);
		}
		//发布到本地缓存
		publishLocalCache(detail);
	}
	/**
	 * 发布到本地缓存
	 * *
	 * @param detail
	 */
	public void publishLocalCache(List<AppInfoDetail> detail){
		if(ListUtils.isNotEmpty(detail)){
			for (AppInfoDetail appBackstageDetail : detail) {
				appCache.put(appBackstageDetail);
			}
		}
	}

	@Override
	public AppInfo select(Long sn) {
		AppInfo appInfo = appBackstageDao.select(sn);
		List<AppInfoDetail> detail = appInfo.getDetail();
		for (AppInfoDetail appInfoDetail : detail) {
			Long appFileSn = appInfoDetail.getAppFileSn();
			if(appFileSn != null && appFileSn > 0){
				Map<String, String> xmlFileMap = baseFileService.select(appFileSn);
				String fileName = xmlFileMap.get("NAME");
				appInfoDetail.setFileName(fileName);
			}
		}
		setIconFileInfo(appInfo);
		return appInfo;
	}
	/**
	 * 
	 * *
	 * @param appInfo
	 */
	private void setIconFileInfo(AppInfo appInfo) {
		Long appIconFileSn = appInfo.getAppIconFileSn();
		if(appIconFileSn != null && appIconFileSn > 0){
			Map<String, String> xmlFileMap = baseFileService.select(appIconFileSn);
			String appIconFileUrl = xmlFileMap.get("VISIT_ADDR");
			appInfo.setAppIconFileUrl(baseFileService.getVisitServerUrl() + appIconFileUrl);
		}
	}
	/**
	 *
	 * *
	 * @param baseEntity
	 */
	private void setDBCommRecord(BaseEntity baseEntity) {
		baseEntity.setAdderSn(-1L);
		baseEntity.setAdderName("system");
		baseEntity.setAddTime(new Date());
		baseEntity.setUpdaterSn(-1L);
		baseEntity.setUpdaterName("system");
		baseEntity.setUpdateTime(new Date());
	}
	@Override
	public List<AppInfo> list(Integer status) {
		List<AppInfo> list = appBackstageDao.list(status);
		for (AppInfo appInfo : list) {
			setIconFileInfo(appInfo);
			appInfo.setQrCodeContent(createQrCodeContent(appInfo));
		}
		return list;
	}
	/**
	 * 获取二维码sn
	 * *
	 * @param productInfo
	 * @return
	 */
	public long getQrFileSn(String name, String content){
		long qrFileSn = 0;
		/*try {
			FileInfo fileUploadObject = new FileInfo();
			fileUploadObject.setName(name + "-二维码.jpg");
			fileUploadObject.setBytes(QrGeneratorUtil.generate(content));
			IFileManageClient fileManageClient = FileManageFactory.getFileManageClient(fileUploadObject);
			fileManageClient.upload();
			qrFileSn = TCUtil.lv(fileManageClient.getFileId());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		return qrFileSn;
	}
	
	/**
	 *
	 * *
	 * @param app
	 * @return
	 */
	public String createQrCodeContent(AppInfo app) {
		String homeUrlPrefix = httpServerUrl + "/fs/app/download/" + app.getIsNeedReg() + "/";
		String encryptSn = EncryptUtil.encode(app.getSn());
		return homeUrlPrefix + encryptSn;
	}
	@Override
	public List<AppInfo> list(Integer status, boolean isNeedReg) {
		List<AppInfo> list = appBackstageDao.list(status, isNeedReg);
		for (AppInfo appInfo : list) {
			setIconFileInfo(appInfo);
			appInfo.setQrCodeContent(createQrCodeContent(appInfo));
		}
		return list;
	}
}
