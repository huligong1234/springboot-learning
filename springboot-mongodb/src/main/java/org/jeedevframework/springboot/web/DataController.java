package org.jeedevframework.springboot.web;

import java.util.List;

import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.common.Response;
import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.entity.DataEntity;
import org.jeedevframework.springboot.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    
	
	@Autowired
    DataService dataService;

    @GetMapping("/all")
    public Response<List<App>> findAll(){
    	//return Response.succeed(this.dataService.find(entity, query));
    	return null;
    }
    
    
    @GetMapping("/findReport")
    public Response findReport(){
    	return Response.succeed(this.dataService.findReport());
    }
    
    @PostMapping(value="/add")
    public Response<String> addOne(DataEntity dataEntity){
    	dataEntity.setSn("A000001");
    	dataEntity.setModelCode("phase");
    	
    	dataEntity.setPropValue("phaseCode", "P0001");
    	dataEntity.setPropValue("phaseName", "一期");
    	this.dataService.create(dataEntity);
    	return Response.succeed(null,Constants.MSG_SAVE_SUCCESS);
    }

  
    @PutMapping("/update")
    public Response<String> updateOne(DataEntity dataEntity){
    	
        return Response.succeed(null,Constants.MSG_UPDATE_SUCCESS);
    }

    
    @DeleteMapping("/delete")
    public Response<String> deleteOne(Integer id){
        return Response.succeed(null,Constants.MSG_DELETE_SUCCESS);
    }
}