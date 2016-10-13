package com.focustech.cief.filemanage.hx.model;

import java.util.Date;

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
@Table(name = "hx_download")
public class HxDownload {
	private Long sn;
	private String deviceId;
	private Long hxInfoSn;
	private Date addTime;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Long getHxInfoSn() {
		return hxInfoSn;
	}
	public void setHxInfoSn(Long hxInfoSn) {
		this.hxInfoSn = hxInfoSn;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
