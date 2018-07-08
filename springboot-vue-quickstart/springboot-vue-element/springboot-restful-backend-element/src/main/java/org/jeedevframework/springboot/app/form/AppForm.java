package org.jeedevframework.springboot.app.form;

import javax.validation.constraints.NotNull;


public class AppForm {

	private Integer id;
	
	@NotNull(message = "应用编号不能为空")
	private String appCode;
	
	@NotNull(message = "应用名称不能为空")
	private String appName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
