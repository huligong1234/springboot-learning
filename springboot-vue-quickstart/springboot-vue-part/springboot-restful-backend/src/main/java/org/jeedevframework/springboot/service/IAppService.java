package org.jeedevframework.springboot.service;

import org.jeedevframework.springboot.entity.App;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

public interface IAppService extends IService<App>  {

	public Page<App> findAppPage(Page<App> page, int isDeleted);
}
