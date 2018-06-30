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
