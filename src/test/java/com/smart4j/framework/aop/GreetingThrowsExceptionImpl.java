package com.smart4j.framework.aop;

import org.springframework.stereotype.Component;

/**
 *
 * Created by lizanle on 2017/4/13.
 */
@Component
public class GreetingThrowsExceptionImpl implements Greeting {
    public void sayHello(String name) {
        System.out.println("hello,"+name);
        throw new NullPointerException("xx");
    }
}
