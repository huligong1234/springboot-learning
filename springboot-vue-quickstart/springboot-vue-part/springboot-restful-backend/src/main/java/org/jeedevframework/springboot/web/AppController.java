package org.jeedevframework.springboot.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
    IAppService appService;

    @GetMapping("/all")
    public List<App> findAll(){
    	EntityWrapper<App> ew = new EntityWrapper<App>();
        return appService.selectList(ew);
    }
    
    @GetMapping("/findPage")
    public List<App> findAppList(int curPage,int pageSize){
    	Page<App> page = new Page<App>(curPage, pageSize);
        return appService.selectPage(page).getRecords();
    }
    
    @GetMapping("/findAppPage")
    public List<App> findAppList(int curPage,int pageSize,int isDeleted){
    	Page<App> page = new Page<App>(curPage, pageSize);
        return appService.findAppPage(page, isDeleted).getRecords();
    }

    @GetMapping("/find")
    public App findOne(String id){
        App user = appService.selectById(Integer.parseInt(id));
        return user;
    }

    @PostMapping(value="/add")
    public void addOne(App app){
    	app.setGmtCreate(new Date());
    	appService.insert(app);
    }

    @PutMapping("/update")
    public void updateOne(App app){
    	app.setGmtModified(new Date());
        appService.updateById(app);
    }

    @DeleteMapping("/delete")
    public void deleteOne(HttpServletRequest request,String id){
        appService.deleteById(Integer.parseInt(id));
    }
}
