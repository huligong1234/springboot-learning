package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.entity.App;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface AppDao extends ElasticsearchRepository<App,Integer> {

}
