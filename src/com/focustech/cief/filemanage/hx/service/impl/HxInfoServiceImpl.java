package com.focustech.cief.filemanage.hx.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.HttpVisitUrl;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.cief.filemanage.hx.dao.HxInfoDao;
import com.focustech.cief.filemanage.hx.model.HxInfo;
import com.focustech.cief.filemanage.hx.service.HxInfoService;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class HxInfoServiceImpl implements HxInfoService<HxInfo> {
	@Autowired
	private HxInfoDao<HxInfo> hxInfoDao;
	@Autowired
	private IBaseFileService<AbstractFile> baseFileService;
	@Autowired
	protected HttpVisitUrl httpVisitUrl;

	@Override
	public void save(HxInfo t) {
		Long sn = t.getSn();
		if(sn == null){
			t.setAddTime(new Date());
			t.setUpdateTime(new Date());
		} else {
			if(t.getParentSn() == -1){
				Long pageUnityFileSn = t.getUnityFileSn();
				Long pageUnityIosFileSn = t.getUnityIosFileSn();
				HxInfo dbHxInfo = hxInfoDao.select(sn);
				Long dbUnityFileSn = dbHxInfo.getUnityFileSn();
				Long dbUnityIosFileSn = dbHxInfo.getUnityIosFileSn();
				if(pageUnityFileSn != null && !pageUnityFileSn.equals(dbUnityFileSn)){
					t.setUnityFileVersion(TCUtil.iv(dbHxInfo.getUnityFileVersion()) + 1);
				}
				if(pageUnityIosFileSn != null && !pageUnityIosFileSn.equals(dbUnityIosFileSn)){
					t.setUnityIosFileVersion(TCUtil.iv(dbHxInfo.getUnityIosFileVersion()) + 1);
				}
			}
			t.setUpdateTime(new Date());
		}
		hxInfoDao.update(t);
	}

	@Override
	public HxInfo select(long sn) {
		HxInfo hxInfo = hxInfoDao.select(sn);
		Long picFileSn = hxInfo.getPicFileSn();
		if(picFileSn != null && picFileSn > 0){
			Map<String, String> picFileMap = baseFileService.select(picFileSn);
			String visitAddr = picFileMap.get("VISIT_ADDR");
			hxInfo.setPicFileUrl(httpVisitUrl.getHttpPrefix() + visitAddr);
		}
		Long unityFileSn = hxInfo.getUnityFileSn();
		if(unityFileSn != null && unityFileSn > 0){
			Map<String, String> unityFileMap = baseFileService.select(unityFileSn);
			String unityFileName = unityFileMap.get("NAME");
			hxInfo.setUnityFileName(unityFileName);
		}
		Long unityIosFileSn = hxInfo.getUnityIosFileSn();
		if(unityIosFileSn != null && unityIosFileSn > 0){
			Map<String, String> unityFileMap = baseFileService.select(unityIosFileSn);
			String unityIosFileName = unityFileMap.get("NAME");
			hxInfo.setUnityIosFileName(unityIosFileName);
		}
		return hxInfo;
	}

	@Override
	public List<HxInfo> list(long xmlInfoSn) {
		return hxInfoDao.list(xmlInfoSn);
	}

	@Override
	public void delete(long xmlInfoSn) {
		HxInfo hxInfo = hxInfoDao.select(xmlInfoSn);
		if(hxInfo != null){
			Long parentSn = hxInfo.getParentSn();
			if(parentSn != -1){
				//删除的是叶子节点
				hxInfoDao.delete(xmlInfoSn);
			} else {
				//删除的是父亲节点
				Long sn = hxInfo.getSn();
				hxInfoDao.delteChilds(sn);
				hxInfoDao.delete(sn);
			}
		}
	}

}
