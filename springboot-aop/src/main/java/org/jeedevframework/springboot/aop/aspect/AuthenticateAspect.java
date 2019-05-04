package org.jeedevframework.springboot.aop.aspect;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;

/**
 * 鉴权 & 验签
 * */
@Aspect
@Component
@Order(100)
public class AuthenticateAspect {
	
	private final Logger logger = LoggerFactory.getLogger(AuthenticateAspect.class);
	
	 /**
     * 定义切点
     */
    @Pointcut("execution(public * org.jeedevframework.springboot.rest.*.*(..))")
    public void authenticate(){}


    @Before("authenticate() && args(modelMap, apiVersion, apiCategory, apiCode)")
    public void authenticateExec(JoinPoint joinPoint,Map modelMap,String apiVersion,String apiCategory,String apiCode) {
    	logger.info(JSON.toJSONString(modelMap) + ","+ apiVersion + "," + apiCategory +","+apiCode);
    }
    
    //@Before("authenticate()")
    public void authenticateExec2(JoinPoint joinPoint) {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();    
    	//logger.info(request.getRequestURI());
    	//logger.info(request.getRemoteAddr());
    	//logger.info(JSON.toJSONString(request.getHeaderNames()));
    	
    	Signature signature = joinPoint.getSignature();
    	//代理的是哪一个方法
    	logger.info("方法："+signature.getName()); //AOP代理类的名字 
    	logger.info("方法所在包:"+signature.getDeclaringTypeName()); //AOP代理类的类（class）信息 signature.getDeclaringType(); 
    	MethodSignature methodSignature = (MethodSignature) signature; 
    	String[] strings = methodSignature.getParameterNames(); 
    	logger.info("参数名："+Arrays.toString(strings)); 
    	logger.info("参数值ARGS : " + Arrays.toString(joinPoint.getArgs()));

    	
    	if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			Object[] arguments = joinPoint.getArgs();
			Map reqParamMap = (Map)arguments[0];
			/*String apiVersion = (String)arguments[1];
			String apiCategory = (String)arguments[2];
			String apiCode = (String)arguments[3];*/
			//Object appId = reqParamMap.get("appId");
			logger.info(request.getRequestURI()+"-->params:"+JSON.toJSONString(reqParamMap));
			//TODO 鉴权，验签
    	}
    }
}
