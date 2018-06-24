package org.jeedevframework.springboot.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeedevframework.springboot.common.Constants;
import org.jeedevframework.springboot.common.Response;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.google.gson.Gson;

/**
 * 登录过滤器，登录验证成功后，生成JWT，加密后加上Token前缀(Bearer)，然后以JSON格式返回body
 * 
 * 登录成功的返回示例：
  {
  "code":"2000",
  "data":"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyOTgxNDg1M30.vx1KGFOTfyxJYaWiaQ7EbYPFeWVNxmC5p8cIJhEivwoWgRuLoz0UUHjIqPCgyNybS0nyHEG73WSGhkyQGqVHTg"
  }
 * 
 * */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super();
        this.setAuthenticationManager(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
    	//String username = ((org.springframework.security.core.userdetails.UserDetails) auth.getPrincipal()).getUsername();
    	String token = TokenAuthenticationService.addAuthentication(res, auth.getName());
        res.getWriter().write(new Gson().toJson(Response.succeed("Bearer " + token)));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write( new Gson().toJson((Response.fail(Constants.MSG_LOGIN_FAIL))));
    }

}
