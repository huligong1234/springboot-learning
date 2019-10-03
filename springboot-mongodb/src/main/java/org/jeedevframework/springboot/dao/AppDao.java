package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.entity.App;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends MongoRepository<App, Integer> {
	App findByAppCode(String appCode);
}
