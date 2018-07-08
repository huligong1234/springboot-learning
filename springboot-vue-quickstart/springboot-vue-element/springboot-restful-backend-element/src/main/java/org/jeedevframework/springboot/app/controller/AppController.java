package org.jeedevframework.springboot.app.controller;

import java.util.Date;
import java.util.List;

import org.jeedevframework.springboot.app.entity.App;
import org.jeedevframework.springboot.app.form.AppForm;
import org.jeedevframework.springboot.app.service.AppService;
import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.common.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {
    
	@Autowired
    AppService appService;

    @GetMapping("/all")
    public Response<List<App>> findAll(){
    	return Response.succeed(this.appService.findAll());
    }
    
    
    @GetMapping("/findPage")
    public Response<Page<App>> findPage(Pageable pageable){
    	Page<App> result = appService.findPage(pageable);
        return Response.succeed(result);
    }
    
    @GetMapping("/findAppPage")
    public Response<Page<App>> findAppPage(Pageable pageable,int isDeleted){
        Page<App> result = appService.findAppPage(isDeleted,pageable);
        return Response.succeed(result);
    }
     
    
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

    @GetMapping("/findByAppCode")
    public Response<App> findByAppCode(String appCode){
        App app = appService.selectByAppCode(appCode);
        if(null == app) {
       	 return Response.succeed(null);
       }
       return Response.succeed(app);
    }
    
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