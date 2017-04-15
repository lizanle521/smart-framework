package com.smart4j.framework.springaop;

import com.smart4j.framework.aop.Apology;
import com.smart4j.framework.aop.GreetingImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 引入增强测试 和 切面测试
 * 当增加了BeanNameAutoProxyCreator这个类以后，已经配置过增强的类，还会被加上一层 增强
 * Created by lizanle on 2017/4/13.
 */
public class IntroduceConfigClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext con = new ClassPathXmlApplicationContext("spring.xml");
        GreetingImpl proxy = (GreetingImpl)con.getBean("introduceGreetingProxy");
        proxy.sayHello("ccc");
        System.out.println("---------");
        Apology apology = (Apology)proxy; // 引入增强
        apology.saySorry("cc");
        System.out.println("---------");
        // 定义切面（包括增强和 切点（正则表达式）），然后定义advisor
        // 直接定义到方法的切面，然后动态创建代理类
        GreetingImpl greetingProxyForAdvisor = (GreetingImpl)con.getBean("greetingImpl");
        greetingProxyForAdvisor.goodMorning("lzl");
    }
}
