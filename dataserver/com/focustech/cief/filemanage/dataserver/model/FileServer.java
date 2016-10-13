package com.focustech.cief.filemanage.dataserver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件服务器相关属性
 * *
 * @author lihaijun
 *
 */
@Entity
@Table(name = "com_file_server")
public class FileServer implements Serializable{
	private static final long serialVersionUID = 1L;
	/**服务器ID*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;
	/**服务器名称*/
	private String name;
	/**服务IP*/
	private String serverIp;
	/**服务域名*/
	private String serverDomain;
	/**服务端口*/
	private String serverPort;
	/**存储文件的根文件夹名称*/
	private String rootFoldName;
	/**访问频率*/
	private Long visitFrequency;
	/**文件目录的物理路径*/
	private String fileRootPath;
	/**最大能存储的文件数量*/
	private Integer maxFileAmount;
	/**当前存储的文件数量*/
	private Integer curFileAmount;
	/**文件主目录下子目录的数量,系统目录下的子目录个数是有限制的*/
	private Integer subFoldAmount;
	/**硬盘容量*/
	private Long hardDiscsCapacity;
	/**文件服务器限制存储文件的类型*/
	private Integer fileType;
	/**文件服务器是否可用*/
	private Integer flagUsable;
	/**创建时间*/
	private Date createTime;

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
	public String getFileRootPath() {
		return fileRootPath;
	}
	public void setFileRootPath(String fileRootPath) {
		this.fileRootPath = fileRootPath;
	}
	public Integer getMaxFileAmount() {
		return maxFileAmount;
	}
	public void setMaxFileAmount(Integer maxFileAmount) {
		this.maxFileAmount = maxFileAmount;
	}
	public Integer getCurFileAmount() {
		return curFileAmount;
	}
	public void setCurFileAmount(Integer curFileAmount) {
		this.curFileAmount = curFileAmount;
	}
	public Integer getSubFoldAmount() {
		return subFoldAmount;
	}
	public void setSubFoldAmount(Integer subFoldAmount) {
		this.subFoldAmount = subFoldAmount;
	}
	public Integer getFlagUsable() {
		return flagUsable;
	}
	public void setFlagUsable(Integer flagUsable) {
		this.flagUsable = flagUsable;
	}
	public Long getVisitFrequency() {
		return visitFrequency;
	}
	public void setVisitFrequency(Long visitFrequency) {
		this.visitFrequency = visitFrequency;
	}
	public Long getHardDiscsCapacity() {
		return hardDiscsCapacity;
	}
	public void setHardDiscsCapacity(Long hardDiscsCapacity) {
		this.hardDiscsCapacity = hardDiscsCapacity;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRootFoldName() {
		return rootFoldName;
	}
	public void setRootFoldName(String rootFoldName) {
		this.rootFoldName = rootFoldName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public String getServerDomain() {
		return serverDomain;
	}
	public void setServerDomain(String serverDomain) {
		this.serverDomain = serverDomain;
	}
	public String getServerPort() {
		return serverPort;
	}
	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

}
