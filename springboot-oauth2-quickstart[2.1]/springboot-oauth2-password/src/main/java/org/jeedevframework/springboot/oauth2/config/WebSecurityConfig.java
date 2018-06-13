package org.jeedevframework.springboot.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * WebSecurityConfigurerAdapter用于保护oauth相关的endpoints，
 * 同时主要作用于用户的登录(form login,Basic auth)
 * 
 * */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated();
    }
	
/*	 @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .requestMatchers().anyRequest()
            .and().authorizeRequests().antMatchers("/oauth/*").permitAll();
    }*/

    //配置内存模式的用户
    @Bean
    @Override
    protected UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("demoUser1").password("{noop}123456").authorities("USER").build());
        manager.createUser(User.withUsername("demoUser2").password("{noop}123456").authorities("USER").build());
        return manager;
    }
    

    /**
     * 需要配置这个支持password模式
     * support password grant type
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean  
    public TokenStore tokenStore() {  
        //使用内存的tokenStore  
        return new InMemoryTokenStore();  
    }
}
