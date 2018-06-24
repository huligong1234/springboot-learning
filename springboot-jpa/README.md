# SpringBoot + Spring Data JPA 学习笔记


本项目示例主要用于快速配置Spring Data JPA 运行环境

### 一、概述
* SpringBoot官网： [http://spring.io/projects/spring-boot](http://spring.io/projects/spring-boot)
* Spring Data JPA官网: http://projects.spring.io/spring-data-jpa/


### 二、依赖版本信息
通过`starter`创建项目，选择Web,MySQL,JDBC,JPA

* JDK1.8
* MySQL 5.7.21
* SpringBoot:2.0.3.RELEASE

### 三、配置和实现

#### 3.1 初始化测试用的数据库及表
/document/init_db.sql  

```sql
--创建数据库
CREATE DATABASE IF NOT EXISTS jeedev DEFAULT CHARSET utf8 COLLATE utf8_general_ci;

--创建数据表
DROP TABLE IF EXISTS app;
CREATE TABLE `app` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NULL COMMENT '更新时间',
  `is_deleted` tinyint unsigned NOT NULL DEFAULT 0 COMMENT '是否逻辑删除,默认否',
  `re_order` int(11) unsigned NOT NULL DEFAULT 0 COMMENT '排序值',
  `app_code` varchar(32) NOT NULL COMMENT '应用编号',
  `app_name` varchar(255) NOT NULL COMMENT '应用名称',
  PRIMARY KEY (`id`),
  KEY `uk_app_code` (`app_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用表';
```

#### 3.2 配置数据库连接等信息application.properties

```properties
server.port=8080

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jeedev?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jackson.serialization.indent-output=true
```

#### 3.3 创建实体类App
略


#### 3.5 创建AppDao接口

Spring Data JPA 封装了通用的CRUD及分页查询实现，所以只需要自定义特殊需求查询方法即可。
如果自定义查询方法遵循JPA约定的规则，也无需自己实现。
自定义的简单查询就是根据方法名来自动生成SQL，主要的语法是findXXBy,readAXXBy,queryXXBy,countXXBy。
例如：希望根据应用编号appCode查询记录，方法名遵循这样的规章就好：
findByAppCode 或 findByAppCodeEquals 或 findAppByAppCodeEquals 或者 read,query等开头。


```java
package org.jeedevframework.springboot.dao;

import org.jeedevframework.springboot.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppDao extends JpaRepository<App, Integer>, JpaSpecificationExecutor<App> {

	App findAppByAppCodeEquals(String appCode);
}

```



### 3.6 创建Service类

略

#### 3.7 创建Controller类

略 


#### 3.8 增加配置文件支持跨域调用

```java
package org.jeedevframework.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CustomCORSConfiguration {
	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.setAllowCredentials(true);
		return corsConfiguration;
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}
}
```

###  四、测试准备



### 五、测试
* 启动 Application

* 通过`curl` 测试 增加记录

```shell
curl -X POST http://localhost:8080/app/add -d "appCode=10001&appName=Temple Run"

curl -X POST http://localhost:8080/app/add -d "appCode=10002&appName=Angry Birds"
```

* 通过`curl` 测试 查询记录

```shell
curl -X GET http://localhost:8080/app/all

curl -X GET http://localhost:8080/app/find?id=1

curl -X GET http://localhost:8080/app/findByAppCode?appCode=10001
```

* 通过`curl` 测试 分页查询记录

```shell
curl -X GET "http://localhost:8080/app/findPage?curPage=1&pageSize=10"

curl -X GET "http://localhost:8080/app/findAppPage?curPage=1&pageSize=10&isDeleted=1"
```

* 通过 `curl` 测试 更新记录

```shell
curl -X PUT http://localhost:8080/app/update -d "id=1&appName=Temple Run 2"
```

* 通过`curl` 测试 删除记录

```shell
curl -X DELETE http://localhost:8080/app/delete?id=1
```







### 七、更多参考

* Spring Data JPA官网文档 https://docs.spring.io/spring-data/jpa/docs/2.1.0.M3/reference/html/

* Spring Data JPA 参考指南  
https://legacy.gitbook.com/book/ityouknow/spring-data-jpa-reference-documentation/details

* Spring boot jpa 多表关联查询  
https://blog.csdn.net/qq_34117825/article/details/71123876

* spring boot(五)：spring data jpa的使用  
https://www.cnblogs.com/ityouknow/p/5891443.html

* SpringBoot第四讲扩展和封装Spring Data JPA(一)_自定义Repository和创建自己的BaseRepository  
https://www.jianshu.com/p/73f48095a7bf

* 第十三章：SpringBoot实战SpringDataJPA
https://www.jianshu.com/p/9d5bf0e4943f

* 第四章：使用QueryDSL与SpringDataJPA实现多表关联查询
https://www.jianshu.com/p/6199e76a5485

* Spring boot data JPA数据库映射关系 : @OneToOne,@OneToMany,@ManyToMany
https://blog.csdn.net/lyg_2012/article/details/70195062

* Spring Data JPA 实现多表关联查询
https://blog.csdn.net/LIU_YANZHAO/article/details/79796684