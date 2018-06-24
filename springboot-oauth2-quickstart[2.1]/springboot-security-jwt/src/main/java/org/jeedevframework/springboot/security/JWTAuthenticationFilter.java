package org.jeedevframework.springboot.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeedevframework.springboot.common.RequestContextProxy;
import org.jeedevframework.springboot.common.Response;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.google.gson.Gson;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * 拦截所有需要JWT的请求的拦截器，去做JWT验证
 * */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
        	Authentication authentication = TokenAuthenticationService.getAuthentication(request,userDetailsService);
        	if(null != authentication) {
        		 SecurityContextHolder.getContext().setAuthentication(authentication);
                 RequestContextProxy.putRequestUsername(authentication.getName());
        	}
            chain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            //response.setStatus(403);
            response.getWriter().write(new Gson().toJson(Response.fail("4030", ex.toString())));
            return;
        } finally {
            RequestContextProxy.clear();
        }
    }
}