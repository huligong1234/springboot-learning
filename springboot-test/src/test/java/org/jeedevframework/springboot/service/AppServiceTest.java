package org.jeedevframework.springboot.service;

import org.jeedevframework.springboot.SpringbootTestApplication;
import org.jeedevframework.springboot.entity.App;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.util.Assert;
import org.testng.annotations.Test;

@SpringBootTest(classes = SpringbootTestApplication.class)
public class AppServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private AppService appService;
	
	@Test
	public void testInsert() {
		App app = new App();
        app.setAppCode("200000");
        app.setAppName("myGames");
        
        app = this.appService.insert(app);
        
        Assert.notNull(app, "app save fail");
        Assert.notNull(app.getId(), "app save fail");
        Assert.isTrue(app.getId()>0,"app save fail");
	}
	
}
