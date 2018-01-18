package com.workec.ectp.aspect;

import com.workec.ectp.entity.Result;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by user on 2017/11/14.
 */
@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);

    @Pointcut("execution(public * com.workec.ectp.controller.*.*(..))")
    public void log(){
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){

        logger.info("---Before---");
        // 请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 请求对象
        HttpServletRequest request = attributes.getRequest();
        //ip
        logger.info("ip={}", request.getRemoteAddr());
        //方法名
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //请求方法+url+args
        logger.info("method:{},url:{},args:{}", request.getMethod(), request.getRequestURL(),joinPoint.getArgs());

    }

    @After("log()")
    public void doAfter(){
        logger.info("---After---");
    }

    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturning(Result result){
        logger.info("response{}",(result==null||result.equals(""))?null:result.toString());
    }
}
