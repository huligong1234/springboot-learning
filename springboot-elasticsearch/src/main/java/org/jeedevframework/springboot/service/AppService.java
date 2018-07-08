package org.jeedevframework.springboot.service;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.jeedevframework.springboot.dao.AppDao;
import org.jeedevframework.springboot.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class AppService {

	@Autowired
	private AppDao appDao;
	
	public Iterable<App> findAll() {
		return this.appDao.findAll();
	}
	
	public Page<App> findPage(Pageable pageable){
		Page<App> page = this.appDao.findAll(pageable);;
        return page;
	}
	
	public Page<App> findPage(Pageable pageable,String searchContent){
		 SearchQuery searchQuery = getEntitySearchQuery(pageable,searchContent);
	     Page<App> appPage = appDao.search(searchQuery);
	     return appPage;
	}
	
	
	public App insert(App app) {
		return this.appDao.save(app);
	}

	public App selectById(Integer parseInt) {
		return this.appDao.findById(parseInt).orElse(null);
	}

	public App updateById(App app) {
		return this.appDao.save(app);
	}

	public void deleteById(int parseInt) {
		App app = new App();
		app.setId(parseInt);
		this.appDao.delete(app);
	}
	
	
	private SearchQuery getEntitySearchQuery(Pageable pageable, String searchContent) {
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder);
        if(StringUtils.isNotBlank(searchContent)) {
            //queryBuilder.should(QueryBuilders.matchPhraseQuery("appCode", searchContent));
            //queryBuilder.should(QueryBuilders.matchPhraseQuery("appName", searchContent));
            
            queryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("appCode", searchContent));
            queryBuilder.should(QueryBuilders.matchPhrasePrefixQuery("appName", searchContent));
        }
        
        //设置权重分 求和模式
        //functionScoreQueryBuilder.scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM);
        //设置权重分最低分
        //functionScoreQueryBuilder.setMinScore(10);

        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }
	
}
