package com.smart4j.framework.jdkproxy;

import com.smart4j.framework.proxy.Hello;
import com.smart4j.framework.proxy.HelloImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/3/21.
 */
public class DynamicProxy implements InvocationHandler {
    private Object object;
    public DynamicProxy(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(object, args);
        after();
        return invoke;
    }

    /**
     * 封装jdk动态代理实现
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(){
        return (T)Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),this);
    }

    public void before(){
        System.out.println("before");
    }
    public void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        DynamicProxy proxy = new DynamicProxy(new HelloImpl());
//        Hello o = (Hello)Proxy.newProxyInstance(HelloImpl.class.getClassLoader(),
//                HelloImpl.class.getInterfaces(),
//                proxy);
        Hello proxy1 = proxy.getProxy();
        proxy1.say("xxx");
    }
}
