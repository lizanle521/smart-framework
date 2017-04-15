package com.smart4j.framework.aspectj;

import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


/**
 * Created by lizanle on 2017/4/13.
 */

@Component
@EnableAspectJAutoProxy
public class GreetingAspectj {

    public Object around(MethodInvocationProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        before();
        Object result = proceedingJoinPoint.proceed();
        after();
        return result;

    }

    private void before() {
        System.out.println("before");
    }
    private void after(){
        System.out.println("after");
    }
}
