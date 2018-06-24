package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends JpaRepository<App, Integer>, JpaSpecificationExecutor<App> {

	App findByAppCode(String appCode);
}
