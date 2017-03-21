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

    public void before(){
        System.out.println("before");
    }
    public void after(){
        System.out.println("after");
    }

    public static void main(String[] args) {
        DynamicProxy proxy = new DynamicProxy(new HelloImpl());
        Hello o = (Hello)Proxy.newProxyInstance(HelloImpl.class.getClassLoader(),
                HelloImpl.class.getInterfaces(),
                proxy);
        o.say("李赞乐");
    }
}
