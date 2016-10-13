package com.focustech.cief.filemanage.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.focustech.cief.filemanage.dataserver.model.BaseEntity;

/**
 *
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "app_info_detail")
public class AppInfoDetail extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;
	private String appId;
	private String name;
	private String versionNum;
	private Integer systemType;
	private Integer deviceType;
	private String codeId;
	private Long appFileSn;
	private Long appDocFileSn;
	private String remark;
	private Integer status;
	private String thirdDownloadUrl;
	private Integer downloadType;
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "APP_SN")
	private AppInfo app;
	private Integer downloadNum;
	private Float appSize;
	private Long qrFileSn;
	@Transient
	private String title;
	@Transient
	private String fileName;
	@Transient
	private String plistVisitUrl;
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
	public String getVersionNum() {
		return versionNum;
	}
	public void setVersionNum(String versionNum) {
		this.versionNum = versionNum;
	}
	public Integer getSystemType() {
		return systemType;
	}
	public void setSystemType(Integer systemType) {
		this.systemType = systemType;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public Long getAppFileSn() {
		return appFileSn;
	}
	public void setAppFileSn(Long appFileSn) {
		this.appFileSn = appFileSn;
	}
	public Long getAppDocFileSn() {
		return appDocFileSn;
	}
	public void setAppDocFileSn(Long appDocFileSn) {
		this.appDocFileSn = appDocFileSn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public AppInfo getApp() {
		return app;
	}
	public void setApp(AppInfo app) {
		this.app = app;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public Integer getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(Integer downloadNum) {
		this.downloadNum = downloadNum;
	}
	public Float getAppSize() {
		return appSize;
	}
	public void setAppSize(Float appSize) {
		this.appSize = appSize;
	}
	public Long getQrFileSn() {
		return qrFileSn;
	}
	public void setQrFileSn(Long qrFileSn) {
		this.qrFileSn = qrFileSn;
	}
	public String getThirdDownloadUrl() {
		return thirdDownloadUrl;
	}
	public void setThirdDownloadUrl(String thirdDownloadUrl) {
		this.thirdDownloadUrl = thirdDownloadUrl;
	}
	public Integer getDownloadType() {
		return downloadType;
	}
	public void setDownloadType(Integer downloadType) {
		this.downloadType = downloadType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPlistVisitUrl() {
		return plistVisitUrl;
	}
	public void setPlistVisitUrl(String plistVisitUrl) {
		this.plistVisitUrl = plistVisitUrl;
	}

}
