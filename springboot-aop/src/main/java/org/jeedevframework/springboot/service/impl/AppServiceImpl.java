package org.jeedevframework.springboot.service.impl;

import org.jeedevframework.springboot.aop.annotation.ServiceLogAnnotation;
import org.jeedevframework.springboot.aop.annotation.TrackTimeAnnotation;
import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.mapper.AppMapper;
import org.jeedevframework.springboot.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

	@Autowired
	private AppMapper appMapper;
	
    @ServiceLogAnnotation(logType = "1",description = "查询记录")
    @TrackTimeAnnotation(description = "查询记录")
	public Page<App> findAppPage(Page<App> page, int isDeleted) {
	    return page.setRecords(appMapper.findAppList(page, isDeleted));
	}
}
