package com.smart4j.framework.testcglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/4/15.
 */
public class AfterProxy implements MethodInterceptor {

    public Object getProxy(Class cls){
        return Enhancer.create(cls,this);
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object o1 = methodProxy.invokeSuper(o, objects);
        after();
        return o1;
    }

    public void after(){
        System.out.println("after");
    }
}
