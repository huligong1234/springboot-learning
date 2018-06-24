package org.jeedevframework.springboot.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.jeedevframework.springboot.dao.AppDao;
import org.jeedevframework.springboot.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional()
public class AppService {

	
	@Autowired
	private AppDao appDao;
	
	public List<App> findAll() {
		return this.appDao.findAll();
	}
	
	public Page<App> findPage(Pageable pageable){
		Page<App> page = this.appDao.findAll(pageable);;
        return page;
	}
	
	public Page<App> findAppPage(int isDeleted,Pageable pageable){
        Page<App> page = appDao.findAll((root, cq, cb) -> {
        	
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), isDeleted));
            
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return page;
	}
	
	
	public App insert(App app) {
		return this.appDao.save(app);
	}

	public App selectById(int parseInt) {
		return this.appDao.findById(parseInt).orElse(null);
	}

	public App selectByAppCode(String appCode) {
		return this.appDao.findByAppCode(appCode);
	}
	
	public App updateById(App app) {
		return this.appDao.save(app);
	}

	public void deleteById(int parseInt) {
		App app = new App();
		app.setId(parseInt);
		this.appDao.delete(app);
	}

	/**
	 * 本方法主要用于Mockito测试，无具体实现
	 * */
	public App findOne(int id) {
		return null;
	}
	
	
}
