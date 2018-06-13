package org.jeedevframework.springboot.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * ResourceServerConfigurerAdapter用于保护oauth要开放的资源，
 * 同时主要作用于client端以及token的认证(Basic auth)
 * 
 * */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 这里设置需要token验证的url
     * 这些需要在WebSecurityConfigurerAdapter中排查掉
     * 否则优先进入WebSecurityConfigurerAdapter,进行的是basic auth或表单认证,而不是token认证
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/api/**")
                .and()
                .authorizeRequests()
                .antMatchers("/api/**").authenticated();
    }


}