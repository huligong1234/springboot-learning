package org.jeedevframework.springboot.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "app")
@EntityListeners(AuditingEntityListener.class)
public class App implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private int id;

	@CreatedDate
	private Date gmtCreate;
	
	@LastModifiedDate
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
				+ ", isDeleted=" + isDeleted + ", appCode=" + appCode + ", appName=" + appName + ", getId()=" + getId()
				+ ", getGmtCreate()=" + getGmtCreate() + ", getGmtModified()=" + getGmtModified() + ", getReOrder()="
				+ getReOrder() + ", getIsDeleted()=" + getIsDeleted() + ", getAppCode()=" + getAppCode()
				+ ", getAppName()=" + getAppName() + "]";
	}
	
	
	
}
