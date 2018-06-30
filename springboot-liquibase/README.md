# SpringBoot + Liquibase 学习笔记

## 一、概述 

目前 Spring Boot 支持较好的两款工具分别是 flyway、liquibase，支持 sql script，在初始化数据源之后执行指定的脚本代码或者脚本文件，本章基于 Liquibase…
Liquibase

LiquiBase 是一个用于数据库重构和迁移的开源工具，通过 changelog文件 的形式记录数据库的变更，然后执行 changelog文件 中的修改，将数据库更新或回滚到一致的状态。

    主要特点
    
    支持几乎所有主流的数据库，如MySQL、PostgreSQL、Oracle、Sql Server、DB2等
    支持多开发者的协作维护；
    日志文件支持多种格式；如XML、YAML、SON、SQL等
    支持多种运行方式；如命令行、Spring 集成、Maven 插件、Gradle 插件等

在平时开发中，无可避免测试库增加字段或者修改字段以及创建表之类的，环境切换的时候如果忘记修改数据库那么肯定会出现 不可描述的事情 ，这个时候不妨考虑考虑Liquibase。

官方文档：http://www.liquibase.org/documentation/index.html

## 二、依赖版本信息
spring-boot-starter-parent: 2.1.0.BUILD-SNAPSHOT
liquibase-core: 3.6.1

## 三、配置和实现

### 3.1 pom.xml增加依赖
通过springboot starter 选择mysql,jdbc,liquiebase

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
	<groupId>org.liquibase</groupId>
	<artifactId>liquibase-core</artifactId>
</dependency>

<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<scope>runtime</scope>
</dependency>
```

### 3.2 Liquibase配置application.properties

```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/jeedev?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
spring.liquibase.user=root
spring.liquibase.password:123456
spring.liquibase.enabled=true
spring.liquibase.drop-first=false
#spring.liquibase.contexts=dev,test,prod
spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.yaml
```

### 3.3 创建db.changelog-master.yaml

位置：/src/main/resource//db/changelog/db.changelog-master.yaml

```yaml
databaseChangeLog:
   - includeAll:
       path: classpath*:db/changelog/sqlfile/
```



也可以具体逐条指定：

```
databaseChangeLog:
  # 支持 yaml 格式的 SQL 语法
  # 同时也支持依赖外部SQL文件
  - changeSet:
      id: 1
      author: Jack
      changes:
        - sqlFile:
            encoding: utf8
            path: classpath:db/changelog/sqlfile/2018063014.sql
  - changeSet:
      id: 2
      author: Jack
      changes:
        - sqlFile:
            encoding: utf8
            path: classpath:db/changelog/sqlfile/2018063015.sql
```

### 3.4 创建数据库变更记录示例

```sql
# src\main\resources\db\changelog\sqlfile\2018063014.sql
INSERT INTO `app` (`id`, `gmt_create`, `gmt_modified`, `is_deleted`, `re_order`, `app_code`, `app_name`) 
VALUES ('1', now(), NULL, '0', '0', '100200300', 'from liquibase');


# src\main\resources\db\changelog\sqlfile\2018063015.sql
INSERT INTO `app` (`id`, `gmt_create`, `gmt_modified`, `is_deleted`, `re_order`, `app_code`, `app_name`) 
VALUES ('2', now(), NULL, '0', '0', '100200301', 'from liquibase');

# src\main\resources\db\changelog\sqlfile\2018063016.sql
ALTER TABLE `app`
ADD COLUMN `memo`  varchar(255) NULL COMMENT '备注' AFTER `app_name`;
```



## 四、运行

* 运行App即可 

  sql内容即会更新执行到数据库，且会记录下 变更记录

  数据库会创建两张表：databasechangelog、databasechangeloglock



## 五、参考资料

* 一起来学SpringBoot | 第二十四篇：数据库管理与迁移（Liquibase）  
  http://blog.battcn.com/2018/06/20/springboot/v2-other-liquibase/
* http://www.liquibase.org/quickstart.html
* http://www.liquibase.org/documentation/spring.html
* https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples/spring-boot-sample-liquibase
* https://medium.com/@harittweets/evolving-your-database-using-spring-boot-and-liquibase-844fcd7931da
* https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html