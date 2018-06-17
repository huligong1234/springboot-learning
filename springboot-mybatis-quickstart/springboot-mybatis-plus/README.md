
## Spring Boot + MyBatis Plus 示例学习

### 一、概述
* SpringBoot官网： [http://spring.io/projects/spring-boot](http://spring.io/projects/spring-boot)
* MyBatis官网: [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
* MyBatis-Plus 官网：[http://mp.baomidou.com/](http://mp.baomidou.com/)
`Mybatis-Plus`（简称`MP`）是一个 `Mybatis` 的增强工具，在 `Mybatis` 的基础上只做增强不做改变，为简化开发、提高效率而生。

备注：MyBatis-Plus 有比 Mybatis 官方的 Generator 更加强大的代码生成插件。采用代码或者 Maven 插件可快速生成 Mapper 、 Model 、 Service 、 Controller 层代码，支持模板引擎，更有超多自定义配置。

### 二、依赖版本信息
通过`starter`创建项目，选择Web,MySQL,JDBC,MyBatis

* JDK1.8
* MySQL 5.7.21
* SpringBoot:2.1.0.BUILD-SNAPSHOT
* MyBatis:1.3.2

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

#### 3.2 配置数据库连接等信息application.yml

```yaml
server:
  port: 8080
  
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/jeedev?useUnicode=true&characterEncoding=utf8&useSSL=false
    username: root
    password: 123456
    driver-class-name: com.mysql.jdbc.Driver

mybatis:
  config-location: classpath:/mybatis/mybatis-config.xml

mybatis-plus:
  # 如果是放在resource目录 classpath:/mybatis/mapper/*Mapper.xml
  mapper-locations: classpath:/mybatis/mapper/*Mapper.xml
  #实体扫描，多个package用逗号或者分号分隔
  typeAliasesPackage: org.jeedevframework.springboot.entity
  global-config:
    #主键类型  0:"数据库ID自增", 1:"用户输入ID",2:"全局唯一ID (数字类型唯一ID)", 3:"全局唯一ID UUID";
    id-type: 3
    #字段策略 0:"忽略判断",1:"非 NULL 判断"),2:"非空判断"
    field-strategy: 2
    #驼峰下划线转换
    db-column-underline: true
    #mp2.3+ 全局表前缀 mp_
    #table-prefix: mp_
    #刷新mapper 调试神器
    #refresh-mapper: true
    #数据库大写下划线转换
    #capital-mode: true

    #逻辑删除配置（下面3个配置）
    logic-delete-value: 1
    logic-not-delete-value: 0
    sql-injector: com.baomidou.mybatisplus.mapper.LogicSqlInjector
    #自定义填充策略接口实现
    #meta-object-handler: com.baomidou.springboot.MyMetaObjectHandler
  configuration:
    #配置返回数据库(column下划线命名&&返回java实体是驼峰命名)，自动匹配无需as（没开启这个，SQL需要写as： select user_id as userId） 
    map-underscore-to-camel-case: true
    cache-enabled: false
    #配置JdbcTypeForNull, oracle数据库必须配置
    jdbc-type-for-null: 'null'
```

#### 3.3 创建实体类App

```java
package org.jeedevframework.springboot.entity;

import java.util.Date;

public class App {
	private int id;
	private Date gmtCreate;
	private Date gmtModified;
	private int isDeleted = 0;
	private int reOrder = 0;
	private String appCode;
	private String appName;
	
	//setter,getter 略	
}

```

#### 3.4 增加MybatisPlusConfig类
```java
package org.jeedevframework.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

@EnableTransactionManagement
@Configuration
@MapperScan("org.jeedevframework.springboot.mapper*")
public class MybatisPlusConfig {
   /*
    * 分页插件，自动识别数据库类型
    * 多租户，请参考官网【插件扩展】
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
      return new PaginationInterceptor();
   }
}
```

#### 3.5 创建Mapper接口

MyBatis Plus 封装了通用的CRUD实现，所以只需要自定义特殊需求查询方法即可

```java
package org.jeedevframework.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.jeedevframework.springboot.entity.App;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

@Mapper
public interface AppMapper extends BaseMapper<App> { 
    List<App> findAppList(Pagination page, Integer isDeleted);
}
```

#### 3.6 新增IAppService接口
```java
package org.jeedevframework.springboot.service;

import org.jeedevframework.springboot.entity.App;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

public interface IAppService extends IService<App>  {
	public Page<App> findAppPage(Page<App> page, int isDeleted);
}

```

#### 3.7 增加AppServiceImpl类
```java 
package org.jeedevframework.springboot.service.impl;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.mapper.AppMapper;
import org.jeedevframework.springboot.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements IAppService {

	@Autowired
	private AppMapper appMapper;
	
	public Page<App> findAppPage(Page<App> page, int isDeleted) {
	    return page.setRecords(appMapper.findAppList(page, isDeleted));
	}
}

```

#### 3.8 增加和配置MyBatis相关配置文件

* mybatis/mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<typeAliases>
		<typeAlias alias="Integer" type="java.lang.Integer" />
		<typeAlias alias="Long" type="java.lang.Long" />
		<typeAlias alias="HashMap" type="java.util.HashMap" />
		<typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
		<typeAlias alias="ArrayList" type="java.util.ArrayList" />
		<typeAlias alias="LinkedList" type="java.util.LinkedList" />
	</typeAliases>
</configuration>
```

* mybatis/mapper/AppMapper.xml

  因为使用了MyBatis-Plus 的通用CRUD，所以仅定义特殊的查询实现

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="org.jeedevframework.springboot.mapper.AppMapper">
  
	<select id="findAppList" resultType="App">
		SELECT * FROM app where is_deleted=#{isDeleted}
	</select>
</mapper>
```

#### 3.9 创建Controller类,定义Web接口访问地址

增加两个针对分页查询示例接口

```java
package org.jeedevframework.springboot.web;

import java.util.Date;
import java.util.List;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.service.IAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
    IAppService appService;

    @GetMapping("/all")
    public List<App> findAll(){
    	EntityWrapper<App> ew = new EntityWrapper<App>();
        return appService.selectList(ew);
    }
    
    @GetMapping("/findPage")
    public List<App> findAppList(int curPage,int pageSize){
    	Page<App> page = new Page<App>(curPage, pageSize);
        return appService.selectPage(page).getRecords();
    }
    
    @GetMapping("/findAppPage")
    public List<App> findAppList(int curPage,int pageSize,int isDeleted){
    	Page<App> page = new Page<App>(curPage, pageSize);
        return appService.findAppPage(page, isDeleted).getRecords();
    }

    @GetMapping("/find")
    public App findOne(String id){
        App user = appService.selectById(Integer.parseInt(id));
        return user;
    }

    @PostMapping(value="/add")
    public void addOne(App app){
    	app.setGmtCreate(new Date());
    	appService.insert(app);
    }

    @PutMapping("/update")
    public void updateOne(App app){
    	app.setGmtModified(new Date());
        appService.updateById(app);
    }

    @DeleteMapping("/delete")
    public void deleteOne(String id){
        appService.deleteById(Integer.parseInt(id));
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