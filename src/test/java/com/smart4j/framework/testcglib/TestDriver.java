package com.smart4j.framework.testcglib;

import com.smart4j.framework.aop.GreetingImpl;

/**
 * 因为cgbli不能对自己增强过的类再次增强，所以以下的测试是会报错的
 * Created by Administrator on 2017/4/15.
 */
public class TestDriver {
    public static void main(String[] args) {
        Object proxy = new BeforeProxy().getProxy(GreetingImpl.class);
        Object proxy1 = new AfterProxy().getProxy(proxy.getClass());
        GreetingImpl imp = (GreetingImpl)proxy1;
        imp.sayHello("xxx");

    }
}
