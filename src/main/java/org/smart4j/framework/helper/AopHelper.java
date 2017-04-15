package org.smart4j.framework.helper;

import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.proxy.AspectProxy;
import org.smart4j.framework.proxy.Proxy;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Created by lizanle on 2017/4/14.
 */
public final  class AopHelper {

    /**
     * 获取所有带Acpect注解的类
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        HashSet<Class<?>> set = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if(annotation != null && annotation.equals(Aspect.class)){
            set.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return set;
    }

    /**
     * 获取代理类和目标类之间的关系
     * @return
     * @throws Exception
     */
    private static Map<Class<?>,Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?>,Set<Class<?>>> classHashMap = new HashMap<Class<?>,Set<Class<?>>>();
        Set<Class<?>> classSetBySuper = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> aClass : classSetBySuper) {
            if(aClass.isAnnotationPresent(Aspect.class)){
                Aspect annotation = aClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(annotation);
                classHashMap.put(aClass,targetClassSet);
            }
        }
        return classHashMap;
    }

    private static Map<Class<?>,List<Proxy>> createTargetMap(Map<Class<?>,Set<Class<?>>> proxyMap) throws Exception{
        return null;
    }
}
