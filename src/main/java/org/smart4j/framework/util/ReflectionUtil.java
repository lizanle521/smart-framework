package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/14.
 */
public class ReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 获取实例
     * @param clasz
     * @return
     */
    public static Object getInstance(Class<?> clasz){
        Object instance;
        try {
            instance = clasz.newInstance();
        } catch (Exception e) {
            logger.error("get instance failure",e);
            throw  new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     * @param obj
     * @param method
     * @param args
     */
    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result = null;
        try {
            //方法需要确认能够访问
            method.setAccessible(true);
            result = method.invoke(obj,args);
        } catch (Exception e) {
            logger.error("method invoke failure",e);
            throw  new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置属性值
     * @param object
     * @param field
     * @param value
     */
    public static void setFieldValue(Object object, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(object,value);
        } catch (IllegalAccessException e) {
            logger.error("field access failure",e);
            throw  new RuntimeException(e);
        }
    }


}
