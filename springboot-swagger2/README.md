# SpringBoot + Swagger2 学习笔记

本项目示例主要用于提供 快速 配置使用 Swagger2

## 一、概述

* SpringBoot官网： http://spring.io/projects/spring-boot

* Spring Data JPA官网: http://projects.spring.io/spring-data-jpa/

* Swagger2 官网： https://swagger.io/

  
Swagger 是一款RESTFUL接口的文档在线自动生成+功能测试功能软件。 Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web  服务。总体目标是使客户端和文件系统作为服务器以同样的速度来更新。文件的方法，参数和模型紧密集成到服务器端的代码，允许API来始终保持同步。Swagger 让部署管理和使用功能强大的API从未如此简单。

Github上地址： https://github.com/swagger-api/swagger-ui




## 二、依赖版本信息

通过starter创建项目，选择Web,MySQL,JDBC,JPA

* JDK1.8
* MySQL 5.7.21
* SpringBoot:2.1.0.BUILD-SNAPSHOT
* Spring Data JPA
* springfox-swagger2: 2.8.0
* springfox-swagger-ui: 2.8.0

## 三、配置和实现
### 3.1 基于SpringBoot+JPA+MySQL接口实现 

略,本项目在之前一个项目springboot-jpa基础上进行配置调整的，
具体可参看springboot-jpa项目:https://github.com/huligong1234/springboot-learning/tree/master/springboot-jpa

### 3.2 pom.xml中配置 支持swagger2
```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.8.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.8.0</version>
</dependency>
```

### 3.3 增加配置类 AppConfig

```java
package org.jeedevframework.springboot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig  {
    @Bean
    public Docket docket() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        pars.add(builder.name("authorization")
        		.description("authorization")
        		.modelRef(new ModelRef("string"))
        		.parameterType("header").required(false).build());
       
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.jeedevframework.springboot"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger2")
                .description("Swagger官网 https://swagger.io/")
                .termsOfServiceUrl("https://swagger.io/")
                .version("1.0")
                .build();
    }

}
```

此时启动App 访问 http://localhost:8088/swagger-ui.html 即已可看到在线文档接口描述和可视化接口调试

### 3.4 在AppController类中接口上 增加 丰富文档结构 的注解（非必须）
```java
package org.jeedevframework.springboot.web;

import java.util.Date;
import java.util.List;

import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.common.Response;
import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.form.AppForm;
import org.jeedevframework.springboot.service.AppService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Api("应用管理接口")
@RestController
@RequestMapping("/app")
public class AppController {
    
	@Autowired
    AppService appService;

	@ApiOperation(value="获取所有应用列表", notes="无分页")
    @GetMapping("/all")
    public Response<List<App>> findAll(){
    	return Response.succeed(this.appService.findAll());
    }
    
    
	@ApiOperation(value="分页获取应用列表", notes="")
    @GetMapping("/findPage")
    public Response<Page<App>> findPage(int curPage,int pageSize){
    	Pageable pageable = PageRequest.of(curPage, pageSize);
    	Page<App> result = appService.findPage(pageable);
        return Response.succeed(result);
    }
    
	@ApiOperation(value="分页获取应用列表", notes="")
    @GetMapping("/findAppPage")
    public Response<Page<App>> findAppPage(int curPage,int pageSize,int isDeleted){
    	Pageable pageable = PageRequest.of(curPage, pageSize);
        Page<App> result = appService.findAppPage(isDeleted,pageable);
        return Response.succeed(result);
    }
     
    
	@ApiOperation(value="根据应用ID获取应用", notes="")
	@ApiImplicitParam(name = "id", value = "应用Id", required = true, dataType = "Integer")
    @GetMapping("/find")
    public Response<App> findOne(Integer id){
    	if(null == id || id<=0) {
    		return Response.succeed(null,Constants.MISS_MUST_PARAMETER);
    	}
        App app = appService.selectById(id);
        if(null == app) {
        	 return Response.succeed(null);
        }
        return Response.succeed(app);
    }

	@ApiOperation(value="根据应用编号获取应用", notes="")
	@ApiImplicitParam(name = "appCode", value = "应用编号", required = true, dataType = "String")
    @GetMapping("/findByAppCode")
    public Response<App> findByAppCode(String appCode){
        App app = appService.selectByAppCode(appCode);
        if(null == app) {
       	 return Response.succeed(null);
       }
       return Response.succeed(app);
    }
    
    @ApiOperation(value="增加应用", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "appCode", value = "应用编号", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "appName", value = "应用名称", required = true, dataType = "String")
    })
    @PostMapping(value="/add")
    public Response<String> addOne(AppForm appForm){
    	if(StringUtils.isEmpty(appForm.getAppCode()) || StringUtils.isEmpty(appForm.getAppName())) {
    		return Response.fail(Constants.MISS_MUST_PARAMETER);
    	}
    	App app = new App();
    	BeanUtils.copyProperties(appForm, app);
    	app.setGmtCreate(new Date());
    	App savedApp = this.appService.insert(app);
    	if(null == savedApp || savedApp.getId()<=0) {
    		return Response.fail(Constants.MSG_SAVE_FAIL);
    	}
    	return Response.succeed(null,Constants.MSG_SAVE_SUCCESS);
    }

    @ApiOperation(value="更新应用", notes="")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "id", value = "应用Id", required = true, dataType = "Integer"),
    	@ApiImplicitParam(name = "appCode", value = "应用编号", required = true, dataType = "String"),
    	@ApiImplicitParam(name = "appName", value = "应用名称", required = true, dataType = "String")
    })
    @PutMapping("/update")
    public Response<String> updateOne(AppForm appForm){
    	if((appForm.getId()<=0) 
    			|| StringUtils.isEmpty(appForm.getAppCode()) 
    			|| StringUtils.isEmpty(appForm.getAppName())) {
    		return Response.fail(Constants.MISS_MUST_PARAMETER);
    	}
    	int idInt = appForm.getId();
    	App oldObj = this.appService.selectById(idInt);
    	if(null == oldObj) {
    		return Response.fail(Constants.MISS_MUST_PARAMETER);
    	}
    	
    	App updateObj = new App();
    	BeanUtils.copyProperties(oldObj, updateObj);
    	updateObj.setAppCode(appForm.getAppCode());
    	updateObj.setAppName(appForm.getAppName());
    	updateObj.setGmtModified(new Date());
    	
    	appService.updateById(updateObj);
        return Response.succeed(null,Constants.MSG_UPDATE_SUCCESS);
    }

    @ApiOperation(value="删除应用", notes="根据应用ID删除应用")
    @ApiImplicitParam(name = "id", value = "应用Id", required = true, dataType = "Integer")
    @DeleteMapping("/delete")
    public Response<String> deleteOne(Integer id){
    	if(null == id || id<=0) {
    		return Response.fail(Constants.MISS_MUST_PARAMETER);
    	}
    	App app = this.appService.selectById(id);
    	if(null == app) {
    		return Response.fail(Constants.MISS_MUST_PARAMETER);
    	}
        appService.deleteById(id);
        return Response.succeed(null,Constants.MSG_DELETE_SUCCESS);
    }
}
```

### 3.5 在AppForm类中接口上 增加 丰富文档结构 的注解（非必须）
```java
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

```

## 四、测试准备

## 五、测试

* 启动 Application

* 通过浏览器访问 http://localhost:8080/swagger-ui.html


## 常用注解
```java
@Api：用在请求的类上，表示对类的说明
    tags="说明该类的作用，可以在UI界面上看到的注解"
    value="该参数没什么意义，在UI界面上也看到，所以不需要配置"

@ApiOperation：用在请求的方法上，说明方法的用途、作用
    value="说明方法的用途、作用"
    notes="方法的备注说明"

@ApiImplicitParams：用在请求的方法上，表示一组参数说明
    @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面
        name：参数名
        value：参数的汉字说明、解释
        required：参数是否必须传
        paramType：参数放在哪个地方
            · header --> 请求参数的获取：@RequestHeader
            · query --> 请求参数的获取：@RequestParam
            · path（用于restful接口）--> 请求参数的获取：@PathVariable
            · body（不常用）
            · form（不常用）    
        dataType：参数类型，默认String，其它值dataType="Integer"       
        defaultValue：参数的默认值

@ApiResponses：用在请求的方法上，表示一组响应
    @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
        code：数字，例如400
        message：信息，例如"请求参数没填好"
        response：抛出异常的类

@ApiModel：用于响应类上，表示一个返回响应数据的信息
            （这种一般用在post创建的时候，使用@RequestBody这样的场景，
            请求参数无法使用@ApiImplicitParam注解进行描述的时候）
    @ApiModelProperty：用在属性上，描述响应类的属性
```


## 相关参考
* Spring启动RESTful API文档使用Swagger 2  
https://blog.csdn.net/boonya/article/details/60875405

* Spring Boot中使用Swagger2构建强大的RESTful API文档   
http://blog.didispace.com/springbootswagger2/