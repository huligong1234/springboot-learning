package org.jeedevframework.springboot.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import javax.annotation.Resource;

import org.jeedevframework.springboot.SpringbootTestApplication;
import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.entity.App;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

/**
 * AbstractTestNGSpringContextTests 没有事务
 * AbstractTransactionalTestNGSpringContextTests 默认启用事务和自动回滚。
 * 如果不想回滚，可手动添加注解 @Rollback(false)
 * 
 */
// @ActiveProfiles(profiles = "dev")
@SpringBootTest(classes = SpringbootTestApplication.class)
@Rollback(false)
public class AppControllerTestngTest extends AbstractTransactionalTestNGSpringContextTests {

	private final static String CODE = "$.code";
	private final static String SUCCESS = "2000";
	private final static String FAIL = "4000";

	@Resource
	private WebApplicationContext context;

	private MockMvc mockMvc;

	private Random rand = new Random();

	private Gson gson = new Gson();

	private HttpHeaders httpHeaders = new HttpHeaders();

	private App testApp = new App();

	@BeforeClass
	public void init() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		httpHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);

		String appCode = String.valueOf(System.currentTimeMillis());
		String appName = appCode + "应用名称";
		testApp.setAppCode(appCode);
		testApp.setAppName(appName);
	}

	@Test
	public void testAddOne() throws Exception {
		App app = new App();
		BeanUtils.copyProperties(testApp, app);

		MvcResult mvcResult = this.mockMvc.perform(post("/app/add").headers(httpHeaders)
				// .contentType(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(app))
				.param("appCode", app.getAppCode()).param("appName", app.getAppName())).andDo(print())
				.andExpect(status().isOk())
				// .andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath(CODE).value(SUCCESS)).andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
	}

	@Test(priority = 1, dependsOnMethods = { "testAddOne" })
	public void testFindByAppCode() throws Exception {
		MvcResult mvcResult = this.mockMvc
				.perform(get("/app/findByAppCode").headers(httpHeaders).param("appCode", testApp.getAppCode()))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data.appCode").value(testApp.getAppCode()))
				.andExpect(jsonPath("$.data.appName").value(testApp.getAppName()))
				.andExpect(jsonPath("$.data.id").isNumber()).andExpect(jsonPath("$.data.gmtCreate").isNotEmpty())
				.andDo(new addDoSetValue()).andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
		
		//error branch
		this.mockMvc.perform(get("/app/findByAppCode")
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED) //数据的格式
				.headers(httpHeaders).param("appCode", String.valueOf(Integer.MAX_VALUE))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test(priority = 1, alwaysRun = true, dependsOnMethods = { "testAddOne" })
	public void testFindAll() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/app/all").headers(httpHeaders)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data").isArray()).andExpect(jsonPath("$.data").isNotEmpty())
				// .andExpect(jsonPath("$.data[0].id").isNotEmpty())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println("testFindAll:" + result);
	}

	@Test(priority = 2, dependsOnMethods = { "testFindByAppCode" })
	public void testFind() throws Exception {
		Assert.isTrue(testApp.getId() > 0, "id must >  0");

		
		MvcResult mvcResult = this.mockMvc.perform(get("/app/find")
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED) //数据的格式
				.headers(httpHeaders).param("id", String.valueOf(testApp.getId()))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data.id").isNumber()).andExpect(jsonPath("$.data.gmtCreate").isNotEmpty())
				.andReturn();
		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
		
	
		//error branch
		this.mockMvc.perform(get("/app/find")
				// .contentType(MediaType.APPLICATION_FORM_URLENCODED) //数据的格式
				.headers(httpHeaders).param("id", String.valueOf(Integer.MAX_VALUE))).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data").isEmpty());
		
		this.mockMvc.perform(get("/app/find")
				.headers(httpHeaders)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath(CODE).value(SUCCESS))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.message").value(Constants.MISS_MUST_PARAMETER));
	}

	@Test(priority = 3, dependsOnMethods = { "testFindByAppCode" })
	public void testUpdateOne() throws Exception {
		Assert.isTrue(testApp.getId() > 0, "id must >  0");

		String appCode = String.valueOf(rand.nextInt(6));
		String appName = appCode + "应用名称";

		MvcResult mvcResult = this.mockMvc
				.perform(put("/app/update").headers(httpHeaders).param("id", String.valueOf(testApp.getId()))
						.param("appCode", appCode).param("appName", appName))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS)).andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);

		testApp.setAppCode(appCode);
		testApp.setAppName(appName);
	}

	@Test(priority = 10, dependsOnMethods = { "testFindByAppCode" })
	public void testDeleteOne() throws Exception {
		Assert.isTrue(testApp.getId() > 0, "id must >  0");

		MvcResult mvcResult = this.mockMvc
				.perform(delete("/app/delete").headers(httpHeaders).param("id", String.valueOf(testApp.getId())))
				.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(CODE).value(SUCCESS)).andReturn();

		String result = mvcResult.getResponse().getContentAsString();
		System.out.println(result);
		
		
		//error branch test
		this.mockMvc.perform(delete("/app/delete")
				.headers(httpHeaders)
				.param("id", String.valueOf(Integer.MAX_VALUE)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath(CODE).value(FAIL))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.message").value(Constants.MISS_MUST_PARAMETER));
		
		this.mockMvc.perform(delete("/app/delete")
				.headers(httpHeaders)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath(CODE).value(FAIL))
				.andExpect(jsonPath("$.data").isEmpty())
				.andExpect(jsonPath("$.message").value(Constants.MISS_MUST_PARAMETER));
	}

	class addDoSetValue implements ResultHandler {
		@Override
		public void handle(MvcResult result) throws Exception {
			String ret = result.getResponse().getContentAsString();
			testApp.setId(JsonPath.read(ret, "$.data.id"));
			System.out.println("addDoSetValue:" + JsonPath.read(ret, "$.data.id"));
		}

	}
}
