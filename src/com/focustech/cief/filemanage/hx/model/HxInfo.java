package com.focustech.cief.filemanage.hx.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "hx_info")
public class HxInfo {

	private Long sn;
	private String name;
	private Long xmlId;
	private Long parentSn;
	private Long picFileSn;
	private Long unityFileSn;
	private Long unityIosFileSn;
	private Integer unityFileVersion;
	private Integer unityIosFileVersion;
	private String buildingName;
	private Double hxPrice;
	private String hxCode;
	private Double hxSize;
	private String hxAddress;
	private String summary;
	private Date addTime;
	private Date updateTime;
	//不存db
	private String xmlInfoSn;
	private String xmlDetailName;
	private List<HxInfo> children = new ArrayList<HxInfo>();
	private String picFileUrl;
	private String unityFileName;
	private String unityIosFileName;
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
	public Long getXmlId() {
		return xmlId;
	}
	public void setXmlId(Long xmlId) {
		this.xmlId = xmlId;
	}
	public Long getParentSn() {
		return parentSn;
	}
	public void setParentSn(Long parentSn) {
		this.parentSn = parentSn;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
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
	public String getXmlInfoSn() {
		return xmlInfoSn;
	}
	public void setXmlInfoSn(String xmlInfoSn) {
		this.xmlInfoSn = xmlInfoSn;
	}
	public Long getPicFileSn() {
		return picFileSn;
	}
	public void setPicFileSn(Long picFileSn) {
		this.picFileSn = picFileSn;
	}
	public Long getUnityFileSn() {
		return unityFileSn;
	}
	public void setUnityFileSn(Long unityFileSn) {
		this.unityFileSn = unityFileSn;
	}
	@Transient
	public String getXmlDetailName() {
		return xmlDetailName;
	}
	public void setXmlDetailName(String xmlDetailName) {
		this.xmlDetailName = xmlDetailName;
	}
	@Transient
	public List<HxInfo> getChildren() {
		return children;
	}
	public void setChildren(List<HxInfo> children) {
		this.children = children;
	}
	@Transient
	public String getPicFileUrl() {
		return picFileUrl;
	}
	public void setPicFileUrl(String picFileUrl) {
		this.picFileUrl = picFileUrl;
	}
	@Transient
	public String getUnityFileName() {
		return unityFileName;
	}
	public void setUnityFileName(String unityFileName) {
		this.unityFileName = unityFileName;
	}
	public Long getUnityIosFileSn() {
		return unityIosFileSn;
	}
	public void setUnityIosFileSn(Long unityIosFileSn) {
		this.unityIosFileSn = unityIosFileSn;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public String getHxCode() {
		return hxCode;
	}
	public void setHxCode(String hxCode) {
		this.hxCode = hxCode;
	}
	public String getHxAddress() {
		return hxAddress;
	}
	public void setHxAddress(String hxAddress) {
		this.hxAddress = hxAddress;
	}
	public Double getHxPrice() {
		return hxPrice;
	}
	public void setHxPrice(Double hxPrice) {
		this.hxPrice = hxPrice;
	}
	public Double getHxSize() {
		return hxSize;
	}
	public void setHxSize(Double hxSize) {
		this.hxSize = hxSize;
	}
	@Transient
	public String getUnityIosFileName() {
		return unityIosFileName;
	}
	public void setUnityIosFileName(String unityIosFileName) {
		this.unityIosFileName = unityIosFileName;
	}
	public Integer getUnityFileVersion() {
		return unityFileVersion;
	}
	public void setUnityFileVersion(Integer unityFileVersion) {
		this.unityFileVersion = unityFileVersion;
	}
	public Integer getUnityIosFileVersion() {
		return unityIosFileVersion;
	}
	public void setUnityIosFileVersion(Integer unityIosFileVersion) {
		this.unityIosFileVersion = unityIosFileVersion;
	}

}
