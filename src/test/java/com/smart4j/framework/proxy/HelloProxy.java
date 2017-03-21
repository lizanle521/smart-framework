package com.smart4j.framework.proxy;

/**
 * Created by Administrator on 2017/3/21.
 */
public class HelloProxy implements Hello {
    Hello hello = new HelloImpl();

    public void say(String name) {
        before();
        hello.say(name);
        after();
    }

    public void before(){

    }

    public void after(){

    }

    public static void main(String[] args) {
        HelloProxy helloProxy = new HelloProxy();
        helloProxy.say("李赞乐");
    }
}
