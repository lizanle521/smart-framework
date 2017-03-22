package com.smart4j.framework.cglibproxy;

import com.smart4j.framework.proxy.Hello;
import com.smart4j.framework.proxy.HelloImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 * Created by Administrator on 2017/3/21.
 */
public class CglibProxy implements MethodInterceptor{
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o,objects);
        after();
        return result;
    }

    public void before(){
        System.out.println("before");
    }

    public void after(){
        System.out.println("after");
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<?> cls){
        return  (T) Enhancer.create(cls, this);
    }

    public static void main(String[] args) {
        CglibProxy instance = new CglibProxy();
        HelloImpl proxy = instance.getProxy(HelloImpl.class);
        proxy.say("xxx");

    }
}
