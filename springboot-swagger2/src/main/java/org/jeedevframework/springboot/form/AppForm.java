package org.jeedevframework.springboot.form;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "应用信息修改Form")
public class AppForm {

	@ApiModelProperty(value = "应用主键ID")
	private Integer id;
	
	@NotNull(message = "应用编号不能为空")
	@ApiModelProperty(value = "应用编号")
	private String appCode;
	
	@NotNull(message = "应用名称不能为空")
	@ApiModelProperty(value = "应用名称")
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
