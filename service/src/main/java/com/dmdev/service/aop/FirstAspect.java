package com.dmdev.service.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class FirstAspect {

    @Pointcut("execution(public * com.dmdev.service.service.*Service.*(..))")
    void anyServiceMethod() {
    }

    @Before("anyServiceMethod() && target(service) ")
    public void addLoggingWithParam(JoinPoint joinPoint,
                           Object service
    ) {
        log.info("before - invoked method {} in class {}, with param {}",
                joinPoint.getSignature().getName(), service, joinPoint.getArgs());
    }

    @AfterReturning(value = "anyServiceMethod() && target(service)", returning = "result",
            argNames = "joinPoint,service,result")
    public void addLoggingAfter(JoinPoint joinPoint,
                                Object service,
                                Object result) {
        log.info("After returning - invoke method {} in class {}, result{}",
                joinPoint.getSignature().getName(), service, result);
    }
}
