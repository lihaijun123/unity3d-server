package com.focustech.cief.filemanage.hx.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.focustech.cief.filemanage.dataserver.web.controller.BaseController;
import com.focustech.cief.filemanage.hx.model.HxInfo;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
import com.focustech.cief.filemanage.hx.service.HxInfoService;
import com.focustech.cief.filemanage.hx.service.XmlDetailService;
import com.focustech.cief.filemanage.hx.service.XmlInfoService;
import com.focustech.common.utils.JsonUtils;
import com.focustech.common.utils.ListUtils;
import com.focustech.common.utils.StringUtils;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Controller
@RequestMapping("/fs/hx")
public class HxManageController extends BaseController{
	@Autowired
	private HxInfoService<HxInfo> hxInfoService;
	@Autowired
	private XmlInfoService<XmlInfo> xmlInfoService;
	@Autowired
	private XmlDetailService<XmlDetail> xmlDetailService;
	/**
	 * 绑定
	 * *
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/bind", method = RequestMethod.GET)
	public String add(ModelMap modelMap, HttpServletResponse response){
		long xmlInfoSn = 0;
		List<HxInfo> list = hxInfoService.list(xmlInfoSn);
		List<HxInfo> parentList = new ArrayList<HxInfo>();
		List<HxInfo> childrenList = new ArrayList<HxInfo>();
		//分组
		for (HxInfo hxInfo : list) {
			if(hxInfo.getParentSn() <= 0){
				parentList.add(hxInfo);
			} else {
				childrenList.add(hxInfo);
			}
		}
		for (HxInfo hxInfo : parentList) {
			long parentSn = hxInfo.getSn();
			for (HxInfo childHxInfo : childrenList) {
				if(childHxInfo.getParentSn() == parentSn){
					hxInfo.getChildren().add(childHxInfo);
				}
			}
		}
		modelMap.put("hxInfoList", parentList);

		List<XmlInfo> xmlInfoList = xmlInfoService.list(XmlInfoService.ACTIVE_XML);
		if(ListUtils.isNotEmpty(xmlInfoList)){
			XmlInfo xmlInfo = xmlInfoList.get(0);
			//XmlInfo xmlInfo = xmlInfoService.select(xmlInfoSn);
			xmlInfoSn = xmlInfo.getSn();
			modelMap.put("xmlInfo", xmlInfo);
			List<XmlDetail> xmlDetailList = xmlDetailService.list(xmlInfoSn);
			modelMap.put("xmlDetailList", xmlDetailList);
		}
		return "/hx/edit";
	}
	/**
	 *
	 * 提交编辑
	 * *
	 * @param sn
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HxInfo hxInfo, ModelMap modelMap, HttpServletResponse response){
		if(StringUtils.isNotEmpty(hxInfo.getName())){
			if(hxInfo.getParentSn() == null){
				hxInfo.setParentSn(-1L);
			}
			hxInfoService.save(hxInfo);
		}
		return redirect("/fs/hx/bind");
	}

	/**
	 * 编辑
	 * *
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edit/{hxInfoSn}", method = RequestMethod.GET)
	public void edit(@PathVariable long hxInfoSn, ModelMap modelMap, HttpServletResponse response){
		if(hxInfoSn > 0){
			HxInfo hxInfo = hxInfoService.select(hxInfoSn);
			try {
				ajaxOutput(response, JsonUtils.beanToJson(hxInfo));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 删除
	 * *
	 * @param modelMap
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete/{hxInfoSn}", method = RequestMethod.GET)
	public void delete(@PathVariable long hxInfoSn, ModelMap modelMap, HttpServletResponse response){
		if(hxInfoSn > 0){
			try {
				hxInfoService.delete(hxInfoSn);
				JSONObject jo = new JSONObject();
				jo.put("status", 0);
				ajaxOutput(response, jo.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
