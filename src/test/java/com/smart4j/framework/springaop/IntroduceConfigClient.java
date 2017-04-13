package com.smart4j.framework.springaop;

import com.smart4j.framework.aop.Apology;
import com.smart4j.framework.aop.GreetingImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 引入增强测试
 * Created by lizanle on 2017/4/13.
 */
public class IntroduceConfigClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("spring.xml");
        GreetingImpl proxy = (GreetingImpl)con.getBean("introduceGreetingProxy");
        proxy.sayHello("ccc");
        Apology apology = (Apology)proxy;
        apology.saySorry("cc");
    }
}
