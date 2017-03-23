package com.smart4j.framework.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *  Cglib动态代理
 *  可以代理没有接口的类
 * Created by Administrator on 2017/3/23.
 */
public class CgLibDynamicProxy implements MethodInterceptor {
    private static CgLibDynamicProxy instance;
    private CgLibDynamicProxy(){

    }

    public static CgLibDynamicProxy getInstance(){
        if(instance == null){
            synchronized (CgLibDynamicProxy.class){
                if(instance == null){
                    instance = new CgLibDynamicProxy();
                }
            }
        }
        return instance;
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<?> cls){
        return (T) Enhancer.create(cls,this);
    }

    public void before(){
        System.out.println("xxx");
    }

    public void after(){
        System.out.println("yyy");
    }

    public static void main(String[] args) {
        Greeting proxy = getInstance().getProxy(GreetingImpl.class);
        proxy.sayHello("zzz");
    }
}
