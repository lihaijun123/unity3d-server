package com.focustech.cief.filemanage.hx.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.web.controller.BaseController;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
import com.focustech.cief.filemanage.hx.service.XmlInfoService;
import com.focustech.common.utils.StringUtils;
import com.focustech.common.utils.TCUtil;

/**
 *
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/xml")
public class XmlManageController extends BaseController{
	@Autowired
	private XmlInfoService<XmlInfo> xmlInfoService;
	/**
	 *
	 * *
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(ModelMap modelMap, HttpServletResponse response){
		List<XmlInfo> xmlInfoList = xmlInfoService.list(XmlInfoService.ACTIVE_XML);
		modelMap.put("xmlInfoList", xmlInfoList);
		return "/xml/list";
	}
	/**
	 *
	 * *
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getXml(ModelMap modelMap, HttpServletResponse response){

		return "/xml/add";
	}
	/**
	 *
	 * *
	 * @param xmlInfo
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addXml(XmlInfo xmlInfo, ModelMap modelMap, HttpServletResponse response){
		Long xmlFileSn = xmlInfo.getXmlFileSn();
		Long datFileSn = xmlInfo.getDatFileSn();
		if(StringUtils.isNotEmpty(xmlInfo.getName()) && xmlFileSn != null && xmlFileSn > 0){
			if(xmlInfo.getXmlType() == null){
				xmlInfo.setXmlType(XmlInfoService.ACTIVE_XML);
			}
			updateVersionNum(xmlInfo, xmlFileSn, datFileSn);
			xmlInfoService.save(xmlInfo);

		}
		return redirect("/fs/xml/list");
	}
	/**
	 *
	 * *
	 * @param xmlInfo
	 * @param xmlFileSn
	 * @param datFileSn
	 */
	private void updateVersionNum(XmlInfo xmlInfo, Long xmlFileSn, Long datFileSn) {
		int versionNum = TCUtil.iv(xmlInfo.getVersionNum());
		Long sn = xmlInfo.getSn();
		if(sn != null){
			XmlInfo dbXmlInfo = xmlInfoService.select(sn);
			Long dbXmlFileSn = dbXmlInfo.getXmlFileSn();
			Long dbDatFileSn = dbXmlInfo.getDatFileSn();
			if(dbXmlFileSn != null && xmlFileSn !=null && !dbXmlFileSn.equals(xmlFileSn)){
				++ versionNum;
			}
			if(dbDatFileSn != null && datFileSn !=null && !dbDatFileSn.equals(datFileSn)){
				++ versionNum;
			}
		}
		xmlInfo.setVersionNum(versionNum);
	}
	/**
	 *
	 * *
	 * @param encryptSn
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edit/{encryptSn}", method = RequestMethod.GET)
	public String edit(@PathVariable String encryptSn, ModelMap modelMap, HttpServletResponse response){
		if(StringUtils.isNotEmpty(encryptSn)){
			String sn = decrypt(encryptSn);
			XmlInfo xmlInfo = xmlInfoService.select(TCUtil.lv(sn));
			modelMap.put("xmlInfo", xmlInfo);
			//List<HxInfo> list = hxInfoService.list(TCUtil.lv(sn));
			//modelMap.put("isModify", ListUtils.isEmpty(list));
		}
		return "/xml/edit";
	}
	/**
	 *
	 * *
	 * @param xmlInfo
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String postEdit(XmlInfo xmlInfo, ModelMap modelMap, HttpServletResponse response){
		Long xmlFileSn = xmlInfo.getXmlFileSn();
		Long datFileSn = xmlInfo.getDatFileSn();
		if(StringUtils.isNotEmpty(xmlInfo.getName()) && xmlFileSn != null && xmlFileSn > 0){
			if(xmlInfo.getXmlType() == null){
				xmlInfo.setXmlType(XmlInfoService.ACTIVE_XML);
			}
			updateVersionNum(xmlInfo, xmlFileSn, datFileSn);
			xmlInfoService.save(xmlInfo);
		}
		return redirect("/fs/xml/list");
	}
}
