package org.jeedevframework.springboot.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.jeedevframework.springboot.SpringbootTestApplication;
import org.jeedevframework.springboot.entity.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

@SpringBootTest(classes = SpringbootTestApplication.class)
@Rollback
@Transactional
//@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
public class AppControllerJunitTest {
	
	@Resource
    private WebApplicationContext context;

    private MockMvc mockMvc;
	    
    private Gson gson = new Gson();
    
    private HttpHeaders httpHeaders = new HttpHeaders();
    
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        httpHeaders.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
    }
	
	@Test
	//@Ignore
    public void testAddOne() throws Exception {
        App app = new App();
        app.setAppCode("100309");
        app.setAppName("myGames");
        
        MvcResult mvcResult = this.mockMvc.perform(
                post("/app/add")
                .headers(httpHeaders)
                //.contentType(MediaType.APPLICATION_JSON_UTF8).content(gson.toJson(app))
                .param("appCode", app.getAppCode())
                .param("appName", app.getAppName())
                )
                .andDo(print())
                .andExpect(status().isOk())
                //.andExpect(status().is2xxSuccessful())
               // .andExpect(jsonPath("$.code").value("success"))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        System.out.println(result);
    }
	
	 @Test
	    public void testFindAll() throws Exception{
	        MvcResult mvcResult = this.mockMvc.perform(
	                get("/app/all")
	                .headers(httpHeaders))
	                .andDo(print())
	                .andExpect(status().isOk())
	             // .andExpect(jsonPath("$.code").value("success"))
	                .andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println(result);
	    }

	    @Test
	    public void testFind() throws Exception{
	        MvcResult mvcResult = this.mockMvc.perform(
	                get("/app/find")
	                //.contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
	                .param("id", "1")
	                .headers(httpHeaders))
	                .andDo(print())
	                .andExpect(status().isOk())
	             // .andExpect(jsonPath("$.code").value("success"))
	                .andReturn();
	        String result = mvcResult.getResponse().getContentAsString();
	        System.out.println(result);
	    }
}
