package com.focustech.cief.filemanage.log.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.focustech.cief.filemanage.user.model.UserInfo;

/**
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "stat_app_use_time_log")
public class StatAppUseTimeLog {
	private Long sn;
	private Long userId;
	private String appName;
	private String useTime;
	private Date addDate;

	private UserInfo userInfo;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public Date getAddDate() {
		return addDate;
	}
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	@Transient
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

}
