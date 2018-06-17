
## Spring Boot + MyBatis XML方式 示例学习

### 一、概述
* SpringBoot官网： [http://spring.io/projects/spring-boot](http://spring.io/projects/spring-boot)
* MyBatis官网: [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)

备注：通过mybatis generator插件可以自动生成Mapper接口及相关xml文件，但本例不包含相关配置和使用说明。

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

#### 3.2 配置数据库连接等信息application.properties

```properties
server.port=8080
spring.datasource.url=jdbc:mysql://localhost:3306/jeedev?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver

mybatis.config-location=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml
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

#### 3.4 创建Mapper接口

```java
package org.jeedevframework.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.jeedevframework.springboot.entity.App;

@Mapper
public interface AppMapper {

	public List<App> findAll();
	public App findOne(int id);
	public void addOne(App app);
	public void updateOne(App app);
	public void deleteOne(int id);
}
```

#### 3.5 增加和配置MyBatis相关配置文件

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

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
  PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="org.jeedevframework.springboot.mapper.AppMapper">

	 <resultMap id="BaseResultMap" type="org.jeedevframework.springboot.entity.App" >
        <id column="id" property="id" jdbcType="INTEGER" />
        <result column="gmt_create" property="gmtCreate" jdbcType="TIMESTAMP" />
        <result column="gmt_modified" property="gmtModified" jdbcType="TIMESTAMP"/>
        <result column="is_deleted" property="isDeleted" jdbcType="INTEGER"/>
        <result column="re_order" property="reOrder" jdbcType="INTEGER"/>
        <result column="app_code" property="appCode" jdbcType="VARCHAR"/>
        <result column="app_namne" property="appName" jdbcType="VARCHAR"/>
    </resultMap>
    
	<select id="findAll" resultType="App">
		SELECT * FROM app
	</select>

	<select id="findOne" parameterType="Integer" resultType="App">
		SELECT *
		FROM app WHERE id=#{id}
	</select>


	<insert id="addOne" useGeneratedKeys="true" keyProperty="id">
		INSERT INTO app(gmt_create,app_code,app_name) VALUES
		(NOW(),#{appCode},#{appName})
	</insert>

	<update id="updateOne">
		UPDATE app SET app_name=#{appName},gmt_modified=NOW()
		WHERE id=#{id}
	</update>

	<delete id="deleteOne">
		DELETE FROM app WHERE id = #{id}
	</delete>
</mapper>
```



#### 3.6 创建Controller类,定义Web接口访问地址

```java
package org.jeedevframework.springboot.web;

import java.util.List;

import org.jeedevframework.springboot.entity.App;
import org.jeedevframework.springboot.mapper.AppMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
    AppMapper appMapper;

    @GetMapping("/all")
    public List<App> findAll(){
        return appMapper.findAll();
    }

    @GetMapping("/find")
    public App findOne(String id){
        App user = appMapper.findOne(Integer.parseInt(id));
        return user;
    }

    @PostMapping(value="/add")
    public void addOne(App app){
    	appMapper.addOne(app);
    }

    @PutMapping("/update")
    public void updateOne(App app){
        appMapper.updateOne(app);
    }

    @DeleteMapping("/delete")
    public void deleteOne(String id){
        appMapper.deleteOne(Integer.parseInt(id));
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

* 通过 `curl` 测试 更新记录

```shell
curl -X PUT http://localhost:8080/app/update -d "id=1&appName=Temple Run 2"
```

* 通过`curl` 测试 删除记录

```shell
curl -X DELETE http://localhost:8080/app/delete?id=1
```