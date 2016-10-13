package com.focustech.cief.filemanage.hx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "xml_detail")
public class XmlDetail {

	private Long sn;
	private String id;
	private String name;
	private Long xmlInfoSn;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getXmlInfoSn() {
		return xmlInfoSn;
	}
	public void setXmlInfoSn(Long xmlInfoSn) {
		this.xmlInfoSn = xmlInfoSn;
	}
}
