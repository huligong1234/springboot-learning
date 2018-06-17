package org.jeedevframework.springboot.web;

import java.util.List;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    AppMapper appMapper;

    @GetMapping("/all")
    public List<App> findAll(){
        return appMapper.findAll();
    }

    @GetMapping("/find")
    public App findOne(String id){
        App user = appMapper.findOne(Integer.parseInt(id));
        return user;
    }

    @PostMapping(value="/add")
    public void addOne(App app){
    	appMapper.addOne(app);
    }

    @PutMapping("/update")
    public void updateOne(App app){
        appMapper.updateOne(app);
    }

    @DeleteMapping("/delete")
    public void deleteOne(String id){
        appMapper.deleteOne(Integer.parseInt(id));
    }
}
