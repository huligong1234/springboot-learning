package org.jeedevframework.springboot.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class AppConfig  {
    @Bean
    public Docket docket() {
        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        pars.add(builder.name("authorization")
        		.description("authorization")
        		.modelRef(new ModelRef("string"))
        		.parameterType("header").required(false).build());
       
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.jeedevframework.springboot"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(pars);
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger2")
                .description("Swagger官网 https://swagger.io/")
                .termsOfServiceUrl("https://swagger.io/")
                .version("1.0")
                .build();
    }

}