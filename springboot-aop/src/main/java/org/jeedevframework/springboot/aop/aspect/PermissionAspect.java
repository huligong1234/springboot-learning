package org.jeedevframework.springboot.aop.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jeedevframework.springboot.aop.annotation.PermissionAnnotation;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;


@Aspect
@Component
public class PermissionAspect {

	 /**
     * 定义切点
     */
    @Pointcut("execution(public * org.jeedevframework.springboot.web.*.*(..))")
    public void privilege(){}

    /**
     * 权限环绕通知
     * @param joinPoint
     * @throws Throwable
     */
    @ResponseBody
    @Around("privilege()")
    public Object permissionCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取访问目标方法
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method targetMethod = methodSignature.getMethod();
        //得到方法的访问权限
        final String methodAccess = privilegeParse(targetMethod);

        //如果该方法上没有权限注解，直接调用目标方法
        if(StringUtils.isEmpty(methodAccess)){
            return joinPoint.proceed();
        }else {
            String currentUserRole = "MANAGER";
            if(methodAccess.equals(currentUserRole)){
               return joinPoint.proceed();
            }else {
                throw new Exception("权限错误");
            }
        }
    }
    
    
    /***
     * 解析权限注解
     * @return 返回注解的authorities值
     * @throws Exception
     */
    public static String privilegeParse(Method method) throws Exception {
        //获取该方法
        if(method.isAnnotationPresent(PermissionAnnotation.class)){
        	PermissionAnnotation annotation = method.getAnnotation(PermissionAnnotation.class);
            return annotation.authorities();
        }
        return null;
    }
}
