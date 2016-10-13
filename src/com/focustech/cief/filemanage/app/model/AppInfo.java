package com.focustech.cief.filemanage.app.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "app_info")
public class AppInfo extends BaseEntity{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;
	private String name;
	private String remark;
	private Long appIconFileSn;
	private Integer status;
	@OneToMany(mappedBy = "app", cascade = CascadeType.ALL)
	private List<AppInfoDetail> detail = new ArrayList<AppInfoDetail>();
	@Transient
	private String appIconFileUrl;
	@Transient
	private String qrCodeContent;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getAppIconFileSn() {
		return appIconFileSn;
	}
	public void setAppIconFileSn(Long appIconFileSn) {
		this.appIconFileSn = appIconFileSn;
	}
	public List<AppInfoDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<AppInfoDetail> detail) {
		this.detail = detail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAppIconFileUrl() {
		return appIconFileUrl;
	}
	public void setAppIconFileUrl(String appIconFileUrl) {
		this.appIconFileUrl = appIconFileUrl;
	}
	public String getQrCodeContent() {
		return qrCodeContent;
	}
	public void setQrCodeContent(String qrCodeContent) {
		this.qrCodeContent = qrCodeContent;
	}
}
