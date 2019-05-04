package org.jeedevframework.springboot.aop.annotation;

import java.lang.annotation.*;

/**
 *自定义注解 拦截service  
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented
public @interface ServiceLogAnnotation {
	String description()  default "";
	String logType()  default "";
}