package com.focustech.cief.filemanage.dataserver.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.focustech.focus3d.bundle.fileserver.data.api.DataServerNode;

/**
 * *
 * 所有文件的父类，描述所有上传文件的共同属性
 * @author lihaijun
 *
 */
@Entity
@Table(name = "com_file_data")
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractFile implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sn;
	/**名称*/
	private String name;
	/**大小*/
	private float size;
	/**格式*/
	private String ext;
	/**本地文件名称*/
	private String localName;
	/**块名称*/
	private String blockName;
	/**文件访问地址*/
	private String visitAddr;
	/**访问频率*/
	private Long visitFrequency;
	/**描述*/
	private String remark;
	/**逻辑删除标志*/
	private Integer isDelete;
	/**父节文件SN*/
	private Long parentFileSn;
	/**创建时间*/
	private Date createTime;
	private Long serverSn;
	@Transient
	private DataServerNode serverNode;

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
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getVisitAddr() {
		return visitAddr;
	}
	public void setVisitAddr(String visitAddr) {
		this.visitAddr = visitAddr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getServerSn() {
		return serverSn;
	}
	public void setServerSn(Long serverSn) {
		this.serverSn = serverSn;
	}
	public Long getVisitFrequency() {
		return visitFrequency;
	}
	public void setVisitFrequency(Long visitFrequency) {
		this.visitFrequency = visitFrequency;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Long getParentFileSn() {
		return parentFileSn;
	}
	public void setParentFileSn(Long parentFileSn) {
		this.parentFileSn = parentFileSn;
	}

	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getLocalName() {
		return localName;
	}
	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public DataServerNode getServerNode() {
		return serverNode;
	}
	public void setServerNode(DataServerNode serverNode) {
		this.serverNode = serverNode;
	}
	/**
	 * *
	 * @return
	 */
	public abstract String getType();
}
