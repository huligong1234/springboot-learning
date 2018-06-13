package org.jeedevframework.springboot.oauth2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
 
    @Autowired
    private TokenStore tokenStore;
 
    @RequestMapping("/sayHello")
    public String sayHello(
    		@RequestHeader(value="Authorization",defaultValue="") String auth,
    		@RequestParam(value = "access_token", required = false) String access_token
    		) {
    	String req_access_token = null;
    	if(!StringUtils.isEmpty(auth)){
    		req_access_token = auth.split(" ")[1];
    	}else{
    		req_access_token = access_token;
    	}
    	if(!StringUtils.isEmpty(req_access_token)){
    		 User userDetails = (User) tokenStore.readAuthentication(req_access_token).getPrincipal();
    	     System.out.println(userDetails.getUsername());
    	}
        return "Hello";
    }
}