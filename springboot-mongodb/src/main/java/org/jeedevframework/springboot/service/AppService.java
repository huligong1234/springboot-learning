package org.jeedevframework.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.jeedevframework.springboot.dao.AppDao;
import org.jeedevframework.springboot.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.mongodb.client.result.UpdateResult;

@Service
public class AppService {

	@Autowired
	private AppDao appDao;
	
	@Autowired
    private MongoTemplate mongoTemplate;

	
	public List<App> findAll() {
		return this.appDao.findAll();
	}
	
	public Page<App> findPage(Pageable pageable){
		Page<App> page = this.appDao.findAll(pageable);;
        return page;
	}
	
	/*public Page<App> findAppPage(int isDeleted,Pageable pageable){
        Page<App> page = appDao.findAll((root, cq, cb) -> {
        	
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isDeleted"), isDeleted));
            
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return page;
	}
	*/
	
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
		/*
		Query query = new Query();
        //Criteria criteria = new Criteria();
        query.addCriteria(Criteria.where("_id").is(app.getId()));
        String collectionsName = "app";
        Update update = new Update();
        update.set("_id",app.getId());
        update.set("appName", app.getAppName());
        UpdateResult result = mongoTemplate.updateFirst(query, update, collectionsName);
        */
        return this.appDao.save(app);
	}

	public void deleteById(int parseInt) {
		App app = new App();
		app.setId(parseInt);
		this.appDao.delete(app);
	}
	
	
}
