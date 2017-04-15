package org.smart4j.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Created by lizanle on 2017/4/14.
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Object[] methodParams = proxyChain.getMethodParams();
        Method targetMethod = proxyChain.getTargetMethod();
        begin();

        try {
            if(intercept(targetClass,targetMethod,methodParams)){
                before(targetClass,targetMethod,methodParams);
                result = proxyChain.doProxyChain();
                after(targetClass,targetMethod,methodParams);
            }else{
                result = proxyChain.doProxyChain();
            }
            return result;
        } catch (Exception e) {
            logger.error("proxy failure",e);
            error(targetClass,targetMethod,methodParams,e);
            throw  e;
        }finally {
            end();
            return result;
        }

    }

    public void begin() {

    }

    public boolean intercept(Class<?> cls,Method method,Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls,Method method,Object[] params) throws Throwable {

    }

    public void after(Class<?> cls,Method method,Object[] params) throws Throwable {

    }

    public void error(Class<?> cls,Method method,Object[] params,Exception e) throws Throwable {

    }

    public void end() {

    }

}
