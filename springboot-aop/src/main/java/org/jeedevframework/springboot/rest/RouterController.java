package org.jeedevframework.springboot.rest;

import java.util.List;
import java.util.Map;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

@RestController
@RequestMapping("/rest")
public class RouterController {

	@Autowired
    IAppService appService;
	
	@RequestMapping(value="/router/{apiVersion}/{apiCategory}/{apiCode}",method = {RequestMethod.GET,RequestMethod.POST})
    public List<App> router(@RequestBody Map modelMap,
    		@PathVariable(value="apiVersion") String apiVersion,
    		@PathVariable(value="apiCategory") String apiCategory,
    		@PathVariable(value="apiCode") String apiCode){
		EntityWrapper<App> ew = new EntityWrapper<App>();
        return appService.selectList(ew);
    }

}
