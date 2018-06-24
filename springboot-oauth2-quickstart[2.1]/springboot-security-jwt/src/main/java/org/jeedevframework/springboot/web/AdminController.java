package org.jeedevframework.springboot.web;

import org.jeedevframework.springboot.entity.User;
import org.jeedevframework.springboot.security.CurrentUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RestController
@RequestMapping("/admin")
public class AdminController {
 
 
    @PostMapping("/user/add")
    public String addUser(@CurrentUser User user,@RequestHeader("Authorization") String auth) {
    	System.out.println("CurrentUser["+user.getUsername()+","+user.getNickName()+"]");
    	System.out.println("Authorization["+auth+"]");
        return "success";
    }
}