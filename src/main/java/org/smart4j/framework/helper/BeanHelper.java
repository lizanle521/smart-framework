package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Bean 助手类
 * Created by Administrator on 2017/3/16.
 */

public final class BeanHelper {
    private static final Logger logger = LoggerFactory.getLogger(BeanHelper.class);
    /**
     * 定义Bean映射 用于存放Bean与Bean实例之间的关系
     */
    public static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();

    static {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        for (Class<?> cls : classSet) {
            Object instance = ReflectionUtil.getInstance(cls);
            BEAN_MAP.put(cls,instance);
        }
    }

    /**
     * 获取类映射
     * @return
     */
    public static Map<Class<?> ,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取类实例
     * @param cls
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> cls){
        if(BEAN_MAP.containsKey(cls)) {
            return (T) BEAN_MAP.get(cls);
        }else{
            throw new RuntimeException("cannot get bean class:"+cls);
        }
    }

    /**
     * 设置bean 实例
     * @param cls
     * @param obj
     */
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls,obj);
    }




}
