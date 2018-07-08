package org.jeedevframework.springboot;

import java.util.Iterator;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.service.AppService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.GetQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ElasticSearchTest {
	
	@Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
	
	
	@Autowired
	private AppService appService;
	
	@Test
	public void testTepmplate() {
		Assert.notNull(elasticsearchTemplate,"elasticsearchTemplate can't null");
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.termQuery("appName","mygame"));
        searchQuery.withIndices("jeedev").withQuery(bqb);
        List<App> list = elasticsearchTemplate.queryForList(searchQuery.build(), App.class);
        for(int i=0;i<list.size();i++){
        	App app = list.get(i);
            System.out.println(app.getGmtCreate()+"\t"+app.getAppCode()+"\t"+app.getAppName());
        }
	}
	
	@Test
	public void queryForObject() {
        GetQuery query = new GetQuery();
        query.setId("1");// 索引的id
        App app = elasticsearchTemplate.queryForObject(query, App.class);
        System.out.println(app.getGmtCreate());
        System.out.println(app.getAppCode());
        System.out.println(app.getAppName());// 实体的id
	}
	
	@Test
	@Ignore
	public void testAdd(){
		App app = new App();
		app.setId(10001);
		app.setGmtCreate(new java.util.Date());
		app.setAppCode("10001");
		app.setAppName("myapp");
		this.appService.insert(app);
	}
	
	@Test
	@Ignore
	public void testAll(){
		Iterable<App> appList  = this.appService.findAll();
		Iterator<App> iter = appList.iterator();
		while(iter.hasNext()) { 
			  System.out.println(iter.next());
			} 
	}
	
	@Test
	@Ignore
	public void testFindAllPage(){
		Pageable pageable = PageRequest.of(0, 10);
		Iterable<App> appList  = this.appService.findPage(pageable);
		Iterator<App> iter = appList.iterator();
		while(iter.hasNext()) { 
			System.out.println(iter.next());
		} 
	}
	
	@Test
	@Ignore
	public void testFindAllPage2(){
		Pageable pageable = PageRequest.of(0, 10);
		String searchContent = "my";
		Iterable<App> appList  = this.appService.findPage(pageable,searchContent);
		Iterator<App> iter = appList.iterator();
		while(iter.hasNext()) { 
			System.out.println(iter.next());
		} 
	}
}
