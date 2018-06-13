package org.jeedevframework.springboot.oauth2.web;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
 
 
    @PostMapping("/sayHello")
    public String sayHello(@RequestHeader("Authorization") String auth) {
    	//System.out.println(auth);//Bearer 583080c0-c281-4ccc-af40-754bf269ca9c
        return "Hello";
    }
}