package com.focustech.cief.filemanage.dataserver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long adderSn;
	private String adderName;
	private Date addTime;
	private Long updaterSn;
	private String updaterName;
	private Date updateTime;

	public Long getAdderSn() {
		return adderSn;
	}

	public void setAdderSn(Long adderSn) {
		this.adderSn = adderSn;
	}

	public String getAdderName() {
		return adderName;
	}

	public void setAdderName(String adderName) {
		this.adderName = adderName;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Long getUpdaterSn() {
		return updaterSn;
	}

	public void setUpdaterSn(Long updaterSn) {
		this.updaterSn = updaterSn;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
