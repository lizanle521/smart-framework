package com.smart4j.framework.springaop;

import com.smart4j.framework.aop.Greeting;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by lizanle on 2017/4/13.
 */
public class ConfigClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext co = new ClassPathXmlApplicationContext("spring.xml");
        Greeting greetingProxy = (Greeting)co.getBean("exceptionGreetingProxy");
        greetingProxy.sayHello("李赞乐");
    }
}
