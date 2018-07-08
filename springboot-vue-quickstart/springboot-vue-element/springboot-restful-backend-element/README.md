
## Spring Boot + Vue 示例学习

本项目示例主要用于提供vue-element-admin项目的后端服务接口

### 一、概述
* SpringBoot官网： [http://spring.io/projects/spring-boot](http://spring.io/projects/spring-boot)
* Spring Data JPA官网: http://projects.spring.io/spring-data-jpa/

### 二、依赖版本信息
通过`starter`创建项目，选择Web,MySQL,JPA

* JDK1.8
* MySQL 5.7.21
* SpringBoot:2.0.3.RELEASE
* spring-data-jpa:2.0.8.RELEASE

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
server.port=8088

spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jeedev?useUnicode=true&characterEncoding=utf8&useSSL=false
spring.datasource.username=root
spring.datasource.password=123456

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jackson.serialization.indent-output=true
```

#### 其他略

可参考这个示例：  
https://github.com/huligong1234/springboot-learning/tree/master/springboot-jpa