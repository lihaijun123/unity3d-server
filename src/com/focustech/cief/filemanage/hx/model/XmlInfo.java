package com.focustech.cief.filemanage.hx.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 *
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "xml_info")
public class XmlInfo {
	private Long sn;
	private String name;
	private Integer xmlType;
	private Long xmlFileSn;
	private Long datFileSn;
	private Integer versionNum;
	private Date addTime;
	private Date updateTime;
	//不存db
	private String encryptSn;
	private String xmlFileName;
	private String datFileName;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Integer getXmlType() {
		return xmlType;
	}
	public void setXmlType(Integer xmlType) {
		this.xmlType = xmlType;
	}
	public Long getXmlFileSn() {
		return xmlFileSn;
	}
	public void setXmlFileSn(Long xmlFileSn) {
		this.xmlFileSn = xmlFileSn;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Transient
	public String getEncryptSn() {
		return encryptSn;
	}
	public void setEncryptSn(String encryptSn) {
		this.encryptSn = encryptSn;
	}
	@Transient
	public String getXmlFileName() {
		return xmlFileName;
	}
	public void setXmlFileName(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}
	public Long getDatFileSn() {
		return datFileSn;
	}
	public void setDatFileSn(Long datFileSn) {
		this.datFileSn = datFileSn;
	}
	@Transient
	public String getDatFileName() {
		return datFileName;
	}
	public void setDatFileName(String datFileName) {
		this.datFileName = datFileName;
	}
	public Integer getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(Integer versionNum) {
		this.versionNum = versionNum;
	}
}
