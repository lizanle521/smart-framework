package com.smart4j.framework.testcglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/15.
 */
public class BeforeProxy implements MethodInterceptor {

    public Object getProxy(Class cls){
        return Enhancer.create(cls,this);
    }
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        return  methodProxy.invokeSuper(o, objects);
    }

    public void before() {
        System.out.println("before");
    }
}
