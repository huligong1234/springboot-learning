package org.jeedevframework.springboot.aop.annotation;

import java.lang.annotation.*;

/**
 *自定义注解  统计耗时
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface TrackTimeAnnotation {
    String description() default "";
}
