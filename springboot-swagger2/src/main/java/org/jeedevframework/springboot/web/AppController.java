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