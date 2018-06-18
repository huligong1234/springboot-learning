package org.jeedevframework.springboot.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

@EnableTransactionManagement
@Configuration
@MapperScan("org.jeedevframework.springboot.mapper*")
public class MybatisPlusConfig {
   /*
    * 分页插件，自动识别数据库类型
    * 多租户，请参考官网【插件扩展】
    */
   @Bean
   public PaginationInterceptor paginationInterceptor() {
      return new PaginationInterceptor();
   }
}