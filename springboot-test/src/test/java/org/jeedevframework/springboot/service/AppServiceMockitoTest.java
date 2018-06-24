package org.jeedevframework.springboot.service;

import static org.mockito.Mockito.when;

import java.util.Date;

import org.jeedevframework.springboot.SpringbootTestApplication;
import org.jeedevframework.springboot.entity.App;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootTestApplication.class)
public class AppServiceMockitoTest {

	@Mock
	private AppService appService;
	
	
	@Before 
	public void setUp(){ 
		App mockApp = new App(); 
		mockApp.setId(1);
		mockApp.setAppCode("10000");
		mockApp.setAppName("myApp");
		mockApp.setGmtCreate(new Date()); 
		when(appService.findOne(1)).thenReturn(mockApp); 
	} 
	
	@Test 
	public void findOneTest() { 
		App app = appService.findOne(1); 
		System.out.println("appName:"+app.getAppName());
		Assert.assertEquals(app.getAppName(), "myApp"); 
	}
}
