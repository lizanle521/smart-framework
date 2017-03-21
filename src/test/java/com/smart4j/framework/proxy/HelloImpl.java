package com.smart4j.framework.proxy;

import com.smart4j.framework.proxy.Hello;

/**
 * hello接口的实现类
 * Created by Administrator on 2017/3/21.
 */
public class HelloImpl  implements Hello {

    public void say(String name) {
        System.out.println(name+"您好");
    }
}
