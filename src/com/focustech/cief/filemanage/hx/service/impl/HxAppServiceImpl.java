package com.focustech.cief.filemanage.hx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.HttpVisitUrl;
import com.focustech.cief.filemanage.dataserver.dao.IBaseFileDao;
import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.cief.filemanage.hx.constant.HxConstant;
import com.focustech.cief.filemanage.hx.dao.HxDownloadDao;
import com.focustech.cief.filemanage.hx.dao.HxInfoDao;
import com.focustech.cief.filemanage.hx.dao.XmlDetailDao;
import com.focustech.cief.filemanage.hx.dao.XmlInfoDao;
import com.focustech.cief.filemanage.hx.model.HxDownload;
import com.focustech.cief.filemanage.hx.model.HxInfo;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
import com.focustech.cief.filemanage.hx.service.HxAppService;
import com.focustech.cief.filemanage.hx.service.XmlInfoService;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.TCUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class HxAppServiceImpl implements HxAppService {
	@Autowired
	private HxInfoDao<HxInfo> hxInfoDao;
	@Autowired
	private XmlDetailDao<XmlDetail> xmlDetailDao;
	@Autowired
	private IBaseFileDao<AbstractFile> baseFileDao;
	@Autowired
	private HttpVisitUrl httpVisitUrl;
	@Autowired
	private HxDownloadDao<HxDownload> hxDownloadDao;
	@Autowired
	protected IBaseFileService<AbstractFile> baseFileService;
	@Autowired
	private XmlInfoDao<XmlInfo> xmlInfoDao;
	@Override
	public JSONObject findUnityFile(String xmlId, int mobileType) {
		List<HxInfo> rootHxInfoList = findParentBySelfOrChildXmlId(xmlId);
		JSONArray jary = new JSONArray();
		for (HxInfo hxInfo : rootHxInfoList) {
			Long unityFileSn = 0l;
			Integer version = 1;
			String url = "";
			JSONObject detailJo = new JSONObject();
			if(HxConstant.MOBILE_TYPE_ANDROID == mobileType){
				unityFileSn = hxInfo.getUnityFileSn();
				version = hxInfo.getUnityFileVersion();
			} else if(HxConstant.MOBILE_TYPE_IOS == mobileType){
				unityFileSn = hxInfo.getUnityIosFileSn();
				version = hxInfo.getUnityIosFileVersion();
			} else {
				unityFileSn = hxInfo.getUnityFileSn();
				version = hxInfo.getUnityFileVersion();
			}
			if(unityFileSn != null && unityFileSn > 0){
				Map<String, String> picFileMap = baseFileDao.selectById(unityFileSn);
				url = httpVisitUrl.getHttpPrefix() + picFileMap.get("VISIT_ADDR");
			}
			detailJo.put("url", url);
			detailJo.put("version", TCUtil.iv(version));
			jary.add(detailJo);
		}
		JSONObject jo = new JSONObject();
		jo.put("data", jary.toString());
		return jo;
	}

	@Override
	public JSONObject recordDownload(String deviceId, String xmlId) {
		List<HxInfo> rootHxInfoList = findParentBySelfOrChildXmlId(xmlId);
		for (HxInfo hxInfo : rootHxInfoList) {
			Long hxInfoSn = hxInfo.getSn();
			HxDownload hxDownload = hxDownloadDao.select(deviceId, hxInfoSn);
			if(hxDownload == null){
				hxDownload = new HxDownload();
				hxDownload.setDeviceId(deviceId);
				hxDownload.setHxInfoSn(hxInfoSn);
				hxDownload.setAddTime(new Date());
				hxDownloadDao.insertOrUpdate(hxDownload);
			}
		}
		JSONObject jo = new JSONObject();
		jo.put("stauts", 0);
		return jo;
	}

	@Override
	public JSONObject listTree(String deviceId, int mobileType) {
		List<HxDownload> hxDownloads = hxDownloadDao.selectList(deviceId);
		JSONArray jary = new JSONArray();
		for (HxDownload hxDownload : hxDownloads) {
			Long hxInfoSn = hxDownload.getHxInfoSn();
			if(hxInfoSn != null && hxInfoSn > 0){
				HxInfo hxInfo = hxInfoDao.select(hxInfoSn);
				if(hxInfo != null){
					JSONObject detailJo = new JSONObject();
					detailJo.put("hxName", hxInfo.getName());
					detailJo.put("hxPrice", hxInfo.getHxPrice());
					detailJo.put("hxSize", hxInfo.getHxSize());
					String hxPicFileUrl = "";
					Long hxPicFileSn = hxInfo.getPicFileSn();
					if(hxPicFileSn != null && hxPicFileSn > 0){
						Map<String, String> hxPicFileMap = baseFileDao.selectById(hxPicFileSn);
						hxPicFileUrl = httpVisitUrl.getHttpPrefix() + hxPicFileMap.get("VISIT_ADDR");
					}
					detailJo.put("hxPicUrl", hxPicFileUrl);
					detailJo.put("hxAddress", hxInfo.getHxAddress());
					detailJo.put("buildingName", hxInfo.getBuildingName());
					jary.add(detailJo);
				}
			}
		}
		JSONObject rvJo = new JSONObject();
		rvJo.put("data", jary.toString());
		return rvJo;
	}

	@Override
	public JSONObject listDownloadFile() {
		String httpPrefix = httpVisitUrl.getHttpPrefix();
		JSONObject jo = new JSONObject();
		jo.put("xmlUrl", httpPrefix + "home.xml");
		jo.put("datUrl", httpPrefix + "home.dat");
		List<XmlInfo> xmlInfoList = xmlInfoDao.list(XmlInfoService.ACTIVE_XML);
		int versionNum = 0;
		if(ListUtils.isNotEmpty(xmlInfoList)){
			versionNum = xmlInfoList.get(0).getVersionNum();
		}
		jo.put("version", String.valueOf(versionNum));
		return jo;
	}
	/**
	 *
	 * *
	 * @param xmlId
	 * @return
	 */
	private List<HxInfo> findParentBySelfOrChildXmlId(String xmlId){
		List<XmlDetail> xmlDetails = xmlDetailDao.selectListByName(xmlId);
		List<HxInfo> rootHxInfoList = new ArrayList<HxInfo>();
		for (XmlDetail xmlDetail : xmlDetails) {
			Long sn = xmlDetail.getSn();
			List<HxInfo> hxInfos = hxInfoDao.selectByXmlId(sn);
			for (HxInfo hxInfo : hxInfos) {
				Long parentSn = hxInfo.getParentSn();
				if(parentSn != -1){
					//子节点
					HxInfo parentHxInfo = hxInfoDao.select(parentSn);
					if(parentHxInfo != null){
						for (HxInfo rootHxInfo : rootHxInfoList) {
							if(rootHxInfo.getSn().equals(parentHxInfo.getSn())){
								parentHxInfo = null;
								break;
							}
						}
						if(parentHxInfo != null){
							rootHxInfoList.add(parentHxInfo);
						}
					}
				} else {
					//父节点
					rootHxInfoList.add(hxInfo);
				}
			}
		}
		return rootHxInfoList;
	}

}
