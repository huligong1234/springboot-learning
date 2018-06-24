package org.jeedevframework.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用于配置security 相关的规则
 */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    static final String SECRET = "SECRET";

    @Autowired
    UserDetailsService customerUserDetailService;


    /**
     * 完全绕过spring security的所有filter.
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web){
        //web.ignoring().antMatchers("/i18n/en");
        //web.ignoring().antMatchers("/i18n/cn");
    }

    /**
     * 需要走spring security, 包含登录和匿名
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()// 关闭csrf验证
        		// 基于token，所以不需要session
        		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        		.authorizeRequests()// 对请求进行认证
        		.antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")// 角色检查
                .antMatchers("/api/**").authenticated() // 所有 /api 请求需要身份认证
                .antMatchers("/index/**").authenticated() // 所有 /index 请求需要身份认证
                .anyRequest().permitAll() // 其他 的所有请求 都放行
                // 除上面外的所有请求全部需要鉴权认证
                //.anyRequest().authenticated();
                .and()
                // 添加一个过滤器 所有访问 /login 的请求交给 JWTLoginFilter 来登录验证处理和生成处理所有的JWT相关内容
                .addFilter(new JWTLoginFilter(authenticationManager()))
                // 添加一个过滤器验证其他请求的Token是否合法
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), customerUserDetailService));
        
        		// 禁用缓存
        		//http.headers().cacheControl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService)
        // 使用BCrypt进行密码的hash
        .passwordEncoder(passwordEncoder());;
     
        // 使用自定义身份验证组件
        //auth.authenticationProvider(new CustomAuthenticationProvider());
    }
    
   //装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
    }
}