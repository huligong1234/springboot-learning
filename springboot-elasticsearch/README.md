SpringBoot + Spring Data ElasticSearch 学习笔记

## 一、概述

Spring Data ElasticSearch官网：

 https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/index.html



https://github.com/elastic/elasticsearch



```
elasticsearch环境准备：

通过官网下载elasticsearch,https://www.elastic.co/cn/downloads/elasticsearch
下载到本地解压后，运行elasticsearch.bat 即可
```

## 二、依赖版本信息

* spring-boot-starter-parent:2.0.3.RELEASE
* spring-data-elasticSearch:3.0.8.RELEASE
* elasticsearch-rest-client:5.6.10




## 三、配置和实现

### pom.xml增加依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

### ElasticSearch连接配置application.properties

```propterties

server.port=8080

spring.data.elasticsearch.cluster-name=elasticsearch
spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300

```


### AppDao继承ElasticsearchRepository
```java
package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.entity.App;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

@Component
public interface AppDao extends ElasticsearchRepository<App,Integer> {

}
```

### AppService
```java
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

```

## 三、单元测试
```java
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

```



## 四、参考资料

* Elasticsearch 和插件 elasticsearch-head 安装详解
https://www.bysocket.com/?p=1744

* Elasticsearch 默认配置 IK 及 Java AnalyzeRequestBuilder 使用
https://www.bysocket.com/?p=1798

* [增删改查] SpringBoot 整合 ElasticSearch 之 ElasticsearchRepository 的 CRUD、分页接口
https://blog.csdn.net/larger5/article/details/79777319

* spring boot2 + mybatis + elasticsearch6.2.4 + mysql 整合（2）——ElasticSearch初尝
https://blog.csdn.net/m0_38084243/article/details/80589761
