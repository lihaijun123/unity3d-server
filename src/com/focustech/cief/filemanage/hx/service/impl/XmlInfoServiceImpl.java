package com.focustech.cief.filemanage.hx.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.focustech.cief.filemanage.dataserver.model.AbstractFile;
import com.focustech.cief.filemanage.dataserver.service.IBaseFileService;
import com.focustech.cief.filemanage.hx.dao.XmlDetailDao;
import com.focustech.cief.filemanage.hx.dao.XmlInfoDao;
import com.focustech.cief.filemanage.hx.model.XmlDetail;
import com.focustech.cief.filemanage.hx.model.XmlInfo;
import com.focustech.cief.filemanage.hx.service.XmlInfoService;
import com.focustech.common.utils.EncryptUtil;
import com.focustech.common.utils.ListUtils;
/**
 * *
 * @author lihaijun
 *
 */
@Service
@Transactional
public class XmlInfoServiceImpl implements XmlInfoService<XmlInfo> {

	@Autowired
	private XmlInfoDao<XmlInfo> xmlInfoDao;
	@Autowired
	private XmlDetailDao<XmlDetail> xmlDetailDao;
	@Autowired
	private IBaseFileService<AbstractFile> baseFileService;

	@Override
	public List<XmlInfo> list() {
		List<XmlInfo> list = xmlInfoDao.list();
		for (XmlInfo xmlInfo : list) {
			Long sn = xmlInfo.getSn();
			String encode = EncryptUtil.encode(sn);
			xmlInfo.setEncryptSn(encode);
		}
		return list;
	}

	@Override
	public XmlInfo select(long sn) {
		XmlInfo xmlInfo = xmlInfoDao.select(sn);
		Long xmlFileSn = xmlInfo.getXmlFileSn();
		Long datFileSn = xmlInfo.getDatFileSn();
		if(xmlFileSn != null && xmlFileSn > 0){
			Map<String, String> xmlFileMap = baseFileService.select(xmlFileSn);
			String xmlFileName = xmlFileMap.get("NAME");
			xmlInfo.setXmlFileName(xmlFileName);
		}
		if(datFileSn != null && datFileSn > 0){
			Map<String, String> xmlFileMap = baseFileService.select(datFileSn);
			String datFileName = xmlFileMap.get("NAME");
			xmlInfo.setDatFileName(datFileName);
		}
		return xmlInfo;
	}

	@Override
	public void save(XmlInfo xmlInfo) {
		//设置之前版本为历史版本
		xmlInfoDao.updateByType(0);
		xmlInfo.setSn(null);
		xmlInfoDao.insertOrUpdate(xmlInfo);
		Long xmlFileSn = xmlInfo.getXmlFileSn();
		byte[] readFile = baseFileService.readFile(xmlFileSn);
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new ByteArrayInputStream(readFile));
			Element root = document.getRootElement();
			List<XmlDetail> xmlInfos = new ArrayList<XmlDetail>();
			Long xmlInfoSn = xmlInfo.getSn();
			listNodes(root, xmlInfos, xmlInfoSn);
			//先删除
			//xmlDetailDao.deleteByXmlInfo(xmlInfoSn);
			//保存
			for (XmlDetail xmlDetail : xmlInfos) {
				xmlDetailDao.insertOrUpdate(xmlDetail);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/**
	 *
	 * *
	 * @param node
	 * @param xmlList
	 * @param xmlSn
	 */
	private void listNodes(Element node, List<XmlDetail> xmlList, long xmlSn) {
		Iterator<Element> iterator = node.elementIterator();
		while(iterator.hasNext()){
			Element e = iterator.next();
			boolean hasNext = e.elementIterator().hasNext();
			if(hasNext){
				listNodes(e, xmlList, xmlSn);
			} else {
				XmlDetail xmlDetail = new XmlDetail();
				List attributes = e.attributes();
				if(ListUtils.isNotEmpty(attributes)){
					xmlDetail.setXmlInfoSn(xmlSn);
					Attribute x = (Attribute)attributes.get(0);
					xmlDetail.setId(x.getValue());
					xmlDetail.setName(x.getValue());
					xmlList.add(xmlDetail);
				}
			}
		}
	}

	@Override
	public List<XmlInfo> list(int type) {
		List<XmlInfo> list = xmlInfoDao.list(type);
		for (XmlInfo xmlInfo : list) {
			Long sn = xmlInfo.getSn();
			String encode = EncryptUtil.encode(sn);
			xmlInfo.setEncryptSn(encode);
		}
		return list;
	}
}
