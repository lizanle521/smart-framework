package org.smart4j.framework.proxy;

/**
 *  代理接口
 * Created by lizanle on 2017/4/13.
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws  Throwable;

}
