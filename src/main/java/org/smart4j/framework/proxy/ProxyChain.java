package org.smart4j.framework.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 为什么需要ProxyChain 来做这个链式调用呢？ 因为经过CGLib增强后的类不能再次被cglib增强
 * Created by lizanle on 2017/4/13.
 */
public class ProxyChain {

    /**
     * 目标类
     */
    private final Class<?> targetClass;
    /**
     * 目标对象
     */
    private final Object targetObject;
    /**
     * 目标方法
     */
    private final Method targetMethod;
    /**
     * 方法代理
     */
    private final MethodProxy methodProxy;
    /**
     * 方法参数
     */
    private final Object[] methodParams;
    /**
     * 代理列表
     */
    private List<Proxy> proxyList = new ArrayList<Proxy>();
    /**
     * 代理索引
     */
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,MethodProxy methodProxy, Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }



    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if(proxyIndex < proxyList.size()){
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}
