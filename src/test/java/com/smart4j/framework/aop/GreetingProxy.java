package com.smart4j.framework.aop;

/**
 *  静态代理
 * Created by Administrator on 2017/3/23.
 */
public class GreetingProxy implements Greeting {
    private  GreetingImpl greeting ;

    public void sayHello(String name) {
        before();
        greeting.sayHello(name);
        after();
    }

    public  GreetingProxy(GreetingImpl greeting) {
        this.greeting = greeting;
    }

    public void before(){
        System.out.println("xxx");
    }
    public void after(){
        System.out.println("zzz");
    }

    public static void main(String[] args) {
        GreetingProxy proxy = new GreetingProxy(new GreetingImpl());
        proxy.sayHello("xxx");

    }
}
