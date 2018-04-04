package com.ximi.wendashequ.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by 单广美 on 2017/10/10.
 *
 * @Description: aop处理日志信息
 */
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("execution(* com.ximi.wendashequ.controller.*.*(..))")
    public void beforeMethod(){
        logger.info("----------------1");
    }

    @After("execution(* com.ximi.wendashequ.controller.*.*(..))")
    public void afterMethod(){
        logger.info("-------------12313");
    }
}
