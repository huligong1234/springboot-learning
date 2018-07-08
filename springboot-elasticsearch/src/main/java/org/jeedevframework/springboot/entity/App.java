package org.jeedevframework.springboot.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
//indexName索引名称 可以理解为数据库名 必须为小写 不然会报org.elasticsearch.indices.InvalidIndexNameException异常
//type类型 可以理解为表名
@Document(indexName = "jeedev",type = "app")
public class App implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;
	
	private Date gmtCreate;
	private Date gmtModified;
	private Integer reOrder = 0;
	private Integer isDeleted = 0;
	private String appCode;
	private String appName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public Integer getReOrder() {
		return reOrder;
	}
	public void setReOrder(Integer reOrder) {
		this.reOrder = reOrder;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Override
	public String toString() {
		return "App [id=" + id + ", gmtCreate=" + gmtCreate + ", gmtModified=" + gmtModified + ", reOrder=" + reOrder
				+ ", isDeleted=" + isDeleted + ", appCode=" + appCode + ", appName=" + appName + "]";
	}
	
	
}
