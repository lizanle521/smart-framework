package org.smart4j.framework.bean;

import java.lang.reflect.Method;

/**
 * 封装action信息
 * Created by Administrator on 2017/3/17.
 */
public class Handler {
    /**
     * controller信息
     */
    private Class<?> controllerClass;

    /**
     * Action 方法
     */
    private Method method;

    public Handler(Class<?> controllerClass, Method method) {
        this.controllerClass = controllerClass;
        this.method = method;
    }

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getMethod() {
        return method;
    }
}
