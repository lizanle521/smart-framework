package com.smart4j.framework.aop;

import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *  Jdk动态代理
 *  这里不能代理没有接口的类
 * Created by Administrator on 2017/3/23.
 */
public class JdkGreetingProxy implements InvocationHandler {

    private Object object;
    public JdkGreetingProxy(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(object,args);
        after();
        return invoke;
    }

    @SuppressWarnings("unchecked")
    public  <T> T getProxy(){
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                this);
    }

    public void before(){
        System.out.println("xxx");
    }

    public void after(){
        System.out.println("xxx");
    }

    public static void main(String[] args) {
        Greeting proxy = new JdkGreetingProxy(new GreetingImpl()).getProxy();
        proxy.sayHello("222");
    }
}
