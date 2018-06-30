package org.jeedevframework.springboot.entity;

import java.io.Serializable;
import java.util.Date;

public class App implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private Date gmtCreate;
	private Date gmtModified;
	private int reOrder = 0;
	private int isDeleted = 0;
	private String appCode;
	private String appName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
	public int getReOrder() {
		return reOrder;
	}
	public void setReOrder(int reOrder) {
		this.reOrder = reOrder;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
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
