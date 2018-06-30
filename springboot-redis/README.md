SpringBoot + Redis 学习笔记

## 一、概述

spring-data-redis官网： https://projects.spring.io/spring-data-redis/

Redis官网：https://redis.io/



## 二、依赖版本信息

spring-boot-starter-parent:2.0.3.RELEASE
spring-data-redis:2.0.5.RELEASE
fastjson:1.2.47
commons-pool2



```

Lettuce

`Lettuce` 和` Jedis` 的都是连接Redis Server的客户端程序。Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。

spring-data-redis 2.0以后默认使用基于Letture的实现。

```



## 三、配置和实现

### pom.xml增加依赖

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-redis</artifactId>
	<exclusions>
		<exclusion>
			<groupId>org.springframework.data</groupId>
			<artifactId>spring-data-redis</artifactId>
		</exclusion>
	</exclusions>
</dependency>
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-pool2</artifactId>
</dependency>
<dependency>
		<groupId>org.springframework.data</groupId>
		<artifactId>spring-data-redis</artifactId>
		<version>2.0.5.RELEASE</version>
</dependency>
```

### Redis连接配置application.properties

```propterties

spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=

# 连接超时时间（毫秒）
spring.redis.timeout=10000
# Redis默认情况下有16个分片，这里配置具体使用的分片，默认是0
spring.redis.database=0
# 连接池最大连接数（使用负值表示没有限制） 默认 8
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制） 默认 -1
spring.redis.lettuce.pool.max-wait=-1
# 连接池中的最大空闲连接 默认 8
spring.redis.lettuce.pool.max-idle=8
# 连接池中的最小空闲连接 默认 0
spring.redis.lettuce.pool.min-idle=0

```

### 增加配置RedisConfig
```java
package org.jeedevframework.springboot.config;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;

@EnableCaching
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
@EnableTransactionManagement
public class RedisConfig extends CachingConfigurerSupport {
	
	@Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory redisConnectionFactory) {
        RedisTemplate template = new RedisTemplate<>();
       
        //使用fastjson序列化
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        
        RedisSerializer stringSerializer = new StringRedisSerializer();
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);
 
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
```

### 封装调用RedisCacheService
```java
package org.jeedevframework.springboot.cache.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class RedisCacheService {
	
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    public Boolean hasCache(String key){
        return redisTemplate.hasKey(key);
    }
    
    /**
     * @param key
     * @param value 字符串类型值
     */
    public void setCache(String key,String value){
    	stringRedisTemplate.opsForValue().set(key,value);
    }
    
    /**
     * @param key
     * @param value 对象类型值
     */
    public void setObjectCache(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }
    
    public Object getObjectCache(String key){
        return redisTemplate.opsForValue().get(key);
    }
    
    public long incrCache(String key,long value){
        return redisTemplate.opsForValue().increment(key, value);
    }
    
    public double incrCache(String key,double value){
        return redisTemplate.opsForValue().increment(key, value);
    }
    
    /**
     * @param key
     * @param value
     * @param timeout 失效时间，单位毫秒
     */
    public void setCache(String key,String value,long timeout){
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set(key,value,timeout, TimeUnit.MILLISECONDS);
    }
    
    /**
     * @param key
     * @param value
     * @param timeout 失效时间，单位毫秒
     * @param unit 时间单位 TimeUnit
     */
    public void setCache(String key,String value,long timeout, TimeUnit unit){
        stringRedisTemplate.opsForValue().set(key,value,timeout, unit);
    }
    
    public String getCache(String key){
        return this.stringRedisTemplate.opsForValue().get(key);
    }
    
    public Boolean deleteCache(String key){
        return this.redisTemplate.delete(key);
    }

}

```

## 三、单元测试
```java
package org.jeedevframework.springboot.cache;

import java.util.concurrent.TimeUnit;

import org.jeedevframework.springboot.cache.impl.RedisCacheService;
import org.jeedevframework.springboot.entity.App;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRedisCacheService {

    
    @Autowired
    private RedisCacheService cacheService;

    @Test
    public void testSetCache() {
        cacheService.setCache("app1000", "1000-myGame");
        String value = cacheService.getCache("app1000");
        Assert.assertTrue("1000-myGame".equals(value));
    }
    
    @Test
    public void testSetCacheExpire() throws InterruptedException {
        cacheService.setCache("app1001", "1001-myGame", 3*1000);//有效期时间，单位为毫秒
        Assert.assertNotNull(cacheService.getCache("app1001"));
        Thread.sleep(2*1000);
        Assert.assertNotNull(cacheService.getCache("app1001"));
        Thread.sleep(1*1000);
        Assert.assertNull(cacheService.getCache("app1001"));
    }
    
    @Test
    public void testSetCacheExpire2() throws InterruptedException {
        cacheService.setCache("app1002", "1002-myGame", 5,TimeUnit.SECONDS);//有效期时间，单位自定义
        Assert.assertNotNull(cacheService.getCache("app1002"));
        Thread.sleep(3*1000);
        Assert.assertNotNull(cacheService.getCache("app1002"));
        Thread.sleep(2*1000);
        Assert.assertNull(cacheService.getCache("app1002"));
    }

    @Test
    public void testSetObjectCache() {
        App app = new App();
        app.setId(1);
        app.setAppCode("2000");
        app.setAppName("myGame");
        cacheService.setObjectCache("app:2000", app);

        App fromCacheApp = (App)cacheService.getObjectCache("app:2000");
        Assert.assertTrue("myGame".equals(fromCacheApp.getAppName()));
    }

    @Test
    public void testIncrCache() {
        Assert.assertTrue(cacheService.deleteCache("myIncrId"));
        cacheService.incrCache("myIncrId", 1L);
        cacheService.incrCache("myIncrId", 1L);
        cacheService.incrCache("myIncrId", 1L);
        cacheService.incrCache("myIncrId", 1L);

        String cacheValue = cacheService.getCache("myIncrId");
        Assert.assertTrue("4".equals(cacheValue));
    }

    @Test
    public void testDeleteCache() {
        cacheService.setCache("abc", "123");
        String value = cacheService.getCache("abc");
        Assert.assertTrue("123".equals(value));
        Assert.assertTrue(cacheService.deleteCache("abc"));
        Assert.assertNull(cacheService.getCache("abc"));
    }
}

```



## 四、参考资料

* springboot2.0集成redis使用  
https://my.oschina.net/mrfu/blog/1631805

* 一起来学SpringBoot | 第九篇：整合Lettuce Redis  
http://blog.battcn.com/2018/05/11/springboot/v2-nosql-redis/

* Spring Data Redis 官方文档学习 (v1.8.4)  
http://85b7be1a.wiz03.com/share/s/25JXUq1LUAMT2GB1mm3HpGN_1mntwp3w8AwP2euXtZ37pjBJ