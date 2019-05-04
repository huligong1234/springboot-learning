package org.jeedevframework.springboot.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jeedevframework.springboot.aop.annotation.TrackTimeAnnotation;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TrackTimeAspect {
	
	@Pointcut("execution(* org.jeedevframework.springboot..*.*(..))")    
    public void trackTimeAspect() {    
    }
	
	/**
	 * 通过@annotation 统计耗时
	 * */
    @Around("@annotation(trackTimeAnnotation)")
    public Object trackTime(ProceedingJoinPoint joinPoint, TrackTimeAnnotation trackTimeAnnotation) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("@annotation-trackTime-------------> Time Taken by " + joinPoint + " -[" + trackTimeAnnotation.description() + "] is " + timeTaken + "ms");
        return result;
    }
    
    /**
     * 通过execution 统计耗时
     * */
    @Around("trackTimeAspect()")
    public Object trackTime2(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        long startTime = System.currentTimeMillis();
        result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        System.out.println("execution-trackTime-------------> Time Taken by " + joinPoint + " is " + timeTaken + "ms");
        return result;
    }
}