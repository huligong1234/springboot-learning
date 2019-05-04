
## Spring Boot + AOP 示例学习

### 一、概述

基于Spring AOP 实现 日志记录、方法执行耗时、权限验证、鉴权、验签名 参考示例

* 实现AOP的切面主要有以下几个要素：

```
    使用@Aspect注解将一个java类定义为切面类
    使用@Pointcut定义一个切入点，可以是一个规则表达式，也可以是一个注解等。
    根据需要在切入点不同位置的切入内容
        使用@Before在切入点开始处切入内容
        使用@After在切入点结尾处切入内容
        使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
        使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
        使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
```

     
* SpringBoot AOP默认配置

```
spring.aop.auto=true # Add @EnableAspectJAutoProxy.
spring.aop.proxy-target-class=true # Whether subclass-based (CGLIB) proxies are to be created (true), as opposed to standard Java interface-based proxies (false).

```

* 更多相关参考

```
Spring Boot中使用AOP统一处理Web请求日志 
http://blog.didispace.com/springbootaoplog/?spm=a2c4e.11153940.blogcont576452.14.3ec0161dL6ZHcW


【spring-boot】spring aop 面向切面编程初接触
http://www.cnblogs.com/lic309/p/4079194.html

Spring AOP 所有切入点指示符详解(execution,within,this,target,args,@within,@target,@args,@annotation)
https://blog.csdn.net/qq_23167527/article/details/78623639
```

### 二、依赖版本信息

	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

### 三、配置和实现



###  四、测试准备


### 五、测试
* 启动 Application

* 测试

```shell
curl -H 'Content-Type: application/json;charset=utf-8' -XPOST 'http://localhost:8080/rest/router/v1/app/app_info' -d '{"appId":"abc123","appCode":"10002","appName":"Angry Birds"}'
```