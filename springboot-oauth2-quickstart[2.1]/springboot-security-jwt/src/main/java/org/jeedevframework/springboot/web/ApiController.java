package org.jeedevframework.springboot.web;

import org.jeedevframework.springboot.entity.User;
import org.jeedevframework.springboot.security.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
 
 
    @PostMapping("/sayHello")
    public String sayHello(@CurrentUser User user,@RequestHeader("Authorization") String auth) {
    	System.out.println("CurrentUser["+user.getUsername()+","+user.getNickName()+"]");
    	System.out.println("Authorization["+auth+"]");
        return "Hello";
    }
}