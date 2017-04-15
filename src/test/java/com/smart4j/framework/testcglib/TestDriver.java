package com.smart4j.framework.testcglib;

import com.smart4j.framework.aop.GreetingImpl;
import org.junit.Test;
import org.smart4j.framework.proxy.Proxy;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/4/15.
 */
public class TestDriver {
    /**
     * 因为cgbli不能对自己增强过的类再次增强，所以以下的测试是会报错的
     */
    @Test
    public  void main() {
        Object proxy = new BeforeProxy().getProxy(GreetingImpl.class);
        Object proxy1 = new AfterProxy().getProxy(proxy.getClass());
        GreetingImpl imp = (GreetingImpl)proxy1;
        imp.sayHello("xxx");

    }


}
