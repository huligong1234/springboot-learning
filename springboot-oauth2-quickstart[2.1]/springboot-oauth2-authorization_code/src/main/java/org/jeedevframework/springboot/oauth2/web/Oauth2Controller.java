package org.jeedevframework.springboot.oauth2.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2Controller {

	@RequestMapping("/callback_oauth_provider")
	public String oauthProvider(@RequestParam("code") String code,@RequestParam("state") String state){
		System.out.println("code:"+code);
		System.out.println("state:"+state);
		//TODO get access_token
		return null;
	}
}
