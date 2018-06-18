package org.jeedevframework.springboot.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class App {
	
	private int id;
	private Date gmtCreate;
	private Date gmtModified;
	private int isDeleted = 0;
	private int reOrder = 0;
	private String appCode;
	private String appName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getIsDeleted() {
		return isDeleted;
	}
	
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getReOrder() {
		return reOrder;
	}
	public void setReOrder(int reOrder) {
		this.reOrder = reOrder;
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
	
}
