package com.smart4j.framework.cglibproxy;

import com.smart4j.framework.proxy.Hello;
import com.smart4j.framework.proxy.HelloImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * cglib动态代理优化
 * Created by Administrator on 2017/3/22.
 */
public class CglibProxySingleton  implements MethodInterceptor{
    private static CglibProxySingleton instance = new CglibProxySingleton();
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    public static CglibProxySingleton getInstance(){
        return instance;
    }

    public void before(){
        System.out.println("xxx");
    }

    public void after(){
        System.out.println("xxxx");
    }

    public <T> T getProxy(Class<?> cls){
        return (T) Enhancer.create(cls,this);
    }

    public static void main(String[] args) {
        Hello proxy = CglibProxySingleton.getInstance().getProxy(HelloImpl.class);
        proxy.say("111");
    }
}
