package org.jeedevframework.springboot.cache.impl;

import java.util.concurrent.TimeUnit;

import org.jeedevframework.springboot.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@SuppressWarnings("unchecked")
@Component
public class RedisCacheService implements CacheService {
	
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
    @Override
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
    
    @Override
    public String getCache(String key){
        return this.stringRedisTemplate.opsForValue().get(key);
    }
    
    @Override
    public Boolean deleteCache(String key){
        return this.redisTemplate.delete(key);
    }

}
