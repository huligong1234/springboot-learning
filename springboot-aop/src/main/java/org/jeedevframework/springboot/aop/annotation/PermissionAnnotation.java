package org.jeedevframework.springboot.aop.annotation;

import java.lang.annotation.*;

/**
 *自定义注解 权限控制
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionAnnotation {
   String authorities() default "ADMIN";
}